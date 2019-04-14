package com.forestbat.warhammer.gui;

import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import com.forestbat.warhammer.tileentity.SpaceRingReader;
import com.forestbat.warhammer.tileentity.ToolsForger;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiToolsForger extends GuiContainer {
    public static ResourceLocation GUI_TOOLS_FORGER=new ResourceLocation("assets/gui/gui_tools_forger");
    public GuiToolsForger(Container inventorySlotsIn){
        super(inventorySlotsIn);
        setGuiSize(176,166);
    }
    @SubscribeEvent
    public TileEntity getTEFromGui(PlayerInteractEvent event, World world){
        return world.getTileEntity(event.getPos());
    }
    @Override
    public void drawBackground(int tint) {
        this.mc.getTextureManager().bindTexture(GUI_TOOLS_FORGER);
        super.drawBackground(tint);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1,1,1,1);
        this.mc.getTextureManager().bindTexture(GUI_TOOLS_FORGER);
        //int i = (this.width - this.xSize) / 2;
        //int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(20, 60, 0, 0, 5*18, 5*18);
        this.drawTexturedModalRect(115,60, 0, 126, this.xSize, 36);

    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
    @SubscribeEvent
    public void initGui(PlayerInteractEvent event){
        super.initGui();
        EnergyBar energyBar=new EnergyBar(mc,this).setMaxValue(new SpaceRingReader().getMaxEnergyStored());
        Button redStoneButton=new Button(mc,this).setDesiredHeight(16).setDesiredWidth(16);
        redStoneButton.addButtonEvent(widget -> {
            if(event.getWorld().getRedstonePower(event.getPos(),null)>0)
                event.getWorld().getTileEntity(event.getPos()).validate();}//todo
        );
    }
    private void drawRecipeSlots(ToolsForger toolsForger) {
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft, guiTop, 0.0F);
        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (short) 240 / 1.0F, 240.0f);

        zLevel = 50F;
        itemRender.zLevel = 50F;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHT0);
        for (int i = 0; i < 25; i++) {
            Slot slot = inventorySlots.getSlot(i);
            ItemStack stack= toolsForger.getStackInSlot(i);
            if (!slot.getHasStack()) {
                itemRender.renderItemAndEffectIntoGUI(stack, slot.xPos, slot.yPos);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH);
                this.mc.getTextureManager().bindTexture(null);
                WarhammerRenderHelper.drawTexturedModelRect(slot.xPos, slot.yPos, 14 * 16, 3 * 16, 16, 16);
                GL11.glEnable(GL11.GL_DEPTH);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }
        itemRender.zLevel = 0.0F;
        zLevel = 0.0F;

        GL11.glPopMatrix();
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX,mouseY,partialTicks);
    }
}
