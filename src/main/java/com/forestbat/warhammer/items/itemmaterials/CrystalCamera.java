package com.forestbat.warhammer.items.itemmaterials;

import com.forestbat.warhammer.Warhammer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CrystalCamera extends ItemArmor {
    private Minecraft mc;
    public CrystalCamera(ArmorMaterial armorMaterial,int renderIndexIn,EntityEquipmentSlot entityEquipmentSlot){
        super(armorMaterial,renderIndexIn,entityEquipmentSlot);
        setCreativeTab(Warhammer.WARHAMMER);
        setUnlocalizedName("crystal_camera");
    }
    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return ((EntityLivingBase)entity).getItemStackFromSlot(EntityEquipmentSlot.HEAD).equals(stack);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        //todo renderHUD(mc,player,1);
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION));
    }

    public void renderHUD(Minecraft mc,EntityPlayer player){
        this.mc=mc;
        ItemStack itemStack=player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if(isValidArmor(itemStack,EntityEquipmentSlot.HEAD,player)) {
            mc.mcProfiler.startSection("Info crystal_eye");
            AttributeModifier attributeModifier = itemStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).
                    get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).iterator().next();
            FontRenderer fontRenderer = mc.fontRenderer;
            fontRenderer.FONT_HEIGHT = 10;
            fontRenderer.drawString("Your Health:" + player.getHealth(), 15, 15, 255 * 255 * 255, false);
            fontRenderer.drawString("Enemy Health:" + ((EntityLivingBase) mc.pointedEntity).getHealth(), 15, 30, 255 * 255 * 255, false);
            fontRenderer.drawString("Your Attack" + attributeModifier.getAmount(), 15, 45, 255 * 255 * 255, false);
            mc.mcProfiler.endSection();
        }
    }
}
