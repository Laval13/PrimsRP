package fr.Laval_13.PrimsRP.Gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import fr.Laval_13.PrimsRP.Character.Job;
import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;
import fr.Laval_13.PrimsRP.Utils.TimeUtils;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiBuilder;

public class Commissariat implements GuiBuilder {

	private Map<String, ItemStack> items = new HashMap<String, ItemStack>();
	private Map<Integer, String> slots = new HashMap<Integer, String>();

	@Override
	public String setName(){
		return PrimsRP.getInstance().getMenus().get("Menus.Commissariat.Title");
	}

	@Override
	public int setSize(){
		return PrimsRP.getInstance().getMenus().getInt("Menus.Commissariat.Size");
	}

	@Override
	public void setContents(Player player, Inventory inv){
		ConfigurationSection section = PrimsRP.getInstance().getMenus().getConfig().getConfigurationSection("Menus.Commissariat");
		for(String rank : section.getKeys(false)){

			if(!(rank.equalsIgnoreCase("Title") || rank.equalsIgnoreCase("Size"))){
			
				Material type = Material.valueOf(PrimsRP.getInstance().getMenus().get("Menus.Commissariat." + rank + ".Type"));
				String name = PrimsRP.getInstance().getMenus().get("Menus.Commissariat." + rank + ".Name");
				int slot = PrimsRP.getInstance().getMenus().getInt("Menus.Commissariat." + rank + ".Slot");
				String lore = PrimsRP.getInstance().getMenus().get("Menus.Commissariat." + rank + ".Lore");

				ItemStack item = new ItemBuilder(type, 1).setName(name).setLore(lore).toItemStack();
				items.put(rank, item);
				slots.put(slot, rank);
				inv.setItem(slot, item);
			
			}
		}
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack item, int slot){

		Character character = PrimsUtils.getCharacter(player);
		ItemStack currentItem = items.get(slots.get(slot));

		if(currentItem != null){
			if(currentItem == item){

				switch(slots.get(slot)){
					
					case "Cadet":
						setNewJob(character, Job.Cadet, Configuration.Other.TimeToRankupCadet.getTime());
						break;

					case "Sergent":
						setNewJob(character, Job.Sergent, Configuration.Other.TimeToRankupSergent.getTime());
						break;

					case "Sergent_Chef":
						setNewJob(character, Job.Sergent_Chef, Configuration.Other.TimeToRankupSergent_Chef.getTime());
						break;

					case "Commandant":
						setNewJob(character, Job.Commandant, Configuration.Other.TimeToRankupCommandant.getTime());
						break;

					case "FBI":
						setNewJob(character, Job.FBI, Configuration.Other.TimeToRankupFBI.getTime());
						break;

					default: break;
				}
			}
		}

	}

	public void setNewJob(Character character, Job job, int time){

		Player player = character.getPlayer();

		if(TimeUtils.convertTicktoMinutes(player.getStatistic(Statistic.PLAY_ONE_TICK)) >= time){
			if(character.getJob() != job){
				character.setJob(job);
				character.save();
				player.sendMessage(Configuration.Messages.ChangeToNewJob.getMessage().replace("%job%", job.getName()));
			}else{
				player.sendMessage(Configuration.Messages.HasAleardyThisJob.getMessage().replace("%job%", job.getName()));
			}
		}else{
			player.sendMessage(Configuration.Messages.IHaventGoodPlayedTime.getMessage().replace("time", String.valueOf(Configuration.Other.TimeToRankupMédecin.getTime())));
		}
		
	}

}
