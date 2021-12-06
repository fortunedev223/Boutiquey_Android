package webkul.opencart.mobikul;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.handlers.CategoryActivityHandler;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.model.ManufactureInfoModel.Manufacture;
import webkul.opencart.mobikul.model.ProductCategory.Category;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.model.ProductCategory.ProductCategory;
import webkul.opencart.mobikul.model.ProductSearch.ProductSearch;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.analytics.MobikulApplication;
import webkul.opencart.mobikul.connection.VolleyConnection;
import webkul.opencart.mobikul.databinding.ActivityBaseNavigationDrawerBinding;
import webkul.opencart.mobikul.databinding.ActivityCategoryBinding;
import webkul.opencart.mobikul.offline.database.DataBaseHandler;


/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */


public class CategoryActivity extends BaseActivity implements SortByData {

    public static int LAYOUT_ITEM_GRID = 0;
    public static int LAYOUT_ITEM_LIST = 1;
    private String categoryId, customId;
    private String categoryName;
    public Editor editor;
    public JSONObject mainObject;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    public MyAdapter myAdapter;
    private MobikulApplication mMobikulApplication;
    private int screen_width;
    private DrawerLayout mDrawerLayout;
    static final int INTENT_LAYERED_NAVIGATION_REQUEST = 1;
    public static final int INTENT_SORT_BY_REQUEST = 2;
    public ImageView viewSwticherImageView;
    private SharedPreferences categoryViewShared;
    public String searchQuery = "";
    private String queryStringJSON;
    public int totalItems;
    protected int pageNumber = 1;
    private boolean loading = false;
    protected Toast mToast;
    String sortData[] = {"", "", ""};
    int productTotal;
    static HashMap<String, String> filterDataHashMap;
    private String filterString;
    private ExpandableListView expListViewForSub;
    private List<ParentListItem> subheaderList;
    private List<ParentListItem> subchildList;
    static JSONObject drawerJsonObject;
    public Bundle extras;
    private Toolbar toolbar;
    private SearchView searchView;
    private String manufacturerId;
    private String manufacturerTitle;
    private RecyclerView subCategoryRecyclerView;
    private List<ParentListItem> parentListItems;
    private DataBaseHandler mOfflineDataBaseHandler;
    private static final String TAG = "CategoryActivity";
    private ActivityBaseNavigationDrawerBinding categoryBinding;
    private ActivityCategoryBinding activity_category;
    private Callback<ProductCategory> productCategoryCallbackLazy;
    private Callback<Manufacture> manufactureCallbackLazy;
    private Callback<ProductSearch> productSearchCallbackLazy;
    private Callback<ProductCategory> productCategoryCallback;
    private Callback<Manufacture> manufactureCallback;
    private ProductCategory productCategory;
    private ProductSearch productSearch;
    private Callback<ProductSearch> productSearchCallback;
    private final String pageLimit = "20";
    private CategoryActivityHandler categoryActivityHandler;
    private TextView title;
    private TextView shopBy, sortBy;
    public static HashSet<String> filterName;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterName.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (extras.containsKey("isNotification")) {
            Intent i = new Intent(CategoryActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        this.finish();
    }


    public void getIntentExtras(Bundle extras) {
        if (extras != null) {
            /*In case of Search by user*/
            if (getIntent().ACTION_SEARCH.equals(getIntent().getAction())) {
                searchQuery = getIntent().getStringExtra(SearchManager.QUERY);
                activity_category.shopByButtonLayout.setVisibility(View.GONE);
            }
            if (getIntent().hasExtra("search")) {
                searchQuery = getIntent().getStringExtra("search");
                activity_category.shopByButtonLayout.setVisibility(View.GONE);
                title.setText(searchQuery);
            }
            /*In case of Search Terms*/
            else if (extras.containsKey("searchTerm")) {
                searchQuery = extras.getString("searchTerm");
                activity_category.shopByButtonLayout.setVisibility(View.GONE);
                setTitle(webkul.opencart.mobikul.helper.Utils.fromHtml(searchQuery));
            } else if (extras.containsKey("queryStringJSON")) {
                queryStringJSON = extras.getString("queryStringJSON");
                activity_category.shopByButtonLayout.setVisibility(View.GONE);
            } else if (extras.containsKey("type") && extras.get("type").toString().equalsIgnoreCase("custom")) {
                customId = extras.getString("id");
            } else {
                if (extras.containsKey("manufacturer_id")) {
                    manufacturerId = extras.getString("manufacturer_id");
                    manufacturerTitle = extras.getString("imageTitle");
                    title.setText(webkul.opencart.mobikul.helper.Utils.fromHtml(manufacturerTitle));
                    activity_category.shopByButtonLayout.setVisibility(View.GONE);
                } else {
                    categoryId = extras.getString("ID");
                    categoryName = String.valueOf(extras.get("CATEGORY_NAME"));
                    title.setText(webkul.opencart.mobikul.helper.Utils.fromHtml(categoryName));
                }
                try {
                    if (extras.containsKey("drawerData"))
                        drawerJsonObject = new JSONObject(extras.getString("drawerData"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mDrawerLayout = categoryBinding.drawerLayout;
                subCategoryRecyclerView = activity_category.subCategoryRecyclerView;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_navigation_drawer);
        filterName = new HashSet<>();
        mMobikulApplication = (MobikulApplication) getApplication();
        FrameLayout contentFrame = categoryBinding.baseContentFrame;
        activity_category = ActivityCategoryBinding.inflate(getLayoutInflater());
        categoryActivityHandler = new CategoryActivityHandler(CategoryActivity.this);
        activity_category.setHandlers(categoryActivityHandler);
        contentFrame.addView(activity_category.getRoot());
        recyclerView = activity_category.myRecyclerView;
        filterDataHashMap = new HashMap<>();
        extras = getIntent().getExtras();
        title = activity_category.toolbar.findViewById(R.id.title);
        title.setTypeface(null);
        getIntentExtras(extras);
        screen_width = getScreenWidth();
        toolbar = activity_category.toolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewSwticherImageView = activity_category.viewSwitcher;
        categoryViewShared = getSharedPreferences("categoryView", MODE_PRIVATE);
        Drawable filter = AppCompatResources.getDrawable(this, R.drawable.ic_filter);
        Drawable sort = AppCompatResources.getDrawable(this, R.drawable.ic_sort);
        shopBy = activity_category.filter;
        sortBy = activity_category.sort;
        shopBy.setCompoundDrawablesWithIntrinsicBounds(filter, null, null, null);
        sortBy.setCompoundDrawablesRelativeWithIntrinsicBounds(sort, null, null, null);
        setCategoryCallback();
        if (categoryViewShared.getBoolean("isGridView", false)) {
            mLayoutManager = new GridLayoutManager(this, 2);
            Drawable list = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_list_view);
            viewSwticherImageView.setImageDrawable(list);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
            Drawable block = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_block_view);
            viewSwticherImageView.setImageDrawable(block);
        }
        recyclerView.setLayoutManager(mLayoutManager);
        mOfflineDataBaseHandler = new DataBaseHandler(this);
        try {
            JSONObject jo = new JSONObject();
            jo.put("page", pageNumber);
            jo.put("width", screen_width);
            jo.put("limit", "20");
            if (!searchQuery.isEmpty()) {
                jo.put("search", searchQuery);
                webkul.opencart.mobikul.helper.Utils.setRecentSearchData(searchQuery, CategoryActivity.this);
                new SweetAlertBox().showProgressDialog(CategoryActivity.this);
                RetrofitCallback.INSTANCE.productSearchCall(CategoryActivity.this, searchQuery,
                        String.valueOf(pageNumber), String.valueOf(screen_width), pageLimit, "", "",
                        new RetrofitCustomCallback<ProductSearch>(productSearchCallback, CategoryActivity.this));
            } else if (extras.containsKey("manufacturer_id")) {
                isOnline();
                if (isInternetAvailable()) {
                    jo.put("manufacturer_id", manufacturerId);
                    new SweetAlertBox().showProgressDialog(CategoryActivity.this);
                    RetrofitCallback.INSTANCE.manufactureInfoCall(CategoryActivity.this, String.valueOf(pageNumber), String.valueOf(pageLimit),
                            String.valueOf(screen_width), manufacturerId, "", "",
                            new RetrofitCustomCallback<>(manufactureCallback, CategoryActivity.this));
                } else {
                    Cursor databaseCursor = mOfflineDataBaseHandler.selectFromOfflineDB("manufacturerInfo", manufacturerId + "");
                    if (databaseCursor != null) {
                        Log.d(TAG, "Number of Records found: " + databaseCursor.getCount());
                        if (databaseCursor.getCount() != 0) {
                            databaseCursor.moveToFirst();
                            String responseData = databaseCursor.getString(0);
                            Log.d(TAG, "Data from Database Query: Method Name : " + databaseCursor.getString(1) + ", Data : " + databaseCursor.getString(0));
                            databaseCursor.close();
                            manufacturerInfoResponse(responseData);
                        }
                    } else {

                        showDialog(this);
                    }
                }
            } else if (extras.containsKey("type") && extras.get("type").toString().equalsIgnoreCase("custom")) {
                jo.put("id", customId);
                new VolleyConnection(CategoryActivity.this).getResponse(Request.Method.POST, "customCollection", jo.toString());
            } else {
                isOnline();
                if (isInternetAvailable()) {
                    jo.put("path", categoryId);
                    new SweetAlertBox().showProgressDialog(CategoryActivity.this);
                    RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                            String.valueOf(screen_width), pageLimit, "", "", getFilter(), new RetrofitCustomCallback(productCategoryCallback, CategoryActivity.this));
                } else {
                    Cursor databaseCursor = mOfflineDataBaseHandler.selectFromOfflineDB("productCategory", categoryId + "");
                    if (databaseCursor != null && databaseCursor.getCount() != 0) {
                        databaseCursor.moveToFirst();
                        String responseData = databaseCursor.getString(0);
                        databaseCursor.close();
                        productCategory = new Gson().fromJson(responseData, ProductCategory.class);
                        setProductCategoryData(productCategory);
                    } else {
                        showDialog(this);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        categoryActivityHandler.setSortByData(this);
    }

    private void setCategoryCallback() {
        categoryActivityHandler.setSortData(sortData);
        productCategoryCallback = new Callback<ProductCategory>() {
            @Override
            public void onResponse(Call<ProductCategory> call, Response<ProductCategory> response) {
                if (response.body().getError() == 1) {
                    Log.d(TAG, "onResponse: ");
                } else {
                    productCategory = response.body();
                    SweetAlertBox.Companion.dissmissSweetAlertBox();
                    if (categoryViewShared.getBoolean("isGridView", false)) {
                        mLayoutManager = new GridLayoutManager(CategoryActivity.this, 2);
                        Drawable list = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_list_view);
                        viewSwticherImageView.setImageDrawable(list);
                    } else {
                        mLayoutManager = new LinearLayoutManager(CategoryActivity.this);
                        Drawable block = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_block_view);
                        viewSwticherImageView.setImageDrawable(block);
                    }
                    recyclerView.setLayoutManager(mLayoutManager);
                    setProductCategoryData(productCategory);
                }
            }

            @Override
            public void onFailure(Call<ProductCategory> call, Throwable t) {
            }
        };
        productSearchCallback = new Callback<ProductSearch>() {
            @Override
            public void onResponse(Call<ProductSearch> call, Response<ProductSearch> response) {
                if (response.body().getError() == 1) {
                    Log.d(TAG, "onResponse: ");
                } else {
                    productSearch = response.body();
                    SweetAlertBox.Companion.dissmissSweetAlertBox();
                    if (categoryViewShared.getBoolean("isGridView", false)) {
                        mLayoutManager = new GridLayoutManager(CategoryActivity.this, 2);
                        Drawable list = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_list_view);
                        viewSwticherImageView.setImageDrawable(list);
                    } else {
                        mLayoutManager = new LinearLayoutManager(CategoryActivity.this);
                        Drawable block = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_block_view);
                        viewSwticherImageView.setImageDrawable(block);
                    }
                    recyclerView.setLayoutManager(mLayoutManager);
                    categoryActivityHandler.getproductSearchSortBy(productSearch);
                    productTotal = Integer.parseInt(response.body().getProductTotal());
                    TextView categoryTitle = activity_category.notificationMessage;
                    myAdapter = null;
                    categoryTitle.setText("Total " + productTotal + " " + getResources().getString(R.string.items_found));
//                    Log.d(TAG, "onResponse: wishlist value ==== "+productSearchItems(productSearch.getProducts()).get(0).isWishlist_status());
                    myAdapter = new MyAdapter(CategoryActivity.this, productSearchItems(productSearch.getProducts()), mMobikulApplication);
                    try {
                        totalItems = totalItems + productSearch.getProducts().size();
                    } catch (Exception e) {
                        totalItems = totalItems + 0;
                    }
                    if (totalItems == 0) {
                        getBinding().funtionBar.setVisibility(View.GONE);
                        getBinding().errorTv.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().funtionBar.setVisibility(View.VISIBLE);
                        getBinding().errorTv.setVisibility(View.GONE);
                    }
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.addOnScrollListener(ScrollListener);
                    recyclerView.setNestedScrollingEnabled(false);
                    activity_category.listCategoryContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProductSearch> call, Throwable t) {
            }
        };
        manufactureCallback = new Callback<Manufacture>() {
            @Override
            public void onResponse(Call<Manufacture> call, Response<Manufacture> response) {
                categoryActivityHandler.getManufactureInfo(response.body());
                SweetAlertBox.Companion.dissmissSweetAlertBox();
                if (categoryViewShared.getBoolean("isGridView", false)) {
                    mLayoutManager = new GridLayoutManager(CategoryActivity.this, 2);
                    Drawable list = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_list_view);
                    viewSwticherImageView.setImageDrawable(list);
                } else {
                    mLayoutManager = new LinearLayoutManager(CategoryActivity.this);
                    Drawable block = AppCompatResources.getDrawable(CategoryActivity.this, R.drawable.ic_block_view);
                    viewSwticherImageView.setImageDrawable(block);
                }
                recyclerView.setLayoutManager(mLayoutManager);
                TextView categoryTitle = activity_category.notificationMessage;
                productTotal = Integer.parseInt(response.body().getManufacturers().getProductTotal());
                myAdapter = null;
                categoryTitle.setText("Total " + +productTotal + getResources().getString(R.string.items_found));
                myAdapter = new MyAdapter(CategoryActivity.this, manufactureItems(response.body()), mMobikulApplication);
                totalItems = totalItems + response.body().getManufacturers().getProducts().size();
                recyclerView.setAdapter(myAdapter);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.addOnScrollListener(ScrollListener);
                activity_category.listCategoryContainer.setVisibility(View.VISIBLE);
                mDrawerLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Manufacture> call, Throwable t) {
            }
        };
    }

    private void setProductCategoryData(ProductCategory productCategory) {
        String catgoryOfflineData = new GsonBuilder().create().toJson(productCategory);
        mOfflineDataBaseHandler.updateIntoOfflineDB("productCategory", catgoryOfflineData, categoryId + "");
        categoryActivityHandler.getproductCategorySortBy(productCategory);
        categoryActivityHandler.getproductCategorySortBy(productCategory);
        productTotal = Integer.parseInt(productCategory.getCategoryData().getProductTotal());
        if (productCategory.getCategoryData().getCategories().size() != 0) {
            activity_category.subCategoryRecyclerView.setVisibility(View.VISIBLE);
            prepareSubCategoryData(productCategory.getCategoryData().getCategories());
            subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
            subCategoryRecyclerView.setAdapter(new CategoryExpandableRecyclerAdapter(CategoryActivity.this, parentListItems));
        }
        TextView categoryTitle = activity_category.notificationMessage;
        myAdapter = null;
        if (productCategory.getCategoryData().getProducts().size() != 0 || productCategory.getCategoryData().getCategories().size() == 0) {
            categoryTitle.setText(getResources().getString(R.string.total) + " " + productTotal + " " + getResources().getString(R.string.items_found));
        } else {
            categoryTitle.setVisibility(View.GONE);
        }
        myAdapter = new MyAdapter(CategoryActivity.this, productCategoryItems(productCategory.getCategoryData().getProducts()), mMobikulApplication);
        totalItems = totalItems + productCategory.getCategoryData().getProducts().size();
        if (totalItems == 0) {
            getBinding().funtionBar.setVisibility(View.GONE);
//            if (productCategory.getCategoryData().getCategories().size() == 0) {
            getBinding().errorTv.setVisibility(View.VISIBLE);
//            }
        } else {
            getBinding().funtionBar.setVisibility(View.VISIBLE);
            getBinding().errorTv.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(ScrollListener);
        activity_category.listCategoryContainer.setVisibility(View.VISIBLE);
        mDrawerLayout.setVisibility(View.VISIBLE);
    }

    public void customCollectionResponse(String backResult) {
        try {
            Log.d(TAG, "customCollectionResponse: response === " + backResult);
            JSONObject jo = new JSONObject(backResult);
            JSONArray jsonArray = jo.getJSONArray("products");
            productTotal = jo.getInt("product_total");
            activity_category.notificationMessage.setVisibility(View.GONE);
            myAdapter = new MyAdapter(CategoryActivity.this, items(jsonArray), mMobikulApplication);
            totalItems = totalItems + jsonArray.length();
            recyclerView.setAdapter(myAdapter);
            recyclerView.addOnScrollListener(ScrollListener);
            recyclerView.setNestedScrollingEnabled(false);
            activity_category.listCategoryContainer.setVisibility(View.VISIBLE);
            // mDrawerLayout.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void prepareSubCategoryData(List<Category> subCategoryList) {
        parentListItems = new ArrayList<>();
        List<SubcategoryParentListItem> subcategoryParentListItems = new ArrayList<>();
        SubcategoryParentListItem viewAllSubcategoryParentListItem = null;
        viewAllSubcategoryParentListItem = new SubcategoryParentListItem(getResources().getString(R.string.view));
        subcategoryParentListItems.add(viewAllSubcategoryParentListItem);
        subchildList = new ArrayList<>();
        SubcategoryParentListItem subcategoryParentListItem = subcategoryParentListItems.get(0);
        List<SubcategoryChildListItem> childItemList = new ArrayList<>();
        for (int j = 0; j < subCategoryList.size(); j++) {
            childItemList.add(new SubcategoryChildListItem(
                    subCategoryList.get(j).getName()
                    , 0
                    , j + 1
            ));
        }
        subcategoryParentListItem.setChildItemList(childItemList);
        parentListItems.add(subcategoryParentListItem);
    }

    private void prepareListData(JSONArray subCategoryData) {
        try {
            parentListItems = new ArrayList<>();
            List<SubcategoryParentListItem> subcategoryParentListItems = new ArrayList<>();
            SubcategoryParentListItem viewAllSubcategoryParentListItem = null;
            viewAllSubcategoryParentListItem = new SubcategoryParentListItem("View");
            subcategoryParentListItems.add(viewAllSubcategoryParentListItem);
            subchildList = new ArrayList<>();
            SubcategoryParentListItem subcategoryParentListItem = subcategoryParentListItems.get(0);
            List<SubcategoryChildListItem> childItemList = new ArrayList<>();
            for (int j = 0; j < subCategoryData.length(); j++) {
                childItemList.add(new SubcategoryChildListItem(
                        subCategoryData.getJSONObject(j).getString("name")
                        , 0
                        , j + 1
                ));
            }
            subcategoryParentListItem.setChildItemList(childItemList);
            parentListItems.add(subcategoryParentListItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private OnScrollListener ScrollListener = new OnScrollListener() {


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastCompletelyVisibleItemPosition = 0;

            if (categoryViewShared.getBoolean("isGridView", false)) {
                try {
                    lastCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } catch (Exception exception) {
                    Log.d("DEBUG", "lastCompletelyVisibleItemPosition");
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                }
            } else {
                try {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                } catch (Exception exception) {
                    lastCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                }
            }

            try {
                if (mToast != null)
                    mToast.setText((lastCompletelyVisibleItemPosition + 1) + getResources().getString(R.string.of_toast_for_no_of_item) + productTotal);
                else
                    mToast = Toast.makeText(CategoryActivity.this, lastCompletelyVisibleItemPosition + 1 + getResources().getString(R.string.of_toast_for_no_of_item) + productTotal, Toast.LENGTH_SHORT);
            } catch (NotFoundException e1) {
                e1.printStackTrace();
            }

            if (dy > 5) {
                mToast.show();
            }

            if (dy < -80 || dy > 80) {
                activity_category.notificationLayout.setVisibility(View.GONE);
            }

            try {
                if (lastCompletelyVisibleItemPosition == totalItems - 1 && totalItems < (productTotal)) {
                    if (!loading) {
                        Log.d(TAG, "onScrolled: ------------>" + "LastPostion" + lastCompletelyVisibleItemPosition +
                                "Total Items:-->" + totalItems + "ProductTotal:-->" + productTotal);
                        activity_category.listcategoryRequestBar.setVisibility(View.VISIBLE);
                        lazyLoadListenter();
                        loading = true;
                        pageNumber++;
                        if (!searchQuery.equals("") && searchQuery.length() != 0) {
                            RetrofitCallback.INSTANCE.productSearchCall(CategoryActivity.this, searchQuery, String.valueOf(pageNumber),
                                    webkul.opencart.mobikul.helper.Utils.getScreenWidth(), pageLimit, sortData[0], sortData[1],
                                    new RetrofitCustomCallback<ProductSearch>(productSearchCallbackLazy, CategoryActivity.this));
                        }
                        if (extras.containsKey("manufacturer_id")) {
                            RetrofitCallback.INSTANCE.manufactureInfoCall(CategoryActivity.this, String.valueOf(pageNumber),
                                    String.valueOf(pageLimit), webkul.opencart.mobikul.helper.Utils.getScreenWidth(), manufacturerId,
                                    sortData[0], sortData[1], new RetrofitCustomCallback<Manufacture>(manufactureCallbackLazy, CategoryActivity.this));
                        } else if (categoryId != null) {
                            RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                                    webkul.opencart.mobikul.helper.Utils.getScreenWidth(), pageLimit, sortData[0], sortData[1], getFilter(),
                                    new RetrofitCustomCallback<ProductCategory>(productCategoryCallbackLazy, CategoryActivity.this));
                        }
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    };

    private void lazyLoadListenter() {
        productCategoryCallbackLazy = new Callback<ProductCategory>() {
            @Override
            public void onResponse(Call<ProductCategory> call, Response<ProductCategory> response) {
                activity_category.listcategoryRequestBar.setVisibility(View.GONE);
                totalItems = totalItems + response.body().getCategoryData().getProducts().size();
                myAdapter.addAll(productCategoryItems(response.body().getCategoryData().getProducts()));
                myAdapter.notifyDataSetChanged();
                loading = false;
            }

            @Override
            public void onFailure(Call<ProductCategory> call, Throwable t) {

            }
        };
        productSearchCallbackLazy = new Callback<ProductSearch>() {
            @Override
            public void onResponse(Call<ProductSearch> call, Response<ProductSearch> response) {
                activity_category.listcategoryRequestBar.setVisibility(View.GONE);
                totalItems = totalItems + response.body().getProducts().size();
                myAdapter.addAll(productSearchItems(response.body().getProducts()));
                myAdapter.notifyDataSetChanged();
                loading = false;

            }

            @Override
            public void onFailure(Call<ProductSearch> call, Throwable t) {

            }
        };
        manufactureCallbackLazy = new Callback<Manufacture>() {
            @Override
            public void onResponse(Call<Manufacture> call, Response<Manufacture> response) {
                activity_category.listcategoryRequestBar.setVisibility(View.GONE);
                totalItems = totalItems + response.body().getManufacturers().getProducts().size();
                myAdapter.addAll(manufactureItems(response.body()));
                myAdapter.notifyDataSetChanged();
                loading = false;
            }

            @Override
            public void onFailure(Call<Manufacture> call, Throwable t) {

            }
        };

    }


    public void manufacturerInfoResponse(String backresult) {
        try {
            if (isInternetAvailable()) {
                Log.i(TAG, "Inserting In Database");
                mOfflineDataBaseHandler.updateIntoOfflineDB("manufacturerInfo", backresult, manufacturerId + "");
            }
            mainObject = new JSONObject(backresult);
            productTotal = mainObject.getJSONObject("manufacturers").getInt("product_total");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        TextView categoryTitle = activity_category.notificationMessage;
        myAdapter = null;
        try {
            categoryTitle.setText("Total " + productTotal + " " + getResources().getString(R.string.items_found));
            myAdapter = new MyAdapter(CategoryActivity.this, items(mainObject.getJSONObject("manufacturers").getJSONArray("products")), mMobikulApplication);
            totalItems = totalItems + mainObject.getJSONObject("manufacturers").getJSONArray("products").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(ScrollListener);
        activity_category.listCategoryContainer.setVisibility(View.VISIBLE);
        mDrawerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void datavalue(@NotNull String[] data) {
        categoryActivityHandler.setSortData(data);
        pageNumber = 1;
        totalItems = 0;
        if (data != null)
            sortData = data;
        categoryActivityHandler.setSortData(sortData);
        Log.d(TAG, "onActivityResult: " + sortData[0] + "  " + sortData[1]);
        new SweetAlertBox().showProgressDialog(CategoryActivity.this);
        activity_category.notificationLayout.setVisibility(View.VISIBLE);
        if (!searchQuery.isEmpty()) {
            RetrofitCallback.INSTANCE.productSearchCall(CategoryActivity.this, searchQuery, String.valueOf(pageNumber),
                    String.valueOf(screen_width), pageLimit, sortData[0], sortData[1],
                    new RetrofitCustomCallback<ProductSearch>(productSearchCallback, CategoryActivity.this));
        } else if (extras.containsKey("manufacturer_id")) {
            RetrofitCallback.INSTANCE.manufactureInfoCall(CategoryActivity.this, String.valueOf(pageNumber),
                    String.valueOf(pageLimit), String.valueOf(screen_width), manufacturerId, sortData[0], sortData[1],
                    new RetrofitCustomCallback<Manufacture>(manufactureCallback, CategoryActivity.this));
        } else {
            RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                    String.valueOf(screen_width), pageLimit, sortData[0], sortData[1], getFilter(),
                    new RetrofitCustomCallback<ProductCategory>(productCategoryCallback, CategoryActivity.this));
        }
    }

    class CategoryExpandableRecyclerAdapter extends ExpandableRecyclerAdapter<CategoryExpandableRecyclerAdapter.CrimeParentViewHolder,
            CategoryExpandableRecyclerAdapter.CrimeChildViewHolder> {
        LayoutInflater mInflater;
        Context ctx;

        public CategoryExpandableRecyclerAdapter(Context ctx, List<ParentListItem> parentItemList) {
            super(parentItemList);
            this.ctx = ctx;
            mInflater = LayoutInflater.from(ctx);
        }

        @Override
        public CrimeParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View view = mInflater.inflate(R.layout.item_subcategory_fragment_elv_group, parentViewGroup, false);
            return new CrimeParentViewHolder(view);
        }

        @Override
        public CrimeChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View view = mInflater.inflate(R.layout.item_subcategory_fragment_elv_child, childViewGroup, false);
            return new CrimeChildViewHolder(view);
        }

        @Override
        public void onBindParentViewHolder(final CrimeParentViewHolder parentViewHolder, final int position, ParentListItem parentListItem) {
            SubcategoryParentListItem subcategoryParentListItem = (SubcategoryParentListItem) parentListItem;
            parentViewHolder.lblListHeader.setText(webkul.opencart.mobikul.helper.Utils.fromHtml(subcategoryParentListItem.getmTitle()));
            if (position == 0) {
                parentViewHolder.lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_green_arrow, 0);
            }
            try {
                if (productCategory.getCategoryData().getProducts().size() == 0 && (productCategory.getCategoryData().getCategories().size() != 0)) {
                    parentViewHolder.setExpanded(true);
                    parentViewHolder.onExpansionToggled(false);
                    if (parentViewHolder.getParentListItemExpandCollapseListener() != null) {
                        parentViewHolder.getParentListItemExpandCollapseListener().onParentListItemExpanded(parentViewHolder.getAdapterPosition());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBindChildViewHolder(CrimeChildViewHolder childViewHolder, final int position, Object childListItem) {
            SubcategoryChildListItem subcategoryChildListItem = (SubcategoryChildListItem) childListItem;
            childViewHolder.txtListChild.setText(webkul.opencart.mobikul.helper.Utils.fromHtml(subcategoryChildListItem.getTitle()));
            childViewHolder.txtListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(ctx, mMobikulApplication.viewCategoryGrid());
                    intent.putExtra("ID", productCategory.getCategoryData().getCategories().get(position - 1).getPath());
                    intent.putExtra("CATEGORY_NAME", productCategory.getCategoryData().getCategories().get(position - 1).getName());
                    ctx.startActivity(intent);
                }
            });
        }

        public class CrimeParentViewHolder extends ParentViewHolder {

            public TextView lblListHeader;

            public CrimeParentViewHolder(View itemView) {
                super(itemView);
                lblListHeader = itemView.findViewById(R.id.lblListHeader);
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_green_arrow, 0);
            }

            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_green_arrow, 0);
                    collapseView();
                } else {
                    lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_green_arrow, 0);
                    expandView();
                }
            }
        }

        public class CrimeChildViewHolder extends ChildViewHolder {

            public TextView txtListChild;

            public CrimeChildViewHolder(View itemView) {
                super(itemView);
                txtListChild = (TextView) itemView
                        .findViewById(R.id.lblListItem);
            }
        }

    }

    List<Product> productSearchItems(List<webkul.opencart.mobikul.model.ProductSearch.Product> products) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String discount = getString(R.string.sale), model = "", instock = "";
            productList.add(new Product(products.get(i).getName(),
                    products.get(i).getThumb(), products.get(i).getPrice(), products.get(i).getDescription(),
                    products.get(i).getRating(), getString(R.string.add_to_cart), getString(R.string.add_to_wishlist), "Discount", "0", "0", "3", products.get(i).getSpecial(),
                    "0", "Model", products.get(i).getProductId(),
                    products.get(i).getHasOption(), products.get(i).getWishlistStatus(),
                    "instock", products.get(i).getFormattedSpecial(),
                    products.get(i).getDominantColor()));
        }
        return productList;
    }

    List<Product> productCategoryItems(List<webkul.opencart.mobikul.model.ProductCategory.Product> products) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String discount = getString(R.string.sale), model = "", instock = "";
            Log.d(TAG, "productCategoryItems: wishlist === " + products.get(i).getWishlist_status() + "   " + products.get(i).getHasOption());
            productList.add(new Product(products.get(i).getName(), products.get(i).getThumb(), products.get(i).getPrice(), products.get(i).getDescription(),
                    products.get(i).getRating(), getString(R.string.add_to_cart), getString(R.string.add_to_wishlist),
                    "Discount", "0", "0", "3", products.get(i).getSpecial(),
                    "0", "Model", products.get(i).getProductId(),
                    products.get(i).getHasOption(),
                    products.get(i).getWishlist_status() == null ? false : products.get(i).getWishlist_status(),
                    "instock", products.get(i).getFormattedSpecial(),
                    products.get(i).getDominantColor()));
        }
        return productList;
    }


    List<Product> manufactureItems(Manufacture manufacture) {
        List<Product> products = new ArrayList<>();
        try {
            for (int i = 0; i < manufacture.getManufacturers().getProducts().size(); i++) {
                String discount = getString(R.string.sale), model = "", instock = "";
                if (manufacture.getManufacturers().getProducts().get(i).getSpecial() != null)
                    discount = manufacture.getManufacturers().getProducts().get(i).getSpecial();
                if (manufacture.getManufacturers().getProducts().get(i).getName() != null)
                    model = manufacture.getManufacturers().getProducts().get(i).getName();
                products.add(new Product(manufacture.getManufacturers().getProducts().get(i).getName(),
                        manufacture.getManufacturers().getProducts().get(i).getThumb(),
                        manufacture.getManufacturers().getProducts().get(i).getPrice(),
                        manufacture.getManufacturers().getProducts().get(i).getDescription(),
                        manufacture.getManufacturers().getProducts().get(i).getRating()
                        , "ADD TO CART", "Add to Wishlist", discount, "0", "0", "3",
                        manufacture.getManufacturers().getProducts().get(i).getSpecial(), "0", model,
                        manufacture.getManufacturers().getProducts().get(i).getProductId(),
                        manufacture.getManufacturers().getProducts().get(i).getHasOption(),
                        manufacture.getManufacturers().getProducts().get(i).getWishlistStatus() == null ? false : manufacture.getManufacturers().getProducts().get(i).getWishlistStatus(),
                        instock,
                        manufacture.getManufacturers().getProducts().get(i).getFormattedSpecial(),
                        manufacture.getManufacturers().getProducts().get(i).getDominantColor()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    List<Product> items(JSONArray categoryData) {
        List<Product> products = new ArrayList<Product>();
        try {
            for (int i = 0; i < categoryData.length(); i++) {
                JSONObject eachProduct = categoryData.getJSONObject(i);
                Log.d("image--->", eachProduct.getString("thumb"));
                Log.d("eachProduct--->", eachProduct.toString(4));

                String discount = getString(R.string.sale), model = "", instock = "";
                if (eachProduct.has("discount"))
                    discount = eachProduct.getString("discount");
                if (eachProduct.has("model"))
                    model = eachProduct.getString("model");
                if (eachProduct.has("instock"))
                    instock = eachProduct.getString("instock");

                products.add(new Product(eachProduct.getString("name"), eachProduct.getString("thumb"),
                        eachProduct.getString("price"), eachProduct.getString("description"),
                        eachProduct.getInt("rating"), "ADD TO CART", "Add to Wishlist",
                        discount, "0", "0", "3", eachProduct.getString("special"),
                        "0", model, eachProduct.getString("product_id"), eachProduct.getBoolean("hasOption"),
                        eachProduct.getBoolean("wishlist_status"),
                        instock, eachProduct.getString("formatted_special"),
                        eachProduct.getString("dominant_color")));
                /*
                 * method add string seller string detail in all the items shown in catalog.
                 */
                addSellerData(eachProduct, products, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


    protected void addSellerData(JSONObject eachProduct, List<Product> products, int i) {

    }


    public void home(View v) {
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_SORT_BY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                pageNumber = 1;
                totalItems = 0;
                if (data != null)
                    sortData = data.getExtras().getStringArray("sortData");
                categoryActivityHandler.setSortData(sortData);
                Log.d(TAG, "onActivityResult: " + sortData[0] + "  " + sortData[1]);
                new SweetAlertBox().showProgressDialog(CategoryActivity.this);
                activity_category.notificationLayout.setVisibility(View.VISIBLE);
                if (!searchQuery.isEmpty()) {
                    RetrofitCallback.INSTANCE.productSearchCall(CategoryActivity.this, searchQuery, String.valueOf(pageNumber),
                            String.valueOf(screen_width), pageLimit, sortData[0], sortData[1],
                            new RetrofitCustomCallback<ProductSearch>(productSearchCallback, CategoryActivity.this));
                } else if (extras.containsKey("manufacturer_id")) {
                    RetrofitCallback.INSTANCE.manufactureInfoCall(CategoryActivity.this, String.valueOf(pageNumber),
                            String.valueOf(pageLimit), String.valueOf(screen_width), manufacturerId, sortData[0], sortData[1],
                            new RetrofitCustomCallback<Manufacture>(manufactureCallback, CategoryActivity.this));
                } else {
                    RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                            String.valueOf(screen_width), pageLimit, sortData[0], sortData[1], getFilter(),
                            new RetrofitCustomCallback<ProductCategory>(productCategoryCallback, CategoryActivity.this));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().ACTION_SEARCH.equals(getIntent().getAction())
                || getIntent().getExtras().containsKey("searchTerm")) {
            getMenuInflater().inflate(R.menu.search_results, menu);
            super.setMenu(menu);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            SearchView.SearchAutoComplete theTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
            theTextArea.setHintTextColor(ContextCompat.getColor(CategoryActivity.this, R.color.light_gray));
            theTextArea.setTextColor(ContextCompat.getColor(CategoryActivity.this, R.color.light_gray));
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQuery(searchQuery, false);
            searchView.clearFocus();
            searchView.setIconifiedByDefault(false);
            SharedPreferences shared = getSharedPreferences(Constant.INSTANCE.getCUSTOMER_SHARED_PREFERENCE_NAME(), MODE_PRIVATE);
            Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            String isSeller = (shared.getString("isSeller", ""));
            MenuItem loginMenuItem = menu.findItem(R.id.login);
            MenuItem signupMenuItem = menu.findItem(R.id.signup);
            menu.findItem(R.id.marketPlace).setVisible(true);
            if (isLoggedIn) {
                loginMenuItem.setTitle(getResources().getString(R.string.logout_title));
                signupMenuItem.setVisible(false);
                if (isSeller.equalsIgnoreCase("1")) {
                    menu.findItem(R.id.sellerDashboard).setVisible(true);
                    menu.findItem(R.id.sellerOrder).setVisible(true);
                }
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void applyFilter() {
        new SweetAlertBox().showProgressDialog(CategoryActivity.this);
        RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                webkul.opencart.mobikul.helper.Utils.getScreenWidth(), pageLimit, sortData[0], sortData[1], getFilter(),
                new RetrofitCustomCallback<ProductCategory>(productCategoryCallback, CategoryActivity.this));
    }

    private String getFilter() {
        StringBuilder builder = new StringBuilder();
        if (filterName.size() != 0) {
            for (Object object : filterName.toArray()) {
                builder.append(object.toString()).append(",");
            }
            return builder.toString().substring(0, builder.toString().length() - 1);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public ActivityCategoryBinding getBinding() {
        return activity_category;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AppSharedPreference.INSTANCE.getWishlistStatus(this)) {
            new SweetAlertBox().showProgressDialog(CategoryActivity.this);
            activity_category.notificationLayout.setVisibility(View.VISIBLE);
            if (!searchQuery.isEmpty()) {
                RetrofitCallback.INSTANCE.productSearchCall(CategoryActivity.this, searchQuery, String.valueOf(pageNumber),
                        String.valueOf(screen_width), pageLimit, sortData[0], sortData[1],
                        new RetrofitCustomCallback<ProductSearch>(productSearchCallback, CategoryActivity.this));
            } else if (extras.containsKey("manufacturer_id")) {
                RetrofitCallback.INSTANCE.manufactureInfoCall(CategoryActivity.this, String.valueOf(pageNumber),
                        String.valueOf(pageLimit), String.valueOf(screen_width), manufacturerId, sortData[0], sortData[1],
                        new RetrofitCustomCallback<Manufacture>(manufactureCallback, CategoryActivity.this));
            } else {
                RetrofitCallback.INSTANCE.productCategoryCall(CategoryActivity.this, categoryId, String.valueOf(pageNumber),
                        String.valueOf(screen_width), pageLimit, sortData[0], sortData[1], getFilter(),
                        new RetrofitCustomCallback<ProductCategory>(productCategoryCallback, CategoryActivity.this));
            }
            AppSharedPreference.INSTANCE.updateWishlistStatus(this, false);
        }

        if (getItemCart() != null) {
            SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
            LayerDrawable icon = (LayerDrawable) getItemCart().getIcon();
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
        }
    }
}
