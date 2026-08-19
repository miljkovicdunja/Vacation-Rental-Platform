package com.example.demo.db;

import com.example.demo.models.Vikendice;
import java.util.List;


public interface VikendiceRepoInterface {
    public int brojVikendica();
    public List<Vikendice> sveVikendice();
    public List<Vikendice> sortiraneVikendiceOpadajuce();
    public List<Vikendice> sortiraneVikendiceRastuce();
    public List<Vikendice> sortiraneVikendiceMestoOpadajuce();
    public List<Vikendice> sortiraneVikendiceMestoRastuce();
    public List<Vikendice> nadjiVikendicuPoNazivu(String naziv);
    public List<Vikendice> nadjiVikendicuPoMestu(String mesto);
    public List<Vikendice> nadjiVikendicuPoNazivuIMestu(String naziv, String mesto);
    public List<Vikendice> vikendiceVlasnika(String kor_ime);
    public Vikendice nadjiVikendicuPoId(int idV);
    public int urediVikendicu(Vikendice vikendica);
    public int obrisiVikendicu(Vikendice vikendica);
    public int dodajVikendicu(Vikendice vikendica);
}
