package com.example.fis;

public class DataAdd {
    private int id_film;
    private int start;
    private int stop;
    private String type_room;
    private String date;

    public DataAdd(int id_film, int start, int stop, String type_room, String date) {
        this.id_film = id_film;
        this.start = start;
        this.stop = stop;
        this.type_room = type_room;
        this.date = date;
    }

    public int getId_film() {
        return id_film;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public String getType_room() {
        return type_room;
    }

    public String getDate() {
        return date;
    }
}
