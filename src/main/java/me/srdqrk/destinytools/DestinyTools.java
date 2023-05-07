package me.srdqrk.destinytools;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DestinyTools extends JavaPlugin {

  protected static DestinyTools instance;
   @Getter MiniMessage mm;

   private @Getter Logger insLogger;

  public static DestinyTools instance() {
    return DestinyTools.instance;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    DestinyTools.instance = this;
    this.mm = MiniMessage.miniMessage();
    this.insLogger = this.logger;
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
}
