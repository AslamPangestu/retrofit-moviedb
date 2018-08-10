package com.mvryan.katalogfilm.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mvryan on 09/08/18.
 */

public class Genre {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private int name;

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }
}
