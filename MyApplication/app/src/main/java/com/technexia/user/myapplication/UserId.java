package com.technexia.user.myapplication;

import android.support.annotation.NonNull;

/**
 * Created by User on 2/1/2018.
 */

public class UserId {
    public String userId;

    public <T extends UserId> T withId(@NonNull final String id){
        this.userId = id;
        return (T) this;
    }
}
