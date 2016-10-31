package com.silentslic.stufftodo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment class for implementation of multiple task pages
 */

public class ScreenSlidePageFragment extends Fragment {

    private static final String TAG = ScreenSlidePageFragment.class.getSimpleName();

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
        try {
            //load();
        }
        catch (Exception ex) {

        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        save();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tasks_page, container, false);

        this.root = rootView;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        try {
            EditText et;
            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
                et = (EditText) rootView.findViewById(EDITTEXT_IDS[i]);
                //et.setText(sharedPref.getString(String.valueOf(i), ""));
                et.setText(sharedPref.getString(TAG + i, ""));
            }
        }
        catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

        return rootView;
    }

    private void load() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        try {
            EditText et;
            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
                et = (EditText) this.root.findViewById(EDITTEXT_IDS[i]);
                //et.setText(sharedPref.getString(String.valueOf(i), ""));
                et.setText(sharedPref.getString(TAG + i, ""));
            }
        }
        catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void save() {
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
            editor.putString(TAG + i, ((EditText) this.root.findViewById(EDITTEXT_IDS[i])).getText().toString());
        }
        editor.clear().apply();
    }


//    private void load() {
//        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
//
//        try {
//
//            JSONObject json = new JSONObject(sharedPref.getString(TAG, "wut"));
//
////            EditText et;
////            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
////                et = (EditText) getView().findViewById(EDITTEXT_IDS[i]);
////                //et.setText(sharedPref.getString(String.valueOf(i), ""));
////                et.setText(json.getString(String.valueOf(i)));
////            }
//        }
//        catch (Exception ex) {
//            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }
//
//    private void save() {
//        JSONObject json = new JSONObject();
//
//        try {
//            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
//                json.put(String.valueOf(i), ((EditText) getView().findViewById(EDITTEXT_IDS[i])).getText());
//            }
//        }
//        catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//
//        if (json.length() > 0) {
//            sharedPref.edit().putString(TAG, json.toString()).apply();
//        }
//    }
}
