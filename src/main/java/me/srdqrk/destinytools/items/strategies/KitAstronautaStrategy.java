package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitAstronautaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack is) {
    Player player = event.getPlayer();
    for (ItemStack item : player.getInventory().getContents()) {
      if (item != null && item.hasItemMeta()
              && item.getItemMeta().hasCustomModelData()
              && item.getItemMeta().getCustomModelData() == DestinyTools.instance().getItemsManager().AGUA_CMD) {

        player.getInventory().remove(item);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10 * 60 * 20, 0));
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
        this.reduceItemAmount(is,player);
        break;
      }
    }
  }
}


