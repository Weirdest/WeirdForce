package ca.weirdestway.weirdforce.block;

import ca.weirdestway.weirdforce.WeirdForce;
import ca.weirdestway.weirdforce.lib.ConfigHandler;
import ca.weirdestway.weirdforce.lib.WeirdForceTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;


public class WeirdProjector extends Block {

	public IIcon[] icons = new IIcon[6];
	public int[] coords = new int[3];
	public String lastObstructionCoords = "init";

	protected WeirdProjector(Material material) {
		super(material);
		this.setBlockName("projector");
		this.setCreativeTab(WeirdForceTabs.tabWeirdForce);
		this.setResistance(500000F);
		this.setHardness(12F);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {

		//Top & Bottom
		this.icons[0] = reg.registerIcon(WeirdForce.MODID + ":" + "energyChannel");
		this.icons[1] = reg.registerIcon(WeirdForce.MODID + ":" + "energyChannel");

		//All other sides
		for(int x = 2; x < 6; x++) {
			this.icons[x] = reg.registerIcon(WeirdForce.MODID + ":" + "projectorSide");
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

	//This is the one called by the game
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {

		//Call my own
		onNeighborBlockChange(world, x, y, z, false);
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, boolean override) {
		if(!world.isRemote) {

			//Something changed, so I will check to see if I am powered
			if(world.isBlockIndirectlyGettingPowered(x, y, z) && !override) {
				//So I am powered now lets see if I already have a field up

				//check north first
				if(world.getBlock(x,  y, (z - 1)) != WeirdBlocks.fieldBlock) {

					//There is no field block but I am powered so check to see if there is another powered projector
					//within the maximum connection range from config
					for(int n = 1; n < ConfigHandler.maxConnect; n++) {

						//If I find a projector block check to see if it's powered
						if(world.getBlock(x, y, (z - n)) == WeirdBlocks.projector) {

							//Found a projector now is it powered?
							if(world.isBlockIndirectlyGettingPowered(x, y, (z - n))) {

								//I know I am powered and so is this guy, so connect
								connectField(world, x, y, z, n, ForgeDirection.NORTH);
							}

							//I found a projector so stop looking in this direction
							break;
						}
					}

				}

				//check east next
				if(world.getBlock((x + 1),  y, z) != WeirdBlocks.fieldBlock) {

					//There is no field block but I am powered so check to see if there is another powered projector
					//within the maximum connection range from config
					for(int n = 1; n < ConfigHandler.maxConnect; n++) {

						//If I find a projector block check to see if it's powered
						if(world.getBlock((x + n),  y, z) == WeirdBlocks.projector) {

							//Found a projector now is it powered?
							if(world.isBlockIndirectlyGettingPowered((x + n),  y, z)) {

								//I know I am powered and so is this guy, so connect
								connectField(world, x, y, z, n, ForgeDirection.EAST);
							}

							//I found a projector so stop looking in this direction
							break;
						}
					}

				}

				//check south next
				if(world.getBlock(x,  y, (z + 1)) != WeirdBlocks.fieldBlock) {

					//There is no field block but I am powered so check to see if there is another powered projector
					//within the maximum connection range from config
					for(int n = 1; n < ConfigHandler.maxConnect; n++) {

						//If I find a projector block check to see if it's powered
						if(world.getBlock(x, y, (z + n)) == WeirdBlocks.projector) {

							//Found a projector now is it powered?
							if(world.isBlockIndirectlyGettingPowered(x, y, (z + n))) {

								//I know I am powered and so is this guy, so connect
								connectField(world, x, y, z, n, ForgeDirection.SOUTH);
							}

							//I found a projector so stop looking in this direction
							break;
						}
					}

				}

				//check west last
				if(world.getBlock((x - 1),  y, z) != WeirdBlocks.fieldBlock) {

					//There is no field block but I am powered so check to see if there is another powered projector
					//within the maximum connection range from config
					for(int n = 1; n < ConfigHandler.maxConnect; n++) {

						//If I find a projector block check to see if it's powered
						if(world.getBlock((x - n),  y, z) == WeirdBlocks.projector) {

							//Found a projector now is it powered?
							if(world.isBlockIndirectlyGettingPowered((x - n),  y, z)) {

								//I know I am powered and so is this guy, so connect
								connectField(world, x, y, z, n, ForgeDirection.WEST);
							}

							//I found a projector so stop looking in this direction
							break;
						}
					}

				}

			} else {
				//I am not powered so check for fields

				//Check North first
				if(world.getBlock(x, y, (z - 1)) == WeirdBlocks.fieldBlock) {

					//So I am not powered and there is a field block!!!!!
					//I will now disconnect in this direction

					disconnectField(world, x, y, z, ForgeDirection.NORTH);
				} 

				//Check east next
				if (world.getBlock((x + 1), y, z) == WeirdBlocks.fieldBlock) {
					disconnectField(world, x, y, z, ForgeDirection.EAST);
				}

				//Check south next
				if(world.getBlock(x, y, (z + 1)) == WeirdBlocks.fieldBlock) {
					disconnectField(world, x, y, z, ForgeDirection.SOUTH);
				}

				//Check west last
				if (world.getBlock((x - 1), y, z) == WeirdBlocks.fieldBlock) {
					disconnectField(world, x, y, z, ForgeDirection.WEST);
				}

			}
		}
	}

	public void connectField(World world, int x, int y, int z, int blocksAway, ForgeDirection direction) {
		//Lets connect!

		//Check if there is any obstructions in the given direction
		if(directionIsValid(world, x, y, z, blocksAway, direction)) {

			//No obstructions were found so start changing blocks
			switch(direction) {
			case NORTH:

				for(int i = 1; i < blocksAway; i++) {
					setBlockPatch(x, y, (z - i), WeirdBlocks.fieldBlock, world);
				}

				break;
			case EAST:

				for(int i = 1; i < blocksAway; i++) {
					setBlockPatch((x + i), y, z, WeirdBlocks.fieldBlock, world);
				}

				break;
			case SOUTH:

				for(int i = 1; i < blocksAway; i++) {
					setBlockPatch(x, y, (z + i), WeirdBlocks.fieldBlock, world);
				}

				break;
			case WEST:

				for(int i = 1; i < blocksAway; i++) {
					setBlockPatch((x - i), y, z, WeirdBlocks.fieldBlock, world);
				}

				break;
			default:
				//Literally only to make eclipse feel better about its life
				break;
			}
		}
	}

	public void disconnectField(World world, int x, int y, int z, ForgeDirection direction) {
		switch(direction) {
		case NORTH:
			for(int n = 1; n < ConfigHandler.maxConnect; n++) {
				//First check for a projector block, if so stop setting to air because all field blocks are gone
				if(world.getBlock(x, y, (z - n)) == WeirdBlocks.projector) { break; }

				//Its not a projector so it must be a field block!
				setBlockPatch(x, y, (z - n), Blocks.air, world);
			}
			break;
		case EAST:
			for(int n = 1; n < ConfigHandler.maxConnect; n++) {
				//First check for a projector block, if so stop setting to air because all field blocks are gone
				if(world.getBlock((x + n), y, z) == WeirdBlocks.projector) { break; }

				//Its not a projector so it must be a field block!
				setBlockPatch((x + n), y, z, Blocks.air, world);
			}
			break;
		case SOUTH:
			for(int n = 1; n < ConfigHandler.maxConnect; n++) {
				//First check for a projector block, if so stop setting to air because all field blocks are gone
				if(world.getBlock(x, y, (z + n)) == WeirdBlocks.projector) { break; }

				//Its not a projector so it must be a field block!
				setBlockPatch(x, y, (z + n), Blocks.air, world);
			}
			break;
		case WEST:
			for(int n = 1; n < ConfigHandler.maxConnect; n++) {
				//First check for a projector block, if so stop setting to air because all field blocks are gone
				if(world.getBlock((x - n), y, z) == WeirdBlocks.projector) { break; }

				//Its not a projector so it must be a field block!
				setBlockPatch((x - n), y, z, Blocks.air, world);
			}
			break;
			
		default:
			//Literally only to make eclipse feel better about its life
			break;
		}
	}

	//This block doesn't care how its destroyed (yet...)
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion boom) { onBlockDestroyedByPlayer(world, x, y, z, 0); }

	//Deletes all field blocks it is connected to
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		onNeighborBlockChange(world, x, y, z, true);
	}

	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {

		onNeighborBlockChange(world, x, y, z, false);

		return 0;
	}

	public boolean isValidBlock(World world, int x, int y, int z) {

		//If we are able to overwrite blocks as per config then go for it!
		if(!ConfigHandler.overwriteBlock) { 
			if(world.getBlock(x, y, z).isAir(world, x, y, z) || isPlant(world.getBlock(x, y, z)) || world.getBlock(x, y, z) == WeirdBlocks.fieldBlock || world.getBlock(x, y, z) == Blocks.snow_layer) {
				//If its air, foliage, fieldBlock, or projector then its valid for shield method
				return true;
			}

			//Its not replaceable
			msgNearPlayerObstruction(world, x, y, z);
			return false;

		} else {

			//Every block is valid!
			return true;
		}
	}

	public boolean isPlant(Block block) {
		if (block == Blocks.farmland || block == Blocks.tallgrass || block == Blocks.red_flower || block == Blocks.yellow_flower)
			return true;

		//Default
		return false;
	}

	public void msgNearPlayerObstruction(World world, int x, int y, int z) {
		if(ConfigHandler.msgObstruction) { //If enabled
			if(!this.lastObstructionCoords.equals(coordId(x, y, z)) || this.lastObstructionCoords.equals("init"))
				world.getClosestPlayer((double)x, (double)y, (double)z, 25).addChatMessage(new ChatComponentText("Please remove obstruction @ " + x + ", " + y + ", " + z));

			//Generates a unique string based on the last used coords
			this.lastObstructionCoords = coordId(x, y, z);
		}
	}

	public String coordId(int x, int y, int z) {
		return Integer.toString(x) + Integer.toString(y) + Integer.toString(z);
	}

	public boolean directionIsValid(World world, int x, int y, int z, int blocksAway, ForgeDirection direction) {
		switch(direction) {
		case NORTH:
			for(int h = 1; h < blocksAway; h++) {

				//If its a projector then there is no obstruction or I would have found it by now
				if(world.getBlock(x, y, z - h) == WeirdBlocks.projector) {break;}
				if(!isValidBlock(world, x, y, z - h)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x, y, z - h);
					return false;
				}
			}
			//No bad block was found so return true
			return true;
		case SOUTH:
			for(int h = 1; h < blocksAway; h++) {
				if(world.getBlock(x, y, z + h) == WeirdBlocks.projector) {break;}
				if(!isValidBlock(world, x, y, z + h)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x, y, z + h);
					return false;
				}
			}
			//No bad block was found so return true
			return true;
		case WEST:
			for(int h = 1; h < blocksAway; h++) {
				if(world.getBlock(x - h, y, z) == WeirdBlocks.projector) {break;}
				if(!isValidBlock(world, x - h, y, z)) {
					//exit and return false because invalid block has been found
					msgNearPlayerObstruction(world, x - h, y, z);
					return false;
				} 
			}
			//No bad block was found so return true
			return true;
		case EAST:
			for(int h = 1; h < blocksAway; h++) {
				if(world.getBlock(x + h, y, z) == WeirdBlocks.projector) {break;}
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