package com.pratilipi.contacts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.R;
import com.pratilipi.contacts.View.MainView;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private Context context;
    private ArrayList<Contacts> contactsList;
    private MainView mainView;

    int[] drawable = {R.drawable.circular_text_blue, R.drawable.circular_text_green,
    R.drawable.circular_text_pink, R.drawable.circular_text_red};

    public ContactsAdapter(ArrayList<Contacts> contactsList, MainView mainView) {
        this.contactsList = contactsList;
        this.mainView = mainView;
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
        holder.indicatorTextView.setText(name.substring(0,1));
        final int sdk = android.os.Build.VERSION.SDK_INT;
        int pos;
        if(position>3){
            pos = position%4;
        } else pos = position;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.indicatorTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable[pos]) );
        } else {
            holder.indicatorTextView.setBackground(ContextCompat.getDrawable(context, drawable[pos]));
        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setDataset(ArrayList<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public TextView indicatorTextView;
        public ConstraintLayout contactsLayout;

        public ContactsViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.name_text_view);
            indicatorTextView = v.findViewById(R.id.indicator_text_view);
            contactsLayout = v.findViewById(R.id.contacts_layout);
            contactsLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mainView.onContactsItemClicked(contactsList.get(getAdapterPosition()));
        }
    }
}
