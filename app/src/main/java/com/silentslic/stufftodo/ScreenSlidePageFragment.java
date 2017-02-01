package com.silentslic.stufftodo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;

/**
 * Fragment class for implementation of multiple pages
 */

public class ScreenSlidePageFragment extends Fragment {

    private  View root;

    SharedPreferences sharedPref;

    private static final int[] EDITTEXT_IDS = {
            R.id.appwidget_edittext1,
            R.id.appwidget_edittext2,
            R.id.appwidget_edittext3,
            R.id.appwidget_edittext4,
            R.id.appwidget_edittext5,
            R.id.appwidget_edittext6,
            R.id.appwidget_edittext7,
            R.id.appwidget_edittext8,
            R.id.appwidget_edittext9,
            R.id.appwidget_edittext10,
    };

    @Override
    public void onResume() {
        super.onResume();
//        try {
//            load();
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }


        try {
            EditText et;
            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
                et = (EditText) root.findViewById(EDITTEXT_IDS[i]);
                Log.i(String.valueOf(i) + " create", this.getArguments().getString("tag", "none") + String.valueOf(i));
                et.setText(this.getArguments().getString("tag", "none") + "+" + String.valueOf(i));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        try {
//            save();
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    public static ScreenSlidePageFragment newInstance(String tag) {

        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        Bundle b = new Bundle();
        b.putString("tag", tag);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tasks_page, container, false);

//        if (getArguments() != null)
//            rootView.setTag(getArguments().getString("tag"));
//        else
//            rootView.setTag("defpage");

        this.root = rootView;

//        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

//        try {
//            EditText et;
//            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
//                et = (EditText) rootView.findViewById(EDITTEXT_IDS[i]);
//                Log.i(String.valueOf(i) + " create", this.getTag() + String.valueOf(i));
//                et.setText(this.getTag() + "+" + String.valueOf(i));
////                et.setText(sharedPref.getString(root.getTag() + String.valueOf(i), ""));
//            }
//        }
//        catch (Exception ex) {
//            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }

        return rootView;
    }

    private void load() throws IOException {
        // for each array load page
        //     for each key load row
        JsonReader reader = new JsonReader(new FileReader(new File(Environment.getExternalStorageDirectory(), "save.json")));
        reader.beginArray();
        for (int i = 0; i < 10; i++) { // lines
            reader.beginObject();
            Log.d(reader.nextName(), " ");
            ((EditText) this.root.findViewById(EDITTEXT_IDS[i])).setText(reader.nextString());
//            while (reader.hasNext()) {
//                Log.d(reader.nextName(), reader.nextString());
//            }
            reader.endObject();
        }
        reader.endArray();
        Log.d("LOAD", "LOADED");
    }

    private void save() throws IOException {
        JsonWriter writer = new JsonWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(), "save.json")));
        writer.beginArray();
        for (int i = 0; i < 10; i++) { // lines
            writer.beginObject();
            writer.name(TAG + i);
            writer.value(((EditText) this.root.findViewById(EDITTEXT_IDS[i])).getText().toString());
            writer.endObject();
        }
        writer.endArray();
        writer.close();

        Log.d("SAVE", "SAVED");
    }
}
