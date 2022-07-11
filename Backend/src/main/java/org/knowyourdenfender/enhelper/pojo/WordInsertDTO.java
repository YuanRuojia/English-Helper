package org.knowyourdenfender.enhelper.pojo;

public class WordInsertDTO {
    String englishWords;
    String phoneticSymbols;
    String wordsTranslation;
    String difficulty;
    String imagePath;
    String audioPath;

    public String getEnglishWords() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public String getPhoneticSymbols() {
        return phoneticSymbols;
    }

    public void setPhoneticSymbols(String phoneticSymbols) {
        this.phoneticSymbols = phoneticSymbols;
    }

    public String getWordsTranslation() {
        return wordsTranslation;
    }

    public void setWordsTranslation(String wordsTranslation) {
        this.wordsTranslation = wordsTranslation;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}
