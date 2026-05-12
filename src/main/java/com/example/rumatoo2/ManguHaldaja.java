package com.example.rumatoo2;

import java.util.Random;

public class ManguHaldaja {


    //prindib juhendi
    // teeb mängija ja vastase objecti ja tagastab need


    public Tegelane[] alustaMängu(){
        System.out.println("Tere tulemast Delta areenile");

        Tegelane[] tegelased = new Tegelane[2];
        tegelased[0] = new Tegelane("Mängija",100,20);
        tegelased[1] = new Tegelane("Oliver", 30, 25);

        //tegelased[1].setPalju(Esemetüüp.Vigastus,10); //Katsetuseks lisan asja

        return tegelased;
    }

    //Arvutab rünnaku efekte ja väljastab info ekraanile
    private boolean[] ründeKalkuleerimine(Tegelane T1, Tegelane T2){
        double tugevus = T1.rünnak();
        Random rand = new Random();
        if(!T1.vigastamine()){
            return new boolean[]{false, true};
        }
        if (T2.OnOlemas(Esemetüüp.Põikamine)&&rand.nextDouble()<=T2.getTõenäosus(Esemetüüp.Põikamine)*T2.getÕnn()){
            System.out.println(T2+" oli piisavalt osav ja põikas eest ära");
            return new boolean[]{true, false};
        }
        System.out.println(T1+" ründab tugevusega: "+tugevus);
        T2.saiPihta(tugevus);
        if (T1.OnOlemas(Esemetüüp.Varastamine)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Varastamine)*T1.getÕnn()){
            System.out.println("Tegelasel "+T1+" õnnestus varastada endale tugevuse võrra elusid juurde");
            T1.paraneb(tugevus);
        }
        if (T1.OnOlemas(Esemetüüp.Vigastus)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Vigastus)*T1.getÕnn()){
            System.out.println("Tegelasel "+T1+" õnnestus anda tegelasele "+T2+" takjas");
            T2.lisaVigastus();
        }
        if (T1.OnOlemas(Esemetüüp.Veritsus)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Veritsus)*T1.getÕnn()){
            System.out.println("Tegelasel "+T1+" õnnestus anda tegelasele "+T2+" veritsus");
            T2.lisaVeritsus();
        }
        boolean elus=T2.kasElus();
        if(elus){
            System.out.println(T2+" sai pihta, alles jäi "+ T2.getElud()+" elu");
            return new boolean[]{true, false};
        }else{
            System.out.println(T2+" sai pihta ja sai surma");
            return new boolean[]{false, false};
        }
    }

    //Tegeleb rünnaku võimalustega ja kutsub välja "ründeKalkuleerimine()"
    public boolean rünnak(Tegelane T1, Tegelane T2){// Esimene tegelane ründab teist
        Random rand = new Random();
        if(T2.OnOlemas(Esemetüüp.Tähelepanu)){
            if(rand.nextDouble()<=T2.getTõenäosus(Esemetüüp.Tähelepanu)*T2.getÕnn()){
                System.out.println(T1+" unustas rünnata, sest jäi Kevinit kuulama");
                return false;
            }
        }
        if(T1.OnOlemas(Esemetüüp.Rünnak2x)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Rünnak2x)*T1.getÕnn()){
            System.out.println("Tegelasel" +T1+ " vedas ja ründab kaks korda");
            boolean [] väärtused = ründeKalkuleerimine(T1,T2);
            if(!väärtused[0]){
                ründeKalkuleerimine(T1,T2);
            }

            return väärtused[1];
        }else{
            return ründeKalkuleerimine(T1,T2)[1];
        }
    }

    //Annab tegelasele 50% elusid tagasi
    public void elusta50(Tegelane T){
        double a = T.getMaxElud()/2;
        T.paraneb(a);
        System.out.println("Mängija ravib ennast, alles on " + T.getElud() + " elu");
    }

    //Tegeleb tegelase käigu lõpus olevate efektitega
    public boolean püsiEfektid(Tegelane T){
        return T.veritsemine();
    }

    //teeb uue vastase suvaliste väärtustega
    public Tegelane uusVastane(int skoor){
        Random rand = new Random();
        System.out.println("Tuleb uus vastane");
        return new Tegelane("Oliveri järglane",rand.nextInt(100)+1+30*skoor,rand.nextInt(50)+1+10*skoor);
    }

    //tegeleb suvalise eseme lisamisega
    public void tasu(Tegelane T){
        Random rand = new Random();

        Esemetüüp ese = T.suvalineEse();
        if(T.OnOlemas(Esemetüüp.Asjad)&&rand.nextDouble()<=T.getTõenäosus(Esemetüüp.Asjad)*T.getÕnn()){
            int a = (int) T.getVäärtus(Esemetüüp.Asjad);
            System.out.println("Vedas, saad "+(a+1)+" "+T.asiToString(ese));
            T.lisaEse(ese,a+1);
        }else{
            System.out.println("Saad ühe "+T.asiToString(ese));
            T.lisaEse(ese,1);
        }

    }

    public void näitaOmadusi(Tegelane T1, Tegelane T2){
        System.out.println("*".repeat(30));
        System.out.println(T1.näitaOmadusi());
        System.out.println("*".repeat(30));
        System.out.println(T2.näitaOmadusi());
        System.out.println("*".repeat(30));
    }
}