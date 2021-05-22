package fr.Laval_13.PrimsRP.Utils;

import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public final class ActionBarAPI {

	public static void sendMessage(Player player, String message){
		if(player == null || message == null){
			return;
		}

        BaseComponent[] componentArray = TextComponent.fromLegacyText(message);
        ChatMessageType actionBar = ChatMessageType.ACTION_BAR;
        
        Spigot spigot = player.spigot();
        spigot.sendMessage(actionBar, componentArray);

	}

}
