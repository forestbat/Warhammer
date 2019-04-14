package com.forestbat.warhammer.container;

import com.forestbat.warhammer.tileentity.SpaceRingReader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

public class SpaceRingReaderContainer extends Container {
    SpaceRingReader spaceRingReader;
    EntityPlayer player;
    public SpaceRingReaderContainer(){
        for(int i=0;i<27;i++) {
            addSlotToContainer(new SlotItemHandler(new SpaceRingReader(),i,16+18*i,40+(i/9)*18));
            addSlotToContainer(new Slot(player.inventory, i, 16 + 18 * i,90+(i/9)*18));
        }
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        //if(playerIn.getPersistentID().toString().equals(EntityMachineBase.create()))
        return true;
    }

    @Override
    public Slot addSlotToContainer(Slot slotIn) {
        return super.addSlotToContainer(slotIn);
    }
}
