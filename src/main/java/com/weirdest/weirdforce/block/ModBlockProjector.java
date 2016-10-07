package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ModBlockProjector extends Block {
	
	public IIcon[] icons = new IIcon[6];	

	protected ModBlockProjector(Material material) {
		super(material);
		this.setBlockName("projector");
		this.setCreativeTab(WeirdForceTabs.tabWeirdForce);
		this.setResistance(5000F);
		this.setHardness(12F);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		
		//Top & Bottom
		this.icons[0] = reg.registerIcon(Main.MODID + ":" + "energyChanel");
		this.icons[1] = reg.registerIcon(Main.MODID + ":" + "energyChanel");
		
		//All other sides
		for(int x = 2; x < 6; x++) {
			this.icons[x] = reg.registerIcon(Main.MODID + ":" + "projectorSide");
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
	    return this.icons[side];
	}

}
