package ru.mirea.kovalikaa.pocketdictionary.data.network;

import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;

import java.io.IOException;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {

    private final DictionaryApi dictionaryApi;

    public NetworkApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.dictionaryApi = retrofit.create(DictionaryApi.class);
    }

    public WordDataModel getDefinitionFromNetwork(String word) {
        System.out.println("Fetching '" + word + "' from REAL network...");
        try {
            List<WordDefinitionDto> response = dictionaryApi.getDefinition(word).execute().body();

            if (response != null && !response.isEmpty()) {
                WordDefinitionDto firstResult = response.get(0);
                for (MeaningDto meaning : firstResult.meanings) {
                    if (meaning.definitions != null && !meaning.definitions.isEmpty()) {
                        String definitionText = meaning.definitions.get(0).definition;
                        return new WordDataModel(word, definitionText, System.currentTimeMillis());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new WordDataModel(word, "Error: " + e.getMessage(), System.currentTimeMillis());
        }
        return new WordDataModel(word, "Definition not found for this word.", System.currentTimeMillis());
    }
}