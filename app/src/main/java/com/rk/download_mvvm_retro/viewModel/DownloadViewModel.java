package com.rk.download_mvvm_retro.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.rk.download_mvvm_retro.GlobalApp;
import com.rk.download_mvvm_retro.repository.remote.Repository;


public class DownloadViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<String>();

    public LiveData<String> getDownloadStatus() {
        return liveData;
    }

    public void downloadFile() {
        Repository.getInstance().downloadFile(GlobalApp.imageUrl, liveData);
    }

}
