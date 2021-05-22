package com.ayprojects.helpinghands.models;

import java.beans.Transient;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class ProductPricesVisibleUsers extends AllCommonUsedAttributes{
    private String userId;
    private String userName;

    @Ignore
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
