package ru.mirea.kovalikaa.pocketdictionary.data.storage;


import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;

public interface WordStorage {
    boolean save(WordDataModel word);
    WordDataModel get(String word);
}