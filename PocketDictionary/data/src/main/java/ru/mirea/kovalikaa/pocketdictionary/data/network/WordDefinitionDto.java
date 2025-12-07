package ru.mirea.kovalikaa.pocketdictionary.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WordDefinitionDto {

    @SerializedName("word")
    @Expose
    public String word;

    @SerializedName("meanings")
    @Expose
    public List<MeaningDto> meanings;

    public String getWord() { return word; }
    public List<MeaningDto> getMeanings() { return meanings; }
}
