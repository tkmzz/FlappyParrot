package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    private Sound fly;
    private Sound hit;
    private Sound score;

    public Sounds(){
        fly = Gdx.audio.newSound(Gdx.files.internal("sons/somVoa.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sons/somHit.mp3"));
        score = Gdx.audio.newSound(Gdx.files.internal("sons/somScore.mp3"));
    }

    public void playSound(String sound){
        if(sound.equals("fly")){
            fly.play();
        } else if(sound.equals("hit")){
            hit.play();
        } else if(sound.equals("score")){
            score.play();
        }
    }

    public void dispose(){
        fly.dispose();
        hit.dispose();
        score.dispose();
    }

}
