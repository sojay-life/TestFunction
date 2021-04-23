package com.sojay.testfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.sojay.testfunction.utils.SignatureUtil;
import com.sojay.testfunction.view.PaintView;
import com.sojay.testfunction.view.PathMeasureView;
import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.utils.X5WebUtils;
import com.ycbjie.webviewlib.widget.WebProgress;
import com.ycbjie.webviewlib.wv.X5WvWebView;

import java.util.HashMap;
import java.util.Map;

public class H5Activity extends AppCompatActivity {

    private X5WvWebView mWebView;
    private WebProgress progress;
    private PaintView mPaintView;
    private PathMeasureView path_measure_view;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;

    private boolean isPaintEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        initView();

    }

    public void initView() {
        mWebView = findViewById(R.id.web_view1);
        progress = findViewById(R.id.progress);
        mPaintView = findViewById(R.id.paint_view);
        path_measure_view = findViewById(R.id.path_measure_view);
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tv4 = findViewById(R.id.tv_4);
        tv5 = findViewById(R.id.tv_5);
        tv6 = findViewById(R.id.tv_6);

        progress.show();
        progress.setColor(this.getResources().getColor(R.color.purple_500));
        mWebView.getX5WebChromeClient().setWebListener(interWebListener);
        mWebView.getX5WebViewClient().setWebListener(interWebListener);

//        String url = "http://lrs.cheerup-edu.cn/wps/index.html";
//        String url = "https://appnew.cheerup-edu.cn/wx/ppt2.html";
//        String url = "https://appnew.cheerup-edu.cn/zbdlw/index.html";
        String url = "https://m.cheerup-edu.cn/kejian/kejian.html?course_id=13";


        mWebView.addJavascriptInterface(new JavascriptApi() {
            @Override
            public void orderStatus(String s) {
                System.out.println("##########   s = " + s);
            }
        }, "course");

        mWebView.loadUrl(url);

        mPaintView.setCanEdit(false);

        tv1.setOnClickListener(v -> mPaintView.setPaintColor(R.color.black));

        tv2.setOnClickListener(v -> mPaintView.setPaintColor(R.color.purple1));

        tv3.setOnClickListener(v -> mPaintView.setXfermode());

        tv4.setOnClickListener(v -> mPaintView.clearAll());

        tv5.setOnClickListener(v -> {
            isPaintEnable = !isPaintEnable;
            mPaintView.setCanEdit(isPaintEnable);
        });

        tv6.setOnClickListener(v -> {
            Path path = new Path();
            path.lineTo(0, 0);
            path.lineTo(200, 0);
            path.lineTo(200, 200);
            path.lineTo(400, 200);
            path.lineTo(400, 400);
            path.lineTo(600, 400);
//            path_measure_view.setPath(Constant.path);
            path_measure_view.setPath(path);
            path_measure_view.startMove();
        });
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


    /**
     * 上一步，这里通知h5切换到上一步
     */
    public void previous() {
//        paintView.clearAll();
        mWebView.loadUrl("javascript:get_android_base('up')");
    }

    /**
     * 下一步，这里设置h5切换到下一步
     */
    public void nextStep() {
//        paintView.clearAll();
        mWebView.loadUrl("javascript:get_android_base('down')");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Toast.makeText(this, "code = " + keyCode, Toast.LENGTH_SHORT).show();

        if (event.getKeyCode() == KeyEvent.KEYCODE_PAGE_UP) {
            previous();
            return true;
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_PAGE_DOWN) {
            nextStep();
            return true;
        }

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
