package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;

public class ItemsManager implements Listener {

  final int FISHING_ROD_CMD = 1;
  final int RIFLE_CMD = 1;
  final int MINIGUN_DE_JUGUETE_CMD = 2;
  final int PEDAZO_DE_CARNE_CMD = 99;
  final int GAFAS_TACTICOS_CMD = 101;
  final int SIERRA_CMD = 102;
  final int PATO_DE_HULE_CMD = 103;
  final int CUERDA_CMD = 104;
  final int PLANTA_CMD = 105;
  final int SUPLEMENTO_ALIMENTICIO_CMD = 106;
  final int ADRENALINA_CMD = 107;
  final int MINA_ELECTRICA_CMD = 108;
  final int BANANA_CMD = 109;
  final int KIT_ASTRONAUTA_CMD = 110;
  final int AGUA_CMD = 111;
  final int POLLO_CHILLON_CMD = 112;
  final int BOTELLA_DE_ACIDO_CMD = 500;
  final int BOTELLA_DE_SULFURO_CMD = 501;


  final String itemNameColor = "<white><bold>";
  final String itemLoreColor = "<gray>";

  private final @Getter HashMap<String, SpecialItem> specialItemMap;
  private MiniMessage mm;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();
    this.mm = DestinyTools.instance().getMm();
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

    this.specialItemMap.put("MinigunDeJuguete", new SpecialItem(new ItemBuilder(Material.CROSSBOW, mm.deserialize(
            itemNameColor + " Minigun de Juguete"))
            .customModelData(MINIGUN_DE_JUGUETE_CMD)
            .lore(mm.deserialize(itemLoreColor + "Minigun que dispara 100 balas"))
            .build()));
    this.specialItemMap.put("SuplementoAlimenticio", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " SuplementoAlimenticio"))
            .customModelData(SUPLEMENTO_ALIMENTICIO_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al darle click secundario (sin abrir inventario) te llena los muslos de hambre"))
            .build()));
    this.specialItemMap.put("Adrenalina", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Adrenalina"))
            .customModelData(ADRENALINA_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al consumirlo te brinda una resistencia mayor al daño"))
            .build()));
    this.specialItemMap.put("Banana", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Banana"))
            .customModelData(BANANA_CMD)
            .lore(mm.deserialize(itemLoreColor + "No tiene ningún uso, pero te la puedes comer y rellenar 4 muslos"))
            .build()));
    this.specialItemMap.put("KitAstronauta", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Kit de Astronauta"))
            .customModelData(KIT_ASTRONAUTA_CMD)
            .lore(mm.deserialize(itemLoreColor + "Si posees el item \"Agua\" te da saturación por 10minutos"))
            .build()));
    this.specialItemMap.put("Agua", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Agua"))
            .customModelData(AGUA_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al consumirlo obtienes 1 muslo de comida, pero si lo juntas con otros objetos, puedes obtener grandes beneficios"))
            .build()));
    this.specialItemMap.put("PolloChillon", new SpecialItem(new ItemBuilder(Material.GOAT_HORN, mm.deserialize(
            itemNameColor + " Pollo Chillón"))
            .customModelData(POLLO_CHILLON_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al usarlo se reproduce un sonido chistoso"))
            .build()));
    this.specialItemMap.put("Planta", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Planta"))
            .customModelData(PLANTA_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al darle click sobre el suelo, se pone la maceta, te brinda bayas cada 5 minutos"))
            .build()));
    this.specialItemMap.put("MinaElectrica", new SpecialItem(new ItemBuilder(Material.PAPER, mm.deserialize(
            itemNameColor + " Mina Eléctrica"))
            .customModelData(MINA_ELECTRICA_CMD)
            .lore(mm.deserialize(itemLoreColor + "Al colocarla, se planta una mina que al ser pisada produce la muerte instantánea por una descarga eléctrica"))
            .build()));

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
    is = addCustomTag(is, "usesLeft", 5);

    return is;
  }
  public ItemStack addCustomTag(ItemStack itemStack, String key, int value) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.getPersistentDataContainer().set(new NamespacedKey(DestinyTools.instance(), key), PersistentDataType.INTEGER, value);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
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

  private ItemMeta skillMinigun(Player player, ItemStack minigun) {
    ItemMeta meta = minigun.getItemMeta();
    CrossbowMeta crossbowMeta = (CrossbowMeta) meta;
    Damageable damageable = (Damageable) meta;
    final int CROWSSBOW_DURABILITY = 465;
    if (damageable.getDamage() <= CROWSSBOW_DURABILITY) {
      crossbowMeta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
      damageable = (Damageable) crossbowMeta;
      final int maxTotalUses = 465;
      final int ourUses = 100;
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

  private void skillBanana(Player player) {
    if (player != null) {
      player.setFoodLevel(player.getFoodLevel() + 8);
      player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
    }
  }

  private void skillAgua(Player player) {
    if (player != null) {
      player.setFoodLevel(player.getFoodLevel() + 2);
      player.playSound(player.getLocation(), Sound.ITEM_HONEY_BOTTLE_DRINK, 1f, 0.4f);
    }
  }

  private void skillKitAstronauta(Player player) {
    if (player != null) {
      for (ItemStack item : player.getInventory().getContents()) {
        if (item != null && item.hasItemMeta()
                && item.getItemMeta().hasCustomModelData()
                && item.getItemMeta().getCustomModelData() == AGUA_CMD) {
          player.getInventory().remove(item);
          player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10 * 60 * 20, 0));
          player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
          break;
        }
      }
    }
  }

  private void skillAdrenalina(Player player) {
    if (player != null) {
      // 10 * 60 * 20 = 10 minutes on ticks, 1 second equals 20 ticks
      player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 60 * 20, 1));
    }
  }

  private void skillPolloChillon(Player player) {
    if (player != null) {
      Location location = player.getLocation();
      String soundName = "SONIDO_CUSTOM"; // TODO: change name
      float volume = 1.0f;
      float pitch = 1.0f;
      location.getWorld().playSound(location, Sound.ENTITY_PLAYER_BURP, volume, pitch);

    }
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
          case MINIGUN_DE_JUGUETE_CMD:
            ItemMeta meta = skillMinigun(player, item);
            if (meta != null) {
              item.setItemMeta(meta);
            } else {
              event.setCancelled(true);
            }
            break;
          case SUPLEMENTO_ALIMENTICIO_CMD:
            player.setFoodLevel(20);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
            item.setAmount(item.getAmount() - 1);
            break;
          case ADRENALINA_CMD:
            skillAdrenalina(player);
            item.setAmount(item.getAmount() - 1);
            break;
          case BANANA_CMD:
            skillBanana(player);
            item.setAmount(item.getAmount() - 1);
            break;
          case AGUA_CMD:
            skillAgua(player);
            item.setAmount(item.getAmount() - 1);
            break;
          case KIT_ASTRONAUTA_CMD:
            skillKitAstronauta(player);
            item.setAmount(item.getAmount() - 1);
            break;
          case POLLO_CHILLON_CMD:
            event.setCancelled(true);
            skillPolloChillon(player);
            break;
          case MINA_ELECTRICA_CMD:
            break;
          case PLANTA_CMD:
            if (event.getClickedBlock() != null  /* && event.getClickedBlock().getType() != Material.AIR */) {
              Block clickedBlock = event.getClickedBlock();
              Location loc = clickedBlock.getLocation().add(0.5, 1, 0.5);
              if (loc.getBlock().getType() == Material.AIR) {
                // loc.getBlock().setType(Material.ITEM_FRAME);
                // loc.getBlock().setData((byte) event.getBlockFace().ordinal());
                ItemFrame itemFrame = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);
                itemFrame.setFacingDirection(event.getBlockFace());
                if (item != null) {
                  itemFrame.setItem(item);
                  item.setAmount(item.getAmount() - 1);
                }
                Bukkit.getScheduler().runTaskTimer(DestinyTools.instance(), () -> {
                  if (!itemFrame.getItem().getType().equals(Material.AIR)) {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.SWEET_BERRIES, 1));
                  }
                }, 0, 5 * 60 * 20);
              }
            }
            break;
        } // END SWITCH
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
          case RIFLE_CMD:
            Arrow arrow = (Arrow) e.getProjectile();
            arrow.customName(DestinyTools.instance().getMm().deserialize("RifleAMMO"));
            arrow.setDamage(0.5);
            break;
          case MINIGUN_DE_JUGUETE_CMD:
            e.setCancelled(true);
            e.getProjectile().remove();
            Snowball snowball = e.getEntity().launchProjectile(Snowball.class);
            snowball.setVelocity(e.getProjectile().getVelocity());
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
    if ((e.getDamager() instanceof Player damager) && ( (e.getEntity() instanceof Player) || (e.getEntity() instanceof Cow) )) {
      LivingEntity damaged = (LivingEntity) e.getEntity();
      ItemStack weapon = damager.getPlayer().getInventory().getItemInMainHand();
      if (weapon.hasItemMeta()
              && weapon.getItemMeta().hasCustomModelData()
              && weapon.getItemMeta().getCustomModelData() == SIERRA_CMD) {

        boolean cancelDamage =  skillSierra(weapon, damager, damaged);
        e.setCancelled(cancelDamage);
      }
    }
  }


  public boolean skillSierra(ItemStack sierra, Player damager, LivingEntity damaged) {
    int usesLeft = getUsesLeft(sierra);
    boolean cancelDamage = false;
    if (usesLeft > 0) {
      ItemStack reward = buildPedazoCarne();
      damager.getInventory().addItem(reward);
      damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 60 * 20, 0));
      damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 60 * 20, 0));
      usesLeft--;
      sierra = setUsesLeft(sierra, usesLeft);
      damager.getInventory().setItemInMainHand(sierra);
    } else {
      Location loc = damager.getLocation();
      loc.getWorld().playSound(loc,Sound.ENTITY_ITEM_BREAK, 1f,1f);
      damager.sendActionBar(mm.deserialize("<white> La sierra se ha roto"));
      cancelDamage = true;
    }
    return cancelDamage;
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
  public int getUsesLeft(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    Integer usesLeft = itemMeta.getPersistentDataContainer().get(new NamespacedKey(DestinyTools.instance(), "usesLeft"), PersistentDataType.INTEGER);
    if (usesLeft == null) {
      return 0;
    } else {
      return usesLeft;
    }
  }

  public ItemStack setUsesLeft(ItemStack itemStack, int usesLeft) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.getPersistentDataContainer().set(new NamespacedKey(DestinyTools.instance(), "usesLeft"), PersistentDataType.INTEGER, usesLeft);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }


}








