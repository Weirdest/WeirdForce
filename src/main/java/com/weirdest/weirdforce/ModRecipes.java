package com.weirdest.weirdforce;

import com.weirdest.weirdforce.item.ModItems;
import com.weirdest.weirdforce.block.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipes {
	//Make it craftable
	public static void init() {
		
		//Item recipes
	GameRegistry.addRecipe(new ItemStack(ModItems.powerReceiver), 
			"SIS", "GRG", "SIS", //Crafting Shape
			'S', Blocks.stone, //Crafting Piece
			'I', Items.iron_ingot, //Crafting Piece
			'G', Items.gold_ingot, //Crafting Piece
			'R', Items.redstone); //Crafting Piece
	
	//Block Recipes
	GameRegistry.addRecipe(new ItemStack(ModBlocks.energyChanel),
			"SFS", "FPF", "SFS",
			'S', Blocks.stone,
			'F', ModItems.forciumIngot,
			'P', ModItems.powerReceiver);
	
	//Smelting Recipes
	GameRegistry.addSmelting(ModBlocks.forciumOre, new ItemStack(ModItems.forciumIngot), 0.7F);
	}
}
