package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BananaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    player.setFoodLevel(player.getFoodLevel() + 8);
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
    ItemStack is = player.getInventory().getItemInMainHand();
    is.setAmount(is.getAmount() - 1);
    player.getInventory().setItemInMainHand(is);

    this.reduceItemAmount(item, player);
  }
}
