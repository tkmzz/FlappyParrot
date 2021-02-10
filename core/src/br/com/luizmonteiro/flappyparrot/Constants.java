package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.Gdx;

public class Constants {

    public static int screenX = Gdx.graphics.getWidth();
    public static int screenY = Gdx.graphics.getHeight();

    public static float pipeVelX = -0.3f*screenX;

    public static int parrotRadius = (int) (0.06f*screenY);

    public static int parrotInitX = (int) (0.2f*screenX);

    public static float decSpeedY = screenY/1.5f;

    public static float impulse = screenY/5;

    public static int pipeW = (int) (0.5f*screenX);

    public static float pipesTime = 3f;

    public static int posMax = (int) (0.7f*screenY);

    public static int gap = (int) (0.2f*screenY);

    public static int btnSize = (int) (0.4f*screenX);
    public static int btnX = (int) (0.3f*screenX);
    public static int btnY = (screenY - btnSize)/2;

}
