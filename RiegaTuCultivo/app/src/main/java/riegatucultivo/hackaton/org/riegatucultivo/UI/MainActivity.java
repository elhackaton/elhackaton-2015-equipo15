package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import riegatucultivo.hackaton.org.riegatucultivo.R;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton addSectorFAB;
    private RecomendacionFragment recomendacionFragment;
    private TabLayout tabs;
    ActionBarDrawerToggle mDrawerToggle;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

/*
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setAlias("prueba");
*/

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarMY);
        addSectorFAB =(FloatingActionButton) findViewById(R.id.add_sector_fb);
        addSectorFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, FormularioSectorActivity.class);
                startActivity(in);
            }
        });
        addSectorFAB.hide();

        // SET MENU DRAWER LAYOUT
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null ){
                setupNavigationDrawerContent(navigationView);
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        recomendacionFragment = new RecomendacionFragment();
        setupViewPager(viewPager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(tab.getPosition());
                        addSectorFAB.hide();
                        break;
                    case 1:
                        viewPager.setCurrentItem(tab.getPosition());
                        addSectorFAB.show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabs = tabLayout;
    }


    /**
     * Carga el navigationDrawer del usuario identificado
     * @param navigationView
     */
    private void setupNavigationDrawerContent(NavigationView navigationView){
        navigationView.inflateMenu(R.menu.navigation_drawer_menu);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_home:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case  R.id.item_navigation_drawer_mapa:
                                menuItem.setChecked(true);
                                Intent in = new Intent(MainActivity.this, LocalizacionActivity.class);
                                startActivity(in);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_log_out:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                salir(0);
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
    }

    /**
     * Salir de la aplicación y abrir la pantalla de login
     * @param opcion
     */
    private void salir(int opcion) {
        switch (opcion){
            case 0: // salir a login
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                finishAffinity();
                startActivity(in);
                break;

        }

    }

    /**
     * Añadimos las pestañas, si es la versión de prueba solo se mostrará la pestaña de servicios.
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterN adapter = new ViewPagerAdapterN(getSupportFragmentManager());
        adapter.addFrag(recomendacionFragment, getString(R.string.tab_recomendacion));
        adapter.addFrag(new MisSectoresFragment(), getString(R.string.tab_mis_sectores));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id ==  android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adapter para el ViewPager
     */
    class ViewPagerAdapterN extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapterN(FragmentManager manager) {
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
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
