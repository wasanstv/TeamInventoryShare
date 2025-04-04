package com.wasans.inventoryshare;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryStorage {
    private final File folder;

    public InventoryStorage(File dataFolder) {
        this.folder = new File(dataFolder, "inventories");
        if (!folder.exists()) folder.mkdirs();
    }

    public void save(String team, ItemStack[] contents) {
        File file = new File(folder, team + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("contents", contents);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ItemStack[] load(String team, int size) {
        File file = new File(folder, team + ".yml");
        if (!file.exists()) return new ItemStack[size];

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<?> list = config.getList("contents");
        if (list == null) return new ItemStack[size];

        return list.toArray(new ItemStack[0]);
    }
}