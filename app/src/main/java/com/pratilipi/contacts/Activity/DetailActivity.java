package com.pratilipi.contacts.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pratilipi.contacts.Model.Contacts;
import com.pratilipi.contacts.Presenter.DetailActivityPresenter;
import com.pratilipi.contacts.R;
import com.pratilipi.contacts.View.DetailView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    int[] drawable = {R.drawable.circular_text_blue, R.drawable.circular_text_green,
            R.drawable.circular_text_pink, R.drawable.circular_text_red};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contact_number)
    TextView contactNumber;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.contact_email)
    TextView contactEmail;
    @BindView(R.id.contact_location)
    TextView contactLocation;
    @BindView(R.id.contact_image)
    ImageView contactImage;
    @BindView(R.id.indicator_text_view)
    TextView indicatorTextView;

    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        contact = (Contacts)getIntent().getSerializableExtra("contactsDetails");
        contactName.setText(contact.getName());
        contactNumber.setText(contact.getNumber());

        DetailActivityPresenter detailActivityPresenter = new DetailActivityPresenter(this, this);
        detailActivityPresenter.getContactImage(Long.parseLong(contact.getId()));
        detailActivityPresenter.getContactEmail(contact.getId());
        detailActivityPresenter.getContactAddress(contact.getId());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onImageBitmapFetched(Bitmap bitmap) {
        if(bitmap!=null){
            contactImage.setVisibility(View.VISIBLE);
            indicatorTextView.setVisibility(View.GONE);
            contactImage.setImageBitmap(bitmap);
        } else {
            contactImage.setVisibility(View.GONE);
            indicatorTextView.setVisibility(View.VISIBLE);
            indicatorTextView.setText(contact.getName().substring(0,1));
            Random rand = new Random();

            // Obtain a number between [0 - 3].
            int n = rand.nextInt(3);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
               indicatorTextView.setBackgroundDrawable(ContextCompat.getDrawable(this, drawable[n]) );
            } else {
                indicatorTextView.setBackground(ContextCompat.getDrawable(this, drawable[n]));
            }
        }
    }

    @Override
    public void onContactEmailFetched(String email) {
        if(email!=null){
            contactEmail.setText(email);
        } else {
            contactEmail.setText(getString(R.string.no_email));
        }
    }

    @Override
    public void onContactAddressFetched(String address) {
        if(address!=null){
            contactLocation.setText(address);
        } else {
            contactLocation.setText(getString(R.string.no_address));
        }
    }
}
