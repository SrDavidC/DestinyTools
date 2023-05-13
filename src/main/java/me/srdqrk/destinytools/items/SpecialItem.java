package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;


public class SpecialItem {

  private @Getter ItemStack itemStack;
  final String itemNameColor = "<white><bold> ";
  final String itemLoreColor = "<gray>";
  final MiniMessage mm = DestinyTools.instance().getMm();

  public SpecialItem(Material material, String itemName, int customModelData, String lore) {
    this.itemStack = new ItemBuilder(material, mm.deserialize(
            itemNameColor + itemName))
            .customModelData(customModelData)
            .lore(mm.deserialize(itemLoreColor + lore))
            .build();
  }
  public SpecialItem(Material material, String itemName, int customModelData, String lore, Color color) {
    this.itemStack = new ItemBuilder(material, mm.deserialize(
            itemNameColor + itemName))
            .customModelData(customModelData)
            .lore(mm.deserialize(itemLoreColor + lore))
            .build();
    PotionMeta meta = (PotionMeta) this.itemStack.getItemMeta();
    meta.setColor(color);
    meta.setCustomModelData(customModelData);

    this.itemStack.setItemMeta(meta);
  }
  public SpecialItem(ItemStack itemStack) {
    this.itemStack = itemStack;
  }
}



