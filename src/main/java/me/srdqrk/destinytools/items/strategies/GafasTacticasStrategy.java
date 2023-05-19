package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GafasTacticasStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    ItemStack helmet = player.getInventory().getItem(39);

    player.getInventory().remove(item);
    if (helmet != null) {
      player.getInventory().addItem(helmet);
    }
    player.getInventory().setItem(39, item);
    player.playSound(
            player.getLocation(),
            Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1F, 1F);
  }
}
