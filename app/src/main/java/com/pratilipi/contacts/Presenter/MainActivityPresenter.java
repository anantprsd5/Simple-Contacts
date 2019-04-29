package com.pratilipi.contacts.Presenter;

import android.content.Context;

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

    public void getContacts(int position, boolean isChanged){
        contactsHelper.getContacts(position, isChanged);
    }

    public void checkIfNewContactAdded(){
        contactsHelper.checkIfNewContactAdded();
    }

    @Override
    public void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count) {
        mainView.onContactsFetched(contactsArrayList, count);
    }

    @Override
    public void ifContactsDataChanged(String message) {
        mainView.onContactsCountChanged(message);
    }


}
