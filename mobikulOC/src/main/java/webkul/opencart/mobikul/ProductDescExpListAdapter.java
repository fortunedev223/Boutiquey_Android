package webkul.opencart.mobikul;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


@SuppressLint("InflateParams")
public class ProductDescExpListAdapter extends BaseExpandableListAdapter {
    /* objects related to reveiw elv*/
    JSONArray reviewList = null;
    LinearLayout reviewLayout;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private JSONArray specificationList;

    public ProductDescExpListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        //         if(convertView == null) {
        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (groupPosition == 0) {
            //for description
            // convertView = infalInflater.inflate(R.layout.item_category_drawer_list, null);
            // TextView txtListChild = (TextView) convertView.findViewById(R.id.category_item_list);
            // txtListChild.setText(Html.fromHtml(childText));
            // txtListChild.setText(Html.fromHtml(childText, new ImageGetter(){
            //
            // @Override
            // public Drawable getDrawable(String source) {
            // return _context.getResources().getDrawable(R.drawable.delete);
            // }
            // }, null));
            WebView myWebView = new WebView(_context);
            try {
                String html = childText;
                String mime = "text/html";
                String encoding = "utf-8";
                myWebView.setFocusable(true);
                myWebView.loadDataWithBaseURL(null, html, mime, encoding, null);
                return myWebView;
            } catch (Exception e) {
                e.printStackTrace();
                return myWebView;
            }
        }
        /* Additional Info may or may not available */
        else if (_listDataHeader.get(groupPosition).equals("Specification")) {
            convertView = infalInflater.inflate(R.layout.layout_additional_information_product, null);
            convertView.setTag("additional_information_product");
            try {
                specificationList = new JSONArray(childText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LinearLayout specification_Values = (LinearLayout) convertView.findViewById(R.id.specification_Value_Layout);

            for (int i = 0; i < specificationList.length(); i++) {
                JSONObject specificationData;
                try {
                    specificationData = specificationList.getJSONObject(i);

                    TextView specificationHeading = new TextView(_context);
                    specificationHeading.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                    specificationHeading.setBackgroundResource(R.drawable.rect_shape);
                    specificationHeading.setText(specificationData.getString("name"));
                    specificationHeading.setTypeface(null, Typeface.BOLD);
                    specificationHeading.setPadding(20, 5, 5, 10);

                    LinearLayout attributesLayout = new LinearLayout(_context);
                    attributesLayout.setOrientation(LinearLayout.HORIZONTAL);
                    attributesLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                    for (int j = 0; j < specificationData.getJSONArray("attribute").length(); j++) {
                        JSONObject specificationDataAttributes = specificationData.getJSONArray("attribute").getJSONObject(j);

                        TextView attributeLabel = new TextView(_context);
                        attributeLabel.setText(specificationDataAttributes.getString("name"));
                        attributeLabel.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.7f));
                        attributeLabel.setBackgroundResource(R.drawable.rect_shape);
                        attributeLabel.setPadding(20, 5, 5, 10);

                        TextView attributeValue = new TextView(_context);
                        attributeValue.setText(specificationDataAttributes.getString("text"));
                        attributeValue.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                        attributeValue.setBackgroundResource(R.drawable.rect_shape);
                        attributeValue.setPadding(20, 5, 5, 10);

                        attributesLayout.addView(attributeLabel);
                        attributesLayout.addView(attributeValue);
                    }

                    specification_Values.addView(specificationHeading);
                    specification_Values.addView(attributesLayout);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            try {
                try {
                    reviewList = new JSONArray(childText);
                } catch (JSONException e) {
                    Log.d("Exception in getChildView", "Review product ELV");
                    e.printStackTrace();
                }
                if (!childText.equals("[]")) {
                    convertView = infalInflater.inflate(R.layout.layout_review_in_product_desc_elv, null);
                    convertView.setTag("review_in_product_desc_elv");
                    reviewLayout = (LinearLayout) convertView.findViewById(R.id.reviewLayout);
                    TextView firstReview = (TextView) convertView.findViewById(R.id.firstReview);
                    if (reviewList.length() != 0)
                        createReview(reviewList.length());
                    else {
                        reviewLayout.setVisibility(View.GONE);
                        firstReview.setVisibility(View.VISIBLE);
                        TextView totalReview = (TextView) convertView.findViewById(R.id.totalReviews);
                        totalReview.setVisibility(View.GONE);
                        TextView ownReview = (TextView) convertView.findViewById(R.id.ownReview);
                        ownReview.setTextSize(13);
                        firstReview.setTextColor(Color.parseColor("3399cc"));
                    }
                    TextView Total = (TextView) convertView.findViewById(R.id.totalReviews);
                    Total.setText(_context.getResources().getString(R.string.total) + " " + reviewList.length() + _context.getResources().getString(R.string._customer_reviews));
                } else {
                    convertView = infalInflater.inflate(R.layout.item_category_drawer_list, null);
                    TextView txtListChild = (TextView) convertView.findViewById(R.id.category_item_list);
                    txtListChild.setTag("first_review");
                    txtListChild.setText(R.string.be_the_first_to_review_this_product);
                    txtListChild.setTextColor(Color.parseColor("#3399cc"));
                }
            } catch (Exception e) {
                Log.d("Exception in taking review in product elv", e.getMessage());
            }
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_category_drawer_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.category_item_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if (getChildrenCount(groupPosition) != 0) {
            final ImageView childLogo = (ImageView) convertView.findViewById(R.id.childOpenCloseLogo);
            if (isExpanded) {
                childLogo.setImageResource(R.drawable.ic_sub);
            } else childLogo.setImageResource(R.drawable.ic_add);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void createReview(int reviewLength) {
        LinearLayout linearLayout = null;
        try {
            for (int i = 0; i < reviewLength; i++) {
                JSONObject reviewListAttributes = reviewList.getJSONObject(i);
//				TextView title = new TextView(_context);
                TextView details = new TextView(_context);
                TextView reviewBy = new TextView(_context);
                TextView reviewOn = new TextView(_context);
                RatingBar ratePoints1 = new RatingBar(_context, null, android.R.attr.ratingBarStyleSmall);
                linearLayout = new LinearLayout(_context);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                linearLayout.setPadding(2, 2, 2, 2);
                reviewBy.setText(reviewListAttributes.getString("author"));
                reviewBy.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                reviewBy.setTextSize(14);
                reviewBy.setTypeface(null, Typeface.BOLD);
                reviewBy.setBackgroundResource(R.drawable.rect_shape);
                reviewBy.setPadding(20, 5, 5, 10);
                reviewOn.setText(reviewListAttributes.getString("date_added"));
                reviewOn.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                reviewOn.setTypeface(null, Typeface.ITALIC);
                reviewOn.setGravity(Gravity.END);
                reviewOn.setBackgroundResource(R.drawable.rect_shape);
                reviewOn.setPadding(5, 5, 20, 10);
                linearLayout.addView(reviewBy);
                linearLayout.addView(reviewOn);
                details.setText(webkul.opencart.mobikul.helper.Utils.fromHtml(reviewListAttributes.getString("text")));
                ratePoints1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                ratePoints1.setRating(Float.parseFloat(reviewListAttributes.getString("rating")));
                reviewLayout.addView(linearLayout);

                reviewLayout.addView(details);
                reviewLayout.addView(ratePoints1);
                reviewLayout.addView(line());
            }
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
    }

    public View line() {
        View v = new View(_context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 2);
        lineParams.setMargins(0, 5, 0, 5);
        v.setLayoutParams(lineParams);
        v.setBackgroundColor(Color.parseColor("#9b9b9b"));
//		v.setPadding(10, 5, 10, 5);
        return v;
    }

}