package ca.weirdestway.weirdforce.block;

import java.util.Random;

import ca.weirdestway.weirdforce.WeirdForce;
import ca.weirdestway.weirdforce.lib.ConfigHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WeirdField extends BlockPane {
	public String topTexture = WeirdForce.MODID + ":forceField";
	public String sideTexture = WeirdForce.MODID + ":forceField_top";
	//public IIcon sideTextureIcon;


	public double partSpawn = 0.02;
	public double red = -1.0D;
	public double green = 1.0D;
	public double blue = 1.0D;


	public WeirdField() {
		super(WeirdForce.MODID + ":forceField", WeirdForce.MODID + ":forceField_top", Material.rock, false);
		this.setBlockUnbreakable();
		this.setLightLevel(0.5F);
		this.setHardness(1000000F);//Ya nope
		this.setResistance(1000000F);//Still nope
	}

	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return world.getBlock(x, y, z) == WeirdBlocks.projector || world.getBlock(x, y, z) == WeirdBlocks.fieldBlock ? true : false;
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity killYou) {
		if(!ConfigHandler.fieldInstaKill) { //No insta kill
			killYou.attackEntityFrom(DamageSource.generic, 7.5F);//Do a lot of damage on contact
		} else { //Insta kill
			killYou.attackEntityFrom(DamageSource.generic, 10000F);
		}
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		double d0 = 0.0625D;

		for (int l = 0; l < 6; ++l)
		{
			double d1 = (double)((float)x + random.nextFloat());
			double d2 = (double)((float)y + random.nextFloat());
			double d3 = (double)((float)z + random.nextFloat());

			if (l == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube())
			{
				d2 = (double)(y + 1) + d0;
			}

			if (l == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube())
			{
				d2 = (double)(y + 0) - d0;
			}

			if (l == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube())
			{
				d3 = (double)(z + 1) + d0;
			}

			if (l == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube())
			{
				d3 = (double)(z + 0) - d0;
			}

			if (l == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube())
			{
				d1 = (double)(x + 1) + d0;
			}

			if (l == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube())
			{
				d1 = (double)(x + 0) - d0;
			}

			if (d1 < (double)x || d1 > (double)(x + 1) || d2 < 0.0D || d2 > (double)(y + 1) || d3 < (double)z || d3 > (double)(z + 1))
			{
				if(random.nextDouble() <= partSpawn) { //Only 1 in (1 / partSpawn) particles get generated

					//String particleName; double xPos; double yPos; double zPos; double red; double green; double blue;
					world.spawnParticle("reddust", d1, d2, d3, red, green, blue);
				}
			}
		}
	}
}
