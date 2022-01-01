package aant.engine;

public class Map {


    public int[][] map;
    int antX, antY;
    int direction;
    int width;
    int height;

    public Map() {
    }

    public Map(Map data) {
        this.antX = data.antX;
        this.antY = data.antY;
        this.direction = data.direction;
        this.width = data.width;
        this.height = data.height;

        this.map = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.map[y][x] = data.map[y][x];
            }
        }

    }

    public int getHeight() {
        return height;
    }

    public int[][] getMap() {
        return map;
    }

    public int getAntX() {
        return antX;
    }

    public int getAntY() {
        return antY;
    }

    public int getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }
}
