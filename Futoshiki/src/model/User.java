package model;

import java.util.ArrayList;

public class User {

    private Settings settings;
    private String password;
    private int[][] matrix;
    private ArrayList<Constrain> constrains;
    private String username;

    public User(Settings settings, String password, int[][] matrix, ArrayList<Constrain> constrains, String username) {
        this.settings = settings;
        this.password = password;
        this.matrix = matrix;
        this.constrains = constrains;
        this.username = username;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Constrain> getConstrains() {
        return constrains;
    }

    public void setConstrains(ArrayList<Constrain> constrains) {
        this.constrains = constrains;
    }
}
