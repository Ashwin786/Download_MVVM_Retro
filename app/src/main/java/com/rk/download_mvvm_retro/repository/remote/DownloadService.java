package com.rk.download_mvvm_retro.repository.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

interface DownloadService {
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
