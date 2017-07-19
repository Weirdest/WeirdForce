package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.block.*;
import ca.weirdestway.weirdforce.item.WeirdItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WeirdRecipes {
	//Make it craftable
	public static void init() {
		
		//Item recipes
	GameRegistry.addRecipe(new ItemStack(WeirdItems.powerReceiver), 
			"SIS", "GRG", "SIS", //Crafting Shape
			'S', Blocks.stone, //Crafting Piece
			'I', Items.iron_ingot, //Crafting Piece
			'G', Items.gold_ingot, //Crafting Piece
			'R', Items.redstone); //Crafting Piece
	GameRegistry.addRecipe(new ItemStack(WeirdItems.powerReceiver), 
			"SGS", "IRI", "SGS", //Crafting Shape
			'S', Blocks.stone, //Crafting Piece
			'I', Items.iron_ingot, //Crafting Piece
			'G', Items.gold_ingot, //Crafting Piece
			'R', Items.redstone); //Crafting Piece
	
	
	GameRegistry.addRecipe(new ItemStack(WeirdItems.focalLens),
			"SSS", "SFS", "SSS",
			'S', Blocks.stone,
			'F', WeirdItems.forciumShard);
	
	//Block Recipes
//	GameRegistry.addRecipe(new ItemStack(ModBlocks.energyChannel),
//			"SFS", "FPF", "SFS",
//			'S', Blocks.stone,
//			'F', ModItems.forciumIngot,
//			'P', ModItems.powerReceiver);
	
	GameRegistry.addRecipe(new ItemStack(WeirdBlocks.forcium),
			"###", "###", "###",
			'#', WeirdItems.forciumIngot);
	
	GameRegistry.addRecipe(new ItemStack(WeirdBlocks.projector),
			"SPS", "SLS", "SPS",
			'S', Blocks.stone,
			'P', WeirdItems.powerReceiver,
			'L', WeirdItems.focalLens);
	
	//Smelting Recipes
	GameRegistry.addSmelting(WeirdBlocks.forciumOre, new ItemStack(WeirdItems.forciumIngot), 0.7F);
	}
}
