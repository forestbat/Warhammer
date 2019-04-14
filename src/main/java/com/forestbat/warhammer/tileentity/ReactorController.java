package com.forestbat.warhammer.tileentity;

import com.forestbat.warhammer.blocks.BlockMachineBase;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class ReactorController extends EntityMachineBase {
    private long PUSH_OUT_SPAN=200;
    private long PULL_IN_SPAN=150;
    private long REACTOR_TEMP;
    private long REACTOR_PRESS;
    private long timer;
    protected boolean OPENING;
    private ReactorController reactorController;

    public boolean isValidReactor(){
        Block reactorCase=new BlockMachineBase(16);
        BlockPos centralPos=reactorController.pos;
        BlockPos[] importantPos={centralPos.up(3),centralPos.down(3),centralPos.west(3),
                centralPos.east(3),centralPos.north(3),centralPos.south(3)};
        for(BlockPos blockPos:importantPos) {
            if (reactorController.world.getBlockState(blockPos).getBlock().equals(reactorCase))
                return true;
        }
        return false;
    }
    public double calculatePower(){
        return (Math.pow(REACTOR_TEMP,0.755)+Math.pow(REACTOR_PRESS,0.545));
    }
    private boolean autoStart(){
        return (REACTOR_TEMP>1e8 || REACTOR_PRESS>1e11)&&OPENING&&isValidReactor();
    }
    public void update(){
        if(autoStart()) {
            timer++;
            pushOut();
            pullIn();
            if(calculatePower()<Integer.MAX_VALUE)
                receiveEnergy((int)calculatePower(),false);
            if(calculatePower()>=Integer.MAX_VALUE)
                receiveEnergy(Integer.MAX_VALUE,false);
        }
        else timer=0;
    }
    public void pushOut(){
        if(autoStart()&&timer%PUSH_OUT_SPAN==0){
            long pushTimer=getWorld().getWorldTime();
            if(timer-pushTimer<60) {
                REACTOR_PRESS -= 1e7;
                REACTOR_TEMP += 2e5;
            }
        }
    }
    public void pullIn() {
        if (autoStart() && timer % PULL_IN_SPAN == 0) {
            long pullTimer = getWorld().getWorldTime();
            while (timer - pullTimer < 60) {
                REACTOR_TEMP += 1e5;
                REACTOR_PRESS += 6e6;
            }
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        super.receiveEnergy(maxReceive,simulate);
        if(maxReceive<Integer.MAX_VALUE)
        return (int)calculatePower();
        else return Integer.MAX_VALUE;
    }
}
