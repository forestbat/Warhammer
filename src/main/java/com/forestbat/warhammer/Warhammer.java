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
import net.minecraft.item.ItemBlock;
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
        @GameRegistry.ObjectHolder("broken_calc_array")
        public static final Item BROKEN_CALC_ARRAY =new Item();
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

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           if(WarhammerConfig.registerAllItems) {
               event.getRegistry().register(Items.GOLD_CRYSTAL.setRegistryName(MOD_ID,"gold_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("gold_crystal"));
               event.getRegistry().register(Items.WOOD_CRYSTAL.setRegistryName(MOD_ID,"wood_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("wood_crystal"));
               event.getRegistry().register(Items.WATER_CRYSTAL.setRegistryName(MOD_ID,"water_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("water_crystal"));
               event.getRegistry().register(Items.FIRE_CRYSTAL.setRegistryName(MOD_ID,"fire_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_crystal"));
               event.getRegistry().register(Items.SOIL_CRYSTAL.setRegistryName(MOD_ID,"soil_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("soil_crystal"));
               event.getRegistry().register(Items.THUNDER_CRYSTAL.setRegistryName(MOD_ID,"thunder_crystal").setCreativeTab(WARHAMMER).setUnlocalizedName("thunder_crystal"));

               event.getRegistry().register(Items.BROKEN_BLAST_ARRAY.setRegistryName(MOD_ID,"broken_blast_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_blast_array"));
               event.getRegistry().register(Items.BROKEN_CALC_ARRAY.setRegistryName(MOD_ID,"broken_calc_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_calc_array"));
               event.getRegistry().register(Items.BROKEN_ENDER_ARRAY.setRegistryName(MOD_ID,"broken_calc_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_calc_array"));
               event.getRegistry().register(Items.BROKEN_ETERNAL_ARRAY.setRegistryName(MOD_ID,"broken_eternal_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_eternal_array"));
               event.getRegistry().register(Items.BROKEN_SHARP_ARRAY.setRegistryName(MOD_ID,"broken_sharp_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_sharp_array"));
               event.getRegistry().register(Items.BROKEN_SHIELD_ARRAY.setRegistryName(MOD_ID,"broken_shield_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_shield_array"));
               event.getRegistry().register(Items.BROKEN_SPACE_ARRAY.setRegistryName(MOD_ID,"broken_space_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_space_array"));
               event.getRegistry().register(Items.BROKEN_STABLE_ARRAY.setRegistryName(MOD_ID,"broken_stable_array").setCreativeTab(WARHAMMER).setUnlocalizedName("broken_stable_array"));

               event.getRegistry().register(Items.BLAST_ARRAY.setRegistryName(MOD_ID,"blast_array").setCreativeTab(WARHAMMER).setUnlocalizedName("blast_array"));
               event.getRegistry().register(Items.CALC_ARRAY.setRegistryName(MOD_ID,"calc_array").setCreativeTab(WARHAMMER).setUnlocalizedName("calc_array"));
               event.getRegistry().register(Items.ENDER_ARRAY.setRegistryName(MOD_ID,"ender_array").setCreativeTab(WARHAMMER).setUnlocalizedName("ender_array"));
               event.getRegistry().register(Items.ETERNAL_ARRAY.setRegistryName(MOD_ID,"eternal_array").setCreativeTab(WARHAMMER).setUnlocalizedName("eternal_array"));
               event.getRegistry().register(Items.SHARP_ARRAY.setRegistryName(MOD_ID,"sharp_array").setCreativeTab(WARHAMMER).setUnlocalizedName("sharp_array"));
               event.getRegistry().register(Items.SHIELD_ARRAY.setRegistryName(MOD_ID,"shield_array").setCreativeTab(WARHAMMER).setUnlocalizedName("shield_array"));
               event.getRegistry().register(Items.SPACE_ARRAY.setRegistryName(MOD_ID,"space_array").setCreativeTab(WARHAMMER).setUnlocalizedName("space_array"));
               event.getRegistry().register(Items.STABLE_ARRAY.setRegistryName(MOD_ID,"stable_array").setCreativeTab(WARHAMMER).setUnlocalizedName("stable_array"));

               event.getRegistry().register(new HeartProtectMirror().setRegistryName(MOD_ID,"heart_protect_mirror").setCreativeTab(WARHAMMER).setUnlocalizedName("heart_protect_mirror"));
               event.getRegistry().register(new CrystalCamera(ItemArmor.ArmorMaterial.GOLD,2, EntityEquipmentSlot.HEAD).
                       setRegistryName(MOD_ID,"crystal_camera").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_camera"));
               event.getRegistry().register(new ArmorPlate().setRegistryName(MOD_ID,"armor_plate").setCreativeTab(WARHAMMER).setUnlocalizedName("armor_plate"));
               event.getRegistry().register(Items.CLIP.setRegistryName(MOD_ID,"clip").setMaxDamage(12800).setCreativeTab(WARHAMMER).setUnlocalizedName("clip"));
               event.getRegistry().register(Items.CARTRIDGE_BAG.setRegistryName(MOD_ID,"cartridge_bag").setMaxDamage(64000).setCreativeTab(WARHAMMER).setUnlocalizedName("cartridge_bag"));

               event.getRegistry().register(Items.CHAIN_SWORD.setRegistryName(MOD_ID,"chain_sword").setCreativeTab(WARHAMMER).setUnlocalizedName("chain_sword"));
               event.getRegistry().register(Items.CRYSTAL_ARMOR.setRegistryName(MOD_ID,"crystal_armor").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_armor"));
               event.getRegistry().register(Items.ELECTRIC_HEAT_EXE.setRegistryName(MOD_ID,"electric_heat_axe").setCreativeTab(WARHAMMER).setUnlocalizedName("electric_heat_axe"));
               event.getRegistry().register(Items.FIRE_PYTHON_HEAVY_GUN.setRegistryName(MOD_ID,"fire_python_heavy_gun").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_python_heavy_gun"));
               event.getRegistry().register(Items.LIGHT_AND_FIRE_SABRE.setRegistryName(MOD_ID,"light_fire_sabre").setCreativeTab(WARHAMMER).setUnlocalizedName("light_fire_sabre"));
               event.getRegistry().register(Items.SHAKE_SABRE.setRegistryName(MOD_ID,"shake_sabre").setCreativeTab(WARHAMMER).setUnlocalizedName("shake_sabre"));
               event.getRegistry().register(Items.SPACE_RING_TIER_1.setRegistryName(MOD_ID,"space_ring_tier1").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_tier1"));
			   event.getRegistry().register(Items.SPACE_RING_TIER_2.setRegistryName(MOD_ID,"space_ring_tier2").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_tier2"));
			   event.getRegistry().register(Items.SPACE_RING_TIER_3.setRegistryName(MOD_ID,"space_ring_tier3").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_tier4"));
			   event.getRegistry().register(Items.SPACE_RING_TIER_4.setRegistryName(MOD_ID,"space_ring_tier4").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_tier4"));
			   event.getRegistry().register(Items.SPACE_RING_TIER_5.setRegistryName(MOD_ID,"space_ring_tier5").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_tier5"));

               event.getRegistry().register(new OreFinderBomb().setRegistryName(MOD_ID,"ore_finder_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("ore_finder_bomb"));
               event.getRegistry().register(new FireBomb().setRegistryName(MOD_ID,"fire_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_bomb"));
               event.getRegistry().register(new FishBomb().setRegistryName(MOD_ID,"fish_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("fish_bomb"));
               event.getRegistry().register(new SkyRoarBomb().setRegistryName(MOD_ID,"sky_roar_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("sky_roar_bomb"));
               event.getRegistry().register(new VoidBomb().setRegistryName(MOD_ID,"void_bomb").setCreativeTab(WARHAMMER).setUnlocalizedName("void_bomb"));

               event.getRegistry().register(new ItemBlock(Blocks.ARRAY_ETCHER.setRegistryName(MOD_ID,"array_etcher").setCreativeTab(WARHAMMER).setUnlocalizedName("array_etcher")));
           }
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            if(WarhammerConfig.registerAllBlocks){
                event.getRegistry().register(Blocks.ARRAY_ETCHER.setRegistryName(MOD_ID,"array_etcher").setCreativeTab(WARHAMMER).setUnlocalizedName("array_etcher"));
                event.getRegistry().register(Blocks.CRYSTAL_CHEST.setRegistryName(MOD_ID,"crystal_chest").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_chest.json"));
                event.getRegistry().register(Blocks.CRYSTAL_CONVERTER.setRegistryName(MOD_ID,"crystal_converter").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_converter"));
                event.getRegistry().register(Blocks.CRYSTAL_GENERATOR.setRegistryName(MOD_ID,"crystal_generator").setCreativeTab(WARHAMMER).setUnlocalizedName("crystal_generator"));
                event.getRegistry().register(Blocks.ENVIRONMENT_CONTROLLER.setRegistryName(MOD_ID,"environment_controller").setCreativeTab(WARHAMMER).setUnlocalizedName("environment_controller"));
                event.getRegistry().register(Blocks.LITTLE_CRYSTAL_BURNER.setRegistryName(MOD_ID,"little_crystal_burner").setCreativeTab(WARHAMMER).setUnlocalizedName("little_crystal_burner"));
                event.getRegistry().register(Blocks.LITTLE_CRYSTAL_COLLIDER.setRegistryName(MOD_ID,"little_crystal_collider").setCreativeTab(WARHAMMER).setUnlocalizedName("little_crystal_collider"));
                event.getRegistry().register(Blocks.PIPES_CONNECTOR.setRegistryName(MOD_ID,"pipes_connector").setCreativeTab(WARHAMMER).setUnlocalizedName("pipes_connector"));
                event.getRegistry().register(Blocks.PLAYER_REINFOCER.setRegistryName(MOD_ID,"player_reinforcer").setCreativeTab(WARHAMMER).setUnlocalizedName("player_reinforcer"));
				event.getRegistry().register(Blocks.REACTOR_CONTROLLER.setRegistryName(MOD_ID,"reactor_controller").setCreativeTab(WARHAMMER).setUnlocalizedName("reactor_controller"));
				event.getRegistry().register(Blocks.SHARD_DIMENSION_FINDER.setRegistryName(MOD_ID,"shard_dimension_finder").setCreativeTab(WARHAMMER).setUnlocalizedName("shard_dimension_finder"));
				event.getRegistry().register(Blocks.SHIELD_GENERATOR.setRegistryName(MOD_ID,"shield_generator").setCreativeTab(WARHAMMER).setUnlocalizedName("shield_generator"));
				event.getRegistry().register(Blocks.SPACE_RING_READER.setRegistryName(MOD_ID,"space_ring_reader").setCreativeTab(WARHAMMER).setUnlocalizedName("space_ring_reader"));
				event.getRegistry().register(Blocks.TOOLS_FORGER.setRegistryName(MOD_ID,"tools_forger").setCreativeTab(WARHAMMER).setUnlocalizedName("tools_forger"));
				event.getRegistry().register(Blocks.TURRET.setRegistryName(MOD_ID,"turret").setCreativeTab(WARHAMMER).setUnlocalizedName("turret"));

				event.getRegistry().register(Blocks.GOLD_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"gold_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("gold_crystal_block"));
                event.getRegistry().register(Blocks.WOOD_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"wood_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("wood_crystal_block"));
                event.getRegistry().register(Blocks.WATER_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"water_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("water_crystal_block"));
                event.getRegistry().register(Blocks.FIRE_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"fire_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("fire_crystal_block"));
                event.getRegistry().register(Blocks.SOIL_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"soil_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("soil_crystal_block"));
                event.getRegistry().register(Blocks.THUNDER_CRYSTAL_BLOCK.setRegistryName(MOD_ID,"thunder_crystal_block").setCreativeTab(WARHAMMER).setUnlocalizedName("thunder_crystal_block"));
                event.getRegistry().register(Blocks.PIPELINE.setRegistryName(MOD_ID,"pipeline").setResistance(1000).setHardness(10).setCreativeTab(WARHAMMER).setUnlocalizedName("pipeline"));
            }
        }
    }
}
