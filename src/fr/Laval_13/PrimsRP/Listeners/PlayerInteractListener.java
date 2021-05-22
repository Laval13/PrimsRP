package fr.Laval_13.PrimsRP.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Gui.Phone.Phone;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event){

		Player player = event.getPlayer();

		if(event.getItem() == null) return;
		if(event.getItem().getItemMeta().getDisplayName() == null) return;

		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getItem().isSimilar(Configuration.Items.Phone.getItemStack())){
				PrimsRP.getInstance().getGuiManager().open(player, Phone.class);
			}
		}
	}
}
