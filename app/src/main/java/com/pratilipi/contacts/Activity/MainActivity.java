package com.pratilipi.contacts.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
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

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);

        mainActivityPresenter = new MainActivityPresenter(this, this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactPermission();
        } else {
            mainActivityPresenter.getContacts(0, isChanged);
        }

        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                Toast.makeText(this, "asd",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onContactsFetched(ArrayList<Contacts> contactsArrayList, int count) {
        if(contactsAdapter==null) {
            contactsAdapter = new ContactsAdapter(contactsArrayList, this);
            recyclerView.setAdapter(contactsAdapter);
            swipeRefreshLayout.setRefreshing(false);
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
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainActivityPresenter.getContacts(0, isChanged);

                } else {
                    Toast.makeText(this, "This app needs your read contacts permission",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactPermission();
        } else
        mainActivityPresenter.checkIfNewContactAdded();
    }

}
