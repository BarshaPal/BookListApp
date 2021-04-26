package com.example.booklistapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Books> {
    private JSONArray Picasso;

    public BookAdapter(Activity context, ArrayList<Books> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list, parent, false);
            Books currentword = getItem(position);


            TextView subtitle = (TextView) listItemView.findViewById(R.id.idTVSubTitle);
            subtitle.setText(currentword.getMs2());

            TextView title = (TextView) listItemView.findViewById(R.id.idTVTitle);
            title.setText(currentword.getMs1());
            TextView publish = (TextView) listItemView.findViewById(R.id.idTVPublishDate);
            publish.setText(currentword.getMs4());
            TextView publisher = (TextView) listItemView.findViewById(R.id.idTVpublisher);
            publisher.setText(currentword.getMs3());
            TextView page = (TextView) listItemView.findViewById(R.id.idTVNoOfPages);
            page.setText(currentword.getMs5());
            TextView rate = (TextView) listItemView.findViewById(R.id.rate);
            rate.setText(currentword.getRate());
        }
        return listItemView;

    }
}
