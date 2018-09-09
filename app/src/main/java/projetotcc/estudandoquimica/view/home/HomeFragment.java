package projetotcc.estudandoquimica.view.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.view.offline.ConteudoOfflineFragment;

public class HomeFragment extends Fragment{

    protected AppBarLayout appBar;
    private TabLayout tabLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        View viewContainer = (View) container.getParent();
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

/*        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) viewContainer.findViewById(R.id.coordinator);
        CoordinatorLayout.LayoutParams params2 = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params2.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appBar, null, 0, 1000, true);*/

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pagina);

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appBar.setExpanded(true,true);
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

    private void setIconsTabLayout(){
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_book);
    }


}

 class TabsAdapter extends FragmentStatePagerAdapter {

    private String[] titles = new String[]{"Estudos", "Estudos", "Quiz"};
    private int tabCount = 3;

    public TabsAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ConteudoCompartilhadoFragment();

            case 1:
                return new ConteudoOfflineFragment();

            case 2:
                return new QuizFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
