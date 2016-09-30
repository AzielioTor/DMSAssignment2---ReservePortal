CREATE DATABASE  IF NOT EXISTS `dmsass2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dmsass2`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: azielserver    Database: dmsass2
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `perm_emp`
--

DROP TABLE IF EXISTS `perm_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perm_emp` (
  `p_emp_id` int(11) NOT NULL,
  `p_fName` varchar(45) NOT NULL,
  `p_lName` varchar(45) DEFAULT NULL,
  `p_phoneNumber` int(11) NOT NULL,
  `p_email` varchar(45) DEFAULT NULL,
  `p_workingAddress` varchar(255) DEFAULT NULL,
  `p_region` varchar(45) DEFAULT NULL,
  `p_licence_expiry` date NOT NULL,
  `manager` tinyint(1) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  PRIMARY KEY (`p_emp_id`),
  UNIQUE KEY `p_emp_id_UNIQUE` (`p_emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perm_emp`
--

LOCK TABLES `perm_emp` WRITE;
/*!40000 ALTER TABLE `perm_emp` DISABLE KEYS */;
INSERT INTO `perm_emp` VALUES (10001,'Aziel','FTShaw',123456,'aziel@school.com','123 Fairytaillane','WEST','2017-09-14',1,'08:00:00','16:00:00'),(10002,'Sez','FTProuting',999999,'Sarah@university.com','23 New Zealand drive','EAST','2019-04-12',0,'07:00:00','15:00:00'),(10003,'Anna','FRBrown',2343548,'anna.brown@gmail.com','123 Great South Road','CENTRAL','2016-12-12',0,'08:30:00','18:00:00'),(10004,'Leng','FTAm',236472,'leng@gmail.com','45 Sid Pl','WEST','2015-06-15',0,'09:00:00','17:00:00'),(10005,'Jason','Singh',865473,'jason@gmail.com','98 howick drive','EAST','2018-05-02',0,'08:00:00','18:00:00'),(10006,'Johnny','Bravo',8551111,'johnny_rulz@aol.com','21 Jump St','SOUTH','2016-12-01',0,'11:00:00','12:00:00'),(14650,'Kylie','FTSmith',4164455,'kylie.smith@gmail.com','223 Queen St Auckland','CENTRAL','2017-12-15',0,'08:30:00','16:30:00'),(18760,'Anna','FTBrown',4161122,'anna.brown@gmail.com','11 Long Street, Massey','West','2016-06-21',0,'08:00:00','15:00:00'),(50001,'Aziel','Shaw',123456,'aziel@school.com','123 Fairytail lane','WEST','2017-09-14',1,'08:00:00','16:00:00'),(54650,'Kylie','FTSmith',4164455,'kylie.smith@gmail.com','223 Queen St, Auckland','Central','2017-01-15',1,'08:30:00','16:30:00');
/*!40000 ALTER TABLE `perm_emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requests` (
  `requestID` int(11) NOT NULL AUTO_INCREMENT,
  `r_emp_id` int(11) NOT NULL,
  `p_emp_id` int(11) NOT NULL,
  `pending` tinyint(1) NOT NULL,
  `accepted` tinyint(1) NOT NULL,
  `dayOfWeek` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`requestID`),
  UNIQUE KEY `requestID_UNIQUE` (`requestID`),
  KEY `r_emp_id_idx` (`r_emp_id`),
  KEY `p_emp_id_idx` (`p_emp_id`),
  CONSTRAINT `p_emp_id` FOREIGN KEY (`p_emp_id`) REFERENCES `perm_emp` (`p_emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `r_emp_id` FOREIGN KEY (`r_emp_id`) REFERENCES `reserve_emp` (`r_emp_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (148,98870,18760,0,1,'WEDNESDAY'),(150,90002,10005,0,1,'THURSDAY'),(155,90001,10006,1,0,'THURSDAY'),(157,98870,10004,0,0,'THURSDAY'),(159,96651,10003,0,0,'FRIDAY');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserve_emp`
--

DROP TABLE IF EXISTS `reserve_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reserve_emp` (
  `r_emp_id` int(11) NOT NULL,
  `r_fName` varchar(45) NOT NULL,
  `r_lName` varchar(45) DEFAULT NULL,
  `r_phoneNumber` int(11) DEFAULT NULL,
  `r_email` varchar(45) DEFAULT NULL,
  `r_address` varchar(255) DEFAULT NULL,
  `available` tinyint(1) NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `r_region` varchar(45) DEFAULT NULL,
  `r_licence_expiry` date DEFAULT NULL,
  `monday` tinyint(1) NOT NULL,
  `tuesday` tinyint(1) NOT NULL,
  `wednesday` tinyint(1) NOT NULL,
  `friday` tinyint(1) NOT NULL,
  `thursday` tinyint(1) NOT NULL,
  `saturday` tinyint(1) NOT NULL,
  `sunday` tinyint(1) NOT NULL,
  `NORTH` tinyint(1) NOT NULL,
  `SOUTH` tinyint(1) NOT NULL,
  `EAST` tinyint(1) NOT NULL,
  `WEST` tinyint(1) NOT NULL,
  `CENTRAL` tinyint(1) NOT NULL,
  PRIMARY KEY (`r_emp_id`),
  UNIQUE KEY `r_emp_id_UNIQUE` (`r_emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserve_emp`
--

LOCK TABLES `reserve_emp` WRITE;
/*!40000 ALTER TABLE `reserve_emp` DISABLE KEYS */;
INSERT INTO `reserve_emp` VALUES (90001,'Ynnes','ResJones',4375555,'jonesy@gmail.com','1 Mediocre Ln, Tamaki',1,'01:00:00','01:00:00','EAST','2016-06-13',1,1,1,1,1,1,1,1,1,1,1,1),(90002,'Tim','ResWorkaholic',4375555,'tim.LovezWork@gmail.com','1 Business St, Auckland',0,'01:00:00','01:00:00','CENTRAL','2017-06-13',1,1,1,1,1,1,1,1,1,1,1,1),(90003,'Nigel','ResNoMates',7770000,'NigelisSad@gmail.com','1 Homeless Cres, Hamilton',0,'01:00:00','01:00:00','CENTRAL','2017-06-13',0,0,0,0,0,0,0,0,0,0,0,0),(96651,'Will','ResSmith',7349999,'willSmith@gmail.com','21 Jump St, Mangere',1,'01:00:00','01:00:00','SOUTH','2016-10-13',1,1,1,1,1,1,1,0,1,1,0,1),(98870,'Zach','ResBloggs',8325544,'zach.bloggs@gmail.com','1 Short St, New Lynn',0,'01:00:00','01:00:00','West','2016-11-05',1,1,1,1,1,0,0,1,0,0,1,1);
/*!40000 ALTER TABLE `reserve_emp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-29 17:04:48
