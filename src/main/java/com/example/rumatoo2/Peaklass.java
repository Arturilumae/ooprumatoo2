package com.example.rumatoo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class Peaklass extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Label silt = new Label("Delta Areen");
        StackPane juur = new StackPane(silt);







        Scene steen = new Scene(juur, 400,300);

        stage.setTitle("Delta Areen");
        stage.setScene(steen);
        stage.show();
    }

    static void main(String[] args) {
        launch(args);
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
    }


}