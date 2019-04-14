package com.forestbat.warhammer.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiIntroduce extends GuiScreen {
    public GuiIntroduce(){
        setGuiSize(176,166);
    }

    @Override
    public void initGui() {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("gui\book_background"));
        Button buttonLeft=new Button(mc,this);
        buttonLeft.draw(10,150);
        Button buttonRight=new Button(mc,this);
        buttonRight.draw(150,150);
    }
}
