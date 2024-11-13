package model;

import java.util.ArrayList;

public class Board {

    private ArrayList<Value> values = new ArrayList<>();
    private ArrayList<Constrain> constraints = new ArrayList<>();

    public Board(ArrayList<Value> values, ArrayList<Constrain> constraints) {
        this.values = values;
        this.constraints = constraints;
    }

    public ArrayList<Value> getValues() {
        return values;
    }

    public ArrayList<Constrain> getConstraints() {
        return constraints;
    }

    public void setValues(ArrayList<Value> values) {
        this.values = values;
    }

    public void setConstraints(ArrayList<Constrain> constraints) {
        this.constraints = constraints;
    }
}
