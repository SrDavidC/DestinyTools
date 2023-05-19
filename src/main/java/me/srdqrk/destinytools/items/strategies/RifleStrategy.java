package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.items.strategies.objects.AMMOLauncher;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RifleStrategy extends AMMOLauncher {
  public RifleStrategy() {
    this.setAmmoType(new ItemStack(Material.ARROW, 1));
    this.setIs_durability(465);
    this.setSpecialItem_durability(32);
  }
}
