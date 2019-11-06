package TowerDefense.thegame.drawer;

import TowerDefense.thegame.Config;
import TowerDefense.thegame.drawer.bullet.*;
import TowerDefense.thegame.drawer.bullet.HighDamageBulletDrawer;
import TowerDefense.thegame.drawer.enemy.NormalEnemyDrawer;
import TowerDefense.thegame.entity.bullet.*;
import TowerDefense.thegame.entity.enemy.NormalEnemy;
import TowerDefense.thegame.entity.enemy.path.Path;
import TowerDefense.utilities.Pair;
import javafx.scene.canvas.GraphicsContext;
import TowerDefense.thegame.GameField;
import TowerDefense.thegame.entity.GameEntity;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameDrawer {
	private static final List<Class<?>> ENTITY_DRAWING_ORDER = List.of(
		NormalBullet.class,
		ExplodingBullet.class,
		FastBullet.class,
		FrozenBullet.class,
		BurningBullet.class,
		HighDamageBulletDrawer.class,
		NormalEnemy.class
	);

	private static final Map<Class<? extends GameEntity>, EntityDrawer> ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
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

	public GameDrawer(GraphicsContext graphicsContext, GameField gameField) {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;
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

		for (final GameEntity entity : entities) {
			final EntityDrawer drawer = getEntityDrawer(entity);

			if (drawer != null) {
				drawer.draw(this.graphicsContext, entity, entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
			}
		}
	}
}
