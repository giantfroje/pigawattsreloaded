package dev.giantfroje.pigawattsreloaded.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;

public class TreadmillBlockItem extends BlockItem {
    public TreadmillBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (context.getWorld().getBlockState(context.getBlockPos().offset(context.getPlayerFacing(), 1)).canReplace(context)) {
            return super.place(context);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("block.pigawattsreloaded.treadmill.tooltip.0"));
    }
}
