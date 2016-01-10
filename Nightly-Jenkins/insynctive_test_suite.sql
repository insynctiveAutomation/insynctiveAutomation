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
-- Table structure for table `test_suite`
--

DROP TABLE IF EXISTS `test_suite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_suite` (
  `test_suite_id` int(11) NOT NULL AUTO_INCREMENT,
  `test_suite_name` varchar(255) DEFAULT NULL,
  `depends_test_suite` int(11) DEFAULT NULL,
  PRIMARY KEY (`test_suite_id`),
  KEY `FK7B68C90BB7D4D3BC` (`depends_test_suite`),
  CONSTRAINT `FK7B68C90BB7D4D3BC` FOREIGN KEY (`depends_test_suite`) REFERENCES `test_suite` (`test_suite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_suite`
--

LOCK TABLES `test_suite` WRITE;
/*!40000 ALTER TABLE `test_suite` DISABLE KEYS */;
INSERT INTO `test_suite` VALUES (4,'Loading Test',NULL),(5,'Loading Test',NULL),(6,'Loading Test',NULL),(7,'Person File',NULL),(8,'Person File',NULL),(9,'Person File',NULL),(10,'Open Documents - Employee Interface',NULL),(11,'Open Documents - Employee Interface',NULL),(12,'Open Documents - Person File',NULL),(13,'Open Documents - Person File',NULL),(14,'2FA - Email - Agent',NULL),(15,'2FA - Email - Agent',NULL),(16,'2FA - Email - Employee',NULL),(17,'2FA - Email - Agent',NULL),(18,'2FA - Email - Employee',NULL),(19,'2FA - Phone - Agent',NULL),(20,'2FA - Email - Agent',NULL),(21,'2FA - Email - Employee',20),(22,'2FA - Phone - Agent',21),(23,'2FA - Phone - Employee',22),(32,'First Login',NULL),(36,'Person File',32),(37,'First Login',NULL),(38,'Person File',37),(39,'First Login',NULL),(40,'Person File',39);
/*!40000 ALTER TABLE `test_suite` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-10 20:16:42
