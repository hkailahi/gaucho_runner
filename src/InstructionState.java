import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A class that implements the state where instructions are displayed
 */
public class InstructionState extends BasicGameState {
    /** Instructions Menu */
    private Menu instructionsMenu;
    /** Instructions state ID */
    private int stateID;
    /** Starting menu x-position */
    private int startingX = 50;
    /** Starting menu y-position */
    private int startingY = 450;
    /** The space between Menu items */
    private int spaceBetweenItems = 131;
    /** Location of graphic when quit button is selected */
    private final String quitSelected = "res/menu/QuitButton.png";
    /** Location of graphic when quit button is unselected */
    private final String quitUnselected = "res/menu/QuitButtonSelected.png";
    /** Image object of graphic when quit button is selected */
    private Image backUs;
    /** Image object of graphic when quit button is selected */
    private Image backS;

    /**
     * Sets instruction state ID
     * @param stateID
     */
    InstructionState(int stateID) {
        super();
        this.stateID = stateID;
    }

    /**
     * Sets the game container and state based game
     * @param gc
     * @param sbg
     */
    public void init(GameContainer gc, StateBasedGame sbg) {
        initMenu(gc, sbg);
    }

    /**
     * Draws the instructions for the game
     * @param gc
     * @param sbg
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("Instructions", 100, 100);
        instructionsMenu.render(gc, g);
    }

    /**
     * Update the game container and state based game
     * @param gc
     * @param sbg
     * @param i
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

    /**
     * Gets state ID
     * @return
     */
    @Override
    public int getID() {
        return stateID;
    }

    /**
     * Initialized instructions menu with images
     * @param gc
     * @param sbg
     */
    private void initMenu(GameContainer gc, StateBasedGame sbg) {
        instructionsMenu = new Menu(startingX, startingY, spaceBetweenItems);

        // create images for each button
        try {
            backUs = new Image(quitUnselected);
            backS = new Image(quitSelected);
        } catch (SlickException ex) {
            ex.printStackTrace();
            return;
        }
        AnimatedButton play = new AnimatedButton(gc, sbg, backUs, backS, startingX, startingY, 0);

        play.add(new ButtonAction() {
            public void perform() {
                /*currentState = STATES.PLAY;
                sbg.enterState(GauchoRunner.PLAY_STATE_ID);*/
            }
        }
        );

        instructionsMenu.addItem(play);

    }
}
