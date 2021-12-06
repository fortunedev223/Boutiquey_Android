package webkul.opencart.mobikul;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.activity.ReturnOrderRequest;
import webkul.opencart.mobikul.deliveryboy.DeliveryBoy;
import webkul.opencart.mobikul.helper.Constant;
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel;
import webkul.opencart.mobikul.model.BaseModel.AddHistory;
import webkul.opencart.mobikul.model.BaseModel.BaseModel;
import webkul.opencart.mobikul.model.DashboardOrderInfo.OrderInfo;
import webkul.opencart.mobikul.model.SellerOrderModel.DataSellerHistory;
import webkul.opencart.mobikul.model.SellerOrderModel.SellerOrderHistory;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.AppSharedPreference;
import webkul.opencart.mobikul.utils.MakeToast;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.databinding.ActivityViewMyOrderBinding;
import webkul.opencart.mobikul.databinding.CommentToOrdersBinding;
import webkul.opencart.mobikul.databinding.CustomOrderViewPageBinding;
import webkul.opencart.mobikul.databinding.DeliveryBoyLayoutBinding;
import webkul.opencart.mobikul.databinding.OrderedProductsInfBinding;
import webkul.opencart.mobikul.databinding.TableRowBinding;
import webkul.opencart.mobikul.databinding.TableRowLayoutBinding;

public class ViewMyOrderActivity extends BaseActivity {
    private static final String TAG = ViewMyOrderActivity.class.getSimpleName();
    String date, orderId, status, canReorder;
    Intent intent;
    LinearLayout Container;
    private ProgressBar spinner;
    SharedPreferences configShared;
    ActivityViewMyOrderBinding orderBinding;
    private boolean isInternetAvailable;
    private Callback<webkul.opencart.mobikul.model.DashboardOrderInfo.OrderInfo> orderInfoCallback;
    public Editor editor;
    private boolean isSeller;
    private int selectedStatus = 0;
    private String selected_order_status_id;
    private CustomOrderViewPageBinding child;
    private OrderedProductsInfBinding orderedProductView;
    private ActionBar actionBar;
    private TextView title;
    private Callback<SellerOrderHistory> sellerOrderCallback;
    private Callback<AddHistory> addHistoryCallback;


    @Override
    public void onBackPressed() {
        setResult(222);
        super.onBackPressed();
    }

    public void isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable());
    }


    @Override
    protected void onResume() {
        Log.d(TAG, " onResume: in ViewMyOrderActivity ==== ");
        if (getItemCart() != null) {

            SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
            LayerDrawable icon = (LayerDrawable) getItemCart().getIcon();
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline();
        if (!isInternetAvailable) {
            showDialog(this);
        } else {
            orderBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_order);
            configShared = getSharedPreferences("configureView", MODE_PRIVATE);
            setToolbarLoginActivity(orderBinding.toolbar.findViewById(R.id.toolbar));
            setSupportActionBar(getToolbarLoginActivity());
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            orderId = getIntent().getExtras().getString("orderId");
            canReorder = getIntent().getExtras().getString("canReorder");
            if (getIntent().getExtras().containsKey("isSeller")) {
                isSeller = true;
            }
            title = (TextView) orderBinding.toolbar.findViewById(R.id.title);
            title.setText(getResources().getString(R.string.order_no) + orderId);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.order_no) + orderId);
            LinearLayout viewOrderLayout = orderBinding.viewOrderLayout;
            viewOrderLayout.setVisibility(View.GONE);
            TextView myOrderHeading = orderBinding.viewmyorderheading;
            myOrderHeading.setText(R.string.order_information);
            myOrderHeading.setTextSize(25);
            orderInfoCallback = new Callback<OrderInfo>() {
                @Override
                public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                    Container = orderBinding.viewOrderLayout;
                    child = CustomOrderViewPageBinding.inflate(getLayoutInflater());
                    Container.addView(child.getRoot());
                    TextView myOrderHeading = child.tvOrderId;
                    String order_id = "<B>" + "#" + orderId + "</B>";
                    myOrderHeading.setText(getString(R.string.order_id) + " " + Html.fromHtml(order_id));
                    TextView viewDate = child.tvOrderDate;
                    viewDate.setText(getString(R.string.placed_on) + " " + response.body().getDateAdded());
                    TextView order_status = child.orderStatus;
                    order_status.setText(getIntent().getStringExtra("status"));
                    TextView shippingMethod = child.shippingMethod;
                    if (AppSharedPreference.INSTANCE.getGdprStatus(ViewMyOrderActivity.this).equals("1")
                            && response.body().getGdprRequestStatus() == 1) {
                        orderBinding.changePasswordInfo.setVisibility(View.GONE);
                    } else {
                        orderBinding.changePasswordInfo.setVisibility(View.GONE);
                    }
                    shippingMethod.setText(response.body().getShippingMethod());
                    if (response.body().getShippingAddress() != null) {
                        TextView shippingHeading = child.tvOrderShippingaddressheading;
                        shippingHeading.setText(R.string.shipping_address_);
                        TextView shippingAddressDetails = child.tvOrderShippingaddress;
                        shippingAddressDetails.setText(Html.fromHtml(response.body().getShippingAddress()));
                    }

                    if (!TextUtils.isEmpty(response.body().getBoyId()) && response.body().getBoyName().length() > 1) {
                        DeliveryBoyLayoutBinding mDboybinding = DeliveryBoyLayoutBinding
                                .inflate(LayoutInflater.from(ViewMyOrderActivity.this));
                        Glide.with(ViewMyOrderActivity.this)
                                .load(response.body().getBoyImage())
                                .into(mDboybinding.dboyImage);
                        mDboybinding.dboyContact.setText(response.body().getBoyTelephone());
                        mDboybinding.dboyName.setText(response.body().getBoyName());
                        mDboybinding.dboyVehicle.setText(response.body().getBoyVehicle());
                        mDboybinding.dboyOtp.setText(response.body().getDeliveryBoyCode());
                        mDboybinding.track.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(
                                        ViewMyOrderActivity.this,
                                        DeliveryBoy.class);
                                intent.putExtra("id", response.body().getBoyId());
                                intent.putExtra("address", response.body().getCustomerShipping());
                                startActivity(intent);
                            }
                        });
                        child.dboy.addView(mDboybinding.getRoot());
                    }
                    TextView billingHeading = child.tvOrderBillingaddressheading;
                    billingHeading.setText(R.string.billing_address_);

                    TextView billingAddressDetails = child.tvOrderBillingaddress;
                    billingAddressDetails.setText(Html.fromHtml(response.body().getPaymentAddress() + "\n"));

                    TextView bellingMethodheading = child.tvOrderPaymentmethodheading;
                    bellingMethodheading.setText(R.string.payment_method_);

                    TextView bellingMethod = child.tvOrderPaymentmethod;
                    bellingMethod.setText(response.body().getPaymentMethod());
                    for (int i = 0; i < response.body().getProducts().size(); i++) {
                        orderedProductView = OrderedProductsInfBinding.inflate(getLayoutInflater());
                        child.productLayout.addView(orderedProductView.getRoot());
                        final TextView productName = orderedProductView.tvOrderPaymentName;
                        productName.setText(Html.fromHtml(response.body().getProducts().get(i).getName()));
                        TextView productCode = orderedProductView.model;
                        if (!response.body().getProducts().get(i).getModel().equalsIgnoreCase("")) {
                            productCode.setText(response.body().getProducts().get(i).getModel());
                        } else {
                            productCode.setVisibility(View.GONE);
                        }
                        final int finalI = i;
                        orderedProductView.returnOrder.setOnClickListener(view -> {
                            Intent intent = new Intent(ViewMyOrderActivity.this, ReturnOrderRequest.class);
                            try {
                                intent.putExtra(Constant.INSTANCE.getORDER_ID(), response.body().getOrderId());
                                intent.putExtra(Constant.INSTANCE.getPRODUCT_ID(), response.body().getProducts().get(finalI).getProductiId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        });
                        int finalI1 = i;
                        orderedProductView.reorder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Callback<AddToCartModel> callback = new Callback<AddToCartModel>() {
                                    @Override
                                    public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                                        SweetAlertBox.Companion.dissmissSweetAlertBox();
                                        if (response.body().getError() != 1) {
                                            startActivity(new Intent(ViewMyOrderActivity.this, Cart.class));
                                        }
                                        MakeToast.Companion.getInstance().shortToast(ViewMyOrderActivity.this, response.body().getMessage());
                                    }

                                    @Override
                                    public void onFailure(Call<AddToCartModel> call, Throwable t) {
                                        SweetAlertBox.Companion.dissmissSweetAlertBox();
                                    }
                                };
                                SweetAlertBox.Companion.getInstance().showProgressDialog(ViewMyOrderActivity.this);
                                RetrofitCallback.INSTANCE.reOrderProduct(ViewMyOrderActivity.this,
                                        response.body().getOrderId(),
                                        response.body().getProducts().get(finalI1).getOrder_product_id(),
                                        new RetrofitCustomCallback(callback, ViewMyOrderActivity.this));
                            }
                        });

                        TextView productPrice = orderedProductView.tvOrderProductPrice;
                        productPrice.setText(response.body().getProducts().get(i).getPrice());

                        TextView bellingQty = orderedProductView.tvOrderProductQty;
                        bellingQty.setText(response.body().getProducts().get(i).getQuantity());

                        TextView bellingSubTotal = orderedProductView.tvOrderProductSubtotal;
                        bellingSubTotal.setText(response.body().getProducts().get(i).getTotal());
//                        orderedProductView.arrowImage.setVisibility(View.GONE);
                    }

                    TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

                    if (response.body().getTotals().size() != 0 && response.body().getTotals().get(0).getTitle() != null)
                        for (int i = 0; i < response.body().getTotals().size(); i++) {
                            TableRowBinding childTotal = TableRowBinding.inflate(getLayoutInflater());
                            if (i == response.body().getTotals().size() - 1) {
                                TextView totalTextHeading = childTotal.heading;
                                totalTextHeading.setText(response.body().getTotals().get(i).getTitle());
                                totalTextHeading.setTypeface(null, Typeface.BOLD);
                                TextView totalTextValue = childTotal.value;
                                totalTextValue.setText(response.body().getTotals().get(i).getText());
                                totalTextHeading.setTextColor(ContextCompat.getColor(ViewMyOrderActivity.this, R.color.black));
                                totalTextValue.setTextColor(ContextCompat.getColor(ViewMyOrderActivity.this, R.color.black));
                                totalTextValue.setTypeface(null, Typeface.BOLD);
                                totalTextValue.setPadding(10, 0, 0, 0);
                                View line = new View(ViewMyOrderActivity.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                                line.setLayoutParams(params);
                                line.setBackgroundColor(Color.parseColor("#CCCCCC"));
                                child.tableLayout.addView(line);
                                child.tableLayout.addView(childTotal.getRoot());
                            } else {
                                childTotal.getRoot().setPadding(0, 5, 0, 10);
                                child.tableLayout.addView(childTotal.getRoot());
                                TextView totalTextHeading = childTotal.heading;
                                totalTextHeading.setText(response.body().getTotals().get(i).getTitle());
                                totalTextHeading.setTypeface(null, Typeface.BOLD);
                                TextView totalTextValue = childTotal.value;
                                totalTextValue.setText(response.body().getTotals().get(i).getText().toString());
                                totalTextValue.setPadding(10, 0, 0, 0);
                            }
                        }


                    TableLayout tableLayoutForOrderHistory = new TableLayout(ViewMyOrderActivity.this);
                    tableLayoutForOrderHistory.setLayoutParams(tableParams);
                    tableLayoutForOrderHistory.setBackgroundColor(Color.parseColor("#EEEEEE"));

                    TableRowLayoutBinding childTotal = TableRowLayoutBinding.inflate(getLayoutInflater());
                    tableLayoutForOrderHistory.addView(childTotal.getRoot());

                    TextView dateValueHeading = childTotal.tableData1;
                    dateValueHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
                    dateValueHeading.setText(R.string.date_added);
                    dateValueHeading.setTypeface(null, Typeface.BOLD);

                    TextView orderStatusHeading = childTotal.tableData2;
                    orderStatusHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
                    orderStatusHeading.setText(R.string.order_status);
                    orderStatusHeading.setTypeface(null, Typeface.BOLD);

                    TextView CommentHeading = childTotal.tableData3;
                    CommentHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                    CommentHeading.setText(R.string.comment);
                    CommentHeading.setTypeface(null, Typeface.BOLD);

                    for (int i = 0; i < response.body().getHistories().size(); i++) {
                        TableRowLayoutBinding child1 = TableRowLayoutBinding.inflate(getLayoutInflater());
                        tableLayoutForOrderHistory.addView(child1.getRoot());
                        TextView dateValue = child1.tableData1;
                        dateValue.setText(response.body().getHistories().get(i).getDateAdded());

                        dateValue.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
                        TextView orderStatus = child1.tableData2;
                        orderStatus.setText(response.body().getHistories().get(i).getStatus());
                        orderStatus.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));

                        TextView Comment = child1.tableData3;
                        Comment.setText(Html.fromHtml(response.body().getHistories().get(i).getComment()));
                        Comment.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                    }

                    spinner = orderBinding.myproreviewprogress;
                    spinner.setVisibility(View.GONE);
                    Container.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<OrderInfo> call, Throwable t) {

                }
            };
            callApi();
        }
    }

    public void callApi() {
        JSONObject jo = new JSONObject();
        setShared(getSharedPreferences("customerData", MODE_PRIVATE));

        sellerOrderCallback = new Callback<SellerOrderHistory>() {
            @Override
            public void onResponse(Call<SellerOrderHistory> call, Response<SellerOrderHistory> response) {
                Log.d(TAG, "callApi onResponse: " + response.body().getError());
                SweetAlertBox.Companion.dissmissSweetAlertBox();
                viewDetails(response.body());
            }

            @Override
            public void onFailure(Call<SellerOrderHistory> call, Throwable t) {

            }
        };
        Log.d(TAG, "callApi: in seller outer " + !isSeller);
        try {
            if (!isSeller) {
                Log.d(TAG, "callApi: in seller if ");
                jo.put("order_id", orderId);
//                new VolleyConnection(this).getResponse(Request.Method.POST, "getOrderInfo", jo.toString());
                RetrofitCallback.INSTANCE.dashboardOrderInfoCall(this, orderId, orderInfoCallback);
            } else {
                Log.d(TAG, "callApi: in seller ELSE ");
                jo.put("id", orderId);
                RetrofitCallback.INSTANCE.getSellerOrderNewCall(ViewMyOrderActivity.this, orderId, new RetrofitCustomCallback(sellerOrderCallback, ViewMyOrderActivity.this));
//                new VolleyConnection(this).getResponse(Request.Method.POST, "getSellerOrders", jo.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void line() {
        View v = new View(ViewMyOrderActivity.this);
        v.setBackgroundColor(Color.parseColor("#636363"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(0, 10, 0, 20);
        v.setLayoutParams(layoutParams);
        Container.addView(v);
    }

    void lineLight() {
        View v = new View(ViewMyOrderActivity.this);
        v.setBackgroundColor(Color.parseColor("#B6B6B6"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        layoutParams.setMargins(0, 10, 0, 10);
        v.setLayoutParams(layoutParams);
        Container.addView(v);

    }


    public void getSellerOrdersResponse(String backresult) {
        Log.d(TAG, "getSellerOrdersResponse: " + backresult);
        try {
            JSONObject jo = new JSONObject(backresult);
            setResponseObject(jo.getJSONObject("data"));
            viewDetails();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getOrderInfoResponse(String backresult) {
        try {
            Log.d(TAG, "getOrderInfoResponse: " + backresult);
            setResponseObject(new JSONObject(backresult));
            viewDetails();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void viewDetails(SellerOrderHistory sellerOrder) {
        try {
            setResponseObject(new JSONObject(new Gson().toJson(sellerOrder.getData(), DataSellerHistory.class)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Container = orderBinding.viewOrderLayout;
            child = CustomOrderViewPageBinding.inflate(getLayoutInflater());
            Container.addView(child.getRoot());
            TextView myOrderHeading = child.tvOrderId;
            String order_id = "<B>" + "#" + orderId + "</B>";
            myOrderHeading.setText(getString(R.string.order_id) + " " + Html.fromHtml(order_id));
            TextView viewDate = child.tvOrderDate;
            viewDate.setText(getString(R.string.placed_on) + " " + sellerOrder.getData().getDateAdded());
            TextView order_status = child.orderStatus;
            if (sellerOrder.getData().getOrderStatus() != null) {
                order_status.setText(sellerOrder.getData().getOrderStatus());
            }
//            if (getResponseObject().has("histories")) {
//                if (getResponseObject().getJSONArray("histories").length() != 0) {
//                    order_status.setText(getResponseObject().getJSONArray("histories").getJSONObject(0).getString("status"));
//                }
//            }
            TextView shipping_method = child.shippingMethod;
            shipping_method.setText(sellerOrder.getData().getShippingMethod());
            if (sellerOrder.getData().getShippingAddress() != null) {
                TextView shippingHeading = child.tvOrderShippingaddressheading;
                shippingHeading.setText(R.string.shipping_address_);
                TextView shippingAddressDetails = child.tvOrderShippingaddress;
                shippingAddressDetails.setText(Html.fromHtml(sellerOrder.getData().getShippingAddress()));

            }

            TextView billingHeading = child.tvOrderBillingaddressheading;
            billingHeading.setText(R.string.billing_address_);

            TextView billingAddressDetails = child.tvOrderBillingaddress;
            billingAddressDetails.setText(Html.fromHtml(sellerOrder.getData().getPaymentAddress() + "\n"));

            TextView bellingMethodheading = child.tvOrderPaymentmethodheading;
            bellingMethodheading.setText(R.string.payment_method_);

            TextView bellingMethod = child.tvOrderPaymentmethod;
            bellingMethod.setText(sellerOrder.getData().getPaymentMethod());

            for (int i = 0; i < sellerOrder.getData().getProducts().size(); i++) {
                orderedProductView = OrderedProductsInfBinding.inflate(getLayoutInflater());
                child.productLayout.addView(orderedProductView.getRoot());

                final TextView productName = orderedProductView.tvOrderPaymentName;
                orderedProductView.returnOrder.setVisibility(View.GONE);
//                Log.d(TAG, " viewDetails: Product name ==== " + sellerOrder.getData().getProducts().get(i).getName());
                if (sellerOrder.getData().getProducts().get(i).getName() != null)
                    productName.setText(Html.fromHtml(sellerOrder.getData().getProducts().get(i).getName()));
                productName.setTag(i);
                int finalI = i;
                orderedProductView.returnOrder.setOnClickListener(view -> {
                    Intent intent = new Intent(ViewMyOrderActivity.this, ReturnOrderRequest.class);
                    try {
                        intent.putExtra(Constant.INSTANCE.getORDER_ID(), sellerOrder.getData().getTextOrderId());
                        intent.putExtra(Constant.INSTANCE.getPRODUCT_ID(), sellerOrder.getData().getProducts().get(finalI).getProductId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                });

                int finalI1 = i;
                orderedProductView.reorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Callback<BaseModel> callback = new Callback<BaseModel>() {
                            @Override
                            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {

                            }

                            @Override
                            public void onFailure(Call<BaseModel> call, Throwable t) {

                            }
                        };
                        RetrofitCallback.INSTANCE.reOrderProduct(ViewMyOrderActivity.this, sellerOrder.getData().getTextOrderId(),
                                sellerOrder.getData().getProducts().get(finalI1).getOrderProductId(),
                                new RetrofitCustomCallback(callback, ViewMyOrderActivity.this));
                    }
                });

                productName.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            int index = (Integer) v.getTag();
                            Intent intent = new Intent(ViewMyOrderActivity.this, ViewProductSimple.class);
                            intent.putExtra("idOfProduct", sellerOrder.getData().getProducts().get(index).getProductId());
                            intent.putExtra("nameOfProduct", sellerOrder.getData().getProducts().get(index).getName());
                            startActivity(intent);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

                TextView productCode = orderedProductView.model;
                if (sellerOrder.getData().getProducts().get(i).getModel() != null
                        && !sellerOrder.getData().getProducts().get(i).getModel().equalsIgnoreCase("")) {
                    productCode.setText(sellerOrder.getData().getProducts().get(i).getModel().toString());
                } else {
                    productCode.setVisibility(View.GONE);
                }

                TextView productPrice = orderedProductView.tvOrderProductPrice;
                productPrice.setText(sellerOrder.getData().getProducts().get(i).getPrice());

                TextView bellingQty = orderedProductView.tvOrderProductQty;
                bellingQty.setText(sellerOrder.getData().getProducts().get(i).getQuantity());

                TextView bellingSubTotal = orderedProductView.tvOrderProductSubtotal;
                bellingSubTotal.setText(sellerOrder.getData().getProducts().get(i).getTotal());
            }


            TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            if (sellerOrder.getData().getTotals().size() != 0 /*&& totals.getJSONObject(0).has("title")*/)
                for (int i = 0; i < sellerOrder.getData().getTotals().size(); i++) {
                    TableRowBinding childTotal = TableRowBinding.inflate(getLayoutInflater());
                    if (i == sellerOrder.getData().getTotals().size() - 1) {
                        TextView totalTextHeading = childTotal.heading;
                        totalTextHeading.setText(sellerOrder.getData().getTotals().get(i).getTitle().toString());
                        totalTextHeading.setTypeface(null, Typeface.BOLD);
                        TextView totalTextValue = childTotal.value;
                        totalTextValue.setText(sellerOrder.getData().getTotals().get(i).getText().toString());
                        totalTextValue.setPadding(10, 0, 0, 0);
                        View line = new View(ViewMyOrderActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                        line.setLayoutParams(params);
                        line.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        child.tableLayout.addView(line);
                        child.tableLayout.addView(childTotal.getRoot());
                    } else {
                        childTotal.getRoot().setPadding(0, 5, 0, 10);
                        child.tableLayout.addView(childTotal.getRoot());
                        TextView totalTextHeading = childTotal.heading;
                        totalTextHeading.setText(sellerOrder.getData().getTotals().get(i).getTitle().toString());
                        totalTextHeading.setTypeface(null, Typeface.BOLD);
                        TextView totalTextValue = childTotal.value;
                        totalTextValue.setText(sellerOrder.getData().getTotals().get(i).getText().toString());
                        totalTextValue.setPadding(10, 0, 0, 0);
                    }
                }

            if (isSeller) {
                final CommentToOrdersBinding commentToAdmin = CommentToOrdersBinding.inflate(getLayoutInflater());
                Container.addView(commentToAdmin.getRoot());
                commentToAdmin.getRoot().setPadding(0, 10, 0, 0);
                final String[] status = new String[sellerOrder.getData().getMarketplaceOrderStatusSequence().size()];
                for (int i = 0; i < status.length; i++) {
                    status[i] = sellerOrder.getData().getMarketplaceOrderStatusSequence().get(i).getName();
                    Log.d("status", status[i] + "");
                    String code = sellerOrder.getData().getMarketplaceOrderStatusSequence().get(i).getOrderStatusId();
                    if (code.equalsIgnoreCase(sellerOrder.getData().getOrderStatusId())) {
                        selectedStatus = i;
                    }
                }
                Spinner dropdown = commentToAdmin.statusSpinner;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMyOrderActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, status);
                dropdown.setAdapter(adapter);
                dropdown.setSelection(selectedStatus);
                final Button addHistory = commentToAdmin.addHistory;
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position < selectedStatus) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMyOrderActivity.this,
                                    R.style.AlertDialogTheme);
                            builder.setTitle("Warning");
                            builder.setMessage(getResources().getString(R.string.cant_change_status)).setPositiveButton(getResources().getString(android.R.string.ok), dialogClickListener)
                                    .show();
                            addHistory.setClickable(false);
                            addHistory.setBackgroundColor(getResources().getColor(R.color.light_gray_color));
                            addHistory.setTextColor(Color.parseColor("#CCCCCC"));
                        } else {
                            try {
                                selected_order_status_id = sellerOrder.getData().getMarketplaceOrderStatusSequence().get(position).getOrderStatusId();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            addHistory.setClickable(true);
                            addHistory.setBackgroundColor(getResources().getColor(R.color.dark_primary_color));
                            addHistory.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                addHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String notifyAdmin, notify;
                            JSONObject jo = new JSONObject();
                            jo.put("order_id", orderId);
                            jo.put("order_status_id", selected_order_status_id);
                            if (commentToAdmin.notifyAdmin.isChecked()) {
                                jo.put("notifyAdmin", 1);
                                notifyAdmin = "1";
                            } else {
                                jo.put("notifyAdmin", 0);
                                notifyAdmin = "0";
                            }
                            if (commentToAdmin.notifyCustomer.isChecked()) {
                                jo.put("notify", 1);
                                notify = "1";
                            } else {
                                jo.put("notify", 0);
                                notify = "0";
                            }
                            Log.d(TAG, "onClick: from that ");
                            jo.put("comment", commentToAdmin.editText.getText().toString());
                            addHistoryCallback = new Callback<AddHistory>() {
                                @Override
                                public void onResponse(Call<AddHistory> call, Response<AddHistory> response) {
                                    SweetAlertBox.Companion.getSweetAlertDialog().dismiss();
                                    addHistoryResponse(response.body());
                                }

                                @Override
                                public void onFailure(Call<AddHistory> call, Throwable t) {

                                }
                            };
                            new SweetAlertBox().showProgressDialog(ViewMyOrderActivity.this);
                            RetrofitCallback.INSTANCE.addHistory(ViewMyOrderActivity.this, orderId,
                                    selected_order_status_id, notifyAdmin, notify, commentToAdmin.editText.getText().toString(),
                                    new RetrofitCustomCallback<>(addHistoryCallback, ViewMyOrderActivity.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
//					---------------- Reorder button and return button ----------------
            TableLayout tableLayoutForOrderHistory = new TableLayout(ViewMyOrderActivity.this);
            tableLayoutForOrderHistory.setLayoutParams(tableParams);
            tableLayoutForOrderHistory.setBackgroundColor(Color.parseColor("#EEEEEE"));
            if (getResponseObject().has("histories")) {
                JSONArray histories = getResponseObject().getJSONArray("histories");
                for (int i = 0; i < histories.length(); i++) {
                    TableRowLayoutBinding child1 = TableRowLayoutBinding.inflate(getLayoutInflater());
                    tableLayoutForOrderHistory.addView(child1.getRoot());
                    TextView dateValue = child1.tableData1;
                    dateValue.setText(histories.getJSONObject(i).getString("date_added"));

                    dateValue.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
                    TextView orderStatus = child1.tableData2;
                    orderStatus.setText(histories.getJSONObject(i).getString("status"));
                    orderStatus.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));

                    TextView Comment = child1.tableData3;
                    Comment.setText(Html.fromHtml(histories.getJSONObject(i).getString("comment")));
                    Comment.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                }
            }

            TableRowLayoutBinding childTotal = TableRowLayoutBinding.inflate(getLayoutInflater());
            tableLayoutForOrderHistory.addView(childTotal.getRoot());

            TextView dateValueHeading = childTotal.tableData1;
            dateValueHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
            dateValueHeading.setText(R.string.date_added);
            dateValueHeading.setTypeface(null, Typeface.BOLD);

            TextView orderStatusHeading = childTotal.tableData2;
            orderStatusHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
            orderStatusHeading.setText(R.string.order_status);
            orderStatusHeading.setTypeface(null, Typeface.BOLD);

            TextView CommentHeading = childTotal.tableData3;
            CommentHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
            CommentHeading.setText(R.string.comment);
            CommentHeading.setTypeface(null, Typeface.BOLD);
//            ((CardView) child.cardViewHistory).addView(tableLayoutForOrderHistory);
            spinner = orderBinding.myproreviewprogress;
            spinner.setVisibility(View.GONE);
            Container.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("ExceptionPost", e.toString());
            e.printStackTrace();
        }
    }

    private void viewDetails() {
        try {
            Container = orderBinding.viewOrderLayout;
            child = CustomOrderViewPageBinding.inflate(getLayoutInflater());
            Container.addView(child.getRoot());
            TextView myOrderHeading = child.tvOrderId;
            String order_id = "<B>" + "#" + orderId + "</B>";
            myOrderHeading.setText(getString(R.string.order_id) + " " + Html.fromHtml(order_id));
            TextView viewDate = child.tvOrderDate;
            viewDate.setText(getString(R.string.placed_on) + " " + getResponseObject().getString("date_added"));
            TextView order_status = child.orderStatus;
            order_status.setText(getIntent().getStringExtra("status"));
//            if (getResponseObject().has("histories")) {
//                if (getResponseObject().getJSONArray("histories").length() != 0) {
//                    order_status.setText(getResponseObject().getJSONArray("histories").getJSONObject(0).getString("status"));
//                }
//            }
            TextView shipping_method = child.shippingMethod;
            shipping_method.setText(getResponseObject().getString("shipping_method"));
            if (getResponseObject().has("shipping_address")) {
                TextView shippingHeading = child.tvOrderShippingaddressheading;
                shippingHeading.setText(R.string.shipping_address_);
                TextView shippingAddressDetails = child.tvOrderShippingaddress;
                shippingAddressDetails.setText(Html.fromHtml(getResponseObject().getString("shipping_address")));
            }

            TextView billingHeading = child.tvOrderBillingaddressheading;
            billingHeading.setText(R.string.billing_address_);

            TextView billingAddressDetails = child.tvOrderBillingaddress;
            billingAddressDetails.setText(Html.fromHtml(getResponseObject().getString("payment_address") + "\n"));

            TextView bellingMethodheading = child.tvOrderPaymentmethodheading;
            bellingMethodheading.setText(R.string.payment_method_);

            TextView bellingMethod = child.tvOrderPaymentmethod;
            bellingMethod.setText(getResponseObject().getString("payment_method"));

            for (int i = 0; i < getResponseObject().getJSONArray("products").length(); i++) {

                orderedProductView = OrderedProductsInfBinding.inflate(getLayoutInflater());
                child.productLayout.addView(orderedProductView.getRoot());
//                orderedProductView.arrowImage.setVisibility(View.GONE);
                final TextView productName = orderedProductView.tvOrderPaymentName;
                productName.setText(Html.fromHtml(getResponseObject().getJSONArray("products").getJSONObject(i).getString("name")));
                productName.setTag(i);
                int finalI = i;
                orderedProductView.returnOrder.setOnClickListener(view -> {
                    Intent intent = new Intent(ViewMyOrderActivity.this, ReturnOrderRequest.class);
                    try {
                        intent.putExtra(Constant.INSTANCE.getORDER_ID(), getResponseObject().getString("order_id"));
                        intent.putExtra(Constant.INSTANCE.getPRODUCT_ID(), getResponseObject().getJSONArray("products")
                                .getJSONObject(finalI).getString("product_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                });

                productName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int index = (Integer) v.getTag();
                            Intent intent = new Intent(ViewMyOrderActivity.this, ViewProductSimple.class);
                            intent.putExtra("idOfProduct", getResponseObject().getJSONArray("products").getJSONObject(index).getString("product_id"));
                            intent.putExtra("nameOfProduct", getResponseObject().getJSONArray("products").getJSONObject(index).getString("name"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

                TextView productCode = orderedProductView.model;
                if (!getResponseObject().getJSONArray("products").getJSONObject(i).getString("model").equalsIgnoreCase("")) {
                    productCode.setText(getResponseObject().getJSONArray("products").getJSONObject(i).getString("model").toString());
                } else {
                    productCode.setVisibility(View.GONE);
                }

                TextView productPrice = orderedProductView.tvOrderProductPrice;
                productPrice.setText(getResponseObject().getJSONArray("products").getJSONObject(i).getString("price"));

                TextView bellingQty = orderedProductView.tvOrderProductQty;
                bellingQty.setText(getResponseObject().getJSONArray("products").getJSONObject(i).getString("quantity"));

                TextView bellingSubTotal = orderedProductView.tvOrderProductSubtotal;
                bellingSubTotal.setText(getResponseObject().getJSONArray("products").getJSONObject(i).getString("total"));
            }


            TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            JSONArray totals = getResponseObject().getJSONArray("totals");
            if (totals.length() != 0 && totals.getJSONObject(0).has("title"))
                for (int i = 0; i < totals.length(); i++) {
                    TableRowBinding childTotal = TableRowBinding.inflate(getLayoutInflater());
                    if (i == totals.length() - 1) {
                        TextView totalTextHeading = childTotal.heading;
                        totalTextHeading.setText(totals.getJSONObject(i).getString("title").toString());
                        totalTextHeading.setTypeface(null, Typeface.BOLD);
                        TextView totalTextValue = childTotal.value;
                        totalTextValue.setText(totals.getJSONObject(i).getString("text").toString());
                        totalTextValue.setPadding(10, 0, 0, 0);
                        View line = new View(ViewMyOrderActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                        line.setLayoutParams(params);
                        line.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        child.tableLayout.addView(line);
                        child.tableLayout.addView(childTotal.getRoot());
                    } else {
                        childTotal.getRoot().setPadding(0, 5, 0, 10);
                        child.tableLayout.addView(childTotal.getRoot());
                        TextView totalTextHeading = childTotal.heading;
                        totalTextHeading.setText(totals.getJSONObject(i).getString("title").toString());
                        totalTextHeading.setTypeface(null, Typeface.BOLD);
                        TextView totalTextValue = childTotal.value;
                        totalTextValue.setText(totals.getJSONObject(i).getString("text").toString());
                        totalTextValue.setPadding(10, 0, 0, 0);
                    }
                }

            if (isSeller) {
                final CommentToOrdersBinding commentToAdmin = CommentToOrdersBinding.inflate(getLayoutInflater());
                Container.addView(commentToAdmin.getRoot());
                commentToAdmin.getRoot().setPadding(0, 10, 0, 0);
                JSONArray statusArray = getResponseObject().getJSONArray("marketplace_order_status_sequence");

                final String[] status = new String[statusArray.length()];
                for (int i = 0; i < status.length; i++) {
                    JSONObject statusName = statusArray.getJSONObject(i);
                    status[i] = statusName.getString("name");
                    Log.d("status", status[i] + "");
                    String code = statusName.getString("order_status_id");
                    if (code.equalsIgnoreCase(getResponseObject().getString("order_status_id"))) {
                        selectedStatus = i;
                    }
                }

                Spinner dropdown = commentToAdmin.statusSpinner;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewMyOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, status);
                dropdown.setAdapter(adapter);
                dropdown.setSelection(selectedStatus);
                final Button addHistory = commentToAdmin.addHistory;

                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position < selectedStatus) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewMyOrderActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("Warning");
                            builder.setMessage(getResources().getString(R.string.cant_change_status)).setPositiveButton(getResources().getString(android.R.string.ok), dialogClickListener)
                                    .show();

                            addHistory.setClickable(false);
                            addHistory.setBackgroundColor(getResources().getColor(R.color.light_gray_color));
                            addHistory.setTextColor(Color.parseColor("#CCCCCC"));
                        } else {
                            try {
                                selected_order_status_id = getResponseObject().getJSONArray("marketplace_order_status_sequence").getJSONObject(position).getString("order_status_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            addHistory.setClickable(true);
                            addHistory.setBackgroundColor(getResources().getColor(R.color.dark_primary_color));
                            addHistory.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                addHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String notifyAdmin, notify;
                            JSONObject jo = new JSONObject();
                            jo.put("order_id", orderId);
                            jo.put("order_status_id", selected_order_status_id);
                            if (commentToAdmin.notifyAdmin.isChecked()) {
                                jo.put("notifyAdmin", 1);
                                notifyAdmin = "1";
                            } else {
                                jo.put("notifyAdmin", 0);
                                notifyAdmin = "0";
                            }


                            if (commentToAdmin.notifyCustomer.isChecked()) {
                                jo.put("notify", 1);
                                notify = "1";
                            } else {
                                jo.put("notify", 0);
                                notify = "0";
                            }

                            jo.put("comment", commentToAdmin.editText.getText().toString());
                            Log.d(TAG, "onClick: from this ");
//                            new VolleyConnection(ViewMyOrderActivity.this).getResponse(Request.Method.POST, "addHistory", jo.toString());
//                            new MobikulMakeConnection(ViewMyOrderActivity.this).execute("addHistory", jo.toString());
                            addHistoryCallback = new Callback<AddHistory>() {
                                @Override
                                public void onResponse(Call<AddHistory> call, Response<AddHistory> response) {
//                                    Log.d(TAG, "onResponse: " + response.body().transactionsTotal);
                                    if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                                        SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                                    }
                                    addHistoryResponse(response.body());
                                }

                                @Override
                                public void onFailure(Call<AddHistory> call, Throwable t) {

                                }
                            };
                            new SweetAlertBox().showProgressDialog(ViewMyOrderActivity.this);
                            RetrofitCallback.INSTANCE.addHistory(ViewMyOrderActivity.this, orderId, selected_order_status_id, notifyAdmin, notify, commentToAdmin.editText.getText().toString(), new RetrofitCustomCallback<>(addHistoryCallback, ViewMyOrderActivity.this));
//                            progress = ProgressDialog.show(ViewMyOrderActivity.this, getResources().getString(webkul.opencart.mobikul.R.string.please_wait), getResources().getString(webkul.opencart.mobikul.R.string.processing_request_response), true);
//                            progress.setCanceledOnTouchOutside(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
//					---------------- Reorder button and return button ----------------

            TableLayout tableLayoutForOrderHistory = new TableLayout(ViewMyOrderActivity.this);
            tableLayoutForOrderHistory.setLayoutParams(tableParams);
            tableLayoutForOrderHistory.setBackgroundColor(Color.parseColor("#EEEEEE"));
            if (getResponseObject().has("histories")) {
                JSONArray histories = getResponseObject().getJSONArray("histories");
                for (int i = 0; i < histories.length(); i++) {
                    TableRowLayoutBinding child1 = TableRowLayoutBinding.inflate(getLayoutInflater());
                    tableLayoutForOrderHistory.addView(child1.getRoot());
                    TextView dateValue = child1.tableData1;
                    dateValue.setText(histories.getJSONObject(i).getString("date_added"));

                    dateValue.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
                    TextView orderStatus = child1.tableData2;
                    orderStatus.setText(histories.getJSONObject(i).getString("status"));
                    orderStatus.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));

                    TextView Comment = child1.tableData3;
                    Comment.setText(Html.fromHtml(histories.getJSONObject(i).getString("comment")));
                    Comment.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                }
            }

            TableRowLayoutBinding childTotal = TableRowLayoutBinding.inflate(getLayoutInflater());
            tableLayoutForOrderHistory.addView(childTotal.getRoot());

            TextView dateValueHeading = childTotal.tableData1;
            dateValueHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
            dateValueHeading.setText(R.string.date_added);
            dateValueHeading.setTypeface(null, Typeface.BOLD);

            TextView orderStatusHeading = childTotal.tableData2;
            orderStatusHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (int) .5));
            orderStatusHeading.setText(R.string.order_status);
            orderStatusHeading.setTypeface(null, Typeface.BOLD);

            TextView CommentHeading = childTotal.tableData3;
            CommentHeading.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
            CommentHeading.setText(R.string.comment);
            CommentHeading.setTypeface(null, Typeface.BOLD);

            spinner = orderBinding.myproreviewprogress;
            spinner.setVisibility(View.GONE);
            Container.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("ExceptionPost", e.toString());
            e.printStackTrace();
        }
    }

    public void addHistoryResponse(AddHistory backresult) {
        if (backresult.getError() == 0) {
            Toast.makeText(getApplicationContext(), backresult.getData(), Toast.LENGTH_LONG).show();
            recreate();
        } else if (backresult.getError() == 1) {
            Toast.makeText(getApplicationContext(), backresult.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
