-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 29, 2017 at 12:33 PM
-- Server version: 5.7.18-0ubuntu0.16.04.1
-- PHP Version: 7.0.15-0ubuntu0.16.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gg_database`
--
CREATE DATABASE IF NOT EXISTS `gg_database` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `gg_database`;

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `LogFeedback`$$
CREATE DEFINER=`emadashour_3`@`%` PROCEDURE `LogFeedback` (IN `studentId` VARCHAR(9), IN `pace` INT, IN `volume` INT, IN `bodyLanguage` INT, IN `clarity` INT, IN `interest` INT)  NO SQL
INSERT into LOG (STUDENT_ID, PACE, VOLUME, BODY_LANGUAGE, CLARITY, INTEREST) VALUES (studentId, pace, volume, bodyLanguage, clarity, interest)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `FEEDBACK`
--

DROP TABLE IF EXISTS `FEEDBACK`;
CREATE TABLE IF NOT EXISTS `FEEDBACK` (
  `STUDENT_ID` varchar(9) NOT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PACE` int(11) DEFAULT NULL,
  `VOLUME` int(11) DEFAULT NULL,
  `BODY_LANGUAGE` int(11) DEFAULT NULL,
  `CLARITY` int(11) DEFAULT NULL,
  `INTEREST` int(11) DEFAULT NULL,
  PRIMARY KEY (`STUDENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `LOG`
--

DROP TABLE IF EXISTS `LOG`;
CREATE TABLE IF NOT EXISTS `LOG` (
  `STUDENT_ID` varchar(9) NOT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PACE` int(11) NOT NULL,
  `VOLUME` int(11) NOT NULL,
  `BODY_LANGUAGE` int(11) NOT NULL,
  `CLARITY` int(11) NOT NULL,
  `INTEREST` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `STUDENT`
--

DROP TABLE IF EXISTS `STUDENT`;
CREATE TABLE IF NOT EXISTS `STUDENT` (
  `STUDENT_ID` varchar(9) NOT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`STUDENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
