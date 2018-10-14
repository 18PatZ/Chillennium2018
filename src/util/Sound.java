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
    private boolean playSong = false;
    Media boom = new Media(new File("sound/boom.mp3").toURI().toString());
    Media ow = new Media(new File("sound/ow.mp3").toURI().toString());
    Media hitwolf = new Media(new File("sound/hitwolf.mp3").toURI().toString());
    Media howl = new Media(new File("sound/howl.mp3").toURI().toString());
    Media oof = new Media(new File("sound/oof.mp3").toURI().toString());
    
    public Sound()
    {
        
    }



    //



        
    public void oof(){
        MediaPlayer Oof = new MediaPlayer(oof);
        Oof.play();
    }
    
    public void howl(){
        MediaPlayer Howl = new MediaPlayer(howl);
        Howl.play();
    }
    public void hitwolf(){
        MediaPlayer hitWolf = new MediaPlayer(hitwolf);
        hitWolf.play();
    }
  
    public void boom(){
        MediaPlayer Boom = new MediaPlayer(boom);
        Boom.play();
    }
        public void ow(){
        MediaPlayer Ow = new MediaPlayer(ow);
        Ow.play();
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
