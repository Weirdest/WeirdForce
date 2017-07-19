package ca.weirdestway.weirdforce.block;

import ca.weirdestway.weirdforce.WeirdForce;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicWeirdBlock extends Block {

	//Common init for all BASIC blocks
	protected BasicWeirdBlock(String unlocalizedName, Material material, String HarvestTool, int HarvestLevel) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(WeirdForce.MODID + ":" + unlocalizedName);
		//Moved this here because of issue with perms
		this.setHarvestLevel(HarvestTool, HarvestLevel);
	}
	
	protected BasicWeirdBlock(String unlocalizedName, Material material) { //No harvest 
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(WeirdForce.MODID + ":" + unlocalizedName);
	}

}
