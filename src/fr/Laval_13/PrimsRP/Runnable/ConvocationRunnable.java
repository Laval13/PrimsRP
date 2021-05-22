package fr.Laval_13.PrimsRP.Runnable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;

public class ConvocationRunnable {

	public ConvocationRunnable(Player player){
		
		ItemStack item = Configuration.Items.Convocation.getItemStack();
		
		player.getInventory().addItem(item);
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				if(player.getInventory().contains(item)){
					player.getInventory().remove(item);
				}
				player.sendMessage(Configuration.Messages.AppelMaireRemovedConvocation.getMessage());
			}
		}.runTaskLater(PrimsRP.getInstance(), 5 * 60 * 20);
		
	}
	
}
