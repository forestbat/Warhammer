package com.forestbat.warhammer.configs;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import static com.forestbat.warhammer.Warhammer.MOD_ID;
import static com.forestbat.warhammer.Warhammer.MOD_NAME;

@Config(modid = MOD_ID,name = MOD_NAME,type = Config.Type.INSTANCE)
public class WarhammerConfig {
        //@Config.Comment(value = "Conversion rate among different energy")
        public static int WithIC2 = 25;
        public static int WithRF = 100;
        public static int WithAE = 50;
        public static int WithMana = 10;
        public static int WithMJ = 10;
        public static int WithTesla = 100;
        public static int withUI = 100;

        public static boolean registerAllItems = true;
        public static boolean registerAllBlocks = true;

        public static int DEFAULT_MANAGE_STORE = 100000;
        public static int GENERATION_TICKS = 50;
        public static int DIMENSION_DISCOVER_TICKS = 1000;
        public static int DIMENSION_BORDER_LENGTH = 20000;
        public static int DIMENSION_AMOUNT_CAN_FOUND=120;

        public static boolean NO_VOID = true;
        public static boolean GET_FISH_FROM_LOG = false;

        public static String ADVANCEMENT_REWARD_ITEM = "diamond";
        public static int ADVANCEMENT_REWARD_AMOUNT = 2;

        public static float BOMB_FLIGHT_VELOCITY = 2;

        public static int KEY_LEFT_HAND = Keyboard.KEY_K;

    public void register() {
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
    }
    @SubscribeEvent
    public void syncConfig(ConfigChangedEvent configChangedEvent){
        if(configChangedEvent.getModID().equals(MOD_ID))
            ConfigManager.sync(MOD_ID,Config.Type.INSTANCE);
    }
}
