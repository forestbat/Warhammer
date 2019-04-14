package com.forestbat.warhammer.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class WarhammerLoader implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass(){
        return new String[]{"com.forestbat.warhammer.asm.ChebishevTransformer","com.forestbat.warhammer.asm.ParentTransformer"};
    }
    public String getModContainerClass(){
        return null;
    }
    public String getSetupClass(){
        return null;
    }
    public void injectData(Map<String,Object> data){

    }
    public String getAccessTransformerClass()
    {
        return "net.minecraftforge.fml.common.asm.transformers.AccessTransformer";
    }
}
