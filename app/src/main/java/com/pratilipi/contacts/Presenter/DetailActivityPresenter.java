package com.pratilipi.contacts.Presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.pratilipi.contacts.Model.DetailsHelper;
import com.pratilipi.contacts.View.DetailView;

public class DetailActivityPresenter implements DetailsHelper.ImageDetails {

    private DetailView detailView;
    private DetailsHelper detailsHelper;

    public DetailActivityPresenter(Context context, DetailView detailView){
        this.detailView = detailView;
        detailsHelper = new DetailsHelper(context, this);
    }

    public void getContactImage(long contactId) {
        detailsHelper.getContactImage(contactId);
    }

    public void getContactEmail(String id) {
        detailsHelper.getContactEmail(id);
    }

    public void getContactAddress(String id) {
       detailsHelper.getContactAddress(id);
    }

    @Override
    public void onImageBitmapFetched(Bitmap bitmap) {
        detailView.onImageBitmapFetched(bitmap);
    }

    @Override
    public void onContactEmailFetched(String email) {
        detailView.onContactEmailFetched(email);
    }

    @Override
    public void onContactAddressFetched(String city, String country) {
        if(city!=null && country!=null)
            detailView.onContactAddressFetched(city+", "+country);
        if(city==null && country == null){
            detailView.onContactAddressFetched(null);
        }
        if(city==null && country!=null){
            detailView.onContactAddressFetched(country);
        }
        if(city!=null &&  country==null){
            detailView.onContactAddressFetched(city);
        }
    }
}
