package dev.giantfroje.pigawattsreloaded;

import dev.giantfroje.pigawattsreloaded.block.TreadmillBlock;
import dev.giantfroje.pigawattsreloaded.block.entity.TreadmillBlockEntity;
import dev.giantfroje.pigawattsreloaded.item.TreadmillBlockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class PigawattsReloaded implements ModInitializer {
    public static final String MOD_ID = "pigawattsreloaded";

    public static final TreadmillBlock TREADMILL_BLOCK = new TreadmillBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));

    public static BlockEntityType<TreadmillBlockEntity> TREADMILL_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "treadmill"), TREADMILL_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "treadmill"), new TreadmillBlockItem(TREADMILL_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

        TREADMILL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "treadmill"), BlockEntityType.Builder.create(TreadmillBlockEntity::new, TREADMILL_BLOCK).build(null));
    }
}
