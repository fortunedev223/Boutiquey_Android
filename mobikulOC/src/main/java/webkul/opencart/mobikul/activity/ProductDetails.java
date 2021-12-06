package webkul.opencart.mobikul.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;

import webkul.opencart.mobikul.BaseActivity;
import webkul.opencart.mobikul.DescriptionTab;
import webkul.opencart.mobikul.FeaturedTab;
import webkul.opencart.mobikul.model.GetProduct.ProductDetail;
import webkul.opencart.mobikul.R;
import webkul.opencart.mobikul.databinding.ActivityProductDetails2Binding;

public class ProductDetails extends BaseActivity {
    private ActivityProductDetails2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details2);
        TabLayout tabLayout = binding.tablayout;
        ViewPager pager = binding.pager;
        Toolbar toolbar = binding.toolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView title = binding.toolbar.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.title_activity_product_details));
        ArrayList<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.desciption));
        titles.add(getResources().getString(R.string.features));
        if (getIntent().hasExtra("detail")) {
            ProductDetail productDetail = getIntent().getParcelableExtra("detail");
            FeaturedTab featuredTab = new FeaturedTab(productDetail);
            DescriptionTab descriptionTab = new DescriptionTab(productDetail);
            ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
            fragmentArrayList.add(descriptionTab);
            fragmentArrayList.add(featuredTab);
            pager.setAdapter(new PageAdapter(getSupportFragmentManager(), titles, fragmentArrayList));
            tabLayout.setupWithViewPager(pager);
            pager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        }
    }

    protected class PageAdapter extends FragmentPagerAdapter {
        ArrayList<String> title;
        ArrayList<Fragment> fragmentArrayList;

        public PageAdapter(FragmentManager fm, ArrayList<String> title, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.title = title;
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
