package com.ppfs.skulkers.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ppfs.skulkers.ShulkersPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class LocalizeService {
    private static final Gson gson = new Gson();
    private HashMap<String, String> localizations = new HashMap<>();
    private static LocalizeService instance;

    private LocalizeService() {

    }

    private static void load() {
        instance = new LocalizeService();
        instance.loadTranslations();
    }

    public static LocalizeService getInstance() {
        if (instance == null) load();
        return instance;
    }

    public void loadTranslations() {
        File file = new File(ShulkersPlugin.getInstance().getDataFolder(), "ru_ru.json");
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            ShulkersPlugin.getInstance().getLogger().info("Creating file " + file.getAbsolutePath());
            ShulkersPlugin.getInstance().saveResource(file.getName(), false);
        }
        ShulkersPlugin.getInstance().getLogger().info("Loading file " + file.getAbsolutePath());
        CompletableFuture.supplyAsync(() -> {
            try {
                return gson.fromJson(new FileReader(file), JsonObject.class);
            } catch (FileNotFoundException e) {
                ShulkersPlugin.getInstance().getLogger().severe("Failed to load json file " + file.getAbsolutePath());
                throw new RuntimeException(e);
            }
        }).thenAcceptAsync(jsonObject ->
            jsonObject.keySet().forEach(key ->
                localizations.put(key, jsonObject.get(key).getAsString())
        )).whenComplete((string, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                ShulkersPlugin.getInstance().getLogger().severe("Failed to load json file " + file.getAbsolutePath());
                return;
            }
            ShulkersPlugin.getInstance().getLogger().info("Loaded json file " + file.getAbsolutePath());
        });
    }

    public String getTranslation(String key) {
        return localizations.get(key);
    }
}
