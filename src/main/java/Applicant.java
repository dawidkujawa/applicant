import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Applicant {
    protected static Position position;
    private static Node node;
    public static boolean cvElementTaken;
    public static int score;
    public static boolean leverOn = false;

    public Applicant() {
        score = 0;
        cvElementTaken = false;
        Applicant.position = new Position(1, 1);
    }

    public static boolean moveUp() {

        if (Grid.getCell(position.row - 1, position.column).getType() == Constants.OBSTACLE) {
            return false;
        }

        position = new Position(position.row - 1, position.column);
        return action();
    }

    public static boolean moveDown() {
        if (Grid.getCell(position.row + 1, position.column).getType() == Constants.OBSTACLE) {
            return false;
        }

        position = new Position(position.row + 1, position.column);
        return action();
    }

    private static boolean action() {
        if (Grid.getCell(position.row, position.column).getType() == Constants.PHOTO && Applicant.cvElementTaken == false) {
            Applicant.cvElementTaken = true;
            Grid.getCell(position.row, position.column).setType(Constants.EMPTY);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.SKILLS && Applicant.cvElementTaken == false) {
            Applicant.cvElementTaken = true;
            Grid.getCell(position.row, position.column).setType(Constants.EMPTY);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.PERSONAL_DATA && Applicant.cvElementTaken == false) {
            Applicant.cvElementTaken = true;
            Grid.getCell(position.row, position.column).setType(Constants.EMPTY);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.EXPERIENCE && Applicant.cvElementTaken == false) {
            Applicant.cvElementTaken = true;
            Grid.getCell(position.row, position.column).setType(Constants.EMPTY);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.EDUCATION && Applicant.cvElementTaken == false) {
            Applicant.cvElementTaken = true;
            Grid.getCell(position.row, position.column).setType(Constants.EMPTY);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.FOLDER && Applicant.cvElementTaken == true) {
            Applicant.cvElementTaken = false;
            Applicant.score++;
            Grid.getCell(position.row, position.column).setType(Constants.FOLDER);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.LEVER_OFF && Applicant.score == 5) {
            Applicant.leverOn = true;
            Grid.getCell(position.row, position.column).setType(Constants.LEVER_ON);
            Grid.getCell(MapGenerator.door.row, MapGenerator.door.column).setType(Constants.DOOR_OPEN);
            MapGenerator.redrawMap();
        } else if (Grid.getCell(position.row, position.column).getType() == Constants.DOOR_OPEN && Applicant.leverOn == true){
            MapGenerator.gameWon();
        }
        return true;
    }

    public static boolean moveLeft() {
        if (Grid.getCell(position.row, position.column - 1).getType() == Constants.OBSTACLE) {
            return false;
        }

        position = new Position(position.row, position.column - 1);
        return action();
    }

    public static boolean moveRight() {
        if (Grid.getCell(position.row, position.column + 1).getType() == Constants.OBSTACLE) {
            return false;
        }

        position = new Position(position.row, position.column + 1);
        return action();
    }

    public Node getNode() {

        double min = position.height;
        if (position.width < position.height)
            min = position.width;

        node = new Circle(position.x + position.width / 2, position.y + position.height / 2, position.width / 3);
        ((Circle) node).setFill(new ImagePattern(Constants.GUY_IMAGE));

        return node;
    }
}
