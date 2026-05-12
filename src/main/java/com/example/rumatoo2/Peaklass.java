package com.example.rumatoo2;

import java.util.Scanner;

public class Peaklass{
    static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ManguHaldaja MH = new ManguHaldaja();

        Tegelane[] tegelased = MH.alustaMängu();
        Tegelane mängija = tegelased[0];
        Tegelane vastane = tegelased[1];

        int skoor =0;

        boolean mängijaKord =true;
        while (true){
            System.out.println("-----------------------------------------------------------------");
            if (mängijaKord){
                System.out.println(
                        "Mida teed?\n" +
                        "\"1\": Ründa vastast.\n" +
                        "\"2\": Ravi ennast 50%.\n" +
                        "\"3\": Vaata enda esemeid.\n"+
                        "\"4\": Vaata omadusi."

                );

                String vastus = scan.nextLine();

                switch (vastus.strip()){
                    case "1":
                        if(MH.rünnak(mängija,vastane)){
                            System.out.println("Said surma.. \nJärgmine kord läheb paremini");
                            System.exit(0);
                        }
                        if (!vastane.kasElus()){
                            System.out.println("Tubli, tapsid vastase ära!");
                            skoor +=1;
                            MH.tasu(mängija);
                            if(skoor ==10){
                                System.out.println("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
                                System.exit(0);
                            }
                            vastane = MH.uusVastane(skoor);
                            break;
                        }else {
                            MH.püsiEfektid(mängija);
                            mängijaKord =false;
                            break;
                        }
                    case "2":
                        MH.elusta50(mängija);
                        mängijaKord =false;
                        break;
                    case "3":
                        System.out.println(mängija.näitaEsemed());
                        break;
                    case "4":
                        MH.näitaOmadusi(mängija,vastane);
                        break;
                    default:
                        System.out.println("Mittesobiv käsk");
                        break;
                }
            }else{
                if(MH.rünnak(vastane, mängija)){
                    System.out.println("Tubli, tapsid vastase ära!");
                    skoor +=1;
                    MH.tasu(mängija);
                    if(skoor ==10){
                        System.out.println("Tubli! Tapsid kõik vastased ära, mäng on läbi!");
                        System.exit(0);
                    }
                    vastane = MH.uusVastane(skoor);
                }
                if (!mängija.kasElus()){
                    System.out.println("Said surma.. \nJärgmine kord läheb paremini");
                    System.exit(0);
                }else {
                    mängijaKord =true;
                }

                MH.püsiEfektid(vastane);
            }
        }
    }
}