CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `itemID` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`itemID`),
  UNIQUE KEY `itemID_UNIQUE` (`itemID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `memberID` int unsigned NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `contact_information` varchar(11) NOT NULL,
  `registration_date` date DEFAULT NULL,
  `signed_waiver` enum('TRUE','FALSE') DEFAULT NULL,
  `membership_status` enum('ACTIVE','INACTIVE','EXPIRED') DEFAULT NULL,
  `membership_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `customerID_UNIQUE` (`memberID`),
  KEY `fk_membership_type` (`membership_type`),
  CONSTRAINT `fk_membership_type` FOREIGN KEY (`membership_type`) REFERENCES `membership_types` (`membership_type`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `membership_types`
--

DROP TABLE IF EXISTS `membership_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership_types` (
  `membership_type` varchar(45) NOT NULL,
  `price` double unsigned NOT NULL,
  PRIMARY KEY (`membership_type`),
  UNIQUE KEY `card_types_UNIQUE` (`membership_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `salesID` int NOT NULL AUTO_INCREMENT,
  `transactionID` int DEFAULT NULL,
  `itemID` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`salesID`),
  UNIQUE KEY `salesID_UNIQUE` (`salesID`),
  KEY `FKst02_idx` (`itemID`),
  KEY `FKst01_idx` (`transactionID`),
  CONSTRAINT `FKst01` FOREIGN KEY (`transactionID`) REFERENCES `transactions` (`transactionID`),
  CONSTRAINT `FKst02` FOREIGN KEY (`itemID`) REFERENCES `items` (`itemID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transactionID` int NOT NULL AUTO_INCREMENT,
  `memberID` int DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `amount` decimal(10,0) NOT NULL,
  `transaction_type` enum('membership_purchase','membership_renewal','consumables','merchandise') DEFAULT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE KEY `saleID_UNIQUE` (`transactionID`),
  KEY `FKts01_idx` (`memberID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


