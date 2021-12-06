package webkul.opencart.mobikul.handlers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import webkul.opencart.mobikul.CategoryActivity;
import webkul.opencart.mobikul.Subcategory;
import webkul.opencart.mobikul.model.SubcategoryModel.Category;

public class SubCategoryGridHandler {
    Activity activity;

    public SubCategoryGridHandler(Activity activity) {
        this.activity = activity;
    }

    public void onclickCategory(View view, Category model) {
        if (model.getChildStatus()) {
            Intent intent = new Intent(activity, Subcategory.class);
            intent.putExtra("id", model.getPath());
            intent.putExtra("CATEGORY_NAME", model.getName());
            intent.putExtra("image", model.getImage());
            intent.putExtra("dominant", model.getDominantColor());
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent(activity, CategoryActivity.class);
            intent.putExtra("ID", model.getPath());
            intent.putExtra("CATEGORY_NAME", model.getName());
            activity.startActivity(intent);
        }

    }
}
