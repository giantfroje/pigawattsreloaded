package dev.giantfroje.pigawattsreloaded.client.render;

import dev.giantfroje.pigawattsreloaded.client.model.PigModel;
import dev.giantfroje.pigawattsreloaded.block.entity.TreadmillBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoBlockRenderer;

public class TreadmillBlockRenderer extends GeoBlockRenderer<TreadmillBlockEntity> {
    public TreadmillBlockRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new PigModel());
    }

    @Override
    public RenderLayer getRenderType(TreadmillBlockEntity treadmill, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(treadmill));
    }
}
