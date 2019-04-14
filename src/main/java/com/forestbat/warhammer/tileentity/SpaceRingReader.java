package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.gui.GuiSpaceRing;
import com.forestbat.warhammer.items.itemtools.SpaceRings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpaceRingReader extends EntityMachineBase {
    private ItemStack readerSlot=inv.get(0);
    public boolean hasSpaceRing() {
        return readerSlot.getItem() == SpaceRings.getByNameOrId("wh.space_ring");//todo
    }
    @SubscribeEvent
    public EnumActionResult onRightClick(PlayerInteractEvent event, EntityPlayer player, Container container){
        ItemStack heldItem=player.getHeldItemMainhand();
        if(event.getPos()==pos&&hasSpaceRing()) {
            player.openGui(new GuiSpaceRing(container),-1,player.world,
                    event.getPos().getX(),event.getPos().getY(),event.getPos().getZ());
            return EnumActionResult.SUCCESS;
        }
        if(event.getPos()==pos&&!hasSpaceRing()&&heldItem.getItem() instanceof SpaceRings) {
            inv.set(0, heldItem);
            return EnumActionResult.SUCCESS;
        }
        else return EnumActionResult.FAIL;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if(hasSpaceRing())
        super.readFromNBT(readerSlot.getTagCompound());
    }
}
