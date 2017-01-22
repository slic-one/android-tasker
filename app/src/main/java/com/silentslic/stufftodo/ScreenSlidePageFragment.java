package com.silentslic.stufftodo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment class for implementation of multiple pages
 */

public class ScreenSlidePageFragment extends Fragment {

    private  View root;

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

//    @Override
//    public void onResume() {
//        try {
//            load();
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        save();
//        super.onDestroy();
//    }

//    public static ScreenSlidePageFragment newInstance(String tag) {
//
//        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
//
//        Bundle b = new Bundle();
//        b.putString("tag", tag);
//        fragment.setArguments(b);
//
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tasks_page, container, false);

        Log.i("Main cont", getContext().toString());

//        if (getArguments() != null)
//            rootView.setTag(getArguments().getString("tag"));
//        else
//            rootView.setTag("4130" + "+" + this.getTag());

        rootView.setTag("4130" + "+" + this.getTag());

//        this.root = rootView;

        try {
            EditText et;
            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
                et = (EditText) rootView.findViewById(EDITTEXT_IDS[i]);
                Log.i(String.valueOf(i) + " create", rootView.getTag() + String.valueOf(i));
                et.setText(rootView.getTag() + "+" + String.valueOf(i));
            }
        }
        catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

        return rootView;
    }

    private void load() {
        // for each array load page
        //     for each key load row
    }

    private void save() {

    }
}
