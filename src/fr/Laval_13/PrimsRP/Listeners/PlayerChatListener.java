package fr.Laval_13.PrimsRP.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Runnable.JobRunnable;
import fr.Laval_13.PrimsRP.Utils.ActionBarAPI;

public class PlayerChatListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();

		if(PrimsRP.getInstance().getDarknetSenderPub().contains(player)){
			event.setFormat(Configuration.Messages.DarknetPubChatFormat.getMessage().replace("%player%", player.getName()).replace("%message%", event.getMessage()));
			PrimsRP.getInstance().getDarknetSenderPub().remove(player);
		}

		if(PrimsRP.getInstance().getDarknetRequestLocation().containsKey(player)){

			String message = event.getMessage();
			Player sender = PrimsRP.getInstance().getDarknetRequestLocation().get(player);
			Location location = player.getLocation();

			if(message.equalsIgnoreCase("Oui") && !message.contains(" ")){
				player.sendMessage(Configuration.Messages.DarknetRequestAccepted.getMessage());
				sender.sendMessage(Configuration.Messages.DarknetRequestSenderAccepted.getMessage().replace("%loc%", "X: " + location.getX() + "Y: " + location.getY() + "Z: " + location.getZ()));
			}else if(message.equalsIgnoreCase("Non") && !message.contains(" ")){
				player.sendMessage(Configuration.Messages.DarknetRequestRefuseed.getMessage());
				sender.sendMessage(Configuration.Messages.DarknetRequestSenderRefused.getMessage());

				Bukkit.getScheduler().runTask(PrimsRP.getInstance(), new BukkitRunnable(){

					int timer = 20;

					@Override
					public void run(){
						if(timer <= 0){
							sender.sendMessage(Configuration.Messages.DarknetRequestSenderAccepted.getMessage().replace("%loc%", "X: " + location.getX() + "Y: " + location.getY() + "Z: " + location.getZ()));
							cancel();
						}
						ActionBarAPI.sendMessage(sender, Configuration.Messages.DarknetRequestActionBarHack.getMessage().replace("%time%", String.valueOf(timer)));
						timer--;
					}
				});

			}else{
				player.sendMessage(Configuration.Messages.DarknetRequestInvalidChoose.getMessage());
			}
		}

		if(PrimsRP.getInstance().getDarknetAcces().containsKey(player) && PrimsRP.getInstance().getDarknetAcces().get(player) == false){
			if(!event.getMessage().equals(Configuration.Other.DarknetPassword.getDarknetPassword())){
				player.sendMessage(Configuration.Messages.DarknetWrongPassword.getMessage());
				PrimsRP.getInstance().getDarknetAcces().remove(player);
			}else{
				player.sendMessage(Configuration.Messages.DarknetGoodPassword.getMessage());
				PrimsRP.getInstance().getDarknetAcces().put(player, true);
			}
			event.setCancelled(true);	
		}

		if(PrimsRP.getInstance().getCreatingCharacter().containsKey(player)){

			Character character = PrimsRP.getInstance().getCreatingCharacter().get(player);
			String message = event.getMessage();

			switch(character.getStep()){

				case Prenom:
					if(message.matches("[a-zA-Z]+") && !message.contains(" ")){
						character.setPrenom(message);
						character.setStep(Character.CreatingStep.Nom);
						PrimsRP.getInstance().getCreatingCharacter().put(player, character);
						player.sendMessage(Configuration.Messages.ChooseName.getMessage());
					}else{
						player.sendMessage(Configuration.Messages.InvalidFirstName.getMessage());
					}
					break;

				case Nom:
					if(message.matches("[a-zA-Z]+") && !message.contains(" ")){
						character.setNom(message);
						character.setStep(Character.CreatingStep.Sexe);
						PrimsRP.getInstance().getCreatingCharacter().put(player, character);
						player.sendMessage(Configuration.Messages.ChooseSexe.getMessage());
					}else{
						player.sendMessage(Configuration.Messages.InvalidName.getMessage());
					}
					break;

				case Sexe:
					if((message.equalsIgnoreCase("Femme") || message.equalsIgnoreCase("Homme")) && !message.contains(" ")){
						character.setSexe(message);
						character.setStep(Character.CreatingStep.Age);
						PrimsRP.getInstance().getCreatingCharacter().put(player, character);
						player.sendMessage(Configuration.Messages.ChooseAge.getMessage());
					}else{
						player.sendMessage(Configuration.Messages.InvalidSexe.getMessage());
					}
					break;

				case Age:
					try{

						int age = Integer.parseInt(message);

						if(!(age >= 80)){
							character.setAge(age);
							character.save();
							player.sendMessage(Configuration.Messages.FinishCreatingCharacter.getMessage());
							player.teleport(Configuration.Locations.FinishCreatingCharacter.getLocation());
							PrimsRP.getInstance().getCreatingCharacter().remove(player);
							new JobRunnable(player);
						}else{
							player.sendMessage(Configuration.Messages.LimitAge.getMessage());
						}
					}catch(NumberFormatException e){
						player.sendMessage(Configuration.Messages.InvalidAge.getMessage());
					}
					break;

				default:
					break;
			}
			event.setCancelled(true);
		}
	}
}
