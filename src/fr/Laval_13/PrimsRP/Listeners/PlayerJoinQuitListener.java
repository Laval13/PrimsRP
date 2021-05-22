package fr.Laval_13.PrimsRP.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Runnable.JobRunnable;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;

public class PlayerJoinQuitListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
				
		if(PrimsUtils.getCharacter(player) == null){	
			Character character = new Character(player, null, null, null, 0, Character.CreatingStep.Prenom);
			player.teleport(Configuration.Locations.FirstSpawn.getLocation());
			player.sendMessage(Configuration.Messages.ChooseFirstName.getMessage());	
			PrimsRP.getInstance().getCreatingCharacter().put(player, character);
		}else{
			PrimsRP.getInstance().getAccount().put(player, PrimsUtils.getCharacter(player));
			new JobRunnable(player);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		JobRunnable.stop(player);
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		Player player = event.getPlayer();
		JobRunnable.stop(player);
	}
	
}
