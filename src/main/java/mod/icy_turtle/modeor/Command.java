package mod.icy_turtle.modeor;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import mod.icy_turtle.modeor.entity.MeteorEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Command {
    public static int executeCommand(CommandContext<ServerCommandSource> context) {

        ServerCommandSource src = context.getSource();

        src.sendFeedback(() -> Text.literal(
                String.valueOf(IntegerArgumentType.getInteger(context, "mass"))), true);
        src.sendFeedback(() -> Text.literal(
                String.valueOf(IntegerArgumentType.getInteger(context, "angle"))), true);
        src.sendFeedback(() -> Text.literal(
                String.valueOf(IntegerArgumentType.getInteger(context, "speed"))), true);

        ServerPlayerEntity player = src.getPlayer();

        MeteorEntity meteor = new MeteorEntity(ModEntities.METEOR, src.getWorld());

        assert player != null;
        // todo probably check what direction player is facing to always spawn it in their view
        // player.getYaw() -> left and right
        // player.getPitch() -> up and down

        meteor.setPosition(player.getX() + 10, player.getY() + 20, player.getZ());

        src.getWorld().spawnEntity(meteor);
        return 1;
    }
}