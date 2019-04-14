package com.forestbat.warhammer.stuff;

import com.forestbat.warhammer.tileentity.PlayerReinforcer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;
import static com.forestbat.warhammer.configs.WarhammerConfig.KEY_LEFT_HAND;

public class AttributeCellAnnihilateGun implements IAttributeInstance {
    private EntityPlayer player;
    public AttributeCellAnnihilateGun(EntityPlayer player){
        this.player=player;
    }
    long timer = Keyboard.getEventNanoseconds();
    @Nonnull
    public IAttribute getAttribute() {
        return new RangedAttribute(null, "attribute_cell_annihilate_gun", 100, 20, 4096);
    }

    @Override
    public double getBaseValue() {
        if (Keyboard.getEventKey() == KEY_LEFT_HAND && timer>1e8)
            return 20 * timer / 2e7;
        else return 0;
    }

    public void setBaseValue(double baseValue) {
        if (Keyboard.getEventKey() == KEY_LEFT_HAND && timer>1e8)
           setBaseValue(20 * timer / 2e8);
    }

    @Override
    @Nonnull
    public Collection<AttributeModifier> getModifiersByOperation(int operation) {
        return new ModifiableAttributeInstance(new AttributeMap(),getAttribute()).getModifiersByOperation(0);
    }

    @Nullable
    @Override
    public AttributeModifier getModifier(UUID uuid) {
        return new AttributeModifier("cellAnnihilateGunModifier", getBaseValue(), 0);
    }

    @Override
    @Nullable
    public Collection<AttributeModifier> getModifiers() {
        return player.getEntityAttribute(new AttributeCellAnnihilateGun(player).getAttribute()).getModifiers();
    }

    @Override
    public boolean hasModifier(AttributeModifier modifier) {
        return true;
    }

    @Override
    public void applyModifier(AttributeModifier modifier) {

    }

    @Override
    public void removeModifier(AttributeModifier modifier) {

    }

    @Override
    public void removeModifier(@Nonnull UUID uuid) {

    }

    @Override
    public void removeAllModifiers() {

    }

    @Override
    public double getAttributeValue() {
        return getBaseValue();
    }

    public void attackTargetEntityWithACAG(EntityPlayer player, Entity targetEntity) {
        if(!PlayerReinforcer.SUPER_STRONG){
            player.setHealth(player.getHealth()-(float)getBaseValue());
        }
        double harm = player.getEntityAttribute(new AttributeCellAnnihilateGun(player).getAttribute()).getAttributeValue();
        targetEntity.setFire(1);
        targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)harm);
        player.setLastAttackedEntity(targetEntity);
        if (targetEntity instanceof MultiPartEntityPart) {
            IEntityMultiPart ientitymultipart = ((MultiPartEntityPart)targetEntity).parent;
            if (ientitymultipart instanceof EntityLivingBase) {
                targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(player),(float)harm);
                player.world.createExplosion(targetEntity,targetEntity.posX,targetEntity.posY,targetEntity.posZ,4,true);
            }
        }
        if (targetEntity instanceof EntityLivingBase) {
            player.addStat(StatList.DAMAGE_DEALT, Math.round(2));
            if (player.world instanceof WorldServer) {
                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR,
                        targetEntity.posX, targetEntity.posY + (double) (targetEntity.height * 0.5F), targetEntity.posZ,
                        5, 0.1D, 0.0D, 0.1D, 0.2D);
                player.world.createExplosion(targetEntity,targetEntity.posX,targetEntity.posY,targetEntity.posZ,4,true);
            }
        }
    }
}


