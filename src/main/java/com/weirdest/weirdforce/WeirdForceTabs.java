package com.weirdest.weirdforce;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.weirdest.weirdforce.item.ModItems; //Only for power receiver

public class WeirdForceTabs {
	//Create tab to put shit in; Set tab name as modid for no good reason other than VARIABLES! 
	public static final CreativeTabs tabWeirdForce = new CreativeTabs(Main.MODID) {
		@Override public Item getTabIconItem() {
			return ModItems.powerReceiver;
		}
	};
}
