package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Color;


public class BotellaDeAcidoStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {


    ItemStack potion = new ItemStack(Material.LINGERING_POTION);
    PotionMeta meta = (PotionMeta) potion.getItemMeta();
    meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER,8 * 20, 0),true);
    meta.setColor(Color.fromRGB(102, 51, 0));
    meta.setCustomModelData(DestinyTools.instance().getItemsManager().BOTELLA_DE_ACIDO_CMD);
    potion.setItemMeta(meta);

    // Throw potion
    ThrownPotion thrownPotion = event.getPlayer().launchProjectile(ThrownPotion.class);
    thrownPotion.setItem(potion);

    this.reduceItemAmount(item, event.getPlayer());
  }
}
