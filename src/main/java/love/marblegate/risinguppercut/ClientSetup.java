package love.marblegate.risinguppercut;

import love.marblegate.risinguppercut.client.renderer.BlankRenderer;
import love.marblegate.risinguppercut.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.RISING_UPPERCUT_WATCHER,(BlankRenderer::new));
        EntityRendererRegistry.register(EntityRegistry.ROCKET_PUNCH_IMPACT_WATCHER,(BlankRenderer::new));
        EntityRendererRegistry.register(EntityRegistry.ROCKET_PUNCH_PROCESS_WATCHER,(BlankRenderer::new));
    }
}
