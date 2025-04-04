package com.wasans.inventoryshare;

import org.bukkit.plugin.java.JavaPlugin;

public class InventoryShare extends JavaPlugin {
    private static InventoryShare instance;
    private TeamInventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        int size = getConfig().getInt("inventory-size", 36);
        InventoryStorage storage = new InventoryStorage(getDataFolder());
        inventoryManager = new TeamInventoryManager(storage, size);
        getServer().getPluginManager().registerEvents(new InventorySyncListener(inventoryManager), this);
        getCommand("teaminv").setExecutor(new TeamInventoryCommand(inventoryManager));
        getLogger().info("✅ 팀 인벤 플러그인: 실시간 인벤 공유 모드 작동 시작!");
    }

    @Override
    public void onDisable() {
        inventoryManager.saveAll();
    }

    public static InventoryShare getInstance() {
        return instance;
    }

    public TeamInventoryManager getInventoryManager() {
        return inventoryManager;
    }
}