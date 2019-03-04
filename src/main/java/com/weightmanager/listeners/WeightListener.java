package com.weightmanager.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.weightmanager.events.InventoryChangeEvent;

public class WeightListener implements Listener {
	public static float defaultWalkSpeed = 0.25f;
	
	@EventHandler
	private void playerPickupItemEvent(InventoryPickupItemEvent e) {
		if (e.getInventory().getHolder() instanceof Player) {
			Player p = (Player) e.getInventory().getHolder();
			Bukkit.getPluginManager().callEvent(new InventoryChangeEvent(p));
		}
	}
	
	@EventHandler
	private void playerDropItemEvent(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		Bukkit.getPluginManager().callEvent(new InventoryChangeEvent(p));
	}
	
	@EventHandler
	private void playerConsumeItemEvent(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		Bukkit.getPluginManager().callEvent(new InventoryChangeEvent(p));
	}
	
	@EventHandler
	private void playerBreakItemEvent(PlayerItemBreakEvent e) {
		Player p = e.getPlayer();
		Bukkit.getPluginManager().callEvent(new InventoryChangeEvent(p));
	}
	
	@EventHandler
	private void playerCraftEvent(CraftItemEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			Bukkit.getPluginManager().callEvent(new InventoryChangeEvent(p));
		}
	}
	
	@EventHandler
	private void inventoryChangeEvent(InventoryChangeEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.SURVIVAL)
			p.setWalkSpeed(defaultWalkSpeed - calcWeight(p.getInventory()));
	}
	
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() == GameMode.SURVIVAL)
			p.setWalkSpeed(defaultWalkSpeed - calcWeight(p.getInventory()));
	}
	
	private float calcWeight(Inventory inv) {
		float weight = 0.0f;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				if (item.getType() != Material.AIR) {
					ItemMeta im = item.getItemMeta();
					List<String> lore = im.getLore();
					if (lore != null) {
						for (String element : lore) {
							if (element.contains("Weight")) {
								double weightedItem = Float.parseFloat(element.substring("&7Weight: &f".length()));
								weight += weightedItem * item.getAmount();
							}
						}
					}
				}
			}
		}
		return weight;
	}
}
