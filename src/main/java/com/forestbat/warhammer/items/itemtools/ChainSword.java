package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ChainSword extends ToolsBase {
    public final int ChainMaxStore = 12000;
    public final int EnergyCost = 1;
    public final int SWORD_HARM = 25;

    public ChainSword() {
        setMaxStackSize(1);
        setUnlocalizedName("wh.chain_sword");
        setCreativeTab(Warhammer.WARHAMMER);
    }

    @Override
    public int getMaxEnergyStored() {
        return ChainMaxStore;
    }

    @Override
    @Nonnull
    public Multimap<String, AttributeModifier> getAttributeModifiers
            (@Nonnull EntityEquipmentSlot equipmentSlot, ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
                    "Tool Modifier", SWORD_HARM, 0));
        }
        return multimap;
    }

    @SubscribeEvent
    public void chanceViolentAttack(LivingAttackEvent event, EntityPlayer player) {
        boolean handChainSword=player.getHeldItemMainhand().getItem() instanceof ChainSword;
        if (event.getSource().equals(DamageSource.causePlayerDamage(player))){
            if (handChainSword && Math.random() <=0.15) {
                event.getEntityLiving().setDead();
                extractEnergy(5, false);
            }
            if(hitEntity(player.getHeldItemMainhand(),event.getEntityLiving(),player)&&handChainSword&&Math.random()>0.15){
                extractEnergy(EnergyCost,false);
            }
        }
    }
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        tooltip.add("This sword has 26 points hurt and 15% violent chance");
    }
}

