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
-- Table structure for table `test_plan_run_x_test_suite_run`
--

DROP TABLE IF EXISTS `test_plan_run_x_test_suite_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_plan_run_x_test_suite_run` (
  `test_plan_run_id` int(11) NOT NULL,
  `test_suite_run_id` int(11) NOT NULL,
  UNIQUE KEY `test_suite_run_id` (`test_suite_run_id`),
  KEY `FK65F2511BA8316F8` (`test_plan_run_id`),
  KEY `FK65F2511B541FA1D8` (`test_suite_run_id`),
  CONSTRAINT `FK65F2511B541FA1D8` FOREIGN KEY (`test_suite_run_id`) REFERENCES `test_suite_run` (`test_suite_run_id`),
  CONSTRAINT `FK65F2511BA8316F8` FOREIGN KEY (`test_plan_run_id`) REFERENCES `test_plan_run` (`test_plan_run_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_plan_run_x_test_suite_run`
--

LOCK TABLES `test_plan_run_x_test_suite_run` WRITE;
/*!40000 ALTER TABLE `test_plan_run_x_test_suite_run` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_plan_run_x_test_suite_run` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-09 20:23:49
