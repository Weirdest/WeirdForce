package com.weirdest.weirdforce.block;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;


public class ModBlockProjector extends Block {

	public IIcon[] icons = new IIcon[6];
	public int[] coords = new int[3];
	public String lastObstructionCoords = "init";

	protected ModBlockProjector(Material material) {
		super(material);
		this.setBlockName("projector");
		this.setCreativeTab(WeirdForceTabs.tabWeirdForce);
		this.setResistance(500000F);
		this.setHardness(12F);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {

		//Top & Bottom
		this.icons[0] = reg.registerIcon(Main.MODID + ":" + "energyChannel");
		this.icons[1] = reg.registerIcon(Main.MODID + ":" + "energyChannel");

		//All other sides
		for(int x = 2; x < 6; x++) {
			this.icons[x] = reg.registerIcon(Main.MODID + ":" + "projectorSide");
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icons[side];
	}


	// Split off from original setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block p_147465_4_, int p_147465_5_, int p_147465_6_) method in order to directly send client and physic updates
	public void markAndNotifyBlockPatch(int x, int y, int z, Chunk chunk, Block oldBlock, Block newBlock, int flag, World world)
	{
		if ((flag & 2) != 0 && (chunk == null || chunk.func_150802_k()))
		{
			world.markBlockForUpdate(x, y, z);
		}

		if (!world.isRemote && (flag & 1) != 0)
		{

			//This was the little shit who was causing the fork bomb
			//this.myWorld.notifyBlockChange(x, y, z, oldBlock);

			if (newBlock.hasComparatorInputOverride())
			{
				world.func_147453_f(x, y, z, newBlock);
			}
		}

	}

	public boolean setBlockPatch(int x, int y, int z, Block blockRef, World world /*int mz, int p_147465_6_*/) //original defaults was 0 & 3
	{
		try {
			if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000)
			{
				if (y < 0)
				{
					return false;
				}
				else if (y >= 256)
				{
					return false;
				}
				else
				{
					Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
					Block block1 = null;
					net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;

					if ((3 & 1) != 0)
					{
						block1 = chunk.getBlock(x & 15, y, z & 15);
					}

					if (world.captureBlockSnapshots && !world.isRemote)
					{
						blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, x, y, z, 3);
						world.capturedBlockSnapshots.add(blockSnapshot);
					}

					boolean flag = chunk.func_150807_a(x & 15, y, z & 15, blockRef, 0);

					if (!flag && blockSnapshot != null)
					{
						world.capturedBlockSnapshots.remove(blockSnapshot);
						blockSnapshot = null;
					}

					world.theProfiler.startSection("checkLight");
					world.func_147451_t(x, y, z);
					world.theProfiler.endSection();

					if (flag && blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
					{
						// Modularize client and physic updates
						markAndNotifyBlockPatch(x, y, z, chunk, block1, blockRef, 3, world);
					}

					return flag;
				}
			}
			else
			{
				return false;
			}
		} catch (java.lang.NullPointerException e) {
			System.out.println("Caught Null pointer execption!");
		}
		return false;
	}


	//FUNCTIONALITY!!!!!!
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if(!world.isRemote) {

			//Why not put it in variables? Because this takes less memory!
			//world.getClosestPlayer(x, y, z - n, 25).addChatComponentMessage(new ChatComponentText("Please Remove Obstructions"));
			//break;

			//I can edit this world
			//Check if there is another projector 8 blocks in 1d direction
			//1d being forward, back, left, right
			for(int n = 1; n < 10; n++) {
				//Check towards north first
				if(world.getBlock(x, y, (z - n)) == ModBlocks.projector) {
					if(isDirectionVaild(world, x, y, z, ForgeDirection.NORTH)) {
						if(!world.isBlockIndirectlyGettingPowered(x, y, (z - n)) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
							//So i have another projector, and they are both turned off
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x, y, z - k) == ModBlocks.projector) {break;}
								setBlockPatch(x, y, (z - k), Blocks.air, world);
							}

						} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y, (z - n))) { //If i'm powered or they are
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x, y, z - k) == ModBlocks.projector) {break;}
								setBlockPatch(x, y, (z - k), ModBlocks.fieldBlock, world);
							}
						}
					}
				} 
			}


			for(int n = 1; n < 10; n++) {
				//Check South
				if(world.getBlock(x, y, (z + n)) == ModBlocks.projector) {
					if(isDirectionVaild(world, x, y, z, ForgeDirection.SOUTH)) {
						if(!world.isBlockIndirectlyGettingPowered(x, y, (z + n)) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
							//If projector is found then set all blocks before it as replaceWith
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x, y, z + k) == ModBlocks.projector) {break;}
								setBlockPatch(x, y, (z + k), Blocks.air, world);
							}

						} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y, (z + n))) { //If i'm powered or they are
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x, y, z + k) == ModBlocks.projector) {break;}
								setBlockPatch(x, y, (z + k), ModBlocks.fieldBlock, world);
							}
						}
					} 
				}

			} 

			for(int n = 1; n < 10; n++) {
				//Check west
				if(world.getBlock((x - n), y, z) == ModBlocks.projector) {
					if(isDirectionVaild(world, x, y, z, ForgeDirection.WEST)) {
						if(!world.isBlockIndirectlyGettingPowered((x - n), y, z) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
							//If projector is found then set all blocks before it as replaceWith
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x - k, y, z) == ModBlocks.projector) {break;}
								setBlockPatch((x - k), y, z, Blocks.air, world);
							}

						} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered((x - n), y, z)) { //If i'm powered or they are
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x - k, y, z) == ModBlocks.projector) {break;}
								setBlockPatch((x - k), y, z, ModBlocks.fieldBlock, world);
							}
						}
					} 
				}

			}

			for(int n = 1; n < 10; n++) {
				//Check East
				if(world.getBlock((x + n), y, z) == ModBlocks.projector) {
					if(isDirectionVaild(world, x, y, z, ForgeDirection.EAST)) {
						if(!world.isBlockIndirectlyGettingPowered((x + n), y, z) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
							//If projector is found then set all blocks before it as replaceWith
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x + k, y, z) == ModBlocks.projector) {break;}
								setBlockPatch((x + k), y, z, Blocks.air, world);
							}

						} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered((x + n), y, z)) { //If i'm powered or they are
							for(int k = 1; k < n; k++) {
								if(world.getBlock(x + k, y, z) == ModBlocks.projector) {break;}
								setBlockPatch((x + k), y, z, ModBlocks.fieldBlock, world);
							}
						}
					} 
				}

			}//For() Container 
		}
	}


	public boolean isValidBlock(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z).isAir(world, x, y, z) || isPlant(world.getBlock(x, y, z)) || world.getBlock(x, y, z) == ModBlocks.fieldBlock || world.getBlock(x, y, z) == Blocks.snow_layer) {
			//If its air, bush, fieldBlock, or projector then its valid for shield method
			return true;
		}

		//Its not replaceable
		msgNearPlayerObstruction(world, x, y, z);
		return false;
	}

	public boolean isPlant(Block block) {
		if (block == Blocks.farmland || block == Blocks.tallgrass || block == Blocks.red_flower || block == Blocks.yellow_flower)
			return true;

		//Default
		return false;
	}

	public void msgNearPlayerObstruction(World world, int x, int y, int z) {
		if(!this.lastObstructionCoords.equals(coordId(x, y, z)) || this.lastObstructionCoords.equals("init"))
			world.getClosestPlayer((double)x, (double)y, (double)z, 25).addChatMessage(new ChatComponentText("Please remove obstruction @ " + x + ", " + y + ", " + z));


		//Generates a unique string based on the last used coords
		this.lastObstructionCoords = coordId(x, y, z);
	}

	public String coordId(int x, int y, int z) {
		return Integer.toString(x) + Integer.toString(y) + Integer.toString(z);
	}

	public boolean isDirectionVaild(World world, int x, int y, int z, ForgeDirection direction) {
		switch(direction) {
		case NORTH:
			for(int h = 1; h < 10; h++) {
				if(world.getBlock(x, y, z - h) == ModBlocks.projector) {break;}
				if(!isValidBlock(world, x, y, z - h)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x, y, z - h);
					return false;
				}
			}
			//No bad block was found so return true
			return true;
		case SOUTH:
			for(int h = 1; h < 10; h++) {
				if(world.getBlock(x, y, z + h) == ModBlocks.projector) {break;}
				if(!isValidBlock(world, x, y, z + h)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x, y, z + h);
					return false;
				}
			}
			//No bad block was found so return true
			return true;
		case WEST:
			for(int h = 1; h < 10; h++) {
				if(world.getBlock(x - h, y, z) == ModBlocks.projector) {break;}
				if(!isValidBlock(world, x - h, y, z)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x - h, y, z);
					return false;
				} 
			}
			//No bad block was found so return true
			return true;
		case EAST:
			for(int h = 1; h < 10; h++) {
				if(world.getBlock(x + h, y, z) == ModBlocks.projector) {break;}
				if(!isValidBlock(world, x + h, y, z)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x + h, y, z);
					return false;
				}
			}
			//No bad block was found so return true
			return true;
		default:
			return false;

		}
	}


}