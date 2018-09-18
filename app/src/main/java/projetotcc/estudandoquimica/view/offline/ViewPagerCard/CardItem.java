package projetotcc.estudandoquimica.view.offline.ViewPagerCard;

import android.os.Parcel;
import android.os.Parcelable;

public class CardItem implements Parcelable {

    private String mTextResource;
    private String mTitleResource;
    private int id;

    public CardItem(int id, String title, String text) {
        mTitleResource = title;
        mTextResource = text;
        this.id = id;
    }

    protected CardItem(Parcel in) {
        mTextResource = in.readString();
        mTitleResource = in.readString();
        id = in.readInt();
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

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTextResource);
        dest.writeString(mTitleResource);
        dest.writeInt(id);
    }
}
