package com.wasans.inventoryshare;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInventoryCommand implements CommandExecutor {
    private final TeamInventoryManager manager;

    public TeamInventoryCommand(TeamInventoryManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("플레이어만 사용할 수 있는 명령어입니다.");
            return true;
        }

        String team = manager.getTeamName(player);
        if (team == null) {
            player.sendMessage(ChatColor.RED + "당신은 어떤 팀에도 속해있지 않습니다.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            manager.toggleSharing(team);
            boolean enabled = manager.isSharingEnabled(team);
            player.sendMessage(ChatColor.WHITE + "팀 인벤 공유가 " + (enabled ? ChatColor.GREEN + "활성화" : ChatColor.RED + "비활성화") + ChatColor.WHITE + "되었습니다.");
            return true;
        }

        player.sendMessage(ChatColor.GRAY + "/teaminv toggle - 팀 인벤 공유 켜기/끄기");
        return true;
    }
}
