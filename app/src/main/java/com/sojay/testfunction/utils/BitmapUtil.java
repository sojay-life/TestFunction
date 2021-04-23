package com.sojay.testfunction.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    /**
     * 加载本地图片
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 加载本地图片
     */
    public static BitmapDrawable getLocalBitmapDrawable(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return new BitmapDrawable(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 裁剪bitmap
     */
    public static Bitmap cropBitmap(Bitmap bitmap, int x, int y, int cropWidth, int corpHeight) {

        if (x + cropWidth > bitmap.getWidth() || y + corpHeight > bitmap.getHeight())
            return null;

        return Bitmap.createBitmap(bitmap, x, y, cropWidth, corpHeight, null, false);


        // x:0    w:960
        // y:300  h:540

        // x:0    w:120
        // y:920  h:160

    }

    /**
     * 裁剪bitmap
     */
    public static BitmapDrawable cropBitmap1(Bitmap bitmap, int x, int y, int cropWidth, int corpHeight) {

        if (x + cropWidth > bitmap.getWidth() || y + corpHeight > bitmap.getHeight())
            return null;

        return new BitmapDrawable(Bitmap.createBitmap(bitmap, x, y, cropWidth, corpHeight, null, false));

    }

    public static BitmapDrawable getBgDrawable(String path1, String path2) {
        Bitmap bitmap1 = getLocalBitmap(path1);
        Bitmap bitmap2 = getLocalBitmap(path2);
        Bitmap cropBitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, 1920, 920, null, false);
        Bitmap cropBitmap2 = Bitmap.createBitmap(bitmap2, 0, 920, 1920, 160, null, false);

        Bitmap compositeBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(compositeBitmap);
        canvas.drawBitmap(cropBitmap1, 0, 0, null);
        canvas.drawBitmap(cropBitmap2, 0, 920, null);
        canvas.save();
        canvas.restore();
        return new BitmapDrawable(compositeBitmap);
    }

    public static void saveBitmap(String path, Bitmap bitmap) {
        if (TextUtils.isEmpty(path) || bitmap == null)
            return;

        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
