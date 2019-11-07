package TowerDefense.thegame.drawer;

import TowerDefense.thegame.Config;
import TowerDefense.thegame.GameField;
import TowerDefense.thegame.drawer.bullet.*;
import TowerDefense.thegame.drawer.stage.StageDrawer;
import TowerDefense.thegame.entity.GameEntity;
import TowerDefense.thegame.entity.bullet.*;
import TowerDefense.thegame.entity.enemy.NormalEnemy;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameDrawer {
	private static final List<Class<?>> ENTITY_DRAWING_ORDER = List.of(
		MachineGunTower.class,
		NormalTower.class,
		SniperTower.class,

		MachineGun.class,
		NormalGun.class,
		SniperGun.class,

		NormalBullet.class,
		ExplodingBullet.class,
		FastBullet.class,
		FrozenBullet.class,
		BurningBullet.class,
		HighDamageBulletDrawer.class

	);

	private static final Map<Class<? extends GameEntity>, EntityDrawer> ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(MachineGunTower.class, new MachineTowerDrawer()),
			Map.entry(NormalTower.class, new NormalTowerDrawer()),
			Map.entry(SniperTower.class, new SniperTowerDrawer()),

			Map.entry(MachineGun.class, new MachineGunDrawer()),
			Map.entry(NormalGun.class, new NormalGunDrawer()),
			Map.entry(SniperGun.class, new SniperGunDrawer()),

			Map.entry(ExplodingBullet.class, new ExplodingBulletDrawer()),
			Map.entry(NormalBullet.class, new NormalBulletDrawer()),
			Map.entry(BurningBullet.class, new BurningBulletDrawer()),
			Map.entry(FrozenBullet.class, new FrozenBulletDrawer()),
			Map.entry(HighDamageBullet.class, new HighDamageBulletDrawer()),
			Map.entry(FastBullet.class, new FastBulletDrawer()),
			Map.entry(NormalEnemy.class, new NormalEnemyDrawer())
	));

	private final GraphicsContext graphicsContext;
	private GameField gameField;
	private StageDrawer stageDrawer;

	public GameDrawer(GraphicsContext graphicsContext, GameField gameField) {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;

		try {
			this.stageDrawer = new StageDrawer(graphicsContext, "src/main/java/TowerDefense/thegame/drawer/stage/TestStage.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int entityDrawingOrderComparator(GameEntity entityA, GameEntity entityB) {
		final int compareOrder = Integer.compare(
				ENTITY_DRAWING_ORDER.indexOf(entityA.getClass()),
				ENTITY_DRAWING_ORDER.indexOf(entityB.getClass())
		);
		if (compareOrder != 0) return compareOrder;
		final int comparePosX = Double.compare(entityA.getPosX(), entityB.getPosX());
		if (comparePosX != 0) return comparePosX;
		final int comparePosY = Double.compare(entityA.getPosY(), entityB.getPosY());
		if (comparePosY != 0) return comparePosY;
		final int compareWidth = Double.compare(entityA.getWidth(), entityB.getWidth());
		if (compareWidth != 0) return compareWidth;
		return Double.compare(entityA.getHeight(), entityB.getHeight());
	}

	private static EntityDrawer getEntityDrawer(GameEntity entity) {
		return ENTITY_DRAWER_MAP.get(entity.getClass());
	}

	public final void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	public final void render() {
		final GameField gameField = this.gameField;
		final List<GameEntity> entities = new ArrayList<>(gameField.getEntities());
		graphicsContext.clearRect(0.0, 0.0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

		try {
			stageDrawer.draw();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (final GameEntity entity : entities) {
			final EntityDrawer drawer = getEntityDrawer(entity);
			if (drawer != null) {
				if (entity instanceof RotatableEntity) {
					drawer.draw(this.graphicsContext, entity, entity.getPosX(), entity.getPosY(), entity.getWidth(),
							entity.getHeight(), ((RotatableEntity) entity).getDegreeRotate());
				} else {
					drawer.draw(this.graphicsContext, entity, entity.getPosX(), entity.getPosY(), entity.getWidth(),
							entity.getHeight(), 0);
				}
			}
		}
	}
}
