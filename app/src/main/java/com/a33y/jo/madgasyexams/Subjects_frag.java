package com.a33y.jo.madgasyexams;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A simple {@link Fragment} subclass.
 */
public class Subjects_frag extends AppCompatActivity implements ListItemClickListener{
    RecyclerView recyclerView;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView title;
    Toolbar toolbar ;
    NavigationView nav;
    ActionBarDrawerToggle drawerbutton;
    DrawerLayout drawer;
    AlertDialog.Builder alert;
    TextView header;
    InterstitialAd mInterstitialAd;
    String message = "GUINEEXAMS est une application conçue pour accompagner  les candidats Guinéens  aux examens de fin d'année scolaire,des classes de 10ème et Terminales.\n\n" +
            "Projet initié par BARRY MAMADOU OURY.\n\n" +
            "Application conçue par Ahmed Yousef et réalisée  par Barry Mamadou Oury.\n\n" +
            "Les Personnes Qui ont Accompagné dans la Réalisation de Ce Projet:\n" +
            "Hassane Sidibé, Dr.Kazaliou ly,  Abdoul Gadiri Diallo(Satina), Team BSK(Elhadj Mamadou), M'ballo, Madjid, Housseinatou Bah, Dr.Sall Abdoullaye Le Français,Idiatou Barry, Mert,Bah Ousmane,Oumar Bah.\n\n" +
            "Partenaire: Club Culturel et Scientifique M.cherif\n\n" +
            "SOURCES: Annales BAC ET BEPC GUINEE, les sujets des pays de la sous region ont été enlevés dans examens-concours.com\n\n" +
            "Contact: appguineexams@gmail.com";
    public Subjects_frag() {
        // Required empty public constructor
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycle);
        title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));
        setUpToolbar();
        getViews();
        setUpNavDrawer();
        loadInstAd();
        drawer.addDrawerListener(drawerbutton);
        recyclerView = findViewById(R.id.rec);
        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        for(Term t : DataList.getTermss()) {
            if(t.getTitle().equalsIgnoreCase(getIntent().getStringExtra("title"))) {
                adapter = new Adapter(t,t.getSubjects(), this, 0);
                adapter.setListItemClickListener(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                break;
            }
        }
    }

    private void loadInstAd(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));


    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.show();
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
        String t = getIntent().getStringExtra("title");
        int i=0;
        if(t.contains("Terminal")){
            menu.add(R.id.grp1, i++, Menu.NONE, t).setChecked(true);
             for(Term term : DataList.getTermss()){
                  if(term.getTitle().contains("Terminal")&& !term.getTitle().equalsIgnoreCase(t)){
                      menu.add(R.id.grp1, i++, Menu.NONE, term.getTitle());
                  }
             }

        }else{
            menu.add(R.id.grp1, i++, Menu.NONE, t).setChecked(true);
            for(Term term : DataList.getTermss()){
                if(!term.getTitle().contains("Terminal")&& !term.getTitle().equalsIgnoreCase(t)){
                    menu.add(R.id.grp1, i++, Menu.NONE, term.getTitle());
                }
            }
            //menu.add(R.id.grp1, i++, Menu.NONE, "BAC");
        }
        menu.setGroupCheckable(R.id.grp1,true,true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title = item.getTitle().toString();
                if(title.equals("BAC")){

                    return true;
                }
                for(Term t : DataList.getTermss()) {
                    if(t.getTitle().equalsIgnoreCase(title)) {
                        adapter = new Adapter(t,t.getSubjects(), Subjects_frag.this, 0);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        break;
                    }
                }
                drawer.closeDrawer(GravityCompat.START);
                Subjects_frag.this.title.setText(title);
                return true;
            }
        });
    }
    private void getViews(){
        drawer = (DrawerLayout)findViewById(R.id.drawerlayout);
        drawerbutton = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.draweropen,R.string.drawerclose);
         header = findViewById(R.id.username);
         header.setVisibility(View.GONE);
        nav = (NavigationView)findViewById(R.id.NavView);
        View v = findViewById(R.id.header);

    }

    @Override
    public void onClick(int pos) {
        requestNewInterstitial();
    }
}
