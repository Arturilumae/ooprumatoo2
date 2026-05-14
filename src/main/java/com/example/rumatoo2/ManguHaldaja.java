package com.example.rumatoo2;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.Random;

public class ManguHaldaja {

    // teeb mängija ja vastase objecti ja tagastab need


    public Tegelane[] alustaMängu(){
        Tegelane[] tegelased = new Tegelane[2];
        tegelased[0] = new Tegelane("Mängija",100,20);
        tegelased[1] = new Tegelane("Oliver", 30, 25);

        //tegelased[0].setPalju(Esemetüüp.Vigastus,10); //Katsetuseks lisan asja

        return tegelased;
    }

    //Arvutab rünnaku efekte ja väljastab info ekraanile
    private void ründeKalkuleerimine(Tegelane T1, Tegelane T2, StringBuilder sb){
        double tugevus = T1.rünnak();
        Random rand = new Random();

        T1.vigastamine(sb);

        if (T2.OnOlemas(Esemetüüp.Põikamine)&&rand.nextDouble()<=T2.getTõenäosus(Esemetüüp.Põikamine)*T2.getÕnn()){
            sb.append(T2).append(" oli piisavalt osav ja põikas eest ära.\n");
            //System.out.println(T2+" oli piisavalt osav ja põikas eest ära");
            return;
        }
        sb.append(T1).append(" ründab tugevusega: ").append(tugevus).append(".\n");
        //System.out.println(T1+" ründab tugevusega: "+tugevus);
        T2.saiPihta(tugevus);
        if (T1.OnOlemas(Esemetüüp.Varastamine)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Varastamine)*T1.getÕnn()){
            sb.append("Tegelasel %s õnnestus varastada endale tugevuse võrra elusid juurde.\n".formatted(T1));
            //System.out.println("Tegelasel "+T1+" õnnestus varastada endale tugevuse võrra elusid juurde");
            T1.paraneb(tugevus);
        }
        if (T1.OnOlemas(Esemetüüp.Vigastus)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Vigastus)*T1.getÕnn()){
            sb.append("Tegelasel %s õnnestus anda tegelasele %s takjas\n".formatted(T1, T2));
            //System.out.println("Tegelasel "+T1+" õnnestus anda tegelasele "+T2+" takjas");
            T2.lisaVigastus();
        }
        if (T1.OnOlemas(Esemetüüp.Veritsus)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Veritsus)*T1.getÕnn()){
            sb.append("Tegelasel %s õnnestus anda tegelasele %s veritsus\n".formatted(T1, T2));
            //System.out.println("Tegelasel "+T1+" õnnestus anda tegelasele "+T2+" veritsus");
            T2.lisaVeritsus();
        }
        boolean elus=T2.kasElus();
        if(elus){
            //System.out.println(T2+" sai pihta, alles jäi "+ T2.getElud()+" elu");
            sb.append("%s sai pihta, alles jäi %s elu\n".formatted(T2,T2.getElud()));
        }else{
            sb.append("%s sai pihta ja sai surma\n".formatted(T2));
            //System.out.println(T2+" sai pihta ja sai surma");
            throw new TegelaneSuri(T2.toString());
        }
    }

    //Tegeleb rünnaku võimalustega ja kutsub välja "ründeKalkuleerimine()"
    public void rünnak(Tegelane T1, Tegelane T2, StringBuilder sb) {// Esimene tegelane ründab teist
        //Thread.sleep(500);

        Random rand = new Random();
        if(T2.OnOlemas(Esemetüüp.Tähelepanu)){
            if(rand.nextDouble()<=T2.getTõenäosus(Esemetüüp.Tähelepanu)*T2.getÕnn()){
                sb.append("%s unustas rünnata, sest jäi Kevinit kuulama\n".formatted(T1));
                //System.out.println(T1+" unustas rünnata, sest jäi Kevinit kuulama");
                return;
            }
        }
        if(T1.OnOlemas(Esemetüüp.Rünnak2x)&&rand.nextDouble()<=T1.getTõenäosus(Esemetüüp.Rünnak2x)*T1.getÕnn()){
            sb.append("Tegelasel %s vedas ja ründab kaks korda".formatted(T1));
            //System.out.println("Tegelasel" +T1+ " vedas ja ründab kaks korda");
            ründeKalkuleerimine(T1,T2,sb);
//            if(!väärtused[0]){
//                ründeKalkuleerimine(T1,T2,sb);
//            }
            return;
        }else{
            ründeKalkuleerimine(T1,T2,sb);
            return;
        }
    }

    //Annab tegelasele 50% elusid tagasi
    public void elusta50(Tegelane T,StringBuilder sb){
        double a = T.getMaxElud()/2;
        T.paraneb(a);
        sb.append("Mängija ravib ennast, alles on %s elu".formatted(T.getElud()));
        //System.out.println("Mängija ravib ennast, alles on " + T.getElud() + " elu");
    }

    //Tegeleb tegelase käigu lõpus olevate efektitega
    public boolean püsiEfektid(Tegelane T){
        return T.veritsemine();
    }

    //teeb uue vastase suvaliste väärtustega
    public Tegelane uusVastane(int skoor,StringBuilder sb){
        Random rand = new Random();
        sb.append("Tuleb uus vastane");
        //System.out.println("Tuleb uus vastane");
        return new Tegelane("Oliveri järglane",rand.nextInt(100)+1+30*skoor,rand.nextInt(50)+1+10*skoor);
    }

    //tegeleb suvalise eseme lisamisega
    public void tasu(Tegelane T,StringBuilder sb){
        Random rand = new Random();

        Esemetüüp ese = T.suvalineEse();
        if(T.OnOlemas(Esemetüüp.Asjad)&&rand.nextDouble()<=T.getTõenäosus(Esemetüüp.Asjad)*T.getÕnn()){
            int a = (int) T.getVäärtus(Esemetüüp.Asjad);
            sb.append("Vedas, saad %s %s\n".formatted(a+1, T.asiToString(ese)));
            //System.out.println("Vedas, saad "+(a+1)+" "+T.asiToString(ese));
            T.lisaEse(ese,a+1);
        }else{
            sb.append("Saad ühe %s\n".formatted(T.asiToString(ese)));
            //System.out.println("Saad ühe "+T.asiToString(ese));
            T.lisaEse(ese,1);
        }

    }

    public void näitaOmadusi(Tegelane T1, Tegelane T2,TextArea kast){
        kast.setText(T1.näitaOmadusi()+"\n"+"*".repeat(30)+"\n"+T2.näitaOmadusi());
    }

    private Tegelane loeFailist() throws FileNotFoundException {
        try(DataInputStream dis = new DataInputStream(new FileInputStream("andmed.dat"))){
            int skoor = dis.readInt();
            double elud = dis.readDouble();
            double tugevus = dis.readDouble();
            int[] esemed = new int[10];
            for (int i = 0; i < 10; i++) {
                int ese = dis.readInt();
                esemed[i] = ese;
            }
            Peaklass.skoor = skoor;
            return new Tegelane("Mängija", elud, tugevus, esemed);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //tere
        }
    }

    public void kirjutaFaili() throws FileNotFoundException {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream("andmed.dat"))){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}