package com.sojay.testfunction;

import android.webkit.JavascriptInterface;

public interface JavascriptApi {

    @JavascriptInterface
    void orderStatus(String s);

}
