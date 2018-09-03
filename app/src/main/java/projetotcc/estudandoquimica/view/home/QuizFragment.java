package projetotcc.estudandoquimica.view.home;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projetotcc.estudandoquimica.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {


    public QuizFragment() {
        // Required empty public constructor
    }

    private AppBarLayout appBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;

    }

}
