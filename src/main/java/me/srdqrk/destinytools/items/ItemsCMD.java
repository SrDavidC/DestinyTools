package me.srdqrk.destinytools.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@CommandAlias("DestinyTools|dt|item|items")
public class ItemsCMD extends BaseCommand {

  public ItemsCMD() {
    // Register Completetion for registered items on ItemManager
    DestinyTools.instance().getPaperCommandManager().getCommandCompletions().registerStaticCompletion(
            "items", new ArrayList<>(DestinyTools.instance().getItemsManager().getSpecialItemMap()
                    .keySet()));
  }

  @Subcommand("get")
  @CommandCompletion("@items")
  public void onGetItem(Player sender, String itemName) {
    SpecialItem specialItem = DestinyTools.instance().getItemsManager().getSpecialItemMap().get(itemName);
    String message;
    if (specialItem != null) {
      sender.getInventory().addItem(specialItem.getItemStack());
      message = "El item ha sido anadido a tu inventario.";
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + message));
    } else {
      message = "El item " + itemName +" no fue encontrado";
      message += "\nCantidad de items actuales: " + DestinyTools.instance().getItemsManager().getSpecialItemMap().size();
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<red>" + message));
    }

  }
  @Subcommand("getall")
  public void onGetAllItems(Player sender) {
    HashMap<String, SpecialItem> map = DestinyTools.instance().getItemsManager().getSpecialItemMap();
    String message;
    for (SpecialItem item : map.values()) {
      sender.getInventory().addItem(item.getItemStack());
    }
    sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + "Te hemos dado todos los items especiales existentes"));

  }
  @Subcommand("give")
  @CommandCompletion("@players @items")
  public void onGetItem(Player sender, Player player ,String itemName) {
    SpecialItem specialItem = DestinyTools.instance().getItemsManager().getSpecialItemMap().get(itemName);
    String message;
    if (specialItem != null) {
      player.getInventory().addItem(specialItem.getItemStack());
      message = "El item ha sido anadido a tu inventario.";
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<green>" + message));
    } else {
      message = "El item " + itemName + " no fue encontrado";
      message += "\nCantidad de items actuales: " + DestinyTools.instance().getItemsManager().getSpecialItemMap().size();
      sender.sendMessage(DestinyTools.instance().getMm().deserialize("<red>" + message));
    }
  }
}
