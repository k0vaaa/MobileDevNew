package ru.mirea.kovalikaa.pocketdictionary.data.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    Call<List<WordDefinitionDto>> getDefinition(@Path("word") String word);
}