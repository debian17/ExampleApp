package com.example.citilin.testapp.ui.webview;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.VideoView;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.network.DownLoadService;
import com.example.citilin.testapp.ui.test.MyBroadcastReceiver;

public class WebViewFragment extends Fragment implements MyBroadcastReceiver.Receive {

    private WebView webView;

    private VideoView videoView;

    private Button youTubeBTN;

    private Counter counter;
    private Button plus;
    private Button minus;

    private Button downLoad;
    private ProgressDialog progressDialog;

    public WebViewFragment() {
    }

    private MyBroadcastReceiver myBroadcastReceiver;
    private static final String DOWNLOAD_URL = "https://api.androidhive.info/progressdialog/hive.jpg";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBroadcastReceiver = new MyBroadcastReceiver(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Ожидайте...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyBroadcastReceiver.UPDATE_PROGRESS);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        counter = (Counter) view.findViewById(R.id.counter);
        plus = (Button) view.findViewById(R.id.counter_plus);
        minus = (Button) view.findViewById(R.id.counter_minus);
        downLoad = (Button) view.findViewById(R.id.downLoad);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.plusOne();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.minusOne();
            }
        });

        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DownLoadService.class);
                intent.putExtra(DownLoadService.DOWNLOAD_URL_KEY, DOWNLOAD_URL);
                getContext().startService(intent);
                progressDialog.setProgress(0);
                //progressDialog.show();
            }
        });

//        webView = (WebView) view.findViewById(R.id.webView);
//
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        //webView.getSettings().setJavaScriptEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        // Configure the client to use when opening URLs
//        webView.setWebViewClient(new MyWebView());
//        // Load the initial URL
//        webView.loadUrl("http://www.example.com");

//        videoView = (VideoView) view.findViewById(R.id.videoView);
//        videoView.setVideoPath("http://techslides.com/demos/sample-videos/small.mp4");
//        MediaController mediaController = new MediaController(getContext());
//
//        mediaController.setAnchorView(videoView);
//
//        videoView.setMediaController(mediaController);
//        videoView.requestFocus();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                videoView.start();
//            }
//        });

//        youTubeBTN = (Button) view.findViewById(R.id.youTubeTest);
//
//        youTubeBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), YouTubeActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public void myAction(String msg) {

    }

    @Override
    public void updateProgress(Intent intent) {
        Log.e("FRAGMENT", String.valueOf(intent.getIntExtra("progress", 0)));
        if (intent.getIntExtra("progress", 0) == 100) {
            progressDialog.dismiss();
        } else {
            progressDialog.incrementProgressBy(1);
        }
    }
}
