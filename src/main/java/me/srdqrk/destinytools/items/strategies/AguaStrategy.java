package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AguaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    player.setFoodLevel(player.getFoodLevel() + 4);
    player.playSound(player.getLocation(), Sound.ITEM_HONEY_BOTTLE_DRINK, 1f, 0.6f);

    this.reduceItemAmount(item, player);
  }
}

