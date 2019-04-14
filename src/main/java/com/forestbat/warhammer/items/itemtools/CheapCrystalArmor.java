package com.forestbat.warhammer.items.itemtools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class CheapCrystalArmor extends ToolsBase {
    public int MAX_CHEAP_ARMOR_ENERGY=640000;

    @Override
    public int getMaxEnergyStored() {
        return MAX_CHEAP_ARMOR_ENERGY;
    }
    public void protectPlayer(EntityPlayer player){
        if(player.inventoryContainer.getInventory().iterator().next().getItem() instanceof CheapCrystalArmor) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
            player.fallDistance=0;
        }
    }
}
