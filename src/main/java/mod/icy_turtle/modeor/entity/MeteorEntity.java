package mod.icy_turtle.modeor.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;

public class MeteorEntity extends FireballEntity
{

	public MeteorEntity(EntityType<? extends MeteorEntity> entityType, World world)
	{
		super(entityType, world);
	}
}