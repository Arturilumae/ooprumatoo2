package com.example.rumatoo2;

import java.util.Random;

public class Tegelane {
    private String nimi;
    private double algsedElud;
    private double maksimumElud;
    private double elud;
    private double algneTugevus;
    private double tugevus;
    private double õnn=1;
    private int veritsus=0;
    private int vigastus=0;
    private Asi[] sisu = new Asi[10];

    public Tegelane(String nimi, double elud, double tugevus) {
        this.nimi = nimi;
        this.algsedElud = elud;
        this.maksimumElud=elud;
        this.elud=elud;
        this.tugevus = tugevus;
        this.algneTugevus =tugevus;


        this.sisu[0] = new Asi("Stimulant",Esemetüüp.Rünnak2x, 0.1,0.05,1);
        this.sisu[1] = new Asi("Must leib",20,Esemetüüp.Elud,0.25);
        this.sisu[2] = new Asi("Takjas",Esemetüüp.Vigastus, 15, 0.25,0.15,2.5);
        this.sisu[3] = new Asi("Õli",Esemetüüp.Põikamine, 0.15,0.05,0,5);
        this.sisu[4] = new Asi("Kiisu",Esemetüüp.Veritsus, 20, 0.2,0.05,1.5);
        this.sisu[5] = new Asi("Kevin",Esemetüüp.Tähelepanu, 0.5,0.1,5);
        this.sisu[6] = new Asi("Ristikhein",2,Esemetüüp.Õnn,0.02);
        this.sisu[7] = new Asi("Printer",Esemetüüp.Asjad,1, 1,0.03,1);
        this.sisu[8] = new Asi("Süstal",Esemetüüp.Varastamine, 0.5,0.1,4);
        this.sisu[9] = new Asi("Teritaja",15,Esemetüüp.Rünne,0.2);
    }

    public String näitaOmadusi(){
        Uuenda();
        return  nimi +"\n"+
                "Elud: "+ elud+"/"+maksimumElud+"\n"+
                "Tugevus "+tugevus+"\n"+
                "Õnn: "+õnn+"\n"+
                "Veritsusi: "+veritsus+"\n"+
                "Takjaid: "+vigastus;
    }
    public String statid(){
        Uuenda();
        return  "%s\n".formatted(nimi) +
                "Elupunkte: %s \n".formatted(elud) +
                "Tugevuspunkte: %s".formatted(tugevus);
    }

    private void Uuenda(){
        tugevus = algneTugevus +sisu[9].väärtus();
        double a = algsedElud +sisu[1].väärtus();
        if (a<elud){
            maksimumElud=a;
            elud=a;
        }else{
            maksimumElud=a;
        }
    }

    public void saiPihta(double kahju) {
        this.elud -= kahju;
    }

    public void paraneb(double eluJuurde) {
        if(maksimumElud>=elud+eluJuurde){
            this.elud += eluJuurde;
        }else{
            this.elud = maksimumElud;
        }
    }

    public double getElud(){
        Uuenda();
        return elud;
    }
    public double getMaxElud(){
        Uuenda();
        return maksimumElud;
    }

    public double rünnak() {
        Uuenda();
        return tugevus;
    }

    public String toString(){
        return nimi;
    }

    public String asiToString(Esemetüüp tüüp){
        for (Asi a:sisu){
            if(a.Tüüp()==tüüp){
                return a.toString();
            }
        }
        return "";
    }

    public String näitaEsemed(){
        String tul="Sul on:\n";
        for (Asi a: sisu){
            if (a.OnOlemas()){
                tul+= a.getPalju()+" "+a+"\n";
            }
        }
        return tul;
    }

    public boolean OnOlemas(Esemetüüp a){
        for (Asi b:sisu){
            if (b.Tüüp()==a && b.OnOlemas()) return true;
        }
        return false;
    }

    public double getVäärtus(Esemetüüp a){
        for (Asi b:sisu){
            if (b.Tüüp()==a) return b.väärtus();
        }
        return 0;
    }

    public double getTõenäosus(Esemetüüp a){
        for (Asi b:sisu){
            if (b.Tüüp()==a) return b.Tõenäosus();
        }
        return -1;
    }

    public double getÕnn(){
        if(OnOlemas(Esemetüüp.Õnn)){
            return õnn*sisu[6].väärtus();
        }
        return õnn;
    }

    public void lisaVigastus(){
         vigastus++;
    }
    public void lisaVeritsus(){
        veritsus++;
    }

    public boolean veritsemine(){
        if (veritsus>0) {
            double tugevus=veritsus*sisu[4].väärtus();
            saiPihta(tugevus);
            System.out.println(nimi+ " veritses ja kaotas "+tugevus+" elu");
            if(!kasElus()){
                System.out.println("Veritses surnuks");
                return false;
            }else{
                System.out.println("Alles jäi "+elud);
                return true;
            }
        }
        return true;
    }

    public void vigastamine(StringBuilder sb){
        if (vigastus>0) {
            double tugevus=vigastus*sisu[2].väärtus();
            saiPihta(tugevus);
            sb.append(nimi+ " sai vigastada Takja poolt ja kaotas "+tugevus+" elu");
            //System.out.println(nimi+ " sai vigastada Takja poolt ja kaotas "+tugevus+" elu");
            if(!kasElus()){
                throw new TegelaneSuri(nimi);
                //System.out.println("Vigastas surnuks");
            }
        }
    }

    public boolean kasElus(){
        return !(elud <= 0);
    }

    public Esemetüüp suvalineEse(){
        double sahver=0;
        Random rand = new Random();
        double suvaline = rand.nextDouble();
        for (Asi a:sisu){
            sahver+=a.getHaruldus();
            if(suvaline <= sahver){
                return a.Tüüp();
            }
        }
        return null;
    }

    public void lisaEse(Esemetüüp a, int b){
        for(Asi c:sisu){
            if(c.Tüüp()==a){
                c.lisaEse(b);
            }
        }
    }

    //Debugimiseks!!
    public void setPalju(Esemetüüp a, int b){
        for (Asi c:sisu){
            if(c.Tüüp()==a){
                c.setPalju(b);
            }
        }
    }
}