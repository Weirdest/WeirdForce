package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.WeirdForce;
import cpw.mods.fml.common.FMLCommonHandler;

public class ConfigHandler {
	
	private static final String _PREFIX = WeirdForce.config.CATEGORY_GENERAL + WeirdForce.config.CATEGORY_SPLITTER;
	
	//Options
	public static boolean overwriteBlock;
	public static final boolean overwriteBlock_default = false;
	public static final String overwriteBlock_desc = "Allow Field Projector to overwrite blocks instead of telling you about them";
	
	public static boolean msgObstruction;
	public static final boolean msgObstruction_default = true;
	public static final String msgObstruction_desc = "Message nearest player about obstruction";
	
	public static boolean fieldInstaKill;
	public static final boolean fieldInstaKill_default = false;
	public static final String fieldInstaKill_desc = "Enable InstaKill on force fields";
	
	public static void sync() {
		FMLCommonHandler.instance().bus().register(WeirdForce.instance);
		
		//Create Category
		final String behavior = _PREFIX + "Behavior";
		WeirdForce.config.addCustomCategoryComment(behavior, "Change the behavior of some things in the mod");
		
		//Add to ^^^ Category
		overwriteBlock = WeirdForce.config.getBoolean("Block Overwrite", behavior, overwriteBlock_default, overwriteBlock_desc);
		msgObstruction = WeirdForce.config.getBoolean("Message Obstruction", behavior, msgObstruction_default, msgObstruction_desc);
		fieldInstaKill = WeirdForce.config.getBoolean("InstaKill", behavior, fieldInstaKill_default, fieldInstaKill_desc);
		
		
		if(WeirdForce.config.hasChanged()) {
			WeirdForce.config.save();
		}
		
	}
}
