package projetotcc.estudandoquimica.view.offline.ViewPagerCard;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

import projetotcc.estudandoquimica.interfaces.CardAdapter;

public class ShandowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager viewPager;
    private CardAdapter cardAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;

    public ShandowTransformer(ViewPager viewPager, CardAdapter cardAdapter) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        this.cardAdapter = cardAdapter;
    }

    public void enableScaling(boolean enable){

        if(mScalingEnabled && !enable){
            CardView currentCard = cardAdapter.getCardViewAt(viewPager.getCurrentItem());
            if(currentCard != null){
                currentCard.animate().scaleX(1);
                currentCard.animate().scaleY(1);
            }
        }else if(!mScalingEnabled && enable){

            CardView currentCard = cardAdapter.getCardViewAt(viewPager.getCurrentItem());
            if(currentCard != null){
                currentCard.animate().scaleY(1.1f);
                currentCard.animate().scaleX(1.1f);
            }
        }

        mScalingEnabled = enable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int realCurrentPosition;
        int nextPosition;
        float baseElevation = cardAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        if(goingLeft){
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        }else{
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        if(nextPosition > cardAdapter.getCount() - 1 || realCurrentPosition > cardAdapter.getCount() - 1){
            return;
        }

        CardView currentCard = cardAdapter.getCardViewAt(realCurrentPosition);

        if(currentCard != null){
            if(mScalingEnabled){
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }

            currentCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = cardAdapter.getCardViewAt(nextPosition);

        if(nextCard != null){
            if(mScalingEnabled){
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }

            nextCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
