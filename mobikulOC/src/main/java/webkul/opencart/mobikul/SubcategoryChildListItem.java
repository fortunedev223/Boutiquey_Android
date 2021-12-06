package webkul.opencart.mobikul;

public class SubcategoryChildListItem {
    private int mChildPosition;
    private int mParentPosition;
    private String mTitle;

    public SubcategoryChildListItem(String mTitle, int parentPosition, int childPosition) {
        this.mTitle = mTitle;
        this.mParentPosition = parentPosition;
        this.mChildPosition = childPosition;
    }

    public int getChildPosition() {
        return mChildPosition;
    }

    public void setChildPosition(int mChildPosition) {
        this.mChildPosition = mChildPosition;
    }

    public int getmParentPosition() {
        return mParentPosition;
    }

    public void setmParentPosition(int mParentPosition) {
        this.mParentPosition = mParentPosition;
    }

    public int getParentPosition() {
        return mParentPosition;
    }

    public void setParentPosition(int mParentPosition) {
        this.mParentPosition = mParentPosition;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}