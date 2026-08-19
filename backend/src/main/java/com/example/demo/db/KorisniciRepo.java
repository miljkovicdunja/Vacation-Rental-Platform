package com.example.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Korisnik;

public class KorisniciRepo implements KorisniciRepoInterface{
    @Override
    public Korisnik login(Korisnik korisnik) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from korisnici where kor_ime = ? and lozinka = ? and tip = ?")) {

            stmt.setString(1, korisnik.getKor_ime());
            stmt.setString(2, korisnik.getLozinka());
            stmt.setString(3, korisnik.getTip());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Korisnik(
                        rs.getString("kor_ime"),
                        rs.getString("lozinka"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("pol"),
                        rs.getString("adresa"),
                        rs.getString("mejl"),
                        rs.getString("tip"),
                        rs.getString("kontakt_telefon"),
                        rs.getString("broj_kreditne_kartice"),
                        rs.getString("profilna_slika"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int azurirajKorisnika(Korisnik korisnik) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "update korisnici set ime = ?, prezime = ?, adresa = ?, mejl = ?, kontakt_telefon = ?, broj_kreditne_kartice = ?, profilna_slika = ? where kor_ime = ?")) {

            stmt.setString(1, korisnik.getIme());
            stmt.setString(2, korisnik.getPrezime());
            stmt.setString(3, korisnik.getAdresa());
            stmt.setString(4, korisnik.getMejl());
            stmt.setString(5, korisnik.getKontakt_telefon());
            stmt.setString(6, korisnik.getBroj_kreditne_kartice());
            stmt.setString(7, korisnik.getProfilna_slika());
            stmt.setString(8, korisnik.getKor_ime());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int promeniLozinku(String kor_ime, String novaLozinka) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "update korisnici set lozinka = ? where kor_ime = ?")) {

            stmt.setString(1, novaLozinka);
            stmt.setString(2, kor_ime);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();    
        }

        return 0;
    }

    @Override
    public String dohvatiLozinku(String kor_ime) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select lozinka from korisnici where kor_ime = ?")) {

            stmt.setString(1, kor_ime);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("lozinka");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int dodajKorisnika(Korisnik korisnik) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into korisnici (kor_ime, lozinka, ime, prezime, pol, adresa, mejl, tip, kontakt_telefon, broj_kreditne_kartice, profilna_slika, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, korisnik.getKor_ime());
            stmt.setString(2, korisnik.getLozinka());
            stmt.setString(3, korisnik.getIme());
            stmt.setString(4, korisnik.getPrezime());
            stmt.setString(5, korisnik.getPol());
            stmt.setString(6, korisnik.getAdresa());
            stmt.setString(7, korisnik.getMejl());
            stmt.setString(8, korisnik.getTip());
            stmt.setString(9, korisnik.getKontakt_telefon());
            stmt.setString(10, korisnik.getBroj_kreditne_kartice());
            stmt.setString(11, korisnik.getProfilna_slika());
            stmt.setString(12, "na cekanju");

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
    }

        return 0;
    }

    @Override
    public int brojTurista() {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select count(*) as broj from korisnici where tip = 'turista'")) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("broj");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int brojVlasnika() {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select count(*) as broj from korisnici where tip = 'vlasnik'")) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("broj");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Korisnik> sviKorisnici() {
        try{
            List<Korisnik> korisnici = new ArrayList<>();
            String query = "SELECT * FROM korisnici where tip!='administrator'";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Korisnik k = new Korisnik(
                    rs.getString("kor_ime"),
                    rs.getString("lozinka"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("pol"),
                    rs.getString("adresa"),
                    rs.getString("mejl"),
                    rs.getString("tip"),
                    rs.getString("kontakt_telefon"),
                    rs.getString("broj_kreditne_kartice"),
                    rs.getString("profilna_slika"),
                    rs.getString("status")
                );
                korisnici.add(k);
            }
            return korisnici;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int obrisiKorisnika(Korisnik korisnik) {
        try{
            Connection conn = DB.source().getConnection();
            
            // Prvo obrisi sve vikendice ovog korisnika
            String deleteVikendiceQuery = "DELETE FROM vikendice WHERE vlasnik = ?";
            PreparedStatement stmtVikendice = conn.prepareStatement(deleteVikendiceQuery);
            stmtVikendice.setString(1, korisnik.getKor_ime());
            stmtVikendice.executeUpdate();
            
            // Zatim obrisi korisnika
            String deleteKorisnikQuery = "DELETE FROM korisnici WHERE kor_ime = ?";
            PreparedStatement stmtKorisnik = conn.prepareStatement(deleteKorisnikQuery);
            stmtKorisnik.setString(1, korisnik.getKor_ime());
            return stmtKorisnik.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deaktivirajKorisnika(Korisnik korisnik) {
        try{
            String query = "UPDATE korisnici SET status='deakt' WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, korisnik.getKor_ime());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Korisnik> korisniciNaCekanju() {
        try{
            List<Korisnik> korisnici = new ArrayList<>();
            String query = "SELECT * FROM korisnici WHERE status='na cekanju'";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Korisnik k = new Korisnik(
                    rs.getString("kor_ime"),
                    rs.getString("lozinka"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("pol"),
                    rs.getString("adresa"),
                    rs.getString("mejl"),
                    rs.getString("tip"),
                    rs.getString("kontakt_telefon"),
                    rs.getString("broj_kreditne_kartice"),
                    rs.getString("profilna_slika"),
                    rs.getString("status")
                );
                korisnici.add(k);
            }
            return korisnici;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int aktivirajKorisnika(Korisnik korisnik) {
        try{
            String query = "UPDATE korisnici SET status='odobreno' WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, korisnik.getKor_ime());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int odbijKorisnika(Korisnik korisnik) {
        try{
            String query = "UPDATE korisnici SET status='odbijeno' WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, korisnik.getKor_ime());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int azurirajProfilnuSliku(String kor_ime, String profilna_slika) {
        try {
            String query = "UPDATE korisnici SET profilna_slika=? WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, profilna_slika);
            stmt.setString(2, kor_ime);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String dohvatiProfilnuSliku(String kor_ime) {
        try {
            String query = "SELECT profilna_slika FROM korisnici WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kor_ime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("profilna_slika");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Korisnik dohvatiKorisnika(String kor_ime) {
        try {
            String query = "SELECT * FROM korisnici WHERE kor_ime=?";
            Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kor_ime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Korisnik(
                    rs.getString("kor_ime"),
                    rs.getString("lozinka"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("pol"),
                    rs.getString("adresa"),
                    rs.getString("mejl"),
                    rs.getString("tip"),
                    rs.getString("kontakt_telefon"),
                    rs.getString("broj_kreditne_kartice"),
                    rs.getString("profilna_slika"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
