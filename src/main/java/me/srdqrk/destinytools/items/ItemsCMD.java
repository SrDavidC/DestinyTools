package me.srdqrk.destinytools.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@CommandAlias("DestinyTools|dt|item|items|i")
@CommandPermission("destinytools.executer")
public class ItemsCMD extends BaseCommand {

  public ItemsCMD() {
    // Register Completetion for registered items on ItemManager
    DestinyTools.instance().getPaperCommandManager().getCommandCompletions().registerStaticCompletion(
            "items", new ArrayList<>(DestinyTools.instance().getItemsManager().getSpecialItemMap()
                    .keySet()));
  }

  @Subcommand("get")
  @CommandCompletion("@items")
  @CommandPermission("destinytools.executer")
  public void onGetItem(Player sender, String itemName, @Default("1") int count) {
    SpecialItem specialItem = DestinyTools.instance().getItemsManager().getSpecialItemMap().get(itemName);
    String message;
    if (specialItem != null) {
      for (int counter = 0; counter < count; counter++) {
        sender.getInventory().addItem(specialItem.getItemStack());
      }
      message = "El item ha sido anadido a tu inventario.";
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + message));
    } else {
      message = "El item " + itemName + " no fue encontrado";
      message += "\nCantidad de items actuales: " + DestinyTools.instance().getItemsManager().getSpecialItemMap().size();
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<red>" + message));
    }

  }

  @Subcommand("getall")
  @CommandPermission("destinytools.executer")
  public void onGetAllItems(Player sender) {
    HashMap<String, SpecialItem> map = DestinyTools.instance().getItemsManager().getSpecialItemMap();
    for (SpecialItem item : map.values()) {
      sender.getInventory().addItem(item.getItemStack());
    }
    sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + "Te hemos dado todos los items especiales existentes"));

  }

  @Subcommand("give")
  @CommandCompletion("@players @items")
  @CommandPermission("destinytools.executer")
  public void onGiveItem(CommandSender sender, OnlinePlayer player, String itemName) {
    SpecialItem specialItem = DestinyTools.instance().getItemsManager().getSpecialItemMap().get(itemName);
    String message;
    if (specialItem != null) {
      player.getPlayer().getInventory().addItem(specialItem.getItemStack());
      message = "El item ha sido anadido a tu inventario.";
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + message));
    } else {
      message = "El item " + itemName + " no fue encontrado";
      message += "\nCantidad de items actuales: " + DestinyTools.instance().getItemsManager().getSpecialItemMap().size();
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<red>" + message));
    }
  }

  @CommandAlias("muslitos")
  @CommandPermission("destinytools.executer")
  @CommandCompletion("@range:1-20 @players")
  public void onHunger(CommandSender sender, String quantity, String player) {
    if (quantity.equalsIgnoreCase("all")) {
      if (player.equalsIgnoreCase("all")) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
          onlinePlayer.setFoodLevel(20);
        }
        sender.sendMessage(ChatColor.GREEN + "Muslitos rellenos para todos los jugadores");
      } else {
        Player onlinePlayer = Bukkit.getPlayer(player);
        onlinePlayer.setFoodLevel(20);
        sender.sendMessage(ChatColor.GREEN + "Muslitos rellenos para " + player);
      }
    } else {
      int foodLevel = Integer.parseInt(quantity);
      if (player.equalsIgnoreCase("all")) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
          onlinePlayer.setFoodLevel(foodLevel);
        }
        sender.sendMessage(ChatColor.GREEN + "Muslitos rellenos con nivel " + foodLevel + " para todos los jugadores");
      } else {
        Player onlinePlayer = Bukkit.getPlayer(player);
        onlinePlayer.setFoodLevel(foodLevel);
        sender.sendMessage(ChatColor.GREEN + "Muslitos rellenos con nivel " + foodLevel + " para " + player);
      }
    }
  }

  @Subcommand("giveRandom")
  @CommandCompletion("@players")
  @CommandPermission("destinytools.executer")
  public void onGiveItem(CommandSender sender, OnlinePlayer player) {
    HashMap<String, SpecialItem> map = DestinyTools.instance().getItemsManager().getSpecialItemMap();
    SpecialItem specialItem = new ArrayList<>(map.values()).get(new Random().nextInt(map.size()));
    String message;
    if (specialItem != null) {
      player.getPlayer().getInventory().addItem(specialItem.getItemStack());
      message = "El item ha sido anadido a tu inventario.";
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + message));
    } else {
      message = "El item no fue encontrado";
      message += "\nCantidad de items actuales: " + DestinyTools.instance().getItemsManager().getSpecialItemMap().size();
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<red>" + message));
    }
  }
}
