package model;

public class WordDefinition {
    private final String word;
    private final String definition;
    private final String imageUrl;

    public WordDefinition(String word, String definition, String imageUrl) {
        this.word = word;
        this.definition = definition;
        this.imageUrl = imageUrl;
    }

    public String getWord() { return word; }
    public String getDefinition() { return definition; }
    public String getImageUrl() { return imageUrl; }
}