/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.items;

import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.plugins.forge.CreativePlugin;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.plugins.forge.LootPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class ItemSteelShears extends ItemShears implements IRailcraftItemSimple {

    public ItemSteelShears() {
        setMaxDamage(500);
        setCreativeTab(CreativePlugin.RAILCRAFT_TAB);
    }

    @Override
    public Item getObject() {
        return this;
    }

    @Override
    public void initializeDefinintion() {
        LootPlugin.addLoot(RailcraftItems.SHEARS_STEEL, 1, 1, LootPlugin.Type.TOOL);
    }

    @Override
    public String getUnlocalizedName() {
        return LocalizationPlugin.convertTag(super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName();
    }

    @Override
    public void defineRecipes() {
        CraftingPlugin.addRecipe(new ItemStack(this), false,
                " I",
                "I ",
                'I', "ingotSteel");
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof IShearable)
            return 15;
        return super.getStrVsBlock(stack, state);
    }

}
