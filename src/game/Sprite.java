package game;

public class Sprite {
    
    public int[] pixels;
    private Textures textures;
    private int x;
    private int y;
    public final int size;
    
    //Overworld Tiles
    public static Sprite grass = new Sprite(16, 0, 0, Textures.textures); // Size, first x sprite, first y sprite, from the image.
    public static Sprite dirt = new Sprite(16, 1, 0, Textures.textures);
    public static Sprite grassdirt = new Sprite(16, 2, 0, Textures.textures);
    public static Sprite grassdirtLeft = new Sprite(16, 3, 0, Textures.textures);
    public static Sprite grassdirtRight = new Sprite(16, 4, 0, Textures.textures);
    public static Sprite grassdirtLeftCorner = new Sprite(16, 5, 0, Textures.textures);
    public static Sprite grassdirtRightCorner = new Sprite(16, 6, 0, Textures.textures);
    public static Sprite grassdirtRightWall = new Sprite(16, 8, 0, Textures.textures);
    public static Sprite grassdirtLeftWall = new Sprite(16, 7, 0, Textures.textures);
    public static Sprite grassdirtCeiling = new Sprite (16, 9, 0, Textures.textures);
    public static Sprite grassdirtCeilingLeft = new Sprite(16, 10, 0, Textures.textures);
    public static Sprite grassdirtCeilingRight = new Sprite(16, 11, 0, Textures.textures);
    public static Sprite grassdirtCeilingLeftCorner = new Sprite(16, 12, 0, Textures.textures);
    public static Sprite grassdirtCeilingRightCorner = new Sprite(16, 13, 0, Textures.textures);
    public static Sprite grassdirtBackground = new Sprite(16, 14, 0, Textures.textures);
    public static Sprite movingPlatformDirt = new Sprite(16, 1, 1, Textures.textures);
    
    //Underworld Tiles
    public static Sprite stone1 = new Sprite(16, 1, 2, Textures.textures);
    public static Sprite stone2 = new Sprite(16, 2, 2, Textures.textures);
    public static Sprite stone3 = new Sprite(16, 3, 2, Textures.textures);
    public static Sprite stone4 = new Sprite(16, 4, 2, Textures.textures);
    
    public static Sprite[] stone = new Sprite[4];
    public static Sprite[] stoneBackground = new Sprite[4];
    public static Sprite[] movingPlatformStone = new Sprite[4];
    
    public static Sprite stoneBackground1 = new Sprite(16, 5, 2, Textures.textures);
    public static Sprite stoneBackground2 = new Sprite(16, 6, 2, Textures.textures);
    public static Sprite stoneBackground3 = new Sprite(16, 7, 2, Textures.textures);
    public static Sprite stoneBackground4 = new Sprite(16, 8, 2, Textures.textures);
    
    //Water Tile
    public static Sprite water = new Sprite(16, 1, 3, Textures.textures);
    
    //Aesthetics (Clouds, Sky, Flower).
    public static Sprite daySky = new Sprite(16, 15, 0, Textures.textures);
    public static Sprite nightSky = new Sprite(16, 15, 1, Textures.textures);
    public static Sprite[] cloud = new Sprite[8];
    public static Sprite[] whiteFlower = new Sprite[4];
    public static Sprite[] redFlower = new Sprite[4];
    public static Sprite[][] flower = new Sprite[4][4]; //Color, animation
    public static Sprite[] coin = new Sprite[4];
    
    //Player.
    public static Sprite[] playerIdle = new Sprite[12];
    public static Sprite[] playerIdleLeft = new Sprite[12];
    public static Sprite[] playerRight = new Sprite[5];
    public static Sprite[] playerLeft = new Sprite[5];
    public static Sprite[] playerUp = new Sprite[5];
    public static Sprite[] playerDown = new Sprite[4];
    public static Sprite[] playerUpLeft = new Sprite[5];
    public static Sprite[] playerDownLeft = new Sprite[4];
    public static Sprite[] playerCrouch = new Sprite[4];
    public static Sprite[] playerDying = new Sprite[11];
    public static Sprite[] playerDyingLeft = new Sprite[11];
    public static Sprite heart = new Sprite(16, 0, 15, Textures.mobs);
    public static Sprite[] attack = new Sprite[3];
    public static Sprite[] attackLeft = new Sprite[3];
    public static Sprite[] attackDeath = new Sprite[10];
    public static Sprite[] attackDeathLeft = new Sprite[10];

    //Enemies.
    public static Sprite[] joeUp = new Sprite[5];
    public static Sprite[] joeDown = new Sprite[2];
    public static Sprite[] joeUpLeft = new Sprite[5];
    public static Sprite[] joeDownLeft = new Sprite[2];
    public static Sprite[] joeDeath = new Sprite[18];
    public static Sprite[] jimIdle = new Sprite[0];
    public static Sprite[] pedallerUp = new Sprite[10];
    public static Sprite[] pedallerDown = new Sprite[10];
    public static Sprite[] pedallerSuspend = new Sprite[5];
    public static Sprite[] pedallerDying = new Sprite[22];
    
    //Boss.
    public static Sprite boss = new Sprite(32, 2, 0, Textures.boss);
    
    //Texts.
    public static Sprite[] text = new Sprite[57];
    public static Sprite[] logo = new Sprite[4];
    
    
    //Map Stuff
    public static Sprite mapPlayerRight = new Sprite(16, 0, 0, Textures.map);
    public static Sprite mapPlayerLeft = new Sprite(16, 1, 0, Textures.map);
    public static Sprite horizontalPathBlocked = new Sprite (16, 0, 1, Textures.map);
    public static Sprite verticalPathBlocked = new Sprite(16, 1, 1, Textures.map);
    public static Sprite horizontalPath = new Sprite(16, 2, 1, Textures.map);
    public static Sprite verticalPath = new Sprite(16, 3, 1, Textures.map);
    public static Sprite mapGround = new Sprite(16, 0, 2, Textures.map);
    public static Sprite mapLevelIncomplete = new Sprite(16, 0, 3, Textures.map);
    public static Sprite mapLevelComplete = new Sprite(16, 1, 3, Textures.map);
    
    //Fill for when there are no blocks available.
    public static Sprite fill = new Sprite(16, 0xdc22e6);
    
    public Sprite(int size, int x, int y, Textures textures) {
        this.size = size;
        pixels = new int[this.size * this.size];
        this.x = x * size;
        this.y = y * size;
        this.textures = textures;
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                pixels[j + i * this.size] = this.textures.pixels[(j + this.x) + (i + this.y) * this.textures.size];
            }
        }
    }
    
    public Sprite(int size, int color) {
        this.size = size;
        pixels = new int[this.size * this.size];
        for(int i = 0; i < this.size * this.size; i++) {
            pixels[i] = color;
        }
    }

}