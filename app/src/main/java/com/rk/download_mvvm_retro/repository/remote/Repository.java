package com.rk.download_mvvm_retro.repository.remote;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;
    private final DownloadService downloadService;
    private static final String TAG = "Repository";
    private static Context mContext;

    public Repository() {

        downloadService = RetroServices.getClient().create(DownloadService.class);
    }

    public static Repository getInstance() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    public void downloadFile(String downloadUrl, final MutableLiveData<String> liveData) {
        Call<ResponseBody> downloadData = downloadService.downloadFile(downloadUrl);
        downloadData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                    if (writtenToDisk)
                        liveData.postValue("Success");
                    else
                        liveData.postValue("Failure");
                } else
                    liveData.postValue("Failure");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                liveData.postValue("Failure");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            Log.e(TAG, "getExternalFilesDir: "+Environment.getExternalStorageDirectory());
//            File futureStudioIconFile = new File(mContext.getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
            File file = new File(Environment.getExternalStorageDirectory(), "SampleDownload.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
