package com.pratilipi.contacts.View;

import android.graphics.Bitmap;

public interface DetailView {

    void onImageBitmapFetched(Bitmap bitmap);
    void onContactEmailFetched(String email);
    void onContactAddressFetched(String address);
}
