package me.srdqrk.destinytools.items;

import lombok.Getter;
import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
  public final int AGUA_CMD = 114;

  private final @Getter HashMap<String, SpecialItem> specialItemMap;
  private final @Getter HashMap<Integer, IItemStrategy> strategyMap;
  private final MiniMessage mm;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();
    this.strategyMap = new HashMap<>();
    this.mm = DestinyTools.instance().getMm();
    DestinyTools.instance().getServer().getPluginManager().registerEvents(this, DestinyTools.instance());

    this.initSpecialItemMap();
    this.initStrategyMap();
  }


  public ItemStack addCustomTag(ItemStack itemStack, String key, int value) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.getPersistentDataContainer().set(new NamespacedKey(DestinyTools.instance(), key), PersistentDataType.INTEGER, value);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private void initStrategyMap() {
    strategyMap.put(GAFAS_TACTICOS_CMD, new GafasTacticasStrategy());
    strategyMap.put(CUERDA_CMD, new CuerdaStrategy());
    strategyMap.put(BANANA_CMD, new BananaStrategy());
    strategyMap.put(RIFLE_CMD, new RifleStrategy());
    strategyMap.put(POLLO_CHILLON_CMD, new PolloChillonStrategy());
    strategyMap.put(MINIGUN_DE_JUGUETE_CMD, new MinigunStrategy());
    strategyMap.put(AGUA_CMD, new AguaStrategy());
    strategyMap.put(KIT_ASTRONAUTA_CMD, new KitAstronautaStrategy());
    strategyMap.put(ADRENALINA_CMD, new AdrenalinaStrategy());
    strategyMap.put(PLANTA_CMD, new PlantaStrategy());
    strategyMap.put(SUPLEMENTO_ALIMENTICIO_CMD, new SuplementoAlimenticioStrategy());
    strategyMap.put(FRESA_CMD, new FresaStrategy());
    strategyMap.put(PEDAZO_DE_CARNE_CMD, new PedazoDeCarneStrategy());
    strategyMap.put(BOTELLA_DE_ACIDO_CMD, new BotellaDeAcidoStrategy());
    strategyMap.put(BOTELLA_DE_SULFURO_CMD, new BotellaDeSulfuroStrategy());
  }
  @EventHandler
  // Strategy pattern applied
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      ItemStack item = event.getItem();
      if (item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
        int model = item.getItemMeta().getCustomModelData();
        IItemStrategy strategy = strategyMap.get(model);
        if (strategy != null) {
          strategy.execute(event, item);
        }
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
            snowball.customName(mm.deserialize("MinigunAMMO"));
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
    } else if ((event.getEntity() instanceof Player || event.getEntity() instanceof Cow) && event.getDamager() instanceof Snowball snowball) {
      if (snowball.customName().equals("MinigunAMMO")) {
        double damage = 0.1;
        event.setDamage(damage);
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

  // TODO: may could be a good idea  refatc this strategy pattern, like attack_strategy
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
      damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15 * 20, 0));
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
      case BOTELLA_DE_ACIDO_CMD:
        Entity hitEntity = e.getHitEntity();
        if (hitEntity != null) {
          if (hitEntity instanceof Player player) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2 * 20, 0));
          }
        }
        break;
      case BOTELLA_DE_SULFURO_CMD:
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

  @EventHandler
  public void onArrowHit(ProjectileHitEvent event) {
    if (event.getEntity() instanceof Arrow) {
      Arrow arrow = (Arrow) event.getEntity();
      if (arrow.customName() != null && arrow.customName().equals("RifleAMMO")) {
        if (event.getHitEntity() == null) {
          arrow.remove();
        }
      }
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



  private void initSpecialItemMap() {
    this.specialItemMap.put("Cuerda",
            new SpecialItem(Material.PAPER, "Cuerda", CUERDA_CMD, "Al usar, el jugador es tpeado 8 bloques abajo"));

    this.specialItemMap.put("CanaDePescar",
            new SpecialItem(Material.FISHING_ROD, "Caña De Pescar", FISHING_ROD_CMD, "Uso limitado de 10"));

    this.specialItemMap.put("LentesTácticos",
            new SpecialItem(Material.PAPER, "Lentes Tácticos", GAFAS_TACTICOS_CMD, ""));

    this.specialItemMap.put("Rifle",
            new SpecialItem(Material.CROSSBOW, "Rifle", RIFLE_CMD, "Tiene 32 balas de puro dolor."));

    this.specialItemMap.put("PatoDeHule",
            new SpecialItem(Material.PAPER, "Pato De Hule", PATO_DE_HULE_CMD, "Si lo colocas en en slot especial, te da 2 corazones extra de vida"));

    this.specialItemMap.put("Sierra",
            new SpecialItem(Material.PAPER, "Sierra", SIERRA_CMD, "Al golpear un jugador le cortas un pedazo de carne comestible"));

    this.specialItemMap.put("PedazoDeCarne",
            new SpecialItem(Material.PAPER, "PedazoDeCarne", PEDAZO_DE_CARNE_CMD, "Te rellena dos muslos de comida"));

    this.specialItemMap.put("BotellaDeAcido", new SpecialItem(Material.PAPER, "Botella de Acido",
            BOTELLA_DE_ACIDO_CMD, "Daña a las víctimas"));

    this.specialItemMap.put("BotellaDeSulfuro", new SpecialItem(Material.PAPER, "Botella de Sulfuro",
            BOTELLA_DE_SULFURO_CMD, "Al lanzarla, le aplica efecto de hambre, veneno y mareo a la víctima"));

    this.specialItemMap.put("MinigunDeJuguete", new SpecialItem(Material.CROSSBOW, "Minigun de Juguete",
            MINIGUN_DE_JUGUETE_CMD, "Minigun que dispara 100 balas"));

    this.specialItemMap.put("SuplementoAlimenticio", new SpecialItem(Material.PAPER, "Suplemento Alimenticio",
            SUPLEMENTO_ALIMENTICIO_CMD, "Al darle click secundario (sin abrir inventario) te llena los muslos de hambre"));

    this.specialItemMap.put("Adrenalina", new SpecialItem(Material.PAPER, "Jeringa de Adrenalina",
            ADRENALINA_CMD, "Al consumirlo te brinda una resistencia mayor al daño"));

    this.specialItemMap.put("Banana", new SpecialItem(Material.PAPER, "Banana",
            BANANA_CMD, "No tiene ningún uso, pero te la puedes comer y rellenar 4 muslos"));

    this.specialItemMap.put("KitAstronauta", new SpecialItem(Material.PAPER, "Kit de Astronauta",
            KIT_ASTRONAUTA_CMD, "Si posees el item \"Agua\" te da saturación por 10minutos"));

    this.specialItemMap.put("Agua", new SpecialItem(Material.PAPER, "Agua",
            AGUA_CMD, "Al consumirlo obtienes 1 muslo de comida, pero si lo juntas con otros objetos, puedes obtener grandes beneficios"));

    this.specialItemMap.put("PolloChillon", new SpecialItem(Material.GOAT_HORN, "Pollo Chillón",
            POLLO_CHILLON_CMD, "Al usarlo se reproduce un sonido chistoso"));

    this.specialItemMap.put("Planta", new SpecialItem(Material.PAPER, "Planta",
            PLANTA_CMD, "Al darle click sobre el suelo, se pone la maceta, te brinda bayas cada 5 minutos"));
    this.specialItemMap.put("Fresa", new SpecialItem(Material.PAPER, "Fresa",
            FRESA_CMD, ""));

    SpecialItem sierra = this.specialItemMap.get("Sierra");
    if (sierra != null) {
      ItemStack sierraIS = sierra.getItemStack();
      sierraIS = addCustomTag(sierraIS, "usesLeft", 5);
      this.specialItemMap.put("Sierra", new SpecialItem(sierraIS));
    }
    SpecialItem canaDePescar = this.specialItemMap.get("CanaDePescar");
    if (canaDePescar != null) {
      ItemStack is = canaDePescar.getItemStack();
      Damageable damageable = (Damageable) is.getItemMeta();
      damageable.setDamage(54);
      is.setItemMeta(damageable);
      this.specialItemMap.put("CanaDePescar", new SpecialItem(is));
    }

  }

}








