package fr.Laval_13.PrimsRP.Gui.Phone;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Character.Job;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class Appel implements GuiBuilder {

	private Map<String, ItemStack> items = new HashMap<String, ItemStack>();
	private Map<Integer, String> slots = new HashMap<Integer, String>();

	@Override
	public String setName(){
		return PrimsRP.getInstance().getMenus().get("Menus.Appel.Title");
	}

	@Override
	public int setSize(){
		return PrimsRP.getInstance().getMenus().getInt("Menus.Appel.Size");
	}

	@Override
	public void setContents(Player player, Inventory inv){
		ConfigurationSection section = PrimsRP.getInstance().getMenus().getConfig().getConfigurationSection("Menus.Appel");
		for(String items : section.getKeys(false)){

			if(!(items.equalsIgnoreCase("Title") || items.equalsIgnoreCase("Size"))){
			
				Material type = Material.valueOf(PrimsRP.getInstance().getMenus().get("Menus.Appel." + items + ".Type"));
				String name = PrimsRP.getInstance().getMenus().get("Menus.Appel." + items + ".Name");
				int slot = PrimsRP.getInstance().getMenus().getInt("Menus.Appel." + items + ".Slot");
				String lore = PrimsRP.getInstance().getMenus().get("Menus.Appel." + items + ".Lore");

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

					case "Police":
						sendAppel(player, Job.Cadet);
						sendAppel(player, Job.Sergent);
						sendAppel(player, Job.Sergent_Chef);
						sendAppel(player, Job.Commandant);
						sendAppel(player, Job.FBI);
						player.sendMessage(Configuration.Messages.AppelPoliciers.getMessage());
						player.closeInventory();
						break;

					case "EMS":
						player.sendMessage(Configuration.Messages.AppelMédecin.getMessage());
						sendAppel(player, Job.Médecin);
						player.closeInventory();
						break;

					case "UberEat":
						player.sendMessage(Configuration.Messages.AppelCuisinier.getMessage());
						sendAppel(player, Job.Cuisinier);
						player.closeInventory();
						break;

					case "Mairie":
						player.sendMessage(Configuration.Messages.AppelMaire.getMessage());
						sendAppel(player, Job.Maire);
						player.closeInventory();
						break;

					default:
						break;
				}
			}
		}

	}

	public static void sendAppel(Player player, Job job){
		for(Player players : Bukkit.getOnlinePlayers()){
			Character character = PrimsUtils.getCharacter(players);
			if(character.getJob() == null) return;
			if(character.getJob() == job){
				switch(job){
					
					case Médecin:
						PrimsUtils.sendAccpetedRefusedMessage(players, "appel accept " + player.getName(), "appel refuse");
						break;

					case Cuisinier:
						PrimsUtils.sendAccpetedRefusedMessage(players, "appel accept " + player.getName(), "appel refuse");
						break;

					case Maire:
						PrimsUtils.sendAccpetedRefusedMessage(players, "appel accept " + player.getName(), "appel refuse");
						break;
						
					default: PrimsUtils.sendAccpetedRefusedMessage(players, "appel accept " + player.getName(), "appel refuse");
				}
			}
		}
	}

}
