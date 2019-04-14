package com.forestbat.warhammer.items.itemtools;

import com.forestbat.warhammer.Warhammer;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class ElectricHeatAxe extends ToolsBase {
    private static int AXE_MAX_STORE=15000;
    private static int AXE_HARM=30;
    private static int EnergyCost=1;
    public ElectricHeatAxe(){
        setMaxStackSize(1);
        setUnlocalizedName("electric_heat_exe");
        setCreativeTab(Warhammer.WARHAMMER);
    }
    @Override
    public int getMaxEnergyStored() {
        return AXE_MAX_STORE;
    }
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot, ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemStack);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
                    "Tool Modifier", AXE_HARM, 0));
        }
        return multimap;
    }

    @SubscribeEvent
    public void chanceViolentAttack(LivingAttackEvent event, EntityPlayer player) {
        boolean handElectricHeatAxe=player.getHeldItemMainhand().getItem() instanceof ElectricHeatAxe;
        if (event.getSource().equals(DamageSource.causePlayerDamage(player))) {
            if (handElectricHeatAxe && Math.random() <=0.1) {
                event.getEntityLiving().setDead();
                extractEnergy(6, false);
            }
            if(hitEntity(player.getHeldItemMainhand(),event.getEntityLiving(),player)&&handElectricHeatAxe&&Math.random()>0.1){
                extractEnergy(EnergyCost,false);
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        for(BlockPos blockPos:BlockPos.getAllInBox(pos.getX()-64,pos.getY()-64,pos.getZ()-64,
                pos.getX()+64,pos.getY()+64,pos.getZ()+64)) {
            if(worldIn.getBlockState(blockPos).getBlock().equals(Blocks.LOG))
            worldIn.destroyBlock(blockPos, true);
        }
        return EnumActionResult.SUCCESS;
    }
}

