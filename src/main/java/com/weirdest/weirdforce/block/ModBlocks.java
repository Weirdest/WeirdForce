package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.WeirdForceTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class ModBlocks {
	
	public static Block energyChannel;
	
	public static final void init(){
		energyChannel = new BasicBlock("powerChannel", Material.iron)
				//Called onto energyChannel
				.setCreativeTab(WeirdForceTabs.tabWeirdForce);
	}
}
