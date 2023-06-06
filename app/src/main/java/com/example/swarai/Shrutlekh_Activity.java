package com.example.swarai;

import static android.content.ContentValues.TAG;

import static com.example.swarai.Anuvad_Activity.REQUEST_PERMISSION_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.mlkit.nl.translate.TranslateLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Shrutlekh_Activity extends AppCompatActivity {


    int AUDIO = 0;

MaterialButton btnSpeak,btnUpload;
    MaterialButton selectLanguage;

    private ArrayList<ModalLanguage> languageArrayList;

    private  String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";

    private static final String TAG = "MAIN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrutlekh);


        btnSpeak = findViewById(R.id.btnSpeak);

        btnUpload = findViewById(R.id.btnUpload);

        selectLanguage = findViewById(R.id.selectLanguage);

        loadAvailableLanguages();


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent,"Select Audio"),AUDIO);
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(Shrutlekh_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });


        selectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sourceLanguageChoose();
            }
        });



    }

    private  void sourceLanguageChoose(){

        PopupMenu popupMenu = new PopupMenu(this,selectLanguage);

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

                selectLanguage.setText(sourceLanguageTitle);
//                sourceLanguageEt.setHint("Enter"+sourceLanguageTitle);

                Log.d(TAG,"onMenuItemClick:sourceLanguageCode:"+ sourceLanguageCode);
                Log.d(TAG,"onMenuItemClick:sourceLanguageTitle:"+ sourceLanguageTitle);

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