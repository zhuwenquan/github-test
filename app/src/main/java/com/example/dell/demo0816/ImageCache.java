package com.example.dell.demo0816;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by DELL on 2016/8/16.
 */
public class ImageCache {
    private static ImageCache imageCache = null;
    private LruCache<String, Bitmap> cache = null;

    private ImageCache() {
        cache = new LruCache<String, Bitmap>((int) Runtime.getRuntime().maxMemory() / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public static ImageCache getInstance() {
        if (imageCache == null) {
            synchronized (ImageCache.class) {
                if (imageCache == null) {
                    imageCache = new ImageCache();
                }
            }
        }
        return imageCache;
    }

    public Bitmap put(String key, Bitmap value) {
        return cache.put(key, value);
    }

    public Bitmap get(String key) {
        return cache.get(key);
    }
}
