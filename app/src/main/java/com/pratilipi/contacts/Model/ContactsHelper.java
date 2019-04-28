package com.pratilipi.contacts.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ContactsHelper {

    private Context context;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    private contactsFetched contactsFetched;

    public ContactsHelper(Context context, contactsFetched contactsFetched) {
        this.context = context;
        this.contactsFetched = contactsFetched;
    }

    public void getContacts(int position) {
        new ContactLoader().execute(position);
    }

    private class ContactLoader extends AsyncTask<Integer, Void, Void>
    {

        private int count;
        int position;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            count = 0;
        }
        @Override
        protected Void doInBackground(Integer... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME +  " ASC");
            position = params[0];

            if (cursor.getCount() > 0) {
                cursor.moveToPosition(position-1);
                while (cursor.moveToNext() && count<100) {
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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            contactsFetched.onContactsFetched(contactsArrayList, position+ count);
        }

    }

    public interface contactsFetched {
        void onContactsFetched(ArrayList<Contacts> contactsArrayList, int position);
    }

}
