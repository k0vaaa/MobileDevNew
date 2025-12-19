package ru.mirea.kovalikaa.fragmentmanagerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class SharedViewModel extends ViewModel {

    private final Map<String, String> countryDescriptions = new HashMap<>();

    private final MutableLiveData<String> selectedItemName = new MutableLiveData<>();

    public SharedViewModel() {
        countryDescriptions.put("Россия", "Столица - Москва. Крупнейшая по площади страна мира, расположенная в Восточной Европе и Северной Азии.");
        countryDescriptions.put("США", "Столица - Вашингтон. Государство в Северной Америке, состоящее из 50 штатов и федерального округа Колумбия.");
        countryDescriptions.put("Китай", "Столица - Пекин. Государство в Восточной Азии. Занимает третье место в мире по территории.");
        countryDescriptions.put("Германия", "Столица - Берлин. Государство в Центральной Европе. Ведущая экономика Европейского союза.");
        countryDescriptions.put("Франция", "Столица - Париж. Трансконтинентальное государство, известное своей культурой, модой и кухней.");
    }

    public void select(String item) {
        selectedItemName.setValue(item);
    }

    public LiveData<String> getSelected() {
        return selectedItemName;
    }

    public String getDescription(String countryName) {
        return countryDescriptions.get(countryName);
    }
}