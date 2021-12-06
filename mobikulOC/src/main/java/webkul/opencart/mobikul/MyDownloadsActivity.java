package webkul.opencart.mobikul;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import webkul.opencart.mobikul.connection.VolleyConnection;
import webkul.opencart.mobikul.databinding.ActivityMyDownloadsBinding;
import webkul.opencart.mobikul.databinding.ItemMyDownloadableProductBinding;

public class MyDownloadsActivity extends BaseActivity {

    private ProgressBar spinner;
    SharedPreferences configShared;
    protected List<DownloadableProductInfo> downloadProductInfo;
    private int flag;
    private int totalItems = 0;
    private int pageNumber = 1;
    private Toast t;
    DownloadableDataAdapter adapter;
    ListView downloadableList;
    private ActionBar actionBar;
    ActivityMyDownloadsBinding downloadsBinding;
    private TextView title;
    private int limit = 5;

    @Override
    protected void onResume() {
//        if (itemCart != null) {
//            SharedPreferences customerDataShared = getSharedPreferences("customerData", MODE_PRIVATE);
//            LayerDrawable icon = (LayerDrawable) itemCart().getIcon();
//            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"));
//        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        isOnline();
        if (!isInternetAvailable()) {

            showDialog(this);
        } else {
            downloadsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_downloads);
            Toolbar toolbar = downloadsBinding.toolbar.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            title = downloadsBinding.toolbar.findViewById(R.id.title);
            LinearLayout mydownloadsContainer = downloadsBinding.mydownloadsContainer;
            mydownloadsContainer.setVisibility(View.GONE);
            title.setText(getResources().getString(R.string.title_activity_my_downloads));
            Boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            if (!isLoggedIn) {
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
            }
            JSONObject jo = new JSONObject();
            try {
                jo.put("page", pageNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new VolleyConnection(this).getResponse(Request.Method.POST, "getDownloadInfo", jo.toString());
        }
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mDownloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(onComplete);
        super.onDestroy();
    }

    DownloadManager mDownloadManager;
    private long mDownloadedFileID;
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Intent fileIntent = new Intent(Intent.ACTION_VIEW);
            // Grabs the Uri for the file that was downloaded.
            Uri mostRecentDownload =
                    mDownloadManager.getUriForDownloadedFile(mDownloadedFileID);
            // DownloadManager stores the Mime Type. Makes it really easy for us.
            String mimeType =
                    mDownloadManager.getMimeTypeForDownloadedFile(mDownloadedFileID);
            Log.d("DownloadFile", "=====MiMe Type==>" + mimeType);
//            fileIntent.setDataAndType(mostRecentDownload, "pdf");
            fileIntent.setDataAndType(mostRecentDownload, mimeType);
            fileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent openIntent = Intent.createChooser(fileIntent, "Open File");
            try {
                startActivity(openIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MyDownloadsActivity.this,
                        "UnSupported Format",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    public void getDownloadInfoResponse(String backresult) {
        try {
            responseObject = new JSONObject(backresult);
            downloadProductInfo = new ArrayList<DownloadableProductInfo>();
            if (!responseObject.getString("error").equalsIgnoreCase("1")) {
                downloadableList = downloadsBinding.mydownloadsLayout;
                final JSONArray mydownloads = responseObject.getJSONArray("downloadData");
                totalItems = totalItems + mydownloads.length();
                for (int i = 0; i < mydownloads.length(); i++) {
                    JSONObject each = new JSONObject(mydownloads.getJSONObject(i).toString());
                    try {
                        downloadProductInfo.add(new DownloadableProductInfo(each.getString("order_id"),
                                each.getString("date_added"),
                                each.getString("name")
                                , each.getString("size"),
                                each.getString(""),
                                each.getString("url")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                downloadableList.setOnScrollListener(new OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view,
                                                     int scrollState) {
                        if (scrollState == 0) {
                            try {
                                if (view.getLastVisiblePosition() == totalItems - 1 && totalItems < (Integer.parseInt(responseObject.getString("downloadsTotal")))) {
                                    if (flag == 0) {
                                        downloadsBinding.myDownloadableProductRequestBar.setVisibility(View.VISIBLE);
                                        flag = 1;
                                        pageNumber++;
                                        JSONObject jo = new JSONObject();
                                        try {
                                            jo.put("page", pageNumber);
                                            jo.put("limit", limit);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        new VolleyConnection(MyDownloadsActivity.this).getResponse(Request.Method.POST, "getDownloadInfo", jo.toString());
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        try {
                            if (t != null)
                                t.setText((view.getLastVisiblePosition() + 1) + getResources().getString(R.string.of_toast_for_no_of_item) + responseObject.getString("downloadsTotal"));
                            else
                                t = Toast.makeText(getApplicationContext(), view.getLastVisiblePosition() + 1 + getResources().getString(R.string.of_toast_for_no_of_item) + responseObject.getString("downloadsTotal"), Toast.LENGTH_SHORT);
                            t.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
                adapter = new DownloadableDataAdapter(MyDownloadsActivity.this, downloadProductInfo);
                downloadableList.setAdapter(adapter);
            } else {
                TextView noDownloadsView = downloadsBinding.errorTv;
                noDownloadsView.setText(responseObject.getString("message"));
                noDownloadsView.setVisibility(View.VISIBLE);
            }
            spinner = downloadsBinding.mydownloadsprogress;
            spinner.setVisibility(View.GONE);
            LinearLayout myorderContainer = downloadsBinding.mydownloadsContainer;
            myorderContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d("Exception in onpost", e.toString());
            e.printStackTrace();
        }
    }

    public void getDownloadInfoResponseLazyLoad(String backresult) {
        try {
            responseObject = new JSONObject(backresult);
            downloadsBinding.myDownloadableProductRequestBar.setVisibility(View.GONE);
            downloadProductInfo = new ArrayList<>();
            totalItems = totalItems + responseObject.getJSONArray("downloadData").length();
            final JSONArray mydownloads = new JSONArray(responseObject.getString("downloadData"));
            for (int i = 0; i < mydownloads.length(); i++) {
                JSONObject each = new JSONObject(mydownloads.getJSONObject(i).toString());
                downloadProductInfo.add(new DownloadableProductInfo(each.getString("order_id"),
                        each.getString("date_added"),
                        each.getString("name")
                        , each.getString("size"),
                        each.getString("extension"),
                        each.getString("url")));
            }
            adapter.addAll(downloadProductInfo);
            flag = 0;
        } catch (JSONException e) {
            Log.d("MyDownloadActivity", "" + e);
            e.printStackTrace();
        }
    }

    class DownloadableDataAdapter extends ArrayAdapter<DownloadableProductInfo> {

        public DownloadableDataAdapter(Context context, List<DownloadableProductInfo> myList) {
            super(context, R.layout.item_my_downloadable_product, myList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                DownloadableProductInfo downloadData = getItem(position);
                ItemMyDownloadableProductBinding binding = ItemMyDownloadableProductBinding.inflate(LayoutInflater.from(MyDownloadsActivity.this), parent, false);
                binding.orderNo.setText(getResources().getString(R.string.order_id) + " #" + downloadData.orderId);
                binding.date.setText(downloadData.dateAdded);
                binding.title.setText(downloadData.fileName);
                binding.size.setText(downloadData.size);
                binding.downloadBtn.setTag(position);
                binding.downloadBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int index = (Integer) v.getTag();
                        android.support.v7.app.AlertDialog.Builder alert =
                                new android.support.v7.app.AlertDialog.Builder(MyDownloadsActivity.this, R.style.AlertDialogTheme);
                        alert.setNegativeButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    if (haveStoragePermission()) {
                                        String url = responseObject.getJSONArray("downloadData").getJSONObject(index).getString("url");
                                        String fileName = responseObject.getJSONArray("downloadData").getJSONObject(index).getString("file");
                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(webkul.opencart.mobikul.helper.Utils.fromHtml(url) + ""));
                                        request.setTitle(fileName);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                            request.allowScanningByMediaScanner();
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        }
                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                        mDownloadedFileID = manager.enqueue(request);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "Downloading started...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setMessage("Do you want to download").show();
                    }
                });
                convertView = binding.getRoot();
            } catch (Exception e) {
                Log.d("Exception in getView", e.getMessage());
                return convertView;
            }
            return convertView;
        }
    }

    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {
                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }
}
