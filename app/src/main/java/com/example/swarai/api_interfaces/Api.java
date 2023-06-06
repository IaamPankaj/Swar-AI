package com.example.swarai.api_interfaces;


import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @Multipart
    @POST("upload_files")
     Call<ResponseBody> uploadFile(@Part("audios") RequestBody file);


}
