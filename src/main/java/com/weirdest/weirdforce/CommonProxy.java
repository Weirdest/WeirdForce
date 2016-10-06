package com.weirdest.weirdforce;

import com.weirdest.weirdforce.block.ModBlocks;
import com.weirdest.weirdforce.item.ModItems;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	//Create and Register
	public void preInit(FMLPreInitializationEvent e) {
		
		//Both the client & server need to have the item(s) & block(s)
		ModItems.inti();
		ModBlocks.init();
	}
	//Crafting and data
	public void init(FMLInitializationEvent e) {
		
	}
	//Anything else
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
