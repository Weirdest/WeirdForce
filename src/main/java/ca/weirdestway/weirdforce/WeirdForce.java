package ca.weirdestway.weirdforce;

import ca.weirdestway.weirdforce.lib.ConfigHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = WeirdForce.MODID, name = WeirdForce.MODNAME, version = WeirdForce.VERSION, guiFactory = "ca.weirdestway.weirdforce.lib.WeirdGuiFactory")
public class WeirdForce {
	//Do i really need to explain this?
    public static final String MODID = "weirdforce";
    public static final String MODNAME = "The Weird Force Mod";
    public static final String VERSION = "1.1.2";
    
    //Create config object
    public static Configuration config;
    
    //Create Instance of Main Class
    @Instance
    public static WeirdForce instance = new WeirdForce();
    
    //Declare Proxies
    @SidedProxy(clientSide="ca.weirdestway.weirdforce.ClientProxy", serverSide="ca.weirdestway.weirdforce.ServerProxy")
    public static CommonProxy proxy;
    
    //Update config only when my config changes
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    	if(event.modID.equals(WeirdForce.MODID)) {
    		ConfigHandler.sync();
    	}
    }
    
    //Inits for both client & server. Is for config and game reg
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	
    	System.out.println("Started Pre-Init");
    	proxy.preInit(e);
    	
    	System.out.println("Loading Config");
    	config = new Configuration(e.getSuggestedConfigurationFile());
    	ConfigHandler.sync();
    }
    
    //For crafting and data
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	System.out.println("Started Full Init");
    	proxy.init(e);
    }
    
    //Other mods compat
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	System.out.println("Started Post-Init");
    	proxy.postInit(e);
    }
}
