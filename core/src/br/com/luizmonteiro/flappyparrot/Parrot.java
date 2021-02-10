package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static br.com.luizmonteiro.flappyparrot.Constants.*;

public class Parrot {

    public Circle body;
    private Texture[] frames;
    private float auxFrames;
    private Vector2 speed;

    public Parrot(int posx, int posy){
        body = new Circle(posx, posy, parrotRadius);

        frames = new Texture[6];
        for(int i = 1 ; i<=6 ; i++){
            frames[i-1] = new Texture("felpudo/felpudoVoa" + i + ".png");
        }

        speed = new Vector2(0, 0);
    }

    public void drawParrot(SpriteBatch batch){
        batch.draw(frames[(int) auxFrames%6], body.x - body.radius, body.y - body.radius,
                body.radius*2, body.radius*2);
    }

    public int updateParrot(float time){
        auxFrames += 6*time;

        body.x += speed.x * time;
        body.y += speed.y * time;
        speed.y -= decSpeedY * time;

        if(body.y + body.radius >= screenY){
            body.y = screenY - body.radius;
            speed.y = -impulse;
        } else if(body.y - body.radius <= 0){
            body.y = body.radius;
            speed.y = impulse;
        }

        if(body.x + body.radius <= 0){
            return 1;
        }
        return 0;

    }

    public void impulse() {
        speed.y += impulse;
    }

    public void gameOver(){
        speed.x = 2*pipeVelX;
        speed.y = 0;
    }

    public void restart(int posX, int posY){
        body = new Circle(posX, posY, parrotRadius);
        speed = new Vector2(0, 0);
    }

    public void dispose(){
        for(int i=0 ; i<=5 ; i++){
            frames[i].dispose();
        }
    }
}
