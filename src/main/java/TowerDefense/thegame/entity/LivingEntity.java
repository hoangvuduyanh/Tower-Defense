package TowerDefense.thegame.entity;

public interface LivingEntity extends DestroyableEntity {
	long getHealth();

	void doEffect(long value);
}
