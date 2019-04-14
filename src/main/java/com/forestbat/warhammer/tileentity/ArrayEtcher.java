package com.forestbat.warhammer.tileentity;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import javax.annotation.Nonnull;
import static com.forestbat.warhammer.Warhammer.Items.*;

public class ArrayEtcher extends EntityMachineBase implements IRecipe {
    private static Item[] WarHammerArrays=new Item[]{BlastArray,CalcArray,
            EnderArray,EternalArray,SharpArray,ShieldArray,
            SpaceArray,StableArray};
    private static Item[] BrokenWarHammerArrays=new Item[]{BrokenBlastArray, /*BrokenCalcArray,*/
            BrokenEnderArray,BrokenEternalArray,BrokenSharpArray,BrokenShieldArray,
            BrokenSpaceArray,BrokenStableArray};
    public boolean matches(@Nonnull InventoryCrafting inventoryCrafting, @Nonnull World world){
        return true;
    }
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventoryCrafting){
      return this.getRecipeOutput().copy();
    }
    public boolean canFit(int x,int y){
        return true;
    }
    @Nonnull
    public ItemStack getRecipeOutput(){
        for(Item item:WarHammerArrays)
            return item.getDefaultInstance();
        return ItemStack.EMPTY;
    }
    public IRecipe setRegistryName(ResourceLocation resourceLocation){
        return new ArrayEtcher();
    }
    public ResourceLocation getRegistryName(){
        return new ResourceLocation("array_etcher_recipes.json");
    }
    public Class<IRecipe> getRegistryType(){
        return IRecipe.class;
    }
}

