package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.Main;
import cpw.mods.fml.common.FMLCommonHandler;

public class ConfigHandler {
	
	private static final String _PREFIX = Main.config.CATEGORY_GENERAL + Main.config.CATEGORY_SPLITTER;
	
	//Options
	public static boolean overwriteBlock;
	public static final boolean overwriteBlock_default = false;
	public static final String overwriteBlock_desc = "Allow Field Projector to overwrite blocks instead of telling you about them";
	
	public static void sync() {
		FMLCommonHandler.instance().bus().register(Main.instance);
		
		//Create Category
		final String behavior = _PREFIX + "Behavior";
		Main.config.addCustomCategoryComment(behavior, "Change the behavior of some things in the mod");
		
		//Add to ^^^ Category
		overwriteBlock = Main.config.getBoolean("Block Overwrite", behavior, overwriteBlock_default, overwriteBlock_desc);
		
		
		if(Main.config.hasChanged()) {
			Main.config.save();
		}
		
	}
}
