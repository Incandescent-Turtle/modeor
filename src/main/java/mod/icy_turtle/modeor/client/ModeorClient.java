package mod.icy_turtle.modeor.client;

import mod.icy_turtle.modeor.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ModeorClient implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		EntityRendererRegistry.register(ModEntities.METEOR, FlyingItemEntityRenderer::new);

	}
}
