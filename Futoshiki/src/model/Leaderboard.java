package model;

import java.util.ArrayList;

public class Leaderboard {

    private RecordList _3by3 = new RecordList() ;
    private RecordList _4by4 = new RecordList();
    private RecordList _5by5 = new RecordList();
    private RecordList _6by6 = new RecordList();
    private RecordList _7by7 = new RecordList();
    private RecordList _8by8 = new RecordList();
    private RecordList _9by9 = new RecordList();
    private RecordList _10by10 = new RecordList();

    public RecordList getRecord(int i) {
        return switch (i) {
            case 3 -> _3by3;
            case 4 -> _4by4;
            case 5 -> _5by5;
            case 6 -> _6by6;
            case 7 -> _7by7;
            case 8 -> _8by8;
            case 9 -> _9by9;
            case 10 -> _10by10;
            default -> null;
        };
    }

    public void setRecord(int i, RecordList record) {
        switch (i) {
            case 3 -> _3by3 = record;
            case 4 -> _4by4 = record;
            case 5 -> _5by5 = record;
            case 6 -> _6by6 = record;
            case 7 -> _7by7 = record;
            case 8 -> _8by8 = record;
            case 9 -> _9by9 = record;
            case 10 -> _10by10 = record;
            default -> {}
        }
    }

}
