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
-- Table structure for table `test_plan_x_test_suite_runner`
--

DROP TABLE IF EXISTS `test_plan_x_test_suite_runner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_plan_x_test_suite_runner` (
  `test_plan_id` int(11) NOT NULL,
  `test_suite_runner_id` int(11) NOT NULL,
  UNIQUE KEY `test_suite_runner_id` (`test_suite_runner_id`),
  KEY `FKAAA8BDD4AF6B79DF` (`test_suite_runner_id`),
  KEY `FKAAA8BDD4E4C5EA4C` (`test_plan_id`),
  CONSTRAINT `FKAAA8BDD4AF6B79DF` FOREIGN KEY (`test_suite_runner_id`) REFERENCES `test_suite_runner` (`test_suite_runner_id`),
  CONSTRAINT `FKAAA8BDD4E4C5EA4C` FOREIGN KEY (`test_plan_id`) REFERENCES `test_plan` (`test_plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_plan_x_test_suite_runner`
--

LOCK TABLES `test_plan_x_test_suite_runner` WRITE;
/*!40000 ALTER TABLE `test_plan_x_test_suite_runner` DISABLE KEYS */;
INSERT INTO `test_plan_x_test_suite_runner` VALUES (2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,20),(2,21),(2,22),(2,23);
/*!40000 ALTER TABLE `test_plan_x_test_suite_runner` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-10 17:27:38
