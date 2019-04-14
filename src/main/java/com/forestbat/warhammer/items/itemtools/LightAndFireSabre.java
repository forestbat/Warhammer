package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class LightAndFireSabre extends ToolsBase {
    private static int AXE_MAX_STORE=20000;
    private static int LIGHT_FIRE_HARM=50;
    private static int EnergyCost=2;
    public LightAndFireSabre(){
        setMaxStackSize(1);
        setUnlocalizedName("light_and_fire_sabre");
        setCreativeTab(Warhammer.WARHAMMER);
    }
    @Override
    public int getMaxEnergyStored() {
        return AXE_MAX_STORE;
    }
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot,ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
                    "Tool Modifier", LIGHT_FIRE_HARM, 0));
        }
        return multimap;
    }

    @SubscribeEvent
    public void chanceViolentAttack(LivingAttackEvent event, EntityPlayer player) {
        boolean handElectricHeatAxe=player.getHeldItemMainhand().getItem() instanceof ElectricHeatAxe;
        if (event.getSource().equals(DamageSource.causePlayerDamage(player))) {
            if (handElectricHeatAxe && Math.random() <=0.25) {
                event.getEntityLiving().setDead();
                extractEnergy(10, false);
            }
            if(hitEntity(player.getHeldItemMainhand(),event.getEntityLiving(),player)&&handElectricHeatAxe&&Math.random()>0.1){
                extractEnergy(EnergyCost,false);
            }
        }
    }
}
