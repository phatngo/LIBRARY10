package com.example.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library10.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tab4ListViewAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    List objects;
    public Tab4ListViewAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);
        TextView tab4_txtEmail=row.findViewById(R.id.tab4_txtEmail);
        TextView tab4_txtBookName=row.findViewById(R.id.tab4_txtBookName);
        TextView tab4_txtBorrowDate=row.findViewById(R.id.tab4_txtBorrowDate);
        Book_User_Borrow book_user_borrow= (Book_User_Borrow) this.objects.get(position);
        tab4_txtEmail.setText(book_user_borrow.getEmail());
        tab4_txtBorrowDate.setText(book_user_borrow.getDate());
        tab4_txtBookName.setText(book_user_borrow.getBookName());
        return row;
    }


}
