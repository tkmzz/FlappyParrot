package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.math.Rectangle;

import static br.com.luizmonteiro.flappyparrot.Constants.*;

public class Score {

    public Rectangle body;

    public Score(float posX, float posY){
        body = new Rectangle(posX, posY, 10, gap);
    }

    public int updateScore(float time){
        body.x += pipeVelX * time;
        if(body.x + body.getWidth() <= 0){
            return 1;
        }
        return 0;
    }

}
