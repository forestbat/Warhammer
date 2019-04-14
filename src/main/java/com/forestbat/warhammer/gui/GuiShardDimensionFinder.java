package com.forestbat.warhammer.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiShardDimensionFinder extends GuiContainer {
    public GuiShardDimensionFinder(Container container){
        super(container);
    }
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(new ResourceLocation("gui_shard_dimension_finder"));
    }
}
