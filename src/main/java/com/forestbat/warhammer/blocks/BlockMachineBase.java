package com.forestbat.warhammer.blocks;

import com.forestbat.warhammer.Warhammer;
import com.forestbat.warhammer.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class BlockMachineBase extends Block implements ITileEntityProvider, IInteractionObject {
    public final int meta;
    public BlockMachineBase(int meta){
        super(Material.ROCK);
        setHardness(1000);
        setResistance(1000);
        setLightLevel(15);
        setCreativeTab(Warhammer.WARHAMMER);
        this.meta=meta;
    }
    public TileEntity createNewTileEntity(@Nonnull World worldIn,int meta){
        switch (this.meta){
            case 1:return new ArrayEtcher();
            case 2:return new CrystalGenerator();
            case 3:return new LittleCrystalBurner();
            case 4:return new LittleCrystalCollider();
            case 5:return new PipesConnector();
            case 6:return new ShardDimensionFinder();
            case 7:return new ShieldGenerator();
            case 8:return new ToolsForger();
            case 9:return new PlayerReinforcer();
            case 10:return new SpaceRingReader();
            case 11:return new Turret();
            case 12:return new CrystalChest();
            case 13:return new EnvironmentController();
            default:return new EntityMachineBase();
        }
    }
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        if(getMetaFromState(state)==7) {
            if (!stack.getTagCompound().hasKey("Owner"))
                stack.setTagCompound(new NBTTagCompound());
            UUID owner = placer.getUniqueID();
            super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if(te!=null) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            NBTTagCompound saveNBT=ItemStackHelper.saveAllItems(nbtTagCompound,new NonNullList<>(),true);
            ItemStack itemStack = new ItemStack(this);
            itemStack.writeToNBT(saveNBT);
            EntityItem entityItem=new EntityItem(worldIn,player.posX,player.posY,player.posZ,itemStack);
            entityItem.setInfinitePickupDelay();
            entityItem.setEntityInvulnerable(true);
            worldIn.spawnEntity(entityItem);
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
