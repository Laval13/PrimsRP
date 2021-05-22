package fr.Laval_13.PrimsRP.Utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.util.Vector;

import fr.Laval_13.PrimsRP.Character;
import fr.Laval_13.PrimsRP.Character.Job;
import fr.Laval_13.PrimsRP.Configuration;
import fr.Laval_13.PrimsRP.PrimsRP;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;

public class PrimsUtils {

	private static Economy Economy = null;

	public static Location getStringLocation(String location){
		String[] l = location.split(";");
		World world = Bukkit.getWorld(l[0]);
		double x = Double.parseDouble(l[1]);
		double y = Double.parseDouble(l[2]);
		double z = Double.parseDouble(l[3]);
		float yaw = Float.parseFloat(l[4]);
		float pitch = Float.parseFloat(l[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static String getLocationString(Location location){
		return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
	}

	public static boolean hasPhone(Player player){
		Material type = Material.valueOf(PrimsRP.getInstance().getConfiguration().get("Items.Phone.Type"));
		String name = PrimsRP.getInstance().getConfiguration().get("Items.Phone.Name");
		String lore = PrimsRP.getInstance().getConfiguration().get("Items.Phone.Lore");
		ItemStack item = new ItemBuilder(type, 1).setName(name).setLore(lore).toItemStack();
		return player.getInventory().contains(item);
	}

	public static void sendAccpetedRefusedMessage(Player player, String accepetedcommand, String refusedcommand){

		TextComponent accpeted = new TextComponent(Configuration.Messages.Accepted.getMessage());
		accpeted.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Configuration.Messages.Accepted.getMessage()).create()));
		accpeted.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, accepetedcommand));
		player.spigot().sendMessage(accpeted);

		TextComponent refused = new TextComponent(Configuration.Messages.Refused.getMessage());
		refused.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Configuration.Messages.Refused.getMessage()).create()));
		refused.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, refusedcommand));
		player.spigot().sendMessage(refused);

	}

	public String getDirectionTo(Player p, Location point){
		Location ploc = p.getLocation();
		ploc.setY(0.0D);
		point.setY(0.0D);
		Vector d = ploc.getDirection();
		Vector v = point.subtract(ploc).toVector().normalize();
		double a = Math.toDegrees(Math.atan2(d.getX(), d.getZ()));
		a -= Math.toDegrees(Math.atan2(v.getX(), v.getZ()));
		a = ((int) (a + 22.5D) % 360);
		if(a < 0.0D)
			a += 360.0D;
		return "" + "⬆⬈➡⬊⬇⬋⬅⬉".charAt((int) a / 45);
	}

	public static Character getCharacter(Player player){

		File folder = new File(PrimsRP.getInstance().getDataFolder(), "Character");
		FileUtils file = new FileUtils(folder.getAbsolutePath(), player.getName() + ".yml");

		if(!folder.exists()){
			folder.mkdirs();
		}

		if(!file.exists()){
			return null;
		}

		Character character = new Character(null, null, null, null, 0, Job.Chomeur);

		character.setNom(file.getString("Nom"));
		character.setPrenom(file.getString("Prenom"));
		character.setSexe(file.getString("Sexe"));
		character.setAge(file.getInt("Age"));
		character.setJob(Job.getFromName(file.getString("Job")));

		return character;

	}

	public static boolean isVaultSetup(){
		if(Bukkit.getPluginManager().getPlugin("Vault") != null){
			RegisteredServiceProvider<Economy> EconomyProvider = PrimsRP.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
			if(EconomyProvider != null){
				Economy = (Economy) EconomyProvider.getProvider();
			}
			return Economy != null;
		}
		return false;
	}

	public static Economy getEconomy(){
		return Economy;
	}

}
