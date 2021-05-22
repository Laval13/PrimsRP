package fr.Laval_13.PrimsRP.Gui.Phone;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class GPS implements GuiBuilder {

	private Map<String, ItemStack> items = new HashMap<String, ItemStack>();
	private Map<Integer, String> slots = new HashMap<Integer, String>();

	@Override
	public String setName(){
		return PrimsRP.getInstance().getMenus().get("Menus.GPS.Title");
	}

	@Override
	public int setSize(){
		return PrimsRP.getInstance().getMenus().getInt("Menus.GPS.Size");
	}

	@Override
	public void setContents(Player player, Inventory inv){
		ConfigurationSection section = PrimsRP.getInstance().getMenus().getConfig().getConfigurationSection("Menus.GPS");
		for(String items : section.getKeys(false)){

			if(!(items.equalsIgnoreCase("Title") || items.equalsIgnoreCase("Size"))){
			
				Material type = Material.valueOf(PrimsRP.getInstance().getMenus().get("Menus.GPS." + items + ".Type"));
				String name = PrimsRP.getInstance().getMenus().get("Menus.GPS." + items + ".Name");
				int slot = PrimsRP.getInstance().getMenus().getInt("Menus.GPS." + items + ".Slot");
				String lore = PrimsRP.getInstance().getMenus().get("Menus.GPS." + items + ".Lore");

				ItemStack item = new ItemBuilder(type, 1).setName(name).setLore(lore).toItemStack();
				this.items.put(items, item);
				slots.put(slot, items);
				inv.setItem(slot, item);
			
			}
		}
	}
	
	@Override
	public void onClick(Player player, Inventory inv, ItemStack item, int slot){

		ItemStack currentItem = items.get(slots.get(slot));

		if(currentItem != null){
			if(currentItem.isSimilar(item)){

				switch(slots.get(slot)){
					
					case "":
						break;
						
					default: break;
				}
			}
		}

	}
}
