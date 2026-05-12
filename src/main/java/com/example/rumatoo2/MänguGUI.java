package com.example.rumatoo2;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MänguGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextArea mängijaStats = new TextArea("500");
        TextArea vastaneStats = new TextArea("500");
        mängijaStats.setMaxHeight(100);
        vastaneStats.setMaxHeight(100);
        mängijaStats.setMaxWidth(100);
        vastaneStats.setMaxWidth(100);

        ImageView pilt = new ImageView(new Image("file:oliver.png"));
        pilt.setFitHeight(300);
        pilt.setFitWidth(300);
        pilt.setPreserveRatio(true);
        pilt.setLayoutX(100);
        pilt.setLayoutY(100);

        HBox paigutusÜleval = new HBox();
        paigutusÜleval.getChildren().addAll(mängijaStats, pilt, vastaneStats);

        TextArea kast = new TextArea("Algne tekst");
        kast.setMaxHeight(150);

        Button ründaVastast = new Button("Ründa vastast");
        Button raviEnnast = new Button("Ravi ennast 50%");
        Button vaataEsemeid = new Button("Vaata enda esemeid");
        Button vaataOmadusi = new Button("Vaata omadusi");

        ründaVastast.setOnAction(e -> kast.setText("a"));

        ründaVastast.setMinWidth(125);
        raviEnnast.setMinWidth(125);
        vaataEsemeid.setMinWidth(125);
        vaataOmadusi.setMinWidth(125);

        VBox nupud = new VBox();
        nupud.setSpacing(5);
        nupud.setMinWidth(125);
        nupud.getChildren().addAll(ründaVastast, raviEnnast, vaataEsemeid, vaataOmadusi);

        HBox paigutus = new HBox();
        paigutus.getChildren().addAll(nupud, kast);
        paigutus.setMaxHeight(150);

        VBox juur = new VBox(10, paigutusÜleval, paigutus);

        stage.setScene(new Scene(juur, 500, 400));
        stage.setTitle("Delta Areen");
        stage.setResizable(false);
        stage.show();
    }


}
