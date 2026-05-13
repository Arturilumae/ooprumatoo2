package com.example.rumatoo2;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

public class MänguGUI{
    public Button ründaVastast;
    public Button raviEnnast;
    public Button vaataEsemeid;
    public Button vaataOmadusi;

    public TextArea kast;
    public TextArea mängijaStats;
    public TextArea vastaneStats;
    public ImageView pilt;

    final double MIN_SUHE = 0.5;    // ratio(1:2)
    final double MAX_SUHE = 5;    // ratio 5:1

    public void ehita(Stage stage) {
        mängijaStats = new TextArea("");
        vastaneStats = new TextArea("");
        mängijaStats.setEditable(false);
        vastaneStats.setEditable(false);
        mängijaStats.setWrapText(true);
        vastaneStats.setWrapText(true);

        pilt = new ImageView(new Image("file:oliver.png"));
        pilt.setPreserveRatio(true);

        HBox paigutusÜleval = new HBox();
        paigutusÜleval.getChildren().addAll(mängijaStats, pilt, vastaneStats);

        kast = new TextArea(
                     "Tere tulemast Delta areenile!!! \nSaad vajutda nuppe, et tegevusi teha.\n" +
                        "Ülesanne on kõik vastased ära tappa.\n" +
                        "Mängu saab mängida nupputega ja hiirega.\n" +
                        "Alustamiseks vajuta Tühikut või kilki hiirega siia kasti");
        kast.setEditable(false);
        kast.setWrapText(true);

        ründaVastast = new Button("Ründa vastast");
        raviEnnast = new Button("Ravi ennast 50%");
        vaataEsemeid = new Button("Vaata enda esemeid");
        vaataOmadusi = new Button("Vaata omadusi");


        ründaVastast.setMaxWidth(Double.MAX_VALUE);
        raviEnnast.setMaxWidth(Double.MAX_VALUE);
        vaataEsemeid.setMaxWidth(Double.MAX_VALUE);
        vaataOmadusi.setMaxWidth(Double.MAX_VALUE);

        ründaVastast.setMaxHeight(Double.MAX_VALUE);
        raviEnnast.setMaxHeight(Double.MAX_VALUE);
        vaataEsemeid.setMaxHeight(Double.MAX_VALUE);
        vaataOmadusi.setMaxHeight(Double.MAX_VALUE);

        VBox nupud = new VBox();
        nupud.setSpacing(5);
        nupud.setFillWidth(true);
        nupud.getChildren().addAll(ründaVastast, raviEnnast, vaataEsemeid, vaataOmadusi);

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

        HBox.setHgrow(paigutus, Priority.ALWAYS);
        HBox.setHgrow(paigutusÜleval, Priority.ALWAYS);



        Scene scene = new Scene(juur, 500, 400);

        pilt.fitWidthProperty().bind(scene.widthProperty().divide(2));


        //AI pool tehtud mis aitab hoida aspect rasiot + teksti suurus

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
        kast.styleProperty().bind(kastiStiil);
        vastaneStats.styleProperty().bind(fondiStiil.concat("-fx-text-alignment: right;"));

        NuppudOff();


        scene.widthProperty().addListener((obs, vana, uus) -> {
            // peale layout'i ümberarvutust
            javafx.application.Platform.runLater(() -> {
                double vajalikKõrgus = juur.prefHeight(uus.doubleValue());
                if (scene.getHeight() < vajalikKõrgus) {
                    stage.setHeight(vajalikKõrgus);  // +40 raamile
                }
            });
        });

        //lõppeb AI poolt tehtud

        stage.setScene(scene);
        stage.setTitle("Delta Areen");
        stage.setResizable(true);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
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
}



//        Scanner scan = new Scanner(System.in);
//        ManguHaldaja MH = new ManguHaldaja();
//
//        Tegelane[] tegelased = MH.alustaMängu();
//        Tegelane mängija = tegelased[0];
//        Tegelane vastane = tegelased[1];
//
//        int skoor =0;
//
//        boolean mängijaKord =true;
//        while (true){
//            System.out.println("-----------------------------------------------------------------");
//            if (mängijaKord){
//                System.out.println(
//                        "Mida teed?\n" +
//                        "\"1\": Ründa vastast.\n" +
//                        "\"2\": Ravi ennast 50%.\n" +
//                        "\"3\": Vaata enda esemeid.\n"+
//                        "\"4\": Vaata omadusi."
//
//                );
//
//                String vastus = scan.nextLine();
//
//                switch (vastus.strip()){
//                    case "1":
//                        if(MH.rünnak(mängija,vastane)){
//                            System.out.println("Said surma.. \nJärgmine kord läheb paremini");
//                            System.exit(0);
//                        }
//                        if (!vastane.kasElus()){
//                            System.out.println("Tubli, tapsid vastase ära!");
//                            skoor +=1;
//                            MH.tasu(mängija);
//                            if(skoor ==10){
//                                System.out.println("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
//                                System.exit(0);
//                            }
//                            vastane = MH.uusVastane(skoor);
//                            break;
//                        }else {
//
//                            MH.püsiEfektid(mängija);
//                            mängijaKord =false;
//                            break;
//                        }
//                    case "2":
//                        MH.elusta50(mängija);
//                        mängijaKord =false;
//                        break;
//                    case "3":
//                        System.out.println(mängija.näitaEsemed());
//                        break;
//                    case "4":
//                        MH.näitaOmadusi(mängija,vastane);
//                        break;
//                    default:
//                        System.out.println("Mittesobiv käsk");
//                        break;
//                }
//            }else{
//                if(MH.rünnak(vastane, mängija)){
//                    System.out.println("Tubli, tapsid vastase ära!");
//                    skoor +=1;
//                    MH.tasu(mängija);
//                    if(skoor ==10){
//                        System.out.println("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
//                        System.exit(0);
//                    }
//                    vastane = MH.uusVastane(skoor);
//                }
//                if (!mängija.kasElus()){
//                    System.out.println("Said surma.. \nJärgmine kord läheb paremini");
//                    System.exit(0);
//                }else {
//                    mängijaKord =true;
//                }
//
//                MH.püsiEfektid(vastane);
//            }
//        }