package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;
import java.util.Objects;

public class ItemsManager implements Listener {

  final int FISHING_ROD_CMD = 1;
  final int GAFAS_TACTICOS_CMD = 101;
  final int RIFLE_CMD = 1;
  final int SIERRA_CMD = 102;
  final int PATO_DE_HULE_CMD = 103;
  final int CUERDA_CMD = 104;
  final int PEDAZO_DE_CARNE_CMD = 99;
  final int BOTELLA_DE_ACIDO_CMD = 500;
  final int BOTELLA_DE_SULFURO_CMD = 501;


  private final @Getter HashMap<String, SpecialItem> specialItemMap;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();

    DestinyTools.instance().getServer().getPluginManager().registerEvents(this, DestinyTools.instance());

    buildItems();
  }

  public void buildItems() {

    SpecialItem cuerda = new SpecialItem(buildCuerda());
    this.specialItemMap.put("Cuerda", cuerda);
    SpecialItem fishing_rod = new SpecialItem(buildFishingRod());
    this.specialItemMap.put("CanaDePescar", fishing_rod);
    SpecialItem gafas = new SpecialItem(buildGafasTacticas());
    this.specialItemMap.put("GafasTacticas", gafas);
    SpecialItem rifle = new SpecialItem(buildRifle());
    this.specialItemMap.put("Rifle", rifle);
    SpecialItem patodehule = new SpecialItem(buildPatoDeHule());
    this.specialItemMap.put("PatoDeHule", patodehule);
    SpecialItem sierra = new SpecialItem(buildSierra());
    this.specialItemMap.put("Sierra", sierra);
    SpecialItem carne = new SpecialItem(buildPedazoCarne());
    this.specialItemMap.put("PedazoDeCarne", carne);
    SpecialItem acido = new SpecialItem(buildBotellaDeAcido());
    this.specialItemMap.put("BotellaDeAcido", acido);
    SpecialItem sulfuro = new SpecialItem(buildBotellaDeSulfuro());
    this.specialItemMap.put("BotellaDeSulfuro", sulfuro);

  }

  public ItemStack buildCuerda() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(CUERDA_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<green><bold> Cuerda"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildFishingRod() {
    ItemStack is = new ItemStack(Material.FISHING_ROD, 1);
    ItemMeta meta = is.getItemMeta();
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Cana de Pescar"));
    meta.setCustomModelData(FISHING_ROD_CMD);
    Damageable damageable = (Damageable) meta;
    damageable.setDamage(44);
    is.setItemMeta(meta);
    return is;
  }

  public ItemStack buildGafasTacticas() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(GAFAS_TACTICOS_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Gafas Tacticas"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildRifle() {
    ItemStack is = new ItemStack(Material.CROSSBOW, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(RIFLE_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Rifle"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildPatoDeHule() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(PATO_DE_HULE_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Patito de Hule"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildSierra() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(SIERRA_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Sierra"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildPedazoCarne() {
    ItemStack is = new ItemStack(Material.PAPER, 1);
    ItemMeta meta = is.getItemMeta();
    meta.setCustomModelData(PEDAZO_DE_CARNE_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Pedazo de Carne"));
    is.setItemMeta(meta);

    return is;
  }

  public ItemStack buildBotellaDeAcido() {
    ItemStack is = new ItemStack(Material.SPLASH_POTION, 1);
    PotionMeta meta = (PotionMeta) is.getItemMeta();
    meta.setColor(Color.fromRGB(102, 51, 0));
    meta.setCustomModelData(BOTELLA_DE_ACIDO_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Botella de Acido"));
    is.setItemMeta(meta);
    return is;
  }
  public ItemStack buildBotellaDeSulfuro() {
    ItemStack is = new ItemStack(Material.SPLASH_POTION, 1);
    PotionMeta meta = (PotionMeta) is.getItemMeta();
    meta.setColor(Color.fromRGB(102, 51, 0));
    meta.setCustomModelData(BOTELLA_DE_SULFURO_CMD);
    meta.displayName(DestinyTools.instance().getMm().deserialize("<white><bold> Botella de Sulfuro"));
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
              && current.getItemMeta().getCustomModelData() == PATO_DE_HULE_CMD) {
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
      } else {
        ItemStack cursor = e.getCursor();
        if (cursor != null && cursor.hasItemMeta()
                && cursor.getItemMeta().hasCustomModelData()
                && cursor.getItemMeta().getCustomModelData() == PATO_DE_HULE_CMD) {
          p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(24);
        }
      }
    }
  }

  @EventHandler
  public void onPlayerAttackAnotherOne(EntityDamageByEntityEvent e) {
    if ((e.getDamager() instanceof Player damager) && (e.getEntity() instanceof Player damaged)) {
      ItemStack weapon = damager.getPlayer().getInventory().getItemInMainHand();
      if (weapon.hasItemMeta() && weapon.getItemMeta().hasCustomModelData() && weapon.getItemMeta().getCustomModelData() == 102) {
        ItemStack reward = buildPedazoCarne();
        damager.getInventory().addItem(reward);
      }
    }
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent e) {
    Projectile projectile = e.getEntity();
    if (!(projectile instanceof ThrownPotion potion)) {
      return;
    }
    PotionMeta meta = (PotionMeta) potion.getItem().getItemMeta();
    if (meta == null || !meta.hasCustomModelData()) {
      return;
    }
    int customModelData = meta.getCustomModelData();
    switch (customModelData) {
      case 500:
        Entity hitEntity = e.getHitEntity();
        if (hitEntity != null) {
          if (hitEntity instanceof LivingEntity) {
            ((LivingEntity) hitEntity).damage(8);
          }
        }
        break;
      case 501:
        hitEntity = e.getHitEntity();
        if (hitEntity != null) {
          if (hitEntity instanceof LivingEntity livingEntity) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 0));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 0));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0));
          }
        }
        break;
      default:
        break;
    }
  }



}








