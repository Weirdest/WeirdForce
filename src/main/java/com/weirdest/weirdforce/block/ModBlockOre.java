package com.weirdest.weirdforce.block;

import java.util.Random;

import com.weirdest.weirdforce.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ModBlockOre extends Block {
	
	private Item drop;
	private int least_quantity;
	private int most_quantity;

    protected ModBlockOre(String unlocalizedName, Material mat, String HarvestTool, int HarvestLevel, Item drop, int least_quantity, int most_quantity) {
        super(mat);
        this.setBlockName(unlocalizedName);
        this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel(HarvestTool, HarvestLevel);
        this.drop = drop;
        this.least_quantity = least_quantity;
        this.most_quantity = most_quantity;
    }
    
    protected ModBlockOre(String unlocalizedName, Material mat, String HarvestTool, int HarvestLevel, Item drop) {
        super(mat);
        this.setBlockName(unlocalizedName);
        this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel(HarvestTool, HarvestLevel);
        this.drop = drop;
        this.least_quantity = 1;
        this.most_quantity = 4;
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
    	return this.drop;
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        if (this.least_quantity >= this.most_quantity)
            return this.least_quantity;
        return this.least_quantity + random.nextInt(this.most_quantity - this.least_quantity + fortune + 1);
    }
}