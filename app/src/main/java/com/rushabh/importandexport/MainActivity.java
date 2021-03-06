package com.rushabh.importandexport;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rushabh.importandexport.activities.AboutUsActivity;
import com.rushabh.importandexport.fragments.HelpUsFragment;
import com.rushabh.importandexport.fragments.MainFragment;
import com.rushabh.importandexport.fragments.WebViewFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AdView adView;
    ImageView shareImage;
    LinearLayout MainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareImage = (ImageView) findViewById(R.id.shareImage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        toolbar.setTranslationY(-(500));
        toolbar.animate()
                .translationY(0)
                .setDuration(500)
                .setStartDelay(300);

        shareImage.setTranslationY(-(700));
        shareImage.animate()
                .translationY(0)
                .setDuration(500)
                .setStartDelay(700);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ADs
        adView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                MainLayout = (LinearLayout) findViewById(R.id.MainLayout);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(0, 0, 0, adView.getHeight());
                MainLayout.setLayoutParams(layoutParams );
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainLayout, new MainFragment())
                .commit();
    }

    public void setActionBarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.homepage){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.MainLayout,new MainFragment())
                    .commit();
        }
        else if (id == R.id.currancyconverter) {
            WebViewFragment webViewFragment = new WebViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title","Currancy converter");
            bundle.putString("url", "https://www.google.com/finance/converter?a=1&from=COP&to=BBD&meta=ei%3DfLxTWJjvAs2nuATxtK-QAw");
            webViewFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.MainLayout, webViewFragment)
                    .addToBackStack(null)
                    .commit();
        }else if (id == R.id.helpus){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out,R.anim.trans_right_in, R.anim.trans_right_out)
                    .replace(R.id.MainLayout,new HelpUsFragment())
                    .addToBackStack(null)
                    .commit();
        }else if (id == R.id.rateapp) {

        } else if (id == R.id.feedback) {
            sendFeedback();
        } else if (id == R.id.aboutus) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendFeedback() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "contacticodedevelopers@gmail.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        Email.putExtra(Intent.EXTRA_TEXT, " ");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }
}
