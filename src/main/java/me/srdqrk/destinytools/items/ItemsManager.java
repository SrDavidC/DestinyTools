package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;


public class ItemsManager implements Listener {

  private final @Getter HashMap<String, SpecialItem> specialItemMap;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();

    DestinyTools.instance().getServer().getPluginManager().registerEvents(this, DestinyTools.instance());

    buildItems();
  }

  public void buildItems() {

    // Cuerda
    SpecialItem cuerda = new SpecialItem(buildCuerda());
    this.specialItemMap.put("Cuerda", cuerda);

    // Fishing Rod
    SpecialItem fishing_rod = new SpecialItem(buildFishingRod());
    this.specialItemMap.put("CanaDePescar", fishing_rod);
    SpecialItem gafas = new SpecialItem(buildGafasTacticas());
    this.specialItemMap.put("GafasTacticas", gafas);
    SpecialItem rifle = new SpecialItem(buildRifle());
    this.specialItemMap.put("Rifle", rifle);
    SpecialItem patodehule = new SpecialItem(buildPatoDeHule());
    this.specialItemMap.put("PatoDeHule", patodehule);
  }

  public ItemStack buildCuerda() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(104);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<yellow><bold>Cuerda"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildFishingRod() {
    String itemName = "Cana de Pescar";
    ItemStack is = new ItemStack(Material.FISHING_ROD, 1);
    ItemMeta meta = is.getItemMeta();
    meta.displayName(DestinyTools.instance().getMm().deserialize("<yellow><bold>" + itemName));
    meta.setCustomModelData(500);
    Damageable damageable = (Damageable) meta;
    damageable.setDamage(44);
    is.setItemMeta(meta);
    return is;
  }

  public ItemStack buildGafasTacticas() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(101);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<yellow><bold>Gafas Tacticas"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildRifle() {
    ItemStack is = new ItemStack(Material.CROSSBOW, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(1);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<yellow><bold>Rifle"));
    is.setItemMeta(meta);

    return is;
  }
  public ItemStack buildPatoDeHule() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(103);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<yellow><bold>Patito de Hule"));
    is.setItemMeta(meta);

    return is;
  }

  private void skillCuerda(Player player) {
    int CUERDA_Y_TELEPORT = 8;
    Location location = player.getLocation();
    int NEW_Y = location.getBlockY() - CUERDA_Y_TELEPORT;

    player.teleport(new Location(location.getWorld(), location.getBlockX(), NEW_Y, location.getBlockZ()));
  }

  private ItemMeta skillCanaDePescar(ItemStack itemStack) {
    Damageable damageableItem = ((Damageable) itemStack.getItemMeta());
    if (damageableItem.getDamage() < 64) {
      damageableItem.setDamage(damageableItem.getDamage() + 1);
    } else {
      damageableItem = null;
    }

    return damageableItem;
  }

  private void skillGafasTacticas(Player player, ItemStack item) {
    ItemStack helmet = player.getInventory().getItem(39);
    player.getInventory().remove(item);
    if (helmet != null) {
      player.getInventory().addItem(helmet);
    }
    player.getInventory().setItem(39, item);
    player.playSound(
            player.getLocation(),
            Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1F, 1F);
  }

  private ItemMeta skillRifle(Player player, ItemStack rifle) {
    ItemMeta meta = rifle.getItemMeta();
    CrossbowMeta crossbowMeta = (CrossbowMeta) meta;
    Damageable damageable = (Damageable) meta;
    final int CROWSSBOW_DURABILITY = 434;
    if (damageable.getDamage() <= CROWSSBOW_DURABILITY) {
      crossbowMeta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
      damageable = (Damageable) crossbowMeta;
      final int maxTotalUses = 465;
      final int ourUses = 32;
      final int uses = maxTotalUses / ourUses;
      final int damage = damageable.getDamage() + uses;
      damageable.setDamage(damage);
      meta = damageable;
    } else {
      player.playSound(
              player.getLocation(),
              Sound.ENTITY_ITEM_BREAK, 1F, 1F);
      meta = null;
    }
    return meta;
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      ItemStack item = event.getItem();
      Player player = event.getPlayer();
      if (item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
        int model = item.getItemMeta().getCustomModelData();
        switch (model) {
          case 101: // gafas tácticas
            skillGafasTacticas(event.getPlayer(), item);
            break;
          case 104: // cuerda
            skillCuerda(event.getPlayer());
            break;
          case 1: // Cana de pescar or Rifle
            if (item.getType() == Material.FISHING_ROD) {
              // change item durability with the skill
              ItemMeta meta = skillCanaDePescar(item);
              if (meta != null) {
                item.setItemMeta(meta);
              } else {
                event.getPlayer().playSound(
                        event.getPlayer().getLocation(),
                        Sound.ENTITY_ITEM_BREAK, 1F, 1F);
                event.setCancelled(true);
              }
            } else if (item.getType() == Material.CROSSBOW) {
              ItemMeta meta = skillRifle(player, item);
              if (meta != null) {
                item.setItemMeta(meta);
              } else {
                event.setCancelled(true);
              }
            }
            break;
        }
      }
    }
  }

  @EventHandler
  public void onShootCrossBowEvent(EntityShootBowEvent e) {
    Player p = (Player) e.getEntity();
    ItemStack item = e.getBow();
    // If special
    if (item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
      // Special Crossbow
      if (e.getBow().getType() == Material.CROSSBOW) {
        ItemMeta meta = item.getItemMeta();
        int model = meta.getCustomModelData();
        switch (model) {
          case 1:
            Arrow arrow = (Arrow) e.getProjectile();
            arrow.customName(DestinyTools.instance().getMm().deserialize("RifleAMMO"));
            arrow.setDamage(0.5);
            break;
        }
      }
    }
  }
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Arrow arrow) {
      if (arrow.customName() != null
              && DestinyTools.instance().getMm().serialize(Objects.requireNonNull(arrow.customName())).equals("RifleAMMO")) {
        event.setDamage(1);
      }
    }
  }
  @EventHandler
  public void onInventoryInteract(InventoryClickEvent e) {
    if (!(e.getWhoClicked() instanceof Player)) {
      return;
    }

    final Player p = (Player) e.getWhoClicked();
    final ItemStack current = e.getCurrentItem();
    final int PATO_SLOT = 17;

    if (e.getSlot() == PATO_SLOT) {
      if (current != null && current.hasItemMeta()
              && current.getItemMeta().hasCustomModelData()
              && current.getItemMeta().getCustomModelData() == 103) {
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
      } else {
        ItemStack cursor = e.getCursor();
        if (cursor != null && cursor.hasItemMeta()
                && cursor.getItemMeta().hasCustomModelData()
                && cursor.getItemMeta().getCustomModelData() == 103) {
          p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(24);
        }
      }
    }
  }





}


