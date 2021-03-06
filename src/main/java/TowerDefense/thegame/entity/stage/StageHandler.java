package TowerDefense.thegame.entity.stage;

import TowerDefense.thegame.drawer.shop.button.*;
import TowerDefense.thegame.entity.bullet.*;
import TowerDefense.thegame.entity.tile.tower.AbstractTower;
import TowerDefense.thegame.entity.tile.tower.MachineGunTower;
import TowerDefense.thegame.entity.tile.tower.NormalTower;
import TowerDefense.thegame.entity.tile.tower.SniperTower;

import java.util.HashMap;
import java.util.Map;

public class StageHandler {
    private static final Map<Class<? extends TowerButtonDrawer>, Class<? extends AbstractTower>> TOWER_GEN_MAP =
            new HashMap<>(Map.ofEntries(
                    Map.entry(NormalGunTowerButtonDrawer.class, NormalTower.class),
                    Map.entry(MachineGunTowerButtonDrawer.class, MachineGunTower.class),
                    Map.entry(SniperGunTowerButtonDrawer.class, SniperTower.class)
            ));

    private static final Map<Class<? extends BulletButtonDrawer>, Class<? extends AbstractBullet>> BULLET_UPDATE_MAP =
            new HashMap<>(Map.ofEntries(
                    Map.entry(NormalBulletButtonDrawer.class, NormalBullet.class),
                    Map.entry(BurningBulletButtonDrawer.class, BurningBullet.class),
                    Map.entry(FrozenBulletButtonDrawer.class, FrozenBullet.class)
                    ));

    public static Class<? extends AbstractTower> getEntityClass(Class<? extends TowerButtonDrawer> buttonClass) {
        return TOWER_GEN_MAP.get(buttonClass);
    }

    public static Class<? extends AbstractBullet> getBulletClass(Class<? extends BulletButtonDrawer> buttonClass) {
        return BULLET_UPDATE_MAP.get(buttonClass);
    }
}
