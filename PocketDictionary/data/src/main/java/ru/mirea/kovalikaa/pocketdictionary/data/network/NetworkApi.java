package ru.mirea.kovalikaa.pocketdictionary.data.network;

import android.util.Log;

import retrofit2.Response;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {

    private final DictionaryApi dictionaryApi;
    private static final String TAG = "NetworkApi";

    public NetworkApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.dictionaryApi = retrofit.create(DictionaryApi.class);
    }

    public WordDataModel getDefinitionFromNetwork(String word) {
        Log.i(TAG, "Fetching '" + word + "' from REAL network...");
        try {
            Response<List<WordDefinitionDto>> response = dictionaryApi.getDefinition(word).execute();
            Log.i(TAG, "Response received with code: " + response.code());

            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                WordDefinitionDto firstResult = response.body().get(0);

                if (firstResult != null && firstResult.meanings != null && !firstResult.meanings.isEmpty() &&
                        firstResult.meanings.get(0).definitions != null && !firstResult.meanings.get(0).definitions.isEmpty()) {

                    String definitionText = firstResult.meanings.get(0).definitions.get(0).definition;
                    Log.i(TAG, "Definition found: " + definitionText);
//                    int imageId = Math.abs(word.hashCode() % 1000);
//                    String imageUrl = "https://picsum.photos/400/200.jpg?random=" + imageId;
                    int size = 400 + (Math.abs(word.hashCode()) % 10);
                    String imageUrl = "https://place.dog/" + size + "/" + size;

                    return new WordDataModel(word, definitionText, imageUrl, System.currentTimeMillis());
                } else {
                    Log.w(TAG, "Response body is valid, but definitions list is empty.");
                }
            } else {
                Log.e(TAG, "Response was not successful or body is empty. Code: " + response.code());
            }
        } catch (Exception e) {
            Log.e(TAG, "Network request failed or JSON parsing error", e);
        }

        return new WordDataModel(word, "Definition not found", null, System.currentTimeMillis());
    }
}