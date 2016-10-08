package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ModBlockField extends BlockPane {
	public String topTexture = Main.MODID + ":forceField";
	public String sideTexture = Main.MODID + ":forceField_top";
	public IIcon sideTextureIcon;


	public ModBlockField() {
		super(Main.MODID + ":forceField", Main.MODID + ":forceField_top", Material.glass, false);
		this.setCreativeTab(WeirdForceTabs.tabWeirdForce); // Only for debugging
		this.setBlockUnbreakable();
	}
	
	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return world.getBlock(x, y, z) == ModBlocks.projector || world.getBlock(x, y, z) == ModBlocks.fieldBlock ? true : false;
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity killYou) {
		killYou.attackEntityFrom(DamageSource.generic, 7.5F);//Do a lot of damage on contact
	}
}
