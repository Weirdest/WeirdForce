package com.weirdest.weirdforce.world;

import java.util.Random;
import com.weirdest.weirdforce.block.ModBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

/* Steps to add generator
 * 1. Create WorldGenerator var
 * 2. Add instance to ModWorldGen()
 * 3. Pick dimension and location for generator in generate()
 */

public class ModWorldGen implements IWorldGenerator {
	
	private WorldGenerator gen_forcium_ore; //Only in overworld
	
	public ModWorldGen() {
	    this.gen_forcium_ore = new WorldGenMinable(ModBlocks.forciumOre, 6); //Max vein size of 6
	}
	
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {   
    	if(world.provider.dimensionId == 0) { //I only generate in the overworld
    		this.runGenerator(this.gen_forcium_ore, world, random, chunkX, chunkZ, 13, 0, 34); //13 chances to spawn, min height of 0 max of 34
    	}
    }
    
    //Dont touch this, its dynamic
    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) {
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator"); //Generate ore below bedrock... sure thing!
        }

        int heightDiff = (maxHeight - minHeight + 1);
        for (int i = 0; i < chancesToSpawn; i ++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, x, y, z);
        }
    }

}
