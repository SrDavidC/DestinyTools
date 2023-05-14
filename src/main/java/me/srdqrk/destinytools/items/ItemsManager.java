package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
  final int BOTELLA_DE_ACIDO_CMD = 105;
  final int BOTELLA_DE_SULFURO_CMD = 106;
  final int PLANTA_CMD = 107;
  final int FRESA_CMD = 108;
  final int SUPLEMENTO_ALIMENTICIO_CMD = 109;
  final int ADRENALINA_CMD = 110;
  final int BANANA_CMD = 111;
  final int POLLO_CHILLON_CMD = 112;
  final int KIT_ASTRONAUTA_CMD = 113;
  final int AGUA_CMD = 114;
  final int MINA_ELECTRICA_CMD = 999;


  private final @Getter HashMap<String, SpecialItem> specialItemMap;
  private MiniMessage mm;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();
    this.mm = DestinyTools.instance().getMm();
    DestinyTools.instance().getServer().getPluginManager().registerEvents(this, DestinyTools.instance());

    buildItems();
  }

  public void buildItems() {
    this.specialItemMap.put("Cuerda",
            new SpecialItem(Material.PAPER,"Cuerda",CUERDA_CMD,"Al usar, el jugador es tpeado 8 bloques abajo"));

    this.specialItemMap.put("CañaDePescar",
            new SpecialItem(Material.FISHING_ROD,"Caña De Pescar",FISHING_ROD_CMD,"Uso limitado de 10"));

    this.specialItemMap.put("LentesTácticos",
            new SpecialItem(Material.PAPER,"Lentes Tácticos",GAFAS_TACTICOS_CMD,""));

    this.specialItemMap.put("Rifle",
            new SpecialItem(Material.CROSSBOW,"Rifle",RIFLE_CMD,"Tiene 32 balas de puro dolor."));

    this.specialItemMap.put("PatoDeHule",
            new SpecialItem(Material.PAPER,"Pato De Hule",PATO_DE_HULE_CMD,"Si lo colocas en en slot especial, te da 2 corazones extra de vida"));

    this.specialItemMap.put("Sierra",
            new SpecialItem(Material.PAPER,"Sierra",SIERRA_CMD,"Al golpear un jugador le cortas un pedazo de carne comestible"));

    this.specialItemMap.put("PedazoDeCarne",
            new SpecialItem(Material.PAPER,"PedazoDeCarne",PEDAZO_DE_CARNE_CMD,"Te rellena dos muslos de comida"));

    this.specialItemMap.put("BotellaDeAcido", new SpecialItem(Material.SPLASH_POTION,"Botella de Acido",
            BOTELLA_DE_ACIDO_CMD, "Daña a las víctimas", Color.fromRGB(102, 51, 0)));

    this.specialItemMap.put("BotellaDeSulfuro", new SpecialItem(Material.SPLASH_POTION,"Botella de Sulfuro",
            BOTELLA_DE_SULFURO_CMD, "Al lanzarla, le aplica efecto de hambre, veneno y mareo a la víctima", Color.fromRGB(102, 51, 0)));

    this.specialItemMap.put("MinigunDeJuguete", new SpecialItem(Material.CROSSBOW,"Minigun de Juguete",
            MINIGUN_DE_JUGUETE_CMD, "Minigun que dispara 100 balas"));

    this.specialItemMap.put("SuplementoAlimenticio", new SpecialItem(Material.PAPER,"Suplemento Alimenticio",
            SUPLEMENTO_ALIMENTICIO_CMD, "Al darle click secundario (sin abrir inventario) te llena los muslos de hambre"));

    this.specialItemMap.put("Adrenalina", new SpecialItem(Material.PAPER,"Jeringa de Adrenalina",
            ADRENALINA_CMD, "Al consumirlo te brinda una resistencia mayor al daño"));

    this.specialItemMap.put("Banana", new SpecialItem(Material.PAPER,"Banana",
            BANANA_CMD, "No tiene ningún uso, pero te la puedes comer y rellenar 4 muslos"));

    this.specialItemMap.put("KitAstronauta", new SpecialItem(Material.PAPER,"Kit de Astronauta",
            KIT_ASTRONAUTA_CMD, "Si posees el item \"Agua\" te da saturación por 10minutos"));

    this.specialItemMap.put("Agua", new SpecialItem(Material.PAPER,"Agua",
            AGUA_CMD, "Al consumirlo obtienes 1 muslo de comida, pero si lo juntas con otros objetos, puedes obtener grandes beneficios"));

    this.specialItemMap.put("PolloChillon", new SpecialItem(Material.GOAT_HORN,"Pollo Chillón",
            POLLO_CHILLON_CMD, "Al usarlo se reproduce un sonido chistoso"));

    this.specialItemMap.put("Planta", new SpecialItem(Material.PAPER,"Planta",
            PLANTA_CMD, "Al darle click sobre el suelo, se pone la maceta, te brinda bayas cada 5 minutos"));
    this.specialItemMap.put("Fresa", new SpecialItem(Material.PAPER,"Fresa",
            FRESA_CMD, ""));

    SpecialItem sierra = this.specialItemMap.get("Sierra");
    if (sierra != null) {
      ItemStack sierraIS = sierra.getItemStack();
      sierraIS = addCustomTag(sierraIS, "usesLeft", 5);
      this.specialItemMap.put("Sierra", new SpecialItem(sierraIS));
    }

  }

  public ItemStack addCustomTag(ItemStack itemStack, String key, int value) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.getPersistentDataContainer().set(new NamespacedKey(DestinyTools.instance(), key), PersistentDataType.INTEGER, value);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private void skillCuerda(Player player) {
    int CUERDA_Y_TELEPORT = 8;
    Location location = player.getLocation();
    int NEW_Y = location.getBlockY() - CUERDA_Y_TELEPORT;

    player.teleport(new Location(location.getWorld(), location.getBlockX(), NEW_Y, location.getBlockZ()));

    ItemStack is = player.getInventory().getItemInMainHand();
    is.setAmount(is.getAmount() -  1);
    player.getInventory().setItemInMainHand(is);
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
      ItemStack is = player.getInventory().getItemInMainHand();
      is.setAmount(is.getAmount() -  1);
      player.getInventory().setItemInMainHand(is);
    }
  }

  private void skillAgua(Player player) {
    if (player != null) {
      player.setFoodLevel(player.getFoodLevel() + 2);
      player.playSound(player.getLocation(), Sound.ITEM_HONEY_BOTTLE_DRINK, 1f, 0.4f);
      ItemStack is = player.getInventory().getItemInMainHand();
      is.setAmount(is.getAmount() -  1);
      player.getInventory().setItemInMainHand(is);
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
          ItemStack is = player.getInventory().getItemInMainHand();
          is.setAmount(is.getAmount() -  1);
          player.getInventory().setItemInMainHand(is);
          break;
        }
      }
    }
  }

  private void skillAdrenalina(Player player) {
    if (player != null) {
      // 10 * 60 * 20 = 10 minutes on ticks, 1 second equals 20 ticks
      player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 60 * 20, 1));
      ItemStack is = player.getInventory().getItemInMainHand();
      is.setAmount(is.getAmount() -  1);
      player.getInventory().setItemInMainHand(is);
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

  private void skillPlanta(Block clickedBlock, BlockFace blockFace, ItemStack item) {
    if (clickedBlock != null && clickedBlock.getType() != null && clickedBlock.getType() != Material.AIR ) {
      Location loc = clickedBlock.getLocation().add(0.5, 1, 0.5);
      if (loc.getBlock().getType() == Material.AIR) {
        ItemFrame itemFrame = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);
        itemFrame.setFacingDirection(blockFace);
        if (item != null) {
          itemFrame.setItem(item);
          item.setAmount(item.getAmount() - 1);
        }
        Bukkit.getScheduler().runTaskTimer(DestinyTools.instance(), () -> {
          if (!itemFrame.getItem().getType().equals(Material.AIR)) {
            loc.getWorld().dropItemNaturally(loc, this.specialItemMap.get("Fresa").getItemStack());
          }
        }, 0, 5 * 60 * 20);
      }
    }
  }

  private void skillMinaElectrica(Block clickedBlock, BlockFace blockFace, ItemStack item) {
    if (clickedBlock != null  /* && event.getClickedBlock().getType() != Material.AIR */) {
      Location loc = clickedBlock.getLocation().add(0.5, 1, 0.5);
      if (loc.getBlock().getType() == Material.AIR) {
        ItemFrame itemFrame = (ItemFrame) loc.getWorld().spawnEntity(loc, EntityType.ITEM_FRAME);
        itemFrame.setFacingDirection(blockFace);

        if (item != null) {
          itemFrame.setItem(item);
          item.setAmount(item.getAmount() - 1); // discarded
        }
      }
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
          case GAFAS_TACTICOS_CMD: // gafas tácticas
            skillGafasTacticas(event.getPlayer(), item);
            break;
          case CUERDA_CMD: // cuerda
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
            break;
          case BANANA_CMD:
            skillBanana(player);
            break;
          case AGUA_CMD:
            skillAgua(player);
            break;
          case KIT_ASTRONAUTA_CMD:
            skillKitAstronauta(player);
            break;
          case POLLO_CHILLON_CMD:
            event.setCancelled(true);
            skillPolloChillon(player);
            break;
          case MINA_ELECTRICA_CMD:
            skillMinaElectrica(event.getClickedBlock(), event.getBlockFace(), item);
            break;
          case PLANTA_CMD:
            skillPlanta(event.getClickedBlock(), event.getBlockFace(),item);
            break;
        } // END SWITCH
      }
    }
  }


  @EventHandler
  public void onShootCrossBowEvent(EntityShootBowEvent e) {
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
    if (!(e.getWhoClicked() instanceof final Player p)) {
      return;
    }

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
    if ((e.getDamager() instanceof Player damager) && ((e.getEntity() instanceof Player) || (e.getEntity() instanceof Cow))) {
      LivingEntity damaged = (LivingEntity) e.getEntity();
      ItemStack weapon = damager.getPlayer().getInventory().getItemInMainHand();
      if (weapon.hasItemMeta()
              && weapon.getItemMeta().hasCustomModelData()
              && weapon.getItemMeta().getCustomModelData() == SIERRA_CMD) {

        boolean cancelDamage = skillSierra(weapon, damager, damaged);
        e.setCancelled(cancelDamage);
      }
    }
  }


  public boolean skillSierra(ItemStack sierra, Player damager, LivingEntity damaged) {
    int usesLeft = getUsesLeft(sierra);
    boolean cancelDamage = false;
    if (usesLeft > 0) {
      ItemStack reward = this.specialItemMap.get("PedazoDeCarne").getItemStack();
      damager.getInventory().addItem(reward);
      damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 60 * 20, 0));
      damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 60 * 20, 0));
      usesLeft--;
      sierra = setUsesLeft(sierra, usesLeft);
      damager.getInventory().setItemInMainHand(sierra);
    } else {
      Location loc = damager.getLocation();
      loc.getWorld().playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1f);
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
  /*
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    Location from = event.getFrom();
    Location to = event.getTo();

    if (from.distance(to) > 0) { // Si el jugador se ha movido
      float currentExp = player.getExp();
      float newExp = currentExp + 0.1f; // Incrementamos la experiencia en 0.1

      if (newExp >= 1f) { // Si la experiencia supera 1, el jugador muere
        player.setHealth(0);
      } else {
        player.setExp(newExp); // Actualizamos la barra de experiencia del jugador
      }
    } else { // Si el jugador no se ha movido
      float currentExp = player.getExp();
      float newExp = currentExp - 0.05f; // Decrementamos la experiencia en 0.05

      if (newExp < 0) { // Si la experiencia es negativa, el jugador muere
        player.setHealth(0);
      } else {
        player.setExp(newExp); // Actualizamos la barra de experiencia del jugador
      }
    }
  }

*/

}








