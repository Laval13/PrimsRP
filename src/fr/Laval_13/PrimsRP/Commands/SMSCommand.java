package fr.Laval_13.PrimsRP.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;

public class SMSCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String messsage, String[] args){
		Player player = (Player) sender;

		if(!PrimsUtils.hasPhone(player)){
			player.sendMessage(Configuration.Messages.SMSPlayerHaventPhone.getMessage());
			return false;
		}

		if(args.length <= 1){
			player.sendMessage(Configuration.Messages.SMSHelp.getMessage());
		}else{

			StringBuilder str = new StringBuilder();
			for(int i = 1; i < args.length; i++){
				str.append(args[i] + " ");
			}

			String message = str.toString();
			Player target = Bukkit.getPlayer(args[0]);

			if(target != null){
				if(PrimsUtils.hasPhone(target)){
					player.sendMessage(Configuration.Messages.SMSSent.getMessage().replace("%player%", player.getName()).replace("%message%", message));
					target.sendMessage(Configuration.Messages.SMSRecived.getMessage().replace("%player%", player.getName()).replace("%message%", message));
				}else{
					player.sendMessage(Configuration.Messages.SMSTargetHaventPhone.getMessage());
				}
			}else{
				player.sendMessage(Configuration.Messages.SMSTargetOffline.getMessage());
			}
		}
		return false;
	}
}
