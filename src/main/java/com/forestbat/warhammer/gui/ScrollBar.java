package com.forestbat.warhammer.gui;
import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ScrollBar extends WidgetBase<ScrollBar> {
    public static final String TYPE_SCROLL_BAR = "scroll_bar";

    public static final boolean DEFAULT_HORIZONTAL = false;
    public static final int DEFAULT_MINIMUM_KNOBSIZE = 4;

    private boolean dragging = false;
    private int dx;
    private int dy;
    private boolean horizontal = DEFAULT_HORIZONTAL;
    private int minimumKnobSize = DEFAULT_MINIMUM_KNOBSIZE;

    public ScrollBar(Minecraft mc, Gui gui) {
        super(mc, gui);
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return !horizontal;
    }

    public ScrollBar setMinimumKnobSize(int m) {
        minimumKnobSize = m;
        return this;
    }

    public ScrollBar setHorizontal() {
        this.horizontal = true;
        return this;
    }

    public ScrollBar setVertical() {
        this.horizontal = false;
        return this;
    }


    @Override
    public void draw(int x, int y) {
        if (!visible) {
            return;
        }
        super.draw(x, y);

        int xx = x + bounds.getX();
        int yy = y + bounds.getY();

        WarhammerRenderHelper.drawThickBeveledBox(xx, yy, xx + bounds.getWidth() - 1, yy + bounds.getHeight() - 1, 1,
                WidgetBase.colorScrollBarTopLeft, WidgetBase.colorScrollBarBottomRight, WidgetBase.colorScrollBarFiller);

        int divider = getMaximum() - getCountSelected();

        if (horizontal) {
            int size = calculateKnobSize(divider, bounds.getWidth());
            int first = calculateKnobOffset(divider, size, bounds.getWidth());
            if (dragging) {
                WarhammerRenderHelper.drawBeveledBox(xx + 1 + first, yy + 2, xx + 1 + first + size - 1, yy + bounds.getHeight() - 4,
                        WidgetBase.colorScrollBarKnobDraggingTopLeft, WidgetBase.colorScrollBarKnobDraggingBottomRight,
                        WidgetBase.colorScrollBarKnobDraggingFiller);
            } else if (isHovering()) {
                WarhammerRenderHelper.drawBeveledBox(xx + 1 + first, yy + 2, xx + 1 + first + size - 1, yy + bounds.getHeight() - 4,
                        WidgetBase.colorScrollBarKnobHoveringTopLeft, WidgetBase.colorScrollBarKnobHoveringBottomRight,
                        WidgetBase.colorScrollBarKnobHoveringFiller);
            } else {
                WarhammerRenderHelper.drawBeveledBox(xx + 1 + first, yy + 2, xx + 1 + first + size - 1,yy + bounds.getHeight() - 4,
                        WidgetBase.colorScrollBarKnobTopLeft, WidgetBase.colorScrollBarKnobBottomRight,
                        WidgetBase.colorScrollBarKnobFiller);
            }
            if (size >= 8) {
                WarhammerRenderHelper.drawVerticalLine(xx + 1 + first + size / 2 - 1, yy + 3, yy + bounds.getHeight() - 6, WidgetBase.colorScrollBarKnobMarkerLine);
                if (size >= 10) {
                    WarhammerRenderHelper.drawVerticalLine(xx + 1 + first + size / 2 - 2 - 1, yy + 3, yy + bounds.getHeight() - 6, WidgetBase.colorScrollBarKnobMarkerLine);
                    WarhammerRenderHelper.drawVerticalLine(xx + 1 + first + size / 2 + 2 - 1, yy + 3, yy + bounds.getHeight() - 6, WidgetBase.colorScrollBarKnobMarkerLine);
                }
            }
        } else {
            int size = calculateKnobSize(divider, bounds.getHeight());
            int first = calculateKnobOffset(divider, size, bounds.getHeight());
            if (dragging) {
                WarhammerRenderHelper.drawBeveledBox(xx + 1, yy + 1 + first, xx + bounds.getWidth() - 2, yy + 1 + first + size - 1, WidgetBase.colorScrollBarKnobDraggingTopLeft, WidgetBase.colorScrollBarKnobDraggingBottomRight, WidgetBase.colorScrollBarKnobDraggingFiller);
            } else if (isHovering()) {
                WarhammerRenderHelper.drawBeveledBox(xx + 1, yy + 1 + first, xx + bounds.getWidth() - 2, yy + 1 + first + size - 1, WidgetBase.colorScrollBarKnobHoveringTopLeft, WidgetBase.colorScrollBarKnobHoveringBottomRight, WidgetBase.colorScrollBarKnobHoveringFiller);
            } else {
                WarhammerRenderHelper.drawBeveledBox(xx + 1, yy + 1 + first, xx + bounds.getWidth() - 2, yy + 1 + first + size - 1, WidgetBase.colorScrollBarKnobTopLeft, WidgetBase.colorScrollBarKnobBottomRight, WidgetBase.colorScrollBarKnobFiller);
            }
            if (size >= 8) {
                WarhammerRenderHelper.drawHorizontalLine(xx + 3, yy + 1 + first + size / 2 - 1, xx + bounds.getWidth() - 4, WidgetBase.colorScrollBarKnobMarkerLine);
                if (size >= 10) {
                    WarhammerRenderHelper.drawHorizontalLine(xx + 3, yy + 1 + first + size / 2 - 2 - 1, xx + bounds.getWidth() - 4, WidgetBase.colorScrollBarKnobMarkerLine);
                    WarhammerRenderHelper.drawHorizontalLine(xx + 3, yy + 1 + first + size / 2 + 2 - 1, xx + bounds.getWidth() - 4, WidgetBase.colorScrollBarKnobMarkerLine);
                }
            }
        }
    }

    private int calculateKnobOffset(int divider, int size, int boundsSize) {
        int first;
        if (divider <= 0) {
            first = 0;
        } else {
            first = (getFirstSelected() * (boundsSize - 2 - size)) / divider;
        }
        return first;
    }

    private int calculateKnobSize(int divider, int boundsSize) {
        int size;
        if (divider <= 0) {
            size = boundsSize - 2;
        } else {
            size = (getCountSelected() * (boundsSize - 2)) / getMaximum();
        }
        if (size < minimumKnobSize) {
            size = minimumKnobSize;
        }
        return size;
    }

    private void updateScrollable(int x, int y) {
        int first;
        int divider = getMaximum() - getCountSelected();
        if (divider <= 0) {
            first = 0;
        } else {
            if (horizontal) {
                int size = calculateKnobSize(divider, bounds.getWidth());
                first = ((x - bounds.getX() - dx) * divider) / (bounds.getWidth() - 4 - size);
            } else {
                int size = calculateKnobSize(divider, bounds.getHeight());
                first = ((y - bounds.getY() - dy) * divider) / (bounds.getHeight() - 4 - size);
            }
        }
        if (first > divider) {
            first = divider;
        }
        if (first < 0) {
            first = 0;
        }
        //setFirstSelected(first);
    }

    @Override
    public boolean mouseWheel(int amount, int x, int y) {
        int first = getFirstSelected();

        int divider = getMaximum() - getCountSelected();
        if (divider <= 0) {
            first = 0;
        } else {
            if (amount > 0) {
                first -= 3;
            } else if (amount < 0) {
                first += 3;
            }
        }
        if (first > divider) {
            first = divider;
        }
        if (first < 0) {
            first = 0;
        }
        //setFirstSelected(first);
        return true;
    }

    @Override
    public WidgetBase<?> mouseClick(int x, int y, int button) {
        super.mouseClick(x, y, button);
        dragging = true;
        int divider = getMaximum() - getCountSelected();
        int first;
        if (horizontal) {
            int size = calculateKnobSize(divider, bounds.getWidth());
            first = calculateKnobOffset(divider, size, bounds.getWidth());
            dx = x - bounds.getX() - first;
            dy = 0;
        } else {
            int size = calculateKnobSize(divider, bounds.getHeight());
            first = calculateKnobOffset(divider, size, bounds.getHeight());
            dx = 0;
            dy = y - bounds.getY() - first;
        }

        return this;
    }

    @Override
    public void mouseRelease(int x, int y, int button) {
        super.mouseRelease(x, y, button);
        if (dragging) {
            updateScrollable(x, y);
            dragging = false;
        }
    }

    @Override
    public void mouseMove(int x, int y) {
        super.mouseMove(x, y);
        if (dragging) {
            updateScrollable(x, y);
        }
    }
    public int getMaximum(){
        return 100;
    }
    public int getCountSelected(){
        return 1;
    }
    public int getFirstSelected() {
        return 0;
    }
}