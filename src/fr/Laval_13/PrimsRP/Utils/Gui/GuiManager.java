package fr.Laval_13.PrimsRP.Utils.Gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Laval_13.PrimsRP.PrimsRP;

public class GuiManager implements Listener {

	private static Map<Class<? extends GuiBuilder>, GuiBuilder> RegisteredMenus;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setLoader(Class<? extends GuiBuilder>... Builder){
		Bukkit.getPluginManager().registerEvents(PrimsRP.getInstance().getGuiManager(), PrimsRP.getInstance());
		RegisteredMenus = new HashMap();
		for(Class<? extends GuiBuilder> Gui : Builder){
			try{
				addMenu(Gui.newInstance());
			}catch(InstantiationException | IllegalAccessException e){
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event){

		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();

		if(event.getCurrentItem() == null){
			return;
		}

		RegisteredMenus.values().stream().filter(menu -> inv.getName().equalsIgnoreCase(menu.setName())).forEach(menu -> {
			menu.onClick(player, inv, current, event.getSlot());
			event.setCancelled(true);
		});

	}

	public void addMenu(GuiBuilder m){
		RegisteredMenus.put(m.getClass(), m);
	}

	public void open(Player player, Class<? extends GuiBuilder> gClass){

		if(!RegisteredMenus.containsKey(gClass)){
			return;
		}

		GuiBuilder menu = RegisteredMenus.get(gClass);
		Inventory inv = Bukkit.createInventory(null, menu.setSize(), menu.setName());
		menu.setContents(player, inv);

		new BukkitRunnable(){

			@Override
			public void run(){
				player.openInventory(inv);
			}

		}.runTaskLater(PrimsRP.getInstance(), 1);

	}

}