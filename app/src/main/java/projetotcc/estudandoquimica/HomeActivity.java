package projetotcc.estudandoquimica;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

import projetotcc.estudandoquimica.view.home.ConteudoCompartilhadoFragment;
import projetotcc.estudandoquimica.view.home.QuizFragment;
import projetotcc.estudandoquimica.view.offline.ConteudoOfflineFragment;
import projetotcc.estudandoquimica.view.turma.TurmaFragment;

public class HomeActivity extends AppCompatActivity {

    protected AppBarLayout appBar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
//
        appBar = findViewById(R.id.appbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
//
        setSupportActionBar(toolbar);
//
        ViewPager viewPager = findViewById(R.id.pagina);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(tabsAdapter);

        tabLayout = findViewById(R.id.tabs);
        //tabLayout.setTabTextColors(Color.parseColor("#FF5D5D5D"), Color.parseColor("#410C5A"));
       // tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#410C5A"));
        //tabLayout.setSelectedTabIndicatorHeight(8);
        tabLayout.setupWithViewPager(viewPager);
//
//       // appBar.addView(tabLayout);
//
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) tabLayout.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
//        tabLayout.setLayoutParams(params);

        getSupportActionBar().setTitle(getString(R.string.app_name));

//
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_friends);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_open_book);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_game);

//        getSupportActionBar().setTitle(getString(R.string.app_name));
//
//
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appBar.setExpanded(true,true);

                if(tab.getPosition() == 0){
                    getSupportActionBar().setTitle(getString(R.string.app_name));
                    getSupportActionBar().show();

                }else if(tab.getPosition() == 1){
                    getSupportActionBar().setTitle("Turmas");
                    getSupportActionBar().show();

                }else if(tab.getPosition() == 2){
                    getSupportActionBar().setTitle("Conteúdo offline");
                    getSupportActionBar().show();

                }else{
                    getSupportActionBar().setTitle("Quiz química");
                    getSupportActionBar().show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class TabsAdapter extends FragmentPagerAdapter {

        private String[] titles = new String[]{"Conteúdos", "Estudos", "Quiz", "Turmas"};
        private int tabCount = 3;

        public TabsAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null; //titles[position];
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                    return new ConteudoCompartilhadoFragment();

                case 1:
                    return new TurmaFragment();


                case 2:
                    return new ConteudoOfflineFragment();

                case 3:
                    return new QuizFragment();

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
