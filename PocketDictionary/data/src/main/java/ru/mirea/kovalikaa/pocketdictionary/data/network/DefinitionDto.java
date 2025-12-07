package ru.mirea.kovalikaa.pocketdictionary.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefinitionDto {

    @SerializedName("definition")
    @Expose
    public String definition;

    @SerializedName("example")
    @Expose
    public String example;

    public String getDefinition() { return definition; }
    public String getExample() { return example; }
}