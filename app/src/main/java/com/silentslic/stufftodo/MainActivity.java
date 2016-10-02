package com.silentslic.stufftodo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import layout.StuffWidget;

public class MainActivity extends AppCompatActivity {


    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { return new ScreenSlidePageFragment(); }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(EDITTEXT_IDS[0]);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        try {
            Toast.makeText(getApplicationContext(), mPager.findViewById(R.id.appwidget_edittext1).toString(), Toast.LENGTH_LONG).show();
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //loadTasks();
    }

//    @Override
//    protected void onDestroy() {
//        //save before exiting
//        saveTasks();
//        super.onDestroy();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_page:

                return true;
            case R.id.action_display_on_widget:
                //force widget update

                saveTasks();
                return true;
            case R.id.action_undo_changes:
                //Load last saved tasks

                loadTasks();
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
        try {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

            EditText et;
            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
                et = (EditText) findViewById(EDITTEXT_IDS[i]);
                et.setText(sharedPref.getString(String.valueOf(i), ""));
            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateWidget() {
        Intent intent = new Intent(this, StuffWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), StuffWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

}
