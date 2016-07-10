package com.stom3.android.postponeMessage;

import com.stom3.android.BaseActivity;

public class PostponeMessage {
    private String text;
    private BaseActivity.OnPostponeListener listener;

    public PostponeMessage() {
    }

    public PostponeMessage(String text, BaseActivity.OnPostponeListener listener) {
        this.text = text;
        this.listener = listener;
    }

    public String getText() {
        return text;
    }
    public BaseActivity.OnPostponeListener getListener() {
        return listener;
    }

}
