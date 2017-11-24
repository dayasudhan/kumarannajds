
package com.kuruvatech.kumarannajds;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
//import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuruvatech.kumarannajds.fragment.AboutFragment;
import com.kuruvatech.kumarannajds.fragment.ImageFragment;
import com.kuruvatech.kumarannajds.fragment.MainFragment;
import com.kuruvatech.kumarannajds.fragment.ShareAppFragment;
import com.kuruvatech.kumarannajds.fragment.VideoFragment;
import com.kuruvatech.kumarannajds.utils.SessionManager;
import com.splunk.mint.Mint;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.splunk.mint.Mint;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layout;
    private DrawerLayout dLayout;
    SessionManager session;
    RelativeLayout navHead;
    TextView name,email,phno;
    private boolean isMainFragmentOpen;
    private boolean isdrawerbackpressed;

    public boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this, "49d903c2");
        Mint.enableLogging(true);
        Mint.setLogging(100, "*:W");
        session = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNavigationDrawer();
        setToolBar();
        FirebaseMessaging.getInstance().subscribeToTopic("news");

//        if (!checkNotificationListenerServiceRunning()) {
//            Toast.makeText(getApplicationContext(),"hi update 1",Toast.LENGTH_LONG).show();
//            startService(new Intent(this, NotificationListener.class));
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"hi update 2",Toast.LENGTH_LONG).show();
//        }
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
        String notification=getIntent().getStringExtra("notificationFragment");
        if (notification!=null &&notification.equals("fcm")) {
            try {
                MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame);
                fragment.getFeeds();
            } catch (ClassCastException e){
            }
        }
        if (!isOnline(MainActivity.this))
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            }
            catch(Exception e)
            {

            }
        }
        isMainFragmentOpen = true;
        isdrawerbackpressed = false;


    }
    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu_selector);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        View hView =  navView.inflateHeaderView(R.layout.header);
        navHead = (RelativeLayout) hView.findViewById(R.id.profileinfo);
        name = (TextView) hView.findViewById(R.id.myNameHeader);
        phno = (TextView) hView.findViewById(R.id.phNoHeader);
        email = (TextView)hView.findViewById(R.id.eMailHeader);

//        name.setText(session.getName());
//        phno.setText(session.getKeyPhone());
//        email.setText(session.getEmail());
        transaction.replace(R.id.frame, new MainFragment());
        isMainFragmentOpen =  true;
        transaction.commit();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment frag = null;
                int itemId = menuItem.getItemId();
                if (itemId == R.id.main) {
                    frag = new MainFragment();
                    isMainFragmentOpen =  true;
                }else if (itemId == R.id.invite) {
                    frag = new ShareAppFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.videos)
                {
//                    startActivity(new Intent(getApplicationContext(),CustomPlayerControlActivity.class));
                    frag = new VideoFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.images)
                {
                    frag = new ImageFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.about_candiate)
                {
                    frag = new AboutFragment();
                    isMainFragmentOpen =  false;
                }
//                else if(itemId == R.id.videos3)
//                {
//                    startActivity(new Intent(getApplicationContext(),YouTubePlayerFragmentActivity.class));
//                }
//                else if(itemId == R.id.videos4)
//                {
//                    startActivity(new Intent(getApplicationContext(),YouTubePlayerAcivity.class));
//                }
                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    dLayout.closeDrawers();
                    return true;
                }

                return false;
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (dLayout.isDrawerOpen(GravityCompat.START)) {
            dLayout.closeDrawer(GravityCompat.START);
        } else if (isMainFragmentOpen == false) {
            if(!isdrawerbackpressed) {
                dLayout.openDrawer(GravityCompat.START);
                isdrawerbackpressed = true;
            }
            else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new MainFragment());
                isMainFragmentOpen = true;
                transaction.commit();
                isdrawerbackpressed = false;
            }
        } else {
            //super.onBackPressed();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        String btnName = null;

        switch(itemId) {

            case android.R.id.home: {
                dLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return false;
    }
    public boolean checkNotificationListenerServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.kuruvatech.election.NotificationListener"
                    .equals(service.service.getClassName())) {
                return true;
            }

        }
        return false;
    }
}
