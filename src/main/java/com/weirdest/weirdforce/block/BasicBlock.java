package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block {

	//Common init for all BASIC blocks
	protected BasicBlock(String unlocalizedName, Material material, String HarvestTool, int HarvestLevel) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
		//Moved this here because of issue with perms
		this.setHarvestLevel(HarvestTool, HarvestLevel);
	}
	
	protected BasicBlock(String unlocalizedName, Material material) { //No harvest 
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
	}

}
