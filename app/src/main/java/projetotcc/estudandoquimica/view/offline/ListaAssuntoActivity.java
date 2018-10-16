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
    private int id;

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
            id = getIntent().getExtras().getInt("id");

        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        cardPagerAdapter = new CardPagerAdapter(this);

        ArrayList<CardItem> cardItems = null;

        if(id == 1) {

            toolbar.setTitle("Tabela Periódica");
            cardItems = new ArrayList<>();
            cardItems.add(new CardItem(0, "1 Tabela periódica","A tabela periódica é uma representação ordenada, que mostra características e informações sobre todos os elementos químicos conhecidos"));
            cardItems.add(new CardItem(1, "1.1 Elementos Químicos","Os elementos químicos também chamados de substâncias simples, são conjuntos de átomos que apresentam o mesmo número de prótons no interior de seu núcleo..."));
            cardItems.add(new CardItem(2, "1.2 Representação dos Elementos químicos","Todos os elementos químicos representados na tabela periódica possuem suas características..."));
            cardItems.add(new CardItem(3, "1.3 Organização dos Elementos Químicos","Os Elementos são organizados de maneira crescente a partir do seu número atômico. Entretanto são classificadas também pelo período e pelas famílias."));
            cardItems.add(new CardItem(4, "1.4 Propriedades dos Elementos","Os elementos químicos possuem dois tipos de propriedades..."));
            cardItems.add(new CardItem(5, "1.4.1 Raio atômico","Característica relacionada ao tamanho do átomo, este valor é obtido a partir da distância do núcleo de dois átomos de mesmo elemento"));
            cardItems.add(new CardItem(6, "1.4.2 Energia (Potencial) de Ionização","Energia mínima necessária para retirar um elétron de um átomo químico que se encontra neutro"));


        } else if(id == 2){

            toolbar.setTitle("Ligações Químicas");
            cardItems = new ArrayList<>();

            cardItems.add(new CardItem(0, "1 Ligações entre átomos e moléculas", "Os átomos ligam entre si para formar moléculas, para que isso ocorra existem três tipos de ligação: a ligação iônica, a ligação covalente e a ligação metálica."));
            cardItems.add(new CardItem(1, "1.1 Ligações iônicas", "Nas ligações iônicas os átomos devem apresentar como característica, a possibilidade de ganhar ou perder elétrons..."));
            cardItems.add(new CardItem(2, "1.2 Ligações covalentes ou molecular", "Nas ligações covalentes os átomos têm que possuir como característica a possibilidade de compartilhar elétrons..."));
            cardItems.add(new CardItem(3, "1.3 Ligações metálicas", "São ligações feitas entre metais e metais, que formam as chamadas ligas metálicas."));

        }else if(id == 3){

            toolbar.setTitle("Química Orgânica");
            cardItems = new ArrayList<>();
            cardItems.add(new CardItem(0, "1 Química Orgânica", "A química orgânica é uma subdivisão da química que estuda os compostos carbônicos e compostos orgânicos."));
            cardItems.add(new CardItem(1, "1.1 Funções e nomenclatura", "As funções orgânicas são caracterizadas por um grupo funcional, que confere características e nomenclaturas especificas."));
            cardItems.add(new CardItem(2, "1.1.1 Hidrocarbonetos", "Os hidrocarbonetos são funções mais simples, pois ela é composta somente por Carbono e Hidrogênio"));
            cardItems.add(new CardItem(3, "1.1.1.1 Prefixo, Parte Central e Terminação", "Os prefixos indicam a quantidade de carbonos existentes na cadeia carbônica."));
            cardItems.add(new CardItem(4, "1.1.2 Alcoois", "Os álcoois são compostos orgânicos que possui como principal característica a presença da hidroxila (OH), ligada a um átomo de Carbono saturado."));
            cardItems.add(new CardItem(5, "1.1.3 Fenol", "O fenol é um composto orgânico que possuem uma ou mais hidroxilas OH que ficam ligadas diretamente a um anel aromático."));
            cardItems.add(new CardItem(6, "1.1.4 Aldeínos", "Os aldeídos são compostos orgânicos que possuem em sua estrutura o grupo CHO e fazem parte das funções oxigenadas"));
            cardItems.add(new CardItem(7, "1.1.5 Centonas", "As cetonas são compostos orgânicos que possui em sua estrutura o grupo funcional CO, que fica localizada entre dois átomos de carbono."));
            cardItems.add(new CardItem(8, "1.1.6 Éter", "O éter é todo composto orgânico onde a cadeia carbônica apresenta o Oxigênio entre dois carbonos."));
            cardItems.add(new CardItem(9, "1.1.7 Éster", "Éster são compostos orgânicos que possuem um radical carbônico no lugar do hidrogênio dos carboxílicos."));
            cardItems.add(new CardItem(10,"1.1.8 Ácido Carboxílico", "Ácido Carboxílico são compostos orgânicos que apresentam uma ou mais carboxilas (COOH) ligadas a cadeia."));
        }

        if(cardItems != null) {

            for (int i = 0; i < cardItems.size(); i++) {

                cardPagerAdapter.addCardItem(cardItems.get(i));
            }


            cardShandowTransformer = new ShandowTransformer(viewPager, cardPagerAdapter);
            cardShandowTransformer.enableScaling(true);

            viewPager.setAdapter(cardPagerAdapter);
            viewPager.setPageTransformer(false, cardShandowTransformer);
            viewPager.setOffscreenPageLimit(3);
        }

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
        it.putExtra("id", id);
        it.putExtra("idAssunto", item.getId());
        it.putExtra("titulo", item.getTitle());
        startActivity(it);
    }
}
