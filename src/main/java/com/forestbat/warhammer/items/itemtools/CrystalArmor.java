package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrystalArmor extends ToolsBase {
    public static int MAX_ARMOR_ENERGY=64000000;
    public CrystalArmor(){
        setMaxStackSize(1);
        setUnlocalizedName("crystal_armor");
        setCreativeTab(Warhammer.WARHAMMER);
    }
    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int itemSlot, boolean isSelected){
        if(!world.isRemote&& player instanceof EntityPlayer){
            onCrystalArmorUpdate(stack.getItem(),world,(EntityPlayer)player);
        }
    }
    public void onCrystalArmorUpdate(Item item, World world, EntityPlayer player){
        ItemStack itemStack=player.inventoryContainer.getInventory().iterator().next();
        if(player instanceof FakePlayer) return;
        player.fallDistance=0;
        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION,200,100,true,false));
        player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST,200,4));
        player.setHealth(player.getMaxHealth());
        if(showDurabilityBar(itemStack)&& itemStack.getItem() instanceof IEnergyStorage && item instanceof CrystalArmor) {
            ((IEnergyStorage)itemStack.getItem()).receiveEnergy(Integer.MAX_VALUE, false);
            ((CrystalArmor)item).extractEnergy(item.getDefaultInstance(),20,false);
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDamaged(LivingAttackEvent event, LivingDeathEvent livingDeathEvent){
        if (event.getEntityLiving() instanceof EntityPlayer||livingDeathEvent.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            for(ItemStack stack:player.inventoryContainer.getInventory())
                if(stack.getItem() instanceof CrystalArmor){
                    event.setCanceled(true);
                    livingDeathEvent.setCanceled(true);
                    extractEnergy(stack,5,false);
            }
        }
    }
    public void changePlayerSkin(EntityPlayer player){

    }

    public int getMaxEnergyStored(){
        return MAX_ARMOR_ENERGY;
    }
    public void giveCapability(EntityPlayer player){
        for(ItemStack stack:player.inventoryContainer.getInventory())
            if(stack.getItem() instanceof CrystalArmor && ((CrystalArmor) stack.getItem()).getEnergyStored()>0) {
                player.capabilities.setFlySpeed(10);
                player.capabilities.allowFlying=true;
        }
    }
}
