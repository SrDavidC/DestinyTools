package me.srdqrk.destinytools.items;

import lombok.Getter;
import lombok.Setter;
import me.srdqrk.destinytools.DestinyTools;
import me.srdqrk.destinytools.items.strategies.*;
import me.srdqrk.destinytools.items.strategies.objects.IItemStrategy;
import me.srdqrk.destinytools.items.strategies.objects.IProjectileStrategy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

  public final int RIFLE_CMD = 1;
  public final int MINIGUN_DE_JUGUETE_CMD = 2;

  public final int FISHING_ROD_CMD = 3;
  public final int PEDAZO_DE_CARNE_CMD = 99;
  public final int GAFAS_TACTICOS_CMD = 101;
  public final int SIERRA_CMD = 102;
  public final int PATO_DE_HULE_CMD = 103;
  public final int CUERDA_CMD = 104;
  public final int BOTELLA_DE_ACIDO_CMD = 105;
  public final int BOTELLA_DE_SULFURO_CMD = 106;
  public final int PLANTA_CMD = 107;
  public final int FRESA_CMD = 108;
  public final int SUPLEMENTO_ALIMENTICIO_CMD = 109;
  public final int ADRENALINA_CMD = 110;
  public final int BANANA_CMD = 111;
  public final int POLLO_CHILLON_CMD = 112;
  public final int KIT_ASTRONAUTA_CMD = 113;
  public final int AGUA_CMD = 114;
  private final @Getter HashMap<String, SpecialItem> specialItemMap;
  private final @Getter HashMap<Integer, IItemStrategy> strategyMap;
  private final @Getter HashMap<Integer, IProjectileStrategy> projectileStrategyMap;
  private final MiniMessage mm;
  private @Getter @Setter boolean disabled;

  public ItemsManager() {
    this.specialItemMap = new HashMap<>();
    this.strategyMap = new HashMap<>();
    this.projectileStrategyMap = new HashMap<>();
    this.mm = DestinyTools.instance().getMm();
    DestinyTools.instance().getServer().getPluginManager().registerEvents(this, DestinyTools.instance());

    this.initSpecialItemMap();
    this.initStrategyMap();
    this.initProjectileStrategyMap();
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
    strategyMap.put(FISHING_ROD_CMD, new CanaDePescarStrategy());
  }
  private void initProjectileStrategyMap() {
    projectileStrategyMap.put(BOTELLA_DE_SULFURO_CMD, new SulfuroPotion());
  }

  // Strategy pattern applied
  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (!disabled) {
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
    } else {
      event.getPlayer().sendMessage(ChatColor.RED + "Los Special Items están desactivados");
    }

  }
  @EventHandler
  public void onArrowHit(ProjectileHitEvent event) {
    if (event.getEntity() instanceof Arrow arrow) {
      if (arrow.customName() != null && arrow.customName().equals(Component.text("RifleAMMO"))) {
        if (event.getHitEntity() == null) {
          arrow.remove();
        }
      }
    }
  }

  @EventHandler
  public void onProjectileHitPlayer(ProjectileHitEvent event) {
    Projectile projectileEntity = event.getEntity();
    int defaultModel = -1000;
    int model = defaultModel;
    ItemStack item = null;
    if (projectileEntity instanceof Arrow projectile) {
      item = projectile.getItemStack();
      if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
        model = item.getItemMeta().getCustomModelData();
      }

    } else if (projectileEntity instanceof ThrownPotion projectile) {
      item = projectile.getItem();
      if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
        model = item.getItemMeta().getCustomModelData();
      }
    }
    if (model != defaultModel) {
      IProjectileStrategy strategy = projectileStrategyMap.get(model);
      if (strategy != null) {
        strategy.execute(event, item);
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
    if (!disabled) {
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
    } else {
      e.setCancelled(true);
      e.getDamager().sendMessage(ChatColor.RED + "Los Special Items están desactivados");
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


  public static int getUsesLeft(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    Integer usesLeft = itemMeta.getPersistentDataContainer().get(new NamespacedKey(DestinyTools.instance(), "usesLeft"), PersistentDataType.INTEGER);
    if (usesLeft == null) {
      return 0;
    } else {
      return usesLeft;
    }
  }

  public static ItemStack setUsesLeft(ItemStack itemStack, int usesLeft) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.getPersistentDataContainer().set(new NamespacedKey(DestinyTools.instance(), "usesLeft"), PersistentDataType.INTEGER, usesLeft);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }



  private void initSpecialItemMap() {
    this.specialItemMap.put("Cuerda",
            new SpecialItem(Material.PAPER, "Cuerda", CUERDA_CMD, "Al dar click derecho", "te teletransporta 8 bloques abajo tuyo"));

    this.specialItemMap.put("CanaDePescar",
            new SpecialItem(Material.FISHING_ROD, "Caña De Pescar", FISHING_ROD_CMD, "Ojala no pesques un resfrío!","Uso limitado de 10"));

    this.specialItemMap.put("LentesTácticos",
            new SpecialItem(Material.PAPER, "Lentes Tácticos", GAFAS_TACTICOS_CMD, ""));

    this.specialItemMap.put("Rifle",
            new SpecialItem(Material.CROSSBOW, "Rifle", RIFLE_CMD, "Tiene 32 balas de puro dolor."));

    this.specialItemMap.put("PatoDeHule",
            new SpecialItem(Material.PAPER, "Pato De Hule", PATO_DE_HULE_CMD, "Te da 2 corazones extra de vida",
                    "Colócalo en el slot especial"));

    this.specialItemMap.put("Sierra",
            new SpecialItem(Material.PAPER, "Sierra", SIERRA_CMD, "Corta, golpea y saborea. 5 usos"));

    this.specialItemMap.put("PedazoDeCarne",
            new SpecialItem(Material.PAPER, "PedazoDeCarne", PEDAZO_DE_CARNE_CMD, "Te rellena dos muslos de comida"));

    this.specialItemMap.put("BotellaDeAcido", new SpecialItem(Material.PAPER, "Botella de Acido",
            BOTELLA_DE_ACIDO_CMD, "Daña a las víctimas en área", "Arrojadiza"));

    this.specialItemMap.put("BotellaDeSulfuro", new SpecialItem(Material.PAPER, "Botella de Sulfuro",
            BOTELLA_DE_SULFURO_CMD, "Triplete tóxico.", "Hambre, veneno y mareo a la víctima.", "Arrojadiza"));

    this.specialItemMap.put("MinigunDeJuguete", new SpecialItem(Material.CROSSBOW, "Minigun de Juguete",
            MINIGUN_DE_JUGUETE_CMD, "Minigun que dispara 80 balas"));

    this.specialItemMap.put("SuplementoAlimenticio", new SpecialItem(Material.PAPER, "Suplemento Alimenticio",
            SUPLEMENTO_ALIMENTICIO_CMD, "Grandiosa comida de categoría militar", "Te completa todos los muslos de comida"));

    this.specialItemMap.put("Adrenalina", new SpecialItem(Material.PAPER, "Jeringa de Adrenalina",
            ADRENALINA_CMD, "Impulso invencible" ,"Aumenta tu resistencia al daño por 10 minutos"));

    this.specialItemMap.put("Banana", new SpecialItem(Material.PAPER, "Banana",
            BANANA_CMD, "Sin uso específico","Puedes comértela y rellenar 4 muslitos"));

    this.specialItemMap.put("KitAstronauta", new SpecialItem(Material.PAPER, "Kit de Astronauta",
            KIT_ASTRONAUTA_CMD, "Explora el cosmos sin límites.", "Con el item 'Agua', obtén 10 minutos de", "saturación para mantener tu energía en las estrellas."));

    this.specialItemMap.put("Agua", new SpecialItem(Material.PAPER, "Agua",
            AGUA_CMD, "Un sorbo, un muslo.","Combínalo con el kit de Astronauta.", "y desbloquea grandiosas recompensas."  ));

    this.specialItemMap.put("PolloChillon", new SpecialItem(Material.GOAT_HORN, "Pollo Chillón",
            POLLO_CHILLON_CMD, "Al usarlo se reproduce un sonido chistoso"));

    this.specialItemMap.put("Planta", new SpecialItem(Material.PAPER, "Planta",
            PLANTA_CMD, "¡Cultiva dulces sueños!","Con un clic en el suelo, florece una maceta mágica",
            "que te obsequia jugosas fresas cada 5 minutos."," ", "Satisface tus antojos con solo estirar la mano."));
    this.specialItemMap.put("Fresa", new SpecialItem(Material.PAPER, "Fresa",
            FRESA_CMD, "Dulce tentación en cada mordisco."));

    SpecialItem sierra = this.specialItemMap.get("Sierra");
    if (sierra != null) {
      ItemStack sierraIS = sierra.getItemStack();
      sierraIS = addCustomTag(sierraIS, "usesLeft", 5);
      this.specialItemMap.put("Sierra", new SpecialItem(sierraIS));
    }
    SpecialItem canaDePescar = this.specialItemMap.get("CanaDePescar");
    if (canaDePescar != null) {
      ItemStack is = canaDePescar.getItemStack();
      is = addCustomTag(is,"usesLeft", 20);
      this.specialItemMap.put("CanaDePescar", new SpecialItem(is));
    }

  }
  @EventHandler
  public void onSnowballHit(final ProjectileHitEvent e) {
    if (e.getEntity().getType() != EntityType.SNOWBALL) {
      return;
    }
    double snowballDamage = 0.05;
    final Entity hitEntity = e.getHitEntity();
    if (hitEntity != null) {
      if (hitEntity instanceof Damageable) {
        ((Damageable)hitEntity).damage(snowballDamage);
      }
    }
  }

}








