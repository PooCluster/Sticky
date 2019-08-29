package game;

import static game.Update.DISTANCE_LIMIT;
import static game.Update.background;
import static game.Update.ball;
import static game.Update.cloud;
import static game.Update.coin;
import static game.Update.distance;
import static game.Update.fakeDirt;
import static game.Update.fakeGrassDirtRightWall;
import static game.Update.flower;
import static game.Update.hud;
import static game.Update.joe;
import static game.Update.level;
import static game.Update.logo;
import static game.Update.map;
import static game.Update.mapPlayer;
import static game.Update.movingPlatform;
import static game.Update.pedaller;
import static game.Update.player;
import static game.Update.scoreCounter;
import static game.Update.text;
import static game.Update.xCamera;
import static game.Update.xCamera2;
import static game.Update.yCamera;
import static game.Update.yCamera2;
import game.level.Tile;
import static game.logic.GameStateManager.menu;
import static game.logic.GameStateManager.playerDead;
import static game.logic.GameStateManager.playersDead;
import static game.logic.GameStateManager.state;




public class Render { 

    public static int[] pixels;
    public static int width;
    public static int height;
    
    
    public static int xMove;
    public static int yMove;
    public static int xMove2;
    public static int yMove2;
    
    /*
    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
*/
    
    public static void renderTile(int x, int y, Tile tile) {
        x -= xMove;
        y -= yMove;
        for(int i = 0; i < tile.sprite.size; i++) {
            int y1 = i + y; 
            for(int j = 0; j < tile.sprite.size; j++) {
                int x1 = j + x;
                if(x1 < 0 || x1 >= width || y1 < 0 || y1 >= height) continue;
                pixels[x1 + y1 * width] = tile.sprite.pixels[j + i * tile.sprite.size];
            }
        }
    }
    
    //TEST CODE
    public static void renderTileLeft(int distance, int x, int y, Tile tile) {
        if (distance > 0) {
            x -= xMove2;
            y -= yMove2;
        } else {
            x -= xMove;
            y -= yMove;
        }
        for(int i = 0; i < tile.sprite.size; i++) {
            int y1 = i + y; 
            for(int j = 0; j < tile.sprite.size; j++) {
                int x1 = j + x;
                if(x1 < 0 || x1 >= width / 2 || y1 < 0 || y1 >= height) continue;
                pixels[x1 + y1 * width] = tile.sprite.pixels[j + i * tile.sprite.size];
            }
        }
    }
    
    public static void renderTileRight(int distance, int x, int y, Tile tile) {
        if (distance > 0) {
            x -= xMove;
            y -= yMove;
        } else {
            x -= xMove2;
            y -= yMove2;
        }
        
        for(int i = 0; i < tile.sprite.size; i++) {
            int y1 = i + y; 
            for(int j = 0; j < tile.sprite.size; j++) {
                int x1 = j + x;
                if(x1 < width / 2 || x1 >= width || y1 < 0 || y1 >= height) continue;
                pixels[x1 + y1 * width] = tile.sprite.pixels[j + i * tile.sprite.size];
            }
        }
    }
    
    public static void renderRegular(int size, int x, int y, Sprite sprite) {
        x -= xMove;
        y -= yMove;
        for (int i = 0; i < size; i++) {
            int y1 = i + y;
            for (int j = 0; j < size; j++) {
                int x1 = j + x;
                if (x1 < 0 || x1 >= width || y1 < 0 || y1 >= height) continue;
                if (sprite.pixels[j + i * size] != 0xffdc22e6) pixels[x1 + y1 * width] = sprite.pixels[j + i * size];
            }
        }
    }
    
    public static void renderFirstCamera(boolean left, int size, int x, int y, Sprite sprite) { //Player 1
        x -= xMove;
        y -= yMove;
        int xBoundary1 = 0;
        int xBoundary2 = width;
        if (left) {
            xBoundary2 /= 2;
        } else {
            xBoundary1 = width / 2;
        }
        for (int i = 0; i < size; i++) {
            int y1 = i + y; 
            for (int j = 0; j < size; j++) {
                int x1 = j + x;
                if (x1 < xBoundary1 || x1 >= xBoundary2 || y1 < 0 || y1 >= height) continue;
                if (sprite.pixels[j + i * size] != 0xffdc22e6) pixels[x1 + y1 * width] = sprite.pixels[j + i * size];
            }
        }
    }
    
    public static void renderSecondCamera(boolean left, int size, int x, int y, Sprite sprite) { //Player 2
        x -= xMove2;
        y -= yMove2;
        int xBoundary1 = 0;
        int xBoundary2 = width;
        if (left) {
            xBoundary2 /= 2;
        } else {
            xBoundary1 = width / 2;
        }
        for (int i = 0; i < size; i++) {
            int y1 = i + y; 
            for (int j = 0; j < size; j++) {
                int x1 = j + x;
                if (x1 < xBoundary1 || x1 >= xBoundary2|| y1 < 0 || y1 >= height) continue;
                if (sprite.pixels[j + i * size] != 0xffdc22e6) pixels[x1 + y1 * width] = sprite.pixels[j + i * size];
            }
        }
    }
    
    public static void renderFade(int size, int x, int y, int grade, int opacity, Sprite sprite){
        x -= xMove;
        y -= yMove;
        for (int i = 0; i < size; i++) {
            int y1 = i + y; 
            for (int j = 0; j < size; j++) {
                int x1 = j + x;
                if (x1 < 0 || x1 >= width || y1 < 0 || y1 >= height) continue;
                if (sprite.pixels[j + i * size] != 0xffdc22e6) {
                    pixels[x1 + y1 * width] = ((pixels[x1 + y1 * width] * (100 - grade)) + (((sprite.pixels[j + i * size] - opacity)* grade))) / 100;
                }
            }
        }
    }
    
    public static void renderHUD(int size, int x, int y, Sprite sprite) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (sprite.pixels[j + i * size] != 0xffdc22e6) {
                    pixels[(x + j) + (y + i) * width] = sprite.pixels[j + i * size];
                }
            }
        }
    }
    
    public static void renderMap(int xPlayer, int yPlayer) {
        
    }
    
    public static void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xff000000;
        }
    }
    
    public static void levelRender() {
        if (Math.abs(distance) <= DISTANCE_LIMIT || playerDead) {
            for (int i = 0; i < level.getHeight(); i++)
                for (int j = 0; j < level.getWidth(); j++)
                    background[j + i * level.getWidth()].render(xCamera, yCamera);
            level.render(xCamera - Render.width / 2, yCamera - Render.height / 2);
            for (int i = 0; i < text.size(); i++) text.get(i).render(xCamera, yCamera);
            for (int i = 0; i < flower.size(); i++) flower.get(i).render(xCamera, yCamera);
            for (int i = player.size() - 1; i >= 0; i--) player.get(i).render(!((menu == null && !playersDead) || (menu != null && playersDead) || state.peek() == 2), playerDead, distance);
            for (int i = 0; i < joe.size(); i++) joe.get(i).render(xCamera, yCamera);
            for (int i = 0; i < pedaller.size(); i++) pedaller.get(i).render(xCamera, yCamera);
            for (int i = 0; i < coin.size(); i++) coin.get(i).render(xCamera, yCamera);
            for (int i = 0; i < fakeGrassDirtRightWall.size(); i++) fakeGrassDirtRightWall.get(i).render(xCamera, yCamera);
            for (int i = 0; i < fakeDirt.size(); i++) fakeDirt.get(i).render(xCamera, yCamera);
            for (int i = 0; i < ball.size(); i++) ball.get(i).render(playerDead, distance);
            for (int i = 0; i < cloud.size(); i++) cloud.get(i).render(xCamera, yCamera);
            for (int i = 0; i < movingPlatform.size(); i++) movingPlatform.get(i).render(xCamera, yCamera);
            for (int i = 0; i < scoreCounter.size(); i++) scoreCounter.get(i).render(xCamera, xCamera2, playerDead, distance);
        } else {
            for (int i = 0; i < level.getHeight(); i++)
                for (int j = 0; j < level.getWidth(); j++)
                    background[j + i * level.getWidth()].render(xCamera, yCamera, xCamera2, yCamera2, distance);
            level.render(xCamera - Render.width / 2, yCamera - Render.height / 2, xCamera2 - Render.width / 2, yCamera2 - Render.height / 2, distance);
            for (int i = 0; i < text.size(); i++) text.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < flower.size(); i++) flower.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = player.size() - 1; i >= 0; i--) player.get(i).render(!((menu == null && !playersDead) || (menu != null && playersDead) || state.peek() == 2), playerDead, distance);
            for (int i = 0; i < joe.size(); i++) joe.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < pedaller.size(); i++) pedaller.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < coin.size(); i++) coin.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < fakeGrassDirtRightWall.size(); i++) fakeGrassDirtRightWall.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < fakeDirt.size(); i++) fakeDirt.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < ball.size(); i++) ball.get(i).render(playerDead, distance);
            for (int i = 0; i < cloud.size(); i++) cloud.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < movingPlatform.size(); i++) movingPlatform.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < scoreCounter.size(); i++) scoreCounter.get(i).render(xCamera, xCamera2, playerDead, distance);
        }
        hud.render();
        if (menu != null) menu.render();
        for (int i = 0; i < pixels.length; i++) Main.pixels[i] = pixels[i];
    }
    
    public static void mapLevelSelectionRender() {
        map.render(mapPlayer.getXPos() - Render.width / 2, mapPlayer.getYPos() - Render.height / 2);
        mapPlayer.render();
        if (menu != null) menu.render();
        for (int i = 0; i < pixels.length; i++) Main.pixels[i] = pixels[i];
    }
    
    public static void titleScreenRender() {
        //Basically levelRender(), but no HUD.
        if (Math.abs(distance) <= DISTANCE_LIMIT || (player.get(0).isDead() || player.get(1).isDead())) {
            for (int i = 0; i < level.getHeight(); i++)
                for (int j = 0; j < level.getWidth(); j++)
                    background[j + i * level.getWidth()].render(xCamera, yCamera);
            level.render(xCamera - Render.width / 2, yCamera - Render.height / 2);
            for (int i = 0; i < text.size(); i++) text.get(i).render(xCamera, yCamera);
            for (int i = 0; i < flower.size(); i++) flower.get(i).render(xCamera, yCamera);
            for (int i = player.size() - 1; i >= 0; i--) player.get(i).render(!((menu == null && !playersDead) || (menu != null && playersDead) || state.peek() == 2), playerDead, distance);
            for (int i = 0; i < joe.size(); i++) joe.get(i).render(xCamera, yCamera);
            for (int i = 0; i < pedaller.size(); i++) pedaller.get(i).render(xCamera, yCamera);
            for (int i = 0; i < coin.size(); i++) coin.get(i).render(xCamera, yCamera);
            for (int i = 0; i < fakeGrassDirtRightWall.size(); i++) fakeGrassDirtRightWall.get(i).render(xCamera, yCamera);
            for (int i = 0; i < fakeDirt.size(); i++) fakeDirt.get(i).render(xCamera, yCamera);
            for (int i = 0; i < ball.size(); i++) ball.get(i).render(playerDead, distance);
            for (int i = 0; i < cloud.size(); i++) cloud.get(i).render(xCamera, yCamera);
            for (int i = 0; i < movingPlatform.size(); i++) movingPlatform.get(i).render(xCamera, yCamera);
            for (int i = 0; i < scoreCounter.size(); i++) scoreCounter.get(i).render(xCamera, xCamera2, playerDead, distance);
        } else {
            for (int i = 0; i < level.getHeight(); i++)
                for (int j = 0; j < level.getWidth(); j++)
                    background[j + i * level.getWidth()].render(xCamera, yCamera, xCamera2, yCamera2, distance);
            level.render(xCamera - Render.width / 2, yCamera - Render.height / 2, xCamera2 - Render.width / 2, yCamera2 - Render.height / 2, distance);
            for (int i = 0; i < text.size(); i++) text.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < flower.size(); i++) flower.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = player.size() - 1; i >= 0; i--) player.get(i).render(!((menu == null && !playersDead) || (menu != null && playersDead) || state.peek() == 2), playerDead, distance);
            for (int i = 0; i < joe.size(); i++) joe.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < pedaller.size(); i++) pedaller.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < coin.size(); i++) coin.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < fakeGrassDirtRightWall.size(); i++) fakeGrassDirtRightWall.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < fakeDirt.size(); i++) fakeDirt.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < ball.size(); i++) ball.get(i).render(playerDead, distance);
            for (int i = 0; i < cloud.size(); i++) cloud.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < movingPlatform.size(); i++) movingPlatform.get(i).render(xCamera, yCamera, xCamera2, yCamera2, distance);
            for (int i = 0; i < scoreCounter.size(); i++) scoreCounter.get(i).render(xCamera, xCamera2, playerDead, distance);
        }
        if (menu != null) menu.render();
        logo.render();
        //Render "Sticky" and other brand stuff like maybe "By PooCluster"
        
        for (int i = 0; i < pixels.length; i++) Main.pixels[i] = pixels[i];
    }
    
    public static void survivalRender() {
        levelRender();
        //Countdown and high score stuff
        for (int i = 0; i < pixels.length; i++) Main.pixels[i] = pixels[i];
    }
    
}