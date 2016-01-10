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
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `param_object_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `FK364492AE442966` (`param_object_id`),
  CONSTRAINT `FK364492AE442966` FOREIGN KEY (`param_object_id`) REFERENCES `paramobject` (`param_object_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (30,'insynctive.tests.LoadingTests','loginTest',37),(31,'insynctive.tests.LoadingTests','apptLoadingTest',38),(32,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',39),(33,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',40),(34,'insynctive.tests.LoadingTests','hrTasksLoadingTest',41),(35,'insynctive.tests.LoadingTests','settingAccountLoadingTest',42),(36,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',43),(37,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',44),(38,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',45),(39,'insynctive.tests.LoadingTests','loginTest',46),(40,'insynctive.tests.LoadingTests','apptLoadingTest',47),(41,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',48),(42,'insynctive.tests.LoadingTests','hrHelpDeskLoadingTest',49),(43,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',50),(44,'insynctive.tests.LoadingTests','hrTasksLoadingTest',51),(45,'insynctive.tests.LoadingTests','settingAccountLoadingTest',52),(46,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',53),(47,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',54),(48,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',55),(49,'insynctive.tests.LoadingTests','loginTest',56),(50,'insynctive.tests.LoadingTests','apptLoadingTest',57),(51,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',58),(52,'insynctive.tests.LoadingTests','hrHelpDeskLoadingTest',59),(53,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',60),(54,'insynctive.tests.LoadingTests','hrTasksLoadingTest',61),(55,'insynctive.tests.LoadingTests','settingAccountLoadingTest',62),(56,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',63),(57,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',64),(58,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',65),(59,'insynctive.tests.PersonFileTest','loginTest',66),(60,'insynctive.tests.PersonFileTest','createPersonTest',67),(61,'insynctive.tests.PersonFileTest','changeNameInTitle',68),(62,'insynctive.tests.PersonFileTest','addTitle',69),(63,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',70),(64,'insynctive.tests.PersonFileTest','changeMaritalStatus',71),(65,'insynctive.tests.PersonFileTest','changeBirthDate',72),(66,'insynctive.tests.PersonFileTest','changeGender',73),(67,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',74),(68,'insynctive.tests.PersonFileTest','addPhoneNumber',75),(69,'insynctive.tests.PersonFileTest','addAlternativePhone',76),(70,'insynctive.tests.PersonFileTest','makePrimaryPhone',77),(71,'insynctive.tests.PersonFileTest','changePrimaryEmail',78),(72,'insynctive.tests.PersonFileTest','addEmergencyContact',79),(73,'insynctive.tests.PersonFileTest','changeEmergencyContact',80),(74,'insynctive.tests.PersonFileTest','removeEmergencyContact',81),(75,'insynctive.tests.PersonFileTest','addUSAddress',82),(76,'insynctive.tests.PersonFileTest','removeUSAddress',83),(77,'insynctive.tests.PersonFileTest','assignJob',84),(78,'insynctive.tests.PersonFileTest','loginTest',85),(79,'insynctive.tests.PersonFileTest','createPersonTest',86),(80,'insynctive.tests.PersonFileTest','changeNameInTitle',87),(81,'insynctive.tests.PersonFileTest','addTitle',88),(82,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',89),(83,'insynctive.tests.PersonFileTest','changeMaritalStatus',90),(84,'insynctive.tests.PersonFileTest','changeBirthDate',91),(85,'insynctive.tests.PersonFileTest','changeGender',92),(86,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',93),(87,'insynctive.tests.PersonFileTest','addPhoneNumber',94),(88,'insynctive.tests.PersonFileTest','addAlternativePhone',95),(89,'insynctive.tests.PersonFileTest','makePrimaryPhone',96),(90,'insynctive.tests.PersonFileTest','changePrimaryEmail',97),(91,'insynctive.tests.PersonFileTest','addEmergencyContact',98),(92,'insynctive.tests.PersonFileTest','changeEmergencyContact',99),(93,'insynctive.tests.PersonFileTest','removeEmergencyContact',100),(94,'insynctive.tests.PersonFileTest','addUSAddress',101),(95,'insynctive.tests.PersonFileTest','removeUSAddress',102),(96,'insynctive.tests.PersonFileTest','assignJob',103),(97,'insynctive.tests.PersonFileTest','loginTest',104),(98,'insynctive.tests.PersonFileTest','createPersonTest',105),(99,'insynctive.tests.PersonFileTest','changeNameInTitle',106),(100,'insynctive.tests.PersonFileTest','addTitle',107),(101,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',108),(102,'insynctive.tests.PersonFileTest','changeMaritalStatus',109),(103,'insynctive.tests.PersonFileTest','changeBirthDate',110),(104,'insynctive.tests.PersonFileTest','changeGender',111),(105,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',112),(106,'insynctive.tests.PersonFileTest','addPhoneNumber',113),(107,'insynctive.tests.PersonFileTest','addAlternativePhone',114),(108,'insynctive.tests.PersonFileTest','makePrimaryPhone',115),(109,'insynctive.tests.PersonFileTest','changePrimaryEmail',116),(110,'insynctive.tests.PersonFileTest','addEmergencyContact',117),(111,'insynctive.tests.PersonFileTest','changeEmergencyContact',118),(112,'insynctive.tests.PersonFileTest','removeEmergencyContact',119),(113,'insynctive.tests.PersonFileTest','addUSAddress',120),(114,'insynctive.tests.PersonFileTest','removeUSAddress',121),(115,'insynctive.tests.PersonFileTest','assignJob',122);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
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
