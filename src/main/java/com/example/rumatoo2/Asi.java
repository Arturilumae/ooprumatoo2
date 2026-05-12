package com.example.rumatoo2;

public class Asi {
    private String nimi; //Asja nimi
    private Esemetüüp tüüp; //Asja tüüp
    private double väärtus; //Asja väärtus midagi tehes
    private double tõenäosus; //Asja kasutamise tõenäosus 0.0-1.0
    private double haruldus; //Kui raske asja saada on 0.0-1.0
    private int palju; //palju seda Asja on
    private double kasvamisKiirus; //Kasutab arvutamisel kui kiiresti kasvab Asja tõenäosus mitme asja puhul

    //Kui on vaja kõik määrada
    public Asi(String nimi, Esemetüüp tüüp, double väärtus, double tõenäosus,double haruldus,double kiirus) {
        this.nimi = nimi;
        this.tüüp = tüüp;
        this.väärtus = väärtus;
        this.tõenäosus = tõenäosus;
        this.haruldus=haruldus;
        palju = 0;
        kasvamisKiirus=kiirus;
    }
    //Kui on ainult tõenäosust vaja
    public Asi(String nimi, Esemetüüp tüüp, double tõenäosus,double haruldus,double kiirus) {
        this.nimi = nimi;
        this.tüüp = tüüp;
        this.väärtus =0;
        this.tõenäosus = tõenäosus;
        this.haruldus=haruldus;
        palju = 0;
        kasvamisKiirus=kiirus;
    }

    //Kui on ainult väärtust vaja
    public Asi(String nimi,double väärtus, Esemetüüp tüüp,double haruldus) {
        this.nimi = nimi;
        this.tüüp = tüüp;
        this.väärtus = väärtus;
        this.haruldus=haruldus;
        this.tõenäosus = 0;
        palju = 0;
        kasvamisKiirus=0;
    }


    //Tagastab Asja väärtuse
    public double väärtus(){
        if (tüüp==Esemetüüp.Vigastus||tüüp==Esemetüüp.Veritsus){return väärtus;}
        return väärtus*palju;
    }

    //Tagastab Asja tõenäosuse
    public double Tõenäosus() {
        return tõenäosus + tõenäosus*(palju-1)/kasvamisKiirus;
    }

    //Tagastab, kas Asi on olemas
    public boolean OnOlemas(){
        if(this.palju >0) {return true;}
        return false;
    }
    //pask

    //tagastab milist tüübi ese on
    public Esemetüüp Tüüp(){
        return tüüp;
    }

    //Lisab Asja juurde
    public void lisaEse(int a){
        palju+=a;
    }

    //Tagastab Asja harulduse
    public double getHaruldus() {
        return haruldus;
    }

    //Tagsatab palju Asja on
    public int getPalju() {
        return palju;
    }

    @Override
    public String toString() {
        return nimi;
    }

    //NB! Debugimiseks
    public void setPalju(int a){
        palju=a;
    }
}