package me.srdqrk.destinytools.items;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;


public class SpecialItem {

  private @Getter
  final ItemStack itemStack;


  public SpecialItem(ItemStack itemStack) {
    this.itemStack = itemStack;
  }
}



