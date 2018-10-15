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
        String s = Util.getFile("" + imageName);
        image = new Image(s);
        width = reqWidth;
        height = image.getHeight() / image.getWidth() * reqWidth;
    }

}
