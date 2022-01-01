package aant.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MapLoader {

    Map data;

    public MapLoader(Map data) {
        this.data = data;
    }

    public void load(String filename) {
        data.width = 0;
        data.height = 0;
        try {
            BufferedReader br = jarBufferedReader(filename);
            String line;
            while ((line = br.readLine()) != null) {
                data.width = line.length();
                data.height++;
            }
            data.map = new int[data.height][data.width];
            clear();
            int y = 0;
            br = jarBufferedReader(filename);
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '*') {
                        data.map[y][i] = 1;
                    }
                    if (line.charAt(i) == '>') {
                        data.antX = i;
                        data.antY = y;
                        data.direction = 1;
                    }
                    if (line.charAt(i) == '<') {
                        data.antX = i;
                        data.antY = y;
                        data.direction = 3;
                    }
                    if (line.charAt(i) == '^') {
                        data.antX = i;
                        data.antY = y;
                        data.direction = 0;
                    }
                    if (line.charAt(i) == '!') {
                        data.antX = i;
                        data.antY = y;
                        data.direction = 2;
                    }
                }
                y++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        for (int y = 0; y < data.height; y++) {
            for (int x = 0; x < data.width; x++) {
                data.map[y][x] = 0;
            }
        }
    }

    public BufferedReader jarBufferedReader(String s) {
        InputStream is = null;
        BufferedReader br = null;
        String line;
        StringBuffer buffer = new StringBuffer();

        is = this.getClass().getResourceAsStream(s);
        br = new BufferedReader(new InputStreamReader(is));
        return br;
    }
}
