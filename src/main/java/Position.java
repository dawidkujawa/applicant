import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.util.Objects;

public class Position {
    public double x;
    public double y;

    public double width;
    public double height;

    public int row;
    public int column;

    public static void initScreenDimension(){
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Constants.screenWidth = visualBounds.getWidth();
        Constants.screenHeight = visualBounds.getHeight();
    }

    public Position() {
    }

    public Position (int i, int j){

        this.row = i;
        this.column = j;

        this.x = (Constants.screenWidth/ Constants.COLUMN_CELL_ACCOUNT)*j;
        this.y = (Constants.screenHeight/ Constants.ROW_CELL_ACCOUNT)*i;
        this.width = Constants.screenWidth/ Constants.COLUMN_CELL_ACCOUNT;
        this.height = Constants.screenHeight/ Constants.ROW_CELL_ACCOUNT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.x, x) == 0 &&
                Double.compare(position.y, y) == 0 &&
                Double.compare(position.width, width) == 0 &&
                Double.compare(position.height, height) == 0 &&
                row == position.row &&
                column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, row, column);
    }
}
