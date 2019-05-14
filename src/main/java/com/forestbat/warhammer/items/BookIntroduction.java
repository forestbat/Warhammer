package com.forestbat.warhammer.items;

import com.forestbat.warhammer.stuff.WarhammerRenderHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBook;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.forestbat.warhammer.gui.WidgetBase.colorBackgroundBevelBright;

public class BookIntroduction extends ItemBook {
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //todo openGui
        WarhammerRenderHelper.drawTexturedModelRect(166,166,colorBackgroundBevelBright,colorBackgroundBevelBright,200,200);
        String string=FontRenderer.getFormatFromString("");
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
