package fr.Laval_13.PrimsRP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.Laval_13.PrimsRP.Commands.AppelCommand;
import fr.Laval_13.PrimsRP.Commands.PhoneCommand;
import fr.Laval_13.PrimsRP.Commands.SMSCommand;
import fr.Laval_13.PrimsRP.Gui.Commissariat;
import fr.Laval_13.PrimsRP.Gui.Phone.Appel;
import fr.Laval_13.PrimsRP.Gui.Phone.Phone;
import fr.Laval_13.PrimsRP.Gui.Phone.Darknet.Darknet;
import fr.Laval_13.PrimsRP.Gui.Phone.Darknet.ResquestSelector;
import fr.Laval_13.PrimsRP.Listeners.PlayerBreakListeners;
import fr.Laval_13.PrimsRP.Listeners.PlayerChatListener;
import fr.Laval_13.PrimsRP.Listeners.PlayerInteractListener;
import fr.Laval_13.PrimsRP.Listeners.PlayerJoinQuitListener;
import fr.Laval_13.PrimsRP.Utils.Settings;
import fr.Laval_13.PrimsRP.Utils.Gui.GuiManager;

public class PrimsRP extends JavaPlugin {
	
	private static PrimsRP Instance;
	private Map<Player, Character> CreatingCharacter = new HashMap<>();
	private Map<Player, Character> Account = new HashMap<>();
	private Map<Player, Boolean> DarknetAcces = new HashMap<>();
	private Map<Player, Player> DarknetRequestLocation = new HashMap<>();
	private List<Player> DarknetSenderPub = new ArrayList<>();
	private GuiManager GuiManager = new GuiManager();
	private Settings config, lang, menus;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){
		PrimsRP.setInstance(this);
		
		getLogger().info("Démarrage en cours...");
		getLogger().info("Si un probleme surviens le Serveur seras automatiquement arreté");
		
		try{
			
			getConfig().options().copyDefaults(true);
			saveConfig();
			
			config = new Settings(this, "config", false);
			lang = new Settings(this, "lang", false);
			menus = new Settings(this, "menus", false);
			
			PrimsRP.getInstance().getGuiManager().setLoader(Phone.class, Commissariat.class, Appel.class, Darknet.class, ResquestSelector.class);
			
			getCommand("sms").setExecutor(new SMSCommand());
			getCommand("appel").setExecutor(new AppelCommand());
			getCommand("phone").setExecutor(new PhoneCommand());
			
			Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
			Bukkit.getPluginManager().registerEvents(new PlayerBreakListeners(), this);
			Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
			Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
			
		}catch(Exception e){
			getLogger().info("Merci de me contacter sur Discord (Laval_13#7304) avec l'erreur si dessous !");
			e.printStackTrace();
			Bukkit.shutdown();
		}
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static PrimsRP getInstance(){ return Instance; }
	public static void setInstance(PrimsRP Class){ Instance = Class; }
	public Map<Player, Character> getCreatingCharacter(){ return CreatingCharacter; }
	public Map<Player, Character> getAccount(){ return Account; }
	public Map<Player, Boolean> getDarknetAcces(){ return DarknetAcces; }
	public Map<Player, Player> getDarknetRequestLocation(){ return DarknetRequestLocation; }
	public List<Player> getDarknetSenderPub(){ return DarknetSenderPub; }
    public GuiManager getGuiManager() { return GuiManager; }
    public Settings getConfiguration() { return config; }
    public Settings getLanguage() { return lang; }
	public Settings getMenus(){ return menus; }
	
}
