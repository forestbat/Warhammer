package com.forestbat.warhammer.tileentity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CrystalConverter extends EntityMachineBase implements IItemHandler {
    public static String[] GOLD_CRYSTAL_CONVERT_LIST={"ingotIron","ingotGold","ingotCopper","ingotTin","ingotLead","ingotSilver",
    "ingotAluminum","ingotNickel","ingotPlatinum","ingotSteel"};
    public static String[] WOOD_CRYSTAL_CONVERT_LIST={"logWood","plankWood","treeSapling","treeLeaves","ingotDraconium","netherStar",
    "enderpearl","bone","slimeball","gunpowder","string","leather","feather","egg","dragonEgg"};
    public static String[] WATER_CRYSTAL_CONVERT_LIST={};
    public static String[] FIRE_CRYSTAL_CONVERT_LIST={"oreCoal","ingotDraconiumAwakened","Yellorium"};
    public static String[] SOIL_CRYSTAL_CONVERT_LIST={"dirt","stone","cobblestone","obsidian","gemDiamond","gemEmerald","gemQuartz",
            "gemPrismarine","dustRedstone","dustGlowstone"};

    public class ConvertCrystalTypeAdapter extends TypeAdapter<ResourceLocation>{
        public void write(JsonWriter jsonWriter, ResourceLocation resourceLocation){
            try {
                jsonWriter.beginObject();
                for (String woodCrystalConverts : WOOD_CRYSTAL_CONVERT_LIST) {
                    if(woodCrystalConverts.equals("netherStar"))
                        jsonWriter.name(woodCrystalConverts).value(0.02);
                    if(woodCrystalConverts.equals("ingotDraconium")){
                        jsonWriter.name(woodCrystalConverts).value(0.05);
                    }
                    else jsonWriter.name(woodCrystalConverts).value(1/(WOOD_CRYSTAL_CONVERT_LIST.length-2));
                }
                for(String goldCrytalConverts:GOLD_CRYSTAL_CONVERT_LIST){
                    if(goldCrytalConverts.equals("ingotIron"))
                    jsonWriter.name(goldCrytalConverts).value(0.15);
                    if(goldCrytalConverts.equals("ingotPlatinum")||goldCrytalConverts.equals("ingotNickel"))
                        jsonWriter.name(goldCrytalConverts).value(0.03);
                    else jsonWriter.name(goldCrytalConverts).value(1/(GOLD_CRYSTAL_CONVERT_LIST.length-3));
                }
                jsonWriter.endObject();
            }catch (IOException e){
                FMLLog.bigWarning("No Convert List!");
            }
        }
        public ResourceLocation read(JsonReader jsonReader){
            return new ResourceLocation("crystal_convert_chances.json");
        }
        public void register(){
            GsonBuilder gsonBuilder = new GsonBuilder();
            final Gson gson = gsonBuilder.create();
            gsonBuilder.registerTypeAdapter(ResourceLocation.class,new ConvertCrystalTypeAdapter());
        }
    }

    public void convert(CrystalGenerator crystalGenerator){
        ItemStack itemCrystalStack=crystalGenerator.getStackInSlot(0);
        if(crystalGenerator.getEnergyStored()>0 && itemCrystalStack.getCount()>=64) {
            itemCrystalStack.shrink(64);
            insertItem(getSlots(),itemCrystalStack,false);
        }
    }
    public ItemStack convertOreStack(ItemStack itemStack){
        Gson gson=new Gson();
        ResourceLocation resourceLocation=new ResourceLocation(gson.toJson(GOLD_CRYSTAL_CONVERT_LIST));
        itemStack.setCount(64);
        return null;
    }
    public int getSlots(){return 26;}
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack itemStack,boolean simulate){
        return new ItemStackHandler().insertItem(slot,itemStack,false);
    }
    @Nonnull
    public ItemStack extractItem(int slot,int amount,boolean simulate) {
        return new ItemStackHandler().extractItem(slot, amount, simulate);
    }
    public int getSlotLimit(int slot){
        if(slot==0)
            return 1600;
        if(slot>0)
            return 64;
        else return 0;
    }
}
