package com.forestbat.warhammer.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class WarhammerRecipesWrapper implements IRecipeWrapper {
    private final List inputs = Lists.newArrayList();
    private IRecipe recipe=CraftingManager.REGISTRY.getObject(new ResourceLocation("forger_recipes"));
    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        List<List<ItemStack>> list = WarhammerJeiPlugin.iJeiHelpers.getStackHelper().expandRecipeItemStackInputs(inputs);
        ingredients.setInputLists(ItemStack.class, list);
        //ingredients.setInputLists(VanillaTypes.ITEM, list)
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }
}
