import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/** A class that implements the state where the game is played */
public class PlayState extends BasicGameState {
    public static int speed = 5;
    /** Map location */
    private final String MAP_PATH = "res/map/fullmap.tmx";
    /** Current x-position */
    int currentX = 0;
    /** Game map */
    private TiledMap map;
    /** Camera that moves the map */
    private Camera camera;
    /** User-controlled object */
    private Player player;
    /** State ID of the playable game */
    private int stateID;
    /** The shortest time possible to move the width of the screen once in seconds */
    private int secondsPerWindow = 1;
    /** The object for the progress bar */
    private ProgressBar progBar;
    /** The object for the game logic */
    private GameLogic logic;
    /** Flag that is set when the player is moving */
    private boolean isMoving = false;
    /** Flag that checks to see if the game needs to be restarted or resumed */
    private boolean needsRestart = false;
    /** Variable that helps with smooth speed adjustments */
    private long speedTime = 0;
    /** Flag that checks if the game is over */
    private boolean isDone = false;

    /**
     * Sets game to playable state
     *
     * @param stateID The ID of the state
     */
    public PlayState(int stateID) {
        super();
        this.stateID = stateID;
    }

    /**
     * Sets the player graphic, position, shape, and loads the map
     *
     * @param gc  The game container that handles the game loop, fps recording and managing the input system
     * @param sbg The current State Based Game.
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        Image playerImage = new Image("res/character/bike.png");
        Vector2f playerPos = new Vector2f(50, 300);
        Shape playerShape = new Rectangle(0, 0, playerImage.getWidth(), playerImage.getHeight());


        //load map
        map = new TiledMap(MAP_PATH);
        camera = new Camera(gc, this.map);
        // add player and progress bar to map
        player = new Player("GauchoRunner", playerImage, playerPos, playerShape, 5);
        progBar = new ProgressBar(250, 20);
        logic = new GameLogic();

        logic.playSound("theme");

        //reset values
        secondsPerWindow = 1;
        isMoving = false;
        needsRestart = false;
        speedTime = 0;
        isDone = false;
        currentX = 0;

    }

    /**
     * Draws map and player and displays time in seconds
     *
     * @param gc  The game container that handles the game loop, fps recording and managing the input system
     * @param sbg The current State Based Game this button is a part of
     * @param g   The graphics context to draw images on the screen
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        camera.drawMap(0, 0);
        player.render(g);
//        g.draw(player.getCollisionShape());

        progBar.update(currentX, camera.mapWidth);
        progBar.render();

        /*
        //Scoreboard
        g.setColor(Color.blue);
        g.fillRoundRect(0, 0, 225, 100, 10);
        g.setColor(Color.white);
        */

        logic.render(g);
    }

    /**
     * Updates time, player position, and camera location
     *
     * @param gc    The game container that handles the game loop, fps recording and managing the input system
     * @param sbg   The current State Based Game this button is a part of
     * @param delta Time in milliseconds that assists in time left and smooth speed adjustments
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        if (needsRestart)
            this.restart(gc, sbg);

        //int speed = camera.mapWidth / (eventsPerSecond * secondsPerWindow * windowsPerMap);
        Input input = gc.getInput();

        speedTime += delta;

        // Enter Pause Screen if user presses escape
        if (input.isKeyDown(Input.KEY_ESCAPE))
            sbg.enterState(3);
        else if (input.isKeyDown(Input.KEY_R))
            this.restart(gc, sbg);
        else if (input.isKeyDown(Input.KEY_UP)) {
            player.setPosition(new Vector2f(player.getPosition().getX(), player.getPosition().getY() - player.getSpeed()));
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.setPosition(new Vector2f(player.getPosition().getX(), player.getPosition().getY() + player.getSpeed()));
        }
        currentX = currentX + speed;

        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (speedTime >= 100) {
                speedTime = 0;
                if (speed > 0) {
                    speed--;
                }
                System.out.println(speed);
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            isMoving = true;
            if (speedTime >= 250) {
                speedTime = 0;
                if (speed < 100) {
                    speed++;
                }
                System.out.println(speed);
            }
        } else {
            isMoving = false;
        }
        // Check bounds
        if (player.getPosition().getY() < 150) {
            player.setPosition(new Vector2f(player.getPosition().getX(), 150));
        } else if (player.getPosition().getY() > 540) {
            player.setPosition(new Vector2f(player.getPosition().getX(), 540));
        }
        //check to see if player is on the dirt
        if ((player.getPosition().getY() < 210) && (speed >= 3)) {
            speed = 3;
        } else if ((player.getPosition().getY() > 485) && (speed >= 3)) {
            speed = 3;
        }


        logic.update(speed, isMoving, player, delta);
        //check to see if collision happened
        if (logic.getIsColliding()) {
            speed = 3;
        }
        if (camera.cameraX > 68050 && !isDone) {
            isDone = true;
            logic.stopSound("theme");
            logic.playSound("cheering");
            speed = 2;
        }
        if (camera.cameraX > 69500) {
            EndPlayState endPlayState = new EndPlayState(4, logic.getScore(), false);//4 = playstate
            sbg.addState(endPlayState);
            logic.stopSound("theme");
            sbg.enterState(4);
        }
        if (logic.getGameOver()) {
            EndPlayState endPlayState = new EndPlayState(4, logic.getScore(), true);//4 = playstate
            sbg.addState(endPlayState);
            logic.stopSound("theme");
            logic.playSound("boo");
            sbg.enterState(4);
        }


        camera.centerOn(currentX, 0);
        camera.translateGraphics();
    }

    /** Restarts the game */
    public void restart() {
        //TODO: implement reset() methods in the following classes:
        //camera.reset();
        //player.reset();
        //logic.reset();
        //hud.reset();
        this.needsRestart = true;
    }

    /** Restarts the game ( using init() ) */
    public void restart(GameContainer gc, StateBasedGame sbg) {
        //TODO: implement init()-indepenent restart()
        System.out.println("Restarting PlayState");
        try {
            this.init(gc, sbg);
        } catch (SlickException ex) {
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Gets state ID of the play state
     *
     * @return returns the current state id
     */
    @Override
    public int getID() {
        return this.stateID;
    }
}
