package webkul.opencart.mobikul;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


/**
 * Created by ashwini.gupta on 6/10/16.
 */
public class LoadImagewithPicasso {
   private static Context mContext;
//   private static ImageView mImageView;
//   private static Uri mUrl;
//   private static int mWidth, mHeight;
   public static LoadImagewithPicasso picassoObject = new LoadImagewithPicasso();

   public static LoadImagewithPicasso getContext(Context context){
        mContext = context;
        return  picassoObject ;
    }

    public void loadImageWithPlaceholder(ImageView imageView, String url,int width, int height){

        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.placeholder)
 //               .resize(width, height)
                .transform(new BitmapTransform(width, height))
//                .rotate(degree)
                .into(imageView);
          }

    public void loadPlaceHolder(ImageView imageView,int width, int height){
        Picasso.with(mContext)
                .load(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .transform(new BitmapTransform(width, height))
//                .rotate(degree)
                .into(imageView);
    }



    /**
     * Transformate the loaded image to avoid OutOfMemoryException
     */
    public class BitmapTransform implements Transformation {

        int maxWidth;
        int maxHeight;

        public BitmapTransform(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth, targetHeight;
            double aspectRatio;

            if (source.getWidth() > source.getHeight()) {
                targetWidth = maxWidth;
                aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                targetHeight = (int) (targetWidth * aspectRatio);
            } else {
                targetHeight = maxHeight;
                aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return maxWidth + "x" + maxHeight;
        }

    }
}
