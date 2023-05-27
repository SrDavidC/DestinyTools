package me.srdqrk.destinytools.items.strategies.objects;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public interface IProjectileStrategy {

  void execute(ProjectileHitEvent event, ItemStack item);
}
