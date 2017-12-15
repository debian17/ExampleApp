package com.example.citilin.testapp.ui.about;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citilin.testapp.BuildConfig;
import com.example.citilin.testapp.R;

public class AboutFragment extends Fragment implements AboutPresenter.View {

    private TextView tvAbout;
    private TextView tvVersionNumber;
    private TextView tvVersionCode;
    private TextView tvCacheSize;
    private Button btnClearCache;
    private ProgressDialog progressDialog;

    private AboutPresenter aboutPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutPresenter = new AboutPresenter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAbout = (TextView) view.findViewById(R.id.tvAbout);
        tvVersionNumber = (TextView) view.findViewById(R.id.tvVersvionNumber);
        tvVersionCode = (TextView) view.findViewById(R.id.tvVersionCode);
        tvCacheSize = (TextView) view.findViewById(R.id.tvCacheSize);
        btnClearCache = (Button) view.findViewById(R.id.btnClearCache);

        btnClearCache.setOnClickListener(v -> aboutPresenter.clearCache());

        tvAbout.setText(R.string.about_app);
        tvVersionNumber.setText(getString(R.string.version_number, BuildConfig.VERSION_NAME));
        tvVersionCode.setText(getString(R.string.version_code, BuildConfig.VERSION_CODE));

        aboutPresenter.attachView(this);

        aboutPresenter.calculateCacheSize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        aboutPresenter.detachView();
    }

    @Override
    public void startProgress() {
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.sharing_process), true, false);
    }

    @Override
    public void stopProgress() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void cacheCleaned() {
        tvCacheSize.setText(getString(R.string.cache_size, Formatter.formatFileSize(getContext(), 0)));

        Toast.makeText(getContext(), getString(R.string.cache_clean_success), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void cacheCleanFailed() {
        tvCacheSize.setText(getString(R.string.cache_size, getString(R.string.unknown)));
        Toast.makeText(getContext(), getString(R.string.cache_clean_failed), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void cacheCleanError() {
        tvCacheSize.setText(getString(R.string.cache_size, getString(R.string.unknown)));
        Toast.makeText(getContext(), getString(R.string.cache_clean_failed), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void calculateCacheSizeSuccess(String cacheSize) {
        tvCacheSize.setText(getString(R.string.cache_size, cacheSize));
    }

    @Override
    public void calculateCacheSizeFailed() {
        tvCacheSize.setText(getString(R.string.cache_size, getString(R.string.unknown)));
        Toast.makeText(getContext(), getString(R.string.cache_calculating_fail), Toast.LENGTH_SHORT)
                .show();
    }

}
