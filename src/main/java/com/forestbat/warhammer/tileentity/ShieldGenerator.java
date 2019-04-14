package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.items.itemtools.CrystalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

public class ShieldGenerator extends EntityMachineBase{
    public static int slot;
    private static int MAX_LENGTH,MAX_HEIGHT,MAX_WIDTH,ROOM_THICK;
    private EntityPlayer player;
    @SubscribeEvent
    public void blockChanger(LivingDeathEvent event,ShieldGenerator shieldGenerator,Entity entity) {
        BlockPos from = shieldGenerator.pos.up(MAX_HEIGHT / 2).north(MAX_WIDTH / 2).west(MAX_LENGTH / 2);
        BlockPos to = shieldGenerator.pos.down(MAX_HEIGHT / 2).south(MAX_WIDTH / 2).east(MAX_LENGTH / 2);
        BlockPos innerFrom = shieldGenerator.pos.up(MAX_HEIGHT / 2 - ROOM_THICK / 2).north(MAX_WIDTH / 2 - ROOM_THICK / 2).west(MAX_LENGTH / 2 - ROOM_THICK / 2);
        BlockPos innerTo = shieldGenerator.pos.down(MAX_HEIGHT / 2 - ROOM_THICK / 2).south(MAX_WIDTH / 2 - ROOM_THICK / 2).east(MAX_LENGTH / 2 - ROOM_THICK / 2);
        Iterator<BlockPos> outBlockPos = BlockPos.getAllInBox(from, to).iterator();
        Iterator<BlockPos> innBlockPos = BlockPos.getAllInBox(innerFrom, innerTo).iterator();
        boolean canBeKill = entity.getPosition() == outBlockPos.next() && entity.getPosition() != innBlockPos.next();
        while (outBlockPos.hasNext() && innBlockPos.hasNext()) {
            for (ItemStack stack : player.inventoryContainer.getInventory()) {
                if (player.getUniqueID() == getOwner() && entity instanceof EntityPlayer && stack.getItem() instanceof CrystalArmor) {
                    return;
                }
                if (canBeKill) {
                    entity.setDead();
                    if (entity instanceof EntityItem && getStackInSlot(slot) == null)
                        setStackInSlot(((EntityItem) entity).getItem(), slot);
                }
                if (event.isCanceled() && !(entity instanceof EntityPlayer))
                    entity.world.removeEntity(event.getEntityLiving());
            }
        }
    }
//todo

   @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setUniqueId("Owner",player.getUniqueID());
        return super.writeToNBT(compound);
    }

    public UUID getOwner(){
        return player.serializeNBT().getUniqueId("Owner");
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void breakShieldBlock(BlockEvent.BreakEvent event,NBTTagCompound nbtTagCompound){
        if(!event.getPlayer().getCachedUniqueIdString().equals(getOwner().toString()))
            event.setCanceled(true);
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void interactShieldBlock(PlayerInteractEvent event,NBTTagCompound nbtTagCompound) {
        if (event.getEntityPlayer().getUniqueID() != getOwner())
            event.setCanceled(true);
    }
    public int getSizeInventory(){return 27;}

}
