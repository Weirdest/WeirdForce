package com.weirdest.weirdforce.item;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public final class ModItems {
	
	public static Item powerReceiver;
	public static Item forciumIngot;
	public static Item focalLens;
	public static Item forciumShard;
	
	public static final void init() {
		powerReceiver = new Item()
				//These are called onto powerReceiver
				.setUnlocalizedName("powerReceiver")
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				//Would be weirdForce:powerReceiver
				.setTextureName(Main.MODID + ":powerReceiver");
		
		//Register the item in the game
		GameRegistry.registerItem(powerReceiver, "powerReceiver");
		
		
		forciumIngot = new Item()
				.setUnlocalizedName("forciumIngot")
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setTextureName(Main.MODID + ":forciumIngot");
		GameRegistry.registerItem(forciumIngot, "forciumIngot");
		
		focalLens = new Item()
				.setUnlocalizedName("focalLens")
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setTextureName(Main.MODID + ":focalLens");
		GameRegistry.registerItem(focalLens, "focalLens");
		
		forciumShard = new Item()
				.setUnlocalizedName("forciumShard")
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setTextureName(Main.MODID + ":forciumShard");
		GameRegistry.registerItem(forciumShard, "forciumShard");
	}
}
