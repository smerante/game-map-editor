package com.studios0110.MapEditor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.studios0110.MapEditor.Handlers.AssetHandler;
import com.studios0110.MapEditor.Handlers.MapHandler;
import com.studios0110.MapEditor.Interface.FileOutputTextListener;
import com.studios0110.MapEditor.Splash.Splash;

/*
Sam Merante: This Starter Screen is a menu to get to other screens.
 */
public class MainScreen implements NewScreenInterface {
    AssetHandler assetHandler;
    MapHandler mapHandler;
    private BitmapFont font;
    private float showFPSDelay,FPS;
    private float width = Splash.screenW, height = Splash.screenH;
    private boolean showDebug,initResize;
    FileOutputTextListener fO,gridSize;

    public void show() {
        System.out.println("Construct Menu Screen");
        Splash.camera.zoom=1;
        Splash.camera.update();
        font = Splash.manager.get("Fonts/Font.fnt");
        FPS = 60;
        showDebug = true;
        fO = new FileOutputTextListener();
        gridSize = new FileOutputTextListener();
        assetHandler = new AssetHandler("/MapAssets");
        mapHandler = new MapHandler(64);
        Gdx.input.setCatchBackKey(true);
        mp.addProcessor(assetHandler);
        assetHandler.addToProccessor(mp);
        mp.addProcessor(mapHandler);
        mp.addProcessor(new GestureDetector(mapHandler));
        Gdx.input.setInputProcessor(mp);
        initResize = false;// set the default input processor to the multiplex processor from NewScreenInterface
      /* menuScreen = Splash.manager.get("ui/menuScreen.png");
		 start = new Button("playButton",new Vector2((width/2)-256,(height/2)-64-200));
		 mp.addProcessor(new GestureDetector(start));*/
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(Splash.camera.combined);
        shapes.setProjectionMatrix(Splash.camera.combined);
        shapes.setAutoShapeType(true);

        shapes.begin();
            mapHandler.draw(shapes);
        shapes.end();

        batch.begin();
            mapHandler.draw(batch);
        batch.end();

        shapes.begin();
            assetHandler.draw(shapes);
        shapes.end();

        batch.begin();
            assetHandler.draw(batch);
        batch.end();

        update();
        showFPS(delta);
    }
    private void update(){
        assetHandler.update();
        mapHandler.update();
        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            Gdx.input.getTextInput(fO,"Enter output file name","","example-map");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F3)){
            mapHandler.reset();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F4)){
            Gdx.input.getTextInput(gridSize,"Enter grid size","","64");
        }
        if(!fO.inputFileName.equals("")){
            mapHandler.printMap(fO.inputFileName);
            fO.clear();
        }
        if(!gridSize.inputFileName.equals("")){
            try{
                int size = Integer.parseInt(gridSize.inputFileName);
                assetHandler = new AssetHandler("/MapAssets");
                mapHandler = new MapHandler(size);
                mapHandler.reset();
                mp.clear();
                mp.addProcessor(assetHandler);
                assetHandler.addToProccessor(mp);
                mp.addProcessor(mapHandler);
                mp.addProcessor(new GestureDetector(mapHandler));
                Gdx.input.setInputProcessor(mp);
                Splash.camera.setToOrtho(false,width,height);
            }catch(Exception e){
                e.printStackTrace();
            }
            gridSize.clear();
        }
    }
    private void showFPS(float delta)
    {
            showDebug = (Gdx.input.isKeyJustPressed(Input.Keys.F1) ? !showDebug: showDebug);
            if(showDebug) {
                showFPSDelay += 1 * Gdx.graphics.getDeltaTime();
                if (showFPSDelay >= 0.25) {
                    showFPSDelay = 0;
                    FPS = (int) Math.ceil(1 / delta);
                }
                    batch.begin();
                    font.draw(batch, "Menu - FPS " + (int) FPS, width/2, height-20);
                    batch.end();
            }

    }

    @Override
    public void resize(int width, int height) {
        if(initResize) {
            Splash.screenW = width;
            Splash.screenH = height;
            assetHandler = new AssetHandler("/MapAssets");
            mapHandler = new MapHandler(64);
            mp.clear();
            mp.addProcessor(assetHandler);
            assetHandler.addToProccessor(mp);
            mp.addProcessor(mapHandler);
            mp.addProcessor(new GestureDetector(mapHandler));
            Gdx.input.setInputProcessor(mp);
            Splash.camera.setToOrtho(false,width,height);
        }
        initResize=true;
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}

}
