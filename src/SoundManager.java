import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * A class that loads a sound resource and makes it available for output
 */
public class SoundManager {
    /** Source of sound*/
    private Sound crash;
    private Sound cheer;
    private Music theme;

    /**
     * Initializes the source of sound
     */
    public SoundManager() {

    }

    /**
     * Loads sound resource
     */
    public void init() throws SlickException {
        theme = new Music("res/sound/Testarossa.wav");
        crash = new Sound("res/sound/crash.wav");
        cheer = new Sound("res/sound/cheering.wav");

    }

    /**
     * Destroys sound resource
     */
    public void destroy() {
//        AL.destroy();
    }

    /**
     * Plays sound resource
     */
    public void play(String src) {
        if(src == "theme"){
            theme.loop();
        }
        else if (src == "crash"){
            crash.play();
        }
        else if (src == "cheering"){
            cheer.play();
        }


    }
    public void stop(String src){
        if(src == "theme"){
//            theme.stop();
            theme.fade(1500, 0f, true);
        }
        else if (src == "crash"){
            crash.stop();
        }
        else if (src == "cheering"){
            cheer.stop();
        }

    }

}
