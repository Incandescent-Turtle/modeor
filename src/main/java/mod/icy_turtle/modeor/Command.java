package mod.icy_turtle.modeor;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import mod.icy_turtle.modeor.entity.MeteorEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Command {
    public static int executeCommand(CommandContext<ServerCommandSource> context) {

        ServerCommandSource src = context.getSource();

        ServerPlayerEntity player = src.getPlayer();
        assert player != null;

        MeteorEntity meteor = new MeteorEntity(ModEntities.METEOR, src.getWorld(), FloatArgumentType.getFloat(context, "speed"),FloatArgumentType.getFloat(context, "gravmax"),FloatArgumentType.getFloat(context, "gravacc"), player.getYaw());

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


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.nasa.gov/neo/rest/v1/feed?start_date=2025-10-05&end_date=2025-10-05&api_key=GfkcwkM5OhbvDk8JWlbB8vDvbeFgcnfMNCKrrdpY"))
                .header("Accept", "application/json")
                .build();

        String apiResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        src.sendFeedback(() -> Text.literal(apiResponse), false);
        return 1;
    }
}