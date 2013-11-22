import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that renders buttons and listens for mouse activity.
 */
public class AnimatedButton extends MouseOverArea {
    /** Checks to see if button is activated*/
    private boolean activated = false;
    /** Checks to see if new input is available*/
    private boolean lastMouseOver = false;
    /** The image that is displayed when no mouse is over the button*/
    private final Image normalImage;
    /** The image that is displayed when the mouse is over the button*/
    private final Image mouseOverImage;
    /** The id of the current state*/
    private int stateID;
    /** Used to enter game states*/
    private StateBasedGame sbg;
    /** An array list that contains actions for the buttons*/
    private final List<ButtonAction> actions = new ArrayList<ButtonAction>();
    /** Should we restart on entering the button's state ID */
    private boolean restart = false;

    /** The constructor for the Animated Button class
     * @param guiContext
     * @param sbg
     * @param normalImage
     * @param mouseOverImage
     * @param x
     * @param y
     */
    public AnimatedButton(GUIContext guiContext, StateBasedGame sbg, Image normalImage, Image mouseOverImage, int x, int y, int stateID) {
        super(guiContext, normalImage, x, y);
        super.setMouseOverImage(mouseOverImage);
        super.setMouseDownImage(mouseOverImage);
        this.sbg = sbg;
        this.stateID = stateID;
        this.normalImage = normalImage;
        this.mouseOverImage = mouseOverImage;
        //this.gc = gameContainer;

    }

    /** The constructor for the Animated Button class
     * @param guiContext
     * @param sbg
     * @param normalImage
     * @param mouseOverImage
     * @param x
     * @param y
     * @param restart
     */
    public AnimatedButton(GUIContext guiContext, StateBasedGame sbg, Image normalImage, Image mouseOverImage, int x, int y, int stateID, boolean restart) {
        super(guiContext, normalImage, x, y);
        super.setMouseOverImage(mouseOverImage);
        super.setMouseDownImage(mouseOverImage);
        this.sbg = sbg;
        this.stateID = stateID;
        this.normalImage = normalImage;
        this.mouseOverImage = mouseOverImage;
        //this.gc = gameContainer;
        this.restart = restart;

    }

    /** Checks to see if the mouse has moved
     * @param oldx
     * @param oldy
     * @param newx
     * @param newy
     */
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (sbg.getCurrentStateID() == stateID) {
            if (isMouseOver() && !lastMouseOver && !isActivated()) {

                lastMouseOver = true;
            } else if (!isMouseOver()) {
                lastMouseOver = false;
            }
        }
        super.mouseMoved(oldx, oldy, newx, newy);
    }

    /** Returns current status of activated */
    public boolean isActivated() {
        return activated;
    }

    /** Sets the value of activated */
    protected void setActivated(boolean b) {
        activated = b;
    }

    /** Checks to see if mouse has been clicked
     * @param button
     * @param x
     * @param y
     * @param clickCount
     * */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
//        if (isMouseOver() && sbg.getCurrentStateID() == stateID)
//        {
//            sbg.enterState(2);    //TODO this is what i added. not sure what the for loop is for. Also change from 1 playState            activated = !activated;
//            for(ButtonAction action: actions)
//            {
//                action.perform();
//            }
//        }
//        super.mouseClicked(button, x, y, clickCount);
        if (isMouseOver()) {
            if (restart) {
                //sbg.getState(1).restart();
            }
            sbg.enterState(stateID, new FadeOutTransition(Color.black, 1000), new FadeInTransition(Color.black, 1000));

        }
    }

    /**
     * Renders the button image
     * @param gc
     * @param g
     */
    public void render(GUIContext gc, Graphics g) {
        if (activated) {
            g.drawImage(normalImage, this.getX(), this.getY());
//            super.render(gc, g);
        } else {
            g.drawImage(mouseOverImage, this.getX(), this.getY());
//            super.render(gc, g);
        }
    }

    /**
     * Adds a button action
     * @param action
     */
    public void add(ButtonAction action) {
        actions.add(action);
    }

}
