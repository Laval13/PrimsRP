package fr.Laval_13.PrimsRP.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Laval_13.PrimsRP.Configuration;

public class PhoneCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String messsage, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length >= 1 && args[0].equalsIgnoreCase("give")){
				player.getInventory().addItem(Configuration.Items.Phone.getItemStack());
			}
		}
		return false;
	}
}
