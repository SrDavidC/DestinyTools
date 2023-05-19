package me.srdqrk.destinytools.items.strategies;

import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlantaStrategy implements IItemStrategy {
  @Override
  public void execute(PlayerInteractEvent event, ItemStack item) {
    Block clickedBlock = event.getClickedBlock();
    BlockFace blockFace = event.getBlockFace();

    if (clickedBlock != null && clickedBlock.getType() != Material.AIR) {
      Location loc = clickedBlock.getLocation().add(0.5, 1, 0.5);
      if (loc.getBlock().getType() == Material.AIR) {
        ItemFrame itemFrame = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);
        itemFrame.setFacingDirection(blockFace);
        if (item != null) {
          itemFrame.setItem(item);
          this.reduceItemAmount(item, event.getPlayer());
        }

        Bukkit.getScheduler().runTaskTimer(DestinyTools.instance(), () -> {
          if (!itemFrame.getItem().getType().equals(Material.AIR)) {
            loc.getWorld().dropItemNaturally(loc, DestinyTools.instance().getItemsManager()
                    .getSpecialItemMap().get("Fresa").getItemStack());
          }
        }, 0, 5 * 60 * 20);
      }
    }
  }
}
