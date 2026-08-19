-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 16, 2025 at 11:12 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zdravlje2025`
--
CREATE DATABASE IF NOT EXISTS `vikendice` DEFAULT CHARACTER SET utf16 COLLATE utf16_bin;
USE `vikendice`;

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--
DROP TABLE IF EXISTS `korisnici`;
CREATE TABLE IF NOT EXISTS `korisnici` (
  `kor_ime` varchar(45) COLLATE utf16_bin NOT NULL,
  `lozinka` varchar(10000000) COLLATE utf16_bin NOT NULL,
  `ime` varchar(45) COLLATE utf16_bin NOT NULL,
  `prezime` varchar(45) COLLATE utf16_bin NOT NULL,
  `pol` char not null,
  `adresa` varchar(100) COLLATE utf16_bin NOT NULL,
  `mejl` varchar(45) COLLATE utf16_bin NOT NULL,
  `tip` varchar(45) COLLATE utf16_bin NOT NULL,
  `kontakt_telefon` varchar(45) COLLATE utf16_bin NOT NULL,
  `broj_kreditne_kartice` varchar(20) COLLATE utf16_bin NOT NULL,
  `profilna_slika`varchar(1000) COLLATE utf16_bin,
  `status` varchar(10) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`kor_ime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`kor_ime`, `lozinka`, `ime`, `prezime`,`pol`,`adresa`, `mejl`, `tip`, `kontakt_telefon`,`broj_kreditne_kartice`,`profilna_slika`,`status`) VALUES
('jeca', '1f78d05a28ccf6633692343d82663ca17b26a39ab39cbaa6ef217c724440cb41', 'Jelena', 'Jelic', 'Z','Vase Pelagica 10, Nis', 'jeca@gmail.com', 'turista', '+381 60111111',300685475120600,'75abd6c3-b0f5-413c-bd56-87fb4a1e5b55_image1.jpeg','odobreno'),
('jova', '799f56b45fb06a14d7e4f79e4c9860581fd6c5b7a55852006fa5ad3f4f3a9f2f', 'Jovan', 'Jovanovic', 'M','Cara Dusana 55, Kragujevac', 'jova@gmail.com', 'vlasnik', '+381 61222222',4539542000684752,'54a78ecc28d5_image3.jpeg','odobreno'),
('maja', 'eacbd73e79352a141db4dcf9fee24eb7bc43eef889a20ae918d63cecd2092570', 'Maja', 'Majic','Z','Bulevar Oslobodjenja 10, Beograd', 'maja@gmail.com', 'administrator','+381 63111222',4929305412022254,'','odobreno'),
('pera', '127431786f11361fdbab136e5f846d75782f1c54f416528552e14ed5964a9256', 'Petar', 'Petrovic', 'M','Radoja Dakica 34, Uzice','pera@gmail.com', 'turista','+381 64111222',5190048563128486,'d56a86f6e5b8_image4.jpeg','odobreno'),
('jana', '79e4d43a6de6852ecaedb6082c6f8c7543b77106943adad0de3688eade4bdcd0', 'Jana', 'Janic', 'Z','Vuka Karadzica 9, Valjevo','jana@gmail.com', 'turista','+381 65222333',5190048515688486,'','na cekanju'),
('sasa', '7f82ec94faac55091c2c675acc4611eb838c9913bcf0aa8c2d700da99cd42d31', 'Sasa', 'Sasic','Cara Lazara 22, Krusevac', 'M','sasa@gmail.com', 'vlasnik','+381 66844558',45396586851412022,'','na cekanju'),
('mila', '5ea55e80b50ab47562d012b56c63e3fda1d11fb454a9f4daec0281247bbff23e', 'Mila', 'Milic','Knjeginje Milice 39, Sabac', 'Z','mila@gmail.com', 'vlasnik','+381 67656001',45396586851419022,'','odobreno'),
('joka', 'cdd9afd77bedf3bc6060177b505f11ada609d3e2e0e49da6b53a99098593134d', 'Jovana', 'Jovanovic', 'Z','Dunavska 5, Sombom','joka@gmail.com', 'vlasnik','+381 69665220',300574201539,'f0a208fcfff7_image8.jpg','odobreno'),
('iva', 'cc6df29d947b908e99fc2a5a14236ed830477126f2356ec69be9f03866b815e9', 'Iva', 'Ivanic','Z','Nemanjina 12, Beograd', 'iva@gmail.com', 'administrator','+381 60225466',4485002145289655,'','odobreno'),
('kika', '6687c7dcf08dd8dcd451fb1234d46dafb2fc88d8116dfbb3c57068e8a6cb7186', 'Kika', 'Kokic','Z','Jovana Cvijiva 18, Pirot', 'kika@gmail.com', 'administrator','+381 632541025',5147785203552,'','odobreno'),
('sonja', '71c109cb95c6b7085388e92f670d7566cc4f81d58acd190ea12fae2a72e64172', 'Sonja', 'Majic','Z','Trg Republike 12, Beograd', 'sonja@gmail.com', 'administrator','+381 68541208',49293054152412254,'','odobreno'),
('luka', '68541b7da88970696c37c0bc52fa2b9eba389a5b69990a8d3d7e005d443180dc', 'Luka', 'Lukic','M','Nikole Tesle 30, Pancevo', 'luka@gmail.com', 'turista','+381 66332015',4929305966822254,'image12.jpeg','na cekanju');

-- --------------------------------------------------------
DROP TABLE IF EXISTS `vikendice`;
CREATE TABLE IF NOT EXISTS `vikendice` (
  `idV` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(200) COLLATE utf16_bin NOT NULL,
  `mesto` varchar(45) COLLATE utf16_bin NOT NULL,
  `usluge` varchar(1000) COLLATE utf16_bin NOT NULL,
  `ocena` float not null,
  `cenovnikProlece` int COLLATE utf16_bin not null,
  `cenovnikLeto` int COLLATE utf16_bin not null,
  `cenovnikJesen` int COLLATE utf16_bin not null,
  `cenovnikZima` int COLLATE utf16_bin not null,
  `telefon` varchar(45) COLLATE utf16_bin NOT NULL,
  `koordinate` varchar(45) COLLATE utf16_bin NOT NULL,
  `slike` varchar(3000) COLLATE utf16_bin NOT NULL,
  `vlasnik` varchar(20) COLLATE utf16_bin NOT NULL,
  `status` varchar(10) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`idV`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;
-- --------------------------------------------------------
INSERT INTO `vikendice` (`idV`, `naziv`, `mesto`, `usluge`,`ocena`,`cenovnikProlece`, `cenovnikLeto`, `cenovnikJesen`, `cenovnikZima`,`telefon`,`koordinate`,`slike`,`vlasnik`,`status`) VALUES
('1', 'Kreativni kutak', 'Srebrno jezero', 'igraonica za decu, masaze, spa',4.2, 1000, 1500, 1500, 1000,'+381 65354120','(28.26121,4.42706)','','jova','aktivno'),
('2', 'Zeleni kutak', 'Tara','ski staza, bazen, spa', 3.8, 1000, 1800, 2000, 1000,'+381 65354120','(-11.70984, -63.00273)','','jova','aktivno'),
('3', 'Romanticni beg', 'Zlatibor', 'teretana, spa, masaze za dvoje',4.4,1500, 2500, 2000,2500,'+381 6348552','(-44.10859, 118.46437)','','joka','aktivno'),
('4', 'Mali raj', 'Soko banja', 'spa, bazen za toplom vodom, dorucak',4.6, 1000,1200, 1500,1500,'+381 60052485','(8.30548, 36.83353)','','joka','aktivno'),
('5', 'Zlatna kuca', 'Palicko jezero', 'dorucak, vecera',4.2, 1000,1500, 1500,1200,'+381 68552063','(63.62585, 107.48841)','','joka','mila'),
('6', 'Mali odmor', 'Palicko jezero', 'wifi, teretana',4.5, 1000,1500, 1500,1200,'+381 68552063','(63.62585, 107.48445)','','joka','mila'),
('7', 'Romanticni beg 2', 'Goc', 'ski pas, spa, masaze za dvoje',4.8,1500, 2500, 2000,2500,'+381 6348552','(-44.10859, 11.8437)','','mila','aktivno'),
('8', 'Kreativni sobicak', 'Goc', 'spa, bazen za toplom vodom, dorucak',3.2, 1200,1200, 1500,1500,'+381 60052485','(8.30548, 36.83353)','','jova','aktivno');
-- --------------------------------------------------------
-- --------------------------------------------------------

DROP TABLE IF EXISTS `rezervacije`;
CREATE TABLE IF NOT EXISTS `rezervacije` (
  `idR` int NOT NULL AUTO_INCREMENT,
`turista` varchar(45) COLLATE utf16_bin NOT NULL,
	`idV` int not null,
  `datumPocetak` date COLLATE utf16_bin NOT NULL,
  `datumKraj` date COLLATE utf16_bin NOT NULL,
  `brOdraslih` int NOT NULL,
  `brDece` int,
  `kartica` varchar(20) COLLATE utf16_bin not null,
  `zahtevi` varchar(500) COLLATE utf16_bin,
  `status` varchar(20) COLLATE utf16_bin not null,
    `komentar` varchar(200) COLLATE utf16_bin,
  PRIMARY KEY (`idR`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;
-- --------------------------------------------------------
INSERT INTO `rezervacije` (`idR`,`turista`,`idV`, `datumPocetak`, `datumKraj`, `brOdraslih`,`brDece`, `kartica`, `zahtevi`, `status`,`komentar`) VALUES
(1,'jeca',3,'2025-12-25','2025-12-29',2,0,'300685475120600','','potvrdjena',''),
(2,'pera',5,'2025-12-31','2026-02-02',2,2,'5190048563128486','dolazak ujutru rano','na cekanju',''),
(3,'jeca',6,'2025-12-26','2025-12-26',2,1,'300685475120600','dorucak','na cekanju',''),
(4,'pera',8,'2025-11-11','2025-11-19',1,3,'5190048563128486','parking mesto','potvrdjena',''),
(5,'jeca',1,'2025-12-06','2025-12-10',2,4,'300685475120600','jak wifi','na cekanju',''),
(6,'pera',5,'2025-10-20','2025-10-29',1,0,'5190048563128486','bazen, spa','obradjena',''),
(7,'jeca',3,'2025-06-11','2025-06-15',3,1,'300685475120600','','obradjena',''),
(8,'pera',4,'2025-11-01','2025-11-09',2,2,'5190048563128486','fen, bazen','potvrdjena',''),
(9,'jeca',4,'2025-12-15','2025-12-19',2,3,'300685475120600','sauna','potvrdjena',''),
(10,'pera',8,'2025-08-20','2025-08-25',1,2,'5190048563128486','teretana, kafic u blizini','obradjena',''),
(11,'jeca',1,'2025-11-03','2025-11-05',5,0,'300685475120600','dorucak','potvrdjena',''),
(12,'pera',3,'2024-12-24','2024-12-29',2,0,'5190048563128486','','na cekanju','');