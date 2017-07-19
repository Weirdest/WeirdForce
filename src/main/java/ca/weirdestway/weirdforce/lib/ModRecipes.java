package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.block.*;
import ca.weirdestway.weirdforce.item.ModItems;
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
	GameRegistry.addRecipe(new ItemStack(ModItems.powerReceiver), 
			"SGS", "IRI", "SGS", //Crafting Shape
			'S', Blocks.stone, //Crafting Piece
			'I', Items.iron_ingot, //Crafting Piece
			'G', Items.gold_ingot, //Crafting Piece
			'R', Items.redstone); //Crafting Piece
	
	
	GameRegistry.addRecipe(new ItemStack(ModItems.focalLens),
			"SSS", "SFS", "SSS",
			'S', Blocks.stone,
			'F', ModItems.forciumShard);
	
	//Block Recipes
//	GameRegistry.addRecipe(new ItemStack(ModBlocks.energyChannel),
//			"SFS", "FPF", "SFS",
//			'S', Blocks.stone,
//			'F', ModItems.forciumIngot,
//			'P', ModItems.powerReceiver);
	
	GameRegistry.addRecipe(new ItemStack(WeirdBlocks.forcium),
			"###", "###", "###",
			'#', ModItems.forciumIngot);
	
	GameRegistry.addRecipe(new ItemStack(WeirdBlocks.projector),
			"SPS", "SLS", "SPS",
			'S', Blocks.stone,
			'P', ModItems.powerReceiver,
			'L', ModItems.focalLens);
	
	//Smelting Recipes
	GameRegistry.addSmelting(WeirdBlocks.forciumOre, new ItemStack(ModItems.forciumIngot), 0.7F);
	}
}
