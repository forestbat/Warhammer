package com.forestbat.warhammer.gui;

import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class EnergyBar extends WidgetBase<EnergyBar> {
    public static final String TYPE_ENERGY_BAR = "energybar";
    
    public static final boolean DEFAULT_HORIZONTAL = false;
    public static final boolean DEFAULT_SHOWTEXT = true;
    public static final boolean DEFAULT_SHOWRFPERTICK = false;
    
    private long value;
    private long maxValue;
    private Integer energyOnColor = null;
    private Integer energyOffColor = null;
    private Integer spacerColor = null;
    private Integer textColor = null;
    private boolean horizontal = DEFAULT_HORIZONTAL;
    private IEnergyStorage handler = null;
    private boolean showText = DEFAULT_SHOWTEXT;
    private boolean showRfPerTick = DEFAULT_SHOWRFPERTICK;
    private long rfPerTick = 0;
    
    public EnergyBar(Minecraft mc, Gui gui) {
        super(mc, gui);
    }
    
    public EnergyBar setHorizontal() {
        horizontal = true;
        return this;
    }
    
    public boolean isHorizontal() {
        return horizontal;
    }
    
    public EnergyBar setHandler(IEnergyStorage handler) {
        this.handler = handler;
        return this;
    }
    
    public IEnergyStorage getHandler() {
        return handler;
    }
    
    public EnergyBar setVertical() {
        horizontal = false;
        return this;
    }
    
    public boolean isVertical() {
        return !horizontal;
    }
    
    @Override
    public List<String> getTooltips() {
        if (tooltips == null) {
            String s = getValue() + " / " + getMaxValue();
            List<String> tt = new ArrayList<>();
            tt.add(s);
            return tt;
        } else {
            return tooltips;
        }
    }
    
    public boolean isShowText() {
        return showText;
    }
    
    public EnergyBar setShowText(boolean showText) {
        this.showText = showText;
        return this;
    }
    
    public boolean isShowRfPerTick() {
        return showRfPerTick;
    }
    
    public EnergyBar setShowRfPerTick(boolean showRfPerTick) {
        this.showRfPerTick = showRfPerTick;
        return this;
    }
    
    public long getRfPerTick() {
        return rfPerTick;
    }
    
    public EnergyBar setRfPerTick(long rfPerTick) {
        this.rfPerTick = rfPerTick;
        return this;
    }
    
    public long getValue() {
        if (handler != null) {
            return handler.getEnergyStored();
        }
        return value;
    }
    
    public EnergyBar setValue(long value) {
        this.value = value;
        return this;
    }
    
    public long getMaxValue() {
        if (handler != null) {
            return handler.getMaxEnergyStored();
        }
        return maxValue;
    }
    
    public EnergyBar setMaxValue(long maxValue) {
        this.maxValue = maxValue;
        return this;
    }
    
    public int getEnergyOnColor() {
        return energyOnColor == null ? WidgetBase.colorEnergyBarHighEnergy : energyOnColor;
    }
    
    public EnergyBar setEnergyOnColor(int energyOnColor) {
        this.energyOnColor = energyOnColor;
        return this;
    }
    
    public int getEnergyOffColor() {
        return energyOffColor == null ? WidgetBase.colorEnergyBarLowEnergy : energyOffColor;
    }
    
    public EnergyBar setEnergyOffColor(int leftColor) {
        this.energyOffColor = leftColor;
        return this;
    }
    
    public int getSpacerColor() {
        return spacerColor == null ? WidgetBase.colorEnergyBarSpacer : spacerColor;
    }
    
    public EnergyBar setSpacerColor(int rightColor) {
        this.spacerColor = rightColor;
        return this;
    }
    
    public int getTextColor() {
        return textColor == null ? WidgetBase.colorEnergyBarText : textColor;
    }
    
    public EnergyBar setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }
    
    @Override
    public void draw(final int x, final int y) {
        if (!visible) {
            return;
        }
        super.draw(x, y);
    
        int bx = x + bounds.getX();
        int by = y + bounds.getX();
        WarhammerRenderHelper.drawThickBeveledBox(bx, by, bx + bounds.getWidth() - 1,
                by + bounds.getHeight() - 1, 1, WidgetBase.colorEnergyBarTopLeft, WidgetBase.colorEnergyBarBottomRight, 0xff636363);
    
        long currentValue = getValue();
        long maximum = getMaxValue();
        if (maximum > 0) {
            int color;
            boolean on = false;
            if (horizontal) {
                int w = (int) ((bounds.getWidth()-2) * (double) currentValue / maximum);
                for (int xx = bx+1 ; xx < bx + bounds.getWidth()-2 ; xx++) {
                    color = getColor(bx, w, on, xx);
                    WarhammerRenderHelper.drawVerticalLine(xx, by+1, by + bounds.getHeight() - 2, color);
                    on = !on;
                }
            } else {
                int h = (int) ((bounds.getHeight()-2) * (double) currentValue / maximum);
                for (int yy = y+1 ; yy < y + bounds.getHeight()-2 ; yy++) {
                    color = getColorReversed(y, h, on, yy);
                    WarhammerRenderHelper.drawHorizontalLine(bx+1, y + by + bounds.getHeight() - yy -2, bx + bounds.getWidth() - 2, color);
                    on = !on;
                }
            }
        }
        if (showText) {
            String s;
            if (showRfPerTick) {
                s = rfPerTick + "RF/t";
            } else {
                s = currentValue + "/" + maximum;
            }
            mc.fontRenderer.drawString(mc.fontRenderer.trimStringToWidth(s, getBounds().getWidth()), x+bounds.getX() + 5, y+bounds.getY()+(bounds.getHeight()-mc.fontRenderer.FONT_HEIGHT)/2, getTextColor());
        }
    }
    
    private int getColor(int pos, int total, boolean on, int cur) {
        int color;
        if (on) {
            if (cur < pos + total) {
                color = getEnergyOnColor();
            } else {
                color = getEnergyOffColor();
            }
        } else {
            color = getSpacerColor();
        }
        return color;
    }
    
    private int getColorReversed(int pos, int total, boolean on, int cur) {
        int color;
        if (on) {
            if (cur < pos + total) {
                color = getEnergyOnColor();
            } else {
                color = getEnergyOffColor();
            }
        } else {
            color = getSpacerColor();
        }
        return color;
    }
}
