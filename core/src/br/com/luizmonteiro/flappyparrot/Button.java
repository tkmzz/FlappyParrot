package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

    private Rectangle button;

    public boolean high = false;
    private float highF = 1.1f;

    private Texture texture;

    public Button(Texture texture, int posX, int posY, int size){
        this.texture = texture;
        button = new Rectangle(posX, posY, size, size);
    }

    public void drawButton(SpriteBatch batch){
        if(high){
            batch.draw(texture,
                    button.x - (button.getWidth()*(highF-1))/2,
                    button.y - (button.getHeight()*(highF-1))/2,
                    button.getWidth()*highF,
                    button.getHeight()*highF);
        } else{
            batch.draw(texture, button.x, button.y, button.getWidth(), button.getHeight());
        }
    }

    public boolean verif(int x, int y){
        if(button.x <= x && button.x + button.width >= x &&
                button.y <= y && button.y + button.height >= y){
            high = true;
        } else{
            high = false;
        }
        return high;
    }

    public void dispose(){
        texture.dispose();
    }

}
