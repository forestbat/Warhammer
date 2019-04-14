package com.forestbat.warhammer.gui;

import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import com.forestbat.warhammer.tileentity.SpaceRingReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSpaceRing extends GuiContainer {
    ResourceLocation GUI_SPACE_RING=new ResourceLocation("assets/gui/gui_space_ring");
    public GuiSpaceRing(Container inventorySlotsIn){
        super(inventorySlotsIn);
        setGuiSize(176,166);
    }
    /** The Gui has 5 rows for Items */
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GL11.glColor4f(1,1,1,1);
        this.mc.getTextureManager().bindTexture(GUI_SPACE_RING);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 5 * 18 + 17);
        this.drawTexturedModalRect(i, j + 5 * 18 + 17, 0, 126, this.xSize, 96);
    }
    /*public void addRollingBar(){
        this.mc.getTextureManager().bindTexture(null);
    }*/
    public void initGui(){
        super.initGui();
        ScrollBar scrollBar=new ScrollBar(mc,this).setVertical().setMinimumKnobSize(60);
        EnergyBar energyBar=new EnergyBar(mc,this).setMaxValue(new SpaceRingReader().getMaxEnergyStored());
    }
    private void drawRecipeSlots(SpaceRingReader spaceRingReader) {
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0.0F);
        GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (short) 240 / 1.0F, 240.0f);

        zLevel = 100.0F;
        itemRender.zLevel = 100.0F;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();

        for (int i = 0; i < 8; i++) {
            ItemStack stack = spaceRingReader.getStackInSlot(9);
            if (!stack.isEmpty()) {
                int slotIdx = i;//todo
            } else {
                //slotIdx = i + CrafterContainer.SLOT_BUFFEROUT - CrafterContainer.BUFFER_SIZE;
            }
            Slot slot = inventorySlots.getSlot(0/*slotIdx*/);
            if (!slot.getHasStack()) {
                itemRender.renderItemAndEffectIntoGUI(stack, slot.xPos, slot.yPos);

                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                this.mc.getTextureManager().bindTexture(null);
                WarhammerRenderHelper.drawTexturedModelRect(slot.xPos, slot.yPos, 14 * 16, 3 * 16, 16, 16);
                GlStateManager.enableDepth();
                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
            }
        }
        itemRender.zLevel = 0.0F;
        zLevel = 0.0F;

        GlStateManager.popMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX,mouseY,partialTicks);
    }
}

