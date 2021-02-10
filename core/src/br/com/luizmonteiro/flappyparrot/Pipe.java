package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static br.com.luizmonteiro.flappyparrot.Constants.*;

public class Pipe {

    private Texture texture;
    public Rectangle body;
    private boolean top;

    public Pipe(float posX, float posY, boolean top){
        this.top = top;
        if(top){
            body = new Rectangle(posX, posY, pipeW, screenY);
        } else{
            body = new Rectangle(posX, posY-screenY, pipeW, screenY);
        }

        texture = new Texture("cano.png");
    }

    public void drawPipe(SpriteBatch batch){
        batch.draw(texture, body.x, body.y, body.getWidth(), body.getHeight(),
                0, 0, texture.getWidth(), texture.getHeight(), false, top);
    }

    public int updatePipe(float time){

        body.x += pipeVelX * time;
        if(body.x + body.getWidth() <= 0){
            return 1;
        }
        return 0;

    }

    public void dispose(){
        texture.dispose();
    }

}
