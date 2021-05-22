package fr.Laval_13.PrimsRP.Utils.Gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {

	public abstract String setName();

	public abstract int setSize();

	public abstract void setContents(Player player, Inventory inv);

	public abstract void onClick(Player player, Inventory inv, ItemStack item, int slot);
}