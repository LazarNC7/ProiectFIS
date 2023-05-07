package com.example.fis;

public class DataClass {
    private String title;
    private Integer startT;
    private Integer finish;
    private String dateT;
    private String roomT;

    public DataClass(String title, Integer startT, Integer finish, String dateT, String roomT) {
        this.title = title;
        this.startT = startT;
        this.finish = finish;
        this.dateT = dateT;
        this.roomT = roomT;
    }

    public String getTitle() {
        return title;
    }

    public Integer getStartT() {
        return startT;
    }

    public Integer getFinish() {
        return finish;
    }

    public String getDateT() {
        return dateT;
    }

    public String getRoomT() {
        return roomT;
    }
}
