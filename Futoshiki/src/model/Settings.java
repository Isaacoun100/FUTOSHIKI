package model;

public class Settings {

    private int size;
    private String difficulty;
    private boolean multilevel;
    private String clock;
    private String side;

    public Settings(int size, String difficulty, boolean multilevel, String clock, String button) {
        this.size = size;
        this.difficulty = difficulty;
        this.multilevel = multilevel;
        this.clock = clock;
        this.side = button;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isMultilevel() {
        return multilevel;
    }

    public void setMultilevel(boolean multilevel) {
        this.multilevel = multilevel;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
