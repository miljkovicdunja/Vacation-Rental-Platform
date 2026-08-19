package com.example.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.example.demo.models.Vikendice;

public class VikendiceRepo implements VikendiceRepoInterface {

    @Override
    public int brojVikendica() {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select count(*) as broj from vikendice");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("broj");
            } else {
                return 0;
            }   
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Vikendice> sveVikendice() { 
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice");
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),    
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> sortiraneVikendiceOpadajuce() {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice order by naziv desc");
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> sortiraneVikendiceRastuce() {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice order by naziv asc");
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> sortiraneVikendiceMestoOpadajuce() {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice order by mesto desc");
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> sortiraneVikendiceMestoRastuce() {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice order by mesto asc");
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> nadjiVikendicuPoNazivu(String naziv) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice where naziv = ?");
            stmt.setString(1, naziv);
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> nadjiVikendicuPoMestu(String mesto) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice where mesto = ?");
            stmt.setString(1, mesto);
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> nadjiVikendicuPoNazivuIMestu(String naziv, String mesto) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice where naziv = ? and mesto = ?");
            stmt.setString(1, naziv);
            stmt.setString(2, mesto);
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vikendice> vikendiceVlasnika(String kor_ime) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice where vlasnik = ?");
            stmt.setString(1, kor_ime);
            ResultSet rs = stmt.executeQuery();
            List<Vikendice> vikendiceList = new ArrayList<>();
            while (rs.next()) {
                Vikendice vikendica = new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
                vikendiceList.add(vikendica);
            }
            return vikendiceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Vikendice nadjiVikendicuPoId(int idV) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from vikendice where idV = ?");
            stmt.setInt(1, idV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vikendice(
                    rs.getInt("idV"),
                    rs.getString("naziv"),
                    rs.getString("mesto"),
                    rs.getString("usluge"),
                    rs.getFloat("ocena"),
                    rs.getInt("cenovnikProlece"),
                    rs.getInt("cenovnikLeto"),
                    rs.getInt("cenovnikJesen"),
                    rs.getInt("cenovnikZima"),
                    rs.getString("telefon"),
                    rs.getString("koordinate"),
                    rs.getString("slike"),
                    rs.getString("vlasnik"),
                    rs.getString("status")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int urediVikendicu(Vikendice vikendica) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("update vikendice set naziv=?, mesto=?, usluge=?, ocena=?, cenovnikProlece=?, cenovnikLeto=?, cenovnikJesen=?, cenovnikZima=?, telefon=?, koordinate=?, slike=?,  status=? where idV=?");
            stmt.setString(1, vikendica.getNaziv());
            stmt.setString(2, vikendica.getMesto());
            stmt.setString(3, vikendica.getUsluge());
            stmt.setFloat(4, vikendica.getOcena());
            stmt.setInt(5, vikendica.getCenovnikProlece());
            stmt.setInt(6, vikendica.getCenovnikLeto());
            stmt.setInt(7, vikendica.getCenovnikJesen());
            stmt.setInt(8, vikendica.getCenovnikZima());
            stmt.setString(9, vikendica.getTelefon());
            stmt.setString(10, vikendica.getKoordinate());
            stmt.setString(11, vikendica.getSlike());
            stmt.setString(12, vikendica.getStatus());
            stmt.setInt(13, vikendica.getIdV());    
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int obrisiVikendicu(Vikendice vikendica) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from vikendice where idV=?");
            stmt.setInt(1, vikendica.getIdV());    
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int dodajVikendicu(Vikendice vikendica) {
        try{
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into vikendice (naziv, mesto, usluge, ocena, cenovnikProlece, cenovnikLeto, cenovnikJesen, cenovnikZima, telefon, koordinate, slike, vlasnik, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, vikendica.getNaziv());
            stmt.setString(2, vikendica.getMesto());
            stmt.setString(3, vikendica.getUsluge());
            stmt.setFloat(4, vikendica.getOcena());
            stmt.setInt(5, vikendica.getCenovnikProlece());
            stmt.setInt(6, vikendica.getCenovnikLeto());
            stmt.setInt(7, vikendica.getCenovnikJesen());
            stmt.setInt(8, vikendica.getCenovnikZima());
            stmt.setString(9, vikendica.getTelefon());
            stmt.setString(10, vikendica.getKoordinate());
            stmt.setString(11, vikendica.getSlike());
            stmt.setString(12, vikendica.getVlasnik());
            stmt.setString(13, vikendica.getStatus());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
 }