import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Cell {
    private int type;
    protected Position position;
    private Node node;

    public Cell(Position position, int type) {
        this.position = position;
        this.type = type;
    }

    public void setType(int type){
        this.type = type;
    }

    public Node getNode(){
        if (type == Constants.PHOTO){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.PHOTO_IMAGE));
        }
        else if (type == Constants.SKILLS){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.SKILLS_IMAGE));
        }
        else if (type == Constants.EDUCATION){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.EDUCATION_IMAGE));
        }
        else if (type == Constants.PERSONAL_DATA){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.PERSONAL_DATA_IMAGE));
        }
        else if (type == Constants.EXPERIENCE){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.EXPERIENCE_IMAGE));
        }
        else if (type == Constants.OBSTACLE){
            this.node = new Rectangle(position.x, position.y, position.width, position.height);
            ((Rectangle)node).setFill(new ImagePattern(Constants.OBSTACLE_IMAGE));
        }
        else if (type == Constants.FOLDER){
            this.node = new Rectangle(position.x, position.y, position.width, position.height);
            ((Rectangle)node).setFill(new ImagePattern(Constants.FOLDER_IMAGE));
        }

        else if (type == Constants.LEVER_OFF){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.LEVER_ON_IMAGE));
        }

        else if (type == Constants.LEVER_ON){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.LEVER_OFF_IMAGE));
        }

        else if (type == Constants.EMPTY){
            this.node = new Rectangle(position.x, position.y, position.width, position.height);
            ((Rectangle)node).setFill(Constants.CELL_EMPTY_COLOR);
        }
        else if (type == Constants.DOOR_CLOSED){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.DOOR_CLOSED_IMAGE));
        }

        else if (type == Constants.DOOR_OPEN){
            this.node = new Circle(position.x + position.width / 2, position.y + position.height / 2.5, position.width / 3);
            ((Circle)node).setFill(new ImagePattern(Constants.DOOR_OPEN_IMAGE));
        }

        return node;
    }

    public int getType() {
        return type;
    }
}
