import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Position.initScreenDimension();

        MapGenerator.generateMap(stage).show();

        MapGenerator.addClickListener();

        GamePlay.applicant = new Applicant();
        GamePlay.dog = new Dog();

        MapGenerator.redrawMap();

        MapGenerator.startTimeLine();


    }

}
