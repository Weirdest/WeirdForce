package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.WeirdForceTabs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class ModBlocks {
	
	public static Block energyChannel;
	
	public static final void init(){
		energyChannel = new BasicBlock("energyChannel", Material.iron, "pickaxe", 3)
				//Called onto energyChannel
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(2000F)
				.setHardness(35F);
		
		GameRegistry.registerBlock(energyChannel, "energyChannel");
		
	}
}
