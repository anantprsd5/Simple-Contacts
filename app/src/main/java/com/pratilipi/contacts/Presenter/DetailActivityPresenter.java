package com.pratilipi.contacts.Presenter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.pratilipi.contacts.View.DetailView;

import java.io.ByteArrayInputStream;

public class DetailActivityPresenter {

    private Context context;
    private DetailView detailView;

    public DetailActivityPresenter(Context context, DetailView detailView){
        this.context = context;
        this.detailView = detailView;
    }


    public void getContactImage(long contactId) {
        Bitmap bitmap = null;
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            bitmap = null;
            return;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    bitmap =  BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }

        detailView.onImageBitmapFetched(bitmap);

    }

    public void getContactEmail(String id) {
        String email = null;
        Cursor cur1 = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);
        while (cur1.moveToNext()) {
            //to get the contact names
            email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        }
        cur1.close();
        detailView.onContactEmailFetched(email);
    }

    public void getContactAddress(String id) {
        String city = null;
        String country = null;
        Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        Cursor postal_cursor  = context.getContentResolver().query(postal_uri,null,  ContactsContract.Data.CONTACT_ID + "="+id, null,null);
        while(postal_cursor.moveToNext())
        {
            city = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            country = postal_cursor.getString(postal_cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
        }
        postal_cursor.close();
        if(city!=null && country!=null)
            detailView.onContactAddressFetched(city+", "+country);
        if(city==null && country == null){
            detailView.onContactAddressFetched(null);
        }
        if(city==null && country!=null){
            detailView.onContactAddressFetched(country);
        }
        if(city!=null &&  country==null){
            detailView.onContactAddressFetched(city);
        }
    }
}
