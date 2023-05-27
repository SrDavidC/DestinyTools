package me.srdqrk.destinytools.items.strategies.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class AMMOLauncher implements IItemStrategy {
  private @Getter @Setter ItemStack ammoType = new ItemStack(Material.ARROW, 1);
  private @Getter @Setter int is_durability = 465; // item stack base durability
  private @Getter @Setter int specialItem_durability = 32;
  private @Getter @Setter Sound launchSound = null;
  private @Getter @Setter float launch_volume = 1F;
  private @Getter @Setter float launch_pitch = 1F;



  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Player player = event.getPlayer();
    ItemMeta meta = item.getItemMeta();
    CrossbowMeta crossbowMeta = (CrossbowMeta) meta;
    Damageable damageable = (Damageable) meta;
    if (damageable.getDamage() <= this.is_durability) {
      crossbowMeta.addChargedProjectile(this.ammoType);
      final int uses = is_durability / specialItem_durability;
      final int damage = damageable.getDamage() + uses;
      damageable.setDamage(damage);

      player.getInventory().getItemInMainHand().setItemMeta(damageable);

      if (this.launchSound != null) {
        player.getWorld().playSound(
                player.getLocation(),this.launchSound,launch_volume,launch_pitch
        );
      }
    } else {
      player.playSound(
              player.getLocation(),
              Sound.ENTITY_ITEM_BREAK, 1F, 1F);
    }
  }
}
