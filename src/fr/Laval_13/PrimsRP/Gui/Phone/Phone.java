package fr.Laval_13.PrimsRP.Gui.Phone;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Gui.Phone.Darknet.Darknet;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class Phone implements GuiBuilder {

	private Map<String, ItemStack> items = new HashMap<String, ItemStack>();
	private Map<Integer, String> slots = new HashMap<Integer, String>();

	@Override
	public String setName(){
		return PrimsRP.getInstance().getMenus().get("Menus.Phone.Title");
	}

	@Override
	public int setSize(){
		return PrimsRP.getInstance().getMenus().getInt("Menus.Phone.Size");
	}

	@Override
	public void setContents(Player player, Inventory inv){
		ConfigurationSection section = PrimsRP.getInstance().getMenus().getConfig().getConfigurationSection("Menus.Phone");
		for(String items : section.getKeys(false)){
			
			if(!(items.equalsIgnoreCase("Title") || items.equalsIgnoreCase("Size"))){

				Material type = Material.valueOf(PrimsRP.getInstance().getMenus().get("Menus.Phone." + items + ".Type"));
				String name = PrimsRP.getInstance().getMenus().get("Menus.Phone." + items + ".Name");
				int slot = PrimsRP.getInstance().getMenus().getInt("Menus.Phone." + items + ".Slot");
				String lore = PrimsRP.getInstance().getMenus().get("Menus.Phone." + items + ".Lore");

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
					
					case "Message":
						player.closeInventory();
						Bukkit.dispatchCommand(player, "sms");
						break;

					case "Appel":
						PrimsRP.getInstance().getGuiManager().open(player, Appel.class);
						break;

					case "GPS":
						
						break;

					case "Darknet":
						if(PrimsRP.getInstance().getDarknetAcces().get(player) != null){
							if(PrimsRP.getInstance().getDarknetAcces().get(player) == true){
								PrimsRP.getInstance().getGuiManager().open(player, Darknet.class);
							}
						}else{
							PrimsRP.getInstance().getDarknetAcces().put(player, false);
							player.sendMessage(Configuration.Messages.DarknetWhatThePassword.getMessage());
							player.closeInventory();
						}
						break;

					default: break;
				}
			}
		}

	}
}
