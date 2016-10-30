package com.silentslic.stufftodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public class MainActivity extends FragmentActivity {


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
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }




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



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);



        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

//        EditText et;
//        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
//            et = (EditText) findViewById(EDITTEXT_IDS[i]);
//            et.setText(sharedPref.getString(String.valueOf(i), "<loading_error>"));
//        }
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

        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
            editor.putString(String.valueOf(i), ((EditText) findViewById(EDITTEXT_IDS[i])).getText().toString());
        }
        editor.clear().apply();

        //debug toast
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
    }

    private void refreshWidget() {

    }
}
