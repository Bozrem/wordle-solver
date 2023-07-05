package org.example;

enum Color {
    GREEN,
    YELLOW,
    GRAY
}

public class Word {
    private final String guess;
    private final Color[] colors = new Color[5];
    private final boolean[] usedLetters = new boolean[5];

    public Word(String guess) {
        this.guess = guess;
        for (int i = 0; i < 5; i++){
            colors[i] = Color.GRAY;
        }
    }

    public void setColor(int index, Color color) {
        colors[index] = color;
    }

    public Color getColor(int index){
        return colors[index];
    }

    public boolean isLetterUsed(int index){
        return usedLetters[index];
    }

    public void useLetter(int index){
        usedLetters[index] = true;
    }

    public String getGuess(){
        return guess;
    }

    public boolean isEqualColors(Word otherData){
        for (int i = 0; i < colors.length; i++){
            if (!colors[i].equals(otherData.getColor(i))){
                return false;
            }
        }
        return true;
    }
}
