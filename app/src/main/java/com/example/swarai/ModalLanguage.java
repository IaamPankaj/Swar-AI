package com.example.swarai;

public class ModalLanguage {

    String LanguageCode;
    String LanguageTitle;

    public ModalLanguage(String languageCode, String languageTitle) {
        LanguageCode = languageCode;
        LanguageTitle = languageTitle;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getLanguageTitle() {
        return LanguageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        LanguageTitle = languageTitle;
    }
}
