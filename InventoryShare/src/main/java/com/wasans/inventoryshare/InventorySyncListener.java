package com.wasans.inventoryshare;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class InventorySyncListener implements Listener {
    private final TeamInventoryManager manager;

    public InventorySyncListener(TeamInventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        manager.syncTeamInventory(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Bukkit.getScheduler().runTaskLater(InventoryShare.getInstance(), () ->
                    manager.updateTeamInventory(player), 1L
            );
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Bukkit.getScheduler().runTaskLater(InventoryShare.getInstance(), () ->
                    manager.updateTeamInventory(player), 1L
            );
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(InventoryShare.getInstance(), () ->
                manager.updateTeamInventory(player), 1L
        );
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(InventoryShare.getInstance(), () ->
                manager.updateTeamInventory(player), 1L
        );
    }
}