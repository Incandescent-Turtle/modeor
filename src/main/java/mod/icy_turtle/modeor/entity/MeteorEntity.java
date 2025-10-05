package mod.icy_turtle.modeor.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import static mod.icy_turtle.modeor.Units.GRAVITY_BPS;
import static mod.icy_turtle.modeor.Units.KMHToBPS;

public class MeteorEntity extends FireballEntity
{

	public MeteorEntity(EntityType<? extends MeteorEntity> entityType, World world)
	{
		super(entityType, world);
	}

	private float speed=0, gravmax=0, gravacc=0, angle=0;
	;
	public MeteorEntity(EntityType<? extends MeteorEntity> entityType, World world, float speed, float gravmax, float gravacc, float angle)
	{
		super(entityType, world);
		this.speed = speed;
		this.gravmax = gravmax;
		this.gravacc = gravacc;
		this.angle=angle;
	}

	@Override
	public void tick() {
		super.tick();

		//  uses initial angle to update x and y, uses gravity to update y
		double yawRad = Math.toRadians(angle+180);

		double xDir = Math.sin(yawRad);
		double zDir = -Math.cos(yawRad);

		double horizSpeed = KMHToBPS(speed);

		double x = xDir * horizSpeed;
		double z = zDir * horizSpeed;

		var y = Math.max(this.getVelocity().y + GRAVITY_BPS/gravacc, -gravmax);

		this.setVelocity(x, y, z);
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
}