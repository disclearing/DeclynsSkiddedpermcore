package me.declyn.core.objects;

public class Rank implements Comparable<Rank> {

    private String name;
    private String prefix;
    private String color;
    private int weight;
    private boolean firstJoin;

    public Rank(String name) {
        this.name = name;
        this.prefix = "&f";
        this.color = "&f";
        this.weight = 0;
        this.firstJoin = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isFirstJoin() {
        return firstJoin;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public void setFirstJoin(boolean firstJoin) {
        this.firstJoin = firstJoin;
    }

    public String getColoredName() {
        return getColor() + getName();
    }

    @Override
    public int compareTo(Rank other) {
        return Integer.compare(this.weight, other.weight);
    }

}
