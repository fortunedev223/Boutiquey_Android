package webkul.opencart.mobikul;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webkul.opencart.mobikul.helper.*;
import webkul.opencart.mobikul.model.ViewNotificationModel.ViewNotification;
import webkul.opencart.mobikul.networkManager.RetrofitCallback;
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback;
import webkul.opencart.mobikul.utils.SweetAlertBox;
import webkul.opencart.mobikul.analytics.MobikulApplication;
import webkul.opencart.mobikul.databinding.ActivityNotificationBinding;
import webkul.opencart.mobikul.databinding.ItemNotificationBinding;

import static android.content.Context.MODE_PRIVATE;

public class NotificationActivity extends Fragment {
    HashSet<String> unreadNotifications;
    String notificationId;
    Object response = null, mainResponse = null;
    private ProgressBar spinner;
    LinearLayout notificationLayout;
    SharedPreferences notificationShared;
    SharedPreferences drawerData;
    public Editor editor;
    private MobikulApplication mMobikulApplication;
    JSONObject drawerDataObject;
    ActivityNotificationBinding notificationBinding;
    private Callback<ViewNotification> notificationCallback;

    public Boolean checkConn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return !(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        notificationBinding = ActivityNotificationBinding.inflate(inflater, container, false);
        mMobikulApplication = (MobikulApplication) getActivity().getApplication();
        notificationCallback = new Callback<ViewNotification>() {
            @Override
            public void onResponse(Call<ViewNotification> call, final Response<ViewNotification> response) {
                if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                    SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                }
                spinner = notificationBinding.mainProgressBar;
                spinner.setVisibility(View.GONE);
                notificationLayout = notificationBinding.notificationLayout;
                notificationLayout.setVisibility(View.VISIBLE);
                if (!(response.body().getNotifications().size() == 0) && isAdded()) {
                    LinearLayout notificationContainer = notificationBinding.notificationContainer;
                    notificationShared = getActivity().getSharedPreferences("com.webkul.mobikul.notification", Context.MODE_MULTI_PROCESS);
                    drawerData = getActivity().getSharedPreferences("drawerData", MODE_PRIVATE);
                    String drawerString = drawerData.getString("drawerData", "");
                    unreadNotifications = (HashSet<String>) notificationShared.getStringSet("unreadNotifications", new HashSet<String>());
                    for (int i = 0; i < response.body().getNotifications().size(); i++) {
                        ItemNotificationBinding child = ItemNotificationBinding.inflate(inflater);
                        notificationContainer.addView(child.getRoot());
                        notificationId = response.body().getNotifications().get(i).getNotificationId();
                        notificationShared = getActivity().getSharedPreferences("com.webkul.mobikul.notification", Context.MODE_MULTI_PROCESS);
                        ImageView notificationImage = child.notificationImage;
                        notificationImage.setLayoutParams(new FrameLayout.LayoutParams(
                                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2));
                        LinearLayout notificationPanel = child.notificationPanel;
                        if (unreadNotifications.contains(notificationId)) {
                            child.newNotificationTitle.setVisibility(View.VISIBLE);
                            child.notificationPanel.setBackgroundColor(Color.parseColor("#ff0097a7"));
                            ((View) child.notificationPanel.getParent()).setPadding(1, 1, 1, 1);
                            notificationPanel.setTag(i + "/" + notificationId + "/" + "containnew");
                        } else {
                            notificationPanel.setTag(i + "/");
                        }
                        if (notificationShared.contains("unreadNotifications")) {
                            Editor editor = notificationShared.edit();
                            editor.remove("unreadNotifications");
                            editor.apply();
                        }
                        Glide.with(getActivity())
                                .load(response.body().getNotifications().get(i).getImage())
                                .into(notificationImage);

                        if (response.body().getNotifications().get(i).getType().equalsIgnoreCase("other")) {
//                            notificationImage.setImageResource(R.drawable.ic_notify);

                            notificationPanel.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    String tag = (String) v.getTag();
                                    final String[] tagArr = tag.split("/");
                                    int index = Integer.parseInt(tagArr[0]);
                                    Intent intent;
                                    intent = new Intent(getActivity(), otherActivty.class);
                                    intent.putExtra("title", response.body().getNotifications().get(index).getTitle());
                                    intent.putExtra("shortDiscription", response.body().getNotifications().get(index).getContent());
                                    startActivityForResult(intent, 0);
                                    Log.d("tag", tag + "");

                                    if (tag.contains("containnew")) {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removeNewPanel(v, tagArr[1]);
                                            }
                                        }, 1000);
                                    }
                                }
                            });
                        } else if (response.body().getNotifications().get(i).getType().equalsIgnoreCase("category")) {
                            notificationImage.setLayoutParams(new FrameLayout.LayoutParams(
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2));
                            Glide.with(getActivity())
                                    .load(response.body().getNotifications().get(i).getImage())
                                    .into(notificationImage);
//                            notificationImage.setImageResource(R.drawable.ic_notify);
                            notificationPanel.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    //SharedPreferences categoryViewShared = getSharedPreferences("categoryView", MODE_PRIVATE);
                                    Intent intent = new Intent(getActivity(), mMobikulApplication.viewCategoryGrid());
                                    String tag = (String) v.getTag();
                                    final String[] tagArr = tag.split("/");
                                    int index = Integer.parseInt(tagArr[0]);

                                    intent.putExtra("ID", response.body().getNotifications().get(index).getId());
                                    intent.putExtra("CATEGORY_NAME", response.body().getNotifications().get(index).getTitle());
                                    intent.putExtra("drawerData", drawerDataObject + "");
                                    startActivity(intent);
                                    Log.d("tag", tag + "");


                                    if (tag.contains("containnew")) {

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removeNewPanel(v, tagArr[1]);
                                            }
                                        }, 1000);
                                    }
                                }
                            });
                        } else if (response.body().getNotifications().get(i).getType().equalsIgnoreCase("custom")) {
                            notificationImage.setLayoutParams(new FrameLayout.LayoutParams(
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2));
                            Glide.with(getActivity())
                                    .load(response.body().getNotifications().get(i).getImage())
                                    .into(notificationImage);
//                            notificationImage.setImageResource(R.drawable.ic_notify);
                            notificationPanel.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    try {
                                        Intent intent;
                                        String tag = (String) v.getTag();
                                        final String[] tagArr = tag.split("/");
                                        int index = Integer.parseInt(tagArr[0]);
                                        intent = new Intent(getActivity(), mMobikulApplication.viewCategoryGrid());
                                        intent.putExtra("type", "custom");
                                        intent.putExtra("id", response.body().getNotifications().get(index).getId());

                                        startActivity(intent);
                                        Log.d("tag", tag + "");

                                        if (tag.contains("containnew")) {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    removeNewPanel(v, tagArr[1]);
                                                }
                                            }, 1000);
                                        }
                                    } catch (Exception e) {
                                        Log.d("type3", "3" + e);
                                    }

                                }
                            });

                        } else {
                            notificationImage.setLayoutParams(new FrameLayout.LayoutParams(
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2));
                            Glide.with(getActivity())
                                    .load(response.body().getNotifications().get(i).getImage())
                                    .into(notificationImage);
//                            notificationImage.setImageResource(R.drawable.ic_notify);
                            notificationPanel.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    try {
                                        Intent intent;
                                        String tag = (String) v.getTag();
                                        final String[] tagArr = tag.split("/");
                                        int index = Integer.parseInt(tagArr[0]);
                                        intent = new Intent(getActivity(), mMobikulApplication.viewProductSimple());
                                        intent.putExtra("idOfProduct", response.body().getNotifications().get(index).getId());
                                        intent.putExtra("nameOfProduct", response.body().getNotifications().get(index).getTitle());
                                        startActivity(intent);
                                        Log.d("tag", tag + "");

                                        if (tag.contains("containnew")) {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    removeNewPanel(v, tagArr[1]);
                                                }
                                            }, 1000);
                                        }
                                    } catch (Exception e) {
                                        Log.d("type4", "4" + e);
                                    }

                                }
                            });
                        }
                        TextView notificationTitle = child.notificationTitle;
                        TextView shortDescriptionNotify = child.shortDescriptionNotify;
                        notificationTitle.setText(response.body().getNotifications().get(i).getTitle());
                        shortDescriptionNotify.setText(Html.fromHtml(response.body().getNotifications().get(i).getSubTitle()));

                    }
                } else {
                    notificationBinding.noNotification.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ViewNotification> call, Throwable t) {
                if (SweetAlertBox.Companion.getSweetAlertDialog() != null) {
                    SweetAlertBox.Companion.getSweetAlertDialog().dismissWithAnimation();
                }
            }
        };
        if (!checkConn()) {
            ((BaseActivity) getActivity()).showDialog(getActivity());
        } else {
            new SweetAlertBox().showProgressDialog(getActivity());
            RetrofitCallback.INSTANCE.viewNotificationCall(getActivity(), new RetrofitCustomCallback(notificationCallback, getActivity()));
        }
        return notificationBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @SuppressLint("CommitPrefEdits")
    private void removeNewPanel(View v, String tag) {
        v.findViewById(R.id.newNotificationTitle).setVisibility(View.GONE);
        v.setBackgroundColor(Color.parseColor("#A8A5A5"));
        unreadNotifications.remove(tag);
        notificationShared.edit().putStringSet("unreadNotifications", unreadNotifications);
        notificationShared.edit().apply();
    }

}
