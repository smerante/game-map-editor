package com.studios0110.MapEditor.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.studios0110.MapEditor.Interface.Button;
import com.studios0110.MapEditor.Splash.Splash;

import java.util.ArrayList;

/**
 * Created by Sam Merante on 2017-11-25.
 */

public class AssetHandler implements InputProcessor{

    boolean loaded,startedLoading;
    FileHandle imageDirectory;
    public static ArrayList<Texture> images = new ArrayList<Texture>();
    public static ArrayList<String> imageNames = new ArrayList<String>();
    public static int numberOfAssets,currentAsset;
    private float width = Splash.screenW, height = Splash.screenH;
    private BitmapFont font;
    private Button grid,overlapObj;
    public static boolean gridSnap,overlapObjects;
    public static final float GUI_RIGHT = 250;
    public AssetHandler(String path){
        imageDirectory = Gdx.files.local(path);
        font = Splash.manager.get("Fonts/Font.fnt");
        loaded = false;
        startedLoading = false;
        numberOfAssets = 0;
        currentAsset = 0;
        grid = new Button("checkBox",new Vector2(120,100));
        grid.setAsCheckBox();
        gridSnap=false;
        overlapObj = new Button("checkBox",new Vector2(180,200));
        overlapObj.setAsCheckBox();
        overlapObjects =false;
    }

    private void initAssets(){
        if(startedLoading){
            if(!loaded){
                loadAssets(imageDirectory);
                loaded=true;
                numberOfAssets = images.size();
            }
        }else{
            startedLoading=true;
        }
    }
    public void addToProccessor(InputMultiplexer mp){
        mp.addProcessor(new GestureDetector(grid));
        mp.addProcessor(new GestureDetector(overlapObj));
    }
    private void loadAssets(FileHandle file){
        if(file.isDirectory()){
            for (FileHandle entry: file.list()) {
                loadAssets(entry);;
            }
        }else{
            if(file.name().contains(".png")
                    ||file.name().contains(".gif")
                    ||file.name().contains(".jpg")
                    ||file.name().contains(".jpeg")) {
                images.add(new Texture(file));
                imageNames.add(file.name());
            }
        }

    }
    public void draw(ShapeRenderer shapes){
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.DARK_GRAY);
        shapes.rect(0,0,GUI_RIGHT,height);
        shapes.setColor(Color.WHITE);
        shapes.rect(25,height-150,200,132);
    }
    public void draw(SpriteBatch batch){
        if(!loaded)
            font.draw(batch, "Loading...",50,height-84);
        else
        {
            batch.draw(images.get(currentAsset),25,height-150,200,132);
            font.draw(batch, ""+imageNames.get(currentAsset),0,height-150);
            font.draw(batch,"Change Assets: Scroll",10,410);
            font.draw(batch,"Move Map: (wasd)",10,380);
            font.draw(batch,"F1: Toggle Grid",10,350);
            font.draw(batch,"F2: Print Map",10,320);
            font.draw(batch,"F3: Clear Map",10,290);
            font.draw(batch,"F4: Grid Size",10,260);
        }
        grid.draw(batch);
        font.draw(batch, "Grid Snap",10,120);
        overlapObj.draw(batch);
        font.draw(batch, "Overlap Objects",10,220);

    }

    public void update(){
        initAssets();
        gridSnap=grid.clicked;
        overlapObjects=overlapObj.clicked;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP || keycode == Input.Keys.RIGHT)
            currentAsset = (currentAsset+1)%numberOfAssets;
        if(keycode == Input.Keys.DOWN || keycode == Input.Keys.LEFT) {
            if(currentAsset==0)
                currentAsset=numberOfAssets-1;
            else
                currentAsset = (currentAsset - 1) % numberOfAssets;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if(amount>0)
            currentAsset = (currentAsset+1)%numberOfAssets;
        if(amount<0) {
            if(currentAsset==0)
                currentAsset=numberOfAssets-1;
            else
                currentAsset = (currentAsset - 1) % numberOfAssets;
        }
        return false;
    }
}
