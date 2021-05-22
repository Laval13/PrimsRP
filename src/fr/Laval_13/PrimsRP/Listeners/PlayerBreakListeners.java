package fr.Laval_13.PrimsRP.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Character.Job;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;

public class PlayerBreakListeners implements Listener {

	public List<Location> RegenBlocks = new ArrayList<>();
	public Map<Location, BukkitTask> Tasks = new HashMap<>();

	@EventHandler
	public void onBreak(BlockBreakEvent event){

		Player player = event.getPlayer();
		Character character = PrimsUtils.getCharacter(player);
		Material material = event.getBlock().getType();
		Job job = character.getJob();
		
		if(job == null) return;

		if(player.hasPermission(Configuration.Other.BypassBreakBlockRegen.getPermission()) || player.isOp()){ return; }
		
		if(getRegenBlocks().contains(event.getBlock().getLocation())){
			event.setCancelled(true);
			return;
		}
				
		switch(job){
			case Bucheron:
				if(material == Material.LOG || material == Material.LOG_2){
					replaceBlock(event.getBlock(), job);
				}
				break;

			case Fermier:
				if(material == Material.WHEAT){
					replaceBlock(event.getBlock(), job);
				}
				break;

			case Mineur:
				if(material == Material.COAL_ORE){
					int random = ThreadLocalRandom.current().nextInt(0, 10);
					if(random == 5){
						event.getBlock().getDrops().forEach(item -> {
							item.setType(Material.EMERALD);
						});
					}
					replaceBlock(event.getBlock(), job);
				}
				break;

			default: break;
		}
	}

	private void replaceBlock(Block block, Job job){

		BlockState state = block.getState();

		new BukkitRunnable(){

			@Override
			public void run(){
				block.setType(Material.STAINED_GLASS);
			}
		}.runTaskLater(PrimsRP.getInstance(), 2L);
		getRegenBlocks().add(block.getLocation());

		
		if(job == Job.Mineur){
			BukkitTask task = new BukkitRunnable(){

				public void run(){
					regen(state, block.getLocation());
				}
			}.runTaskLater(PrimsRP.getInstance(), Configuration.Other.DelayMineur.getTimeMineur() * 20);
			Tasks.put(block.getLocation(), task);
		}else{
			BukkitTask task = new BukkitRunnable(){

				public void run(){
					regen(state, block.getLocation());
				}
			}.runTaskLater(PrimsRP.getInstance(), Configuration.Other.DelayDefault.getTime() * 20);
			Tasks.put(block.getLocation(), task);
		}

	}

	private void regen(BlockState state, Location location){
		state.update(true);
		RegenBlocks.remove(location);
		Tasks.remove(location);
	}

	private List<Location> getRegenBlocks(){
		return RegenBlocks;
	}
}
