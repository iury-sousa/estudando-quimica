package projetotcc.estudandoquimica.interfaces;

import android.support.v7.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 7;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
