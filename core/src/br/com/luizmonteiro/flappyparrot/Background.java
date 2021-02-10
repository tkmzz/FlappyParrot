package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.luizmonteiro.flappyparrot.Constants.*;

public class Background {

    private Texture texture;

    private float posx1;
    private float posx2;

    public Background(){
        texture = new Texture("fundo.png");
        posx1 = 0;
        posx2 = screenX;
    }


    public void drawBackground(SpriteBatch batch){
        batch.draw(texture, posx1, 0, screenX, screenY);
        batch.draw(texture, posx2, 0, screenX, screenY);
    }

    public void updateBackground(float time){
        posx1 += time*pipeVelX;
        posx2 += time*pipeVelX;

        if(posx1 + screenX <= 0){
            posx1 = screenX;
            posx2 = 0;
        } else if(posx2 + screenX <= 0){
            posx2 = screenX;
            posx1 = 0;
        }
    }

    public void dispose(){
        texture.dispose();
    }
}
