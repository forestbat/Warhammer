package com.forestbat.warhammer.stuff;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.SetAttributes;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Random;

public class LostTreasure implements IWorldGenerator {
    public static final ResourceLocation LOST_TREASURE_FIRST=new ResourceLocation
            ("warhammer","resources/firsttreasurechest");
    public LootTableManager lootTableManager;
    @Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event){
        LootFunctionManager.registerFunction(new SetAttributes.Serializer());
    }
    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent loadEvent){
        String poolName="lost_treasure_first";
        ResourceLocation resourceLocation=loadEvent.getName();
        if(resourceLocation==LOST_TREASURE_FIRST){
            LootTable lostTreasureFirst=loadEvent.getLootTableManager().getLootTableFromLocation(resourceLocation);
            if(lostTreasureFirst!=LootTable.EMPTY_LOOT_TABLE) {
                LootPool pool = lostTreasureFirst.getPool(poolName);
                loadEvent.getTable().addPool(pool);
            }
        }
    }

    /** 3038 is hunluan's mcbbs uid*/
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
       int blockPosX=chunkX*16;
       int blockPosZ=chunkZ*16;
       TileEntityChest tileEntityChest=new TileEntityChest();
       IItemHandler inv=tileEntityChest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
       for(int i=0;i<20000;i++)
           if(blockPosX==3038*i) {
               world.setTileEntity(new BlockPos(blockPosX, 38, blockPosZ), tileEntityChest);
               LootTable lootTable=lootTableManager.getLootTableFromLocation(LOST_TREASURE_FIRST);
               List<ItemStack> chestItems=lootTable.generateLootForPools(new Random(),new LootContext.Builder((WorldServer)world).build());
               ItemHandlerHelper.insertItemStacked(inv,chestItems.iterator().next(),false);
           }
    }
}
