package mod.icy_turtle.modeor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;


public class Command implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("meteor")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Component.literal("Called /foo with no arguments"), false);
                    return 1;
                })));
    }
}