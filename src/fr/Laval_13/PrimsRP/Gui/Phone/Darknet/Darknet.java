package fr.Laval_13.PrimsRP.Gui.Phone.Darknet;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class Darknet implements GuiBuilder {

	private Map<String, ItemStack> items = new HashMap<String, ItemStack>();
	private Map<Integer, String> slots = new HashMap<Integer, String>();

	@Override
	public String setName(){
		return PrimsRP.getInstance().getMenus().get("Menus.Darknet.Title");
	}

	@Override
	public int setSize(){
		return PrimsRP.getInstance().getMenus().getInt("Menus.Darknet.Size");
	}

	@Override
	public void setContents(Player player, Inventory inv){
		ConfigurationSection section = PrimsRP.getInstance().getMenus().getConfig().getConfigurationSection("Menus.Darknet");
		for(String items : section.getKeys(false)){

			if(!(items.equalsIgnoreCase("Title") || items.equalsIgnoreCase("Size"))){
			
				Material type = Material.valueOf(PrimsRP.getInstance().getMenus().get("Menus.Darknet." + items + ".Type"));
				String name = PrimsRP.getInstance().getMenus().get("Menus.Darknet." + items + ".Name");
				int slot = PrimsRP.getInstance().getMenus().getInt("Menus.Darknet." + items + ".Slot");
				String lore = PrimsRP.getInstance().getMenus().get("Menus.Darknet." + items + ".Lore");

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
					
					case "Inconnu":
						
						player.closeInventory();
						
						int one = 0;
						
						for(String messages : Configuration.Messages.DarknetDiscussionInconnu.getDiscussion()){
							new BukkitRunnable(){
								
								@Override
								public void run(){
									player.sendMessage(messages);
								}
							}.runTaskLater(PrimsRP.getInstance(), one * 3 * 20);
							one++;
						}
						
						break;

					case "Vendeur":
						
						player.closeInventory();
						
						int two  = 0;
						
						for(String messages : Configuration.Messages.DarknetDiscussionVendeur.getDiscussion()){
							new BukkitRunnable(){
								
								@Override
								public void run(){
									player.sendMessage(messages);
								}
							}.runTaskLater(PrimsRP.getInstance(), two * 3 * 20);
							two++;
						}						
						break;

					case "Acheteur":
						
						player.closeInventory();

						int three = 0;
						
						for(String messages : Configuration.Messages.DarknetDiscussionAcheteur.getDiscussion()){
							new BukkitRunnable(){
								
								@Override
								public void run(){
									player.sendMessage(messages);
								}
							}.runTaskLater(PrimsRP.getInstance(), three * 3 * 20);
							three++;
						}
						break;

					case "RequestCoordonnee":
						PrimsRP.getInstance().getGuiManager().open(player, ResquestSelector.class);
						break;

					case "Pub":
						player.closeInventory();
						player.sendMessage(Configuration.Messages.DarknetWhatYourPub.getMessage());
						PrimsRP.getInstance().getDarknetSenderPub().add(player);
						break;
						
					default: break;
				}
			}
		}

	}
}
