package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class BotellaDeSulfuroStrategy implements IItemStrategy {

  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    ItemStack potion = new ItemStack(Material.SPLASH_POTION);
    PotionMeta meta = (PotionMeta) potion.getItemMeta();
    // m.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 0), true);
    // meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0), true);
    // meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 0), true);
    meta.setColor(Color.fromRGB(102, 51, 0));
    meta.setCustomModelData(DestinyTools.instance().getItemsManager().BOTELLA_DE_SULFURO_CMD);
    potion.setItemMeta(meta);

    // Throw potion
    ThrownPotion thrownPotion = event.getPlayer().launchProjectile(ThrownPotion.class);
    thrownPotion.setItem(potion);
    thrownPotion.setShooter(event.getPlayer());

    this.reduceItemAmount(item, event.getPlayer());
  }
}
