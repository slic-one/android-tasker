package com.silentslic.stufftodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

    //TODO: find out wtf is wrong, prefs won't load as they don't exist
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sharedPref = this.getSharedPreferences("tasks", Context.MODE_PRIVATE);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        Toast.makeText(getApplicationContext(), (sharedPref.getString(findViewById(R.id.appwidget_edittext1).toString(), "nope")), Toast.LENGTH_LONG).show();
        EditText et;
        for (int id : EDITTEXT_IDS) {
            et = (EditText) findViewById(id);
            et.setText(sharedPref.getString(findViewById(id).toString(), ""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save: // User chose the "Save File" action, save the file...
                saveTasksToFile();
                return true;
            default: // Invoke the superclass to handle unrecognized action.
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTasksToFile() {
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int id : EDITTEXT_IDS) {
            editor.putString(findViewById(id).toString(), ((EditText) findViewById(id)).getText().toString());
        }
        editor.apply();
    }
}
