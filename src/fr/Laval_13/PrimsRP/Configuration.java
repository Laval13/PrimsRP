package fr.Laval_13.PrimsRP;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.Laval_13.PrimsRP.Utils.ItemBuilder;
import fr.Laval_13.PrimsRP.Utils.PrimsUtils;

public class Configuration {

	public enum Other {
		
		Salary("Salary"),
		NPCCuisinier("NPC.Cuisinier"),
		NPCBucheron("NPC.Bucheron"),
		NPCMineur("NPC.Mineur"),
		NPCFermier("NPC.Fermier"),
		NPCMédecin("NPC.Médecin"),
		NPCEboueur("NPC.Eboueur"),
		NPCCommissariat("NPC.Commissariat"),
		TimeToRankupMédecin("Time.Rankup.Médecin"),
		TimeToRankupCadet("Time.Rankup.Cadet"),
		TimeToRankupSergent("Time.Rankup.Sergent"),
		TimeToRankupSergent_Chef("Time.Rankup.Sergent_Chef"),
		TimeToRankupCommandant("Time.Rankup.Commandant"),
		TimeToRankupFBI("Time.Rankup.FBI"),
		DelayDefault("Time.Delay.Default"),
		DelayMineur("Time.Delay.Mineur"),
		BypassBreakBlockRegen("Permission.BypassBreakBlockRegen"),
		DarknetPassword("DarknetPassword");

		private String path;
		
		private Other(String path){
			this.path = path;
		}
		
		public int getSalary(){
			return PrimsRP.getInstance().getConfiguration().getInt(path);
		}
		
		public int getNPCId(){
			return PrimsRP.getInstance().getConfiguration().getInt(path);
		}
		
		public int getTime(){
			return PrimsRP.getInstance().getConfiguration().getInt(path);
		}
		
		public int getTimeMineur(){
			String[] split = PrimsRP.getInstance().getConfiguration().get(path).split(";");
			return ThreadLocalRandom.current().nextInt(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
		}
		
		public String getPermission(){
			return PrimsRP.getInstance().getConfiguration().get(path);
		}
		
		public String getDarknetPassword(){
			return PrimsRP.getInstance().getConfiguration().get(path);
		}
		
	}
	
	public enum Items {
		
		Phone("Items.Phone"),
		Convocation("Items.Convocation");
		
		private String path;
		
		private Items(String path){
			this.path = path;
		}
		
		public ItemStack getItemStack(){
			
			Material type = Material.valueOf(PrimsRP.getInstance().getConfiguration().get(path + ".Type"));
			String name = PrimsRP.getInstance().getConfiguration().get(path + ".Name");
			String lore = PrimsRP.getInstance().getConfiguration().get(path + ".Lore");

			ItemStack item = new ItemBuilder(type, 1).setName(name).setLore(lore).toItemStack();
			return item;
		}
		
	}
	
	public enum Locations {
		
		FirstSpawn("Locations.FirstSpawn"),
		FinishCreatingCharacter("Locations.FinishCreatingCharacter");
		
		private String path;
		
		private Locations(String path){
			this.path = path;
		}
		
		public Location getLocation(){
			String path = PrimsRP.getInstance().getConfiguration().get(this.path);
			return PrimsUtils.getStringLocation(path);
		}
		
	}
	
	public enum Messages {
		
		Accepted("Messages.Accepted"),
		Refused("Messages.Refused"),
		ChooseFirstName("Messages.ChooseFirstName"),
		InvalidFirstName("Messages.InvalidFirstName"),
		ChooseName("Messages.ChooseName"),
		InvalidName("Messages.InvalidName"),
		ChooseSexe("Messages.ChooseSexe"),
		InvalidSexe("Messages.InvalidSexe"),
		ChooseAge("Messages.ChooseAge"),
		LimitAge("Messages.LimitAge"),
		InvalidAge("Messages.InvalidAge"),
		FinishCreatingCharacter("Messages.FinishCreatingCharacter"),
		HasAleardyThisJob("Messages.HasAleardyThisJob"),
		ChangeToNewJob("Messages.ChangeToNewJob"),
		IHaventGoodPlayedTime("Messages.IHaventGoodPlayedTime"),
		SMSHelp("Messages.SMS.Help"),
		SMSRecived("Messages.SMS.Recived"),
		SMSSent("Messages.SMS.Sent"),
		SMSTargetOffline("Messages.SMS.TargetOffline"),
		SMSPlayerHaventPhone("Messages.SMS.PlayerHaventPhone"),
		SMSTargetHaventPhone("Messages.SMS.TargetHaventPhone"),
		AppelMédecin("Messages.Appel.Medecin"),
		AppelCuisinier("Messages.Appel.Cuisinier"),
		AppelMaire("Messages.Appel.Maire"),
		AppelPoliciers("Messages.Appel.Policiers"),
		AppelWaitHereServiceGoingOn("Messages.Appel.WaitHereServiceGoingOn"),
		AppelRefused("Messages.Appel.Refused"),
		AppelAccepted("Messages.Appel.Accepted"),
		AppelMaireSentConvocation("Messages.Appel.Maire.SentConvocation"),
		AppelMaireReceviedConvocation("Messages.Appel.Maire.ReceviedConvocation"),
		AppelMaireRemovedConvocation("Messages.Appel.Maire.RemovedConvocation"),
		DarknetWhatThePassword("Messages.Darknet.WhatThePassword"),
		DarknetWrongPassword("Messages.Darknet.WrongPassword"),
		DarknetGoodPassword("Messages.Darknet.GoodPassword"),
		DarknetWhatYourPub("Messages.Darknet.WhatYourPub"),
		DarknetPubChatFormat("Messages.Darknet.PubChatFormat"),
		DarknetDiscussionInconnu("Messages.Darknet.Discussion.Inconnu"),
		DarknetDiscussionVendeur("Messages.Darknet.Discussion.Vendeur"),
		DarknetDiscussionAcheteur("Messages.Darknet.Discussion.Acheteur"),
		DarknetRequestTarget("Messages.Darknet.Request.Target"),
		DarknetRequestInvalidChoose("Messages.Darknet.Request.InvalidChoose"),
		DarknetRequestAccepted("Messages.Darknet.Request.Accepted"),
		DarknetRequestRefuseed("Messages.Darknet.Request.Refused"),
		DarknetRequestSenderAccepted("Messages.Darknet.Request.SenderAccepted"),
		DarknetRequestSenderRefused("Messages.Darknet.Request.SenderRefused"),
		DarknetRequestActionBarHack("Messages.Darknet.Request.ActionBarHack");
		
		private String path;
		
		private Messages(String path){
			this.path = path;
		}
		
		public String getMessage(){
			return PrimsRP.getInstance().getLanguage().get(path);
		}
		
		public List<String> getDiscussion(){
			return PrimsRP.getInstance().getLanguage().getList(path);
		}
		
	}
	
}
