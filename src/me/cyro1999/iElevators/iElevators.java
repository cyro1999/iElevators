package me.cyro1999.iElevators;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class iElevators
  extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "iElevators by Cyro1999 has been enabled.");
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
      (e.getClickedBlock().getType() == Material.SIGN_POST) || (e.getClickedBlock().getType() == Material.WALL_SIGN)))
    {
      BlockState state = e.getClickedBlock().getState();
      if ((state instanceof Sign))
      {
        Sign s = (Sign)state;
        if ((s.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9[Elevator]"))) && (s.getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&0Up"))))
        {
          Location startLoc = s.getLocation();
          double y = startLoc.getY() + 1.0D;
          while (Bukkit.getWorld(s.getWorld().getName()).getBlockAt(new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), y, startLoc.getZ())).getType() == Material.AIR)
          {
            y += 1.0D;
            if (y > 255.0D)
            {
              p.sendMessage(ChatColor.RED + "No valid location found.");
              break;
            }
          }
          if (y > 255.0D) {
            return;
          }
          Location finalLoc = new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), y, startLoc.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
          if (Bukkit.getWorld(s.getWorld().getName()).getBlockAt(new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), finalLoc.getY() + 1.0D, startLoc.getZ())).getType() != Material.AIR)
          {
            p.sendMessage(ChatColor.RED + "No valid location found.");
            return;
          }
          p.teleport(finalLoc.add(0.5D, 1.0D, 0.5D));
          p.sendMessage(ChatColor.GREEN + "You have been teleported!");
        }
        else if ((s.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9[Elevator]"))) && (s.getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&0Down"))))
        {
          Location startLoc = s.getLocation();
          double y = startLoc.getY() - 1.0D;
          while (Bukkit.getWorld(s.getWorld().getName()).getBlockAt(new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), y, startLoc.getZ())).getType() != Material.AIR)
          {
            y -= 1.0D;
            if (y < 1.0D)
            {
              p.sendMessage(ChatColor.RED + "No valid location found.");
              break;
            }
          }
          if (y < 1.0D) {
            return;
          }
          Location finalLoc = new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), y, startLoc.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
          if (Bukkit.getWorld(s.getWorld().getName()).getBlockAt(new Location(Bukkit.getWorld(s.getWorld().getName()), startLoc.getX(), finalLoc.getY() - 1.0D, startLoc.getZ())).getType() != Material.AIR)
          {
            p.sendMessage(ChatColor.RED + "No valid location found.");
            return;
          }
          p.teleport(finalLoc.add(0.5D, -1.0D, 0.5D));
          p.sendMessage(ChatColor.GREEN + "You have been teleported!");
        }
      }
    }
  }
  
  @EventHandler
  public void onSign(SignChangeEvent e)
  {
    if (e.getLine(0).equalsIgnoreCase("[Elevator]"))
    {
      if (e.getLine(1).equalsIgnoreCase("Up"))
      {
        e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&9[Elevator]"));
        e.setLine(1, ChatColor.translateAlternateColorCodes('&', "&0Up"));
        e.getPlayer().sendMessage(ChatColor.YELLOW + "You have created an elevator!");
        return;
      }
      if (e.getLine(1).equalsIgnoreCase("Down"))
      {
        e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&9[Elevator]"));
        e.setLine(1, ChatColor.translateAlternateColorCodes('&', "&0Down"));
        e.getPlayer().sendMessage(ChatColor.YELLOW + "You have created an elevator!");
      }
    }
  }
}
