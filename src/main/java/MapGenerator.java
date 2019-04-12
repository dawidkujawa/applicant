import com.sun.deploy.util.BlackList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static javafx.scene.paint.Color.*;

public class MapGenerator {
    protected static Pane root;
    private static Grid grid;
    public static Timeline timeline;
    public static Scene scene;
    public static Stage stage;

    public static Stage generateMap(Stage stage) {

        MapGenerator.stage = stage;
        root = new Pane();
        root.setStyle("-fx-background-color: lightgrey");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(root, Constants.screenWidth, Constants.screenHeight);

        createCVElements();

        createLever();

        createDoor();

        initObscatles();

        initFolder();


        grid = new Grid();


        for (int i = 0; i < Constants.COLUMN_CELL_ACCOUNT; i++) {
            for (int j = 0; j < Constants.ROW_CELL_ACCOUNT; j++) {

                Position position = new Position(i, j);
                int type = Constants.OBSTACLE;

                if (i != Constants.COLUMN_CELL_ACCOUNT - 1 && j != Constants.ROW_CELL_ACCOUNT - 1 && i != 0 && j != 0) {
                    if (isCVElement(position) && isPhoto(position))
                        type = Constants.PHOTO;
                    else if (isCVElement(position) && isSkills(position))
                        type = Constants.SKILLS;
                    else if (isCVElement(position) && isExperience(position))
                        type = Constants.EXPERIENCE;
                    else if (isCVElement(position) && isPersonalData(position))
                        type = Constants.PERSONAL_DATA;
                    else if (isCVElement(position) && isEducation(position))
                        type = Constants.EDUCATION;
                    else if (isLever(position) && Applicant.leverOn == false)
                        type = Constants.LEVER_OFF;
                    else if (isLever(position) && Applicant.leverOn == true)
                        type = Constants.LEVER_ON;
                    else if (isDoor(position))
                        type = Constants.DOOR_CLOSED;
                    else if (isDoor(position) && Applicant.leverOn == true)
                        type = Constants.DOOR_OPEN;
                    else if (isObstacle(position))
                        type = Constants.OBSTACLE;
                    else if (isFolder(position))
                        type = Constants.FOLDER;
                    else
                        type = Constants.EMPTY;
                }

                Cell cell = new Cell(position, type);
                grid.addCell(cell);

                root.getChildren().add(cell.getNode());

            }

        }

        stage.setScene(scene);

        return stage;
    }

    public static void redrawMap() {

        Position.initScreenDimension();

        root.getChildren().clear();

        for (int i = 0; i < Constants.ROW_CELL_ACCOUNT; i++)
            for (int j = 0; j < Constants.COLUMN_CELL_ACCOUNT; j++)
                root.getChildren().add(grid.getCell(i, j).getNode());

        root.getChildren().add(GamePlay.applicant.getNode());
        root.getChildren().add(GamePlay.dog.getNode());

        Position scorePosition = new Position(2, Constants.COLUMN_CELL_ACCOUNT - 2);
        Text text = new Text(scorePosition.x - 80, scorePosition.y - 10, "collected \npages: " + Applicant.score);
        text.setFill(WHITE);
        text.setStroke(BLACK);
        text.setStrokeWidth(1.2);
        text.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 50));

        Position folderPosition = new Position(15, Constants.COLUMN_CELL_ACCOUNT - 15);
        Text folderText = new Text(110, 925, "↓ Your folder");
        folderText.setFill(BROWN);
        folderText.setFont(Font.font("HELVETICA", 25));

        Position commandPosition = new Position(2, 2);
        Text command = new Text(30, 50, "Collect pages of your CV \nand put them in the folder! \ndo it one by one");
        command.setFill(WHITE);
        command.setStroke(BLACK);
        command.setStrokeWidth(1.2);
        command.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 50));

        Position command2Position = new Position(2, 2);
        Text command2 = new Text(30, 50, "Put it in the folder, quickly!");
        command2.setFill(WHITE);
        command2.setStroke(BLACK);
        command2.setStrokeWidth(1.2);
        command2.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 50));

        Position command3Position = new Position(2, 2);
        Text command3 = new Text(30, 50, "Now pull the lever in the bottom right ↘\nand open the door");
        command3.setFill(WHITE);
        command3.setStroke(BLACK);
        command3.setStrokeWidth(1.2);
        command3.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 50));

        Position command4Position = new Position(2, 2);
        Text command4 = new Text(30, 50, "The door is open,\n run away!");
        command4.setFill(WHITE);
        command4.setStroke(BLACK);
        command4.setStrokeWidth(1.2);
        command4.setFont(Font.font("HELVETICA", FontWeight.BOLD,Constants.screenWidth / 50));

        root.getChildren().add(text);
        root.getChildren().add(folderText);

        if (Applicant.score == 5 && Applicant.leverOn == false) {
            root.getChildren().add(command3);
        } else if (Applicant.cvElementTaken == false && Applicant.leverOn == false) {
            root.getChildren().add(command);
        } else if (Applicant.cvElementTaken == true) {
            root.getChildren().add(command2);
        } else if (Applicant.score == 5 && Applicant.leverOn == true) {
            root.getChildren().add(command4);
        }


        root.requestFocus();
    }

    private static boolean isObstacle(Position position) {

        for (int i = 0; i < obstacles.size(); i++) {
            Position tmpPosition = obstacles.get(i);
            if (position.row == tmpPosition.row && position.column == tmpPosition.column)
                return true;
        }
        return false;
    }

    private static boolean isFolder(Position position) {

        for (int i = 0; i < folder.size(); i++) {
            Position tmpPosition = folder.get(i);
            if (position.row == tmpPosition.row && position.column == tmpPosition.column)
                return true;
        }
        return false;
    }

    private static boolean isCVElement(Position position) {
        for (int i = 0; i < cvElements.size(); i++) {
            Position tmpPosition = cvElements.get(i);
            if (position.row == tmpPosition.row && position.column == tmpPosition.column)
                return true;
        }
        return false;
    }

    private static boolean isPhoto(Position position){
        if (position.equals(cvElements.get(0))){
            return true;
        }
        return false;
    }

    private static boolean isSkills(Position position){
        if (position.equals(cvElements.get(1))){
            return true;
        }
        return false;
    }

    private static boolean isExperience(Position position){
        if (position.equals(cvElements.get(2))){
            return true;
        }
        return false;
    }

    private static boolean isPersonalData(Position position){
        if (position.equals(cvElements.get(3))){
            return true;
        }
        return false;
    }

    private static boolean isEducation(Position position){
        if (position.equals(cvElements.get(4))){
            return true;
        }
        return false;
    }

    private static boolean isLever(Position position) {
        if (position.row == lever.row && position.column == lever.column)
            return true;
        else {
            return false;
        }
    }

    private static boolean isDoor(Position position) {
        if (position.row == door.row && position.column == door.column)
            return true;
        else {
            return false;
        }
    }

    public static boolean isApplicant(Position position) {
        if (position.row == Applicant.position.row && position.column == Applicant.position.column) {
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<Position> obstacles = new ArrayList<>();

    private static void initObscatles() {

        obstacles.add(new Position(1, 12));

        obstacles.add(new Position(2, 2));
        obstacles.add(new Position(2, 3));
        obstacles.add(new Position(2, 4));
        obstacles.add(new Position(2, 6));
        obstacles.add(new Position(2, 7));
        obstacles.add(new Position(2, 8));
        obstacles.add(new Position(2, 9));
        obstacles.add(new Position(2, 10));

        obstacles.add(new Position(3, 2));
        obstacles.add(new Position(3, 3));
        obstacles.add(new Position(3, 4));
        obstacles.add(new Position(3, 6));
        obstacles.add(new Position(3, 7));
        obstacles.add(new Position(3, 8));
        obstacles.add(new Position(3, 9));
        obstacles.add(new Position(3, 10));

        obstacles.add(new Position(5, 2));
        obstacles.add(new Position(5, 3));
        obstacles.add(new Position(5, 4));
        obstacles.add(new Position(5, 6));
        obstacles.add(new Position(5, 10));
        obstacles.add(new Position(5, 11));
        obstacles.add(new Position(5, 12));

        obstacles.add(new Position(6, 6));
        obstacles.add(new Position(6, 12));

        obstacles.add(new Position(7, 1));
        obstacles.add(new Position(7, 2));
        obstacles.add(new Position(7, 3));
        obstacles.add(new Position(7, 4));
        obstacles.add(new Position(7, 6));
        obstacles.add(new Position(7, 7));
        obstacles.add(new Position(7, 8));
        obstacles.add(new Position(7, 9));
        obstacles.add(new Position(7, 10));
        obstacles.add(new Position(7, 12));

        obstacles.add(new Position(8, 4));
        obstacles.add(new Position(8, 6));
        obstacles.add(new Position(8, 12));

        obstacles.add(new Position(9, 4));
        obstacles.add(new Position(9, 6));
        obstacles.add(new Position(9, 7));
        obstacles.add(new Position(9, 8));
        obstacles.add(new Position(9, 9));

        obstacles.add(new Position(10, 1));
        obstacles.add(new Position(10, 3));
        obstacles.add(new Position(10, 4));
        obstacles.add(new Position(10, 6));

        obstacles.add(new Position(11, 6));
        obstacles.add(new Position(11, 9));
        obstacles.add(new Position(11, 10));
        obstacles.add(new Position(11, 11));

        obstacles.add(new Position(12, 9));

        obstacles.add(new Position(13, 1));
        obstacles.add(new Position(13, 2));
        obstacles.add(new Position(13, 3));
        obstacles.add(new Position(13, 4));
        obstacles.add(new Position(13, 6));
        obstacles.add(new Position(13, 9));
        obstacles.add(new Position(13, 10));
        obstacles.add(new Position(13, 11));
        obstacles.add(new Position(13, 12));

        obstacles.add(new Position(14, 4));
        obstacles.add(new Position(14, 6));

        obstacles.add(new Position(15, 4));
        obstacles.add(new Position(15, 6));
        obstacles.add(new Position(15, 7));
        obstacles.add(new Position(15, 9));
        obstacles.add(new Position(15, 10));
        obstacles.add(new Position(15, 11));
        obstacles.add(new Position(15, 12));

        obstacles.add(new Position(16, 1));
        obstacles.add(new Position(16, 2));
        obstacles.add(new Position(16, 4));
        obstacles.add(new Position(16, 12));

        obstacles.add(new Position(17, 6));
        obstacles.add(new Position(17, 7));
        obstacles.add(new Position(17, 8));
        obstacles.add(new Position(17, 9));
        obstacles.add(new Position(17, 10));
        obstacles.add(new Position(17, 12));

        obstacles.add(new Position(18, 2));
        obstacles.add(new Position(18, 3));
        obstacles.add(new Position(18, 4));
        obstacles.add(new Position(18, 12));

        obstacles.add(new Position(19, 4));
        obstacles.add(new Position(19, 7));
        obstacles.add(new Position(19, 8));

        obstacles.add(new Position(20, 1));
        obstacles.add(new Position(20, 2));
        obstacles.add(new Position(20, 4));
        obstacles.add(new Position(20, 7));
        obstacles.add(new Position(20, 10));
        obstacles.add(new Position(20, 11));
        obstacles.add(new Position(20, 12));

        obstacles.add(new Position(21, 7));
        obstacles.add(new Position(21, 12));

        obstacles.add(new Position(22, 3));
        obstacles.add(new Position(22, 4));
        obstacles.add(new Position(22, 5));
        obstacles.add(new Position(22, 6));
        obstacles.add(new Position(22, 7));
        obstacles.add(new Position(22, 8));
        obstacles.add(new Position(22, 9));
        obstacles.add(new Position(22, 10));
        obstacles.add(new Position(22, 12));

        obstacles.add(new Position(23, 12));



        int loopSize = obstacles.size();
        for (int i = 0; i < loopSize; i++) {
            Position tmpPosition = obstacles.get(i);
            Position newPosition = new Position(tmpPosition.row, Constants.COLUMN_CELL_ACCOUNT - 1 - tmpPosition.column);
            obstacles.add(newPosition);
        }
    }

    private static ArrayList<Position> folder = new ArrayList<>();

    public static void initFolder() {
        folder.add(new Position(23, 1));

    }

    private static ArrayList<Position> cvElements = new ArrayList<>();

    public static void createCVElements() {
        cvElements = new ArrayList<>();

        addRandomCVElement(cvElements, 0);
        addRandomCVElement(cvElements, 1);
        addRandomCVElement(cvElements, 2);
        addRandomCVElement(cvElements, 3);
        addRandomCVElement(cvElements, 4);
    }

    public static void addRandomCVElement(ArrayList<Position> cvElements, int index) {
        Random random = new Random();
        int minVal = 1;
        int maxVal = 23;
        cvElements.add(new Position(random.nextInt((maxVal - minVal) + 1) + minVal, random.nextInt((maxVal - minVal) + 1) + minVal));
        if (isObstacle(cvElements.get(index)) || isFolder(cvElements.get(index)) || isLever(cvElements.get(index))) {
            cvElements.remove(index);
            addRandomCVElement(cvElements, index);
        }
    }

    public static Position lever = new Position();

    public static void createLever() {
        lever = new Position(23, 23);
    }

    public static Position door = new Position();

    public static void createDoor() {
        door = new Position(12, 12);
    }


    public static void addClickListener() {
        MapGenerator.root.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.UP)
                Applicant.moveUp();
            else if (event.getCode() == KeyCode.DOWN)
                Applicant.moveDown();
            else if (event.getCode() == KeyCode.LEFT)
                Applicant.moveLeft();
            else if (event.getCode() == KeyCode.RIGHT)
                Applicant.moveRight();
            else if (event.getCode() == KeyCode.P)
                pauseOn();
            else if (event.getCode() == KeyCode.S)
                pauseOff();

        });
    }

    public static void invalidateClickListener() {
        MapGenerator.root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                replay();
            }
        });
    }

    public static void replay() {

        MapGenerator.generateMap(stage);

        Applicant.score = 0;

        Applicant.cvElementTaken = false;

        Applicant.leverOn = false;

        MapGenerator.addClickListener();

        GamePlay.applicant = new Applicant();

        GamePlay.dog = new Dog();


        MapGenerator.redrawMap();

        MapGenerator.startTimeLine();
    }

    public static void gameEnded() {
        invalidateClickListener();
        ;
        timeline.stop();

        Text gameOverText = new Text("                Game Over \n \n  your dog just bit your ass :(");
        gameOverText.setFill(WHITE);
        gameOverText.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 30));
        gameOverText.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 4.3));
        gameOverText.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(Constants.screenHeight / 7));

        Text text = new Text("\npages in your CV folder: " + Applicant.score);
        text.setFill(WHITE);
        text.setFont(Font.font("HELVETICA", Constants.screenWidth / 50));
        text.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 9));
        text.layoutYProperty().bind(scene.heightProperty().divide(1.7));

        Text replayText = new Text("press space");
        replayText.setFill(WHITE);
        replayText.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 40));
        replayText.setStroke(BLACK);
        replayText.setStrokeWidth(1.2);
        replayText.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 14));
        replayText.layoutYProperty().bind(scene.heightProperty().divide(1.2));

        Rectangle rectangle = new Rectangle(Constants.screenWidth / 2, Constants.screenHeight / 2);
        rectangle.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(rectangle.getWidth() / 2));
        rectangle.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(rectangle.getHeight() / 2));
        rectangle.setFill(Constants.gameOverRectangleColor);
        rectangle.setStroke(Constants.gameOverStrokeColor);
        root.getChildren().add(rectangle);
        root.getChildren().add(gameOverText);
        root.getChildren().add(text);
        root.getChildren().add(replayText);

    }


    public static void pauseOn() {
        timeline.stop();
        Text pause = new Text("Pause");
        pause.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 20));
        pause.setFill(WHITE);
        pause.setStroke(BLACK);
        pause.setStrokeWidth(1.5);
        pause.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 13));
        pause.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(Constants.screenHeight / 5));

        Text start = new Text("press S to start");
        start.setFont(Font.font("HELVETICA", Constants.screenWidth / 40));
        start.setFill(WHITE);
        start.setStroke(BLACK);
        start.setStrokeWidth(1.5);
        start.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 11));
        start.layoutYProperty().bind(scene.heightProperty().divide(1.5).subtract(Constants.screenHeight / 4));

        root.getChildren().add(pause);
        root.getChildren().add(start);

    }

    public static void pauseOff() {
        timeline.play();
    }


    public static void gameWon() {
        invalidateClickListener();
        timeline.stop();

        Text gameWonText = new Text("Congratulations!");
        gameWonText.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 20));
        gameWonText.setFill(WHITE);
        gameWonText.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 5.2));
        gameWonText.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(Constants.screenHeight / 6.5));

        Text gameWonText2 = new Text("\nYour employer is absolutely amazed \n\n            You just got the job!");
        gameWonText2.setFont(Font.font("HELVETICA", Constants.screenWidth / 35));
        gameWonText2.setFill(WHITE);
        gameWonText2.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 4.5));
        gameWonText2.layoutYProperty().bind(scene.heightProperty().divide(1.5).subtract(Constants.screenHeight / 4));

        Text replayText = new Text("press space to play again");
        replayText.setFill(WHITE);
        replayText.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 40));
        replayText.setStroke(BLACK);
        replayText.setStrokeWidth(1.2);
        replayText.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 7));
        replayText.layoutYProperty().bind(scene.heightProperty().divide(1.2));

        Rectangle rectangle = new Rectangle(Constants.screenWidth / 2, Constants.screenHeight / 2);
        rectangle.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(rectangle.getWidth() / 2));
        rectangle.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(rectangle.getHeight() / 2));
        rectangle.setFill(Constants.gameOverRectangleColor);
        rectangle.setStroke(Constants.gameOverStrokeColor);
        root.getChildren().add(rectangle);
        root.getChildren().add(gameWonText);
        root.getChildren().add(gameWonText2);
        root.getChildren().add(replayText);
    }

    public static void startTimeLine() {
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            GamePlay.dog.moveGhost();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        initialBoard();

    }

    private static void initialBoard() {
        timeline.stop();
        Text initial = new Text("Whoops!");
        initial.setFont(Font.font("HELVETICA", FontWeight.BOLD, Constants.screenWidth / 20));
        initial.setFill(WHITE);
        initial.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 10));
        initial.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(Constants.screenHeight / 6));

        Text start = new Text("Your little doggo just scattered your CV around the apartment! \n" +
                "Now you have to collect all the pages \n" +
                "and put them in the folder in the bottom left ↙ \n" +
                "But remember: you can only take one page at the time! \n" +
                "Follow the instructions in the upper left ↖ \n" +
                "Be carefull! Your little doggo is very angry! \n\n" +
                "Press S if your ready, press P if you want to pause the game \n" +
                "Good luck!");
        start.setFont(Font.font("HELVETICA", Constants.screenWidth / 70));
        start.setFill(WHITE);
        start.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(Constants.screenWidth / 5));
        start.layoutYProperty().bind(scene.heightProperty().divide(1.5).subtract(Constants.screenHeight / 4));

        Rectangle rectangle = new Rectangle(Constants.screenWidth / 2, Constants.screenHeight / 2);
        rectangle.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(rectangle.getWidth() / 2));
        rectangle.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(rectangle.getHeight() / 2));
        rectangle.setFill(Constants.gameOverRectangleColor);
        rectangle.setStroke(Constants.gameOverStrokeColor);
        root.getChildren().add(rectangle);
        root.getChildren().add(start);
        root.getChildren().add(initial);
    }

}

































