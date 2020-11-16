package com.sojay.testfunction.wps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.SignatureUtil;
import com.sojay.testfunction.x5.X5WebView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WPSActivity extends AppCompatActivity {

    private FrameLayout webGroup;
    private X5WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_p_s);

        webGroup = findViewById(R.id.web_group);

        mWebView = new X5WebView(this, null);
        webGroup.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
//                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
//                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
//                    changGoForwardButton(view);
//                /* mWebView.showLog("test Log"); */
            }
        });







        Map<String, String> map = new HashMap<>();
        map.put("_w_filepath", "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx");
        map.put("_w_appid", "7c5157d432754e119561730963937e9f");
        String signature = SignatureUtil.getSignature(map, "7890");
        try {
            String urlParam = SignatureUtil.getUrlParam(map);
            System.out.println("##################    signature = " + signature + "  --  " + urlParam);

            String url = "https://wwo.wps.cn/office/p/a?" +
                    urlParam +
                    "&_w_signature=" + signature;

            // https://wwo.wps.cn/office/p/a?_w_fname=https://fdshfjkhsdjkfhjsd.pptx&_w_appid=dsfhjhsdhjfghjsdgfh&_w_signature=sdkfhjjkdshfjkhds

            System.out.println("##########   url  = " + url);

            mWebView.loadUrl(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }









        String url0 = "https://wwo.wps.cn/office/p/bb753270d99b49d599e47d1912977a4d?_w_userid=-1&_w_filetype=web&_w_filepath=";
        String url1 = "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx";
        String url2 = "&_w_tokentype=1&_w_appid=5b8f173bd752464d81b7aa78001c697f&_w_redirectkey=123456&_w_signature=gXDj4U8tBL8%2Bw7PxaFwC2cYM%2F9Q%3D";




//        String url = "https://wwo.wps.cn/office/p/bb753270d99b49d599e47d1912977a4d?_w_userid=-1&_w_filetype=web&_w_filepath=" +
////                "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx" +
//                "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/《我变成一只喷火龙了》demo课 - 副本.pptx" +
//                "&_w_tokentype=1&_w_appid=5b8f173bd752464d81b7aa78001c697f&_w_redirectkey=123456&_w_signature=gXDj4U8tBL8%2Bw7PxaFwC2cYM%2F9Q%3D";

//        String url = "https://wwo.wps.cn/office/p/0eb1e93e11ef427f92cce3d9bd5c9138?_w_userid=-1&_w_filetype=web&_w_filepath=" +
//                "https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx" +
//                "&_w_tokentype=1&_w_appid=5b8f173bd752464d81b7aa78001c697f&_w_redirectkey=123456&_w_signature=gXDj4U8tBL8%2Bw7PxaFwC2cYM%2F9Q%3D";







        // https://wwo.wps.cn/office/p/bb753270d99b49d599e47d1912977a4d?_w_filepath=https://qiyuanwxxcx.oss-cn-beijing.aliyuncs.com/videos/test.pptx&_w_appid=5b8f173bd752464d81b7aa78001c697f


//        mX5WebView.loadUrl(url);
//        mX5WebView.getX5WebChromeClient().setWebListener(interWebListener);
//        mX5WebView.getX5WebViewClient().setWebListener(interWebListener);
    }

    @Override
    protected void onDestroy() {
//        if (mX5WebView != null) {
//            mX5WebView.destroy();
//        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mX5WebView != null) {
//            mX5WebView.resume();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mX5WebView != null) {
//            mX5WebView.stop();
//        }
    }

}
