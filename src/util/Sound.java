/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author start
 */
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound 
{
    private boolean running = false;
    private Thread thread;

    public Sound()
    {
        
    }



    //
    private boolean playSong = false;
    Media boom = new Media(new File("sound/boom.mp3").toURI().toString());
    MediaPlayer Boom = new MediaPlayer(boom);

    

  
    public void boom(){
        Boom.play();
    }
//    public void boom(){
//        InputStream sound;
//        try
//        {
//            this.clip = AudioSystem.getClip();
//        }
//        catch(LineUnavailableException e)
//        {
//            e.printStackTrace();
//        }
//        url = new File("sound/boom.mp3").toURI().toString();
//        this.playSong = true;
//        this.inputStream = null;
//    }
}
