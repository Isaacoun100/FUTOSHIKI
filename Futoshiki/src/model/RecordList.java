package model;

import java.util.ArrayList;

public class RecordList {

    private ArrayList<Record> easy = new ArrayList<>();
    private ArrayList<Record> intermediate  = new ArrayList<>();
    private ArrayList<Record> hard = new ArrayList<>();

    public ArrayList<Record> getEasy() {
        return easy;
    }

    public void setEasy(ArrayList<Record> easy) {
        this.easy = easy;
    }

    public ArrayList<Record> getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(ArrayList<Record> intermediate) {
        this.intermediate = intermediate;
    }

    public ArrayList<Record> getHard() {
        return hard;
    }

    public void setHard(ArrayList<Record> hard) {
        this.hard = hard;
    }

    public void addEasy(Record record) {
        easy.add(record);
    }

    public void addIntermediate(Record record) {
        intermediate.add(record);
    }

    public void addHard(Record record) {
        hard.add(record);
    }

}
