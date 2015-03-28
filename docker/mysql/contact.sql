-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 24 feb 2015 om 19:50
-- Serverversie: 5.6.12-log
-- PHP-versie: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `contact`
--
CREATE DATABASE IF NOT EXISTS `contact` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `contact`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `contacts`
--

CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `UUID` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `contacts`
--

INSERT INTO `contacts` (`id`, `userId`, `name`, `email`, `UUID`) VALUES
(1, 1, 'john', 'john@gmail.com', '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),
(4, 1, 'cindy', 'cindy@gmail.com', '�	\n-��OϺ7�!n�M'),
(5, 1, 'peter', 'peter@gmail.com', '�[�*x\\J�B\rX$K�');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `sequences`
--

CREATE TABLE IF NOT EXISTS `sequences` (
  `SEQ_NAME` varchar(32) NOT NULL,
  `SEQ_NUMBER` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `sequences`
--

INSERT INTO `sequences` (`SEQ_NAME`, `SEQ_NUMBER`) VALUES
('CONTACT_ID', 6);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL,
  `username` varchar(64) NOT NULL,
  `passwd` varchar(255) NOT NULL,
  `uuid` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden uitgevoerd voor tabel `users`
--

INSERT INTO `users` (`id`, `username`, `passwd`, `uuid`) VALUES
(1, 'robbert', 'robbert', '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),
(2, 'john', 'pw', '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),
(3, 'q', 'q', '\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
