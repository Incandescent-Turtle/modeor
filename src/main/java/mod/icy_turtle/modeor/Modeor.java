package mod.icy_turtle.modeor;

import mod.icy_turtle.modeor.entity.MeteorEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class Modeor implements ModInitializer
{
	public static final String MOD_ID = "modeor";

	@Override
	public void onInitialize()
	{
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("modeor")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                            .executes(Command::executeCommand));
        });
    }
}
