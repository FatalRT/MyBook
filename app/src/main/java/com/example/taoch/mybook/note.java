package com.example.taoch.mybook;

import java.sql.Time;

/*便签类*/
public class note {

    private int id;
    private String name;
    private Time tine;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Time getTine() {
        return tine;
    }

    public void setTine(Time tine) {
        this.tine = tine;
    }
}
