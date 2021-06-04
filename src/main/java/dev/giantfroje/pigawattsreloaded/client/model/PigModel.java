package dev.giantfroje.pigawattsreloaded.client.model;

import dev.giantfroje.pigawattsreloaded.PigawattsReloaded;
import dev.giantfroje.pigawattsreloaded.block.TreadmillBlock;
import dev.giantfroje.pigawattsreloaded.block.entity.TreadmillBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PigModel extends AnimatedGeoModel<TreadmillBlockEntity> {
    @Override
    public Identifier getModelLocation(TreadmillBlockEntity treadmill) {
        if (treadmill.getWorld() != null) {
            BlockState state = treadmill.getWorld().getBlockState(treadmill.getPos());
            if (state.get(TreadmillBlock.ON) && !state.get(TreadmillBlock.FRONT)) {
                return new Identifier(PigawattsReloaded.MOD_ID, "geo/pig.geo.json");
            }
        }
        return new Identifier(PigawattsReloaded.MOD_ID, "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TreadmillBlockEntity treadmill) {
        return new Identifier(PigawattsReloaded.MOD_ID, "textures/block/pig.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TreadmillBlockEntity treadmill) {
        return new Identifier(PigawattsReloaded.MOD_ID, "animations/pig.animation.json");
    }
}
