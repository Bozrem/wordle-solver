package org.example;

enum Color {
    GREEN,
    YELLOW,
    GRAY
}

public class Word {
    private String guess;
    private final ColorSet colors;
    private final boolean[] usedLetters = new boolean[5];

    public Word(String guess) {
        this.guess = guess;
        colors = new ColorSet(new Color[]{Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY});
        for (int i = 0; i < 5; i++){
            colors.setColor(i, Color.GRAY);
        }
    }
    public Word(String guess, ColorSet colors){
        this.guess = guess;
        this.colors = colors;
    }

    public void setColor(int index, Color color) {
        colors.setColor(index, color);
    }

    public Color getColor(int index){
        return colors.getColor(index);
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
        for (int i = 0; i < 5; i++){
            if (!colors.getColor(i).equals(otherData.getColor(i))){
                return false;
            }
        }
        return true;
    }

    public ColorSet toColorSet(){
        return colors;
    }
}
