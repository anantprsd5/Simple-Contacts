package com.pratilipi.contacts.Activity;

import android.os.Bundle;

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
            initScrollListener();
        }
        else {
            contactsAdapter.setDataset(contactsArrayList);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    public void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount-50 && firstVisibleItemPosition >= 0) {
                        //bottom of list!
                        if(count%100==0){
                            mainActivityPresenter.getContacts(count);
                        }
                    }
            }
        });
    }
}
