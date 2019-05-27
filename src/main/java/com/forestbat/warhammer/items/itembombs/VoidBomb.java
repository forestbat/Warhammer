package com.forestbat.warhammer.items.itembombs;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

public class VoidBomb extends BombBase{
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn, EnumHand handIn) {
        bombAction(playerIn,worldIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    public void bombAction(EntityPlayer player,World world){
        BlockPos blockPos=shootCalculator(player,world);
        Iterator<EntityLivingBase> entities=world.getEntities
            (EntityLivingBase.class,input -> input.getDistanceSq(blockPos)<20).iterator();
        while (entities.hasNext())
        entities.next().addPotionEffect(new VoidPotionEffect(MobEffects.WITHER,400));
    }
    public static class VoidPotionEffect extends PotionEffect{
        public VoidPotionEffect(Potion potion, int duration){
            super(potion,duration);
        }
        public void performEffect(EntityLivingBase entityIn)
        {
            if (this.getDuration() > 0 && entityIn!=null)
            {
                entityIn.attackEntityFrom(DamageSource.OUT_OF_WORLD,5);
            }
        }
    }
}
