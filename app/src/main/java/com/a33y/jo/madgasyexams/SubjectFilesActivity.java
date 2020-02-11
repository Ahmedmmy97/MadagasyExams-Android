package com.a33y.jo.madgasyexams;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SubjectFilesActivity extends AppCompatActivity {
    Toolbar toolbar ;
    NavigationView nav;
    ActionBarDrawerToggle drawerbutton;
    DrawerLayout drawer;
    ViewPager viewPager;
    TabLayout tabs;
    android.support.v4.app.Fragment[] fragments;
    sujetFrag frag1 = new sujetFrag();
    corrigesFrag frag2= new corrigesFrag();
    coursFrag frag3= new coursFrag();
    TabAdapter adapter;
    TextView Title;
    TabItem courstab;
    AlertDialog.Builder alert;
    AdView adView;
    String message = "GUINEEXAMS est une application conçue pour accompagner  les candidats Guinéens  aux examens de fin d'année scolaire,des classes de 10ème et Terminales.\n\n" +
            "Projet initié par BARRY MAMADOU OURY.\n\n" +
            "Application conçue par Ahmed Yousef et réalisée  par Barry Mamadou Oury.\n\n" +
            "Les Personnes Qui ont Accompagné dans la Réalisation de Ce Projet:\n" +
            "Hassane Sidibé, Dr.Kazaliou ly,  Abdoul Gadiri Diallo(Satina), Team BSK(Elhadj Mamadou), M'ballo, Madjid, Housseinatou Bah, Dr.Sall Abdoullaye Le Français,Idiatou Barry, Mert,Bah Ousmane,Oumar Bah.\n\n" +
            "Partenaire: Club Culturel et Scientifique M.cherif\n\n" +
            "SOURCES: Annales BAC ET BEPC GUINEE, les sujets des pays de la sous region ont été enlevés dans examens-concours.com\n\n" +
            "Contact: appguineexams@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject);
        MobileAds.initialize(this, "ca-app-pub-7038787403547669~6510290389");
        setUpToolbar();
        getViews();
        setUpNavDrawer();
        drawer.addDrawerListener(drawerbutton);
        fragments = new android.support.v4.app.Fragment[]{frag1, frag2, frag3};
        setTabs(getIntent().getBundleExtra("subject"));
        Title.setText(((Subject)getIntent().getBundleExtra("subject").getSerializable("subject")).getTitle());
    }
    private void setTabs(Bundle subject){
        FragmentManager fm = getSupportFragmentManager();
        Term t = (Term)subject.getSerializable("term");
        if(t.getTitle().equalsIgnoreCase("sujets proposes")){
            fragments= new android.support.v4.app.Fragment[]{frag1, frag3};
            try {
                tabs.removeTabAt(2);
            }catch (Exception e){

            }

        }
        if(t.getTitle().equalsIgnoreCase("comment s'orienter")){
            fragments= new android.support.v4.app.Fragment[]{new apres_frag()};
            try {
                tabs.removeAllTabs();

            }catch (Exception e){

            }
            tabs.setVisibility(View.GONE);
        }
        adapter = new TabAdapter(fm,fragments,subject);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabs.setScrollPosition(position,positionOffset,true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);

            }
        });
    }
    private void setUpToolbar() {
        toolbar = findViewById(R.id.my_toolbar) ;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.topmenu,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                String market_uri = "https://play.google.com/store/apps/details?id="+getPackageName();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, market_uri);
                startActivity(Intent.createChooser(intent, "Share App"));
                break;
            case R.id.about:
                alert = new AlertDialog.Builder(this);
                alert.setMessage(message);
                alert.show();
                break;
        }
        return true;
    }

    private void setUpNavDrawer() {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        Menu menu = nav.getMenu();
        menu.clear();
        Term t= (Term) getIntent().getBundleExtra("subject").getSerializable("term");
        int i =0;
        for(Subject s : t.getSubjects()){
            if (s.getTitle().equals(((Subject)getIntent().getBundleExtra("subject").getSerializable("subject")).getTitle()))
            {
                menu.add(R.id.grp1, i++, Menu.NONE, s.getTitle()).setChecked(true);
            }
            else{
                menu.add(R.id.grp1, i++, Menu.NONE, s.getTitle());
            }

        }
        menu.setGroupCheckable(R.id.grp1,true,true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Term t= (Term) getIntent().getBundleExtra("subject").getSerializable("term");

                for(Subject s : t.getSubjects()){
                    if (s.getTitle().equals(item.getTitle()))
                    {
                        Bundle b = new Bundle();
                        b.putSerializable("subject", s);
                        b.putSerializable("term",t);
                        setTabs(b);
                        drawer.closeDrawer(GravityCompat.START);
                        Title.setText(s.getTitle());
                        break;
                    }


                }
                return true;
            }
        });
    }
    private void getViews(){
        drawer = (DrawerLayout)findViewById(R.id.drawerlayout);
        drawerbutton = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.draweropen,R.string.drawerclose);
        viewPager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tablayout);
        nav = (NavigationView)findViewById(R.id.NavView);
        View v = findViewById(R.id.header);
        Title = findViewById(R.id.textView);
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
      //  Log.i("testDevice",String.valueOf(adRequest.isTestDevice(this))) ;
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }
}
