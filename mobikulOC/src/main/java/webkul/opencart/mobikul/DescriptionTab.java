package webkul.opencart.mobikul;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import webkul.opencart.mobikul.model.GetProduct.ProductDetail;

@SuppressLint("ValidFragment")
public class DescriptionTab extends Fragment {
    ViewGroup container;
    LayoutInflater inflater;
    String description;
    private ProductDetail productDetail;
    View v;
    private WebView webView;

    public DescriptionTab() {
    }

    @SuppressLint("ValidFragment")
    public DescriptionTab(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_description_tab, container, false);
        ((BaseActivity) getActivity()).setLocale();
        webView = v.findViewById(R.id.webView);
        if (productDetail.getDescription().length() > 1) {
            webView.setVisibility(View.VISIBLE);
            v.findViewById(R.id.error_tv).setVisibility(View.GONE);
            try {
                String html = productDetail.getDescription();
                String mime = "text/html";
                String encoding = "utf-8";
                webView.setFocusable(true);
                webView.loadDataWithBaseURL(null, html, mime, encoding, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            v.findViewById(R.id.error_tv).setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
        return v;
    }
}
