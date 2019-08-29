package game.entity.hud;

import static game.Update.player;
import java.util.ArrayList;

public class HUD {

    public static ArrayList<Heart>[] heart = new ArrayList[2];
    public static ArrayList<ScoreText> scoreText = new ArrayList<>();
    
    public HUD() {
        for (int j = 0; j < 2; j++) {
            heart[j] = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                heart[j].add(new Heart(i, j));
            }
        }
    }
    
    public void score() {
        int numberOfNumbers = 0;
        int scoreGetter = 1;
        while (scoreGetter <= game.Update.score) {
            scoreGetter *= 10;
            numberOfNumbers++;
        }
        if (game.Update.score == 0) numberOfNumbers = 1;
        scoreText.clear();
        int tempScore[] = new int[numberOfNumbers];
        for (int i = 0; i < numberOfNumbers; i++) {
            int score = game.Update.score;
            int a = i;
            while (a >= 0) {
                tempScore[i] = score % 10;
                score /= 10;
                a--;
            }
            scoreText.add(new ScoreText(i, tempScore[i] + 26));
        }
    }
    
    
    public void update() {
        for (int i = 0; i < player.size(); i++) {
            while (heart[i].size() != player.get(i).getHealth()) {
                if (heart[i].size() > player.get(i).getHealth()) {
                    heart[i].remove(heart[i].size() - 1);
                } else {
                    heart[i].add(new Heart(heart[i].size(), i));
                }
            }
        }
        score();
    }
    
    public void render() {
        for (int j = 0; j < player.size(); j++) {
            for (int i = 0; i < heart[j].size(); i++) {
                heart[j].get(i).render();
            }
        }
        for (int i = 0; i < scoreText.size(); i++) {
            scoreText.get(i).render();
        }
    }
    
}
