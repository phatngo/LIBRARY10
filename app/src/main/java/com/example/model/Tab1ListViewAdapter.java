package com.example.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library10.R;

import java.util.ArrayList;
import java.util.List;

public class Tab1ListViewAdapter extends ArrayAdapter<Book> {
    Activity context;
    int resource;
    List <Book> objects;
    public Tab1ListViewAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);

        TextView tab1_tab2_txtISBN = row.findViewById(R.id.tab1_tab2_txtISBN);
        TextView tab1_tab2_txtName = row.findViewById(R.id.tab1_tab2_txtName);
        TextView tab1_tab2_txtAuthor = row.findViewById(R.id.tab1_tab2_txtAuthor);
        TextView tab1_tab2_txtGenre = row.findViewById(R.id.tab1_tab2_txtGenre);
        ImageButton tab1_tab2_btnDelete=row.findViewById(R.id.tab1_tab2_btnDelete);

        Book book=objects.get(position);
        tab1_tab2_txtISBN.setText(String.valueOf(book.getISBN()));
        tab1_tab2_txtName.setText(book.getBookName());
        tab1_tab2_txtAuthor.setText(book.getAuthor());
        tab1_tab2_txtGenre.setText(book.getGenre());

        tab1_tab2_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return row;
    }

    private void remove(int position) {
        objects.remove(position);
        this.notifyDataSetChanged();
    }
}
