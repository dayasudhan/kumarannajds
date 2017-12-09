
package com.kuruvatech.kumarannajds;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kuruvatech.kumarannajds.fragment.AachivementsFragment;
import com.kuruvatech.kumarannajds.fragment.AboutFragment;
import com.kuruvatech.kumarannajds.fragment.CmFragment;
import com.kuruvatech.kumarannajds.fragment.ImageFragment;
import com.kuruvatech.kumarannajds.fragment.JanathadarshanaFragment;
import com.kuruvatech.kumarannajds.fragment.JdsManifestoFragment;
import com.kuruvatech.kumarannajds.fragment.MainFragment;
import com.kuruvatech.kumarannajds.fragment.ShareAppFragment;
import com.kuruvatech.kumarannajds.fragment.SuggetionFragment;
import com.kuruvatech.kumarannajds.fragment.VideoFragment;
import com.kuruvatech.kumarannajds.fragment.Settingfragment;
import com.kuruvatech.kumarannajds.utils.Constants;
import com.kuruvatech.kumarannajds.utils.SessionManager;
import com.splunk.mint.Mint;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private RelativeLayout layout;
    private DrawerLayout dLayout;
    SessionManager session;
    RelativeLayout navHead;
    TextView name,email,phno;
    private boolean isMainFragmentOpen;
    private boolean isdrawerbackpressed;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean fromUser=true;
    Toolbar tb;
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

        setContentView(R.layout.activity_main_new);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.USERNAME);
        String token = FirebaseInstanceId.getInstance().getToken();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        setNavigationDrawer();
        setToolBar();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
//
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
//         if (!checkNotificationListenerServiceRunning()) {
//            Toast.makeText(getApplicationContext(),"hi update 1",Toast.LENGTH_LONG).show();
//            startService(new Intent(this, NotificationListener.class));
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"hi update 2",Toast.LENGTH_LONG).show();
//        }
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
//        String notification=getIntent().getStringExtra("notificationFragment");
//        if (notification!=null &&notification.equals("fcm")) {
//            try {
////                MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame);
////                fragment.getFeeds();
//            } catch (ClassCastException e){
//            }
//        }
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
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu_selector);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    Switch switchLanguage;

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

//    public void setLocale(String lang) { //call this in onCreate()
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//
//    }

    private ProgressDialog mProgressDialog;



    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        navView.setItemIconTintList(null);
        View hView =  navView.inflateHeaderView(R.layout.header);
        navHead = (RelativeLayout) hView.findViewById(R.id.profileinfo);
//        name = (TextView) hView.findViewById(R.id.myNameHeader);
//        phno = (TextView) hView.findViewById(R.id.phNoHeader);
//        email = (TextView)hView.findViewById(R.id.eMailHeader);
     //     switchLanguage = (Switch) hView.findViewById(R.id.language);
//   //     name.setText(session.getName());
//        phno.setText(session.getKeyPhone());
//        email.setText(session.getEmail());
//        transaction.replace(R.id.frame, new AboutFragment());
        isMainFragmentOpen =  true;
        transaction.commit();
        fromUser=false;
//        switchLanguage.setChecked(!isEnglish);
//        switchLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//
//                    ((App) getApplication()).setLocale(new Locale("kn"));
//                    isEnglish=false;
//                } else {
//                    isEnglish=true;
//                    ((App) getApplication()).setLocale(new Locale("en"));
//
//                }
//                refreshUI();
//            }
//        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment frag = null;

                int itemId = menuItem.getItemId();
                if (itemId == R.id.main) {
//                    tb.setBackground(getResources().getDrawable(R.color.jds_toolbar));
//                    tabLayout.setBackground(getResources().getDrawable(R.color.jds_background));
                    viewPager.setCurrentItem(0);
                    isMainFragmentOpen =  true;
                }else if (itemId == R.id.invite) {
                    viewPager.setCurrentItem(9);
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.videos)
                {

                    viewPager.setCurrentItem(7);
//                    startActivity(new Intent(getApplicationContext(),CustomPlayerControlActivity.class));
                  //  frag = new VideoFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.images)
                {
//                    tb.setBackground(getResources().getDrawable(R.color.congress_toolbar));
//                    tabLayout.setBackground(getResources().getDrawable(R.color.colorAccent));
                    viewPager.setCurrentItem(6);
                  //  frag = new ImageFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.about_candiate)
                {
                    viewPager.setCurrentItem(1);
                    //frag = new AboutFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.as_a_position)
                {
                    viewPager.setCurrentItem(2);
//                    frag = new CmFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.achievements)
                {
                    viewPager.setCurrentItem(3);
                  //  frag = new AachivementsFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.janatadarshan)
                {
                    viewPager.setCurrentItem(4);
                //    frag = new JanathadarshanaFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.manifesto)
                {
                    viewPager.setCurrentItem(5);
                 ///   frag = new JdsManifestoFragment();
                    isMainFragmentOpen =  false;
                }
                else if(itemId == R.id.settings2)
                {
                  viewPager.setCurrentItem(10);
                   //    frag = (Fragment) new GeneralPreferenceFragment();
                   isMainFragmentOpen =  false;
                    //startActivity(new Intent(MainActivity.this, SettingsPrefActivity.class));
                 //  Intent a = new Intent(getApplicationContext(), SettingsActivity.class);
                //    //a.putExtra("url", "https://s3.ap-south-1.amazonaws.com/chunavane/hdk/images.jpg");
                 //   startActivity(a);

                }
                else if(itemId == R.id.suggestion_box)
                {
                    viewPager.setCurrentItem(8);
                    ///   frag = new JdsManifestoFragment();
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
//                if (frag != null) {
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame, frag);
//                    transaction.commit();
//
//                    return true;
//                }
                dLayout.closeDrawers();
                return true;
            }
        });
    }

    private static boolean isEnglish = true;

    private void refreshUI() {
        mProgressDialog.show();
        recreate();
        mProgressDialog.hide();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), getString(R.string.home));
        adapter.addFragment(new AboutFragment(), getString(R.string.about_person));
        adapter.addFragment(new CmFragment(), getString(R.string.as_chief));
        adapter.addFragment(new AachivementsFragment(), getString(R.string.achivements));
        adapter.addFragment(new JanathadarshanaFragment(), getString(R.string.janatadarshan));
        adapter.addFragment(new JdsManifestoFragment(), getString(R.string.manifesto));
        adapter.addFragment(new ImageFragment(), getString(R.string.image_gallery));
        adapter.addFragment(new VideoFragment(), getString(R.string.hdk_tv));
        adapter.addFragment(new SuggetionFragment(), getString(R.string.suggestion_box_tab));
        adapter.addFragment(new ShareAppFragment(), getString(R.string.share));
        adapter.addFragment(new Settingfragment(), getString(R.string.settings_tab));
        viewPager.setAdapter(adapter);
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
//            else {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame, new MainFragment());
//                isMainFragmentOpen = true;
//                transaction.commit();
//                isdrawerbackpressed = false;
//            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            if ("com.kuruvatech.kumarannajds.NotificationListener"
                    .equals(service.service.getClassName())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        if(tab.getPosition() == 0)
        {
          setTheme(R.color.jds_background,R.color.jds_toolbar,
                  Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.jds_background))));
        }
        else if(tab.getPosition() == 1)
        {
          //  getActionBar().setTitle();
            setTheme(R.color.Amber,R.color.amber_tool_bar_color,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.Amber))));
        }
        else if(tab.getPosition() == 2)
        {
            setTheme(R.color.grapefruit2,R.color.grapefruit1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.grapefruit2))));
        }
        else if(tab.getPosition() == 3)
        {
            setTheme(R.color.bluejeans2,R.color.bluejeans1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.bluejeans2))));
        }
        else if(tab.getPosition() == 4)
        {
            setTheme(R.color.pinkrose2,R.color.pinkrose1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.pinkrose2))));
        }
        else if(tab.getPosition() == 5)
        {
            setTheme(R.color.lavender2,R.color.lavender1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.lavender2))));
        }
        else if(tab.getPosition() == 6)
        {
            setTheme(R.color.sunflower2,R.color.sunflower1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.sunflower2))));
        }
        else if(tab.getPosition() ==7)
        {
            setTheme(R.color.mint2,R.color.mint1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.mint2))));
        }
        else if(tab.getPosition() ==8)
        {
            setTheme(R.color.bittersweet2,R.color.bittersweet1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.bittersweet2))));
        }
        else if(tab.getPosition() ==9)
        {
            setTheme(R.color.Cobalt,R.color.NavyBlue,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.Cobalt))));
        }
        else if(tab.getPosition() ==10)
        {
            setTheme(R.color.sunflower2,R.color.sunflower1,
                    Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.sunflower2))));
        }
        viewPager.setCurrentItem(tab.getPosition());
    }

    private void setTheme(int themecolor,int toolbacolor,int statusbarcolor)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            tb.setBackground(getResources().getDrawable(toolbacolor));
            tabLayout.setBackground(getResources().getDrawable(themecolor));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusbarcolor);
        }
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.about_candiate) {
//            viewPager.setCurrentItem(0);
//        } else if (id == R.id.achievements) {
//            viewPager.setCurrentItem(1);
//        } else if (id == R.id.as_a_position) {
//            viewPager.setCurrentItem(2);
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//
//    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
