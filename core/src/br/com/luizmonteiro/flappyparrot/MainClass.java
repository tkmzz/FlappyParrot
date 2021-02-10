package br.com.luizmonteiro.flappyparrot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static br.com.luizmonteiro.flappyparrot.Constants.*;

public class MainClass extends ApplicationAdapter {

	private SpriteBatch batch;
	private Background background;
	private Parrot parrot;
	private List<Pipe> pipes;
	private float pipeTime;
	private int status = 0; //0: stopped 1: playing 2: lost  3: restart
	private List<Score> scoresObj;
	private int scores = 0;
	private boolean marked = false;

	private BitmapFont font;
	private GlyphLayout glyphLayout = new GlyphLayout();

	private Button btnStart;
	private Button btnRestart;

	private Sounds sound;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Background();
		parrot = new Parrot(parrotInitX, screenY/2);
		pipes = new ArrayList<Pipe>();
		pipeTime = pipesTime;
		scoresObj = new ArrayList<Score>();

		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int) (0.2f*screenX);
		parameter.color = new Color(1, 1, 1, 1);
		font = generator.generateFont(parameter);
		generator.dispose();

		btnStart = new Button(new Texture("botoes/BotaoPlay.png"), btnX, btnY, btnSize);
		btnRestart = new Button(new Texture("botoes/BotaoReplay.png"), btnX, btnY, btnSize);

		sound = new Sounds();
	}

	@Override
	public void render () {
		input();

		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		draw();

		batch.end();
	}

	private void input() {
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = screenY - Gdx.input.getY();

			if(status == 0){

				btnStart.verif(x, y);

			} else if(status == 1){

				parrot.impulse();
				sound.playSound("fly");

			} else if(status == 3){

				btnRestart.verif(x, y);

			}
		} else if(!Gdx.input.isTouched()){
			if(btnStart.high){
				status = 1;
				btnStart.high = false;
			}
			if(btnRestart.high){
				status = 1;
				parrot.restart(parrotInitX, screenY/2);
				pipes.clear();
				pipeTime = pipesTime;
				scores = 0;
				marked = false;
				scoresObj.clear();
				btnRestart.high = false;
			}
		}
	}

	private void update(float time) {
		if(status == 1){
			background.updateBackground(time);

			for(int i=0 ; i<pipes.size() ; i++){
				if(pipes.get(i).updatePipe(time) == 1){
					pipes.remove(i);
					i--;
				}
			}

			for(int i = 0; i< scoresObj.size() ; i++){
				if(scoresObj.get(i).updateScore(time) == 1){
					scoresObj.remove(i);
					i--;
				}
			}

			pipeTime -= time;
			if(pipeTime <= 0){
				Random random = new Random();
				int pos = random.nextInt(posMax);
				pos -= posMax/2;
				pipes.add(new Pipe(screenX, screenY/2 + pos + gap/2, true));
				pipes.add(new Pipe(screenX, screenY/2 + pos - gap/2, false));
				scoresObj.add(new Score(screenX + pipeW + 2*parrotRadius, screenY/2 + pos - gap/2));
				pipeTime = pipesTime;
			}

			for(Pipe p: pipes){
				if(Intersector.overlaps(parrot.body, p.body)){
					sound.playSound("hit");
					parrot.gameOver();
					status = 2;
				}
			}

			boolean inter = false;
			for(Score s: scoresObj){
				if(Intersector.overlaps(parrot.body, s.body)){
					if(!marked){
						scores++;
						sound.playSound("score");
						marked = true;
					}
					inter = true;
				}
			}
			if(!inter) marked = false;
		}

		if(status == 1 || status == 2){
			if(parrot.updateParrot(time) == 1){
				status = 3;
			}
		}
	}

	private void draw(){
		background.drawBackground(batch);

		for(Pipe p: pipes){
			p.drawPipe(batch);
		}

		parrot.drawParrot(batch);

		font.draw(batch, String.valueOf(scores),
				(screenX - getSizeX(font, String.valueOf(scores)))/2,
				0.98f*screenY);

		if(status == 0){
			btnStart.drawButton(batch);
		} else if(status == 3){
			btnRestart.drawButton(batch);
		}
	}

	private float getSizeX(BitmapFont font, String text){
		glyphLayout.reset();
		glyphLayout.setText(font, text);
		return glyphLayout.width;
	}

	@Override
	public void dispose () {
		background.dispose();
		parrot.dispose();

		for(Pipe p: pipes){
			p.dispose();
		}

		font.dispose();

		sound.dispose();
		btnStart.dispose();
		btnRestart.dispose();

		batch.dispose();
	}
}
