package fr.Laval_13.PrimsRP.Listeners;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Character.Job;
import fr.Laval_13.PrimsRP.Gui.Commissariat;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;
import fr.Laval_13.PrimsRP.Utils.TimeUtils;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class CitizensListener implements Listener {

	@EventHandler
	public void onClick(NPCRightClickEvent event){

		int id = event.getNPC().getId();
		Player player = event.getClicker();
		Character character = PrimsUtils.getCharacter(player);
		
		if(id == Configuration.Other.NPCCuisinier.getNPCId()){
			setNewJob(character, Job.Cuisinier);
		}

		if(id == Configuration.Other.NPCBucheron.getNPCId()){
			setNewJob(character, Job.Bucheron);
		}

		if(id == Configuration.Other.NPCMineur.getNPCId()){
			setNewJob(character, Job.Mineur);
		}

		if(id == Configuration.Other.NPCFermier.getNPCId()){
			setNewJob(character, Job.Fermier);
		}

		if(id == Configuration.Other.NPCEboueur.getNPCId()){
			setNewJob(character, Job.Eboueur);
		}
		
		if(id == Configuration.Other.NPCMédecin.getNPCId()){
			if(TimeUtils.convertTicktoMinutes(player.getStatistic(Statistic.PLAY_ONE_TICK)) >= Configuration.Other.TimeToRankupMédecin.getTime()){
				setNewJob(character, Job.Médecin);				
			}else{
				player.sendMessage(Configuration.Messages.IHaventGoodPlayedTime.getMessage().replace("%time%", String.valueOf(Configuration.Other.TimeToRankupMédecin.getTime())));
			}
		}
		
		if(id == Configuration.Other.NPCCommissariat.getNPCId()){
			PrimsRP.getInstance().getGuiManager().open(player, Commissariat.class);
		}
		
	}
	
	public void setNewJob(Character character, Job job){
		
		Player player = character.getPlayer();
		
		if(character.getJob() != job){
			character.setJob(job);
			character.save();
			player.sendMessage(Configuration.Messages.ChangeToNewJob.getMessage().replace("%job%", job.getName()));
		}else{
			player.sendMessage(Configuration.Messages.HasAleardyThisJob.getMessage().replace("%job%", job.getName()));
		}		
	}
	
}
