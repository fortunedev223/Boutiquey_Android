package webkul.opencart.mobikul;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import webkul.opencart.mobikul.databinding.ActivityViewPagerExampleBinding;
import webkul.opencart.mobikul.databinding.LayoutViewProductSmallImageViewBinding;
import webkul.opencart.mobikul.helper.*;

public class ViewPagerExampleActivity extends BaseActivity {

    private String imageUrl;
    private ArrayList childImage;
    private String productName;
    private List productImageArrString;
    private int imageSelection;
    private ExtendedViewPager mViewPager;
    private static String[] imageUrls;
    ActivityViewPagerExampleBinding viewPagerExampleBinding;

    /**
     * Step 1: Download and set up v4 support library: http://developer.android.com/tools/support-library/setup.html
     * Step 2: Create ExtendedViewPager wrapper which calls TouchImageView.canScrollHorizontallyFroyo
     * Step 3: ExtendedViewPager is a custom view and must be referred to by its full package name in XML
     * Step 4: Write TouchImageAdapter, located below
     * Step 5. The ViewPager in the XML should be ExtendedViewPager
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_cart).setVisible(false);
        menu.findItem(R.id.action_bell).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            viewPagerExampleBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_pager_example);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setToolbarLoginActivity((Toolbar) viewPagerExampleBinding.toolbar.findViewById(R.id.toolbar));
        setSupportActionBar(getToolbarLoginActivity());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imageUrl = extras.getString("imageUrl");
            productName = extras.getString("productName");
            productImageArrString = extras.getStringArrayList("productImageArr");
            imageSelection = extras.getInt("imageSelection");
            childImage = extras.getStringArrayList("childList");
        }
        JSONArray productImageArr = null;
        productImageArr = new JSONArray(productImageArrString);
        imageUrls = new String[productImageArr.length()];
        ((TextView) viewPagerExampleBinding.toolbar.findViewById(R.id.title)).setText(productName);
        for (int i = 0; i < productImageArrString.size(); i++) {
            imageUrls[i] = productImageArrString.get(i).toString();
            if (imageUrl.equals(productImageArrString.get(i))) {
                imageSelection = i;
            }
        }
        setTitle(Html.fromHtml(productName));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout smallImageBtnlayout = viewPagerExampleBinding.smallImageBtnlayout;
        for (int noOfImages = 0; noOfImages < childImage.size(); noOfImages++) {
            LayoutViewProductSmallImageViewBinding child = LayoutViewProductSmallImageViewBinding.inflate(getLayoutInflater());
            child.getRoot().setId(View.generateViewId());
            child.getRoot().setTag(noOfImages);
            smallImageBtnlayout.addView(child.getRoot());
            String smallImageURL = "";
            ImageView smallimageButton = child.smallimageButton;
            smallImageURL = childImage.get(noOfImages).toString();
            smallimageButton.setTag(noOfImages);
            try {
                Picasso.with(ViewPagerExampleActivity.this).load(smallImageURL).into(smallimageButton);
                child.getRoot().setTag(noOfImages);
                if (noOfImages < productImageArr.length() - 1) {
                    View view = new View(ViewPagerExampleActivity.this);
                    view.setLayoutParams(new android.app.ActionBar.LayoutParams(1, android.app.ActionBar.LayoutParams.MATCH_PARENT));
                    view.setBackgroundColor(Color.TRANSPARENT);
                    smallImageBtnlayout.addView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mViewPager = viewPagerExampleBinding.viewPager;
        mViewPager.setAdapter(new TouchImageAdapter());
        mViewPager.setCurrentItem(imageSelection);
    }

    public class TouchImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
//            TouchImageView img = new TouchImageView(container.getContext(), viewPagerExampleBinding.smallImageBtnHSV);
            ImageView img = new ImageView(container.getContext());
            img.setBackgroundColor(Color.parseColor(getIntent().getStringExtra("dominant")));
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                    webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight()));
            try {
                Glide.with(ViewPagerExampleActivity.this)
                        .load(imageUrls[position])
//                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                img.setBackgroundColor(0);
                                return false;
                            }
                        })
                        .dontAnimate()
                        .skipMemoryCache(true)
                        .into(img);

//                Picasso.with(ViewPagerExampleActivity.this).load(imageUrls[position]).into(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            img.setImageResource(imageUrls[position]);
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeProductsLargeImage(View v) {
        int imageSelection = (Integer) v.getTag();
        mViewPager.setCurrentItem(imageSelection);
    }

}



