package fr.Laval_13.PrimsRP.Runnable;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;

public class JobRunnable implements Runnable {
		
	private static Map<Player, Integer> runnables = new HashMap<>();
	
	public JobRunnable(Player player){
		getRunnables().put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(PrimsRP.getInstance(), this, 5 * 60 * 20, 5 * 60 * 20));			
	}	

	@Override
	public void run(){
		for(Player players : Bukkit.getOnlinePlayers()){
			if(PrimsUtils.isVaultSetup()){
				PrimsUtils.getEconomy().depositPlayer(players, Configuration.Other.Salary.getSalary());
			}
		}
	}
	
	public static void stop(Player player){
		Bukkit.getScheduler().cancelTask(getRunnables().get(player));
	}	
	
	public static Map<Player, Integer> getRunnables(){
		return runnables;
	}
	
}
