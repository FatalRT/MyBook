package com.example.taoch.mybook;

import org.litepal.crud.LitePalSupport;

/*用户类*/
public class user  extends LitePalSupport {
    private int id;
    private String name;
    private String paw;

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

    public String getPaw() {
        return paw;
    }

    public void setPaw(String paw) {
        this.paw = paw;
    }
}
