package projetotcc.estudandoquimica.view.offline;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import projetotcc.estudandoquimica.componentesPersonalizados.CircleProgressBar;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardItem;
import projetotcc.estudandoquimica.view.offline.ViewPagerCard.CardPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoOfflineFragment extends Fragment implements View.OnClickListener{


    public ConteudoOfflineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_conteudo_offline, container, false);

        final CircleProgressBar progressBar = view.findViewById(R.id.processo_titulo1);
        final CircleProgressBar progressBar2 = view.findViewById(R.id.processo_titulo2);
        final CircleProgressBar progressBar3 = view.findViewById(R.id.processo_titulo3);

        progressBar.setProgress(100);
        progressBar2.setProgress(100);
        progressBar3.setProgress(100);
        setHasOptionsMenu(false);

        ViewSwitcher vsBasico = view.findViewById(R.id.vsBasico);
        ViewSwitcher vsLig = view.findViewById(R.id.vsLig);
        ViewSwitcher vsOrg = view.findViewById(R.id.vsOrg);

        vsLig.setOnClickListener(this);
        vsBasico.setOnClickListener(this);
        vsOrg.setOnClickListener(this);

       return view;
    }


    public void onClickItem(View view) {
    }

    @Override
    public void onClick(View v) {

        if(v instanceof ViewSwitcher){

            ViewSwitcher viewSwitcher = (ViewSwitcher) v;
            viewSwitcher.showNext();
            int id = 0;

            if(v.getId() == R.id.vsBasico){

                id = 1;
            }else if(v.getId() == R.id.vsLig){

                id = 2;

            }else if(v.getId() == R.id.vsOrg){

                id = 3;
            }

            Intent it = new Intent(getContext(), ListaAssuntoActivity.class);
            it.putExtra("id", id);
            startActivity(it);

        }

    }
}
