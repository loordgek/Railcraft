/* 
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import mods.railcraft.api.electricity.GridTools;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricMinecart;
import mods.railcraft.common.plugins.forge.ItemRegistry;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.plugins.forge.*;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.init.Blocks;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ItemElectricMeter extends ItemRailcraft implements IActivationBlockingItem {

    private static Item item;

    public static void register() {
        if (item == null) {
            String tag = "railcraft.tool.electric.meter";
            if (RailcraftConfig.isItemEnabled(tag)) {
                item = new ItemElectricMeter().setUnlocalizedName(tag);
                ItemRegistry.registerItem(item);

                CraftingPlugin.addShapedRecipe(new ItemStack(item),
                        "T T",
                        "BGB",
                        " C ",
                        'B', Blocks.stone_button,
                        'G', Blocks.glass_pane,
                        'C', "ingotCopper",
                        'T', "ingotTin");

                ItemRegistry.registerItemStack(tag, new ItemStack(item));

                LootPlugin.addLootWorkshop(new ItemStack(item), 1, 1, tag);
            }
//            CreeperPlugin.fixCreepers();
        }
    }

    public static ItemStack getItem() {
        if (item == null)
            return null;
        return new ItemStack(item);
    }

    public ItemElectricMeter() {
        setMaxDamage(0);
        setMaxStackSize(1);
        setFull3D();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        EntityPlayer thePlayer = event.entityPlayer;

        Entity entity = event.target;

        ItemStack stack = thePlayer.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof ItemElectricMeter)
            thePlayer.swingItem();

        if (Game.isNotHost(thePlayer.worldObj))
            return;

        if (stack != null && stack.getItem() instanceof ItemElectricMeter)
            if (entity instanceof IElectricMinecart) {
                IElectricMinecart cart = (IElectricMinecart) entity;
                IElectricMinecart.ChargeHandler ch = cart.getChargeHandler();
                ChatPlugin.sendLocalizedChat(thePlayer, "railcraft.gui.electric.meter.charge", ch.getCharge(), ch.getCapacity(), ch.getLosses());
                event.setCanceled(true);
            }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (Game.isNotHost(world))
            return false;
        IElectricGrid gridObject = GridTools.getGridObjectAt(world, x, y, z);
        boolean returnValue = false;
        if (gridObject != null) {
            IElectricGrid.ChargeHandler handler = gridObject.getChargeHandler();
            ChatPlugin.sendLocalizedChat(player, "railcraft.gui.electric.meter.charge", handler.getCharge(), handler.getCapacity(), handler.getLosses());
            returnValue = true;
        }
        return returnValue;
    }

}