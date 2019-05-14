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
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
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

import java.io.IOException;

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

    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
        public static final Block PIPELINE=new Block(Material.ROCK);
        public static final Block ARRAY_ETCHER=new BlockMachineBase(1);
        public static final Block CRYSTAL_CHEST=new BlockMachineBase(2);
        public static final Block CRYSTAL_CONVERTER=new BlockMachineBase(3);
        public static final Block CRYSTAL_GENERATOR=new BlockMachineBase(4);
        public static final Block ENVIRONMENT_CONTROLLER=new BlockMachineBase(5);
        public static final Block LITTLE_CRYSTAL_BURNER=new BlockMachineBase(6);
        public static final Block LITTLE_CRYSTAL_COLLIDER=new BlockMachineBase(7);
        public static final Block PIPES_CONNECTOR=new BlockMachineBase(8);
        public static final Block PLAYER_REINFOCER=new BlockMachineBase(9);
        public static final Block REACTOR_CONTROLLER=new BlockMachineBase(10);
        public static final Block SHARD_DIMENSION_FINDER=new BlockMachineBase(11);
        public static final Block SHIELD_GENERATOR=new BlockMachineBase(12);
        public static final Block SPACE_RING_READER=new BlockMachineBase(13);
        public static final Block TOOLS_FORGER=new BlockMachineBase(14);
        public static final Block TURRET=new BlockMachineBase(15);

        public static final Block GOLD_CRYSTAL_BLOCK=new BlockCrystals(1);
        public static final Block WOOD_CRYSTAL_BLOCK=new BlockCrystals(2);
        public static final Block WATER_CRYSTAL_BLOCK=new BlockCrystals(3);
        public static final Block FIRE_CRYSTAL_BLOCK=new BlockCrystals(4);
        public static final Block SOIL_CRYSTAL_BLOCK=new BlockCrystals(5);
        public static final Block THUNDER_CRYSTAL_BLOCK=new BlockMachineBase(6);
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
        @GameRegistry.ObjectHolder("gold_crystal")
        public static final Item GOLD_CRYSTAL =new Item();
        @GameRegistry.ObjectHolder("wood_crystal")
        public static final Item WOOD_CRYSTAL =new Item();
        @GameRegistry.ObjectHolder("water_crystal")
        public static final Item WATER_CRYSTAL =new Item();
        @GameRegistry.ObjectHolder("fire_crystal")
        public static final Item FIRE_CRYSTAL =new Item();
        @GameRegistry.ObjectHolder("soil_crystal")
        public static final Item SOIL_CRYSTAL =new Item();
        @GameRegistry.ObjectHolder("thunder_crystal")
        public static final Item THUNDER_CRYSTAL =new Item();

        @GameRegistry.ObjectHolder("broken_blast_array")
        public static final Item BROKEN_BLAST_ARRAY =new Item();
        //@GameRegistry.ObjectHolder("broken_calc_array")
        //public static final Item BROKEN_CALC_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_ender_array")
        public static final Item BROKEN_ENDER_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_eternal_array")
        public static final Item BROKEN_ETERNAL_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_sharp_array")
        public static final Item BROKEN_SHARP_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_shield_array")
        public static final Item BROKEN_SHIELD_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_space_array")
        public static final Item BROKEN_SPACE_ARRAY =new Item();
        @GameRegistry.ObjectHolder("broken_stable_array")
        public static final Item BROKEN_STABLE_ARRAY =new Item();

        @GameRegistry.ObjectHolder("blast_array")
        public static final Item BLAST_ARRAY =new Item();
        @GameRegistry.ObjectHolder("calc_array")
        public static final Item CALC_ARRAY =new Item();
        @GameRegistry.ObjectHolder("ender_array")
        public static final Item ENDER_ARRAY =new Item();
        @GameRegistry.ObjectHolder("eternal_array")
        public static final Item ETERNAL_ARRAY =new Item();
        @GameRegistry.ObjectHolder("sharp_array")
        public static final Item SHARP_ARRAY =new Item();
        @GameRegistry.ObjectHolder("shield_array")
        public static final Item SHIELD_ARRAY =new Item();
        @GameRegistry.ObjectHolder("shield_array")
        public static final Item SPACE_ARRAY =new Item();
        @GameRegistry.ObjectHolder("stable_array")
        public static final Item STABLE_ARRAY =new Item();

        @GameRegistry.ObjectHolder("clip")
        public static final Item CLIP =new Item();
        @GameRegistry.ObjectHolder("cartridge_bag")
        public static final Item CARTRIDGE_BAG =new Item();
        @GameRegistry.ObjectHolder("crystal_camera")
        public static final Item CRYSTAL_CAMERA=new CrystalCamera(ItemArmor.ArmorMaterial.GOLD,2, EntityEquipmentSlot.HEAD);
        @GameRegistry.ObjectHolder("heart_protect_mirror")
        public static final Item HEART_PROTECT_MIRROR=new HeartProtectMirror();
        @GameRegistry.ObjectHolder("armor_plate")
        public static final Item ARMOR_PLATE=new ArmorPlate();

        @GameRegistry.ObjectHolder("chain_sword")
        public static final Item CHAIN_SWORD=new ChainSword();
        @GameRegistry.ObjectHolder("crystal_armor")
		public static final Item CRYSTAL_ARMOR=new CrystalArmor();
        @GameRegistry.ObjectHolder("electric_heat_exe")
		public static final Item ELECTRIC_HEAT_EXE=new ElectricHeatAxe();
        @GameRegistry.ObjectHolder("fire_python_heavy_exe")
        public static final Item FIRE_PYTHON_HEAVY_GUN=new FirePythonHeavyGun();
        @GameRegistry.ObjectHolder("light_and_fire_sabre")
		public static final Item LIGHT_AND_FIRE_SABRE=new LightAndFireSabre();
        @GameRegistry.ObjectHolder("shake_sabre")
		public static final Item SHAKE_SABRE=new ShakeSabre();
        @GameRegistry.ObjectHolder("space_ring_tier1")
		public static final Item SPACE_RING_TIER_1=new SpaceRings(1);
		@GameRegistry.ObjectHolder("space_ring_tier2")
		public static final Item SPACE_RING_TIER_2=new SpaceRings(2);
		@GameRegistry.ObjectHolder("space_ring_tier3")
		public static final Item SPACE_RING_TIER_3=new SpaceRings(3);
		@GameRegistry.ObjectHolder("space_ring_tier4")
		public static final Item SPACE_RING_TIER_4=new SpaceRings(4);
		@GameRegistry.ObjectHolder("space_ring_tier5")
		public static final Item SPACE_RING_TIER_5=new SpaceRings(5);

		@GameRegistry.ObjectHolder("fire_bomb")
        public static final Item FIRE_BOMB=new FireBomb();
		@GameRegistry.ObjectHolder("fish_bomb")
        public static final Item FISH_BOMB=new FishBomb();
		@GameRegistry.ObjectHolder("ore_finder_bomb")
        public static final Item ORE_FIND_BOMB=new OreFinderBomb();
		@GameRegistry.ObjectHolder("sky_roar_bomb")
        public static final Item SKY_ROAR_BOMB=new SkyRoarBomb();
		@GameRegistry.ObjectHolder("void_bomb")
        public static final Item VOID_BOMB=new VoidBomb();

		@GameRegistry.ObjectHolder("array_etcher")
        public static final ItemBlock ARRAY_ETCHER=new ItemBlock(Blocks.ARRAY_ETCHER);
		@GameRegistry.ObjectHolder("crystal_chest")
        public static final ItemBlock CRYSTAL_CHEST=new ItemBlock(Blocks.CRYSTAL_CHEST);
		@GameRegistry.ObjectHolder("crystal_converter")
        public static final ItemBlock CRYSTAL_CONVERTER=new ItemBlock(Blocks.CRYSTAL_CONVERTER);
		@GameRegistry.ObjectHolder("crystal_generator")
        public static final ItemBlock CRYSTAL_GENERATOR=new ItemBlock(Blocks.CRYSTAL_GENERATOR);
		@GameRegistry.ObjectHolder("environment_controller")
        public static final ItemBlock ENVIRONMENT_CONTROLLER=new ItemBlock(Blocks.ENVIRONMENT_CONTROLLER);
		@GameRegistry.ObjectHolder("little_crystal_burner")
        public static final ItemBlock LITTLE_CRYSTAL_BURNER=new ItemBlock(Blocks.LITTLE_CRYSTAL_BURNER);
		@GameRegistry.ObjectHolder("little_crystal_collider")
        public static final ItemBlock LITTLE_CRYSTAL_COLLIDER=new ItemBlock(Blocks.LITTLE_CRYSTAL_COLLIDER);
		@GameRegistry.ObjectHolder("pipes_generator")
        public static final ItemBlock PIPES_CONNECTOR=new ItemBlock(Blocks.PIPES_CONNECTOR);
		@GameRegistry.ObjectHolder("player_reinforcer")
        public static final ItemBlock PLAYER_REINFORCER=new ItemBlock(Blocks.PLAYER_REINFOCER);
		@GameRegistry.ObjectHolder("shard_dimension_finder")
        public static final ItemBlock SHARD_DIMENSION_FINDER=new ItemBlock(Blocks.SHARD_DIMENSION_FINDER);
		@GameRegistry.ObjectHolder("shield_generator")
        public static final ItemBlock SHIELD_GENERATOR=new ItemBlock(Blocks.SHIELD_GENERATOR);
		@GameRegistry.ObjectHolder("tools_forger")
        public static final ItemBlock SPACE_RING_READER=new ItemBlock(Blocks.SPACE_RING_READER);
		@GameRegistry.ObjectHolder("turret")
        public static final ItemBlock TURRET=new ItemBlock(Blocks.TURRET);
    }

    @SubscribeEvent
    public void registerModels(){
        ModelLoader.setCustomModelResourceLocation(Items.BLAST_ARRAY,0,new ModelResourceLocation(Items.BLAST_ARRAY.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.CALC_ARRAY,0,new ModelResourceLocation(Items.CALC_ARRAY.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.ENDER_ARRAY,0,new ModelResourceLocation(Items.ENDER_ARRAY.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.ETERNAL_ARRAY,0,new ModelResourceLocation(Items.ETERNAL_ARRAY.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.GOLD_CRYSTAL,0,new ModelResourceLocation(Items.GOLD_CRYSTAL.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.WOOD_CRYSTAL,0,new ModelResourceLocation(Items.WOOD_CRYSTAL.getRegistryName(),"inventory"));
        ModelLoader.setCustomStateMapper(new BlockMachineBase(1), new StateMap.Builder().build());
    }

    @SubscribeEvent
    public void registerModels(ModelBakeEvent event){
        try {
            final ModelResourceLocation bakedLocation = new ModelResourceLocation(
               Items.ARMOR_PLATE.getRegistryName(), "normal");
            final IBakedModel baked = event.getModelRegistry().getObject(bakedLocation);
            event.getModelRegistry().putObject(bakedLocation, baked);

            final ModelResourceLocation itemLocation = new ModelResourceLocation(
                new ResourceLocation("huntingdim", "frame"), "inventory");
            final IBakedModel bakedItem = event.getModelRegistry().getObject(itemLocation);
            event.getModelRegistry().putObject(itemLocation, bakedItem);
        }catch(Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    //@SubscribeEvent
    public static void easyItemRegister(RegistryEvent.Register<Item> registryEvent,Item item,String rlname){
        registryEvent.getRegistry().register(item.setRegistryName(rlname).setCreativeTab(WARHAMMER).setUnlocalizedName(rlname));
    }

    //@SubscribeEvent
    public static void easyBlockRegister(RegistryEvent.Register<Block> registryEvent,Block block,String rlname){
        registryEvent.getRegistry().register(block.setRegistryName(rlname).setCreativeTab(WARHAMMER).setUnlocalizedName(rlname));
    }
    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           if(WarhammerConfig.registerAllItems) {
               Warhammer.easyItemRegister(event,Items.GOLD_CRYSTAL,"gold_crystal");
               Warhammer.easyItemRegister(event,Items.WOOD_CRYSTAL,"wood_crystal");
               Warhammer.easyItemRegister(event,Items.WATER_CRYSTAL,"water_crystal");
               Warhammer.easyItemRegister(event,Items.FIRE_CRYSTAL,"fire_crystal");
               Warhammer.easyItemRegister(event,Items.SOIL_CRYSTAL,"soil_crystal");
               Warhammer.easyItemRegister(event,Items.THUNDER_CRYSTAL,"thunder_crystal");

               Warhammer.easyItemRegister(event,Items.BROKEN_BLAST_ARRAY,"broken_blast_array");
               //Warhammer.easyItemRegister(event,Items.BROKEN_CALC_ARRAY,"broken_calc_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_ENDER_ARRAY,"broken_calc_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_ETERNAL_ARRAY,"broken_eternal_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_SHARP_ARRAY,"broken_sharp_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_SHIELD_ARRAY,"broken_shield_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_SPACE_ARRAY,"broken_space_array");
               Warhammer.easyItemRegister(event,Items.BROKEN_STABLE_ARRAY,"broken_stable_array");

               Warhammer.easyItemRegister(event,Items.BLAST_ARRAY,"blast_array");
               Warhammer.easyItemRegister(event,Items.CALC_ARRAY,"calc_array");
               Warhammer.easyItemRegister(event,Items.ENDER_ARRAY,"ender_array");
               Warhammer.easyItemRegister(event,Items.ETERNAL_ARRAY,"eternal_array");
               Warhammer.easyItemRegister(event,Items.SHARP_ARRAY,"sharp_array");
               Warhammer.easyItemRegister(event,Items.SHIELD_ARRAY,"shield_array");
               Warhammer.easyItemRegister(event,Items.SPACE_ARRAY,"space_array");
               Warhammer.easyItemRegister(event,Items.STABLE_ARRAY,"stable_array");

               Warhammer.easyItemRegister(event,Items.HEART_PROTECT_MIRROR,"heart_protect_mirror");
               Warhammer.easyItemRegister(event,Items.CRYSTAL_CAMERA,"crystal_camera");
               Warhammer.easyItemRegister(event,Items.ARMOR_PLATE,"armor_plate");
               Warhammer.easyItemRegister(event,Items.CLIP.setMaxDamage(12800),"clip");
               Warhammer.easyItemRegister(event,Items.CARTRIDGE_BAG.setMaxDamage(64000),"cartridge_bag");

               Warhammer.easyItemRegister(event,Items.CHAIN_SWORD,"chain_sword");
               Warhammer.easyItemRegister(event,Items.CRYSTAL_ARMOR,"crystal_armor");
               Warhammer.easyItemRegister(event,Items.ELECTRIC_HEAT_EXE,"electric_heat_axe");
               Warhammer.easyItemRegister(event,Items.FIRE_PYTHON_HEAVY_GUN,"fire_python_heavy_gun");
               Warhammer.easyItemRegister(event,Items.LIGHT_AND_FIRE_SABRE,"light_fire_sabre");
               Warhammer.easyItemRegister(event,Items.SHAKE_SABRE,"shake_sabre");
               Warhammer.easyItemRegister(event,Items.SPACE_RING_TIER_1,"space_ring_tier1");
			   Warhammer.easyItemRegister(event,Items.SPACE_RING_TIER_2,"space_ring_tier2");
			   Warhammer.easyItemRegister(event,Items.SPACE_RING_TIER_3,"space_ring_tier3");
			   Warhammer.easyItemRegister(event,Items.SPACE_RING_TIER_4,"space_ring_tier4");
			   Warhammer.easyItemRegister(event,Items.SPACE_RING_TIER_5,"space_ring_tier5");

               Warhammer.easyItemRegister(event,Items.ORE_FIND_BOMB,"ore_finder_bomb");
               Warhammer.easyItemRegister(event,Items.FIRE_BOMB,"fire_bomb");
               Warhammer.easyItemRegister(event,Items.FISH_BOMB,"fish_bomb");
               Warhammer.easyItemRegister(event,Items.SKY_ROAR_BOMB,"sky_roar_bomb");
               Warhammer.easyItemRegister(event,Items.VOID_BOMB,"void_bomb");

               Warhammer.easyItemRegister(event,Items.ARRAY_ETCHER,"array_etcher");
               Warhammer.easyItemRegister(event,Items.CRYSTAL_CHEST,"crystal_chest");
               Warhammer.easyItemRegister(event,Items.CRYSTAL_CONVERTER,"crystal_converter");
               Warhammer.easyItemRegister(event,Items.CRYSTAL_GENERATOR,"crystal_generator");
               Warhammer.easyItemRegister(event,Items.ENVIRONMENT_CONTROLLER,"environment_controller");
               Warhammer.easyItemRegister(event,Items.LITTLE_CRYSTAL_BURNER,"little_crystal_burner");
               Warhammer.easyItemRegister(event,Items.LITTLE_CRYSTAL_COLLIDER,"little_crystal_collider");
               Warhammer.easyItemRegister(event,Items.PIPES_CONNECTOR,"pipes_connector");
               Warhammer.easyItemRegister(event,Items.PLAYER_REINFORCER,"player_reinforcer");
               Warhammer.easyItemRegister(event,Items.SHARD_DIMENSION_FINDER, "shard_dimension_finder");
               Warhammer.easyItemRegister(event,Items.SHIELD_GENERATOR,"shield_generator");
               Warhammer.easyItemRegister(event,Items.SPACE_RING_READER,"space_ring_reader");
               Warhammer.easyItemRegister(event,Items.TURRET,"turret");
           }
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            if(WarhammerConfig.registerAllBlocks){
                Warhammer.easyBlockRegister(event,Blocks.ARRAY_ETCHER,"array_etcher");
                Warhammer.easyBlockRegister(event,Blocks.CRYSTAL_CHEST,"crystal_chest");
                Warhammer.easyBlockRegister(event,Blocks.CRYSTAL_CONVERTER,"crystal_converter");
                Warhammer.easyBlockRegister(event,Blocks.CRYSTAL_GENERATOR,"crystal_generator");
                Warhammer.easyBlockRegister(event,Blocks.ENVIRONMENT_CONTROLLER,"environment_controller");
                Warhammer.easyBlockRegister(event,Blocks.LITTLE_CRYSTAL_BURNER,"little_crystal_burner");
                Warhammer.easyBlockRegister(event,Blocks.LITTLE_CRYSTAL_COLLIDER,"little_crystal_collider");
                Warhammer.easyBlockRegister(event,Blocks.PIPES_CONNECTOR,"pipes_connector");
                Warhammer.easyBlockRegister(event,Blocks.PLAYER_REINFOCER,"player_reinforcer");
				Warhammer.easyBlockRegister(event,Blocks.REACTOR_CONTROLLER,"reactor_controller");
				Warhammer.easyBlockRegister(event,Blocks.SHARD_DIMENSION_FINDER,"shard_dimension_finder");
				Warhammer.easyBlockRegister(event,Blocks.SHIELD_GENERATOR,"shield_generator");
				Warhammer.easyBlockRegister(event,Blocks.SPACE_RING_READER,"space_ring_reader");
				Warhammer.easyBlockRegister(event,Blocks.TOOLS_FORGER,"tools_forger");
				Warhammer.easyBlockRegister(event,Blocks.TURRET,"turret");

				Warhammer.easyBlockRegister(event,Blocks.GOLD_CRYSTAL_BLOCK,"gold_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.WOOD_CRYSTAL_BLOCK,"wood_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.WATER_CRYSTAL_BLOCK,"water_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.FIRE_CRYSTAL_BLOCK,"fire_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.SOIL_CRYSTAL_BLOCK,"soil_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.THUNDER_CRYSTAL_BLOCK,"thunder_crystal_block");
                Warhammer.easyBlockRegister(event,Blocks.PIPELINE.setResistance(1000).setHardness(10),"pipeline");
            }
        }
    }
}
