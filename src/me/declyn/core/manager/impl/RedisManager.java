package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.util.CC;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.commands.RedisPipeline;

public class RedisManager extends Manager {

    private JedisPool jedisPool;
    private Jedis jedisSubscriber;

    public RedisManager(ManagerHandler managerHandler) {
        super(managerHandler);
        if (managerHandler.getFileManager().getOptionsYaml().getBoolean("database.redis.use")) {
            setupRedis();
        }
    }

    public void sendMessage(String message) {

        if (!managerHandler.getFileManager().getOptionsYaml().getBoolean("database.redis.use")) {
            for (Player all : getPlugin().getServer().getOnlinePlayers()) {
                all.sendMessage(CC.translate(message));
            }
        }

        this.publish("CoreMessage", message);
    }

    public void sendPermissionMessage(String permission, String message) {

        if (!managerHandler.getFileManager().getOptionsYaml().getBoolean("database.redis.use")) {
            for (Player all : getPlugin().getServer().getOnlinePlayers()) {
                if (all.hasPermission(permission)) {
                    all.sendMessage(CC.translate(message));
                }
            }
        }

        this.publish("CorePermissionMessage", permission + "//" + message);
    }

    private void publish(String channel, String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Jedis jedis = jedisPool.getResource()) {
                    if (managerHandler.getFileManager().getOptionsYaml().getBoolean("database.redis.auth.enabled")) {
                        jedis.auth(managerHandler.getFileManager().getOptionsYaml().getString("database.redis.auth.password"));
                    }

                    jedis.publish(channel, message);
                }
            }
        }, "CoreJedisPublisherThread").start();
    }

    private void subscribe(String... channels) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedisSubscriber = new Jedis(managerHandler.getFileManager().getOptionsYaml().getString("database.redis.host"), managerHandler.getFileManager().getOptionsYaml().getInt("database.redis.port"));
                if (managerHandler.getFileManager().getOptionsYaml().getBoolean("database.redis.auth.enabled")) {
                    jedisSubscriber.auth(managerHandler.getFileManager().getOptionsYaml().getString("database.redis.auth.password"));
                }
                try {
                    jedisSubscriber.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            if (channel.equals("CoreMessage")) {
                                for (Player all : getPlugin().getServer().getOnlinePlayers()) {
                                    all.sendMessage(CC.translate(message));
                                }
                            }

                            if (channel.equals("CorePermissionMessage")) {
                                String[] fullMessage = message.split("//");
                                String permission = fullMessage[0];
                                String messageToSend = fullMessage[1];

                                for (Player all : getPlugin().getServer().getOnlinePlayers()) {
                                    if (all.hasPermission(permission)) {
                                        all.sendMessage(CC.translate(messageToSend));
                                    }
                                }
                            }

                        }
                    }, channels);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setupRedis() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(RedisPipeline.class.getClassLoader());
        jedisPool = new JedisPool(managerHandler.getFileManager().getOptionsYaml().getString("database.redis.host"), managerHandler.getFileManager().getOptionsYaml().getInt("database.redis.port"));
        Thread.currentThread().setContextClassLoader(classLoader);

        String[] channels = new String[]{"CoreMessage", "CorePermissionMessage"};
        subscribe(channels);

        if(jedisPool != null) {
            sendConsoleMessage("&a[Core] Connected to redis and subscribing to channels.");
        }
    }

}
