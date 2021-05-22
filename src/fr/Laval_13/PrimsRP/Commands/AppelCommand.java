package fr.Laval_13.PrimsRP.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.Runnable.ConvocationRunnable;

public class AppelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String messsage, String[] args){
		if(args.length >= 1 && args[0].equalsIgnoreCase("refuse")){
			sender.sendMessage(Configuration.Messages.AppelRefused.getMessage());
		}else if(args.length >= 1 && args[0].equalsIgnoreCase("accept")){
			
			if(args[1].equalsIgnoreCase("Maire")){
				
				Player target = Bukkit.getPlayer(args[1]);
				
				target.sendMessage(Configuration.Messages.AppelMaireReceviedConvocation.getMessage());
				sender.sendMessage(Configuration.Messages.AppelMaireSentConvocation.getMessage());
				
				new ConvocationRunnable(target);				
				
				return false;
			}
			
			Player target = Bukkit.getPlayer(args[1]);
			Location location = target.getLocation();
			
			target.sendMessage(Configuration.Messages.AppelWaitHereServiceGoingOn.getMessage());
			sender.sendMessage(Configuration.Messages.AppelAccepted.getMessage().replace("%loc%", "X: " + location.getX() +  "Y: " + location.getY() +  "Z: " + location.getZ()));
		}
		return false;
	}
}
