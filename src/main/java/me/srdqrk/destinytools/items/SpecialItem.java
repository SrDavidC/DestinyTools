package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;


public class SpecialItem {

  final private @Getter ItemStack itemStack;
  final String itemNameColor = "<white><bold>   ";
  final String itemLoreColor = "<gray>";
  final MiniMessage mm = DestinyTools.instance().getMm();

  public SpecialItem(Material material, String itemName, int customModelData, String... lore) {
    List<Component> componentsLore = new ArrayList<>();
    for (String line : lore) {
      componentsLore.add(mm.deserialize(itemLoreColor + line).decoration(TextDecoration.ITALIC, false));
    }

    this.itemStack = new ItemBuilder(material, mm.deserialize(
            itemNameColor + itemName).decoration(TextDecoration.ITALIC, false))
            .customModelData(customModelData)
            .lore(componentsLore)
            .build();
  }

  public SpecialItem(ItemStack itemStack) {
    this.itemStack = itemStack;
  }
}



