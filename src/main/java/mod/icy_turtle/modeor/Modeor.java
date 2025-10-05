package mod.icy_turtle.modeor;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

import static net.minecraft.server.command.CommandManager.argument;

public class Modeor implements ModInitializer
{
    public static final String MOD_ID = "modeor";

    @Override
    public void onInitialize()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("meteor")
                    .then(argument("mass", IntegerArgumentType.integer())
                            .then(argument("angle", IntegerArgumentType.integer())
                                    .then(argument("speed", IntegerArgumentType.integer())
                                            .executes(Command::executeCommand)))));
        });
    }
}