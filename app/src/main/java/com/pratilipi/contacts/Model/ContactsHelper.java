package com.pratilipi.contacts.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

public class ContactsHelper {

    private Context context;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    private contactsFetched contactsFetched;

    public static int count;

    public ContactsHelper(Context context, contactsFetched contactsFetched) {
        this.context = context;
        this.contactsFetched = contactsFetched;
    }

    public void getContacts() {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME +  " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext() && count<=10) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (cursorInfo.moveToNext()) {
                        Contacts contacts = new Contacts();
                        contacts.setId(id);
                        contacts.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                        contacts.setNumber(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        contactsArrayList.add(contacts);
                        break;
                    }

                    cursorInfo.close();
                }
                count++;
            }
            cursor.close();
        }
        contactsFetched.onContactsFetched(contactsArrayList);
    }

    public interface contactsFetched {
        void onContactsFetched(ArrayList<Contacts> contactsArrayList);
    }

}
