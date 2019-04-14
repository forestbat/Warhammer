package com.forestbat.warhammer;

import com.forestbat.warhammer.blocks.BlockCrystals;
import com.forestbat.warhammer.blocks.BlockMachineBase;
import com.forestbat.warhammer.configs.WarhammerConfig;
import com.forestbat.warhammer.items.itembombs.*;
import com.forestbat.warhammer.items.itemmaterials.ArmorPlate;
import com.forestbat.warhammer.items.itemmaterials.CrystalCamera;
import com.forestbat.warhammer.items.itemmaterials.HeartProtectMirror;
import com.forestbat.warhammer.items.itemtools.*;
import com.forestbat.warhammer.tileentity.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Warhammer.MOD_ID,
        name = Warhammer.MOD_NAME,
        version = Warhammer.VERSION,
        dependencies = "after:*;"
)
public class Warhammer {

    public static final String MOD_ID = "warhammer";
    public static final String MOD_NAME = "Warhammer";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static Logger LOGGER= LogManager.getLogger();

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Warhammer INSTANCE;
    public static CreativeTabs WARHAMMER=new CreativeTabs("Warhammer 40k") {
        @MethodsReturnNonnullByDefault
        public ItemStack getTabIconItem() {
            return new OreFinderBomb().getDefaultInstance();
        }
    };
    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(ToolsForger.class,new ResourceLocation("tools_forger"));
        GameRegistry.registerTileEntity(Turret.class,new ResourceLocation("turret"));
        GameRegistry.registerTileEntity(ShieldGenerator.class,new ResourceLocation("shield_generator"));
        GameRegistry.registerTileEntity(SpaceRingReader.class,new ResourceLocation("space_ring_reader"));
        GameRegistry.registerTileEntity(CrystalChest.class,new ResourceLocation("crystal_chest.json"));
        GameRegistry.registerTileEntity(PipesConnector.class,new ResourceLocation("pipes_connector"));
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    //@GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
        public static Block Pipeline=new Block(Material.ROCK);
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
        @GameRegistry.ObjectHolder("gold_crystal")
        public static final Item GoldCrystal=new Item();
        @GameRegistry.ObjectHolder("wood_crystal")
        public static final Item WoodCrystal=new Item();
        @GameRegistry.ObjectHolder("water_crystal")
        public static final Item WaterCrystal=new Item();
        @GameRegistry.ObjectHolder("fire_crystal")
        public static final Item FireCrystal=new Item();
        @GameRegistry.ObjectHolder("soil_crystal")
        public static final Item SoilCrystal=new Item();
        @GameRegistry.ObjectHolder("thunder_crystal")
        public static final Item ThunderCrystal=new Item();

        public static Item BrokenBlastArray=new Item();
        //public static Item BrokenCalcArray =new Item();
        public static Item BrokenEnderArray=new Item();
        public static Item BrokenEternalArray=new Item();
        public static Item BrokenSharpArray=new Item();
        public static Item BrokenShieldArray=new Item();
        public static Item BrokenSpaceArray=new Item();
        public static Item BrokenStableArray=new Item();

        public static Item BlastArray=new Item();
        public static Item CalcArray=new Item();
        public static Item EnderArray=new Item();
        public static Item EternalArray=new Item();
        public static Item SharpArray=new Item();
        public static Item ShieldArray=new Item();
        public static Item SpaceArray=new Item();
        public static Item StableArray=new Item();

        public static Item Clip=new Item();
        public static Item CartridgeBag=new Item();
    }
    @SubscribeEvent
    public void registerModels(){
        ModelLoader.setCustomModelResourceLocation(Items.BlastArray,0,new ModelResourceLocation(Items.BlastArray.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.CalcArray,0,new ModelResourceLocation(Items.CalcArray.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.EnderArray,0,new ModelResourceLocation(Items.EnderArray.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.EternalArray,0,new ModelResourceLocation(Items.EternalArray.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.GoldCrystal,0,new ModelResourceLocation(Items.GoldCrystal.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.WoodCrystal,0,new ModelResourceLocation(Items.WoodCrystal.getRegistryName(),"inventory"));
        ModelLoader.setCustomStateMapper(new BlockMachineBase(1), new StateMap.Builder().build());
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           if(WarhammerConfig.registerAllItems) {
               event.getRegistry().register(Items.GoldCrystal.setRegistryName(MOD_ID,"gold_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("gold_crystal"));
               event.getRegistry().register(Items.WoodCrystal.setRegistryName(MOD_ID,"wood_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("wood_crystal"));
               event.getRegistry().register(Items.WaterCrystal.setRegistryName(MOD_ID,"water_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("water_crystal"));
               event.getRegistry().register(Items.FireCrystal.setRegistryName(MOD_ID,"fire_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_crystal"));
               event.getRegistry().register(Items.SoilCrystal.setRegistryName(MOD_ID,"soil_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("soil_crystal"));
               event.getRegistry().register(Items.ThunderCrystal.setRegistryName(MOD_ID,"thunder_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("thunder_crystal"));

               event.getRegistry().register(Items.BrokenBlastArray.setRegistryName(MOD_ID,"broken_blast_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_blast_array"));
               //event.getRegistry().register(Items.BrokenCalcArray.setRegistryName(MOD_ID,"broken_calc_array"));
               event.getRegistry().register(Items.BrokenEnderArray.setRegistryName(MOD_ID,"broken_calc_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_calc_array"));
               event.getRegistry().register(Items.BrokenEternalArray.setRegistryName(MOD_ID,"broken_eternal_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_eternal_array"));
               event.getRegistry().register(Items.BrokenSharpArray.setRegistryName(MOD_ID,"broken_sharp_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_sharp_array"));
               event.getRegistry().register(Items.BrokenShieldArray.setRegistryName(MOD_ID,"broken_shield_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_shield_array"));
               event.getRegistry().register(Items.BrokenSpaceArray.setRegistryName(MOD_ID,"broken_space_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_space_array"));
               event.getRegistry().register(Items.BrokenStableArray.setRegistryName(MOD_ID,"broken_stable_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_stable_array"));

               event.getRegistry().register(Items.BlastArray.setRegistryName(MOD_ID,"blast_array").setCreativeTab(WARHAMMER).setUnlocalizedName("blast_array"));
               event.getRegistry().register(Items.CalcArray.setRegistryName(MOD_ID,"calc_array").setCreativeTab(WARHAMMER).setUnlocalizedName("calc_array"));
               event.getRegistry().register(Items.EnderArray.setRegistryName(MOD_ID,"ender_array").setCreativeTab(WARHAMMER).setUnlocalizedName("ender_array"));
               event.getRegistry().register(Items.EternalArray.setRegistryName(MOD_ID,"eternal_array").setCreativeTab(WARHAMMER).setUnlocalizedName("eternal_array"));
               event.getRegistry().register(Items.SharpArray.setRegistryName(MOD_ID,"sharp_array").setCreativeTab(WARHAMMER).setUnlocalizedName("sharp_array"));
               event.getRegistry().register(Items.ShieldArray.setRegistryName(MOD_ID,"shield_array").setCreativeTab(WARHAMMER).setUnlocalizedName("shield_array"));
               event.getRegistry().register(Items.SpaceArray.setRegistryName(MOD_ID,"space_array").setCreativeTab(WARHAMMER).setUnlocalizedName("space_array"));
               event.getRegistry().register(Items.StableArray.setRegistryName(MOD_ID,"stable_array").setCreativeTab(WARHAMMER).setUnlocalizedName("stable_array"));

               event.getRegistry().register(new HeartProtectMirror().setRegistryName(MOD_ID,"heart_protect_mirror").setCreativeTab(WARHAMMER).setUnlocalizedName("heart_protect_mirror"));
               event.getRegistry().register(new CrystalCamera(ItemArmor.ArmorMaterial.GOLD,2, EntityEquipmentSlot.HEAD).
                       setRegistryName(MOD_ID,"crystal_camera").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_camera"));
               event.getRegistry().register(new ArmorPlate().setRegistryName(MOD_ID,"armor_plate").setCreativeTab(WARHAMMER).setUnlocalizedName("armor_plate"));
               event.getRegistry().register(Items.Clip.setRegistryName(MOD_ID,"clip").setMaxDamage(12800).setCreativeTab(WARHAMMER).setUnlocalizedName("clip"));
               event.getRegistry().register(Items.CartridgeBag.setRegistryName(MOD_ID,"cartridge_bag").setMaxDamage(64000).setCreativeTab(WARHAMMER).setUnlocalizedName("cartridge_bag"));

               event.getRegistry().register(new ChainSword().setRegistryName(MOD_ID,"chain_sword").setCreativeTab(WARHAMMER).setUnlocalizedName("chain_sword"));
               event.getRegistry().register(new CrystalArmor().setRegistryName(MOD_ID,"crystal_armor").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_armor"));
               event.getRegistry().register(new ElectricHeatAxe().setRegistryName(MOD_ID,"electric_heat_axe").setCreativeTab(WARHAMMER).setUnlocalizedName("electric_heat_axe"));
               event.getRegistry().register(new FirePythonHeavyGun().setRegistryName(MOD_ID,"fire_python_heavy_gun").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_python_heavy_gun"));
               event.getRegistry().register(new LightAndFireSabre().setRegistryName(MOD_ID,"light_fire_sabre").setCreativeTab(WARHAMMER).setUnlocalizedName("light_fire_sabre"));
               event.getRegistry().register(new ShakeSabre().setRegistryName(MOD_ID,"shake_sabre").setCreativeTab(WARHAMMER).setUnlocalizedName("shake_sabre"));
               event.getRegistry().register(new SpaceRings().setRegistryName(MOD_ID,"space_ring").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring"));

               event.getRegistry().register(new OreFinderBomb().setRegistryName(MOD_ID,"ore_finder_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("ore_finder_bomb"));
               event.getRegistry().register(new FireBomb().setRegistryName(MOD_ID,"fire_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_bomb"));
               event.getRegistry().register(new FishBomb().setRegistryName(MOD_ID,"fish_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("fish_bomb"));
               event.getRegistry().register(new SkyRoarBomb().setRegistryName(MOD_ID,"sky_roar_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("sky_roar_bomb"));
               event.getRegistry().register(new VoidBomb().setRegistryName(MOD_ID,"void_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("void_bomb"));
           }
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            if(WarhammerConfig.registerAllBlocks){
                event.getRegistry().register(new BlockMachineBase(1).setRegistryName(MOD_ID,"array_etcher").setCreativeTab(WARHAMMER).setUnlocalizedName("array_etcher"));
                event.getRegistry().register(new BlockMachineBase(2).setRegistryName(MOD_ID,"biome_changer").setCreativeTab(WARHAMMER).setUnlocalizedName("biome_changer"));
                event.getRegistry().register(new BlockMachineBase(3).setRegistryName(MOD_ID,"crystal_chest").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_chest.json"));
                event.getRegistry().register(new BlockMachineBase(4).setRegistryName(MOD_ID,"crystal_converter").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_converter"));
                event.getRegistry().register(new BlockMachineBase(5).setRegistryName(MOD_ID,"crystal_generator").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_generator"));

                event.getRegistry().register(new BlockCrystals(1).setRegistryName(MOD_ID,"gold_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("gold_crystal_block"));
                event.getRegistry().register(new BlockCrystals(2).setRegistryName(MOD_ID,"wood_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("wood_crystal_block"));
                event.getRegistry().register(new BlockCrystals(3).setRegistryName(MOD_ID,"water_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("water_crystal_block"));
                event.getRegistry().register(new BlockCrystals(4).setRegistryName(MOD_ID,"fire_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_crystal_block"));
                event.getRegistry().register(new BlockCrystals(5).setRegistryName(MOD_ID,"soil_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("soil_crystal_block"));
                event.getRegistry().register(new BlockCrystals(6).setRegistryName(MOD_ID,"thunder_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("thunder_crystal_block"));
                event.getRegistry().register(Blocks.Pipeline.setRegistryName(MOD_ID,"pipeline").setResistance(1000).setHardness(10).setCreativeTab(WARHAMMER).setUnlocalizedName("pipeline"));
            }
        }
    }
}
