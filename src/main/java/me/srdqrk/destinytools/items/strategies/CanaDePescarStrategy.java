package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;


import static me.srdqrk.destinytools.items.ItemsManager.getUsesLeft;
import static me.srdqrk.destinytools.items.ItemsManager.setUsesLeft;

public class CanaDePescarStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    int usesLeft = getUsesLeft(item);
    if (usesLeft > 0) {
      usesLeft--;
      item = setUsesLeft(item, usesLeft);
      Damageable meta = (Damageable) item.getItemMeta();
      meta.setDamage(meta.getDamage() + (64/15));
      item.setItemMeta(meta);
      event.getPlayer().getInventory().setItemInMainHand(item);
    } else {
      event.setCancelled(true);
      Location loc = event.getPlayer().getLocation();
      loc.getWorld().playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1f);
      event.getPlayer().sendActionBar(DestinyTools.instance().getMm().deserialize("<white> La caña se ha roto"));
    }
  }
}
