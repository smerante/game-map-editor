package com.studios0110.MapEditor.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.studios0110.MapEditor.Interface.FileOutputTextListener;
import com.studios0110.MapEditor.Splash.Splash;

import java.util.ArrayList;

/**
 * Created by Sam Merante on 2017-11-25.
 */

public class MapHandler implements InputProcessor, GestureListener {
    private static ArrayList<Texture> images = new ArrayList<Texture>();
    private static ArrayList<String> imageNames = new ArrayList<String>();
    private static ArrayList<Vector2> imageMappedPositions = new ArrayList<Vector2>();
    private float width = Splash.screenW, height = Splash.screenH;
    boolean snap,overlap;
    float gridSize;
    Vector2 viewPosition,originGridPoint,viewGridPoints;
    private BitmapFont font;
    boolean showGrid;
    public void reset(){
        images = new ArrayList<Texture>();
        imageNames = new ArrayList<String>();
        imageMappedPositions = new ArrayList<Vector2>();
        width = Splash.screenW;
        height = Splash.screenH;
    }
    public MapHandler(float size){
        width = Splash.screenW;
        height = Splash.screenH;
        showGrid=true;
        overlap=false;
        font = Splash.manager.get("Fonts/MapGrid.fnt");
        viewPosition = new Vector2();
        snap=true;
        gridSize=size;
        viewGridPoints= new Vector2(width/gridSize,height/gridSize);
        originGridPoint = new Vector2(viewGridPoints.x/2,viewGridPoints.y/2);
    }
    public void draw(SpriteBatch batch){
        if(showGrid) {
            drawGraph(batch);
        }

        for(int i=0;i<images.size();i++){
            batch.draw(images.get(i),imageMappedPositions.get(i).x+((Math.round(originGridPoint.x)-viewPosition.x)*gridSize),imageMappedPositions.get(i).y+((Math.round(originGridPoint.y)-viewPosition.y)*gridSize));
        }
    }
    public void update(){
        snap = AssetHandler.gridSnap;
        overlap = AssetHandler.overlapObjects;
    }
    private void drawGraph(SpriteBatch batch){
        //Q1 : Top Right
        int x = (int) viewPosition.x;
        int y = (int) viewPosition.y;
        for (int w = Math.round(originGridPoint.x); w <= viewGridPoints.x; w++) {
            for (int h = Math.round(originGridPoint.y); h <= viewGridPoints.y; h++) {
                font.draw(batch, "(" + x * (int) gridSize + "," + y * (int) gridSize + ")", w * gridSize, h * gridSize);
                y++;
            }
            x++;
            y = (int) viewPosition.y;
        }

        //Q2 : Bottom Right
        x = (int) viewPosition.x;
        y = (int) viewPosition.y;
        for (int w = Math.round(originGridPoint.x); w <= viewGridPoints.x; w++) {
            for (int h = Math.round(originGridPoint.y); h >= 0; h--) {
                font.draw(batch, "(" + x * (int) gridSize + "," + y * (int) gridSize + ")", w * gridSize, h * gridSize);
                y--;
            }
            x++;
            y = (int) viewPosition.y;
        }
        //Q3 : Bottom Left
        x = (int) viewPosition.x;
        y = (int) viewPosition.y;
        for (int w = Math.round(originGridPoint.x); w >= 0; w--) {
            for (int h = Math.round(originGridPoint.y); h >= 0; h--) {
                font.draw(batch, "(" + x * (int) gridSize + "," + y * (int) gridSize + ")", w * gridSize, h * gridSize);
                y--;
            }
            x--;
            y = (int) viewPosition.y;
        }
        //Q4 : Top Left
        x = (int) viewPosition.x;
        y = (int) viewPosition.y;
        for (int w = Math.round(originGridPoint.x); w >= 0; w--) {
            for (int h = Math.round(originGridPoint.y); h <= viewGridPoints.y; h++) {
                font.draw(batch, "(" + x * (int) gridSize + "," + y * (int) gridSize + ")", w * gridSize, h * gridSize);
                y++;
            }
            x--;
            y = (int) viewPosition.y;
        }
    }
    public void draw(ShapeRenderer shapes){
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.GREEN);
        shapes.circle((Math.round(originGridPoint.x)-viewPosition.x)*gridSize,(Math.round(originGridPoint.y)-viewPosition.y)*gridSize,5);
        if(showGrid) {
            shapes.setColor(Color.WHITE);
            for (int w = 0; w <= viewGridPoints.x; w++) {
                shapes.rect(gridSize * w, 0, 2, height);
            }
            for (int h = 0; h <= viewGridPoints.y; h++) {
                shapes.rect(0, gridSize * h, width, 2);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            viewPosition.y = viewPosition.y+1;
        }
        if(keycode == Input.Keys.S){
            viewPosition.y = viewPosition.y-1;
        }
        if(keycode == Input.Keys.A){
            viewPosition.x = viewPosition.x-1;
        }
        if(keycode == Input.Keys.D){
            viewPosition.x = viewPosition.x+1;
        }
        if(keycode == Input.Keys.F1){
            showGrid=!showGrid;
        }
        //Gdx.app.log("MapPos","("+viewPosition.x*(int)gridSize+","+viewPosition.y*(int)gridSize+")");
        return false;
    }


    @Override
    public boolean keyUp(int keycode) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean keyTyped(char character) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean scrolled(int amount) {/*************InputListener**************/
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {/*************GestureListener**************/
    if(x<=AssetHandler.GUI_RIGHT){
        return false;
    }
        float mappedX = ((Math.round(originGridPoint.x) - viewPosition.x) * gridSize);
        float mappedY = ((Math.round(originGridPoint.y) - viewPosition.y) * gridSize);
        float gameMapXPosClicked = (x - mappedX);
        float flipedY = height - y;
        float gameMapYPosClicked = (flipedY - mappedY);
        int snappedPosX = 0;
        int snappedPosY = 0;
        if (gameMapXPosClicked <= 0)
            snappedPosX = -(int) ((Math.abs(gameMapXPosClicked) - (Math.abs(gameMapXPosClicked) % gridSize)) + gridSize);
        else
            snappedPosX = (int) (gameMapXPosClicked - (gameMapXPosClicked % gridSize));

        if (gameMapYPosClicked <= 0)
            snappedPosY = -(int) ((Math.abs(gameMapYPosClicked) - (Math.abs(gameMapYPosClicked) % gridSize)) + gridSize);
        else
            snappedPosY = (int) (gameMapYPosClicked - (gameMapYPosClicked % gridSize));

        if(button==0) {
            boolean collide=false;
            for(int i=0;i<images.size();i++) {
                if (checkCollide(new Vector2(gameMapXPosClicked, gameMapYPosClicked), imageMappedPositions.get(i), images.get(i).getWidth(), images.get(i).getHeight())) {
                    collide=true;
                    break;
                }
            }
            if(!collide || overlap) {
                images.add(AssetHandler.images.get(AssetHandler.currentAsset));
                imageNames.add(AssetHandler.imageNames.get(AssetHandler.currentAsset));
                if (snap) {
                    imageMappedPositions.add(new Vector2(snappedPosX, snappedPosY));
                } else {
                    imageMappedPositions.add(new Vector2(gameMapXPosClicked, gameMapYPosClicked));
                }
            }
        }
        if(button==1){
            for(int i=0;i<images.size();i++){
                if(checkCollide(new Vector2(gameMapXPosClicked, gameMapYPosClicked),imageMappedPositions.get(i),images.get(i).getWidth(),images.get(i).getHeight())){
                    images.remove(i);
                    imageNames.remove(i);
                    imageMappedPositions.remove(i);
                }
            }
        }
        return false;
    }
    public boolean checkCollide(Vector2 clickPos, Vector2 imgPos, float imgW, float imgH){
        if(clickPos.x > imgPos.x && clickPos.x < imgPos.x+imgW){
            if(clickPos.y>imgPos.y && clickPos.y < imgPos.y+imgH){
                return true;
            }
        }
        return false;
    }
    public void printMap(String fileName){
        FileHandle fH = Gdx.files.local(fileName+"_"+imageMappedPositions.size()+"_pics.gamemap");
        fH.delete();
        System.out.println("Output of file: "+fileName+"_"+imageMappedPositions.size()+"_pics.gamemap");
        for(int i = 0; i < imageMappedPositions.size(); i++){
            String name = imageNames.get(i).substring(0,imageNames.get(i).indexOf("."));
            fH.writeString(name+":"+imageMappedPositions.get(i).x + ":"+imageMappedPositions.get(i).y+"\n",true);
            System.out.println(name+":"+imageMappedPositions.get(i).x + ":"+imageMappedPositions.get(i).y);
        }
    }

    @Override
    public boolean longPress(float x, float y) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {/*************GestureListener**************/
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {/*************GestureListener**************/
        return false;
    }
}
