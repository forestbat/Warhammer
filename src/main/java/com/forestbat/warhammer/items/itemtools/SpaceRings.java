package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.math.IntMath.pow;

public class SpaceRings extends ToolsBase implements INBTSerializable<NBTBase> {
    public static long[] STORAGE_TIER={pow(2,8),pow(2,16),pow(2,24),pow(2,32),pow(2,40)};
    //private final Item item = new Item();
    public Object2ObjectMap<Item,List<ItemStack>> itemStacks= new Object2ObjectOpenHashMap<>(2003);
    private NBTTagList nbtTagList=new NBTTagList();
    NBTSizeTracker nbtSizeTracker=new NBTSizeTracker(16777216);

    public SpaceRings(){
       setMaxStackSize(1);
       setUnlocalizedName("space_ring");
       setCreativeTab(Warhammer.WARHAMMER);
    }
    @SuppressWarnings("unchecked")
    public Collection<ItemStack> getStack(){
        ObjectIterator<List<ItemStack>> itemStackIterator=itemStacks.values().iterator();
        while(itemStackIterator.hasNext()) {
            return itemStacks.values().iterator().next();
        }
        return Collections.EMPTY_LIST;
    }

    public void absorbItems(EntityPlayer entityPlayer, EntityItem entityItem, float partialTicks){
        //super.onItemUse(entityPlayer,entityPlayer.world,entityItem.getPosition(),EnumHand.MAIN_HAND,null,0,0,0);
        BlockPos playerTrace=entityPlayer.rayTrace(entityPlayer.REACH_DISTANCE.getDefaultValue(),partialTicks).getBlockPos();
        if(entityItem.getPosition()==playerTrace)
            if(entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof SpaceRings)
                input(entityItem.getItem(),entityItem.getItem().getCount());
    }
    public ItemStack input(ItemStack itemStack, int amount){
        List<ItemStack> otherStackList=itemStacks.get(itemStack.getItem());
        for(long storageTier:STORAGE_TIER)
          for(ItemStack otherStack:otherStackList) {
              if (isEqual(itemStack,otherStack)&&getStored()+amount<=storageTier) {
                  otherStack.grow(amount);
                  return null;
              }
              if (isEqual(itemStack,otherStack)&&getStored()+amount>storageTier) {
                  otherStack.grow(amount-(int)(storageTier-getStored()));
                  return ItemHandlerHelper.copyStackWithSize(itemStack,amount);
              }
              if(!isEqual(itemStack,otherStack)&&getStored()+amount<=storageTier){
                  itemStacks.put(itemStack.getItem(),otherStackList);
                  return null;
              }
              if(!isEqual(itemStack,otherStack)&&getStored()+amount>storageTier){
                  return ItemHandlerHelper.copyStackWithSize(itemStack,amount);
              }
          }
          serializeNBT();
          return null;
    }
    public ItemStack output(@Nonnull ItemStack itemStack, int amount) {
        List<ItemStack> otherStackList=itemStacks.get(itemStack.getItem());
        for (ItemStack otherStack:otherStackList) {
            if (isEqual(otherStack, itemStack)) {
                if (amount > otherStack.getCount()) {
                    amount = otherStack.getCount();
                }
                {
                    if (otherStack.getCount()==amount) {
                        otherStackList.remove(otherStack);
                    } else {
                        otherStack.shrink(amount);
                    }
                }
                return ItemHandlerHelper.copyStackWithSize(otherStack, amount);
            }
        }
        serializeNBT();
        return null;
    }
    public NBTBase serializeNBT(){
        for(ItemStack itemStack:getStack()) {
            NBTTagCompound nbtTagCompound=itemStack.getTagCompound();
            if(nbtTagCompound!=null) {
                nbtTagList.appendTag(nbtTagCompound);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        return nbt;
    }
    public void deserializeNBT(NBTBase nbtBase){
        //NBTTagCompound nbtTagCompound= CompressedStreamTools.read(new DataInputStream(),nbtSizeTracker);
    }
    public boolean isEqual(ItemStack left,ItemStack right){
        return  (!left.hasTagCompound()&&!right.hasTagCompound())||
                (left.getItem()==right.getItem()&&
                left.getTagCompound()==right.getTagCompound()&&
                left.getMetadata()==right.getMetadata());
    }
    public long getStored(){
        return getStack().stream().mapToLong(ItemStack::getCount).sum();
    }

    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return super.updateItemStackNBT(nbt);
    }
}
