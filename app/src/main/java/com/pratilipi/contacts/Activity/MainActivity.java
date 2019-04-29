package com.pratilipi.contacts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.pratilipi.contacts.Adapter.ContactsAdapter;
import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.Presenter.MainActivityPresenter;
import com.pratilipi.contacts.R;
import com.pratilipi.contacts.View.MainView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private ArrayList<String> contacts = new ArrayList<>();
    private MainActivityPresenter mainActivityPresenter;
    private ContactsAdapter contactsAdapter;

    @BindView(R.id.contact_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Contacts");

        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);

        mainActivityPresenter = new MainActivityPresenter(this, this);
        mainActivityPresenter.getContacts(0, isChanged);

        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count) {
        if(contactsAdapter==null) {
            contactsAdapter = new ContactsAdapter(contactsArrayList, this);
            recyclerView.setAdapter(contactsAdapter);
        }
        else {
            contactsAdapter.setDataset(contactsArrayList);
            contactsAdapter.notifyDataSetChanged();
        }

        //Lazy loading of cursor data to improve performance
        if(count%100==0){
            mainActivityPresenter.getContacts(count, isChanged);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onContactsItemClicked(Contacts contacts) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("contactsDetails", contacts);
        startActivity(intent);
    }

    @Override
    public void onContactsCountChanged(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        isChanged = true;
        mainActivityPresenter.getContacts(0, isChanged);
        isChanged = false;
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityPresenter.checkIfNewContactAdded();
    }

}
