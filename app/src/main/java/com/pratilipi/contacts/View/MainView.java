package com.pratilipi.contacts.View;

import com.pratilipi.contacts.Model.Contacts;

import java.util.ArrayList;

public interface MainView {

    void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count);

    void onContactsItemClicked(int id);
}
