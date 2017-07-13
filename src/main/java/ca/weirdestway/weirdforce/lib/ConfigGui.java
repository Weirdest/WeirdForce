package ca.weirdestway.weirdforce.lib;

import ca.weirdestway.weirdforce.Main;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen screen) {
		super(screen, new ConfigElement(Main.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), 
				Main.MODID, true, false, GuiConfig.getAbridgedConfigPath(Main.config.toString()));
	}

}
