package com.weirdest.weirdforce;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {
	//Do i really need to explain this?
    public static final String MODID = "weirdforce";
    public static final String MODNAME = "The Weird Force Mod";
    public static final String VERSION = "1.0.2";
    
    //Make it now so forge won't :)
    @Instance
    public static Main instance = new Main();
    
    @SidedProxy(clientSide="com.weirdest.weirdforce.ClientProxy", serverSide="com.weirdest.weirdforce.ServerProxy")
    public static CommonProxy proxy;
        
    //Inits for both client & server
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	System.out.println("Started Pre-Init");
    	proxy.preInit(e);
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	System.out.println("Started Full Init");
    	proxy.init(e);
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	System.out.println("Started Post-Init");
    	proxy.postInit(e);
    }
}
