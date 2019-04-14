package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.stuff.AttributeCellAnnihilateGun;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PlayerReinforcer extends EntityMachineBase {
    public static boolean MACHINE_IS_OPEN=true;
    public static boolean SUPER_STRONG=true;
    public void playerReinforce(EntityMachineBase entityMachineBase, EntityPlayer entityPlayer){
        if(MACHINE_IS_OPEN){
            entityPlayer.capabilities.setFlySpeed(10);
            entityPlayer.capabilities.allowFlying=true;
            entityPlayer.addPotionEffect(new PotionEffect
                    (MobEffects.ABSORPTION,200,100,true,false));
            entityPlayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,200,6,true,false));
            if(SUPER_STRONG){
                entityPlayer.getCooldownTracker().setCooldown(ItemStack.EMPTY.getItem(),1);
                entityPlayer.getCooledAttackStrength(0);
                entityPlayer.getEntityAttribute(new AttributeCellAnnihilateGun().getAttribute()).getBaseValue();
            }
        }
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return super.extractEnergy(maxExtract, simulate);
    }

    public void update(){
        ChunkProviderClient chunkProviderClient=new ChunkProviderClient(world);
        chunkProviderClient.loadChunk(getPos().getX(),getPos().getZ());
    }
}
