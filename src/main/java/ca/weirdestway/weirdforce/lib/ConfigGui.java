package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.WeirdForce;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen screen) {
		super(screen, new ConfigElement(WeirdForce.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), 
				WeirdForce.MODID, true, false, GuiConfig.getAbridgedConfigPath(WeirdForce.config.toString()));
	}

}
