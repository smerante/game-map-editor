package com.studios0110.MapEditor.Interface;

import com.badlogic.gdx.Input;

/**
 * Created by Sam Merante on 2018-01-21.
 */

public class FileOutputTextListener implements Input.TextInputListener {
    public String inputFileName;
    public FileOutputTextListener(){
        inputFileName="";
    }
    @Override
    public void input(String text) {
        inputFileName=text;
    }
    public void clear(){
        inputFileName="";
    }
    @Override
    public void canceled() {

    }
}

