package com.sojay.testfunction.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class LoadImgUtil {

    private static volatile LoadImgUtil ins = null;

    private LoadImgUtil() {

    }

    public static LoadImgUtil ins() {
        LoadImgUtil mins = ins;
        if (mins == null) {
            synchronized (LoadImgUtil.class) {
                mins = ins;
                if (mins == null) {
                    mins = new LoadImgUtil();
                    ins = mins;
                }
            }
        }
        return mins;
    }

    public void loadAvatar(Context context, String url, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.override(200, 200);
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    public void loadThumbnail(Context context, String url, int placeholder, ImageView imageView, int width, int height) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url.contains("imageView") ? url : url + "?imageView2/1/w/" + width + "/h/" + height).apply(options).into(imageView);
    }

    public void loadThumbnail(Context context, String url, int placeholder, ImageView imageView, int width, int height, RequestListener listener) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url.contains("imageView") ? url : url + "?imageView2/1/w/" + width + "/h/" + height).listener(listener).apply(options).into(imageView);
    }

    public void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage1(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage1(Context context, String url, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url))
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImageNoCache(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.skipMemoryCache(true);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage(Context context, String url, int placeholder, ImageView imageView, RequestListener<Bitmap> listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).asBitmap().load(url).listener(listener).apply(options).into(imageView);
    }

    public void loadImageWithSize(Context context, String url, int placeholder, ImageView imageView, int width, int height) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        options.override(width, height);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImageWithSize(Context context, String url, ImageView imageView, int width, int height) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.override(width, height);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage(Context context, String url, ImageView imageView, RequestListener listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).listener(listener).apply(options).into(imageView);
    }

    public void loadSpecilImage(Context context, String url, int placeholder, ImageView imageView, RequestListener listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url).listener(listener).apply(options).into(imageView);
    }

    public void loadSpecilImage(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url).apply(options).into(imageView);
    }

    public void loadSpecilImage(Context context, String url, int placeholder, ImageView imageView, int width, int height) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        options.override(width, height);
        Glide.with(context).asDrawable().load(url).apply(options).into(imageView);
    }

    public void loadTagImage(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url).apply(options).into(imageView);
    }

    public void loadSpecilImage(Context context, String url, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url))
            return;
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url).apply(options).into(imageView);
    }

    public void loadCircleImage(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        options.transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(imageView);
    }

//    public void loadCircleImage(Context context, String url, int placeholder, ImageView imageView) {
//        RequestOptions options = new RequestOptions();
//        options.centerCrop();
//        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
//        options.placeholder(placeholder);
//        options.priority(Priority.HIGH);
//        options.transform(new CropCircleTransformation());
//        Glide.with(context).load(url).apply(options).into(imageView);
//    }

    public void loadRoundImage(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        options.transform(new GlideRoundTransform());
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadRoundImage(Context context, String url, int placeholder, ImageView imageView, int radius) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        options.priority(Priority.HIGH);
        options.transform(new GlideRoundTransform(radius));
//        options.transform(new RoundedCornersTransformation(radius, 0,
//                RoundedCornersTransformation.CornerType.RESOURCE));
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadImage1(Context context, String url, ImageView imageView, RequestListener listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).listener(listener).apply(options).into(imageView);
    }

    /**
     * 获取bitmap (new BitmapImageViewTarget(imageview))
     *
     * @param context
     * @param url
     * @param target
     */
    public void loadDrawable(Context context, String url, Target target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).asDrawable().load(url).apply(options).into(target);
    }

    public void loadBitmap(Context context, String url, Target target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).asBitmap().load(url).apply(options).into(target);
    }

    /**
     * @param context
     * @param url
     * @param target
     */
    public void loadFile(Context context, String url, Target target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
//        options.centerCrop();
//        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).asFile().load(url).apply(options).into(target);
    }

    public void loadFile(Context context, String url, RequestListener listener) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
//        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).asFile().load(url).apply(options).listener(listener);
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param target
     */
    public void loadGif(Context context, String url, int placeholder, Target target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.placeholder(placeholder);
        Glide.with(context).asGif().load(url).apply(options).into(target);
    }

    public void loadGif(Context context, String url, int placeholder, int width, int height, Target target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.placeholder(placeholder);
        options.override(width, height);
        Glide.with(context).asGif().load(url).apply(options).into(target);
    }

    public void loadGif(Context context, String url, int placeholder, RequestListener target) {
        if (context == null)
            return;
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.placeholder(placeholder);
        Glide.with(context).asGif().load(url).apply(options).listener(target);
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadGif(Context context, String url, int placeholder, ImageView imageView) {
        if (context == null || TextUtils.isEmpty(url)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        url = getRealUrl(url);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        options.placeholder(placeholder);
        Glide.with(context).asGif().load(url).apply(options).into(imageView);
    }

    public void loadLocalImage(Context context, String path, int placeholder, final ImageView imageView, int width, int height) {
        if (context == null || TextUtils.isEmpty(path)) {
            if (placeholder != 0)
                imageView.setImageResource(placeholder);
            return;
        }
        Uri url = Uri.fromFile(new File(path));
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        if (width != 0 && height != 0)
            options.override(width / 5, height / 5);
        options.priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public void loadLocalImage(Context context, String path, int placeholder, final ImageView imageView, int width, int height, RequestListener<Bitmap> listener) {
        if (context == null)
            return;
        Uri url = Uri.fromFile(new File(path));
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.placeholder(placeholder);
        if (width != 0 && height != 0)
            options.override(width / 5, height / 5);
        options.priority(Priority.HIGH);
        Glide.with(context).asBitmap().listener(listener).load(url).apply(options).into(imageView);
    }

    public void loadLocalImage(Context context, String path, final ImageView imageView, RequestListener listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.priority(Priority.HIGH);
        Glide.with(context).load(path).listener(listener).apply(options).into(imageView);
    }

    public void loadLocalCircleImage(Context context, String path, final ImageView imageView, RequestListener listener) {
        if (context == null)
            return;
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.centerCrop();
        options.priority(Priority.HIGH);
        options.transform(new GlideCircleTransform());
        Glide.with(context).load(path).listener(listener).apply(options).into(imageView);
    }

    public String getRealUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.contains("http://") || url.contains("https://")) {
            return url;
        }
        return "" + url;
    }

    public class SimpleRequestListener implements RequestListener<Bitmap> {
        @Override
        public boolean onLoadFailed(GlideException e, Object o, Target<Bitmap> target, boolean b) {
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
            return false;
        }
    }

}
