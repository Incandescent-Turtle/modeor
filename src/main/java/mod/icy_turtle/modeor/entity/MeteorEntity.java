package mod.icy_turtle.modeor.entity;

import mod.icy_turtle.modeor.data.MeteorData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import static mod.icy_turtle.modeor.Units.GRAVITY_BPS;
import static mod.icy_turtle.modeor.Units.KMHToBPS;

public class MeteorEntity extends FireballEntity
{
	private float speed=0, gravmax=0, gravacc=0, angle=0;
	private double startingY = 300;
	private final ServerBossBar bossBar;

	private final MeteorData data;
	public MeteorEntity(MeteorData data, EntityType<? extends MeteorEntity> entityType, World world)
	{
		super(entityType, world);
		this.bossBar = new ServerBossBar(Text.literal("☄ Meteor Approaching!").formatted(Formatting.RED), BossBar.Color.RED, BossBar.Style.PROGRESS);
		this.bossBar.setPercent(1.0f); // start full
		this.bossBar.setVisible(true);
		this.data = data;
		this.setCustomName(Text.literal(data.name));
	}

	public MeteorEntity(EntityType<? extends MeteorEntity> entityType, World world)
	{
		this(MeteorData.DEFAULT, entityType, world);
	}

	public MeteorEntity(MeteorData data, EntityType<? extends MeteorEntity> entityType, World world, float speed, float gravmax, float gravacc, float angle)
	{
		this(data, entityType, world);
		this.speed = speed;
		this.gravmax = gravmax;
		this.gravacc = gravacc;
		this.angle=angle;
	}

	@Override
	public void tick() {
		super.tick();
		var world = getEntityWorld();

		if (!world.isClient() && world instanceof ServerWorld)
		{
			// uses initial angle to update x and y, uses gravity to update y
			double yawRad = Math.toRadians(angle+180);

			double xDir = Math.sin(yawRad);
			double zDir = -Math.cos(yawRad);

			double horizSpeed = KMHToBPS(speed);

			double x = xDir * horizSpeed;
			double z = zDir * horizSpeed;

			var y = Math.max(this.getVelocity().y + GRAVITY_BPS/gravacc, -gravmax);

			this.setVelocity(x, y, z);

			// altitude boss bar
			double altitude = this.getY();
			float percent = (float)Math.max(0.0, Math.min(1.0, (altitude-63) / (startingY-63)));
			this.bossBar.setPercent(percent);

			this.bossBar.setName(Text.literal("☄ " + this.getCustomName().getString() + " (" + (int)this.getY() + " m)"));
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult)
	{
		var world = getEntityWorld();
		if (!world.isClient()) {
			world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f, true, World.ExplosionSourceType.MOB);
			this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public void onSpawnPacket(EntitySpawnS2CPacket entitySpawnS2CPacket)
	{
		super.onSpawnPacket(entitySpawnS2CPacket);
		startingY = this.getY();
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		bossBar.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		bossBar.removePlayer(player);
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);
		this.bossBar.clearPlayers();
	}
}