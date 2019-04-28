package com.pratilipi.contacts.Activity;

import android.os.Bundle;
import android.util.Log;

import com.pratilipi.contacts.Adapter.ContactsAdapter;
import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.Presenter.MainActivityPresenter;
import com.pratilipi.contacts.R;
import com.pratilipi.contacts.View.MainView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private ArrayList<String> contacts = new ArrayList<>();
    private MainActivityPresenter mainActivityPresenter;
    private ContactsAdapter contactsAdapter;

    @BindView(R.id.contact_recycler_view)
    RecyclerView recyclerView;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainActivityPresenter = new MainActivityPresenter(this, this);
        mainActivityPresenter.getContacts(0);

        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count) {
        this.count = count;
        if(contactsAdapter==null) {
            contactsAdapter = new ContactsAdapter(contactsArrayList);
            recyclerView.setAdapter(contactsAdapter);
        }
        else {
            contactsAdapter.setDataset(contactsArrayList);
            contactsAdapter.notifyDataSetChanged();
        }
        if(count%100==0){
            Log.wtf("mainCount", ",,"+count);
            mainActivityPresenter.getContacts(count);
        }
    }


}
