package mod.icy_turtle.modeor;

import mod.icy_turtle.modeor.entity.MeteorEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModEntities
{
	public static final Identifier METEOR_ID = Identifier.of(Modeor.MOD_ID, "meteor");
	public static final RegistryKey<EntityType<?>> METEOR_KEY =
			RegistryKey.of(Registries.ENTITY_TYPE.getKey(), METEOR_ID);
	private static final EntityType<MeteorEntity> METEOR_TYPE_BUILT =
			EntityType.Builder.<MeteorEntity>create(MeteorEntity::new, SpawnGroup.MISC)
					.dimensions(0.75f, 0.75f)
					.build(METEOR_KEY);

	public static final EntityType<MeteorEntity> METEOR =
			Registry.register(Registries.ENTITY_TYPE, METEOR_KEY, METEOR_TYPE_BUILT);

}
