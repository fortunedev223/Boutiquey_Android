package webkul.opencart.mobikul;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class SubcategoryParentListItem implements ParentListItem {

    private String mTitle,imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private List<SubcategoryChildListItem> mChildItemList;

    public SubcategoryParentListItem(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public List<SubcategoryChildListItem> getChildItemList() {
        return mChildItemList;
    }

    public void setChildItemList(List<SubcategoryChildListItem> list) {
        mChildItemList = list;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
