package fr.Laval_13.PrimsRP.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemBuilder {

	private ItemStack is;

	public ItemBuilder(Material m){
		this(m, 1);
	}

	public ItemBuilder(ItemStack is){
		this.is = is;
	}

	public ItemBuilder(Material m, int amount){
		is = new ItemStack(m, amount);
	}

	public ItemBuilder(Material m, int amount, short meta){
		is = new ItemStack(m, amount, meta);
	}

	@Override
	public ItemBuilder clone(){
		return new ItemBuilder(is);
	}

	public ItemBuilder setDurability(short dur){
		is.setDurability(dur);
		return this;
	}

	public ItemBuilder setName(String name){
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level){
		is.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemBuilder removeEnchantment(Enchantment ench){
		is.removeEnchantment(ench);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment ench, int level){
		ItemMeta im = is.getItemMeta();
		im.addEnchant(ench, level, true);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder setInfinityDurability(){
		is.setDurability(Short.MAX_VALUE);
		return this;
	}

	public ItemBuilder setLore(String... lore){
		ItemMeta im = is.getItemMeta();
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder setLore(String lore){
		ItemMeta im = is.getItemMeta();
		im.setLore(lore.isEmpty() ? new ArrayList<String>() : Arrays.asList(lore.split("\\n")));
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder setLore(List<String> lore){
		ItemMeta im = is.getItemMeta();
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setWoolColor(DyeColor color){
		if(!is.getType().equals(Material.WOOL)){
			return this;
		}
		is.setDurability(color.getWoolData());
		return this;
	}

	public ItemBuilder setLeatherArmorColor(Color color){
		try{
			LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
			im.setColor(color);
			is.setItemMeta(im);
		}catch(ClassCastException expected){}
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag flag){
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(flag);
		is.setItemMeta(im);
		return this;

	}

	public ItemBuilder addCustomEffect(PotionEffectType potion){
		if(!is.getType().equals(Material.POTION)){
			return this;
		}else{
			PotionMeta im = (PotionMeta) is.getItemMeta();
			im.addCustomEffect(new PotionEffect(potion, 0, 0), false);
			is.setItemMeta(im);
			return this;
		}
	}

	public ItemBuilder addCustomEffectSplash(PotionEffectType potion){
		if(!is.getType().equals(Material.SPLASH_POTION)){
			return this;
		}else{
			PotionMeta im = (PotionMeta) is.getItemMeta();
			im.addCustomEffect(new PotionEffect(potion, 0, 0), false);
			is.setItemMeta(im);
			return this;
		}
	}

	public ItemBuilder setSkullOwner(String owner){
		try{

			String[] playerTexture;

			try{
				Object entityPlayer = Bukkit.getPlayer(owner).getClass().getMethod("getHandle").invoke(Bukkit.getPlayer(owner));
				Method getProfileMethod = entityPlayer.getClass().getMethod("getProfile", new Class<?>[0]);
				GameProfile gameProfile = (GameProfile) getProfileMethod.invoke(entityPlayer);
				Property property = gameProfile.getProperties().get("textures").iterator().next();
				playerTexture = new String[] { property.getSignature(), property.getValue() };
			}catch(Exception e){
				playerTexture = new String[] { "K9P4tCIENYbNpDuEuuY0shs1x7iIvwXi4jUUVsATJfwsAIZGS+9OZ5T2HB0tWBoxRvZNi73Vr+syRdvTLUWPusVXIg+2fhXmQoaNEtnQvQVGQpjdQP0TkZtYG8PbvRxE6Z75ddq+DVx/65OSNHLWIB/D+Rg4vINh4ukXNYttn9QvauDHh1aW7/IkIb1Bc0tLcQyqxZQ3mdglxJfgIerqnlA++Lt7TxaLdag4y1NhdZyd3OhklF5B0+B9zw/qP8QCzsZU7VzJIcds1+wDWKiMUO7+60OSrIwgE9FPamxOQDFoDvz5BOULQEeNx7iFMB+eBYsapCXpZx0zf1bduppBUbbVC9wVhto/J4tc0iNyUq06/esHUUB5MHzdJ0Y6IZJAD/xIw15OLCUH2ntvs8V9/cy5/n8u3JqPUM2zhUGeQ2p9FubUGk4Q928L56l3omRpKV+5QYTrvF+AxFkuj2hcfGQG3VE2iYZO6omXe7nRPpbJlHkMKhE8Xvd1HP4PKpgivSkHBoZ92QEUAmRzZydJkp8CNomQrZJf+MtPiNsl/Q5RQM+8CQThg3+4uWptUfP5dDFWOgTnMdA0nIODyrjpp+bvIJnsohraIKJ7ZDnj4tIp4ObTNKDFC/8j8JHz4VCrtr45mbnzvB2DcK8EIB3JYT7ElJTHnc5BKMyLy5SKzuw=", "eyJ0aW1lc3RhbXAiOjE1MjkyNTg0MTE4NDksInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxYzc3Y2U4ZTU0OTI1YWI1ODEyNTQ0NmVjNTNiMGNkZDNkMGNhM2RiMjczZWI5MDhkNTQ4Mjc4N2VmNDAxNiJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc2N2Q0ODMyNWVhNTMyNDU2MTQwNmI4YzgyYWJiZDRlMjc1NWYxMTE1M2NkODVhYjA1NDVjYzIifX19" };
			}

			if(playerTexture[0] == null || playerTexture[0].isEmpty() || playerTexture[1] == null || playerTexture[1].isEmpty()){
				playerTexture[0] = "K9P4tCIENYbNpDuEuuY0shs1x7iIvwXi4jUUVsATJfwsAIZGS+9OZ5T2HB0tWBoxRvZNi73Vr+syRdvTLUWPusVXIg+2fhXmQoaNEtnQvQVGQpjdQP0TkZtYG8PbvRxE6Z75ddq+DVx/65OSNHLWIB/D+Rg4vINh4ukXNYttn9QvauDHh1aW7/IkIb1Bc0tLcQyqxZQ3mdglxJfgIerqnlA++Lt7TxaLdag4y1NhdZyd3OhklF5B0+B9zw/qP8QCzsZU7VzJIcds1+wDWKiMUO7+60OSrIwgE9FPamxOQDFoDvz5BOULQEeNx7iFMB+eBYsapCXpZx0zf1bduppBUbbVC9wVhto/J4tc0iNyUq06/esHUUB5MHzdJ0Y6IZJAD/xIw15OLCUH2ntvs8V9/cy5/n8u3JqPUM2zhUGeQ2p9FubUGk4Q928L56l3omRpKV+5QYTrvF+AxFkuj2hcfGQG3VE2iYZO6omXe7nRPpbJlHkMKhE8Xvd1HP4PKpgivSkHBoZ92QEUAmRzZydJkp8CNomQrZJf+MtPiNsl/Q5RQM+8CQThg3+4uWptUfP5dDFWOgTnMdA0nIODyrjpp+bvIJnsohraIKJ7ZDnj4tIp4ObTNKDFC/8j8JHz4VCrtr45mbnzvB2DcK8EIB3JYT7ElJTHnc5BKMyLy5SKzuw=";
				playerTexture[1] = "eyJ0aW1lc3RhbXAiOjE1MjkyNTg0MTE4NDksInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxYzc3Y2U4ZTU0OTI1YWI1ODEyNTQ0NmVjNTNiMGNkZDNkMGNhM2RiMjczZWI5MDhkNTQ4Mjc4N2VmNDAxNiJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc2N2Q0ODMyNWVhNTMyNDU2MTQwNmI4YzgyYWJiZDRlMjc1NWYxMTE1M2NkODVhYjA1NDVjYzIifX19";
			}

			SkullMeta im = (SkullMeta) is.getItemMeta();

			GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
			gameProfile.getProperties().put("textures", new Property("textures", playerTexture[1], playerTexture[0]));

			Field profileField = null;

			try{
				profileField = im.getClass().getDeclaredField("profile");
				profileField.setAccessible(true);
				profileField.set(im, gameProfile);
			}catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1){
				e1.printStackTrace();
			}

			is.setItemMeta(im);
		}catch(ClassCastException expected){}
		return this;
	}

	public ItemStack toItemStack(){
		return is;
	}

}