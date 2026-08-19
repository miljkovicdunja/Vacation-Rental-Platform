package com.example.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Korisnik;
import com.example.demo.models.Rezervacije;

public class RezervacijaRepo implements RezervacijeRepoInterface {

    @Override
    public int brojRezervacija24h() {
        try{
            String query = "SELECT COUNT(*) AS broj FROM rezervacije WHERE datumPocetak > CURDATE() - INTERVAL 1 DAY AND datumPocetak < CURDATE() and status!='otkazana' and status!='na cekanju'";
            java.sql.Connection conn = DB.source().getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("broj");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int brojRezervacija7dana() {
        try{
            String query = "SELECT COUNT(*) AS broj FROM rezervacije WHERE datumPocetak >= CURDATE() - INTERVAL 7 DAY AND datumPocetak < CURDATE() and status!='otkazana' and status!='na cekanju'";
            java.sql.Connection conn = DB.source().getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("broj");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int brojRezervacija30dana() {
        try{
            String query = "SELECT COUNT(*) AS broj FROM rezervacije WHERE datumPocetak >= CURDATE() - INTERVAL 30 DAY AND datumPocetak < CURDATE() and status!='otkazana' and status!='na cekanju'";
            java.sql.Connection conn = DB.source().getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("broj");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public List<Rezervacije> trenutneRezervacijeKorisnika(String korisnik) {
        try{
            List<Rezervacije> rezervacije = new ArrayList<>();
            String query = "SELECT * FROM rezervacije WHERE turista = ? AND status!='otkazana' AND status!='na cekanju' AND datumKraj >= CURDATE()";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, korisnik);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rezervacije r = new Rezervacije(
                    rs.getInt("idR"),
                    rs.getString("turista"),
                    rs.getInt("idV"),
                    rs.getDate("datumPocetak"),
                    rs.getDate("datumKraj"),
                    rs.getInt("brOdraslih"),
                    rs.getInt("brDece"),
                    rs.getString("kartica"),
                    rs.getString("zahtevi"),
                    rs.getString("status"),
                    rs.getString("komentar")
                );
                rezervacije.add(r);
            }
            return rezervacije;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new java.util.ArrayList<>();
    }
    @Override
    public int dodajRezervaciju(Rezervacije rezervacija) {
        try{
            String query = "INSERT INTO rezervacije (turista, idV, datumPocetak, datumKraj, brOdraslih, brDece, kartica, zahtevi, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, rezervacija.getTurista());
            stmt.setInt(2, rezervacija.getIdV());
            stmt.setDate(3, new java.sql.Date(rezervacija.getDatumPocetak().getTime()));
            stmt.setDate(4, new java.sql.Date(rezervacija.getDatumKraj().getTime()));
            stmt.setInt(5, rezervacija.getBrOdraslih());
            stmt.setInt(6, rezervacija.getBrDece());
            stmt.setString(7, rezervacija.getKartica());
            stmt.setString(8, rezervacija.getZahtevi());
            stmt.setString(9, "na cekanju");
            return stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return 0;
    }
    @Override
    public List<Rezervacije> neobradjeneRezervacije(Korisnik korisnik) {
        try{
            List<Rezervacije> rezervacije = new ArrayList<>();
            String query = "SELECT r.* FROM rezervacije r " +
                          "JOIN vikendice v ON r.idV = v.idV " +
                          "WHERE r.status='na cekanju' AND v.vlasnik=? " +
                          "ORDER BY r.datumPocetak ASC";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, korisnik.getKor_ime());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rezervacije r = new Rezervacije(
                    rs.getInt("idR"),
                    rs.getString("turista"),
                    rs.getInt("idV"),
                    rs.getDate("datumPocetak"),
                    rs.getDate("datumKraj"),
                    rs.getInt("brOdraslih"),
                    rs.getInt("brDece"),
                    rs.getString("kartica"),
                    rs.getString("zahtevi"),
                    rs.getString("status"),
                    rs.getString("komentar")
                );
                rezervacije.add(r);
            }
            return rezervacije;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public int prihvatiRezervaciju(int rezervacija) {
        try{
            String query = "UPDATE rezervacije SET status='prihvacena' WHERE idR=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, rezervacija);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int odbijRezervaciju(int rezervacija, String komentar) {
        try{
            String query = "UPDATE rezervacije SET status='odbijena', komentar=? WHERE idR=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, komentar);
            stmt.setInt(2, rezervacija);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int otkaziRezervaciju(Rezervacije rezervacija) {
        try{
            String query = "UPDATE rezervacije SET status='otkazana' WHERE idR=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, rezervacija.getIdR());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}