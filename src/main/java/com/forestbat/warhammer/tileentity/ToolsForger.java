package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.recipes.ToolsForgerRecipe;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ToolsForger extends EntityMachineBase implements IItemHandler {
    //todo
    private int SIZE_INVENTORY=51;
    private int BUFFER_SIZE=25;
    private ItemStack[] itemStacks=new ItemStack[getSizeInventory()];
    public void autoRecipe(ToolsForger toolsForger, JsonObject resourceLocation, int slot){
            while(slot<getSizeInventory()-BUFFER_SIZE-1) {
            itemStacks[slot] = toolsForger.getStackInSlot(slot);
            if (Ingredient.fromStacks(itemStacks) == ToolsForgerRecipe.deserializeIngredient(resourceLocation)) {
                itemStacks[slot].shrink(resourceLocation.get("key").getAsJsonObject().get("count").getAsInt());
                itemStacks[getSizeInventory()] = ToolsForgerRecipe.deserializeItem
                        (JsonUtils.getJsonObject(resourceLocation, "result"));
                toolsForger.extractEnergy(20,false);
            }
        }
    }
    public int getSizeInventory(){
        return SIZE_INVENTORY;
    }

    public int getSlots(){
        return SIZE_INVENTORY;
    }

    public ItemStack insertItem(int slot,ItemStack stack,boolean simulate) {
        return new ItemStackHandler().insertItem(slot,stack,false);
    }
    public ItemStack extractItem(int slot,int amount,boolean simulate){
        return new ItemStackHandler().extractItem(slot,amount,false);
    }

    @Override
    public int getSlotLimit(int slot) {
        if(slot<getSizeInventory()-BUFFER_SIZE)
            return 256;
        else return 1024;
    }
}



