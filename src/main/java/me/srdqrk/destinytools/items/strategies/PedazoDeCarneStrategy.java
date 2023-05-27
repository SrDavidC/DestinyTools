package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PedazoDeCarneStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    player.setFoodLevel(player.getFoodLevel() + 4);
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 0.7F);

    this.reduceItemAmount(item, player);
  }
}
