package projetotcc.estudandoquimica.view.offline;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardItem;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardPagerAdapter;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.ShandowTransformer;


public class SubConteudoActivity extends AppCompatActivity{

    private ViewPager viewPager;

    private CardPagerAdapter cardPagerAdapter;
    private ShandowTransformer cardShandowTransformer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        cardPagerAdapter = new CardPagerAdapter(this);
        ArrayList<CardItem> cardItems = getIntent().getParcelableArrayListExtra("cardItems");

        for(int i = 0; i < cardItems.size(); i++){
            cardPagerAdapter.addCardItem(cardItems.get(i));
        }

        cardShandowTransformer = new ShandowTransformer(viewPager, cardPagerAdapter);

        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, cardShandowTransformer);
        viewPager.setOffscreenPageLimit(3);
        cardShandowTransformer.enableScaling(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return true;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
