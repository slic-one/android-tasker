package com.silentslic.stufftodo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

    private static int NUM_PAGES = 1;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This opens alert to ask for saving changes.

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Save changes?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            saveTasks();
//                            updateWidget();

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

        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private static int counter = 0;

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

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("cont", this.toString());

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //Log.i("pref_load", String.valueOf(sharedPref.getInt("pagesCount", 999)));

        String value = sharedPref.getString("pagesCount", "1");

        NUM_PAGES = Integer.parseInt(value);
        mPagerAdapter.notifyDataSetChanged();

        Log.i("NUM_PAGES create", String.valueOf(NUM_PAGES));

        counter = 0;

        for (int i = NUM_PAGES; i > 0; i--) {
            addPage(i);
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
            case R.id.action_new_page:
                NUM_PAGES++;
                mPagerAdapter.notifyDataSetChanged();
                addPage(NUM_PAGES);
                return true;
            case R.id.action_undo_changes:
                //Load last saved tasks

                //loadTasks();
                return true;
            default:
                // Invoke the superclass to handle unrecognized action.
                return super.onOptionsItemSelected(item);
        }
    }

    private void addPage(int pageNumber) {
        Log.i("page", "addPage");

        //getSupportFragmentManager().beginTransaction().add(ScreenSlidePageFragment.newInstance("page" + counter++), String.valueOf(pageNumber)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.linear, new ScreenSlidePageFragment(), String.valueOf(pageNumber)).commit();


        Log.i("NUM_PAGES", String.valueOf(NUM_PAGES));
        sharedPref.edit().putString("pagesCount", String.valueOf(NUM_PAGES)).clear().apply();
        //Log.i("pref", String.valueOf(sharedPref.getInt("pagesCount", 999)));
    }

//    private void saveTasks() {
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        for (int i = 0; i < EDITTEXT_IDS.length; i++) {
//            editor.putString(String.valueOf(i), ((EditText) findViewById(EDITTEXT_IDS[i])).getText().toString());
//        }
//        editor.clear().apply();
//
//        //updateWidget();
//
//        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//    }
//
//    private void loadTasks() {
//        try {
//            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//
//            EditText et;
//            for (int i = 0; i < EDITTEXT_IDS.length; i++) {
//                et = (EditText) findViewById(EDITTEXT_IDS[i]);
//                et.setText(sharedPref.getString(String.valueOf(i), ""));
//            }
//        }
//        catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }

//    private void updateWidget() {
//        Intent intent = new Intent(this, StuffWidget.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), StuffWidget.class));
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
//        sendBroadcast(intent);
//    }

}
