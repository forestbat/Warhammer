//TODO:I have many things todo,I want to put custom widgets into GuiContainer,and learn how to design GUI,the progression is slow...
package com.forestbat.warhammer.gui;

import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.util.Rectangle;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class WidgetBase<T>{
    public static final int DEFAULT_BACKGROUND_OFFSET = 256;
    public static final int DEFAULT_FILLED_RECT_THICKNESS = 0;
    public static final int DEFAULT_FILLED_BACKGROUND = -1;
    public static final boolean DEFAULT_BACKGROUND_HORIZONTAL = true;

    public static int colorScrollBarTopLeft = 0xff2b2b2b;
    public static int colorScrollBarBottomRight = 0xffffffff;
    public static int colorScrollBarFiller = 0xff636363;
    public static int colorScrollBarKnobTopLeft = 0xffeeeeee;
    public static int colorScrollBarKnobBottomRight = 0xff333333;
    public static int colorScrollBarKnobFiller = 0xff8b8b8b;
    public static int colorScrollBarKnobDraggingTopLeft = 0xff5c669d;
    public static int colorScrollBarKnobDraggingBottomRight = 0xffbcc5ff;
    public static int colorScrollBarKnobDraggingFiller = 0xff7f89bf;
    public static int colorScrollBarKnobMarkerLine = 0xff4e4e4e;
    public static int colorScrollBarKnobHoveringTopLeft = 0xffa5aac5;
    public static int colorScrollBarKnobHoveringBottomRight = 0xff777c99;
    public static int colorScrollBarKnobHoveringFiller = 0xff858aa5;

    public static int colorTextNormal = 0xFF303030;
    public static int colorTextInListNormal = 0xFF151515;
    public static int colorTextDisabled = 0xFFa0a0a0;

    public static int colorTextFieldFiller = 0xffc6c6c6;
    public static int colorTextFieldFocusedFiller = 0xffeeeeee;
    public static int colorTextFieldHoveringFiller = 0xffdadada;
    public static int colorTextFieldCursor = 0xff000000;
    public static int colorTextFieldTopLeft = 0xff2b2b2b;
    public static int colorTextFieldBottomRight = 0xffffffff;

    public static int colorEnergyBarTopLeft = 0xff2b2b2b;
    public static int colorEnergyBarBottomRight = 0xffffffff;
    public static int colorEnergyBarHighEnergy = 0xffdd0000;
    public static int colorEnergyBarLowEnergy = 0xff631111;
    public static int colorEnergyBarSpacer = 0xff430000;
    public static int colorEnergyBarText = 0xffffffff;

    public static int colorListBackground = 0xff8b8b8b;
    public static int colorListSeparatorLine = 0xff5c5c5c;
    public static int colorListSelectedHighlightedGradient1 = 0xffbbbb00;
    public static int colorListSelectedHighlightedGradient2 = 0xff999900;
    public static int colorListSelectedGradient1 = 0xff616161;
    public static int colorListSelectedGradient2 = 0xff414141;
    public static int colorListHighlightedGradient1 = 0xff717120;
    public static int colorListHighlightedGradient2 = 0xff515110;

    public static int colorBackgroundBevelBright = 0xffffffff;
    public static int colorBackgroundBevelDark = 0xff2b2b2b;
    public static int colorBackgroundFiller = 0xffc6c6c6;

    public static int colorToggleNormalBorderTopLeft = 0xffeeeeee;
    public static int colorToggleNormalBorderBottomRight = 0xff777777;
    public static int colorToggleNormalFiller = 0xffc6c6c6;
    public static int colorToggleDisabledBorderTopLeft = 0xffeeeeee;
    public static int colorToggleDisabledBorderBottomRight = 0xff777777;
    public static int colorToggleDisabledFiller = 0xffc6c6c6;
    public static int colorToggleTextNormal = 0xFF303030;
    public static int colorToggleTextDisabled = 0xFFa0a0a0;

    public static int colorButtonExternalBorder = 0xff000000;

    public static int colorCycleButtonTriangleNormal = 0xff000000;
    public static int colorCycleButtonTriangleDisabled = 0xff888888;

    public static int colorButtonBorderTopLeft = 0xffeeeeee;
    public static int colorButtonBorderBottomRight = 0xff777777;
    public static int colorButtonFiller = 0xffc6c6c6;
    public static int colorButtonFillerGradient1 = 0xffb1b1b1;
    public static int colorButtonFillerGradient2 = 0xffe1e1e1;

    public static int colorButtonDisabledBorderTopLeft = 0xffeeeeee;
    public static int colorButtonDisabledBorderBottomRight = 0xff777777;
    public static int colorButtonDisabledFiller = 0xffc6c6c6;
    public static int colorButtonDisabledFillerGradient1 = 0xffb1b1b1;
    public static int colorButtonDisabledFillerGradient2 = 0xffe1e1e1;

    public static int colorButtonSelectedBorderTopLeft = 0xff5c669d;
    public static int colorButtonSelectedBorderBottomRight = 0xffbcc5ff;
    public static int colorButtonSelectedFiller = 0xff7f89bf;
    public static int colorButtonSelectedFillerGradient1 = 0xff6a74aa;
    public static int colorButtonSelectedFillerGradient2 = 0xff949ed4;

    public static int colorButtonHoveringBorderTopLeft = 0xffa5aac5;
    public static int colorButtonHoveringBorderBottomRight = 0xff999ebb;
    public static int colorButtonHoveringFiller = 0xffa2a7c2;
    public static int colorButtonHoveringFillerGradient1 = 0xff8d92ad;
    public static int colorButtonHoveringFillerGradient2 = 0xffbabfda;

    protected Rectangle bounds;
    protected int desiredWidth = -1;
    protected int desiredHeight = -1;

    protected Minecraft mc;
    protected Gui gui;
    protected Window window;

    private boolean enabled = true;
    private boolean hovering = false;
    protected boolean visible = true;
    protected List<String> tooltips = null;
    protected List<ItemStack> items = null;
    private Set<Integer> enableFlags = new HashSet<>();
    private String name;
    private String channel;

    private boolean layoutDirty = true;
    private Object userObject = null;

    private ResourceLocation background1 = null;
    private ResourceLocation background2 = null;
    private boolean background2Horizontal = DEFAULT_BACKGROUND_HORIZONTAL;
    private int backgroundOffset = DEFAULT_BACKGROUND_OFFSET;
    private int filledRectThickness = DEFAULT_FILLED_RECT_THICKNESS;
    private int filledBackground = DEFAULT_FILLED_BACKGROUND;
    private int filledBackground2 = DEFAULT_FILLED_BACKGROUND;

    public ConcurrentHashMap<T,? extends WidgetBase> map=new ConcurrentHashMap<>(19);
    private final Map<String, List<ChannelEvent>> channelEvents = new ConcurrentHashMap<>();

    public boolean containsWidget(WidgetBase<T> w) {
        return w == this;
    }

    public WidgetBase (Minecraft mc, Gui gui) {
        this.mc = mc;
        this.gui = gui;
    }

    public String getName() {
        return name;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }


    public T setName(String name) {
        this.name = name;
        if (channel == null) {
            channel = name;     // Automatic channel
        }
        return (T)this;
    }

    public T setChannel(String channel) {
        this.channel = channel;
        return (T) this;
    }

    protected void fireChannelEvents() {
        fireChannelEvents(new ConcurrentHashMap());
    }

    protected void fireChannelEvents(String id) {
        fireChannelEvents(map);
    }

    protected void fireChannelEvents(@Nonnull ConcurrentHashMap params) {
        if (window != null && channel != null) {
          fireChannelEvents(channel, this, params);
        }
    }
    public void fireChannelEvents(String channel, WidgetBase<T> widget, @Nonnull ConcurrentHashMap params) {
        if (channelEvents.containsKey(channel)) {
            for (ChannelEvent event : channelEvents.get(channel)) {
                event.fire(widget, params);
            }
        }
    }

    protected void drawBox(int xx, int yy, int color) {
        Gui.drawRect(xx, yy, xx, yy + bounds.getHeight(), color);
        Gui.drawRect(xx + bounds.getWidth(), yy, xx + bounds.getWidth(), yy + bounds.getHeight(), color);
        Gui.drawRect(xx, yy, xx + bounds.getWidth(), yy, color);
        Gui.drawRect(xx, yy + bounds.getHeight(), xx + bounds.getWidth(), yy + bounds.getHeight(), color);
    }

    public WidgetBase<?> getWidgetAtPosition(int x, int y) {
        return this;
    }

    public T setTooltips(String... tooltips) {
        if (tooltips.length > 0) {
            this.tooltips = new ArrayList<>(tooltips.length);
            Collections.addAll(this.tooltips, tooltips);
        } else {
            this.tooltips = null;
        }
        return (T)this;
    }

    public T setTooltipItems(ItemStack...items) {
        if (items.length > 0) {
            this.items = new ArrayList<>(items.length);
            Collections.addAll(this.items, items);
        } else {
            this.items = Collections.emptyList();
        }
        return (T)this;
    }

    public List<String> getTooltips() {
        return tooltips;
    }

    public List<ItemStack> getTooltipItems() {
        return items;
    }

    public boolean isHovering() {
        return hovering;
    }

    public T setHovering(boolean hovering) {
        this.hovering = hovering;
        return (T)this;
    }

    public T setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (T)this;
    }

    public T setEnabledFlags(String... flags) {
        for (String flag : flags) {
           //todo
        }
        return (T)this;
    }

    @Nonnull
    public Set<Integer> getEnabledFlags() {
        return enableFlags;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isEnabledAndVisible() {
        return enabled && visible;
    }

    public T setVisible(boolean visible) {
        this.visible = visible;
        return (T) this;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getDesiredSize(Dimension dimension) {
        if (dimension == Dimension.DIMENSION_WIDTH) {
            return getDesiredWidth();
        } else {
            return getDesiredHeight();
        }
    }

    public int getDesiredWidth() {
        return desiredWidth;
    }

    public  T setDesiredWidth(int desiredWidth) {
        this.desiredWidth = desiredWidth;
        return ( T) this;
    }

    public int getDesiredHeight() {
        return desiredHeight;
    }

    public  T setDesiredHeight(int desiredHeight) {
        this.desiredHeight = desiredHeight;
        return (T) this;
    }

    public  T setBackground(ResourceLocation bg) {
        return setBackgrounds(bg, null);
    }

    public  T setBackgrounds(ResourceLocation bg1, ResourceLocation bg2) {
        this.background1 = bg1;
        this.background2 = bg2;
        this.background2Horizontal = true;
        this.backgroundOffset = 256;
        return (T) this;
    }

    public T setBackgroundLayout(boolean horizontal, int offset) {
        this.background2Horizontal = horizontal;
        this.backgroundOffset = offset;
        return (T) this;
    }

    /**
     * Use this instead of a textured background.
     * @param thickness use 0 to disable
     * @return
     */
    public T setFilledRectThickness(int thickness) {
        filledRectThickness = thickness;
        return (T) this;
    }

    public int getFilledBackground() {
        return filledBackground;
    }

    public int getFilledBackground2() {
        return filledBackground2;
    }

    public T setFilledBackground(int filledBackground) {
        this.filledBackground = filledBackground;
        this.filledBackground2 = -1;
        return (T) this;
    }

    public  T setFilledBackground(int filledBackground, int filledBackground2) {
        this.filledBackground = filledBackground;
        this.filledBackground2 = filledBackground2;
        return (T) this;
    }


    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean in(int x, int y) {
        if (bounds == null) {
            return false;
        } else {
            return bounds.contains(x, y);
        }
    }

    protected void drawBackground(int x, int y, int w, int h) {
        if (!visible) {
            return;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int xx = x + bounds.getX();
        int yy = y + bounds.getY();
        if (background1 != null) {
            mc.getTextureManager().bindTexture(background1);
            if (background2 == null) {
                gui.drawTexturedModalRect(xx, yy, 0, 0, w, h);
            } else {
                if (background2Horizontal) {
                    gui.drawTexturedModalRect(xx, yy, 0, 0, backgroundOffset, h);
                    mc.getTextureManager().bindTexture(background2);
                    gui.drawTexturedModalRect(xx + backgroundOffset, yy, 0, 0, w - backgroundOffset, h);
                } else {
                    gui.drawTexturedModalRect(xx, yy, 0, 0, w, backgroundOffset);
                    mc.getTextureManager().bindTexture(background2);
                    gui.drawTexturedModalRect(xx, yy + backgroundOffset, 0, 0, w, h - backgroundOffset);
                }
            }
        } else if (filledRectThickness > 0) {
            WarhammerRenderHelper.drawThickBeveledBox(xx, yy, xx + w - 1, yy + h - 1, filledRectThickness, colorBackgroundBevelBright, colorBackgroundBevelDark, filledBackground == -1 ? colorBackgroundFiller : filledBackground);
        } else if (filledRectThickness < 0) {
            WarhammerRenderHelper.drawThickBeveledBox(xx, yy, xx + w - 1, yy + h - 1, -filledRectThickness,colorBackgroundBevelDark, colorBackgroundBevelBright, filledBackground == -1 ? colorBackgroundFiller : filledBackground);
        } else if (filledBackground != -1) {
            WarhammerRenderHelper.drawHorizontalGradientRect(xx, yy, xx + w - 1, yy + h - 1, filledBackground, filledBackground2 == -1 ? filledBackground : filledBackground2);
        }
    }

    protected void drawBackground(int x, int y) {
        if (!visible) {
            return;
        }
        drawBackground(x, y, bounds.getWidth(), bounds.getHeight());
    }

    protected void drawStyledBoxNormal(GuiContainer guiContainer, int x1, int y1, int x2, int y2) {
        drawStyledBox(guiContainer, x1, y1, x2, y2,
        colorButtonBorderTopLeft, colorButtonFiller, colorButtonFillerGradient1,
                colorButtonFillerGradient2, colorButtonBorderBottomRight);
    }

    protected void drawStyledBoxNormal(GuiContainer guiContainer, int x1, int y1, int x2, int y2, int averageOverride) {
        drawStyledBox(guiContainer, x1, y1, x2, y2,
        colorButtonBorderTopLeft, averageOverride, averageOverride, averageOverride, colorButtonBorderBottomRight);
    }

    protected void drawStyledBoxSelected(GuiContainer guiContainer, int x1, int y1, int x2, int y2) {
        drawStyledBox(guiContainer, x1, y1, x2, y2,
                colorButtonSelectedBorderTopLeft, colorButtonSelectedFiller, colorButtonSelectedFillerGradient1,
                colorButtonSelectedFillerGradient2, colorButtonSelectedBorderBottomRight);
    }

    protected void drawStyledBoxHovering(GuiContainer guiContainer, int x1, int y1, int x2, int y2) {
        drawStyledBox(guiContainer, x1, y1, x2, y2,
                colorButtonHoveringBorderTopLeft, colorButtonHoveringFiller, colorButtonHoveringFillerGradient1,
                colorButtonHoveringFillerGradient2, colorButtonHoveringBorderBottomRight);
    }

    protected void drawStyledBoxDisabled(GuiContainer guiContainer, int x1, int y1, int x2, int y2) {
        drawStyledBox(guiContainer, x1, y1, x2, y2,
                colorButtonDisabledBorderTopLeft, colorButtonDisabledFiller, colorButtonDisabledFillerGradient1,
                colorButtonDisabledFillerGradient2, colorButtonDisabledBorderBottomRight);
    }

    private void drawStyledBox(GuiContainer guiContainer, int x1, int y1, int x2, int y2, int bright, int average, int average1, int average2, int dark) {
        WarhammerRenderHelper.drawThinButtonBox(x1, y1, x2, y2, bright, average, dark);
    }

    public void draw(int x, int y) {
        drawBackground(x, y);
    }

    public WidgetBase<?> mouseClick(int x, int y, int button) {
        return null;
    }

    public void mouseRelease(int x, int y, int button) {
    }

    public void mouseMove(int x, int y) {
    }

    public boolean mouseWheel(int amount, int x, int y) {
        return false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        return false;
    }
    /**
     * Mark this widget as dirty so that the system knows a new relayout is needed.
     */
    void markDirty() {
        layoutDirty = true;
    }

    void markClean() {
        layoutDirty = false;
    }

    boolean isDirty() {
        return layoutDirty;
    }

    public Object getUserObject() {
        return userObject;
    }

    public  T setUserObject(Object obj) {
        userObject = obj;
        return (T) this;
    }
    public interface ChannelEvent {

        void fire(@Nonnull WidgetBase<?> source, @Nonnull ConcurrentHashMap params);
    }
    public enum Dimension {
        DIMENSION_WIDTH,
        DIMENSION_HEIGHT
    }
    }


