package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.AMMOLauncher;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class MinigunStrategy extends AMMOLauncher {
  public MinigunStrategy() {
    this.setAmmoType(new ItemStack(Material.ARROW, 1));
    this.setIs_durability(465);
    this.setSpecialItem_durability(100);
    this.setLaunchSound(Sound.ENTITY_SHULKER_SHOOT);
    this.setLaunch_pitch(0.3f);
    this.setLaunch_volume(1F);
  }
}
