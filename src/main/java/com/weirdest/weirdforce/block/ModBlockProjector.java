package com.weirdest.weirdforce.block;

import java.util.concurrent.Callable;

import com.weirdest.weirdforce.Main;
import com.weirdest.weirdforce.WeirdForceTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ModBlockProjector extends Block {
	
	public IIcon[] icons = new IIcon[6];
	public int[] coords = new int[3];
	public World myWorld;

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
	public void markAndNotifyBlockPatch(int x, int y, int z, Chunk chunk, Block oldBlock, Block newBlock, int flag)
    {
        if ((flag & 2) != 0 && (chunk == null || chunk.func_150802_k()))
        {
            this.myWorld.markBlockForUpdate(x, y, z);
        }
        
        if (!this.myWorld.isRemote && (flag & 1) != 0)
        {
        	
        	//This was the little shit who was causing the fork bomb
            //this.myWorld.notifyBlockChange(x, y, z, oldBlock);
			
            if (newBlock.hasComparatorInputOverride())
            {
                this.myWorld.func_147453_f(x, y, z, newBlock);
            }
        }
	
    }
	
    public boolean setBlockPatch(int x, int y, int z, Block blockRef /*int mz, int p_147465_6_*/) //original defaults was 0 & 3
    {
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
                Chunk chunk = this.myWorld.getChunkFromChunkCoords(x >> 4, z >> 4);
                Block block1 = null;
                net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;

                if ((3 & 1) != 0)
                {
                    block1 = chunk.getBlock(x & 15, y, z & 15);
                }

                if (this.myWorld.captureBlockSnapshots && !this.myWorld.isRemote)
                {
                    blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(this.myWorld, x, y, z, 3);
                    this.myWorld.capturedBlockSnapshots.add(blockSnapshot);
                }

                boolean flag = chunk.func_150807_a(x & 15, y, z & 15, blockRef, 0);

                if (!flag && blockSnapshot != null)
                {
                    this.myWorld.capturedBlockSnapshots.remove(blockSnapshot);
                    blockSnapshot = null;
                }

                this.myWorld.theProfiler.startSection("checkLight");
                this.myWorld.func_147451_t(x, y, z);
                this.myWorld.theProfiler.endSection();

                if (flag && blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
                {
                    // Modularize client and physic updates
                    markAndNotifyBlockPatch(x, y, z, chunk, block1, blockRef, 3);
                }

                return flag;
            }
        }
        else
        {
            return false;
        }
    }
    
    
    //Holy fucking shit, this patch actually worked!
    public void notifyBlockOfNeighborChange(int x, int y, int z, final Block blockTypeRef)
    {
        if (!this.myWorld.isRemote)
        {
            Block block = this.myWorld.getBlock(x, y, z);

            try
            {
                onNeighborBlockChange(this.myWorld, x, y, z, blockTypeRef);
            }
            catch (Throwable throwable1)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception while updating neighbours in patched method");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
                int l;

                try
                {
                    l = this.myWorld.getBlockMetadata(x, y, z);
                }
                catch (Throwable throwable)
                {
                    l = -1;
                }

                crashreportcategory.addCrashSectionCallable("Source block type", new Callable()
                {
                    private static final String __OBFID = "CL_00000142";
                    public String call()
                    {
                        try
                        {
                            return String.format("ID #%d (%s // %s)", new Object[] {Integer.valueOf(Block.getIdFromBlock(blockTypeRef)), blockTypeRef.getUnlocalizedName(), blockTypeRef.getClass().getCanonicalName()});
                        }
                        catch (Throwable throwable2)
                        {
                            return "ID #" + Block.getIdFromBlock(blockTypeRef);
                        }
                    }
                });
                CrashReportCategory.func_147153_a(crashreportcategory, x, y, z, block, l);
                throw new ReportedException(crashreport);
            }
        }
    }
	
	//Store world
	public void onBlockAdded(World world, int x, int y, int z) {
		this.myWorld = world;
	}
	
	//FUNCTIONALITY!!!!!!
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if(!world.isRemote) {
			//I can edit this world
			powerToggle(world, x, y, z); 

		}
	}


	public void powerToggle(World world, int x, int y, int z) {
		//Check if there is another projector 8 blocks in 1d direction
		//1d being forward, back, left, right
		for(int n = 1; n < 10; n++) {
			
			//Check towards north first
			if(world.getBlock(x, y, (z - n)) == ModBlocks.projector) {
				if(!world.isBlockIndirectlyGettingPowered(x, y, (z - n)) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
				//If projector is found then set all blocks before it as replaceWith
					for(int k = 1; k < n; k++) {
						setBlockPatch(x, y, (z - k), Blocks.air);
					}
					
				} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y, (z - n))) { //If i'm powered or they are
					for(int k = 1; k < n; k++) {
						setBlockPatch(x, y, (z - k), ModBlocks.fieldBlock);
					}
				}
			} 
			
			//Check South
			if(world.getBlock(x, y, (z + n)) == ModBlocks.projector) {
				if(!world.isBlockIndirectlyGettingPowered(x, y, (z + n)) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
				//If projector is found then set all blocks before it as replaceWith
					for(int k = 1; k < n; k++) {
						setBlockPatch(x, y, (z + k), Blocks.air);
					}
					
				} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y, (z + n))) { //If i'm powered or they are
					for(int k = 1; k < n; k++) {
						setBlockPatch(x, y, (z + k), ModBlocks.fieldBlock);
					}
				}
			}
			
			//Check west
			if(world.getBlock((x - n), y, z) == ModBlocks.projector) {
				if(!world.isBlockIndirectlyGettingPowered((x - n), y, z) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
				//If projector is found then set all blocks before it as replaceWith
					for(int k = 1; k < n; k++) {
						setBlockPatch((x - k), y, z, Blocks.air);
					}
					
				} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered((x - n), y, z)) { //If i'm powered or they are
					for(int k = 1; k < n; k++) {
						setBlockPatch((x - k), y, z, ModBlocks.fieldBlock);
					}
				}
			} 
			
			//Check East
			if(world.getBlock((x + n), y, z) == ModBlocks.projector) {
				if(!world.isBlockIndirectlyGettingPowered((x + n), y, z) && !world.isBlockIndirectlyGettingPowered(x, y, z)) { //both blocks are off
				//If projector is found then set all blocks before it as replaceWith
					for(int k = 1; k < n; k++) {
						setBlockPatch((x + k), y, z, Blocks.air);
					}
					
				} else if(world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered((x + n), y, z)) { //If i'm powered or they are
					for(int k = 1; k < n; k++) {
						setBlockPatch((x + k), y, z, ModBlocks.fieldBlock);
					}
				}
			} 
		}//For() Container
	}//Method Container

}
