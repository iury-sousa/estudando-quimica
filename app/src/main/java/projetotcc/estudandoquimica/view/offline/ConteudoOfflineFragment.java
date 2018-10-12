package projetotcc.estudandoquimica.view.offline;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import projetotcc.estudandoquimica.componentesPersonalizados.CircleProgressBar;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardItem;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoOfflineFragment extends Fragment {


    public ConteudoOfflineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_conteudo_offline, container, false);

        RelativeLayout imageButton = view.findViewById(R.id.titulo1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<CardItem> cardItems = new ArrayList<>();
                cardItems.add(new CardItem(1,"Ligações químicas", "Os átomos ligam entre si para formar moléculas, para que isso ocorra existem três tipos de ligação: a ligação iônica, a ligação covalente e a ligação metálica."));
//                cardItems.add(new CardItem(R.string.title_2, R.string.text_1));
//                cardItems.add(new CardItem(R.string.title_3, R.string.text_1));
//                cardItems.add(new CardItem(R.string.title_4, R.string.text_1));

                Intent it = new Intent(getContext(), ListaAssuntoActivity.class);
                it.putParcelableArrayListExtra("cardItems", cardItems);
                it.putExtra("titulo", "Básico");
                startActivity(it);
            }
        });

        LinearLayout quiOrganica = view.findViewById(R.id.quimica_organica);
        quiOrganica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<CardItem> cardItems = new ArrayList<>();
                cardItems.add(new CardItem(1,"Química Orgânica", "A química orgânica é uma subdivisão da química que estuda os compostos carbônicos e compostos orgânicos."));
                cardItems.add(new CardItem(2, "Funções e nomenclatura", "As funções orgânicas são caracterizadas por um grupo funcional, que confere características e nomenclaturas especificas."));

                Intent it = new Intent(getContext(), ListaAssuntoActivity.class);
                it.putParcelableArrayListExtra("cardItems", cardItems);
                it.putExtra("titulo", "Química Orgânica");
                startActivity(it);
            }
        });

        final CircleProgressBar progressBar = view.findViewById(R.id.processo_titulo1);
        final CircleProgressBar progressBar2 = view.findViewById(R.id.processo_titulo2);
        final CircleProgressBar progressBar3 = view.findViewById(R.id.processo_titulo3);

        progressBar.setProgress(100);
        progressBar2.setProgress(100);
        progressBar3.setProgress(100);
        setHasOptionsMenu(false);
       return view;
    }

}
