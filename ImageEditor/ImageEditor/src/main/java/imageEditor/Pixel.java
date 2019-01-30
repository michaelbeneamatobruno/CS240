package imageEditor;

/**
 * Created by micha on 1/15/2018.
 */

public class Pixel {
    private int red;
    private int green;
    private int blue;


    public Pixel(){
        red = 0;
        green = 0;
        blue = 0;
    }
    public Pixel(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String print() {
        return red + " " + green + " " + blue + " ";
    }

    public int invertRed() {
        int invert = red - 255;
        invert =  java.lang.Math.abs(invert);
        return invert;
    }

    public int invertGreen() {
        int invert = green - 255;
        invert =  java.lang.Math.abs(invert);
        return invert;
    }

    public int invertBlue() {
        int invert = blue - 255;
        invert =  java.lang.Math.abs(invert);
        return invert;
    }

    public int grayScale() {
        int grayScale = (red + green + blue) / 3;
        return grayScale;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

}
