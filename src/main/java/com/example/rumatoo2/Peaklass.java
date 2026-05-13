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

public class Peaklass extends Application {
    private final MänguGUI MG = new MänguGUI();
    private final StringBuilder sb = new StringBuilder();
    private final ManguHaldaja MH = new ManguHaldaja();

    private Tegelane mängija;
    private Tegelane vastane;

    private boolean mängualustada=true;
    private boolean mängja_kord=true;
    private boolean mängläbi=false;
    private int skoor=0;


    @Override
    public void start(Stage stage) throws Exception {
        MG.ehita(stage);

        //põhikooood
        Tegelane[] tegelased = MH.alustaMängu();
        mängija = tegelased[0];
        vastane = tegelased[1];

        MG.kast.setOnMouseClicked(e -> {
            kastiteod();
        });

        MG.ründaVastast.setOnAction(e -> {
            mängija_rünnak();
        });

        MG.raviEnnast.setOnAction(e -> {
            raviEnnast();
        });

        MG.vaataEsemeid.setOnAction(e -> {
            MG.kast.setText(mängija.näitaEsemed());
        });

        MG.vaataOmadusi.setOnAction(e -> {
            MH.näitaOmadusi(mängija,vastane,MG.kast);
        });
    }
    private void kastiteod() {
        if(mängläbi){
            System.exit(0);
        }

        if (mängualustada) {
            uuenda();
            MG.kast.setText("Mida teed?");
            MG.NuppudOn();
            mängualustada = false;
        }else if (!mängja_kord) {
            vastase_kord();
        }
    }

    private void mängija_rünnak(){
        if(mängja_kord){
            sb.setLength(0);
            try{
                MH.rünnak(mängija,vastane,sb);
            }catch (TegelaneSuri e){
                if(e.equals("Mängija")){
                    uuenda();
                    MG.kast.appendText(sb + "Said surma.. \nJärgmine kord läheb paremini");
                    return;
                }else{
                    vastaneTapetud();
                }
            }
            //MH.rünnak(mängija,vastane,sb);
            mängija_korra_lõpp();
        }
    }

    private void mängija_korra_lõpp(){
//        if(!vastane.kasElus()){
//            MG.kast.appendText("Tubli, tapsid vastase ära!");
//            skoor+=1;
//            MH.tasu(mängija,sb);
//            if (skoor<10){
//                vastane = MH.uusVastane(skoor,sb);
//            }
//            MG.kast.setText(sb.toString());
//            uuenda();
//            return;
//        }
        MG.kast.setText(sb.toString());
        mängja_kord=false;
        MG.NuppudOff();
        uuenda();
    }

    private void uuenda(){
        MG.mängijaStats.setText(mängija.statid());
        MG.vastaneStats.setText(vastane.statid());
    }

    private void vastaneTapetud(){
        MG.kast.appendText("Tubli, tapsid vastase ära!");
        skoor+=1;
        if (skoor >= 10) {
            MG.kast.setText("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
            mängläbi=true;
        }
        MH.tasu(mängija,sb);
        MG.kast.setText(sb.toString());
        vastane = MH.uusVastane(skoor,sb);
        uuenda();
    }




    private void raviEnnast(){
        sb.setLength(0);
        MH.elusta50(mängija,sb);
        MG.kast.setText(sb.toString());
        mängja_kord=false;
        MG.NuppudOff();
        uuenda();
    }

    private void vastase_kord(){
        sb.setLength(0);
        try{
            MH.rünnak(vastane,mängija,sb);
        }catch (TegelaneSuri e){
            if("Mängija".equals(e.toString())){
                uuenda();
                MG.kast.appendText(sb + "Said surma.. \nJärgmine kord läheb paremini");
                return;
            }else{
                vastaneTapetud();
            }
        }
//        if(MH.rünnak(vastane,mängija,sb)){
//            uuenda();
//            MG.kast.appendText(sb + "Said surma.. \nJärgmine kord läheb paremini");
//            return;
//        };
        MG.kast.setText(sb.toString());
        mängja_kord=true;
        MG.NuppudOn();
        uuenda();
    }

    static void main(String[] args) {
        launch(args);
    }
}