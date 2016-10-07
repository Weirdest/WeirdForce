package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.WeirdForceTabs;
import com.weirdest.weirdforce.item.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class ModBlocks {
	
	public static Block energyChanel;
	public static Block forciumOre;
	public static Block forcium; //Solid block
	public static Block projector;
	public static Block volcanizedForcium;
	
	public static final void init(){
		
		//Move to own class soon
		energyChanel = new BasicBlock("energyChanel", Material.iron, "pickaxe", 3)
				//Called onto energyChannel
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(2000F)//Blast
				.setHardness(35F);//Mining
		
		GameRegistry.registerBlock(energyChanel, "energyChanel");
		
		forciumOre = new BasicBlock("forciumOre", Material.rock, "pickaxe", 2)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(5F)
				.setHardness(10F);
		GameRegistry.registerBlock(forciumOre, "forciumOre");
		
		forcium = new BasicBlock("forcium", Material.iron, "pickaxe", 2)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(100F)
				.setHardness(20F);
		GameRegistry.registerBlock(forcium, "forcium");
		
		//All values are set in the constructor
		projector = new ModBlockProjector(Material.iron);
		GameRegistry.registerBlock(projector, "projector");
		
		volcanizedForcium = new ModBlockItemDrop("volcanizedForcium", Material.glass, "pickaxe", 3, ModItems.forciumShard, 1, 1)//Will always get one
				.setHardness(15F)
				.setResistance(500F)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce);
		GameRegistry.registerBlock(volcanizedForcium, "volcanizedForcium");
		
	}
}
