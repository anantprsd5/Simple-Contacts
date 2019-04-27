package com.pratilipi.contacts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private Context context;
    private ArrayList<Contacts> contactsList;

    public ContactsAdapter(ArrayList<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.contacts_view, parent, false);
        return new ContactsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, final int position) {
        String name = contactsList.get(position).getName();
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;

        public ContactsViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.test_text);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
