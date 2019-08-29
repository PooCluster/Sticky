package game;

import game.level.map.Map;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LoadMap {
    
    public LoadMap(int name) {
        Update.map = new Map("resources/maps/" + name + "/" + name + ".png");
        loadMap("resources/maps/" + name + "/" + name + ".txt");
    }
    
    private void loadMap(String filePath) {
        SoundEngine.clear();
        Scanner scan = new Scanner(new InputStreamReader(Main.class.getResourceAsStream(filePath)));
        String key = scan.next();
        if (key.equals("music:")) {
            SoundEngine.clear();
            SoundEngine.loadMusic(scan.next());
            key = scan.next();
        }
        if (key.equals("playerPos:")) {
            //Update.mapPlayer = new MapPlayer(scan.nextInt(), scan.nextInt());
        }
    }
    
}
