package com.forestbat.warhammer.tileentity;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EnvironmentController extends EntityMachineBase{
    private int TEMPRETURE_SLOT=0;
    private int HUMIDITY_SLOT=1;
    private int WOOD_CRYSTAL_SLOT=2;
    private int ENERGY_COST=250;

    public static void setBiome(BlockPos pos, World world, Biome biome) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        if(chunk != null) {
            byte[] biomeArray = chunk.getBiomeArray();
            biomeArray[chunk.x|chunk.z] = (byte)(Biome.getIdForBiome(biome) & 255);
            chunk.markDirty();
            world.getChunkProvider().provideChunk(chunk.x, chunk.z);
        } else {
           FMLLog.bigWarning("Tried changing biome at non-existing chunk for position ");
        }
    }

    public void decideBiome(JsonObject resourceLocation){
        //ResourceLocation resourceLocation=new ResourceLocation("assets/envctrl.json");
            int temperatureMaxAmount = resourceLocation.getAsJsonObject("temperature_max_amount").getAsInt();
            int temperatureMinAmount = resourceLocation.getAsJsonObject("temperature_min_amount").getAsInt();
            int humidityMaxAmount=resourceLocation.getAsJsonObject("humidity_max_amount").getAsInt();
            int humidityMinAmount=resourceLocation.getAsJsonObject("humidity_min_amount").getAsInt();
            int livesMinAmount=resourceLocation.getAsJsonObject("lives_min_amount").getAsInt();
            int livesMaxAmount=resourceLocation.getAsJsonObject("lives_max_amount").getAsInt();

            Item temperatureItem=Item.getByNameOrId(resourceLocation.getAsJsonObject("temperature_item").getAsString());
            Item humidityItem=Item.getByNameOrId(resourceLocation.getAsJsonObject("humidity_item").getAsString());
            Item livesItem=Item.getByNameOrId(resourceLocation.getAsJsonObject("lives_item").getAsString());
            ItemStack nowTempItem=getStackInSlot(TEMPRETURE_SLOT);
            ItemStack nowHumiItem=getStackInSlot(HUMIDITY_SLOT);
            ItemStack nowLivesItem=getStackInSlot(WOOD_CRYSTAL_SLOT);

            boolean tempReady=nowTempItem.getItem().equals(temperatureItem)&& nowTempItem.getCount()>=temperatureMinAmount
                    &&nowTempItem.getCount()<=temperatureMaxAmount;
            boolean humiReady=nowHumiItem.getItem().equals(humidityItem)&&nowHumiItem.getCount()>=humidityMinAmount
                    &&nowHumiItem.getCount()<=humidityMaxAmount;
            boolean livesReady=nowLivesItem.getItem().equals(livesItem)&&nowLivesItem.getCount()>=livesMinAmount
                    &&nowLivesItem.getCount()<=livesMaxAmount;
            if(tempReady && humiReady && livesReady) {
                TileEntityBeaconRenderer.renderBeamSegment(pos.getX(),pos.getY(),pos.getZ(),
                        0,1,200,0,256,new float[]{255,255,255});
                setBiome(pos,world, ForgeRegistries.BIOMES.getValue(new ResourceLocation("hell")));//todo
                extractEnergy(ENERGY_COST,false);
            }
        }

    @Override
    public int getMaxEnergyStored() {
        return 25000;
    }
}
