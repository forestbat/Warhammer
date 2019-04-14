package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import javax.annotation.Nonnull;

public class ShakeSabre extends ToolsBase {
    private final int SABRE_MAX_STORE = 15000;
    private final int EnergyCost = 1;
    private final int SABRE_HARM= 30;

    public ShakeSabre() {
        setMaxDamage(-1);
        setUnlocalizedName("wh.shake_sabre");
        setCreativeTab(Warhammer.WARHAMMER);
    }

    @Override
    public int getMaxEnergyStored() {
        return SABRE_MAX_STORE;
    }

    public boolean showDurabilityBar(ItemStack itemStack) {
        return !(getEnergyStored() == SABRE_MAX_STORE);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (getEnergyStored() / SABRE_MAX_STORE);
    }

    @Override
    @Nonnull
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
                    "Tool Modifier", SABRE_HARM, 0));
        }
        return multimap;
    }
    public void chanceViolentAttack(LivingAttackEvent event, EntityPlayer player){
        if(event.getSource().equals(DamageSource.causePlayerDamage(player)))
            if(player.getHeldItemMainhand().getItem() instanceof ShakeSabre && Math.random()<0.1){
                event.getEntityLiving().setDead();
                extractEnergy(5,false);
            }
    }

    public boolean hitEntity(ItemStack itemStack, EntityLivingBase elb, EntityLivingBase player) {
        return true;
    }
    public int receiveEnergy(ToolsBase buffer, int maxReceive, boolean simulate){
        ItemStack itemStack=buffer.getDefaultInstance();
        if(!itemStack.hasTagCompound())
           itemStack.setTagCompound(new NBTTagCompound());
        int storeAmount=itemStack.getTagCompound().getInteger(ENERGY);
        int receive=Math.min(getMaxEnergyStored()-storeAmount,maxReceive);
        if(!simulate){
            storeAmount+=receive;
            itemStack.getTagCompound().setInteger(ENERGY,storeAmount);
        }
        return receive;
    }
    public int extractEnergy(ToolsBase buffer,int maxExtract,boolean simulate){
        ItemStack itemStack=buffer.getDefaultInstance();
        int storeAmount=itemStack.getTagCompound().getInteger(ENERGY);
        if(!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());
        int extract=Math.min(storeAmount,EnergyCost);
        if(!simulate){
            storeAmount-=extract;
            itemStack.getTagCompound().setInteger(ENERGY,storeAmount);
        }
        return extract;
    }
}


