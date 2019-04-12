import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Dog {
    protected Position position;
    private Node node;

    public Dog() {
        this.position = new Position(2, 23);
        GamePlay.dog = this;

    }

    public void moveGhost() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 0) + 1) + 0;

        if (Applicant.position.row > this.position.row) {
            if (Applicant.position.column > this.position.column) {

                randomNum = rand.nextInt((1 - 0) + 1) + 0;

                if (randomNum == 0)
                    moveDown();
                else
                    moveRight();
            }


            if (Applicant.position.column <= this.position.column) {
                randomNum = rand.nextInt((1 - 0) + 1) + 0;

                if (randomNum == 0)
                    moveDown();
                else
                    moveLeft();
            }

        } else if (Applicant.position.row <= this.position.row) {

            if (Applicant.position.column >= this.position.column) {

                randomNum = rand.nextInt((1 - 0) + 1) + 0;

                if (randomNum == 0)
                    moveUp();
                else
                    moveRight();
            }

            if (Applicant.position.column < this.position.column){

                randomNum = rand.nextInt((1 - 0) + 1) + 0;

                if (randomNum == 0)
                    moveUp();
                else
                    moveLeft();
            }
        }

        if (MapGenerator.isApplicant(this.position)) {
            MapGenerator.gameEnded();
        } else {
            MapGenerator.redrawMap();
        }

    }

    public boolean moveUp() {

        if (Grid.getCell(position.row - 1, position.column).getType() == Constants.OBSTACLE)
            return false;

        position = new Position(position.row - 1, position.column);
        MapGenerator.redrawMap();
        return true;

    }

    public boolean moveDown() {

        if (Grid.getCell(position.row + 1, position.column).getType() == Constants.OBSTACLE)
            return false;

        position = new Position(position.row + 1, position.column);
        MapGenerator.redrawMap();

        return true;
    }

    public boolean moveLeft() {

        if (Grid.getCell(position.row, position.column - 1).getType() == Constants.OBSTACLE)
            return false;

        position = new Position(position.row, position.column - 1);
        MapGenerator.redrawMap();

        return true;
    }

    public boolean moveRight() {

        if (Grid.getCell(position.row, position.column + 1).getType() == Constants.OBSTACLE)
            return false;

        position = new Position(position.row , position.column+ 1);
        MapGenerator.redrawMap();

        return true;
    }

    public Node getNode() {

        node = new Circle(position.x + position.width / 2, position.y + position.height / 2, position.width / 3);
        ((Circle) node).setFill(new ImagePattern(Constants.DOG_IMAGE));

        return node;
    }
}


