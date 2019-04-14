package com.forestbat.warhammer.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class Button extends WidgetBase<Button>{
    public static boolean pressed=false;
    private static List<ButtonEvent> buttonEvents=null;
    public GuiContainer guiContainer;
    public Button(Minecraft mc, Gui gui){
        super(mc,gui);
    }

    @Override
    public Button setBackground(ResourceLocation bg) {
        return super.setBackground(bg);
    }

    @Override
    public WidgetBase<?> mouseClick(int x, int y, int button) {
        if(isEnabledAndVisible()) {
            pressed = true;
            return this;
        }
        return null;
    }
    public void mouseRelease(int x, int y, int button) {
        super.mouseRelease(x, y, button);
        if (pressed) {
            pressed = false;
            if (isEnabledAndVisible()) {
                fireButtonEvents();
            }
        }
    }
    public Button addButtonEvent(ButtonEvent event){
        buttonEvents= Lists.newArrayList();
        buttonEvents.add(event);
        return this;
    }
    public void removeButtonEvent(ButtonEvent event){
        if(buttonEvents!=null)
            buttonEvents.remove(event);
    }
    private void fireButtonEvents() {
        fireChannelEvents();
        if (buttonEvents != null) {
            for (ButtonEvent event : buttonEvents) {
                event.buttonClicked(this);
            }
        }
    }
    public void draw(int x, int y) {
        if (!visible) {
            return;
        }
        int xx = x + bounds.getX();
        int yy = y + bounds.getY();

        if (isEnabledAndVisible()) {
            if (pressed) {
                drawStyledBoxSelected(guiContainer, xx, yy, xx + bounds.getWidth() - 1, yy + bounds.getHeight() - 1);
            } else if (isHovering()) {
                drawStyledBoxHovering(guiContainer, xx, yy, xx + bounds.getWidth() - 1, yy + bounds.getHeight() - 1);
            } else {
                drawStyledBoxNormal(guiContainer, xx, yy, xx + bounds.getWidth() - 1, yy + bounds.getHeight() - 1);
            }
        } else {
            drawStyledBoxDisabled(guiContainer, xx, yy, xx + bounds.getWidth() - 1, yy + bounds.getHeight() - 1);
        }
    }
    public interface ButtonEvent{
        void buttonClicked(WidgetBase widget);
    }

}
