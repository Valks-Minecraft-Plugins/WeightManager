package com.weightmanager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.weightmanager.listeners.WeightListener;

public class WeightManager extends JavaPlugin {
	@Override
	public void onEnable() {
		registerListeners();
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new WeightListener(), this);
	}
}
