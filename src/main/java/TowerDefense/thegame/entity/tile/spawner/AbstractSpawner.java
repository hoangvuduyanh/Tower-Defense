package TowerDefense.thegame.entity.tile.spawner;

import TowerDefense.thegame.GameField;
import TowerDefense.thegame.GameStage;
import TowerDefense.thegame.entity.AbstractEntity;
import TowerDefense.thegame.entity.SpawnListener;
import TowerDefense.thegame.entity.UpdatableEntity;
import TowerDefense.thegame.entity.enemy.AbstractEnemy;
import TowerDefense.thegame.entity.enemy.path.Path;
import TowerDefense.thegame.entity.tile.AbstractTile;
import TowerDefense.utilities.Pair;

import javax.annotation.Nonnull;

public abstract class AbstractSpawner<E extends AbstractEnemy> extends AbstractTile implements UpdatableEntity {
    private final double spawningSize;
    @Nonnull private final Class<E> spawningClass;
    private final long spawnInterval;
    private long tickDown;
    private long numOfSpawn;
    private Path path;
    protected AbstractSpawner(double posX, double posY, double width, double height, double spawningSize, @Nonnull Class<E> spawningClass, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(posX, posY, width, height);
        this.spawningSize = spawningSize;
        this.spawningClass = spawningClass;
        this.spawnInterval = spawnInterval;
        this.tickDown = initialDelay;
        this.numOfSpawn = numOfSpawn;
        findPath();
    }
    public void findPath () {
        path = new Path();
        path.addInstruction(Pair.immutableOf(100.0, 1));
        path.addInstruction(Pair.immutableOf(100.0, 3));
        path.addInstruction(Pair.immutableOf(100.0, 0));
        path.addInstruction(Pair.immutableOf(100.0, 2));
    }
    public final void onUpdate(@Nonnull GameField field) {
        System.out.printf("spawn pos = %f %f, size = %f %f, spawningSize = %f, tickdown = %d\n", getPosX(), getPosY(), getWidth(), getHeight(), spawningSize, tickDown);
        this.tickDown -= 1;
        if (tickDown <= 0 && numOfSpawn > 0) {
            numOfSpawn -= 1;
            E newEnemy = doSpawn(getPosX(), getPosY());
            newEnemy.setPath(path);
            field.getEntities().add(newEnemy);
            this.tickDown = spawnInterval;
        }

    }


    @Nonnull
    protected abstract E doSpawn(double posX, double posY);
}