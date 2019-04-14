package com.forestbat.warhammer.items.itemmaterials;

import com.forestbat.warhammer.Warhammer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class ArmorPlate extends Item {
    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return true;
    }
    public ArmorPlate(){
        setCreativeTab(Warhammer.WARHAMMER);
    }

    @SubscribeEvent
    public void avoidAxe(LivingDamageEvent event){
        DamageSource damageSource=event.getSource();
        Entity enemy=damageSource.getTrueSource();
        if(damageSource.getDamageType().equals("player") && enemy instanceof EntityLivingBase)
            if(((EntityLivingBase)enemy).getHeldItemMainhand().getItem()instanceof ItemAxe)
                event.setCanceled(true);
    }
}
