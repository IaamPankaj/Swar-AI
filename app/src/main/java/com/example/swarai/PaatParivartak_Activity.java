package com.example.swarai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.PopupMenu;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;


import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaatParivartak_Activity extends AppCompatActivity {

    private EditText sourceLanguageEt;
    private TextView destinationLanguageTv;
    private MaterialButton sourceLanguageChooseBtn,destinationLanguageChooseBtn;
    private MaterialButton translateBtn;

    private TranslatorOptions translatorOptions;

    private Translator translator;

    private ProgressDialog progressDialog;

    private ArrayList<ModalLanguage> languageArrayList;

    private static final String TAG = "MAIN_TAG";


    private  String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";
    private  String destinationLanguageCode = "hi";
    private String destinationLanguageTitle = "Hindi";


    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paat_parivartak);

        sourceLanguageEt = findViewById(R.id.sourceLanguageEt);
        destinationLanguageTv = findViewById(R.id.destinationLanguageTv);
        sourceLanguageChooseBtn = findViewById(R.id.sourceLanguageChooseBtn);
        destinationLanguageChooseBtn = findViewById(R.id.destinationLanguageChooseBtn);
        translateBtn = findViewById(R.id.translateBtn);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadAvailableLanguages();


        sourceLanguageChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sourceLanguageChoose();
            }
        });

        destinationLanguageChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destinationLanguageChoose();
            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateData();
            }
        });



    }

    private String sourceLanguageText = "";



    private void validateData() {

        sourceLanguageText = sourceLanguageEt.getText().toString().trim();

        Log.d(TAG,"validateData:sourceLanguageText :" +sourceLanguageText);

        if (sourceLanguageText.isEmpty()) {
            Toast.makeText(this, "Enter Text To Translate", Toast.LENGTH_SHORT).show();
        }else{
            startTranslations();
        }

    }

    private void startTranslations() {

        progressDialog.setMessage("Processing Language Model...");
        progressDialog.show();


        translatorOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(destinationLanguageCode)
                .build();

        translator = Translation.getClient(translatorOptions);


        DownloadConditions downloadConditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void unused){

                        Log.d(TAG,"onSuccess: model ready,starting translate....");

                        progressDialog.setMessage("Translating....");


                        translator.translate(sourceLanguageText)
                                .addOnSuccessListener(new OnSuccessListener<String>(){
                                    @Override
                                    public void onSuccess(String translatedText){

                                        Log.d(TAG,"onSuccess: translatedText"+translatedText);
                                        progressDialog.dismiss();

                                        destinationLanguageTv.setText(translatedText);

                                        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                            @Override
                                            public void onInit(int i) {
                                                if(i != TextToSpeech.ERROR){
                                                    t1.setLanguage(Locale.JAPANESE);
                                                }
                                            }
                                        });


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e){

                                        progressDialog.dismiss();
                                        Log.e(TAG,"onFailure:",e);
                                        Toast.makeText(PaatParivartak_Activity.this, "Failed To translate due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){

                        Log.e(TAG,"onFailure:",e);
                        progressDialog.dismiss();
                        Toast.makeText(PaatParivartak_Activity.this, "Failed to ready model due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private  void sourceLanguageChoose(){

        PopupMenu popupMenu = new PopupMenu(this,sourceLanguageChooseBtn);

        for (int i = 0; i < languageArrayList.size(); i++ ){

            popupMenu.getMenu().add(Menu.NONE,i,i,languageArrayList.get(i).LanguageTitle);
        }

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int position = menuItem.getItemId();

                sourceLanguageCode = languageArrayList.get(position).LanguageCode;
                sourceLanguageTitle = languageArrayList.get(position).LanguageTitle;

                sourceLanguageChooseBtn.setText(sourceLanguageTitle);
                sourceLanguageEt.setHint("Enter " + sourceLanguageTitle);

                Log.d(TAG,"onMenuItemClick:sourceLanguageCode:"+ sourceLanguageCode);
                Log.d(TAG,"onMenuItemClick:sourceLanguageTitle:"+ sourceLanguageTitle);

                return false;
            }
        });
    }

    private void destinationLanguageChoose(){

        PopupMenu popupMenu = new PopupMenu(this,destinationLanguageChooseBtn);

        for (int i = 0; i < languageArrayList.size(); i++ ){

            popupMenu.getMenu().add(Menu.NONE,i ,i,languageArrayList.get(i).getLanguageTitle()  );
        }

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int position = menuItem.getItemId();

                destinationLanguageCode = languageArrayList.get(position).LanguageCode;
                destinationLanguageTitle = languageArrayList.get(position).LanguageTitle;

                destinationLanguageChooseBtn.setText(destinationLanguageTitle);

                Log.d(TAG,"onMenuItemClick: destinationLanguageCode :+ destinationLanguageCode");
                Log.d(TAG,"onMenuItemClick: destinationLanguageTitle :+ destinationLanguageTitle");

                return false;
            }
        });
    }

    private void loadAvailableLanguages() {

        languageArrayList  = new ArrayList<>();

        List<String> languageCodeList = TranslateLanguage.getAllLanguages();

        for(String languageCode : languageCodeList){
            String languageTitle = new Locale(languageCode).getDisplayLanguage();

            Log.d(TAG,"loadAvailableLanguages:  languageCode: "+languageCode);
            Log.d(TAG,"loadAvailableLanguages: languageTitle: "+languageTitle);

            ModalLanguage modalLanguage = new ModalLanguage(languageCode,languageTitle);
            languageArrayList.add(modalLanguage);
        }
    }
}
