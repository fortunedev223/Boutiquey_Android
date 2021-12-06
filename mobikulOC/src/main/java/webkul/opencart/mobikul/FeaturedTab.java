package webkul.opencart.mobikul;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import webkul.opencart.mobikul.adapter.ProductFeatureAdapter;
import webkul.opencart.mobikul.adapterModel.ProductFeatureAdapterModel;
import webkul.opencart.mobikul.model.GetProduct.ProductDetail;

@SuppressLint("ValidFragment")
public class FeaturedTab extends Fragment {
    View v = null;
    private ProductDetail productDetail;
    private LinearLayout layout;

    @SuppressLint("ValidFragment")
    public FeaturedTab() {

    }

    public FeaturedTab(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_featured_tab, container, false);
        layout = v.findViewById(R.id.featureContentLayout);

        if (productDetail.getAttributeGroups().size() != 0) {
            RecyclerView recyclerView = new RecyclerView(getActivity());
            ProductFeatureAdapter adapter;
            ArrayList<ProductFeatureAdapterModel> featureAdapterModels = new ArrayList<>();
            for (int i = 0; i < productDetail.getAttributeGroups().size(); i++) {
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> text = new ArrayList<>();
                for (int j = 0; j < productDetail.getAttributeGroups().get(i).getAttribute().size(); j++) {
                    name.add(productDetail.getAttributeGroups().get(i).getAttribute().get(j).getName());
                    text.add(productDetail.getAttributeGroups().get(i).getAttribute().get(j).getText());
                }
                featureAdapterModels.add(new ProductFeatureAdapterModel(
                        productDetail.getAttributeGroups().get(i).getName(),
                        name, text));
            }
            adapter = new ProductFeatureAdapter(getActivity(), featureAdapterModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            layout.addView(recyclerView);
        } else {
            TextView textView = new TextView(getActivity());
            textView.setText(getActivity().getResources().getString(R.string.no_feature_available));
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            textView.setTextSize(16);
            textView.setPadding(20, 20, 20, 20);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(textView);
        }
        return v;
    }

}
