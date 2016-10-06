package com.weirdest.weirdforce.item;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
		GameRegistry.addRecipe(new ItemStack(powerReceiver), 
				"SIS", "GRG", "SIS", //Crafting Shape
				'S', Blocks.stone, //Crafting Piece
				'I', Items.iron_ingot, //Crafting Piece
				'G', Items.gold_ingot, //Crafting Piece
				'R', Items.redstone); //Crafting Piece
		
	}
}
