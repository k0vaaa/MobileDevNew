package ru.mirea.kovalikaa.pocketdictionary.data.storage.models;

public class WordDataModel {
    private final String word;
    private final String definition;
    private final String imageUrl;
    private final long saveTimestamp;

    public WordDataModel(String word, String definition, String imageUrl, long saveTimestamp) {
        this.word = word;
        this.definition = definition;
        this.imageUrl = imageUrl;
        this.saveTimestamp = saveTimestamp;
    }

    public String getWord() { return word; }
    public String getDefinition() { return definition; }
    public String getImageUrl() { return imageUrl; }
    public long getSaveTimestamp() { return saveTimestamp; }
}