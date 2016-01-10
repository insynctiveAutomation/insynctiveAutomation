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
-- Table structure for table `test_suite_x_test`
--

DROP TABLE IF EXISTS `test_suite_x_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_suite_x_test` (
  `test_suite_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  UNIQUE KEY `test_id` (`test_id`),
  KEY `FK162689ADD5DF5FC8` (`test_suite_id`),
  KEY `FK162689AD45FDCB27` (`test_id`),
  CONSTRAINT `FK162689AD45FDCB27` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`),
  CONSTRAINT `FK162689ADD5DF5FC8` FOREIGN KEY (`test_suite_id`) REFERENCES `test_suite` (`test_suite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_suite_x_test`
--

LOCK TABLES `test_suite_x_test` WRITE;
/*!40000 ALTER TABLE `test_suite_x_test` DISABLE KEYS */;
INSERT INTO `test_suite_x_test` VALUES (4,30),(4,31),(4,32),(4,33),(4,34),(4,35),(4,36),(4,37),(4,38),(5,39),(5,40),(5,41),(5,42),(5,43),(5,44),(5,45),(5,46),(5,47),(5,48),(6,49),(6,50),(6,51),(6,52),(6,53),(6,54),(6,55),(6,56),(6,57),(6,58),(7,59),(7,60),(7,61),(7,62),(7,63),(7,64),(7,65),(7,66),(7,67),(7,68),(7,69),(7,70),(7,71),(7,72),(7,73),(7,74),(7,75),(7,76),(7,77),(8,78),(8,79),(8,80),(8,81),(8,82),(8,83),(8,84),(8,85),(8,86),(8,87),(8,88),(8,89),(8,90),(8,91),(8,92),(8,93),(8,94),(8,95),(8,96),(9,97),(9,98),(9,99),(9,100),(9,101),(9,102),(9,103),(9,104),(9,105),(9,106),(9,107),(9,108),(9,109),(9,110),(9,111),(9,112),(9,113),(9,114),(9,115),(10,116),(10,117),(11,118),(11,119),(12,120),(12,121),(12,122),(13,123),(13,124),(13,125),(14,126),(14,127),(14,128),(14,129),(14,130),(15,131),(15,132),(15,133),(15,134),(15,135),(16,136),(16,137),(16,138),(16,139),(16,140),(16,141),(16,142),(17,143),(17,144),(17,145),(17,146),(17,147),(18,148),(18,149),(18,150),(18,151),(18,152),(18,153),(18,154),(19,155),(19,156),(19,157),(19,158),(19,159),(20,160),(20,161),(20,162),(20,163),(20,164),(21,165),(21,166),(21,167),(21,168),(21,169),(21,170),(21,171),(22,172),(22,173),(22,174),(22,175),(22,176),(23,177),(23,178),(23,179),(23,180),(23,181),(23,182),(23,183),(32,232),(36,246),(36,247),(36,248),(36,249),(36,250),(36,251),(36,252),(36,253),(36,254),(36,255),(36,256),(36,257),(36,258),(36,259),(36,260),(36,261),(36,262),(36,263),(36,264),(37,265),(38,266),(38,267),(38,268),(38,269),(38,270),(38,271),(38,272),(38,273),(38,274),(38,275),(38,276),(38,277),(38,278),(38,279),(38,280),(38,281),(38,282),(38,283),(38,284),(39,285),(40,286),(40,287),(40,288),(40,289),(40,290),(40,291),(40,292),(40,293),(40,294),(40,295),(40,296),(40,297),(40,298),(40,299),(40,300),(40,301),(40,302),(40,303),(40,304);
/*!40000 ALTER TABLE `test_suite_x_test` ENABLE KEYS */;
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
