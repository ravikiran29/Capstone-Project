package com.example.ravikiranpathade.newstrends.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ravikiranpathade.newstrends.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapters.NewsCursorAdapter;
import adapters.NewsRecyclerAdapter;
import data.NewsContract;
import models.Articles;
import models.Source;

public class AlertedNewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, NewsCursorAdapter.checkEmpty {

    ListView listView;
    NewsCursorAdapter adapter;
    TextView text;

    NavigationView navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportLoaderManager().initLoader(1, null, this);
        setContentView(R.layout.activity_alerted_news);
        Toolbar t = findViewById(R.id.alertsBar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("News Alerts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawerLayoutAlert);
        navigation = findViewById(R.id.navigationViewAlert);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        adapter = new NewsCursorAdapter(this, null, "CHECK", this);
        listView = findViewById(R.id.alertsRecyclerView);
        text = findViewById(R.id.noAlertTextView);
        listView.setAdapter(adapter);

        setupDrawer(navigation);

    }

    private void setupDrawer(NavigationView navigation) {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectNavigationDrawerItem(item);
                return true;
            }
        });
    }
    private void selectNavigationDrawerItem(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.home:

                Intent intentMain = new Intent(this,MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.favorites:
                Intent intentFav = new Intent(this,FavoritesActivity.class);
                startActivity(intentFav);
                break;
            case R.id.alerts:
                Intent intentAlert = new Intent(this, AlertedNewsActivity.class);
                startActivity(intentAlert);
                break;
            case R.id.addKeywords:
                Intent intentAdd = new Intent(this, AddKeywordActivity.class);
                startActivity(intentAdd);
                break;
            default:

        }
        drawerLayout.closeDrawers();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, NewsContract.NewsAlertsEntry.FINAL_URI,
                null,
                null,
                null,
                "DATE DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Cursor cursor1 = getContentResolver().query(NewsContract.NewsAlertsEntry.FINAL_URI,
                null,
                null,
                null,
                "DATE DESC");
        adapter.swapCursor(cursor1);
        if (cursor1.getCount() == 0) {
            text.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapter.swapCursor(null);
    }

    @Override
    public void onCheckEmpty(boolean checkBoolean) {
        Log.d("Checks Listener", "Working");
        if (checkBoolean == true) {
            text.setVisibility(View.VISIBLE);
        }
    }
}
