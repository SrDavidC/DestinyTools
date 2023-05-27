package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CuerdaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player =  event.getPlayer();
    int CUERDA_Y_TELEPORT = 8;
    Location location = player.getLocation();
    int NEW_Y = location.getBlockY() - CUERDA_Y_TELEPORT;

    player.teleport(new Location(location.getWorld(), location.getBlockX(), NEW_Y, location.getBlockZ()));

    this.reduceItemAmount(item, player);
  }
}
