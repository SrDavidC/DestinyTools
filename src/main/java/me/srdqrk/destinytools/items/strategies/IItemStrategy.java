package me.srdqrk.destinytools.items.strategies;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface IItemStrategy {
  void execute(PlayerInteractEvent event, ItemStack item);
  default void reduceItemAmount(ItemStack is, Player player) {
    is.setAmount(is.getAmount() - 1);
    player.getInventory().setItemInMainHand(is);
  }
}



