package ca.weirdestway.weirdforce;

import ca.weirdestway.weirdforce.block.ModBlocks;
import ca.weirdestway.weirdforce.item.ModItems;
import ca.weirdestway.weirdforce.lib.ModRecipes;
import ca.weirdestway.weirdforce.world.ModWorldGen;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	//Create and Register
	public void preInit(FMLPreInitializationEvent e) {
		
		//Both the client & server need to have the item(s) & block(s)
		ModItems.init();
		ModBlocks.init();
	}
	//Crafting and data
	public void init(FMLInitializationEvent e) {
		ModRecipes.init();
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);//Runs ASAP
	}
	//Anything else
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
}
