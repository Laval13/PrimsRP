package fr.Laval_13.PrimsRP.Gui.Phone.Darknet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class ResquestSelector implements GuiBuilder {

	@Override
	public int setSize(){
		return 9 * 5;
	}

	@Override
	public String setName(){
		return "Requêtes des coordonées";
	}

	@Override
	public void setContents(Player player, Inventory inv){

		int slot = 0;

		for(Player players : Bukkit.getOnlinePlayers()){

			Character character = PrimsUtils.getCharacter(players);
						
			if((slot + 1) >= 9 * 5){
				break;
			}

			try{
				inv.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(players.getName()).setName("§7" + players.getName()).setLore("§7" + character.getNom() + " " + character.getPrenom()).toItemStack());				
				slot++;
			}catch(NullPointerException e){ }

		}
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack item, int slot){

		Player target = Bukkit.getPlayer(item.getItemMeta().getDisplayName().replace("§7", ""));

		target.sendMessage(Configuration.Messages.DarknetRequestTarget.getMessage());
		PrimsRP.getInstance().getDarknetRequestLocation().put(target, player);
		
	}

}
