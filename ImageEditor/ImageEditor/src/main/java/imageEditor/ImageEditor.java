package imageEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by micha on 1/15/2018.
 */

public class ImageEditor {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File(args[0]));
            sc.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            sc.next();
            int w =  sc.nextInt();
            int h = sc.nextInt();
            int max = sc.nextInt();
            Pixel[][] arr = new Pixel[h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    arr[i][j] = new Pixel(sc.nextInt(), sc.nextInt(), sc.nextInt()); //three times.
                }
            }

            if (args[2].equals("grayscale")) {
                System.out.print("grayscale");
                Pixel[][] grayScale = new Pixel[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {

                        grayScale[i][j] = new Pixel(arr[i][j].grayScale(), arr[i][j].grayScale(), arr[i][j].grayScale());
                    }
                }
                arr = grayScale;
            }
            else if (args[2].equals("invert")) {
                System.out.print("invert");
                Pixel[][] inverted = new Pixel[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        inverted[i][j] = new Pixel(arr[i][j].invertRed(), arr[i][j].invertGreen(), arr[i][j].invertBlue());
                    }
                }
                arr = inverted;
            }
            else if (args[2].equals("emboss")) {
                System.out.print("emboss");
                Pixel[][] emboss = new Pixel[h][w];
                int embossRed;
                int embossGreen;
                int embossBlue;
                int v;
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        if (i == 0 || j == 0) {
                            v = 128;
                        }
                        else {
                            embossRed = arr[i][j].getRed() - arr[i - 1][j - 1].getRed();
                            embossGreen = arr[i][j].getGreen() - arr[i - 1][j - 1].getGreen();
                            embossBlue = arr[i][j].getBlue() - arr[i - 1][j - 1].getBlue();
                            v = embossRed;
                            if (Math.abs(v) < Math.abs(embossGreen)) v = embossGreen;
                            if (Math.abs(v) < Math.abs(embossBlue)) v = embossBlue;
                            v = v + 128;
                            if (v < 0) v = 0;
                            if (v > 255) v = 255;
                        }
                        emboss[i][j] = new Pixel(v, v, v);
                    }
                }
                arr = emboss;
            }
            else if (args[2].equals("motionblur")) {
                System.out.print("motionblur");
                Pixel[][] motionBlur = new Pixel[h][w];
                int mBlur = Integer.parseInt(args[3]);
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
//                        if (mBlur + j >= w) {
//                            int counter = 0;
//                            for (int m = w - 1; m >= j; m--) {
//                                redMBlur += arr[i][m].getRed();
//                                greenMBlur += arr[i][m].getGreen();
//                                blueMBlur += arr[i][m].getBlue();
//                                counter++;
//                            }
//                            redMBlur = redMBlur / counter;
//                            greenMBlur = greenMBlur / counter;
//                            blueMBlur = blueMBlur / counter;
//                            motionBlur[i][j] = new Pixel(redMBlur, greenMBlur, blueMBlur);
//                        }
//                        else {
//                            if (mBlur == 1) {
//                                redMBlur = arr[i][j].getRed();
//                                greenMBlur = arr[i][j].getGreen();
//                                blueMBlur = arr[i][j].getBlue();
//                                motionBlur[i][j] = new Pixel(redMBlur, greenMBlur, blueMBlur);
//                            }
//                            else {
                            int counter = 0;
                            int redMBlur = 0;
                            int greenMBlur = 0;
                            int blueMBlur = 0;
                            for (int l = 0; l < mBlur; l++) {
                                if (j + l >= w) {
                                    break;
                                }
                                redMBlur += arr[i][j + l].getRed();
                                greenMBlur += arr[i][j + l].getGreen();
                                blueMBlur += arr[i][j + l].getBlue();
                                counter++;
                            }
                            redMBlur = redMBlur / counter;
                            greenMBlur = greenMBlur / counter;
                            blueMBlur = blueMBlur / counter;
                            motionBlur[i][j] = new Pixel(redMBlur, greenMBlur, blueMBlur);
//                            }

//                        }
                    }
                }
                arr = motionBlur;
            }



            PrintWriter pw = new PrintWriter(args[1]);
            pw.println("P3");
            pw.println(w + " " + h);
            pw.println(max);
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    pw.print(arr[i][j].print());
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
//string arrays contains the parameters in the file.
//in, out, flags.
//pixel class that has the three types, int r, g, and b.
