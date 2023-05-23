package me.srdqrk.destinytools.items.strategies;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.Color;

import java.awt.*;
import java.util.Arrays;

public class BotellaDeAcidoStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    ItemStack potion = new ItemStack(Material.SPLASH_POTION);
    PotionMeta meta = (PotionMeta) potion.getItemMeta();
    meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER,8 * 20, 0),true);
    meta.setColor(Color.fromRGB(102, 51, 0));
    potion.setItemMeta(meta);

    // Throw potion
    ThrownPotion thrownPotion = event.getPlayer().launchProjectile(ThrownPotion.class);
    thrownPotion.setItem(potion);

    this.reduceItemAmount(item, event.getPlayer());
  }
}
