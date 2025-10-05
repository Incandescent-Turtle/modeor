package mod.icy_turtle.modeor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

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
