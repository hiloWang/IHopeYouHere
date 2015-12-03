package com.hilo.others;

import android.app.Application;
import android.content.Context;

import com.hilo.R;
import com.hilo.util.LogUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Created by hilo on 15/12/1.
 * <p/>
 * Drscription:
 *
 * 1)
 * 使用@Event事件注解(@ContentView, @ViewInject等更多示例参考sample项目)

     * 1. 方法必须私有限定,
     * 2. 方法以Click或Event结尾, 方便配置混淆编译参数 :
     * -keepattributes *Annotation*
     * -keepclassmembers class * {
     * void *(android.view.View);
     * *** *Click(...);
     * *** *Event(...);
     * }
     * 3. 方法参数形式必须和type对应的Listener接口一致.
     * 4. 注解参数value支持数组: value={id1, id2, id3}
     * 5. 其它参数说明见{@link org.xutils.event.annotation.Event}类的说明.
        @Event(value = R.id.btn_test_baidu1,
        type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class)
        private vid onTestBaidu1Click(View view) {
        ...
        }

 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // Xutils init
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

        Configuration cfg = Configuration.getConfig();
        Configuration.saveConfig();

        cfg.deviceType = 1;
        if (getResources().getBoolean(R.bool.isTablet)) cfg.deviceType = 2;
        cfg.deviceName = android.os.Build.BRAND + "_" + android.os.Build.PRODUCT;
        LogUtils.I("mlog", "deviceName: " + cfg.deviceName);
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(this);
    }
}
