package com.example.dododo.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dododo.finalproject.data.Contract;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import static com.example.dododo.finalproject.gson.*;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragmentFragment extends Fragment  {

    public DetailFragmentFragment() {
    }



    ImageView imageview;
    TextView title;
    TextView overview;
    TextView rating;
    TextView release;
    Gson gson = new Gson();
    ResultsEntity gsonobject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();

        Bundle getIntentExtra = getActivity().getIntent().getExtras();
        if (getIntentExtra != null) {
            gsonobject = getIntentExtra.getParcelable("movieId");
        } else {
            Bundle getArgus = getArguments();
            gsonobject = getArgus.getParcelable("movieId");
        }
        int movieId = gsonobject.getId();

        title = (TextView) rootView.findViewById(R.id.title);
        title.setText(gsonobject.getTitle());
        overview = (TextView) rootView.findViewById(R.id.overview);
        overview.setText(gsonobject.getOverview());

        release = (TextView) rootView.findViewById(R.id.release_date);
        release.setText(gsonobject.getRelease_date());

        rating = (TextView) rootView.findViewById(R.id.rating);
        rating.setText(gsonobject.getVote_average() + "");

        imageview = (ImageView) rootView.findViewById(R.id.imageviewditals);
        String imageurl = gsonobject.getPoster_path();

        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w185" + imageurl).into(imageview);

        return rootView;
    }

    ResultsEntity rerivedetail(int id) {
        Uri uriFindByID = Contract.mostpopEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id)).build();
        Cursor mostpopCursor = getActivity().getContentResolver().query(
                uriFindByID,
                new String[]{Contract.mostpopEntry.COLUMN_titel,
                        Contract.mostpopEntry.COLUMN_image, Contract.mostpopEntry.COLUMN_overview, Contract.mostpopEntry.COLUMN_releasdate
                        , Contract.mostpopEntry.COLUMN_rated},
                Contract.mostpopEntry.ColumnMovieID + " = ?",
                new String[]{String.valueOf(id)},
                null);
        mostpopCursor.moveToFirst();
        ResultsEntity object = new ResultsEntity();
        object.setOriginal_title(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_titel)));
        object.setOverview(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_overview)));
        object.setPoster_path(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_image)));
        object.setRelease_date(mostpopCursor.getString(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_releasdate)));
        object.setVote_average(mostpopCursor.getDouble(mostpopCursor.getColumnIndex(Contract.mostpopEntry.COLUMN_rated)));

        return object;
    }


}
