package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SuplementoAlimenticioStrategy implements IItemStrategy{
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    player.setFoodLevel(20);
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
    item.setAmount(item.getAmount() - 1);
  }
}
