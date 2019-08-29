package game;

import static game.Update.DISTANCE_LIMIT;

public class Camera {

    public static int xCamera(int xPlayer, int xMin, int xMax) {
        int x;
        if (xPlayer >= xMax + 16) {
            x = xMax + 16;
        } else if (xPlayer <= xMin) {
            x = xMin;
        } else {
            x = xPlayer;
        }
        return x - 8;
    }
    
    public static int xCamera(int xPlayer, int xPlayer2, int xMin, int xMax) {
        int x;
        if ((xPlayer + xPlayer2) / 2 >= xMax + 16) {
            x = xMax + 16;
        } else if ((xPlayer + xPlayer2) / 2 < xMin) {
            x = xMin;
        } else {
            x = (xPlayer + xPlayer2) / 2;
        }
        return x - 8;
    }
    
    public static int xCameraSplit(int xPlayer, int xMin, int xMax, int distance) {
        int x;
        if (distance > DISTANCE_LIMIT) { //Player 1 on the right.
            if (xPlayer >= xMax + (DISTANCE_LIMIT / 2) + 16) {
                x = xMax + 16;
            } else if (xPlayer <= xMin + (DISTANCE_LIMIT / 2)) {
                x = xMin;
            } else {
                x = xPlayer - (DISTANCE_LIMIT / 2);
            }
        } else { //Player 1 on the left.
            if (xPlayer >= xMax - (DISTANCE_LIMIT / 2) + 16) {
                x = xMax + 16;
            } else if (xPlayer <= xMin - (DISTANCE_LIMIT / 2)) {
                x = xMin;
            } else {
                x = xPlayer + (DISTANCE_LIMIT / 2);
            }
        }
        return x - 8;
    }
    
    public static int xCameraSplit2(int xPlayer, int xMin, int xMax, int distance) {
        int x;
        if (distance > DISTANCE_LIMIT) { //Player 1 on the right.
            if (xPlayer >= xMax - (DISTANCE_LIMIT / 2) + 16) {
                x = xMax + 16;
            } else if (xPlayer <= xMin - (DISTANCE_LIMIT / 2)) {
                x = xMin;
            } else {
                x = xPlayer + (DISTANCE_LIMIT / 2);
            }
        } else { //Player 1 on the left.
            if (xPlayer >= xMax + (DISTANCE_LIMIT / 2) + 16) {
                x = xMax + 16;
            } else if (xPlayer <= xMin + (DISTANCE_LIMIT / 2)) {
                x = xMin;
            } else {
                x = xPlayer - (DISTANCE_LIMIT / 2);
            }
        }
        return x - 8;
    }
    
    public static int yCamera(int yPlayer, int yMax) {
        int y = yPlayer;
        if (yPlayer >= yMax) {
            y = yMax;
        }
        return y;
    }
    
    public static int yCamera(int yPlayer, int yPlayer2, int yMax) {
        int y = (yPlayer + yPlayer2) / 2;
        if ((yPlayer + yPlayer2) / 2 >= yMax) {
            y = yMax;
        }
        return y;
    }
    
}