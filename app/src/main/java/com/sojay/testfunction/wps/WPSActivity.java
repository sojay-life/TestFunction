package com.sojay.testfunction.wps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.SignatureUtil;
import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.utils.X5WebUtils;
import com.ycbjie.webviewlib.widget.WebProgress;
import com.ycbjie.webviewlib.wv.X5WvWebView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WPSActivity extends AppCompatActivity {

    private X5WvWebView mWebView;
    private WebProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_p_s);

        initView();

    }

    public void initView() {
        mWebView = findViewById(R.id.web_view1);
        progress = findViewById(R.id.progress);
        progress.show();
        progress.setColor(this.getResources().getColor(R.color.purple_500));
        mWebView.getX5WebChromeClient().setWebListener(interWebListener);
        mWebView.getX5WebViewClient().setWebListener(interWebListener);

        String u = getIntent().getStringExtra("url");

        if (u == null || TextUtils.isEmpty(u)) {

            // wps 是以H5的形式呈现的
            // 管理平台中配置回调地址  https://xx.com/xx
            // wps回调地址：v1/3rd/file/info 以参数形式配置路由
            // 后台返回数据

            // 因为移动端使用webview加载，所以签名不需要 URLEncoder.encode

           Map<String, String> map = new HashMap<>();
            map.put("_w_appid", "7c5157d432754e119561730963937e9f");
//        map.put("_w_fname", "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx");
            map.put("_w_fname", "aaaaa");
//            map.put("_w_mode", "simple");
//            map.put("_w_hidecmb", "1");

            String signature = SignatureUtil.getSignature(map, "b746d66fa7934450a01c2c1b52a65329");

            String urlParam = SignatureUtil.getUrlParam(map);

            System.out.println("##################    signature = " + signature + "  --  " + urlParam);

            String url = "https://wwo.wps.cn/office/p/a?" +
                    urlParam +
                    "_w_signature=" + signature;

            System.out.println("##########   url  = " + url);

            mWebView.loadUrl(url);

        } else {
            mWebView.loadUrl(u);
        }


    }

    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            progress.hide();
        }

        @Override
        public void showErrorView(@X5WebUtils.ErrorType int type) {
            switch (type){
                //没有网络
                case X5WebUtils.ErrorMode.NO_NET:
                    break;
                //404，网页无法打开
                case X5WebUtils.ErrorMode.STATE_404:

                    break;
                //onReceivedError，请求网络出现error
                case X5WebUtils.ErrorMode.RECEIVED_ERROR:

                    break;
                //在加载资源时通知主机应用程序发生SSL错误
                case X5WebUtils.ErrorMode.SSL_ERROR:

                    break;
                default:
                    break;
            }
        }

        @Override
        public void startProgress(int newProgress) {
            progress.setWebProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack() && event.getKeyCode() ==
                KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.clearHistory();
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.destroy();
            //mWebView = null;
        }
        super.onDestroy();
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(false);
        }
    }

}
