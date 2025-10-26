package ru.mirea.kovalikaa.pocketdictionary.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;


public class SharedPrefWordStorage implements WordStorage {

    private static final String PREFERENCES_NAME = "dictionary_settings";
    private static final String KEY_DEFINITION_SUFFIX = "_definition";
    private static final String KEY_TIMESTAMP_SUFFIX = "_timestamp";
    private static final String DEFAULT_VALUE = "No definition found";

    private final SharedPreferences sharedPreferences;

    public SharedPrefWordStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(WordDataModel word) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(word.getWord() + KEY_DEFINITION_SUFFIX, word.getDefinition());
        editor.putLong(word.getWord() + KEY_TIMESTAMP_SUFFIX, word.getSaveTimestamp());
        editor.apply();
        return true;
    }

    @Override
    public WordDataModel get(String word) {
        String definition = sharedPreferences.getString(word + KEY_DEFINITION_SUFFIX, DEFAULT_VALUE);
        long timestamp = sharedPreferences.getLong(word + KEY_TIMESTAMP_SUFFIX, 0);
        return new WordDataModel(word, definition, timestamp);
    }
}