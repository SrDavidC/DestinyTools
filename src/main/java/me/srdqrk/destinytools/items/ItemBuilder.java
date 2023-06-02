package me.srdqrk.destinytools.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {
  private final Material material;
  private int amount = 1;
  private final Component name;
  private int customModelData = 0;
  private List<Component> lore = new ArrayList<>();
  private final Map<Enchantment, Integer> enchantments = new HashMap<>();
  private boolean unbreakable = false;
  private ItemFlag[] flags = {};

  private ItemMeta meta;


  public ItemBuilder(Material material, Component name) {
    this.material = material;
    this.name = name;
  }

  public ItemBuilder amount(int amount) {
    this.amount = amount;
    return this;
  }

  public ItemBuilder customModelData(int customModelData) {
    this.customModelData = customModelData;
    return this;
  }

  public ItemBuilder lore(List<Component> lore) {
    this.lore = lore;
    return this;
  }

  public ItemBuilder enchant(Enchantment enchantment, int level) {
    this.enchantments.put(enchantment, level);
    return this;
  }

  public ItemBuilder unbreakable() {
    this.unbreakable = true;
    return this;
  }

  public ItemBuilder flags(ItemFlag... flags) {
    this.flags = flags;
    return this;
  }
  public ItemBuilder metaData(ItemMeta meta) {
    this.meta = meta;
    return this;
  }

  public ItemStack build() {
    ItemStack itemStack = new ItemStack(material, amount);
    ItemMeta meta = (this.meta != null) ? this.meta : itemStack.getItemMeta();

    meta.displayName(name);
    meta.setCustomModelData(customModelData);
    meta.lore(lore);
    enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
    meta.setUnbreakable(unbreakable);
    meta.addItemFlags(flags);

    itemStack.setItemMeta(meta);

    return itemStack;
  }
}
