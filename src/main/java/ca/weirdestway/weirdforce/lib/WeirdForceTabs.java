package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.WeirdForce;
import ca.weirdestway.weirdforce.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WeirdForceTabs {
	//Create tab to put shit in; Set tab name as modid for no good reason other than VARIABLES! 
	public static final CreativeTabs tabWeirdForce = new CreativeTabs(WeirdForce.MODID) {
		@Override public Item getTabIconItem() {
			return ModItems.powerReceiver;
		}
	};
}
