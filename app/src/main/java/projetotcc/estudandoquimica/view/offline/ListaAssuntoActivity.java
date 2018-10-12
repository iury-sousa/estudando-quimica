package projetotcc.estudandoquimica.view.offline;

import android.content.Intent;
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


public class ListaAssuntoActivity extends AppCompatActivity implements CardPagerAdapter.ClickListener{

    private ViewPager viewPager;

    private CardPagerAdapter cardPagerAdapter;
    private ShandowTransformer cardShandowTransformer;
    private int idAssunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        Bundle b = getIntent().getExtras();

        if(b != null ){
            idAssunto = getIntent().getExtras().getInt("idAssunto");
            toolbar.setTitle(getIntent().getExtras().getString("titulo"));
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        cardPagerAdapter = new CardPagerAdapter(this);
        ArrayList<CardItem> cardItems = getIntent().getParcelableArrayListExtra("cardItems");

        for(int i = 0; i < cardItems.size(); i++){

            cardPagerAdapter.addCardItem(cardItems.get(i));
        }

        cardShandowTransformer = new ShandowTransformer(viewPager, cardPagerAdapter);
        cardShandowTransformer.enableScaling(true);

        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, cardShandowTransformer);
        viewPager.setOffscreenPageLimit(3);

        cardPagerAdapter.setClickListener(this);

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

    @Override
    public void onEstudarClick(CardItem item) {
        Intent it = new Intent(this, ExibirConteudoActivity.class);
        it.putExtra("idConteudo", item.getId());
        it.putExtra("titulo", item.getTitle());
        startActivity(it);
    }
}
