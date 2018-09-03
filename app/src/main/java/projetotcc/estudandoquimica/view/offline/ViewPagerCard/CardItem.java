package projetotcc.estudandoquimica.view.offline.ViewPagerCard;

import android.os.Parcel;
import android.os.Parcelable;

public class CardItem implements Parcelable {

    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    protected CardItem(Parcel in) {
        mTextResource = in.readInt();
        mTitleResource = in.readInt();
    }

    public static final Creator<CardItem> CREATOR = new Creator<CardItem>() {
        @Override
        public CardItem createFromParcel(Parcel in) {
            return new CardItem(in);
        }

        @Override
        public CardItem[] newArray(int size) {
            return new CardItem[size];
        }
    };

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextResource);
        dest.writeInt(mTitleResource);
    }
}
