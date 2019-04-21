package com.forestbat.warhammer.items.itemtools;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Iterator;

import static com.forestbat.warhammer.Warhammer.Items.CARTRIDGE_BAG;
import static com.forestbat.warhammer.Warhammer.Items.CLIP;
import static com.forestbat.warhammer.Warhammer.WARHAMMER;

public class FirePythonHeavyGun extends ToolsBase {
    private int GUN_MAX_STORE=256000;
    private static int SHOOT_DISTANCE=Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
    public FirePythonHeavyGun(){
        setUnlocalizedName("fire_python_heavy_gun");
        setCreativeTab(WARHAMMER);
    }
    @Override
    public int getMaxEnergyStored() {
        return GUN_MAX_STORE;
    }
    public void onUsingTick(ItemStack itemStack, EntityLivingBase player, int count) {
        Iterator<ItemStack> itemStackIterator = ((EntityPlayer) player).inventoryContainer.inventoryItemStacks.iterator();
        while (itemStackIterator.hasNext()) {
            ItemStack clip = itemStackIterator.next();
            BlockPos blockPos = rayTrace(player.world, (EntityPlayer) player, false).getBlockPos();
            if (clip.equals(CLIP) || clip.equals(CARTRIDGE_BAG)) {
                Block block = player.world.getBlockState(blockPos).getBlock();
                EntityLivingBase enemyEntity = (EntityLivingBase) rayTrace(player.world, (EntityPlayer) player, false).entityHit;
                while (count <= getMaxDamage(CLIP.getDefaultInstance())) {
                    clip.setItemDamage(clip.getItemDamage() - 1);
                    enemyEntity.setHealth(enemyEntity.getHealth() - 7);
                    if (enemyEntity.getHealth() < 1)
                        enemyEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 200);
                    extractEnergy(itemStack, 1, false);
                    block.breakBlock(player.world, blockPos, block.getDefaultState());
                    block.dropBlockAsItem(player.world, blockPos, block.getDefaultState(), 1);
                }
            }
        }
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn){
        RayTraceResult result=playerIn.rayTrace(SHOOT_DISTANCE,1);
        Timer timer=new Timer(20);
        if(result!=null) {
            onUsingTick(new ItemStack(this),playerIn,timer.elapsedTicks);
        }
        return new ActionResult<>(EnumActionResult.PASS, new ItemStack(this));
    }
}
