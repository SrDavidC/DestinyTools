package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PolloChillonStrategy implements  IItemStrategy{
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    Location location = player.getLocation();
    String soundName = "mbpstudios:zairdeluque.sfx_alarm"; // TODO: change name
    float volume = 1.0f;
    float pitch = 1.0f;
    location.getWorld().playSound(location, soundName, volume, pitch);
    // this.reduceItemAmount(item, player);
  }
}

