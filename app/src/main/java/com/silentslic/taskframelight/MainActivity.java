package com.silentslic.taskframelight;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import layout.StuffWidget;

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

    private SharedPreferences sharedPref;

    // Specifies if there are any unsaved changes in the text fields
    boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(null);

        loadTasks();
    }

    @Override
    public void onBackPressed() {
        // If everything is saved/unchanged - just quit
        if (checkTextFieldsForChanges())
            MainActivity.this.finish();
        else {
            // If changes were made - ask to save them
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Save changes?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            saveTasks();
                            updateWidget();
                            MainActivity.this.finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    });

            builder1.setNeutralButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }
            );

            AlertDialog alert11 = builder1.create();
            alert11.show();
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
            case R.id.action_undo_changes:
                //Load last saved tasks

                loadTasks();
                return true;
            case R.id.action_save_changes:
                saveTasks();
                return true;
            default:
                // Invoke the superclass to handle unrecognized action.
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTasks() {
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
            editor.putString(String.valueOf(i), ((EditText) findViewById(EDITTEXT_IDS[i])).getText().toString());
        }
        editor.clear().apply();

        updateWidget();

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    private void loadTasks() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        EditText et;
        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
            et = (EditText) findViewById(EDITTEXT_IDS[i]);
            et.setText(sharedPref.getString(String.valueOf(i), ""));
        }
    }

    private void updateWidget() {
        Intent intent = new Intent(this, StuffWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), StuffWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

    // Test
    public void clearRow() {
        EditText toClear = (EditText)findViewById(R.id.appwidget_edittext1);// view.getRootView().getTag()
        toClear.setText("");
    }

    public void addRow(View view) {
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }

    public void rowActions(View view) {
        /* TODO custom AlertDialog with next options:
                delete row
                add due date
                change background
        */
        Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
    }

    private boolean checkTextFieldsForChanges() {
        // Test

        EditText et = (EditText)findViewById(R.id.appwidget_edittext1);
        return (et.getText().toString().equals(sharedPref.getString(String.valueOf(0), "")));
    }
}
