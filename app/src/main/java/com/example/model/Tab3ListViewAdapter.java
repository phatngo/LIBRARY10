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

import java.util.ArrayList;
import java.util.List;

public class Tab3ListViewAdapter extends ArrayAdapter<Book> {
    Activity context;
    int resource;
    List<Book> objects;
    public Tab3ListViewAdapter(@NonNull Activity context, int resource, @NonNull List<Book> objects) {
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

        TextView tab3_txtISBN=row.findViewById(R.id.tab3_txtISBN);
        TextView tab3_txtName=row.findViewById(R.id.tab3_txtName);
        TextView tab3_txtAuthor=row.findViewById(R.id.tab3_txtAuthor);
        TextView tab3_txtGenre=row.findViewById(R.id.tab3_txtGenre);
        TextView tab3_txtQuantity=row.findViewById(R.id.tab3_txtQuantity);

        Book book=this.objects.get(position);
        tab3_txtISBN.setText(book.getISBN());
        tab3_txtName.setText(book.getBookName());
        tab3_txtAuthor.setText(book.getAuthor());
        tab3_txtGenre.setText(book.getGenre());
        if(book.getQuantity()==0){
            tab3_txtQuantity.setText("Out of order");
        }else{
            tab3_txtQuantity.setText(String.valueOf(book.getQuantity()));
        }
        return row;
    }
}
