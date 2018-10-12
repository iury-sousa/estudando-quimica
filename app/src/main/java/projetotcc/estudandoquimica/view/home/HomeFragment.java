package projetotcc.estudandoquimica.view.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.offline.ConteudoOfflineFragment;
import projetotcc.estudandoquimica.view.turma.TurmaFragment;

public class HomeFragment extends Fragment{

    protected AppBarLayout appBar;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        View viewContainer = (View) container.getParent();
        TabsAdapter tabsAdapter = new TabsAdapter(getActivity().getSupportFragmentManager());



        appBar = (AppBarLayout) viewContainer.findViewById(R.id.appbar);

        tabLayout = new TabLayout(Objects.requireNonNull(getActivity()));
        tabLayout.setTabTextColors(Color.parseColor("#FF5D5D5D"), Color.parseColor("#410C5A"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#410C5A"));
        tabLayout.setSelectedTabIndicatorHeight(8);

        appBar.addView(tabLayout);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) tabLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        tabLayout.setLayoutParams(params);
        ViewPager viewPager = view.findViewById(R.id.pagina);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_school);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_book);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_book);

        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle(getString(R.string.app_name));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appBar.setExpanded(true,true);

                if(tab.getPosition() == 0){
                    activity.getSupportActionBar().setTitle(getString(R.string.app_name));
                    activity.getSupportActionBar().show();

                }else if(tab.getPosition() == 1){
                    activity.getSupportActionBar().setTitle("Turmas");
                    activity.getSupportActionBar().show();

                }else if(tab.getPosition() == 2){
                    activity.getSupportActionBar().setTitle("Conteúdo offline");
                    activity.getSupportActionBar().show();

                }else{
                    activity.getSupportActionBar().setTitle("Quiz química");
                    activity.getSupportActionBar().show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //setIconsTabLayout();

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(tabLayout);
    }


}

 class TabsAdapter extends FragmentStatePagerAdapter {

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

        switch (position){
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
