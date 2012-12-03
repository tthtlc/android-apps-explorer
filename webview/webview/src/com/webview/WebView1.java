/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.webview.R;


/**
 * Sample creating 10 webviews.
 */
public class WebView1 extends Activity {
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.webview_1);
        
        final String mimeType = "text/html";
        final String encoding = "utf-8";
        
        WebView wv;
        
        wv = (WebView) findViewById(R.id.wv1);
        wv.loadData("<a href='http://stackoverflow.com/questions/tagged/android'>Android at Stackoverflow </a>", mimeType, encoding);
        
        wv = (WebView) findViewById(R.id.wv2);
        wv.loadData("<a href='http://www.google.com.sg/search?q=site%3Aandroid-developers.blogspot.com'> Android Blogs </a>", mimeType, encoding);

        wv = (WebView) findViewById(R.id.wv3);
        wv.loadData("<a href='www.androidpit.com'>AndroidPIT</a>", mimeType, encoding);
    }
}
