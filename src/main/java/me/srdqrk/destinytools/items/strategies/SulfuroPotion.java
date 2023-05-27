package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.IProjectileStrategy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class SulfuroPotion implements IProjectileStrategy {

  @Override
  public void execute(ProjectileHitEvent event, ItemStack item) {
    Entity hitEntity = event.getHitEntity();
    ProjectileSource shooter = event.getEntity().getShooter();
    if (hitEntity instanceof LivingEntity && shooter instanceof LivingEntity && hitEntity != shooter) {
      ((LivingEntity) hitEntity).addPotionEffect((new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 0)));
      ((LivingEntity) hitEntity).addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0)));
      ((LivingEntity) hitEntity).addPotionEffect((new PotionEffect(PotionEffectType.POISON, 10 * 20, 0)));
    }
  }
}
