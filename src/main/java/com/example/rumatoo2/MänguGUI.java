package com.example.rumatoo2;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MänguGUI{
    public Button ründaVastast;
    public Button raviEnnast;
    public Button vaataEsemeid;
    public Button vaataOmadusi;
    public Button failiSalvestus;

    public TextArea kast;
    public TextArea mängijaStats;
    public TextArea vastaneStats;
    public ImageView pilt;

    public void ehita(Stage stage) {
        mängijaStats = new TextArea("");
        vastaneStats = new TextArea("");
        mängijaStats.setEditable(false);
        vastaneStats.setEditable(false);
        mängijaStats.setWrapText(true);
        vastaneStats.setWrapText(true);

        pilt = new ImageView(new Image("file:oliver.png"));
        pilt.setPreserveRatio(true);
        pilt.setFitHeight(500);
        pilt.setFitWidth(400);

        HBox paigutusÜleval = new HBox();
        paigutusÜleval.getChildren().addAll(mängijaStats, pilt, vastaneStats);

        kast = new TextArea(
                     "Tere tulemast Delta areenile! \nSaad vajutda nuppe, et tegevusi teha.\n" +
                        "Ülesanne on kõik vastased ära tappa.\n" +
                         "Kastid on rohelised kui on sinu kord, punased kui on vastase kord.\n" +
                        "Alustamiseks vajuta Tühikut või kilki hiirega siia kasti.\n");
        kast.setEditable(false);
        kast.setWrapText(true);

        ründaVastast = new Button("(1) Ründa vastast");
        raviEnnast = new Button("(2) Ravi ennast 50%");
        vaataEsemeid = new Button("(3) Vaata esemeid");
        vaataOmadusi = new Button("(4) Vaata omadusi");
        failiSalvestus = new Button(("(5) Salvesta Mäng"));



        ründaVastast.setMaxWidth(Double.MAX_VALUE);
        raviEnnast.setMaxWidth(Double.MAX_VALUE);
        vaataEsemeid.setMaxWidth(Double.MAX_VALUE);
        vaataOmadusi.setMaxWidth(Double.MAX_VALUE);
        failiSalvestus.setMaxWidth(Double.MAX_VALUE);

        ründaVastast.setMaxHeight(Double.MAX_VALUE);
        raviEnnast.setMaxHeight(Double.MAX_VALUE);
        vaataEsemeid.setMaxHeight(Double.MAX_VALUE);
        vaataOmadusi.setMaxHeight(Double.MAX_VALUE);
        failiSalvestus.setMaxHeight(Double.MAX_VALUE);

        VBox nupud = new VBox();
        nupud.setSpacing(3);
        nupud.setFillWidth(true);
        nupud.getChildren().addAll(ründaVastast, raviEnnast, vaataEsemeid, vaataOmadusi, failiSalvestus);

        HBox paigutus = new HBox();
        paigutus.getChildren().addAll(nupud, kast);
        nupud.prefWidthProperty().bind(paigutus.widthProperty().divide(3));
        kast.prefWidthProperty().bind(paigutus.widthProperty().multiply(2).divide(3));


        VBox juur = new VBox(10, paigutusÜleval, paigutus);

        //akna suuruse muutmiste prioriteetid
        VBox.setVgrow(nupud, Priority.ALWAYS);
        VBox.setVgrow(paigutusÜleval, Priority.ALWAYS);
        VBox.setVgrow(paigutus, Priority.ALWAYS);
        VBox.setVgrow(ründaVastast, Priority.ALWAYS);
        VBox.setVgrow(raviEnnast, Priority.ALWAYS);
        VBox.setVgrow(vaataEsemeid, Priority.ALWAYS);
        VBox.setVgrow(vaataOmadusi, Priority.ALWAYS);
        VBox.setVgrow(failiSalvestus, Priority.ALWAYS);

        HBox.setHgrow(paigutus, Priority.ALWAYS);
        HBox.setHgrow(paigutusÜleval, Priority.ALWAYS);


        Scene scene = new Scene(juur, 500, 400);

        pilt.fitWidthProperty().bind(scene.widthProperty().divide(2));


        //AI poolt tehtud mis aitab hoida aspect ratiot + teksti suurus

        StringBinding fondiStiil = Bindings.createStringBinding(
                () -> "-fx-font-size: " + Math.max(10, scene.getWidth() / 35) + "px;"+
                "-fx-border-color: #FF7F50;" +
                        "-fx-border-width: 2;",
                scene.widthProperty()
        );
        StringBinding nuppuStiil = Bindings.createStringBinding(
                () -> "-fx-font-size: " + Math.max(10, scene.getWidth() / 35) + "px;"+
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;",
                scene.widthProperty()
        );
        StringBinding kastiStiil = Bindings.createStringBinding(
                () -> "-fx-font-size: " + Math.max(10, scene.getWidth() / 35) + "px;"+
                        "-fx-border-color: black;" +
                        "-fx-border-width: 3;"+
                        "-fx-text-alignment: center;",
                scene.widthProperty()
        );

        ründaVastast.styleProperty().bind(nuppuStiil);
        raviEnnast.styleProperty().bind(nuppuStiil);
        vaataEsemeid.styleProperty().bind(nuppuStiil);
        vaataOmadusi.styleProperty().bind(nuppuStiil);
        mängijaStats.styleProperty().bind(fondiStiil);
        vastaneStats.styleProperty().bind(fondiStiil);
        failiSalvestus.styleProperty().bind(fondiStiil);
        kast.styleProperty().bind(kastiStiil);
        vastaneStats.styleProperty().bind(fondiStiil.concat("-fx-text-alignment: right;"));

        NuppudOff();
        failiSalvestamaOff();


        scene.widthProperty().addListener((obs, vana, uus) -> {
            // peale layout'i ümberarvutust
            javafx.application.Platform.runLater(() -> {
                double vajalikKõrgus = juur.prefHeight(uus.doubleValue());
                if (scene.getHeight() < vajalikKõrgus) {
                    stage.setHeight(vajalikKõrgus);  // +40 raamile
                }
            });
        });

        //lõpeb AI poolt tehtud
        stage.setScene(scene);
        stage.setTitle("Delta Areen");
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.show();


    }

    public void NuppudOn(){
        ründaVastast.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
        raviEnnast.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
        vaataEsemeid.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
        vaataOmadusi.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
    }
    public void NuppudOff(){
        ründaVastast.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(3), Insets.EMPTY)));
        raviEnnast.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(3), Insets.EMPTY)));
        vaataEsemeid.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(3), Insets.EMPTY)));
        vaataOmadusi.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(3), Insets.EMPTY)));
    }
    public void failiSalvestamaOn(){
        failiSalvestus.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
    }
    public void failiSalvestamaOff(){
        failiSalvestus.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(3), Insets.EMPTY)));
    }

    public void pildiMuutmine(){
        pilt.setImage(new Image("file:jarglane.png"));
    }
}