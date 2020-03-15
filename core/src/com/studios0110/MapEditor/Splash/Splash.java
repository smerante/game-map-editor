package com.studios0110.MapEditor.Splash;

/*
Sam Merante: Splash loading screen, loads assets
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.studios0110.MapEditor.screens.MainScreen;

public class Splash implements Screen
{
	public static AssetManager manager = new AssetManager();
	public static float GRAVITY = -2300.0f,FLOOR = 90;
	private Stage stage = new Stage();
	private ShapeRenderer shapeBatch = new ShapeRenderer();
	private SpriteBatch batch = new SpriteBatch();
	public static OrthographicCamera camera = new OrthographicCamera();
	public static int screenW = 1500, screenH = 800;
    private boolean[] done;
	private void loadSplashScreens()
	{
		Image splashScreen = new Image(new Texture("splash/splash1.png"));
		Image splashScreen2 = new Image(new Texture("splash/splash2.png"));
		splashScreen.setWidth(Gdx.graphics.getWidth());
		splashScreen.setHeight(Gdx.graphics.getHeight());
		splashScreen2.setWidth(Gdx.graphics.getWidth());
		splashScreen2.setHeight(Gdx.graphics.getHeight());
        float delayTime = 0.1f;
        splashScreen.addAction(Actions.sequence(
	    		 Actions.alpha(0),
	    		 Actions.fadeIn(3f* delayTime),
	    		 Actions.delay(3f* delayTime),
	    		Actions.run
	    		(
	    		 new Runnable() 
	    	        {
	    	            @Override
	    	            public void run() 
	    	            {
	    	            	done[0] = true;
	    	            }
	    	        }
	    		)
           ));
	    
	    splashScreen2.addAction(Actions.sequence(
	    		Actions.alpha(0),
	    		Actions.delay(3f* delayTime),
	    		Actions.fadeIn(1.5f* delayTime),
	    		Actions.fadeOut(1.5f* delayTime)
		        ));
	    
		stage.addActor(splashScreen);
	    stage.addActor(splashScreen2);
	    	
	}
	
	@Override
	public void show() {
		camera.setToOrtho(false,screenW,screenH);
		done = new boolean[2];
		done[0] = false;
		done[1] = false;
		loadSplashScreens();
		//AssestManager
		manager.load("Fonts/Font.png",Texture.class);
		manager.load("Fonts/Font.fnt",BitmapFont.class);
		manager.load("Fonts/MapGrid.png",Texture.class);
		manager.load("Fonts/MapGrid.fnt",BitmapFont.class);
		manager.load("ui/checkBox.png",Texture.class);
		manager.load("ui/checkBoxPushed.png",Texture.class);
		//AssestManager
		
		
	}
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0,0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(manager.update())//if manager is done uploading then we can finish
			done[1] = true;
	    shapeBatch.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
		stage.act();
		stage.draw();
		shapeBatch.begin(ShapeType.Filled);
			shapeBatch.setColor(Color.DARK_GRAY);
			shapeBatch.rect(screenW/2 - 120, 385, 200, 17);
			shapeBatch.setColor(Color.GREEN);
			shapeBatch.rect(screenW/2 - 120, 384, (manager.getProgress()*200), 20);
		shapeBatch.end();
		if(done[0] && done[1]){
    	 ((Game)Gdx.app.getApplicationListener()).setScreen(new MainScreen());
		}
		
		
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
