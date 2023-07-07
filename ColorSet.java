package org.example;

import java.util.Arrays;

public class ColorSet {
    private Color[] colors;

    public ColorSet(Color[] colors) {
        this.colors = colors;
    }

    public ColorSet(String colors) {
        this.colors = new Color[5];
        colors = colors.toLowerCase();
        for (int i = 0; i < 5; i++) {
            switch (colors.charAt(i)) {
                case 'b':
                    this.colors[i] = Color.GRAY;
                case 'y':
                    this.colors[i] = Color.YELLOW;
                case 'g':
                    this.colors[i] = Color.GREEN;
            }
        }
    }

    public ColorSet(){

    }

    public Color getColor(int index) {
        return colors[index];
    }

    public void setColor(int index, Color color) {
        colors[index] = color;
    }

    public boolean incrementColor() {
        if (isBlank()){
            colors = new Color[5];
            Arrays.fill(colors, Color.GRAY);
            return true;
        }
        for (int i = 0; i < 5; i++) {
            switch (colors[i]) {
                case GRAY:
                    setColor(i, Color.YELLOW);
                    return true;
                case YELLOW:
                    setColor(i, Color.GREEN);
                    return true;
                case GREEN:
                    setColor(i, Color.GRAY);
            }
        }
        return false;
    }

    private boolean isBlank(){
        return (colors == null);
    }

    public String toStringForm() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            switch (colors[i]) {
                case GRAY:
                    str.append('b');
                case YELLOW:
                    str.append('y');
                case GREEN:
                    str.append('g');
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Color c : colors) {
            str.append(c).append(" ");
        }
        return str.toString();
    }

}
