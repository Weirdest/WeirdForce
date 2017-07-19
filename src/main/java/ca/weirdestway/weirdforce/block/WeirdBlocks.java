package ca.weirdestway.weirdforce.block;

import ca.weirdestway.weirdforce.item.WeirdItems;
import ca.weirdestway.weirdforce.lib.WeirdForceTabs;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public final class WeirdBlocks {
	
//	public static Block energyChannel;
	public static Block forciumOre;
	public static Block forcium; //Solid block of forcium
	public static Block projector;
	public static Block volcanizedForcium;
	public static Block fieldBlock;
	
	public static final void init(){
		
		//Move to own class soon; Doesn't do anything yet
//		energyChannel = new BasicBlock("energyChannel", Material.iron, "pickaxe", 3)
//				//Called onto energyChannel
//				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
//				.setResistance(2000F)//Blast
//				.setHardness(35F);//Mining
//		
//		GameRegistry.registerBlock(energyChannel, "energyChannel");
		
		forciumOre = new BasicWeirdBlock("forciumOre", Material.rock, "pickaxe", 2)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(5F)
				.setHardness(10F);
		GameRegistry.registerBlock(forciumOre, "forciumOre");
		
		forcium = new BasicWeirdBlock("forcium", Material.iron, "pickaxe", 2)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce)
				.setResistance(100F)
				.setHardness(20F);
		GameRegistry.registerBlock(forcium, "forcium");
		
		volcanizedForcium = new WeirdItemDrop("volcanizedForcium", Material.glass, "pickaxe", 3, WeirdItems.forciumShard, 1, 1)//Will always get one
				.setHardness(15F)
				.setResistance(500F)
				.setCreativeTab(WeirdForceTabs.tabWeirdForce);
		GameRegistry.registerBlock(volcanizedForcium, "volcanizedForcium");
		
		//All values are set in the constructor
		projector = new WeirdProjector(Material.iron);
		GameRegistry.registerBlock(projector, "projector");
		
		//Again, all in the constructor
		fieldBlock = new WeirdField();
		GameRegistry.registerBlock(fieldBlock, "fieldBlock");
		
	}
}
