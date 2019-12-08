package TowerDefense.thegame.entity.shop;

import TowerDefense.thegame.Config;
import TowerDefense.thegame.GameStage;
import TowerDefense.thegame.drawer.GameDrawer;
import TowerDefense.thegame.drawer.shop.ShopDrawer;
import TowerDefense.thegame.drawer.shop.button.BulletButtonDrawer;
import TowerDefense.thegame.drawer.shop.button.TowerButtonDrawer;
import TowerDefense.thegame.entity.bullet.BurningBullet;
import TowerDefense.thegame.entity.bullet.FrozenBullet;
import TowerDefense.thegame.entity.bullet.NormalBullet;
import TowerDefense.thegame.entity.stage.StageHandler;
import TowerDefense.thegame.entity.tile.tower.AbstractTower;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;

public class ButtonHandler {
    private GameStage gameStage;
    private Pane gamePane;
    private GameDrawer gameDrawer;
    private ShopDrawer shopDrawer;

    public ButtonHandler(GameStage gameStage, Pane gamePane, GameDrawer gameDrawer, ShopDrawer shopDrawer) {
        this.gameStage = gameStage;
        this.gamePane = gamePane;
        this.gameDrawer = gameDrawer;
        this.shopDrawer = shopDrawer;
    }

    public void handleEventTower(TowerButtonDrawer button) {
        button.setOnMousePressed(buttonPressedMouseEvent -> {
            gameDrawer.getStageDrawer().setPlacingTower(true);
            gamePane.setOnMousePressed(gamePressedMouseEvent -> {
                int row = (int) (gamePressedMouseEvent.getY() / Config.TILE_SIZE);
                int column = (int) (gamePressedMouseEvent.getX() / Config.TILE_SIZE);

                if (gameDrawer.getStageLoader().getCurrentLayout(row, column) == 0) {
                    try {
                        gameStage.addEntity(StageHandler.getEntityClass(button.getClass()).getDeclaredConstructor(double.class, double.class).newInstance(column * Config.TILE_SIZE, row * Config.TILE_SIZE));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    gameDrawer.getStageLoader().setCurrentLayout(row, column, 420);
                }

                gameDrawer.getStageDrawer().setPlacingTower(false);
            });
        });

        button.setOnMouseEntered(mouseEnteredEvent -> {
            shopDrawer.setRenderingRemovingTowerFunction(false);
            shopDrawer.setRenderingUpgradingTowerFunction(false);

            shopDrawer.setBulletClass(null);
            shopDrawer.setRenderingBulletStats(false);

            shopDrawer.setTowerClass(StageHandler.getEntityClass(button.getClass()));
            shopDrawer.setRenderingTowerStats(true);
        });
    }

    public void handleEventBullet(BulletButtonDrawer button) {
        button.setOnMousePressed(buttonPressedMouseEvent -> {
            gameDrawer.getStageDrawer().setUpgradingTower(true);
            gamePane.setOnMousePressed(gamePressedMouseEvent -> {
                int row = (int) (gamePressedMouseEvent.getY() / Config.TILE_SIZE);
                int column = (int) (gamePressedMouseEvent.getX() / Config.TILE_SIZE);

                if (gameDrawer.getStageLoader().getCurrentLayout(row, column) == 420) {
                    AbstractTower tower = (AbstractTower) gameStage.getGameEntity(row, column);
                    if (tower != null) {
                        Class bulletClass = StageHandler.getBulletClass(button.getClass());

                        if (gameStage.getMoney() >= 10L) {
                            if (tower.addBullet(bulletClass)) {
                                if (bulletClass == BurningBullet.class) {
                                    gameStage.reduceMoney(Config.BURNING_BULLET_COST);
                                } else if (bulletClass == FrozenBullet.class) {
                                    gameStage.reduceMoney(Config.FROZEN_BULLET_COST);
                                } else if (bulletClass == NormalBullet.class) {
                                    gameStage.reduceMoney(Config.NORMAL_BULLET_COST);
                                }
                            }
                        }
                    }
                }

                gameDrawer.getStageDrawer().setUpgradingTower(false);
            });
        });

        button.setOnMouseEntered(mouseEnteredEvent -> {
            shopDrawer.setRenderingRemovingTowerFunction(false);
            shopDrawer.setRenderingUpgradingTowerFunction(false);

            shopDrawer.setTowerClass(null);
            shopDrawer.setRenderingTowerStats(false);

            shopDrawer.setBulletClass(StageHandler.getBulletClass(button.getClass()));
            shopDrawer.setRenderingBulletStats(true);
        });
    }

    public void handleEventSellingTower(Button button) {
        button.setOnMousePressed(mouseEvent -> {
            gameDrawer.getStageDrawer().setSellingTower(true);
            gamePane.setOnMousePressed(gamePressedMouseEvent -> {
                int row = (int) (gamePressedMouseEvent.getY() / Config.TILE_SIZE);
                int column = (int) (gamePressedMouseEvent.getX() / Config.TILE_SIZE);

                if (gameDrawer.getStageLoader().getCurrentLayout(row, column) == 420) {
                    AbstractTower tower = (AbstractTower) gameStage.getGameEntity(row, column);
                    if (tower != null) {
                        gameStage.removeEntity(tower);
                        gameDrawer.getStageLoader().setCurrentLayout(row, column, 0);
                    }
                }

                gameDrawer.getStageDrawer().setSellingTower(false);
            });
        });

        button.setOnMouseEntered(mouseEnteredEvent -> {
            shopDrawer.setTowerClass(null);
            shopDrawer.setRenderingTowerStats(false);

            shopDrawer.setBulletClass(null);
            shopDrawer.setRenderingBulletStats(false);

            shopDrawer.setRenderingUpgradingTowerFunction(false);
            shopDrawer.setRenderingRemovingTowerFunction(true);
        });
    }

    public void handleEventUpgradingTower(Button button) {
        button.setOnMousePressed(mouseEvent -> {
            gameDrawer.getStageDrawer().setUpgradingTower(true);
            gamePane.setOnMousePressed(gamePressedMouseEvent -> {
                int row = (int) (gamePressedMouseEvent.getY() / Config.TILE_SIZE);
                int column = (int) (gamePressedMouseEvent.getX() / Config.TILE_SIZE);

                if (gameDrawer.getStageLoader().getCurrentLayout(row, column) == 420) {
                    AbstractTower tower = (AbstractTower) gameStage.getGameEntity(row, column);
                    if (tower != null) {
                        tower.upgrade();
                        gameStage.reduceMoney(tower.getUpgradedCost());
                    }
                }

                gameDrawer.getStageDrawer().setUpgradingTower(false);
            });
        });

        button.setOnMouseEntered(mouseEnteredEvent -> {
            shopDrawer.setTowerClass(null);
            shopDrawer.setRenderingTowerStats(false);

            shopDrawer.setBulletClass(null);
            shopDrawer.setRenderingBulletStats(false);

            shopDrawer.setRenderingRemovingTowerFunction(false);
            shopDrawer.setRenderingUpgradingTowerFunction(true);
        });
    }
}
