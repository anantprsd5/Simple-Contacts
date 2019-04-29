package com.pratilipi.contacts.Model;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class ContactObserver extends ContentObserver {

    public ContactObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
        Log.wtf("abvcx", "yesss");
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        Log.wtf("abvcx", "yesss");
    }
}
