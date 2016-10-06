package com.weirdest.weirdforce.item;

import com.weirdest.weirdforce.Main;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import com.weirdest.weirdforce.WeirdForceTabs;

public final class ModItems {
	
	public static Item powerReceiver;
	
	public static final void inti() {
		powerReceiver = new Item()
				//These are called onto powerReceiver
				.setUnlocalizedName("powerReceiver")
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				//Would be weirdForce:powerReceiver
				.setTextureName(Main.MODID + ":powerReceiver");
		
		//Register the item in the game
		GameRegistry.registerItem(powerReceiver, "powerReceiver");
		
	}
}
