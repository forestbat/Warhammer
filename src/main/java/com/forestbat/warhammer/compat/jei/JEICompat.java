package com.forestbat.warhammer.compat.jei;

import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class JEICompat implements IGuiIngredientGroup,ICraftingGridHelper{
    @Override
    public void init(int slotIndex, boolean input, int xPosition, int yPosition) {
       // if(getGuiIngredients()<=9);
           // setInputs(IGuiItemStackGroup.class,WarhammerJeiPlugin.iJeiHelpers.getStackHelper().expandRecipeItemStackInputs(),3,3);
    }

    @Override
    public void init(int slotIndex, boolean input, IIngredientRenderer ingredientRenderer, int xPosition,
                     int yPosition, int width, int height, int xPadding, int yPadding) {

    }

    @Override
    public void set(IIngredients ingredients) {

    }

    @Override
    public void set(int slotIndex, @Nullable Object ingredient) {

    }

    @Override
    public void set(int slotIndex, @Nullable List ingredients) {

    }

    @Override
    public void setBackground(int slotIndex, IDrawable background) {

    }

    @Override
    public Map<Integer, ? extends IGuiIngredient<ItemStack>> getGuiIngredients() {
        return null;
    }

    @Override
    public <T> void setInputs(IGuiIngredientGroup<T> ingredientGroup, List<List<T>> inputs) {
        ingredientGroup.getGuiIngredients();
    }

    @Override
    public <T> void setInputs(IGuiIngredientGroup<T> ingredientGroup, List<List<T>> inputs, int width, int height) {

    }

    @Override
    public void setOverrideDisplayFocus(@Nullable IFocus focus) {

    }

    @Override
    public void addTooltipCallback(ITooltipCallback tooltipCallback) {

    }

    @Deprecated
    @Override
    public void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input) {

    }
    @Deprecated
    @Override
    public void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input, int width, int height) {

    }
    @Deprecated
    @Override
    public void setOutput(IGuiItemStackGroup guiItemStacks, List<ItemStack> output) {

    }
}
