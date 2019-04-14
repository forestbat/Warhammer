package com.forestbat.warhammer.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.gui.inventory.GuiContainer;

@JEIPlugin
public class WarhammerJeiPlugin implements IModPlugin,IRecipeWrapperFactory<WarhammerJeiPlugin> {
    public static IJeiHelpers iJeiHelpers;
    @Override
    public void register(IModRegistry registry) {
        iJeiHelpers=registry.getJeiHelpers();
        iJeiHelpers.getVanillaRecipeFactory();
        registry.addRecipeClickArea(GuiContainer.class,40,40,60,60);
        registry.handleRecipes(WarhammerJeiPlugin.class,new WarhammerJeiPlugin(),"Tools_Forger");
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(WarhammerJeiPlugin recipe) {
        return new WarhammerRecipesWrapper();
    }
}
