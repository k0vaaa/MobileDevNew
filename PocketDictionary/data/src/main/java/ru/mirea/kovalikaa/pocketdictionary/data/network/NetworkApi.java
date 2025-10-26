package ru.mirea.kovalikaa.pocketdictionary.data.network;

import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;

public class NetworkApi {
    public WordDataModel getDefinitionFromNetwork(String word) {
        System.out.println("Fetching '" + word + "' from FAKE network...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new WordDataModel(word, "This is a FAKE definition from the NETWORK", System.currentTimeMillis());
    }
}