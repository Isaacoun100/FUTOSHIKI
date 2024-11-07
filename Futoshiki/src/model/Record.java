package model;

import java.time.LocalTime;

public class Record {

    private String username;
    private LocalTime time;

    public Record(String username, LocalTime time) {
        this.username = username;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
