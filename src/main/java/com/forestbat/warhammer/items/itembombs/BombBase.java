package com.forestbat.warhammer.items.itembombs;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.util.Timer;

import javax.annotation.Nonnull;

import static com.forestbat.warhammer.configs.WarhammerConfig.BOMB_FLIGHT_VELOCITY;

public class BombBase extends Item {
    //public static final float RAD_CHANGER = 0.017453292F;
    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, @Nonnull EntityPlayer playerIn,EnumHand handIn) {
        ItemStack itemStack=playerIn.getHeldItemMainhand();
        if(!playerIn.capabilities.isCreativeMode)
            itemStack.shrink(1);
        worldIn.playSound(playerIn,playerIn.posX,playerIn.posY,playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS,1,1);
        if(worldIn.isRemote){
            shootCalculator(playerIn,playerIn.getEntityWorld());
        }
        return new ActionResult<>(EnumActionResult.SUCCESS,itemStack);
    }
    public BlockPos shootCalculator(Entity entityThrower,World world){
        float v0=BOMB_FLIGHT_VELOCITY;
        Timer timer=new Timer();
        double v0AxisX=MathHelper.cos(entityThrower.rotationYaw*0.017453F);
        double v0AxisY=MathHelper.sin(entityThrower.rotationPitch*0.017453F);
        double v0AxisZ=MathHelper.sin(entityThrower.rotationYaw*0.017453F);
        double shootY=entityThrower.posY+v0AxisY*timer.getTime()-4.89015*timer.getTime()*timer.getTime();
        double shootX=entityThrower.posX+v0AxisX*timer.getTime();
        double shootZ=entityThrower.posZ+v0AxisZ*timer.getTime();
        return new BlockPos(shootX,shootY,shootZ);
    }
    /*public static class ThrowEvent extends Event {
        public static ThrowEvent INSTANCE;
        EntityPlayer entityPlayer;
        BlockPos pos;
        public ThrowEvent(@Nonnull EntityPlayer entityPlayer, BlockPos pos){
            this.entityPlayer=entityPlayer;
            this.pos=pos;
            long timer=entityPlayer.world.getWorldTime();
            MinecraftForge.EVENT_BUS.register(new ThrowEvent(entityPlayer,pos));
        }
        public World getWorld(){
            return entityPlayer.world;
        }
        public BlockPos getPos(){
            return entityPlayer.getPosition();
        }
        public long getTimer(){
            return entityPlayer.world.getWorldTime();
        }
        @SubscribeEvent
        public void isStart(ThrowEvent event,BombBase bombBase){
            bombBase.shootCalculator(entityPlayer,entityPlayer.world);
        }
    }*/

    /**Come from vanilla MC,it can only happen sound, particles, drop spawn*/
    public void bombAction(EntityPlayer entityThrower,World world) {
        BlockPos shooter = shootCalculator(entityThrower, world);
        world.spawnParticle(EnumParticleTypes.REDSTONE, shooter.getX(), shooter.getY(), shooter.getZ(), 5, 5, 5);
        Block block = world.getBlockState(shooter).getBlock();
        if (block != Blocks.AIR || block != Blocks.WATER || block != Blocks.FLOWING_WATER) {
            world.playSound(entityThrower, shootCalculator(entityThrower, entityThrower.world).getX(),
                    shootCalculator(entityThrower, entityThrower.world).getY(), shootCalculator(entityThrower, entityThrower.world).getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, 5F);
            world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, shootCalculator(entityThrower, entityThrower.world).getX(),
                    shootCalculator(entityThrower, entityThrower.world).getY(), shootCalculator(entityThrower, entityThrower.world).getZ(),
                    1.0D, 0.0D, 0.0D);
        }
    }
}
