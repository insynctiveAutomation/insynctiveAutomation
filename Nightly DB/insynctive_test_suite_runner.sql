-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: insynctive
-- ------------------------------------------------------
-- Server version	5.5.27

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
-- Table structure for table `test_suite_runner`
--

DROP TABLE IF EXISTS `test_suite_runner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_suite_runner` (
  `test_suite_runner_id` int(11) NOT NULL AUTO_INCREMENT,
  `browser` varchar(255) DEFAULT NULL,
  `environment` varchar(255) DEFAULT NULL,
  `test_suite_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`test_suite_runner_id`),
  KEY `FKD1CE0E4D5DF5FC8` (`test_suite_id`),
  CONSTRAINT `FKD1CE0E4D5DF5FC8` FOREIGN KEY (`test_suite_id`) REFERENCES `test_suite` (`test_suite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_suite_runner`
--

LOCK TABLES `test_suite_runner` WRITE;
/*!40000 ALTER TABLE `test_suite_runner` DISABLE KEYS */;
INSERT INTO `test_suite_runner` VALUES (4,'FIREFOX','automationqa',4),(5,'CHROME','automationqa',5),(6,'IPAD','automationqa',6),(7,'FIREFOX','automationqa',7),(8,'CHROME','automationqa',8),(9,'IPAD','automationqa',9);
/*!40000 ALTER TABLE `test_suite_runner` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-09 20:23:48
