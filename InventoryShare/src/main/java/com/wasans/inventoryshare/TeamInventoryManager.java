package com.wasans.inventoryshare;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TeamInventoryManager {
    private final Map<String, ItemStack[]> teamInventories = new HashMap<>();
    private final Set<String> disabledTeams = new HashSet<>();
    private final InventoryStorage storage;
    private final int defaultSize;

    public TeamInventoryManager(InventoryStorage storage, int defaultSize) {
        this.storage = storage;
        this.defaultSize = defaultSize;
    }

    public void syncTeamInventory(Player player) {
        String team = getTeamName(player);
        if (team == null || !isSharingEnabled(team)) return;

        ItemStack[] contents = teamInventories.computeIfAbsent(team, t -> storage.load(t, defaultSize));
        player.getInventory().setContents(contents);
    }

    public void updateTeamInventory(Player source) {
        String team = getTeamName(source);
        if (team == null || !isSharingEnabled(team)) return;

        ItemStack[] contents = source.getInventory().getContents();
        teamInventories.put(team, contents);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (team.equals(getTeamName(p)) && !p.equals(source)) {
                p.getInventory().setContents(contents);
            }
        }
    }

    public void toggleSharing(String team) {
        if (disabledTeams.contains(team)) disabledTeams.remove(team);
        else disabledTeams.add(team);
    }

    public boolean isSharingEnabled(String team) {
        return !disabledTeams.contains(team);
    }

    public void saveAll() {
        for (Map.Entry<String, ItemStack[]> entry : teamInventories.entrySet()) {
            storage.save(entry.getKey(), entry.getValue());
        }
    }

    public String getTeamName(Player player) {
        if (player.getScoreboard() == null || player.getScoreboard().getEntryTeam(player.getName()) == null)
            return null;
        return player.getScoreboard().getEntryTeam(player.getName()).getName();
    }
}