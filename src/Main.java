import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final int BLACK = -16777216;
        final int RED = -65536;
        final int GREEN = -16744320;
        final int YELLOW = -256;
        final int BLUE = -16711681;
        final int WHITE = -1;

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resource\\Labyrinth.bmp"));
        } catch (IOException e) {

        }

        //Преобразуем картинку в int массив.
        int[][] sourceImage = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                sourceImage[i][j] = image.getRGB(j, i);
                if (sourceImage[i][j] == BLACK) {
                    sourceImage[i][j] = 1;
                } else {
                    sourceImage[i][j] = 0;
                }
            }
        }

        //В новый массив скидываем только каждый третий эдемент искомого (специфика входной картинки).
        int[][] labyrinth = new int[(image.getHeight() / 3) + 1][(image.getWidth() / 3) + 1];
        int[][] path = new int[(image.getHeight() / 3) + 1][(image.getWidth() / 3) + 1];
        for (int i = 0; i < (image.getHeight() / 3) + 1; i++) {
            for (int j = 0; j < (image.getWidth() / 3) + 1; j++) {
             labyrinth[i][j] = sourceImage[i * 3][j * 3];
             path[i][j] = sourceImage[i * 3][j * 3];
            }
        }
        int endCount;
        int edgeCount;;
        do {
            endCount = 0;
            for (int i = 1; i < path.length - 1; i++) {
                for (int j = 1; j < path.length - 1; j++) {
                    if (path[i][j] == 0) {
                        edgeCount = 0;
                        if (path[i - 1][j] == 1) {
                            edgeCount++;
                        }
                        if (path[i + 1][j] == 1) {
                            edgeCount++;
                        }
                        if (path[i][j - 1] == 1) {
                            edgeCount++;
                        }
                        if (path[i][j + 1] == 1) {
                            edgeCount++;
                        }
                        if (edgeCount == 3) {
                            path[i][j] = 1;
                            endCount++;
                        }
                    }
                }
            }
        } while (endCount != 0);

        //Save complete labyrinth to file.
        BufferedImage outputImage = new BufferedImage((image.getWidth() / 3) + 1, (image.getHeight() / 3) + 1, 12);
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth.length; j++) {
                if (labyrinth[i][j] == 1) {
                    outputImage.setRGB(j, i, BLACK);
                } else {
                    outputImage.setRGB(j, i, WHITE);
                }
            }
        }
        ImageIO.write(outputImage, "bmp", new File("result\\labyrinth.bmp"));

        //Save path to file.
        outputImage = new BufferedImage((image.getWidth() / 3) + 1, (image.getHeight() / 3) + 1, 12);
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                if (path[i][j] == 1) {
                    outputImage.setRGB(j, i, BLACK);
                } else {
                    outputImage.setRGB(j, i, WHITE);
                }
            }
        }
        ImageIO.write(outputImage, "bmp", new File("result\\path.bmp"));

        //Compare path and labyrinth
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth.length; j++) {
                if (path[i][j] == 0) {
                    labyrinth[i][j] = 2;
                }
            }
        }

        outputImage = new BufferedImage((image.getWidth() / 3) + 1, (image.getHeight() / 3) + 1, 1);
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth.length; j++) {
                if (labyrinth[i][j] == 0) {
                    outputImage.setRGB(j, i, WHITE);
                }
                if (labyrinth[i][j] == 1) {
                    outputImage.setRGB(j, i, BLACK);
                }
                if (labyrinth[i][j] == 2) {
                    outputImage.setRGB(j, i, GREEN);
                }
            }
        }
        ImageIO.write(outputImage, "bmp", new File("result\\complete.bmp"));
    }
}
