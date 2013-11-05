import org.newdawn.slick.*;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class GauchoRunner extends BasicGame{

    private TiledMap map;
    private Player player;

    public GauchoRunner(){
        super("Gaucho Runner");
    }

	public static void main(String[] argv) {
//		MainMenu mainMenu = new MainMenu();
//		mainMenu.start();
//		System.exit(0);

        try{
            AppGameContainer app = new AppGameContainer(new GauchoRunner());
            app.setVSync(true);
            app.setTargetFrameRate(60);
            app.setDisplayMode(800,600, false);
            app.start();
        }
        catch(SlickException e){
            e.printStackTrace();
        }
	}
    @Override
    public void init(GameContainer container) throws SlickException
    {
           Image playerImage = new Image("res/character/bike.png");
           Vector2f playerPos = new Vector2f(400, 300);
           Shape playerShape = new Rectangle(playerPos.x, playerPos.y, playerImage.getWidth(), playerImage.getHeight());
           playerShape.setLocation(playerPos);

           map = new TiledMap("res/map/DemoMap.tmx");
           player = new Player("GauchoRunner", playerImage, playerPos, playerShape);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        //TODO: CAN THIS BE MORE EFFICIENT?
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP))
        {
           player.setPosition(new Vector2f(player.getPosition().getX(),player.getPosition().getY() + 5));
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            player.setPosition(new Vector2f(player.getPosition().getX(),player.getPosition().getY() - 5));
        }
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            player.setPosition(new Vector2f(player.getPosition().getX() - 5,player.getPosition().getY()));
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            player.setPosition(new Vector2f(player.getPosition().getX()+5,player.getPosition().getY()));
        }
    }
    public void render(GameContainer container, Graphics g) throws SlickException
    {
           map.render(0,0);
            // TODO - FIGURE OUT HOW TO RENDER PLAYER
           //player.render(g);

    }
}