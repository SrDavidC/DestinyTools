package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AdrenalinaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    // 10 * 60 * 20 = 10 minutes on ticks, 1 second equals 20 ticks
    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 60 * 20, 1));
    item.setAmount(item.getAmount() - 1);
    player.getInventory().setItemInMainHand(item);
  }
}

