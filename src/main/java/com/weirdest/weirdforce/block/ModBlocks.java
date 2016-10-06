package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.WeirdForceTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class ModBlocks {
	
	public static Block energyChanel;
	public static Block forciumOre;
	
	public static final void init(){
		energyChanel = new BasicBlock("energyChanel", Material.iron, "pickaxe", 3)
				//Called onto energyChannel
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(2000F)
				.setHardness(35F);
		
		GameRegistry.registerBlock(energyChanel, "energyChanel");
		
		forciumOre = new BasicBlock("forciumOre", Material.rock, "pickaxe", 2)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(5F)
				.setHardness(10F);
		GameRegistry.registerBlock(forciumOre, "forciumOre");
	}
}
