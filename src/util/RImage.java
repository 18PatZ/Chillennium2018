package util;

import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;

@Getter
public class RImage {

    private Image image;
    private double width;
    private double height;

    public RImage(String imageName, double reqWidth){
        image = new Image(new File("images/" + imageName).toURI().toString());
        width = reqWidth;
        height = image.getHeight() / image.getWidth() * reqWidth;
    }

}
