package com.pratilipi.contacts.Model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.IOException;
import java.io.InputStream;

public class DetailsHelper {

    private Context context;
    private ImageDetails imageDetails;

    public DetailsHelper(Context context, ImageDetails imageDetails){
        this.context = context;
        this.imageDetails = imageDetails;
    }

    public void getContactImage(long contactId){
        Bitmap bitmap = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId));

            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

            assert inputStream != null;
            if(inputStream!=null)
                inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        imageDetails.onImageBitmapFetched(bitmap);
    }

    public void getContactEmail(String id){
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
        imageDetails.onContactEmailFetched(email);
    }

    public void getContactAddress(String id){
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
        imageDetails.onContactAddressFetched(city, country);
    }

    public interface ImageDetails{
        void onImageBitmapFetched(Bitmap bitmap);
        void onContactEmailFetched(String email);
        void onContactAddressFetched(String city, String country);
    }
}
