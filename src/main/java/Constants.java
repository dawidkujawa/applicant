import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Constants {

    public final static int ROW_CELL_ACCOUNT = 25;
    public final static int COLUMN_CELL_ACCOUNT = 25;

    public static int EMPTY = 0;
    public static int OBSTACLE = 1;

    public static int PERSONAL_DATA = 2;
    public static int PHOTO = 3;
    public static int EXPERIENCE = 4;
    public static int SKILLS = 5;
    public static int EDUCATION = 6;

    public static int FOLDER = 7;
    public static int LEVER_OFF = 8;
    public static int LEVER_ON = 9;
    public static int DOOR_CLOSED = 10;
    public static int DOOR_OPEN = 11;

    public static Color CELL_EMPTY_COLOR = Color.LIGHTGREY;
    public static Color cellBookColor = Color.RED;
    public static Color gameOverRectangleColor = Color.DARKGREY;
    public static Color gameOverStrokeColor = Color.RED;

    public final static Image DOG_IMAGE = new Image ("file:dog.png");
    public final static Image GUY_IMAGE = new Image("file:guy.png");
    public final static Image OBSTACLE_IMAGE = new Image("file:wall.png");
    public final static Image FOLDER_IMAGE = new Image("file:folder.png");

    public final static Image EXPERIENCE_IMAGE = new Image("file:experience.png");
    public final static Image PHOTO_IMAGE = new Image("file:photo.png");
    public final static Image SKILLS_IMAGE = new Image("file:skills.png");
    public final static Image EDUCATION_IMAGE = new Image("file:education.png");
    public final static Image PERSONAL_DATA_IMAGE = new Image("file:personal-data.png");

    public final static Image LEVER_ON_IMAGE = new Image("file:lever_on.png");
    public final static Image LEVER_OFF_IMAGE = new Image("file:lever_off.png");

    public final static Image DOOR_CLOSED_IMAGE = new Image("file:door-closed.png");
    public final static Image DOOR_OPEN_IMAGE = new Image("file:door-open.png");


    public static double screenWidth;
    public static double screenHeight;


}
