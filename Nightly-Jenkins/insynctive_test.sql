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
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (30,'insynctive.tests.LoadingTests','loginTest',37),(31,'insynctive.tests.LoadingTests','apptLoadingTest',38),(32,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',39),(33,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',40),(34,'insynctive.tests.LoadingTests','hrTasksLoadingTest',41),(35,'insynctive.tests.LoadingTests','settingAccountLoadingTest',42),(36,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',43),(37,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',44),(38,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',45),(39,'insynctive.tests.LoadingTests','loginTest',46),(40,'insynctive.tests.LoadingTests','apptLoadingTest',47),(41,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',48),(42,'insynctive.tests.LoadingTests','hrHelpDeskLoadingTest',49),(43,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',50),(44,'insynctive.tests.LoadingTests','hrTasksLoadingTest',51),(45,'insynctive.tests.LoadingTests','settingAccountLoadingTest',52),(46,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',53),(47,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',54),(48,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',55),(49,'insynctive.tests.LoadingTests','loginTest',56),(50,'insynctive.tests.LoadingTests','apptLoadingTest',57),(51,'insynctive.tests.LoadingTests','hrChecklistLoadingTest',58),(52,'insynctive.tests.LoadingTests','hrHelpDeskLoadingTest',59),(53,'insynctive.tests.LoadingTests','hrPeopleLoadingTest',60),(54,'insynctive.tests.LoadingTests','hrTasksLoadingTest',61),(55,'insynctive.tests.LoadingTests','settingAccountLoadingTest',62),(56,'insynctive.tests.LoadingTests','settingOtherSettingsLoadingTest',63),(57,'insynctive.tests.LoadingTests','settingPeopleLoadingTest',64),(58,'insynctive.tests.LoadingTests','settingV3SettingsLoadingTest',65),(59,'insynctive.tests.PersonFileTest','loginTest',66),(60,'insynctive.tests.PersonFileTest','createPersonTest',67),(61,'insynctive.tests.PersonFileTest','changeNameInTitle',68),(62,'insynctive.tests.PersonFileTest','addTitle',69),(63,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',70),(64,'insynctive.tests.PersonFileTest','changeMaritalStatus',71),(65,'insynctive.tests.PersonFileTest','changeBirthDate',72),(66,'insynctive.tests.PersonFileTest','changeGender',73),(67,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',74),(68,'insynctive.tests.PersonFileTest','addPhoneNumber',75),(69,'insynctive.tests.PersonFileTest','addAlternativePhone',76),(70,'insynctive.tests.PersonFileTest','makePrimaryPhone',77),(71,'insynctive.tests.PersonFileTest','changePrimaryEmail',78),(72,'insynctive.tests.PersonFileTest','addEmergencyContact',79),(73,'insynctive.tests.PersonFileTest','changeEmergencyContact',80),(74,'insynctive.tests.PersonFileTest','removeEmergencyContact',81),(75,'insynctive.tests.PersonFileTest','addUSAddress',82),(76,'insynctive.tests.PersonFileTest','removeUSAddress',83),(77,'insynctive.tests.PersonFileTest','assignJob',84),(78,'insynctive.tests.PersonFileTest','loginTest',85),(79,'insynctive.tests.PersonFileTest','createPersonTest',86),(80,'insynctive.tests.PersonFileTest','changeNameInTitle',87),(81,'insynctive.tests.PersonFileTest','addTitle',88),(82,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',89),(83,'insynctive.tests.PersonFileTest','changeMaritalStatus',90),(84,'insynctive.tests.PersonFileTest','changeBirthDate',91),(85,'insynctive.tests.PersonFileTest','changeGender',92),(86,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',93),(87,'insynctive.tests.PersonFileTest','addPhoneNumber',94),(88,'insynctive.tests.PersonFileTest','addAlternativePhone',95),(89,'insynctive.tests.PersonFileTest','makePrimaryPhone',96),(90,'insynctive.tests.PersonFileTest','changePrimaryEmail',97),(91,'insynctive.tests.PersonFileTest','addEmergencyContact',98),(92,'insynctive.tests.PersonFileTest','changeEmergencyContact',99),(93,'insynctive.tests.PersonFileTest','removeEmergencyContact',100),(94,'insynctive.tests.PersonFileTest','addUSAddress',101),(95,'insynctive.tests.PersonFileTest','removeUSAddress',102),(96,'insynctive.tests.PersonFileTest','assignJob',103),(97,'insynctive.tests.PersonFileTest','loginTest',104),(98,'insynctive.tests.PersonFileTest','createPersonTest',105),(99,'insynctive.tests.PersonFileTest','changeNameInTitle',106),(100,'insynctive.tests.PersonFileTest','addTitle',107),(101,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',108),(102,'insynctive.tests.PersonFileTest','changeMaritalStatus',109),(103,'insynctive.tests.PersonFileTest','changeBirthDate',110),(104,'insynctive.tests.PersonFileTest','changeGender',111),(105,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',112),(106,'insynctive.tests.PersonFileTest','addPhoneNumber',113),(107,'insynctive.tests.PersonFileTest','addAlternativePhone',114),(108,'insynctive.tests.PersonFileTest','makePrimaryPhone',115),(109,'insynctive.tests.PersonFileTest','changePrimaryEmail',116),(110,'insynctive.tests.PersonFileTest','addEmergencyContact',117),(111,'insynctive.tests.PersonFileTest','changeEmergencyContact',118),(112,'insynctive.tests.PersonFileTest','removeEmergencyContact',119),(113,'insynctive.tests.PersonFileTest','addUSAddress',120),(114,'insynctive.tests.PersonFileTest','removeUSAddress',121),(115,'insynctive.tests.PersonFileTest','assignJob',122),(116,'insynctive.tests.DocumentTest','loginTest',123),(117,'insynctive.tests.DocumentTest','getDocuments',124),(118,'insynctive.tests.DocumentTest','loginTest',125),(119,'insynctive.tests.DocumentTest','getDocuments',126),(120,'insynctive.tests.PersonFileTest','loginTest',127),(121,'insynctive.tests.PersonFileTest','openPersonFIle',128),(122,'insynctive.tests.PersonFileTest','openDocuments',129),(123,'insynctive.tests.PersonFileTest','loginTest',130),(124,'insynctive.tests.PersonFileTest','openPersonFIle',131),(125,'insynctive.tests.PersonFileTest','openDocuments',132),(126,'insynctive.tests.PersonFileTest','loginTest',133),(127,'insynctive.tests.PersonFileTest','config2FAOn',134),(128,'insynctive.tests.PersonFileTest','logOut',135),(129,'insynctive.tests.PersonFileTest','loginWith2FAEmail',136),(130,'insynctive.tests.PersonFileTest','config2FAOff',137),(131,'insynctive.tests.PersonFileTest','loginTest',138),(132,'insynctive.tests.PersonFileTest','config2FAOn',139),(133,'insynctive.tests.PersonFileTest','logOut',140),(134,'insynctive.tests.PersonFileTest','loginWith2FAEmail',141),(135,'insynctive.tests.PersonFileTest','config2FAOff',142),(136,'insynctive.tests.PersonFileTest','firstLogin',143),(137,'insynctive.tests.PersonFileTest','config2FAOff',144),(138,'insynctive.tests.PersonFileTest','logOut',145),(139,'insynctive.tests.PersonFileTest','loginWith2FAEmail',146),(140,'insynctive.tests.PersonFileTest','logOut2',147),(141,'insynctive.tests.PersonFileTest','loginAsAgentTest',148),(142,'insynctive.tests.PersonFileTest','config2FAOff',149),(143,'insynctive.tests.PersonFileTest','loginTest',150),(144,'insynctive.tests.PersonFileTest','config2FAOn',151),(145,'insynctive.tests.PersonFileTest','logOut',152),(146,'insynctive.tests.PersonFileTest','loginWith2FAEmail',153),(147,'insynctive.tests.PersonFileTest','config2FAOff',154),(148,'insynctive.tests.PersonFileTest','firstLogin',155),(149,'insynctive.tests.PersonFileTest','config2FAOff',156),(150,'insynctive.tests.PersonFileTest','logOut',157),(151,'insynctive.tests.PersonFileTest','loginWith2FAEmail',158),(152,'insynctive.tests.PersonFileTest','logOut2',159),(153,'insynctive.tests.PersonFileTest','loginAsAgentTest',160),(154,'insynctive.tests.PersonFileTest','config2FAOff',161),(155,'insynctive.tests.PersonFileTest','loginTest',162),(156,'insynctive.tests.PersonFileTest','config2FAOn',163),(157,'insynctive.tests.PersonFileTest','logOut',164),(158,'insynctive.tests.PersonFileTest','loginWith2FAPhone',165),(159,'insynctive.tests.PersonFileTest','config2FAOff',166),(160,'insynctive.tests.PersonFileTest','loginTest',167),(161,'insynctive.tests.PersonFileTest','config2FAOn',168),(162,'insynctive.tests.PersonFileTest','logOut',169),(163,'insynctive.tests.PersonFileTest','loginWith2FAEmail',170),(164,'insynctive.tests.PersonFileTest','config2FAOff',171),(165,'insynctive.tests.PersonFileTest','firstLogin',172),(166,'insynctive.tests.PersonFileTest','config2FAOff',173),(167,'insynctive.tests.PersonFileTest','logOut',174),(168,'insynctive.tests.PersonFileTest','loginWith2FAEmail',175),(169,'insynctive.tests.PersonFileTest','logOut2',176),(170,'insynctive.tests.PersonFileTest','loginAsAgentTest',177),(171,'insynctive.tests.PersonFileTest','config2FAOff',178),(172,'insynctive.tests.PersonFileTest','loginTest',179),(173,'insynctive.tests.PersonFileTest','config2FAOn',180),(174,'insynctive.tests.PersonFileTest','logOut',181),(175,'insynctive.tests.PersonFileTest','loginWith2FAPhone',182),(176,'insynctive.tests.PersonFileTest','config2FAOff',183),(177,'insynctive.tests.PersonFileTest','loginTest',184),(178,'insynctive.tests.PersonFileTest','config2FAOn',185),(179,'insynctive.tests.PersonFileTest','logOut',186),(180,'insynctive.tests.PersonFileTest','loginWith2FAPhone',187),(181,'insynctive.tests.PersonFileTest','logOut',188),(182,'insynctive.tests.PersonFileTest','loginAsAgentTest',189),(183,'insynctive.tests.PersonFileTest','config2FAOff',190),(232,'insynctive.tests.PersonFileTest','firstLogin',839),(246,'insynctive.tests.PersonFileTest','loginTest',853),(247,'insynctive.tests.PersonFileTest','createPersonTest',854),(248,'insynctive.tests.PersonFileTest','changeNameInTitle',855),(249,'insynctive.tests.PersonFileTest','addTitle',856),(250,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',857),(251,'insynctive.tests.PersonFileTest','changeMaritalStatus',858),(252,'insynctive.tests.PersonFileTest','changeBirthDate',859),(253,'insynctive.tests.PersonFileTest','changeGender',860),(254,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',861),(255,'insynctive.tests.PersonFileTest','addPhoneNumber',862),(256,'insynctive.tests.PersonFileTest','addAlternativePhone',863),(257,'insynctive.tests.PersonFileTest','makePrimaryPhone',864),(258,'insynctive.tests.PersonFileTest','changePrimaryEmail',865),(259,'insynctive.tests.PersonFileTest','addEmergencyContact',866),(260,'insynctive.tests.PersonFileTest','changeEmergencyContact',867),(261,'insynctive.tests.PersonFileTest','removeEmergencyContact',868),(262,'insynctive.tests.PersonFileTest','addUSAddress',869),(263,'insynctive.tests.PersonFileTest','removeUSAddress',870),(264,'insynctive.tests.PersonFileTest','assignJob',871),(265,'insynctive.tests.PersonFileTest','firstLogin',872),(266,'insynctive.tests.PersonFileTest','loginTest',873),(267,'insynctive.tests.PersonFileTest','createPersonTest',874),(268,'insynctive.tests.PersonFileTest','changeNameInTitle',875),(269,'insynctive.tests.PersonFileTest','addTitle',876),(270,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',877),(271,'insynctive.tests.PersonFileTest','changeMaritalStatus',878),(272,'insynctive.tests.PersonFileTest','changeBirthDate',879),(273,'insynctive.tests.PersonFileTest','changeGender',880),(274,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',881),(275,'insynctive.tests.PersonFileTest','addPhoneNumber',882),(276,'insynctive.tests.PersonFileTest','addAlternativePhone',883),(277,'insynctive.tests.PersonFileTest','makePrimaryPhone',884),(278,'insynctive.tests.PersonFileTest','changePrimaryEmail',885),(279,'insynctive.tests.PersonFileTest','addEmergencyContact',886),(280,'insynctive.tests.PersonFileTest','changeEmergencyContact',887),(281,'insynctive.tests.PersonFileTest','removeEmergencyContact',888),(282,'insynctive.tests.PersonFileTest','addUSAddress',889),(283,'insynctive.tests.PersonFileTest','removeUSAddress',890),(284,'insynctive.tests.PersonFileTest','assignJob',891),(285,'insynctive.tests.PersonFileTest','firstLogin',892),(286,'insynctive.tests.PersonFileTest','loginTest',893),(287,'insynctive.tests.PersonFileTest','createPersonTest',894),(288,'insynctive.tests.PersonFileTest','changeNameInTitle',895),(289,'insynctive.tests.PersonFileTest','addTitle',896),(290,'insynctive.tests.PersonFileTest','changeNameInPersonalInfo',897),(291,'insynctive.tests.PersonFileTest','changeMaritalStatus',898),(292,'insynctive.tests.PersonFileTest','changeBirthDate',899),(293,'insynctive.tests.PersonFileTest','changeGender',900),(294,'insynctive.tests.PersonFileTest','addSocialSecurityNumber',901),(295,'insynctive.tests.PersonFileTest','addPhoneNumber',902),(296,'insynctive.tests.PersonFileTest','addAlternativePhone',903),(297,'insynctive.tests.PersonFileTest','makePrimaryPhone',904),(298,'insynctive.tests.PersonFileTest','changePrimaryEmail',905),(299,'insynctive.tests.PersonFileTest','addEmergencyContact',906),(300,'insynctive.tests.PersonFileTest','changeEmergencyContact',907),(301,'insynctive.tests.PersonFileTest','removeEmergencyContact',908),(302,'insynctive.tests.PersonFileTest','addUSAddress',909),(303,'insynctive.tests.PersonFileTest','removeUSAddress',910),(304,'insynctive.tests.PersonFileTest','assignJob',911);
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

-- Dump completed on 2016-01-10 20:16:43
