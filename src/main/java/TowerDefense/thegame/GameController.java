package TowerDefense.thegame;

import TowerDefense.thegame.drawer.GameDrawer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

public class GameController extends AnimationTimer {
    private final GraphicsContext graphicsContext;
    private GameStage gameStage;
    private GameField gameField;
    private GameDrawer gameDrawer;
    private Pane gamePane;

    public GameController(GraphicsContext graphicsContext, Pane gamePane) {
        this.graphicsContext = graphicsContext;
        this.gameStage = new GameStage();
        this.gameField = new GameField(gameStage);
        this.gameDrawer = new GameDrawer(graphicsContext, gameField);
        this.gamePane = gamePane;
    }

    final void closeRequestHandler(WindowEvent windowEvent) {
        stop();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void handle(long l) {
        gameField.handle();
        gameDrawer.render();
    }

    public void start() {
        super.start();
    }

    public GameStage getGameStage() { return gameStage; }
    public Pane getGamePane() { return gamePane; }
    public GameDrawer getGameDrawer() { return gameDrawer; }
}
