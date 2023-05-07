package me.srdqrk.destinytools.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
    sender.getInventory().addItem(specialItem.getItemStack());
  }



}
