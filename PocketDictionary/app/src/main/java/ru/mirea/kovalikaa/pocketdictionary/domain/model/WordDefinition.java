package ru.mirea.kovalikaa.pocketdictionary.domain.model;

public class WordDefinition {
    private final String word;
    private final String definition;

    public WordDefinition(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() { return word; }
    public String getDefinition() { return definition; }
}