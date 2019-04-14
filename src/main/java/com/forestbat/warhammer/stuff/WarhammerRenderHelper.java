package com.forestbat.warhammer.stuff;

import com.forestbat.warhammer.items.itemtools.FirePythonHeavyGun;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector3f;

import java.util.Random;

import static com.forestbat.warhammer.gui.WidgetBase.colorButtonExternalBorder;
import static org.lwjgl.opengl.GL11.*;

public class WarhammerRenderHelper {
    public static float rot = 0f;

    public static void renderEntity(Entity entity, int xPos, int yPos) {
        renderEntity(entity, xPos, yPos, 10);
    }

    public static void renderEntity(Entity entity, int xPos, int yPos, float scale) {
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GlStateManager.enableColorMaterial();
        GL11.glPushMatrix();
        GL11.glTranslatef(xPos + 8, yPos + 16, 50f);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glRotatef(135, 0, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glRotatef(-135, 0, 1, 0);
        GL11.glRotatef(rot, 0, 1, 0);
        GL11.glRotatef(0, 1, 0, 0);
        entity.rotationPitch = 0;
        GL11.glTranslatef(0, (float) entity.getYOffset(), 0);
        Minecraft.getMinecraft().getRenderManager().playerViewY = 180F;
        Minecraft.getMinecraft().getRenderManager().renderEntity(entity, 0, 0, 0, 0, 1, false);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
    }

    public static boolean renderObject(Minecraft mc, int x, int y, Object object, boolean highlight) {
        if (object instanceof Entity) {
            renderEntity((Entity) object, x, y);
            return true;
        }
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        return renderObject(mc, renderItem, x, y, object, true, 200);
    }

    public static boolean renderObject(Minecraft mc, RenderItem renderItem, int x, int y, Object object, boolean highlight, float level) {
        renderItem.zLevel = level;
        if (object instanceof Item)
            return renderItemStack(mc, renderItem, new ItemStack((Item) object, 1), x, y, "", highlight);
        if (object instanceof Block)
            return renderItemStack(mc, renderItem, new ItemStack((Block) object, 1), x, y, "", highlight);
        if (object instanceof ItemStack) {
            return renderItemStack(mc, renderItem, (ItemStack) object, x, y, "", highlight);
        }
        if (object instanceof FluidStack) {
            return renderFluidStack(mc, (FluidStack) object, x, y, highlight);
        }
        if (object instanceof TextureAtlasSprite) {
            return renderIcon(mc, renderItem, (TextureAtlasSprite) object, x, y, highlight);
        }
        return renderItemStack(mc, renderItem, ItemStack.EMPTY, x, y, "", highlight);
    }

    public static boolean renderIcon(Minecraft mc, RenderItem itemRender, TextureAtlasSprite itm, int xo, int yo, boolean highlight) {
        return true;
    }

    public static boolean renderItemStack(Minecraft mc, RenderItem renderItem, ItemStack itemStack, int x, int y, String id, boolean highlight) {
        GL11.glColor3f(1, 1, 1);
        boolean rc = false;
        if (highlight) {
            GL11.glEnable(GL11.GL_LIGHTING);
            drawVerticalGradientRect(x, y, x+16, y+16, 0x80ffffff, 0xffffffff);
        }
        if (itemStack != null) {
            rc = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(0, 0, 32);
            GL11.glColor3f(1, 1, 1);
            //GL33.glColorP3u(0,IntBuffer.allocate(16384));
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            RenderHelper.enableStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            renderItem.renderItemAndEffectIntoGUI(itemStack, x, y);
            renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, x, y, id, id.length() - 2);
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
        String amount;
        if (itemStack.getCount() <= 1) {
            amount = "";
        } else if (itemStack.getCount() < 1e5) {
            amount = itemStack.getCount() + "";
        } else if (itemStack.getCount() < 1e6) {
            amount = itemStack.getCount() / 1000 + "k";
        } else if (itemStack.getCount() < 1e9) {
            amount = itemStack.getCount() / 1000000 + "m";
        } else {
            amount = itemStack.getCount() / 1e9 + "g";
        }
        renderItemStack(mc, renderItem, itemStack, x, y, amount, highlight);
        return rc;
    }

    public static boolean renderFluidStack(Minecraft mc, FluidStack fluidStack, int x, int y,boolean highLight) {
        Fluid fluid = fluidStack.getFluid();
        ResourceLocation fluidResource = fluid.getStill();
        TextureMap textureMap = mc.getTextureMapBlocks();
        TextureAtlasSprite fluidAtlasSprite = null;
        if (fluidResource != null)
            fluidAtlasSprite = textureMap.getTextureExtry(fluidResource.toString());
        if (fluidAtlasSprite != null)
            fluidAtlasSprite = textureMap.getMissingSprite();
        int fluidColor = fluid.getColor();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fluidColor);
        setGLColorFromInt(fluidColor);
        drawFluidTexture(x, y, fluidAtlasSprite, 100);
        return true;
    }

    public static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }

    public static void drawFluidTexture(float x, float y, TextureAtlasSprite textureAtlasSprite, float zLevel) {
        double uMin = textureAtlasSprite.getMinU();
        double uMax = textureAtlasSprite.getMaxU();
        double vMin = textureAtlasSprite.getMinV();
        double vMax = textureAtlasSprite.getMaxV();
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + 16, zLevel).tex(uMin, vMax).endVertex();
        buffer.pos(x + 16, y, zLevel).tex(uMax, vMin).endVertex();
        buffer.pos(x + 16, y + 16, zLevel).tex(uMax, vMax).endVertex();
        buffer.pos(x, y, zLevel).tex(uMin, vMin).endVertex();
        tessellator.draw();
    }

    public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int x, int y, String id, float scaled) {
        String txt = null;
        if (!itemStack.isEmpty()) {
            int stackSize = itemStack.getCount();
            if (stackSize != 1 || id != null) {
                txt = id == null ? String.valueOf(stackSize) : id;
                if (id == null && stackSize < 1) {
                    txt = TextFormatting.DARK_AQUA + String.valueOf(stackSize);
                }
            }
            GL11.glDisable(GL_LIGHTING);
            GL11.glDisable(GL_DEPTH);
            GL11.glDisable(GL_BLEND);
            if (scaled >= 2) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                fontRenderer.drawStringWithShadow(txt, (x + 17) * 2 - 1 - fontRenderer.getStringWidth(txt), y * 2 + 24, 16777215);
                GL11.glPopMatrix();
            } else if (scaled < 2) {
                GL11.glPushMatrix();
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                fontRenderer.drawStringWithShadow(txt, (x - 2) * 1.34f + 24 - fontRenderer.getStringWidth(txt), y * 1.34f + 14, 16777215);
            } else {
                fontRenderer.drawStringWithShadow(txt, x + 17 - fontRenderer.getStringWidth(txt), y + 9, 16777215);
            }
            GL11.glEnable(GL_LIGHTING);
            GL11.glEnable(GL_DEPTH);
            GL11.glEnable(GL_BLEND);
        }
        if (itemStack.isItemStackDamageable()) {
            double durability = itemStack.getItemDamage();
            int j = (int) Math.round(13.0D - durability / itemStack.getMaxDamage() * 13.0D);
            int i = (int) Math.round(255.0D - durability / itemStack.getMaxDamage() * 255.0D);
            GL11.glDisable(GL_LIGHTING);
            GL11.glDisable(GL_DEPTH);
            GL11.glDisable(GL_TEXTURE_2D);
            GL11.glDisable(GL_ALPHA);
            GL11.glDisable(GL_BLEND);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            draw(vertexbuffer, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
            draw(vertexbuffer, x + 2, y + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
            draw(vertexbuffer, x + 2, y + 13, j, 1, 255 - i, i, 0, 255);
            GL11.glEnable(GL_BLEND);
            GL11.glEnable(GL_ALPHA);
            GL11.glEnable(GL_TEXTURE_2D);
            GL11.glEnable(GL_DEPTH);
            GL11.glEnable(GL_LIGHTING);
        }
        EntityPlayer entityPlayer = Minecraft.getMinecraft().player;
        float playertick = entityPlayer == null ? 0 : entityPlayer.getCooldownTracker().getCooldown(itemStack.getItem(),
                Minecraft.getMinecraft().getRenderPartialTicks());
        if (entityPlayer != null) {
            GL11.glDisable(GL_LIGHTING);
            GL11.glDisable(GL_DEPTH);
            GL11.glDisable(GL_TEXTURE_2D);
            Tessellator tessellator=Tessellator.getInstance();
            BufferBuilder buffer=tessellator.getBuffer();
            draw(buffer,x,y+ MathHelper.floor(16.0F * (1.0F - playertick)), 16, MathHelper.ceil(16.0F /256f), 255, 255, 255, 127);
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }

    public static void draw(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x, y + height, 0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + height, 0, 0).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + height, y + height, 0).color(red, green, blue, alpha).endVertex();
        Tessellator tessellator=Tessellator.getInstance();
        tessellator.draw();
    }

    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
        float zLevel = 0.0f;
        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_ALPHA);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x2, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos(x2, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL_BLEND);
        GL11.glEnable(GL_ALPHA);
        GL11.glEnable(GL_TEXTURE_2D);
    }

    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
        float zLevel = 0.0f;

        float f = (color1 >> 24 & 255) / 255.0F;
        float f1 = (color1 >> 16 & 255) / 255.0F;
        float f2 = (color1 >> 8 & 255) / 255.0F;
        float f3 = (color1 & 255) / 255.0F;
        float f4 = (color2 >> 24 & 255) / 255.0F;
        float f5 = (color2 >> 16 & 255) / 255.0F;
        float f6 = (color2 >> 8 & 255) / 255.0F;
        float f7 = (color2 & 255) / 255.0F;
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_ALPHA);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x1, y1, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x1, y2, zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos(x2, y2, zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos(x2, y1, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL_BLEND);
        GL11.glEnable(GL_ALPHA);
        GL11.glEnable(GL_TEXTURE_2D);
    }

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    public static void drawLeftTraingle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x + 1, y - 1, y + 1, color);
        drawVerticalLine(x + 2, y - 2, y + 2, color);
    }

    public static void drawRightTraingle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x - 1, y - 1, y + 1, color);
        drawVerticalLine(x - 2, y - 2, y + 2, color);
    }

    public static void drawUpTraingle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x - 1, y + 1, x + 1, color);
        drawHorizontalLine(x - 2, y + 2, x + 2, color);
    }

    public static void drawDownTraingle(int x, int y, int color) {
        drawVerticalLine(x, y, x, color);
        drawVerticalLine(x - 1, y - 1, x + 1, color);
        drawVerticalLine(x - 1, y - 2, x + 2, color);
    }

    public static void drawThickButtonBox(int x1, int x2, int y1, int y2, int bright, int average, int dark) {
        Gui.drawRect(x1 + 2, y1 + 2, x2 - 2, y2 - 2, average);
        drawHorizontalLine(x1 + 1, y1, x2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y2 - 1, x2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y1 + 1, x2 - 2, bright);
        drawVerticalLine(x1 + 1, y1 + 2, y2 - 3, bright);
        drawHorizontalLine(x1 + 1, y2 - 2, x2 - 1, dark);
        drawVerticalLine(x2 - 2, y1 + 1, y2 - 2, dark);
    }

    public static void drawThinButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average);
        drawHorizontalLine(x1 + 1, y1, x2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y2 - 1, x2 - 1, colorButtonExternalBorder);
        drawVerticalLine(x1, y1 + 1, y2 - 1, colorButtonExternalBorder);
        drawVerticalLine(x2 - 1, y1 + 1, y2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y1 + 1, x2 - 2, bright);
        drawVerticalLine(x1 + 1, y1 + 2, y2 - 3, bright);
        drawHorizontalLine(x1 + 1, y2 - 2, x2 - 1, dark);
        drawVerticalLine(x2 - 2, y1 + 1, y2 - 2, dark);
    }

    public static void drawThinButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1,
                                                 int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1 + 1, y1, x2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y2 - 1, x2 - 1, colorButtonExternalBorder);
        drawVerticalLine(x1, y1 + 1, y2 - 1, colorButtonExternalBorder);
        drawVerticalLine(x2 - 1, y1 + 1, y2 - 1, colorButtonExternalBorder);
        drawHorizontalLine(x1 + 1, y1 + 1, x2 - 2, bright);
        drawVerticalLine(x1 + 1, y1 + 2, y2 - 3, bright);
        drawHorizontalLine(x1 + 1, y2 - 2, x2 - 1, dark);
        drawVerticalLine(x2 - 2, y1 + 1, y2 - 2, dark);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        drawBeveledBox(x1, y1, x2, y2, bright, dark, average);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1,
                                                 int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1, y1, x2 - 1, bright);
        drawVerticalLine(x1, y1, y2 - 1, bright);
        drawVerticalLine(x2 - 1, y1, y2 - 1, dark);
        drawHorizontalLine(x1, y2 - 1, x2, dark);
    }

    /**
     * Draw a beveled box. x2 and y2 are not included.
     */
    public static void drawBeveledBox(int x1, int y1, int x2, int y2, int topleftcolor, int botrightcolor,
                                      int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        drawHorizontalLine(x1, y1, x2 - 1, topleftcolor);
        drawVerticalLine(x1, y1, y2 - 1, topleftcolor);
        drawVerticalLine(x2 - 1, y1, y2 - 1, botrightcolor);
        drawHorizontalLine(x1, y2 - 1, x2, botrightcolor);
    }

    /**
     * Draw a thick beveled box. x2 and y2 are not included.
     */
    public static void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor,
                                           int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    /**
     * Draw a beveled box. x2 and y2 are not included.
     */
    public static void drawFlatBox(int x1, int y1, int x2, int y2, int border, int fill) {
        if (fill != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fill);
        }
        drawHorizontalLine(x1, y1, x2 - 1, border);
        drawVerticalLine(x1, y1, y2 - 1, border);
        drawVerticalLine(x2 - 1, y1, y2 - 1, border);
        drawHorizontalLine(x1, y2 - 1, x2, border);
    }

    public static void drawTexturedModelRect(int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, zLevel).tex(((u + 0) / 256f), ((v + height) / 256f)).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex(((u + width) / 256f), ((v + height) / 256f)).endVertex();
        buffer.pos(x + width, y + 0, zLevel).tex(((u + width) / 256f), ((v + 0) / 256f)).endVertex();
        buffer.pos(x, y + 0, zLevel).tex(((u + 0) / 256f), ((v + 0) / 256f)).endVertex();
        tessellator.draw();
    }

    public static void renderBillBoardQuadBright(double scale) {
        GL11.glPushMatrix();
        rotateToPlayer();
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        buffer.pos(-scale, scale, 0).tex(0, 1).lightmap(240 >> 16 & 65535, 240 & 65535).
                color(255, 255, 255, 128);
        buffer.pos(scale, -scale, 0).tex(1, 0).lightmap(240 >> 16 & 65535, 240 & 65535).
                color(255, 255, 255, 128);
        buffer.pos(scale, scale, 0).tex(1, 1).lightmap(240 >> 16 & 65535, 240 & 65535).
                color(255, 255, 255, 128);
        buffer.pos(-scale, -scale, 0).tex(0, 0).lightmap(240 >> 16 & 65535, 240 & 65535).
                color(255, 255, 255, 128);
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void renderBillboardQuadWithRotation(float rot, double scale) {
        GL11.glPushMatrix();
        rotateToPlayer();
        GL11.glRotatef(rot, 0, 0, 1);
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 1).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 1).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void renderBillBoardQuad(double scale) {
        GL11.glPushMatrix();
        rotateToPlayer();
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        buffer.pos(-scale, scale, 0).tex(0, 1).endVertex();
        buffer.pos(scale, -scale, 0).tex(1, 0).endVertex();
        buffer.pos(scale, scale, 0).tex(1, 1).endVertex();
        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void rotateToPlayer() {
        GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
    }

    public static int renderText(Minecraft mc, int x, int y, String id, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 32);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL_LIGHTING);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL_LIGHTING);
        GL11.glDisable(GL_DEPTH);
        GL11.glDisable(GL_BLEND);
        int width = mc.fontRenderer.getStringWidth(id);
        mc.fontRenderer.drawString(id, x, y, color);
        GL11.glEnable(GL_LIGHTING);
        GL11.glEnable(GL_BLEND);
        GL11.glEnable(GL_DEPTH);
        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL_LIGHTING);
        return width;
    }

    public static void drawBeam(Vector3f S, Vector3f E, Vector3f P, float width) {
        Vector3f PS = Sub(S, P);
        Vector3f SE = Sub(E, S);

        Vector3f normal = Cross(PS, SE);
        //normal = normal.normalize();
        Vector3f half = Mul(normal, width);
        Vector3f p1 = Add(S, half);
        Vector3f p2 = Sub(S, half);
        Vector3f p3 = Add(E, half);
        Vector3f p4 = Sub(E, half);

        drawQuad(Tessellator.getInstance(), p1, p3, p4, p2);
    }

    private static void drawQuad(Tessellator tessellator, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
        int brightness = 240;
        int b1 = brightness >> 16 & 65535;
        int b2 = brightness & 65535;
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.pos(p1.x, p1.getY(), p1.getZ()).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
        buffer.pos(p2.getX(), p2.getY(), p2.getZ()).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
        buffer.pos(p3.getX(), p3.getY(), p3.getZ()).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
        buffer.pos(p4.getX(), p4.getY(), p4.getZ()).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
    }

    private static Vector3f Cross(Vector3f a, Vector3f b) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;
        return new Vector3f(x, y, z);
    }

    private static Vector3f Sub(Vector3f a, Vector3f b) {
        return new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    private static Vector3f Add(Vector3f a, Vector3f b) {
        return new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    private static Vector3f Mul(Vector3f a, float f) {
        return new Vector3f(a.x * f, a.y * f, a.z * f);
    }

    public static void renderHighLightedBlocksOutline(BufferBuilder buffer, float mx, float my, float mz, float r,
                                                      float g, float b, float a) {
        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
    }
    private void drawFire(BufferBuilder buffer, double size, double offset) {
        buffer.pos(offset, -size, -size).tex(0, 0).endVertex();
        buffer.pos(offset, -size, size).tex(0, 1).endVertex();
        buffer.pos(offset, size, size).tex(1, 1).endVertex();
        buffer.pos(offset, size, -size).tex(1, 0).endVertex();
    }
    public void renderFire(FirePythonHeavyGun fphg, EntityPlayer entityPlayer){
        ITextureObject textureObject=new LayeredTexture("fire");//todo
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureObject.getGlTextureId());
        Tessellator tessellator=Tessellator.getInstance();
        BufferBuilder buffer=tessellator.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        for (int i = 0; i <= 5; i++) {
            double r = Math.random();
            double g = Math.random();
            double b = 0.5f + (Math.random() * 0.5f);
            GL11.glColor4d(r, g, b, 1);
            double offset = 2.5 - (double) i;
            double size = 10 * (0.5 + (Math.random()));
            drawFire(buffer,size,offset);
        }
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
}



