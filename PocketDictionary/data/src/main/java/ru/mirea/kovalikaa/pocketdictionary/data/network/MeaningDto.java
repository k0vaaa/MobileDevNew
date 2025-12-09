package ru.mirea.kovalikaa.pocketdictionary.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MeaningDto {

    @SerializedName("partOfSpeech")
    @Expose
    public String partOfSpeech;

    @SerializedName("definitions")
    @Expose
    public List<DefinitionDto> definitions;

    public String getPartOfSpeech() { return partOfSpeech; }
    public List<DefinitionDto> getDefinitions() { return definitions; }
}