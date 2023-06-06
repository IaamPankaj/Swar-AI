package com.example.swarai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swarai.api_interfaces.Api;
import com.example.swarai.services.RetrofitService;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Gender_Activity extends AppCompatActivity {

    private Button speechBtn,uploadBtn;
    private TextView iSpeechTv;
    private Button predict;

    private Bitmap bitmap;

    int AUDIO = 0;

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int PICKFILE_REQUEST_CODE = 101;
    String uploadUrl;

    Retrofit retrofit;
    Api apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        retrofit = RetrofitService.getService();

         apiService = retrofit.create(Api.class);

        speechBtn = findViewById(R.id.speechBtn);
        iSpeechTv = findViewById(R.id.iSpeechTv);
        uploadBtn = findViewById(R.id.uploadBtn);

        predict = findViewById(R.id.predict);


        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to convert into text");


                try {
                    startActivityForResult(intent, 100);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Gender_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });



        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload,1);


            }
        });



        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFileApi();

            }
        });

    }

    private void uploadFileApi() {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("audio/*");
        File file = new File(uploadUrl.replace("/document/raw:" , ""));
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        Call<ResponseBody> call = apiService.uploadFile(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                ResponseBody data = response.body();

                Log.d("API_OUTPUT", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("API_OUTPUT", t.toString());

                //Handle failure
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                Uri uri = data.getData();
                uploadUrl = uri.getPath();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        switch (requestCode) {
//            case PICKFILE_RESULT_CODE:
//                if (resultCode == RESULT_OK) {
//                    String FilePath = data.getData().getPath();
//                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    iSpeechTv.setText(results.get(0));
//                    uploadBtn.setText(FilePath);
//
//                }
//                break;
//        }
//
//
//    }
}