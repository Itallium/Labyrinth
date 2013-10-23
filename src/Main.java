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
        for (int i = 0; i < (image.getHeight() / 3) + 1; i++) {
            for (int j = 0; j < (image.getWidth() / 3) + 1; j++) {
             labyrinth[i][j] = sourceImage[i * 3][j * 3];
            }
        }

        //Сохраняем готовую картинку в файл.
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
        ImageIO.write(outputImage, "bmp", new File("result\\saved.bmp"));
    }
}
