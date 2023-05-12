package me.srdqrk.destinytools.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {
  private Material material;
  private int amount = 1;
  private Component name;
  private int customModelData = 0;
  private List<Component> lore = new ArrayList<>();
  private Map<Enchantment, Integer> enchantments = new HashMap<>();
  private boolean unbreakable = false;
  private ItemFlag[] flags = {};

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

  public ItemBuilder lore(Component... lore) {
    this.lore = Arrays.stream(lore).toList();
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

  public ItemStack build() {
    ItemStack itemStack = new ItemStack(material, amount);
    ItemMeta meta = itemStack.getItemMeta();

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