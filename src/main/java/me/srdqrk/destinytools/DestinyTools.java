package me.srdqrk.destinytools;

import co.aikar.commands.PaperCommandManager;
import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import lombok.Setter;
import me.srdqrk.destinytools.items.ItemsCMD;
import me.srdqrk.destinytools.items.ItemsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class DestinyTools extends JavaPlugin {

  private static DestinyTools instance;
   @Getter MiniMessage mm;

   private @Getter Logger insLogger;

   private @Getter ItemsManager itemsManager;

   private @Getter PaperCommandManager paperCommandManager;


  public static DestinyTools instance() {
    return DestinyTools.instance;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    DestinyTools.instance = this;

    // Libraries setup
    this.mm = MiniMessage.miniMessage();
    this.insLogger = this.logger;
    FastInvManager.register(this);

    // Register managers
    this.itemsManager = new ItemsManager();
    this.paperCommandManager = new PaperCommandManager(this);

    // Register Commands
    this.paperCommandManager.registerCommand(new ItemsCMD());


    this.logger.info( "§b DestinyTools is on fire!");


  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public void logStaff(String message) {
    for (Player staffPlayer : Bukkit.getOnlinePlayers()) {
      if (staffPlayer.hasPermission("destinytools.executer")) {
        Component parsed = this.mm.deserialize("<yellow>[INFO] " + message);
        staffPlayer.sendMessage(parsed);
      }
    }
  }

  public List<Player> getElHoyoParticipants() {
    String pluginName = "teamming-slots";
    Plugin ts = Bukkit.getPluginManager().getPlugin(pluginName);
    List<Player> participants;
    if (ts != null) {
      FileConfiguration config = ts.getConfig();
      if (config != null) {
        List<String> playerNames = config.getStringList("participantes");
        participants = playerNames.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull).
                collect(Collectors.toList());
      } else {
        this.logger.log(Level.SEVERE, "Error mientras se intentaba acceder a los participantes en DestinyTools." +
                "El plugin " + pluginName + " no tiene archivo de configuración.");
        participants = null;
      }
    } else {
      this.logger.log(Level.SEVERE, "Error mientras se intentaba acceder a los participantes en DestinyTools." +
              "El plugin " + pluginName + " no existe en la carpeta plugins.");
      participants = null;
    }
    return participants;
  }


}
