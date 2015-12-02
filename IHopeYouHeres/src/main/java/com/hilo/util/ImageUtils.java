package com.hilo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.hilo.R;
import com.hilo.others.Company;
import com.hilo.others.MyApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hilo on 15/12/2.
 * <p/>
 * Drscription:
 */
public class ImageUtils {

    private static HashMap<String, Bitmap> imageMaps = new HashMap<>();
    private static DisplayImageOptions options;
    private static AnimateFirstDisplayListener animateFirstListener = new AnimateFirstDisplayListener();
    private static ImageLoader mImageLoader;
    private static int mScrWidth = 0;
    private static int mScrHeight = 0;

    static {
        options = getImageloaderOptions();
        if (null == options) {
            initImageLoaderOptions();
            options = getImageloaderOptions();
        }
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                    int pos = imageUri.lastIndexOf('/');
                    String pathfile = Company.getInstance().userFilePath + imageUri.substring(pos + 1);
                    File myCaptureFile = new File(pathfile);
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                        loadedImage.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                        bos.flush();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ImageUtils(ImageView imageView, String url) {
        LogUtils.I("mlog", "ImageUtils: " + url);
        init(imageView);
        mImageLoader.displayImage(url, imageView, options, animateFirstListener);
    }


    private void init(ImageView view) {
        if (mImageLoader == null) initImageLoader(view.getContext());
        if (options == null) initImageLoaderOptions();
        if (animateFirstListener == null) animateFirstListener = new AnimateFirstDisplayListener();
    }

    public static void initImageLoaderOptions() {
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        if (options == null) {
            /**
             EXACTLY :图像将完全按比例缩小的目标大小
             EXACTLY_STRETCHED:图片会缩放到目标大小完全
             IN_SAMPLE_INT:图像将被二次采样的整数倍
             IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
             NONE:图片不会调整
             */
            options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.approve_image_local)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.approve_image_default)  // 设置图片Uri为空或是错误FileUtils.getOptios()的时候显示的图片
                    .showImageOnFail(R.mipmap.approve_image_default)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                    .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型//
                    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                    .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                            // .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                            // .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                            // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少,避免使用 他会重新创建新的ARGB_8888格式的Bitmap对象
                    .build();
//        ImageLoader.getInstance().clearMemoryCache();
//        ImageLoader.getInstance().clearDiskCache();
        }
    }

    public static void initImageLoader(Context context) {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        if (AnimateFirstDisplayListener.displayedImages != null)
            AnimateFirstDisplayListener.displayedImages.clear();
//		File cacheDir =StorageUtils.getOwnCacheDirectory(this, Configuration.getConfig().companyCode + "/"+Configuration.getConfig().userCode);
        File cacheDir = new File(Company.getInstance().userImagePath);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(5)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(200) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    public static DisplayImageOptions getImageloaderOptions() {
        return options;
    }


    // ------------------------------------------------------------------------------------------------------------------------------
    public static Bitmap decodeFile(String pathfile) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathfile, opts);

        if (mScrWidth == 0 || mScrHeight == 0) {
            DisplayMetrics dm = MyApplication.mContext.getResources().getDisplayMetrics();
            mScrWidth = dm.widthPixels;
            mScrHeight = dm.heightPixels;
        }
        int inSampleSize = calculateInSampleSize(opts, mScrWidth, mScrHeight);

        opts.inJustDecodeBounds = false;
        opts.inDither = true;
        opts.inPurgeable = true;
        opts.inTempStorage = new byte[12 * 1024];
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = inSampleSize;
        File file = new File(pathfile);
        Bitmap bmp = null;
        if (file.exists())
            bmp = BitmapFactory.decodeFile(pathfile, opts);
        return bmp;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public static void transImage(String srcFile, String desFile, int maxSize, int quality) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcFile, opts);

        int inSampleSize = calculateInSampleSize(opts, maxSize, maxSize);

        opts.inJustDecodeBounds = false;
        opts.inDither = true;
        opts.inPurgeable = true;
        opts.inTempStorage = new byte[12 * 1024];
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = inSampleSize;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bmp = BitmapFactory.decodeFile(srcFile, opts);
        if (bmp == null) return;
        int w, h;
        int bw = bmp.getWidth();
        int bh = bmp.getHeight();
        if (bw >= bh) {
            w = maxSize;
            h = bh * w / bw;
        } else {
            h = maxSize;
            w = bw * h / bh;
        }
        Bitmap newbmp = Bitmap.createScaledBitmap(bmp, w, h, false);

        FileOutputStream fw;
        try {
            fw = new FileOutputStream(desFile);
            newbmp.compress(Bitmap.CompressFormat.JPEG, quality, fw);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        newbmp.recycle();
        bmp.recycle();
        System.gc();
    }

    public static void transImage(Bitmap bmp, String desFile, int maxSize, int quality) {
        if (bmp == null) return;
        int w, h;
        int bw = bmp.getWidth();
        int bh = bmp.getHeight();
        if (bw >= bh) {
            w = maxSize;
            h = bh * w / bw;
        } else {
            h = maxSize;
            w = bw * h / bh;
        }
        Bitmap newbmp = Bitmap.createScaledBitmap(bmp, w, h, false);

        FileOutputStream fw;
        try {
            fw = new FileOutputStream(desFile);
            newbmp.compress(Bitmap.CompressFormat.JPEG, quality, fw);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        newbmp.recycle();
        bmp.recycle();
        System.gc();
    }

    public static Bitmap getCirlceImage(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int size = Math.min(width, height);
        Bitmap roundBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
//		paint.setColor(0xffffffff);
        int s = size / 2;
        canvas.drawCircle(s, s, s, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//	    canvas.drawBitmap(bmp, 0, 0, paint); //it will run faster if use this line code.
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        Rect src = new Rect(x, y, x + size, y + size);
        Rect dst = new Rect(0, 0, size, size);
        canvas.drawBitmap(bmp, src, dst, paint);

        bmp.recycle();
        System.gc();
        return roundBitmap;
    }

}
