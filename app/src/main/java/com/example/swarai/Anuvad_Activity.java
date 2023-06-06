package com.example.swarai;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
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

public class Anuvad_Activity extends AppCompatActivity {

    ImageView mic;
    TextView OutoutText;

    MaterialButton inputSelectLanguage,outputSelectLanguage;

    private ArrayList<ModalLanguage> languageArrayList;

    private  String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";
    private  String destinationLanguageCode = "hi";
    private String destinationLanguageTitle = "Hindi";


    private TranslatorOptions translatorOptions;

    private Translator translator;

    public static final int REQUEST_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuvad);

        loadAvailableLanguages();


        mic = findViewById(R.id.mic);
        OutoutText = findViewById(R.id.OutoutText);

        inputSelectLanguage = findViewById(R.id.inputSelectLanguage);
        outputSelectLanguage = findViewById(R.id.outputSelectLanguage);

        inputSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sourceLanguageChoose();

            }
        });

        outputSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destinationLanguageChoose();

            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to convert into text");


                try {
                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Anuvad_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    private void EndTranslation(String sourceLanguageCode,String destinationLanguageCode,String source){

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


                        translator.translate(source)
                                .addOnSuccessListener(new OnSuccessListener<String>(){
                                    @Override
                                    public void onSuccess(String translatedText){

                                        Log.d(TAG,"onSuccess: translatedText"+translatedText);


                                        OutoutText.setText(translatedText);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e){


                                        Log.e(TAG,"onFailure:",e);
                                        Toast.makeText(Anuvad_Activity.this, "Failed To translate due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){

                        Log.e(TAG,"onFailure:",e);

                        Toast.makeText(Anuvad_Activity.this, "Failed to ready model due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private  void sourceLanguageChoose(){

        PopupMenu popupMenu = new PopupMenu(this,inputSelectLanguage);

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

                inputSelectLanguage.setText(sourceLanguageTitle);

                Log.d(TAG,"onMenuItemClick:sourceLanguageCode:"+ sourceLanguageCode);
                Log.d(TAG,"onMenuItemClick:sourceLanguageTitle:"+ sourceLanguageTitle);

                return false;
            }
        });
    }

    private void destinationLanguageChoose(){

        PopupMenu popupMenu = new PopupMenu(this,outputSelectLanguage);

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

                outputSelectLanguage.setText(destinationLanguageTitle);

                Log.d(TAG,"onMenuItemClick: destinationLanguageCode :+ destinationLanguageCode");
                Log.d(TAG,"onMenuItemClick: destinationLanguageTitle :+ destinationLanguageTitle");

                return false;
            }
        });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (resultCode ==  RESULT_OK && data!=null) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                OutoutText.setText(results.get(0));


                EndTranslation(sourceLanguageCode,destinationLanguageCode,OutoutText.getText().toString());


            }
        }

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

