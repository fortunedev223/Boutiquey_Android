package webkul.opencart.mobikul.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import webkul.opencart.mobikul.helper.Utils;
import webkul.opencart.mobikul.SortByData;
import webkul.opencart.mobikul.databinding.SortItemLayoutForManufactureBinding;

public class SortItemAdapterForManufacture extends RecyclerView.Adapter<SortItemAdapterForManufacture.MyViewHolder> {
    Context mContext;
    ArrayList<webkul.opencart.mobikul.model.ManufactureInfoModel.Sort> categories;
    private int selectedPosition = -1;
    SortByData sortByData;
    BottomSheetDialog sheetDialog;
    String sortData[];

    public SortItemAdapterForManufacture(Context mContext, ArrayList<webkul.opencart.mobikul.model.ManufactureInfoModel.Sort> categories,
                                         SortByData sortByData, BottomSheetDialog sheetDialog, String sortData[]) {
        this.mContext = mContext;
        this.categories = categories;
        this.sortByData = sortByData;
        this.sheetDialog = sheetDialog;
        this.sortData = (sortData[0] == "" && sortData[1] == "") ? new String[2] : sortData;
        this.selectedPosition = (sortData[2].equals("")) ? -1 : Integer.parseInt(sortData[2]);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SortItemLayoutForManufactureBinding sortItemLayoutBinding = SortItemLayoutForManufactureBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new MyViewHolder(sortItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.label.setText(Utils.fromHtml(categories.get(position).getText()).toString());
        holder.radioButton.setChecked((selectedPosition == -1 && position == 0) || (selectedPosition == position));
        holder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                String a[] = new String[3];
                a[0] = categories.get(position).getValue();
                a[1] = categories.get(position).getOrder();
                a[2] = position + "";
                sortByData.datavalue(a);
                sheetDialog.dismiss();
            }
        });
        holder.radioButton.setOnClickListener(view -> holder.llParent.performClick());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        RadioButton radioButton;
        LinearLayout llParent;

        public MyViewHolder(SortItemLayoutForManufactureBinding itemView) {
            super(itemView.getRoot());
            label = itemView.label;
            radioButton = itemView.radioButton;
            llParent = itemView.llParent;
        }
    }
}

