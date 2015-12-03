# IHopeYouHere
    
   - MateriaNavgaion and Toolbar
   
   
   
    * 1. BaseActivity 为 Activity 栈管理类, 其实这样管理并不是理想的, 容易引起内存泄露.
    * 2. HttpClient.XX; // 自己封装的, 请求网络方式
    * 3. new ImageUtils(imageView, url); // iamgeLoader 加载网络图片方式
    * 4. DialogManager.getInstance().XX // dialog 动画调用方式
    
    
    
    
                                       #### tips ####
 
### XUtils:
 添加Android默认混淆配置${sdk.dir}/tools/proguard/proguard-android.txt
 不要混淆xUtils中的注解类型，添加混淆配置：-keep class * extends java.lang.annotation.Annotation { *; }
 对使用DbUtils模块持久化的实体类不要混淆，或者注解所有表和列名称@Table(name="xxx")，@Id(column="xxx")，@Column(column="xxx"),@Foreign(column="xxx",foreign="xxx")；
 

## 使用@Event事件注解(@ContentView, @ViewInject等更多示例参考sample项目)

/**
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
 **/
@Event(value = R.id.btn_test_baidu1,
        type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
private void onTestBaidu1Click(View view) {
...
}

## 请求网络
 /**
  * 自定义实体参数类请参考:
  * 请求注解 {@link org.xutils.http.annotation.HttpRequest}
  * 请求注解处理模板接口 {@link org.xutils.http.app.ParamsBuilder}
  *
  * 需要自定义类型作为callback的泛型时, 参考:
  * 响应注解 {@link org.xutils.http.annotation.HttpResponse}
  * 响应注解处理模板接口 {@link org.xutils.http.app.ResponseParser}
  *
  * 示例: 查看 org.xutils.sample.http 包里的代码
  */
 BaiduParams params = new BaiduParams();
 params.wd = "xUtils";
 // 有上传文件时使用multipart表单, 否则上传原始文件流.
 // params.setMultipart(true);
 // 上传文件方式 1
 // params.uploadFile = new File("/sdcard/test.txt");
 // 上传文件方式 2
 // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
 Callback.Cancelable cancelable
        = x.http().get(params,
        /**
         * 1. callback的泛型:
         * callback参数默认支持的泛型类型参见{@link org.xutils.http.loader.LoaderFactory},
         * 例如: 指定泛型为File则可实现文件下载, 使用params.setSaveFilePath(path)指定文件保存的全路径.
         * 默认支持断点续传(采用了文件锁和尾端校验续传文件的一致性).
         * 其他常用类型可以自己在LoaderFactory中注册,
         * 也可以使用{@link org.xutils.http.annotation.HttpResponse}
         * 将注解HttpResponse加到自定义返回值类型上, 实现自定义ResponseParser接口来统一转换.
         * 如果返回值是json形式, 那么利用第三方的json工具将十分容易定义自己的ResponseParser.
         * 如示例代码{@link org.xutils.sample.http.BaiduResponse}, 可直接使用BaiduResponse作为
         * callback的泛型.
         *
         * 2. callback的组合:
         * 可以用基类或接口组合个种类的Callback, 见{@link org.xutils.common.Callback}.
         * 例如:
         * a. 组合使用CacheCallback将使请求检测缓存或将结果存入缓存(仅GET请求生效).
         * b. 组合使用PrepareCallback的prepare方法将为callback提供一次后台执行耗时任务的机会,
         * 然后将结果给onCache或onSuccess.
         * c. 组合使用ProgressCallback将提供进度回调.
         * ...(可参考{@link org.xutils.image.ImageLoader}
         * 或 示例代码中的 {@link org.xutils.sample.download.DownloadCallback})
         *
         * 3. 请求过程拦截或记录日志: 参考 {@link org.xutils.http.app.RequestTracker}
         *
         * 4. 请求Header获取: 参考 {@link org.xutils.http.app.InterceptRequestListener}
         *
         * 5. 其他(线程池, 超时, 重定向, 重试, 代理等): 参考 {@link org.xutils.http.RequestParams
         *
         **/
        new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onFinished() {
 
            }
        });
 
      // cancelable.cancel(); // 取消
      // 如果需要记录请求的日志, 可使用RequestTracker接口(优先级依次降低, 找到一个实现后会忽略后面的):
      // 1. 自定义Callback同时实现RequestTracker接口;
      // 2. 自定义ResponseParser同时实现RequestTracker接口;
      // 3. 在LoaderFactory注册.
      
## 网络请求: 如果你只需要一个简单的版本:  
   @Event(value = R.id.btn_test_baidu2)
   private void onTestBaidu2Click(View view) {
       RequestParams params = new RequestParams("https://www.baidu.com/s");
       params.setSslSocketFactory(...); // 设置ssl
       params.addQueryStringParameter("wd", "xUtils");
       x.http().get(params, new Callback.CommonCallback<String>() {
           @Override
           public void onSuccess(String result) {
               Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
           }
   
           @Override
           public void onError(Throwable ex, boolean isOnCallback) {
               Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
           }
   
           @Override
           public void onCancelled(CancelledException cex) {
               Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
           }
   
           @Override
           public void onFinished() {
   
           }
       });
   }
## 带缓存的网络请求
    BaiduParams params = new BaiduParams();
    params.wd = "xUtils";
    // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
    params.setCacheMaxAge(1000 * 60);
    Callback.Cancelable cancelable
            // 使用CacheCallback, xUtils将为该请求缓存数据.
            = x.http().get(params, new Callback.CacheCallback<String>() {
    
        private boolean hasError = false;
        private String result = null;
    
        @Override
        public boolean onCache(String result) {
            // 得到缓存数据, 缓存过期后不会进入这个方法.
            // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
            //
            // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
            //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
            //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
            //
            // * 如果信任该缓存返回 true, 将不再请求网络;
            //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
            //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
            //
            this.result = result;
            return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
        }
    
        @Override
        public void onSuccess(String result) {
            // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
            // 但是 onFinished 总会被调用.
            this.result = result;
        }
    
        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            hasError = true;
            Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            if (ex instanceof HttpException) { // 网络错误
                HttpException httpEx = (HttpException) ex;
                int responseCode = httpEx.getCode();
                String responseMsg = httpEx.getMessage();
                String errorResult = httpEx.getResult();
                // ...
            } else { // 其他错误
                // ...
            }
        }
    
        @Override
        public void onCancelled(CancelledException cex) {
            Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
        }
    
        @Override
        public void onFinished() {
            if (!hasError && result != null) {
                // 成功获取数据
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }
        }
    });
    
## 使用数据库
Parent test = db.selector(Parent.class).where("id", "in", new int[]{1, 3, 6}).findFirst();
long count = db.selector(Parent.class).where("name", "LIKE", "w%").and("age", ">", 32).count();
List<Parent> testList = db.selector(Parent.class).where("id", "between", new String[]{"1", "5"}).findAll();

## 绑定图片
x.image().bind(imageView, url, imageOptions);

// assets file
x.image().bind(imageView, "assets://test.gif", imageOptions);

// local file
x.image().bind(imageView, new File("/sdcard/test.gif").toURI().toString(), imageOptions);
x.image().bind(imageView, "/sdcard/test.gif", imageOptions);
x.image().bind(imageView, "file:///sdcard/test.gif", imageOptions);
x.image().bind(imageView, "file:/sdcard/test.gif", imageOptions);

x.image().bind(imageView, url, imageOptions, new Callback.CommonCallback<Drawable>() {...});
x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {...});
x.image().loadFile(url, imageOptions, new Callback.CommonCallback<File>() {...});
 