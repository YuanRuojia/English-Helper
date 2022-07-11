package org.knowyourdenfender.enhelper.dao;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("english_words_table")
public class WordDAO {
    String englishWords;
    String phoneticSymbols;

    String wordsTranslation;
    String difficulty;
    String imagePath;
    String audioPath;

    public String getWord() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public String getSymbol() {
        return phoneticSymbols;
    }

    public void setPhoneticSymbols(String phoneticSymbols) {
        this.phoneticSymbols = phoneticSymbols;
    }

    public String getTrans() {
        return wordsTranslation;
    }

    public String getWordsTranslation()
    {
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
