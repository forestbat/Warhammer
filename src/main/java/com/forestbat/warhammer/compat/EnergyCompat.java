package com.forestbat.warhammer.compat;

import appeng.api.config.Actionable;
import appeng.api.networking.energy.IAEPowerStorage;
import com.forestbat.warhammer.tileentity.PipesConnector;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;
import vazkii.botania.api.mana.IManaPool;

import static com.forestbat.warhammer.configs.WarhammerConfig.*;
import static com.google.common.math.IntMath.pow;

public class EnergyCompat {
    @Optional.Method(modid = "ic2")
    public void icEnergyConvert(PipesConnector pipesConnector,TileEntity tileEntity) {
        if (pipesConnector.isConnectedWith(tileEntity) && tileEntity instanceof IEnergySink) {
        double energyAccept = Math.min(pipesConnector.getEnergyStored()*WithIC2, ((IEnergySink) tileEntity).getDemandedEnergy());
            ((IEnergySink) tileEntity).injectEnergy(null, energyAccept,
                    pow(2, 2 * ((IEnergySink) tileEntity).getSinkTier() + 5));
        }
        if(pipesConnector.isConnectedWith(tileEntity) && tileEntity instanceof IEnergySource){
            double energyEmit=((IEnergySource)tileEntity).getOfferedEnergy();
            if(pipesConnector.getEnergyStored()<pipesConnector.getMaxEnergyStored())
                pipesConnector.receiveEnergy((int)energyEmit/WithIC2,false);
        }
    }

    /*@Optional.Method(modid = "botania")
    public void botaniaEnergyConvert(PipesConnector pipesConnector,TileEntity tileEntity){
        if(pipesConnector.isConnectedWith(tileEntity)&&tileEntity instanceof IManaPool) {
            int manaAccept = Math.min(pipesConnector.getMaxEnergyStored() * WithMana, ((IManaPool)tileEntity).getCurrentMana());
            pipesConnector.extractEnergy(manaAccept,false);
            ((IManaPool)tileEntity).recieveMana(manaAccept);
        }
    }*/

    @Optional.Method(modid = "redstoneflux")
    public void rfEnergyConvert(PipesConnector pipesConnector,TileEntity tileEntity){
        if(pipesConnector.isConnectedWith(tileEntity) && tileEntity instanceof IEnergyStorage) {
            int rfAccept = Math.min(pipesConnector.getEnergyStored() * WithRF,
                    ((IEnergyStorage) tileEntity).getMaxEnergyStored() - ((IEnergyStorage) tileEntity).getEnergyStored());
            ((IEnergyStorage) tileEntity).receiveEnergy(rfAccept,false);
            pipesConnector.extractEnergy(rfAccept,false);
        }
    }
    @Optional.Method(modid = "appliedenergistics")
    public void aeEnergyConvert(PipesConnector pipesConnector,TileEntity tileEntity){
        if(pipesConnector.isConnectedWith(tileEntity)&& tileEntity instanceof IAEPowerStorage){
            double aeAccept=Math.min(pipesConnector.getEnergyStored()*WithAE,
                    ((IAEPowerStorage)tileEntity).getAEMaxPower()-((IAEPowerStorage) tileEntity).getAECurrentPower());
            pipesConnector.extractEnergy((int)aeAccept,false);
            ((IAEPowerStorage)tileEntity).injectAEPower(aeAccept, Actionable.MODULATE);
        }
    }
    /*@Optional.Method(modid = "GalaticraftAPI")
    public void gcEnergyConvert(PipesConnector pipesConnector,TileEntity tileEntity){
        if(pipesConnector.isConnectedWith(tileEntity)&& tileEntity instanceof IEnergyStorageGC){
            double gcAccept=Math.min(pipesConnector.getEnergyStored()*WithGC,
                    ((IEnergyStorageGC)tileEntity).getCapacityGC()-((IEnergyStorageGC) tileEntity).getEnergyStoredGC());
            ((IEnergyStorageGC)tileEntity).receiveEnergyGC(gcAccept, false);
        }
    }*/
}

