package mod.icy_turtle.modeor;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import mod.icy_turtle.modeor.data.DataFetcher;
import mod.icy_turtle.modeor.data.MeteorData;
import mod.icy_turtle.modeor.entity.MeteorEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Command {
    public static int executeCommand(CommandContext<ServerCommandSource> context) {

        ServerCommandSource src = context.getSource();

        ServerPlayerEntity player = src.getPlayer();
        assert player != null;

        MeteorData nextMeteorData = DataFetcher.getNextMeteor();

        src.sendFeedback(() -> Text.literal(nextMeteorData.toString()), false);

        MeteorEntity meteor = new MeteorEntity(nextMeteorData, ModEntities.METEOR, src.getWorld(), FloatArgumentType.getFloat(context, "speed"),FloatArgumentType.getFloat(context, "gravmax"),FloatArgumentType.getFloat(context, "gravacc"), player.getYaw());

        // spawns meteor 2 blocks in front of player and 2 blocks above eye height
        float yaw = player.getYaw();
        double yawRad = Math.toRadians(-yaw - 180);
        double xDir = Math.sin(yawRad);
        double zDir = Math.cos(yawRad);

        double spawnDistance = 2.0;
        double spawnX = player.getX() + xDir * spawnDistance;
        double spawnY = player.getY() + player.getStandingEyeHeight() + 2;
        double spawnZ = player.getZ() + zDir * spawnDistance;

        meteor.setPosition(spawnX, spawnY, spawnZ);

        meteor.setYaw(player.getYaw());
        src.getWorld().spawnEntity(meteor);

        return 1;
    }
}