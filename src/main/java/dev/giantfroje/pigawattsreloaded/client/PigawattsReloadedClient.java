package dev.giantfroje.pigawattsreloaded.client;

import dev.giantfroje.pigawattsreloaded.PigawattsReloaded;
import dev.giantfroje.pigawattsreloaded.client.render.TreadmillBlockRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class PigawattsReloadedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(PigawattsReloaded.TREADMILL_BLOCK_ENTITY, TreadmillBlockRenderer::new);
    }
}
