package com.forestbat.warhammer.compat.jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import java.util.List;

import static com.forestbat.warhammer.Warhammer.MOD_ID;

public class WarhammerCategory implements IRecipeCategory {
    @Override
    public String getUid() {
        return null;
    }

    @Override
    public String getModName() {
        return MOD_ID;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public IDrawable getBackground() {
        return null;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

    }
}
