package TowerDefense.thegame.drawer.shop.icon;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class AbstractIconDrawer extends Node implements IconDrawer {
    private Image icon;
    private ImageView imageView;
    private String label;
    private String price;

    public AbstractIconDrawer(String filePath, String label, String price) throws IOException {
        this.icon = new Image(new FileInputStream(filePath));
        this.imageView = new ImageView(icon);
        this.label = label;
        this.price = price;
    }

    public ImageView getImageView() { return imageView; }
    public String getLabel() { return label; }
    public String getPrice() { return price; }

    @Override
    public void draw(GraphicsContext graphicsContext, double screenPosX, double screenPosY, double screenWidth, double screenHeight) {
        graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);
        graphicsContext.drawImage(icon, screenPosX, screenPosY, screenWidth, screenHeight);
    }
}