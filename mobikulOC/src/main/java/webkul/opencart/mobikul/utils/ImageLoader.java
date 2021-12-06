package webkul.opencart.mobikul.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import webkul.opencart.mobikul.helper.Utils;
import webkul.opencart.mobikul.R;

public class ImageLoader {

    @BindingAdapter(value = {"bind:loadImage", "bind:dominantColor"}, requireAll = false)
    public static void loadImage(ImageView view, String imageUrl, String dominantColor) {
        Log.d("DominantColorCategory", "==========>" + dominantColor);

        if (dominantColor != null) {
            view.setBackgroundColor(Color.parseColor(dominantColor));
        }
        if (imageUrl != null && !imageUrl.equals("")) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            view.setBackgroundColor(0);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .override(Utils.getDeviceScreenWidth() / 2,
                            Utils.getDeviceScreenWidth() / 2)
                    .fitCenter()
                    .skipMemoryCache(false)
                    .into(view);
        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }

    }

    @BindingAdapter({"bind:loadBannerImage"})
    public static void loadBannerImage(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .into(view);
        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    @BindingAdapter(value = {"bind:loadProductBannerImage", "bind:dominantColor"}, requireAll = false)
    public static void loadProductBannerImage(ImageView view, String imageUrl, String dominantColor) {
        if (dominantColor != null) {
            view.setBackgroundColor(Color.parseColor(dominantColor));
        }
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            view.setBackgroundColor(0);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .skipMemoryCache(false)
                    .into(view);


        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    @BindingAdapter(value = {"bind:loadCarousal", "bind:dominantColor"}, requireAll = false)
    public static void loadCarousal(ImageView view, String imageUrl, String dominantColor) {
        if (dominantColor != null) {
            view.setBackgroundColor(Color.parseColor(dominantColor));
        }
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            view.setBackgroundColor(0);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .skipMemoryCache(false)
                    .into(view);


        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    @BindingAdapter({"bind:loadCategoryIcon"})
    public static void loadCategoryIcon(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);

            Glide.with(view.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(70, 70)
                    .dontAnimate()
                    .into(view);

        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .override(70, 70)
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    @BindingAdapter({"bind:loadSubCategoryIcon"})
    public static void loadSubCategoryIcon(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);


        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    @BindingAdapter({"bind:loadProfile"})
    public static void loadImageProfile(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("Image", "loadImage: " + imageUrl);
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .override(100, 100)
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);

        } else {
            Glide.with(view.getContext())
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

}
