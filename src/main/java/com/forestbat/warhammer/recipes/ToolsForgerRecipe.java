package com.forestbat.warhammer.recipes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static net.minecraftforge.common.ForgeHooks.getContainerItem;

//TODO This class should be rewritten
public class ToolsForgerRecipe implements IShapedRecipe {
    public final int recipeWidth;
    public final int recipeHeight;
    public final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final String group;
    private static Iterator<IRecipe> iRecipeIterator = CraftingManager.REGISTRY.iterator();

    public ToolsForgerRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.group = group;
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = ingredients;
        this.recipeOutput = result;
    }

    public String getGroup() {
        return this.group;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, getContainerItem(itemstack));
        }
        return nonnulllist;
    }

    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        return false;
    }

    public IRecipe setRegistryName(ResourceLocation name) {
        return new ToolsForgerRecipe(null, 5, 5, recipeItems, recipeOutput);
    }

    public ResourceLocation getRegistryName() {
        return new ResourceLocation("forger_recipes");
    }

    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }

    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.getRecipeOutput().copy();
    }

    public int getRecipeWidth() {
        return 5;
    }

    public int getRecipeHeight() {
        return 5;
    }

    public static ToolsForgerRecipe deserialize(JsonObject resourceLocation) {
        String s = JsonUtils.getString(resourceLocation, "group", "");
        Map<String, Ingredient> map = deserializeKey(JsonUtils.getJsonObject(resourceLocation, "key"));
        String[] astring = patternFromJson(JsonUtils.getJsonArray(resourceLocation, "pattern"));
        int i = astring[0].length();
        int j = astring.length;
        NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, map, i, j);
        ItemStack itemstack = deserializeItem(JsonUtils.getJsonObject(resourceLocation, "result"));
        return new ToolsForgerRecipe(null, 5, 5, nonnulllist, itemstack);
    }

    public static NonNullList<Ingredient> deserializeIngredients(String[] itemIDs, Map<String, Ingredient> stringIngredientMap,
                                                                 int width, int height) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(width * height, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(stringIngredientMap.keySet());
        set.remove(" ");
        for (int i = 0; i < itemIDs.length; ++i) {
            for (int j = 0; j < itemIDs[i].length(); ++j) {
                String s = itemIDs[i].substring(j, j + 1);
                Ingredient ingredient = stringIngredientMap.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }
                set.remove(s);
                nonnulllist.set(j + width * i, ingredient);
            }
        }
        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    public static String[] patternFromJson(JsonArray jsonArray) {
        String[] astring = new String[jsonArray.size()];
        if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for (int i = 0; i < astring.length; ++i) {
                String s = JsonUtils.getString(jsonArray.get(i), "pattern[" + i + "]");
                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }
                astring[i] = s;
            }
            return astring;
        }
    }

    public static Map<String, Ingredient> deserializeKey(JsonObject jsonObject) {
        Map<String, Ingredient> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if ((entry.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' " +
                        "is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            map.put(entry.getKey(), deserializeIngredient(entry.getValue()));
        }
        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static Ingredient deserializeIngredient(@Nullable JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray == null)
            throw new JsonSyntaxException("There's nothing in json");
        if (jsonArray.size() == 0)
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
        if (jsonElement.isJsonObject()) {
            return Ingredient.fromStacks(deserializeItem(jsonElement.getAsJsonObject()));
        } else {
            ItemStack[] aitemstack = new ItemStack[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                aitemstack[i] = deserializeItem(JsonUtils.getJsonObject(jsonArray.get(i), "item"));
            }
            return Ingredient.fromStacks(aitemstack);
        }
    }

    public static ItemStack deserializeItem(JsonObject resourceLocation) {
        if (JsonUtils.getJsonObject(resourceLocation, "key").get("count") == null)
            return ShapedRecipes.deserializeItem(resourceLocation, false);
        else return ShapedRecipes.deserializeItem(resourceLocation, true);
    }

    public static IRecipe parseRecipeJson(JsonObject jsonObject) {
        Iterator<IRecipe> iRecipeIterator=CraftingManager.REGISTRY.iterator();
        while (iRecipeIterator.hasNext()) {
            IRecipe iRecipe=iRecipeIterator.next();
            if (iRecipe instanceof ShapedRecipes) {
                return ToolsForgerRecipe.deserialize(jsonObject);
            } else if (iRecipe instanceof ShapelessRecipes) {
                return ToolsForgerRecipe.deserialize(jsonObject);
            } else if (iRecipe instanceof ToolsForgerRecipe) {
                return ToolsForgerRecipe.deserialize(jsonObject);
            } else {
                throw new JsonSyntaxException("Invalid or unsupported recipe type");
            }
        }
        return null;
    }
}
