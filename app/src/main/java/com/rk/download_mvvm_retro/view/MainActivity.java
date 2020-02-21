package com.rk.download_mvvm_retro.view;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rk.download_mvvm_retro.R;
import com.rk.download_mvvm_retro.viewModel.DownloadViewModel;

public class MainActivity extends AppCompatActivity {
    Button btn_download;
    private DownloadViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_download = (Button) findViewById(R.id.download_button);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call_permission()) {
                    viewModel.downloadFile();
                }
            }
        });
        viewModel = ViewModelProviders.of(this).get(DownloadViewModel.class);
        Observer<String> downloadResultObserver = new Observer<String>() {

            @Override
            public void onChanged(@NonNull String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getDownloadStatus().observe(this,downloadResultObserver);
    }

    private boolean call_permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return false;
            }
        }
        return true;
    }
}
