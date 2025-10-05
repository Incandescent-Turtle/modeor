package mod.icy_turtle.modeor.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Meteor extends LargeFireball
{
	public Meteor(EntityType<? extends LargeFireball> entityType, Level level)
	{
		super(entityType, level);
	}

	public Meteor(Level level, LivingEntity livingEntity, Vec3 vec3, int i)
	{
		super(level, livingEntity, vec3, i);
	}
}
