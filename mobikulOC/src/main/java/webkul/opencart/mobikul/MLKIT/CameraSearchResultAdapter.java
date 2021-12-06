//package webkul.opencart.mobikul.MLKIT;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.List;
//
//import webkul.opencart.mobikul.R;
//
//
//public class CameraSearchResultAdapter extends RecyclerView.Adapter<CameraSearchResultAdapter.ViewHolder> {
//    private Context context;
//    private List<String> labelList;
//
//    public CameraSearchResultAdapter(Context context, List<String> labelList) {
//        this.context = context;
//        this.labelList = labelList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.camera_simple_spinner_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        ((TextView) holder.itemView.findViewById(R.id.label_tv)).setText(labelList.get(position));
//        holder.itemView.findViewById(R.id.label_tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((CameraSearchActivity) context).sendResultBack(position);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return labelList.size();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//}
