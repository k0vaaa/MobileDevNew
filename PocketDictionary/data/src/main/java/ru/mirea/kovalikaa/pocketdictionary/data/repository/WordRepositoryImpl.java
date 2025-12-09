package ru.mirea.kovalikaa.pocketdictionary.data.repository;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.WordDefinition;
import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.data.network.NetworkApi;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.WordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordEntity;


public class WordRepositoryImpl implements WordRepository {

    private final WordStorage sharedPrefStorage;
    private final WordDao wordDao;
    private final NetworkApi networkApi;

    public WordRepositoryImpl(WordStorage sharedPrefStorage, WordDao wordDao, NetworkApi networkApi) {
        this.sharedPrefStorage = sharedPrefStorage;
        this.wordDao = wordDao;
        this.networkApi = networkApi;
    }

    @Override
    public WordDefinition getDefinition(String word) {
        WordEntity roomEntity = wordDao.getDefinitionByWord(word);
        if (roomEntity != null) {
            System.out.println("Got '" + word + "' from ROOM cache.");
            return mapToDomainModel(mapFromRoomEntity(roomEntity));
        }

        System.out.println("'" + word + "' not in cache. Fetching from NETWORK.");
        WordDataModel dataFromNetwork = networkApi.getDefinitionFromNetwork(word);

        if (dataFromNetwork != null && !"Definition not found".equals(dataFromNetwork.getDefinition())) {
            WordEntity entityToCache = mapToRoomEntity(dataFromNetwork);
            entityToCache.isFavorite = false;
            wordDao.saveDefinition(entityToCache);
            System.out.println("Cached '" + word + "' to Room.");
        }

        return mapToDomainModel(dataFromNetwork);
    }

    @Override
    public void saveWordToFavorites(String word) {
        wordDao.setFavorite(word);
        System.out.println("Set as favorite: " + word);
    }

    @Override
    public List<WordDefinition> getFavorites() {
        List<WordEntity> entities = wordDao.getAllFavorites();
        List<WordDefinition> domainModels = new ArrayList<>();
        for (WordEntity entity : entities) {
            domainModels.add(mapToDomainModel(mapFromRoomEntity(entity)));
        }
        return domainModels;
    }

    @Override
    public void removeFavorite(WordDefinition word) {
        wordDao.deleteFavorite(mapToRoomEntity(mapToDataModel(word)));
    }

    private WordDataModel mapToDataModel(WordDefinition domainModel) {
        return new WordDataModel(domainModel.getWord(), domainModel.getDefinition(), domainModel.getImageUrl(), System.currentTimeMillis());
    }

    private WordDefinition mapToDomainModel(WordDataModel dataModel) {
        if (dataModel == null) return null;
        return new WordDefinition(dataModel.getWord(), dataModel.getDefinition(), dataModel.getImageUrl());
    }

    private WordEntity mapToRoomEntity(WordDataModel dataModel) {
        WordEntity entity = new WordEntity();
        entity.word = dataModel.getWord();
        entity.definition = dataModel.getDefinition();
        entity.imageUrl = dataModel.getImageUrl();
        entity.timestamp = dataModel.getSaveTimestamp();
        return entity;
    }

    private WordDataModel mapFromRoomEntity(WordEntity roomEntity) {
        if (roomEntity == null) return null;
        return new WordDataModel(roomEntity.word, roomEntity.definition, roomEntity.imageUrl, roomEntity.timestamp);
    }
}