package com.example.rumatoo2;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Peaklass extends Application {
    private final MänguGUI MG = new MänguGUI();
    private final StringBuilder sb = new StringBuilder();
    private final ManguHaldaja MH = new ManguHaldaja();

    private Tegelane mängija;
    private Tegelane vastane;

    private boolean mängualustada=true;
    private boolean mängja_kord=false;
    private boolean saad_salvestada=false;
    private boolean mängläbi=false;
    public static int skoor=0;


    @Override
    public void start(Stage stage) throws Exception {
        MG.ehita(stage);

        if(!MH.kasFailEksisteerib()){
            Tegelane[] tegelased = MH.alustaMängu(sb);
            mängija=tegelased[0];
            vastane=tegelased[1];
            MG.kast.appendText(sb.toString());
            sb.setLength(0);
        }else{
            MG.kast.appendText("Leitsin faili, tahad laadida vana mängu?");
            MG.failiSalvestus.setText("Lae vana mäng");
            MG.failiSalvestamaOn();
        }

        //mängija interaksioon
        //hiir
        MG.kast.setOnMouseClicked(e -> {
            try {
                kastiteod();
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
        });

        MG.ründaVastast.setOnAction(e -> {
            try {
                mängija_rünnak();
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
        });

        MG.raviEnnast.setOnAction(e -> {
            try {
                raviEnnast();
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
        });

        MG.vaataEsemeid.setOnAction(e -> {
            try {
                vaataEsemeid();
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
        });

        MG.vaataOmadusi.setOnAction(e -> {
            try {
                näitaOmadusi();
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
        });
        MG.failiSalvestus.setOnAction(e -> {
            try {
                if(mängualustada) {
                    laadidaSalvestus();
                }else{
                    salvestadaMäng();
                }
            } catch (Exception f) {
                MG.kast.appendText(f.getMessage());
            }
        });

        //klaviatuur
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            boolean töödeldud = true;
            try {
                switch (e.getCode()) {
                    case SPACE -> kastiteod();
                    case DIGIT1, NUMPAD1 -> mängija_rünnak();
                    case DIGIT2, NUMPAD2 -> raviEnnast();
                    case DIGIT3, NUMPAD3 -> vaataEsemeid();
                    case DIGIT4, NUMPAD4 -> näitaOmadusi();
                    case DIGIT5, NUMPAD5 -> {
                        if(mängualustada){
                            try{
                                laadidaSalvestus();
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }else{
                            salvestadaMäng();
                        }
                    }
                    default -> töödeldud = false;
                }
            } catch (ViganeSisestus f) {
                MG.kast.appendText(f.getMessage());
            }
            if (töödeldud) e.consume();
        });
    }

    private void salvestadaMäng(){
        if(mängja_kord&&saad_salvestada){
            mängja_kord = false;
            saad_salvestada=false;
            MH.kirjutaFaili(mängija);
            MG.kast.setText("Mäng salvestatud faili: andmed.dat");
            mängläbi=true;
            MG.NuppudOff();
            MG.failiSalvestamaOff();
        }else{
            throw new ViganeSisestus("Pead ootama oma korda, et salvestada.\n");
        }

    }

    private void laadidaSalvestus() throws FileNotFoundException {
        if(mängualustada){
            mängualustada=false;
            mängija = MH.loeFailist();
            vastane = MH.uusVastane(skoor);
            uuenda();
            MG.kast.setText("Mida teed?");
            MG.NuppudOn();
            mängualustada = false;
            mängja_kord=true;
            MG.failiSalvestus.setText("(5) Salvesta Mäng");
            MG.failiSalvestamaOff();

        }else {
            throw new ViganeSisestus("Vigane sisestus\n");
        }
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
            mängja_kord=true;
            saad_salvestada=true;
        }else if (!mängja_kord) {
            mängja_kord=true;
            saad_salvestada=true;
            sb.setLength(0);
            vastase_kord();
        }else{
            throw new ViganeSisestus("Tee oma otsus!!!\n");
        }
    }

    private void mängija_rünnak(){
        if(mängja_kord){
            sb.setLength(0);
            try{
                MH.rünnak(mängija,vastane,sb);
            }catch (TegelaneSuri e){
                if(e.getMessage().equals("Mängija")){
                    uuenda();
                    MG.kast.appendText(sb + "Said surma.. \nJärgmine kord läheb paremini");
                    mängläbi = true;
                    return;
                }else{
                    vastaneTapetud();
                    return;
                }
            }
            mängija_korra_lõpp();
        }else {
            throw new ViganeSisestus("Pole sinu kord!!!\n");
        }
    }

    private void mängija_korra_lõpp(){
        MG.kast.setText(sb.toString());
        mängja_kord=false;
        saad_salvestada=false;
        MG.NuppudOff();
        MG.failiSalvestamaOff();
        uuenda();
    }

    private void uuenda(){
        MG.mängijaStats.setText(mängija.statid());
        MG.vastaneStats.setText(vastane.statid());
    }

    private void vastaneTapetud(){
        MG.kast.appendText("Tubli, tapsid vastase ära!\n");
        skoor+=1;
        if (skoor >= 10) {
            MG.kast.setText("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
            mängläbi=true;
        }
        MH.tasu(mängija,sb);
        sb.append("Tuleb uus vastane\n");
        MG.kast.setText(sb.toString());
        vastane = MH.uusVastane(skoor);
        uuenda();
    }

    private void raviEnnast(){
        if(!mängualustada&&mängja_kord){
            sb.setLength(0);
            MH.elusta50(mängija,sb);
            MG.kast.setText(sb.toString());
            mängja_kord=false;
            MG.NuppudOff();
            MG.failiSalvestamaOff();
            uuenda();
        }else {
            throw new ViganeSisestus("Pole sinu kord!!!\n");
        }
    }

    private void vaataEsemeid(){
        if(mängja_kord){
            MG.kast.setText(mängija.näitaEsemed());
        }else {
            throw new ViganeSisestus("Pole sinu kord!!!\n");
        }
    }

    private void näitaOmadusi(){
        if(mängja_kord){
            MH.näitaOmadusi(mängija, vastane, MG.kast);
        }else {
            throw new ViganeSisestus("Pole sinu kord!!!\n");
        }
    }

    private void vastase_kord(){
        sb.setLength(0);
        try{
            MH.rünnak(vastane,mängija,sb);
        }catch (TegelaneSuri e){
            if(e.getMessage().equals("Mängija")){
                uuenda();
                MG.kast.appendText(sb + "Said surma.. \nJärgmine kord läheb paremini");
                mängläbi=true;
                return;
            }else{
                vastaneTapetud();
            }
        }
        MG.kast.setText(sb.toString());
        MG.NuppudOn();
        MG.failiSalvestamaOn();
        uuenda();
    }

    static void main(String[] args) {
        launch(args);
    }
}