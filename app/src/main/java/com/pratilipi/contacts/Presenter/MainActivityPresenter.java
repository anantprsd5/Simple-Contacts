package com.pratilipi.contacts.Presenter;

import android.content.Context;
import android.util.Log;

import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.Model.ContactsHelper;
import com.pratilipi.contacts.View.MainView;

import java.util.ArrayList;

public class MainActivityPresenter implements ContactsHelper.contactsFetched {

    private Context context;
    private ContactsHelper contactsHelper;

    private MainView mainView;

    public MainActivityPresenter(Context context, MainView mainView){
        this.context = context;
        contactsHelper = new ContactsHelper(context, this);
        this.mainView = mainView;
    }

    public void getContacts(int position){
        contactsHelper.getContacts(position);
    }

    @Override
    public void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count) {
        Log.wtf("abcdef", ","+contactsArrayList.size());
        mainView.onContactsFetched(contactsArrayList, count);
    }
}
