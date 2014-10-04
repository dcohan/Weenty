DROP DATABASE IF EXISTS `Cuponera`;

CREATE DATABASE `Cuponera`;

USE `Cuponera`;

-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: paylessdbprincipal.test.emerios.com    Database: Payless
-- ------------------------------------------------------
-- Server version	5.6.12-log

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
-- Table structure for table `Campaign`
--

DROP TABLE IF EXISTS `Campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Campaign` (
  `CampaignId` int(11) NOT NULL AUTO_INCREMENT,
  `Step` char(1) NOT NULL,
  `Resume` char(1) DEFAULT NULL,
  `Name` varchar(200) DEFAULT NULL,
  `Message` varchar(255) NOT NULL,
  `MessageAction` varchar(255) DEFAULT NULL,
  `MessageActionLabel` varchar(50) DEFAULT NULL,
  `OriginalStartDate` datetime NOT NULL,
  `StartDate` datetime NOT NULL,
  `EndDate` datetime DEFAULT NULL,
  `ContinueNextDay` int(1) NOT NULL,
  `Total` int(11) DEFAULT NULL,
  `Success` int(11) DEFAULT NULL,
  `Fail` int(11) DEFAULT NULL,
  `LastUpdate` datetime DEFAULT NULL,
  `LastOs` char(1) DEFAULT NULL,
  `LockId` varchar(36) DEFAULT NULL,
  `LockDate` datetime DEFAULT NULL,
  `FirstExecution` datetime DEFAULT NULL,
  `Discarded` int(1) DEFAULT NULL,
  `CreationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `TestProfileId` int(11) DEFAULT NULL,
  `UploadToken` varchar(36) DEFAULT NULL,
  `ProfileGroupId` int(11) DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`CampaignId`),
  KEY `IX_Campaign_LockId` (`LockId`),
  KEY `IX_Campaign_Step` (`Step`,`CampaignId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Coupons`
--

DROP TABLE IF EXISTS `Coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupons` (
  `IdCoupon` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) DEFAULT NULL,
  `PromoCode` varchar(50) DEFAULT NULL,
  `TargetURL` varchar(255) DEFAULT NULL,
  `Active` int(1) DEFAULT NULL,
  `StartDatetime` datetime DEFAULT NULL,
  `ExpirationDatetime` datetime DEFAULT NULL,
  `ItemOrder` int(11) DEFAULT '0',
  `IdProfileGroup` int(11) DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdCoupon`),
  KEY `ix_coupons_idprofile_group` (`IdProfileGroup`),
  CONSTRAINT `fk_ix_coupons_profilegroupid` FOREIGN KEY (`IdProfileGroup`) REFERENCES `ProfileGroups` (`IdProfileGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DeviceOs`
--

DROP TABLE IF EXISTS `DeviceOs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DeviceOs` (
  `IdDeviceOs` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(20) NOT NULL,
  PRIMARY KEY (`IdDeviceOs`),
  UNIQUE KEY `Code_UNIQUE` (`Code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT=' ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DeviceTypes`
--

DROP TABLE IF EXISTS `DeviceTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DeviceTypes` (
  `IdDeviceType` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(20) NOT NULL,
  PRIMARY KEY (`IdDeviceType`),
  UNIQUE KEY `Code_UNIQUE` (`Code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EnabledSections`
--

DROP TABLE IF EXISTS `EnabledSections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnabledSections` (
  `IdEnabledSection` int(11) NOT NULL AUTO_INCREMENT,
  `Section` varchar(45) DEFAULT NULL,
  `IdDeviceOs` int(11) DEFAULT NULL,
  `IsEnabled` int(1) DEFAULT NULL,
  PRIMARY KEY (`IdEnabledSection`),
  KEY `ix_es_IdDeviceOs_idx` (`IdDeviceOs`),
  CONSTRAINT `ix_es_IdDeviceOs` FOREIGN KEY (`IdDeviceOs`) REFERENCES `DeviceOs` (`IdDeviceOs`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FailedNotification`
--

DROP TABLE IF EXISTS `FailedNotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FailedNotification` (
  `CampaignId` int(11) NOT NULL,
  `ProfileId` int(11) NOT NULL,
  `Xid` varchar(30) NOT NULL,
  `Os` char(1) NOT NULL,
  `Error` varchar(50) NOT NULL,
  `ErrorDetail` varchar(255) DEFAULT NULL,
  `ProcessedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CampaignId`,`ProfileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `HomeOffers`
--

DROP TABLE IF EXISTS `HomeOffers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HomeOffers` (
  `IdHomeOffer` int(11) NOT NULL AUTO_INCREMENT,
  `TargetURL` varchar(255) DEFAULT NULL,
  `Active` int(1) DEFAULT NULL,
  `StartDatetime` datetime DEFAULT NULL,
  `ExpirationDatetime` datetime DEFAULT NULL,
  `ItemOrder` int(11) DEFAULT '0',
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdHomeOffer`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ImageSizes`
--

DROP TABLE IF EXISTS `ImageSizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ImageSizes` (
  `IdImageSize` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `IdDeviceOs` int(11) NOT NULL,
  `Width` int(11) DEFAULT NULL,
  `Height` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdImageSize`),
  KEY `ix_IdDeviceOs_idx` (`IdDeviceOs`),
  CONSTRAINT `ix_IdDeviceOs` FOREIGN KEY (`IdDeviceOs`) REFERENCES `DeviceOs` (`IdDeviceOs`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LookBookAttributeCategories`
--

DROP TABLE IF EXISTS `LookBookAttributeCategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LookBookAttributeCategories` (
  `IdLookBookAttributeCategory` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(40) NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `ItemOrder` int(11) DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdLookBookAttributeCategory`),
  UNIQUE KEY `Code_UNIQUE` (`Code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LookBookAttributes`
--

DROP TABLE IF EXISTS `LookBookAttributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LookBookAttributes` (
  `IdLookBookAttribute` int(11) NOT NULL AUTO_INCREMENT,
  `IdLookBookAttributeCategory` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `ItemOrder` int(11) DEFAULT '0',
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdLookBookAttribute`),
  KEY `fk_LookBookFilters_LookBookFiltersCategories1_idx` (`IdLookBookAttributeCategory`),
  CONSTRAINT `fk_LookBookFilters_LookBookFiltersCategories1` FOREIGN KEY (`IdLookBookAttributeCategory`) REFERENCES `LookBookAttributeCategories` (`IdLookBookAttributeCategory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4040 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LookBookItemAttributes`
--

DROP TABLE IF EXISTS `LookBookItemAttributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LookBookItemAttributes` (
  `idLookBookItemAttribute` int(11) NOT NULL AUTO_INCREMENT,
  `IdLookBookItem` int(11) NOT NULL,
  `IdLookBookAttribute` int(11) NOT NULL,
  PRIMARY KEY (`idLookBookItemAttribute`),
  KEY `Item_attrib_idx` (`IdLookBookItem`,`IdLookBookAttribute`),
  KEY `attrib_item_idx` (`IdLookBookAttribute`,`IdLookBookItem`),
  CONSTRAINT `fk_LookBookItemAttributes_LookBookAttributes1` FOREIGN KEY (`IdLookBookAttribute`) REFERENCES `LookBookAttributes` (`IdLookBookAttribute`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_LookBookItemAttributes_LookBookItems1` FOREIGN KEY (`IdLookBookItem`) REFERENCES `LookBookItems` (`IdLookBookItem`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LookBookItems`
--

DROP TABLE IF EXISTS `LookBookItems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LookBookItems` (
  `IdLookBookItem` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) DEFAULT NULL,
  `Description` varchar(800) DEFAULT NULL,
  `LotId` varchar(100) DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdLookBookItem`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Modules`
--

DROP TABLE IF EXISTS `Modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Modules` (
  `idModule` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`idModule`),
  UNIQUE KEY `idModules_UNIQUE` (`idModule`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PendingNotification`
--

DROP TABLE IF EXISTS `PendingNotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PendingNotification` (
  `CampaignId` int(11) NOT NULL,
  `ProfileId` int(11) NOT NULL,
  `Xid` varchar(30) NOT NULL,
  `Os` char(1) NOT NULL,
  `LockId` varchar(36) DEFAULT NULL,
  `LockDate` datetime DEFAULT NULL,
  PRIMARY KEY (`CampaignId`,`ProfileId`),
  KEY `IX_PendingNotification_Campaign_Os_LockId` (`CampaignId`,`Os`,`LockId`,`ProfileId`),
  KEY `IX_PendingNotificationLockId` (`LockId`,`CampaignId`,`ProfileId`,`Xid`,`Os`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permissions` (
  `idPermission` int(11) NOT NULL AUTO_INCREMENT,
  `IdRole` int(11) NOT NULL,
  `IdModule` int(11) NOT NULL,
  `Read` tinyint(1) DEFAULT '0',
  `Create` tinyint(1) DEFAULT '0',
  `Update` tinyint(1) DEFAULT '0',
  `Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idPermission`),
  UNIQUE KEY `idPermission_UNIQUE` (`idPermission`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PreHomeImages`
--

DROP TABLE IF EXISTS `PreHomeImages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PreHomeImages` (
  `IdPreHomeImage` int(11) NOT NULL AUTO_INCREMENT,
  `Active` int(11) DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdPreHomeImage`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroupDeviceOs`
--

DROP TABLE IF EXISTS `ProfileGroupDeviceOs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroupDeviceOs` (
  `IdProfileGroupDeviceOs` int(11) NOT NULL AUTO_INCREMENT,
  `IdProfileGroup` int(11) NOT NULL,
  `IdDeviceOs` int(11) NOT NULL,
  PRIMARY KEY (`IdProfileGroupDeviceOs`),
  KEY `ix_IdProfileGroup_idx` (`IdProfileGroup`),
  KEY `ix_IdDeviceOs_idx` (`IdDeviceOs`),
  CONSTRAINT `ix_pgd_IdDeviceOs` FOREIGN KEY (`IdDeviceOs`) REFERENCES `DeviceOs` (`IdDeviceOs`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ix_pgd_IdProfileGroup` FOREIGN KEY (`IdProfileGroup`) REFERENCES `ProfileGroups` (`IdProfileGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroupMembers`
--

DROP TABLE IF EXISTS `ProfileGroupMembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroupMembers` (
  `IdProfileGroupMember` int(11) NOT NULL AUTO_INCREMENT,
  `IdProfileGroup` int(11) NOT NULL,
  `IdProfile` int(11) NOT NULL,
  PRIMARY KEY (`IdProfileGroupMember`),
  KEY `ix_pgm_group_idx` (`IdProfileGroup`),
  KEY `ix_pgm_profile_idx` (`IdProfile`),
  CONSTRAINT `ix_pgm_group` FOREIGN KEY (`IdProfileGroup`) REFERENCES `ProfileGroups` (`IdProfileGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ix_pgm_profile` FOREIGN KEY (`IdProfile`) REFERENCES `Profiles` (`IdProfile`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14397 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroupMembersTemporary`
--

DROP TABLE IF EXISTS `ProfileGroupMembersTemporary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroupMembersTemporary` (
  `IdProfile` int(11) NOT NULL,
  PRIMARY KEY (`IdProfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroupStates`
--

DROP TABLE IF EXISTS `ProfileGroupStates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroupStates` (
  `ProfileGroupStates` int(11) NOT NULL AUTO_INCREMENT,
  `IdProfileGroup` int(11) NOT NULL,
  `IdState` char(2) NOT NULL,
  PRIMARY KEY (`ProfileGroupStates`),
  KEY `ix_pgs_group_idx` (`IdProfileGroup`),
  KEY `ix_pgs_state_idx` (`IdState`),
  CONSTRAINT `ix_pgs_state` FOREIGN KEY (`IdState`) REFERENCES `States` (`IdState`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ix_pgs_group` FOREIGN KEY (`IdProfileGroup`) REFERENCES `ProfileGroups` (`IdProfileGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroupZipcodes`
--

DROP TABLE IF EXISTS `ProfileGroupZipcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroupZipcodes` (
  `IdProfileGroupZipcode` int(11) NOT NULL AUTO_INCREMENT,
  `IdProfileGroup` int(11) NOT NULL,
  `Zipcode` varchar(5) NOT NULL,
  PRIMARY KEY (`IdProfileGroupZipcode`),
  KEY `ix_pgz_group_idx` (`IdProfileGroup`),
  CONSTRAINT `ix_pgz_group` FOREIGN KEY (`IdProfileGroup`) REFERENCES `ProfileGroups` (`IdProfileGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileGroups`
--

DROP TABLE IF EXISTS `ProfileGroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileGroups` (
  `IdProfileGroup` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `IsFixed` int(1) DEFAULT NULL,
  `AppVersion` varchar(45) DEFAULT NULL,
  `Geolocation` int(1) DEFAULT NULL,
  `PushNotification` int(1) DEFAULT NULL,
  `SMSNotification` int(1) DEFAULT NULL,
  `EmailNotification` int(1) DEFAULT NULL,
  `PostalNotification` int(1) DEFAULT NULL,
  `MinAge` int(11) DEFAULT NULL,
  `MembersCount` int(11) DEFAULT NULL,
  `MembersComputingDatetime` datetime DEFAULT NULL,
  `LockId` char(40) DEFAULT NULL,
  `LockDate` datetime DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdProfileGroup`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Profiles`
--

DROP TABLE IF EXISTS `Profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Profiles` (
  `IdProfile` int(11) NOT NULL AUTO_INCREMENT,
  `DeviceId` char(40) NOT NULL,
  `IdDeviceType` int(11) NOT NULL,
  `IdDeviceOs` int(11) NOT NULL,
  `AppVersion` varchar(40) NOT NULL,
  `ResolutionWidth` int(11) DEFAULT NULL,
  `ResolutionHeight` int(11) DEFAULT NULL,
  `CompletedRegistration` int(1) DEFAULT '0',
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `Geolocation` int(1) DEFAULT '0',
  `PushNotification` int(1) DEFAULT '0',
  `SMSNotification` int(1) DEFAULT '0',
  `Xid` varchar(40) DEFAULT NULL,
  `EmailNotification` int(1) DEFAULT '0',
  `PostalNotification` int(1) DEFAULT '0',
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `BirthDate` date DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `Address_1` varchar(255) DEFAULT NULL,
  `Address_2` varchar(255) DEFAULT NULL,
  `City` varchar(255) DEFAULT NULL,
  `IdState` char(2) DEFAULT NULL,
  `ZipCode` varchar(5) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `LastAppUseDatetime` datetime DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdProfile`),
  UNIQUE KEY `DeviceId_UNIQUE` (`DeviceId`),
  KEY `fk_Profiles_DeviceTypes1_idx` (`IdDeviceType`),
  KEY `fk_Profiles_DeviceOs1_idx` (`IdDeviceOs`),
  KEY `fk_idstate_idx` (`IdState`),
  CONSTRAINT `fk_idstate` FOREIGN KEY (`IdState`) REFERENCES `States` (`IdState`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Profiles_DeviceOs1` FOREIGN KEY (`IdDeviceOs`) REFERENCES `DeviceOs` (`IdDeviceOs`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Profiles_DeviceTypes1` FOREIGN KEY (`IdDeviceType`) REFERENCES `DeviceTypes` (`IdDeviceType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=181637 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PushNotificationsRatio`
--

DROP TABLE IF EXISTS `PushNotificationsRatio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PushNotificationsRatio` (
  `Ratio` int(11) NOT NULL,
  `UpdateDateTime` datetime NOT NULL,
  PRIMARY KEY (`UpdateDateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `idRole` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Rolescol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idRole`),
  UNIQUE KEY `idRole_UNIQUE` (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SocialFeedAccessLog`
--

DROP TABLE IF EXISTS `SocialFeedAccessLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialFeedAccessLog` (
  `IdSocialFeedAccessLog` int(11) NOT NULL AUTO_INCREMENT,
  `Source` varchar(255) DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdSocialFeedAccessLog`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SocialUpdates`
--

DROP TABLE IF EXISTS `SocialUpdates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialUpdates` (
  `IdSocialUpdate` int(11) NOT NULL AUTO_INCREMENT,
  `IdSourceItem` char(32) NOT NULL,
  `Name` varchar(800) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Text` varchar(800) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ImageURL` varchar(255) DEFAULT NULL,
  `TargetURL` varchar(255) DEFAULT NULL,
  `Source` char(2) NOT NULL,
  `SocialItemDatetime` datetime DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdSocialUpdate`),
  UNIQUE KEY `ItemId_source_UQ` (`IdSourceItem`,`Source`)
) ENGINE=InnoDB AUTO_INCREMENT=2325 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SqlScriptLog`
--

DROP TABLE IF EXISTS `SqlScriptLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SqlScriptLog` (
  `Id` varchar(36) NOT NULL,
  `FileName` varchar(255) DEFAULT NULL,
  `Iteration` varchar(10) DEFAULT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Comment` varchar(1024) DEFAULT NULL,
  `ErrorMessage` varchar(1024) DEFAULT NULL,
  `ExecutionDateTime` datetime NOT NULL,
  `ExecutionStatus` int(11) NOT NULL,
  `Forced` tinyint(1) NOT NULL,
  `HashCode` varchar(50) DEFAULT NULL,
  `Timestamp` binary(8) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `States`
--

DROP TABLE IF EXISTS `States`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `States` (
  `IdState` char(2) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdState`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Stores`
--

DROP TABLE IF EXISTS `Stores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stores` (
  `IdStore` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `ContactNumber` varchar(40) DEFAULT NULL,
  `Latitude` double DEFAULT NULL,
  `Longitude` double DEFAULT NULL,
  `ZipCode` varchar(10) DEFAULT NULL,
  `City` varchar(100) DEFAULT NULL,
  `IdState` char(2) NOT NULL,
  `StoreHours` text,
  `GeolocationProcessingDatetime` datetime DEFAULT NULL,
  `GeolocationTries` int(11) NOT NULL DEFAULT '0',
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdStore`),
  KEY `fk_Stores_States1_idx` (`IdState`),
  KEY `ix_geolocationprocessing` (`GeolocationProcessingDatetime`),
  CONSTRAINT `IdState` FOREIGN KEY (`IdState`) REFERENCES `States` (`IdState`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SucceedNotification`
--

DROP TABLE IF EXISTS `SucceedNotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SucceedNotification` (
  `CampaignId` int(11) NOT NULL,
  `ProfileId` int(11) NOT NULL,
  `Xid` varchar(30) NOT NULL,
  `Os` char(1) NOT NULL,
  `JobId` varchar(255) DEFAULT NULL,
  `ProcessedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CampaignId`,`ProfileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `IdRole` int(11) DEFAULT NULL,
  `CreationDatetime` datetime DEFAULT NULL,
  `ModificationDatetime` datetime DEFAULT NULL,
  `DeletionDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `idUsers_UNIQUE` (`IdUser`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `XtifyTimeLog`
--

DROP TABLE IF EXISTS `XtifyTimeLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `XtifyTimeLog` (
  `XtifyTimeLogID` int(11) NOT NULL AUTO_INCREMENT,
  `ExecDateTime` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `ExecStart` datetime(3) NOT NULL,
  `ExecEnd` datetime(3) NOT NULL,
  `HTTPCode` int(10) NOT NULL,
  PRIMARY KEY (`XtifyTimeLogID`)
) ENGINE=InnoDB AUTO_INCREMENT=546 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'Payless'
--
/*!50003 DROP FUNCTION IF EXISTS `MapIdDeviceOsToCharacterCode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` FUNCTION `MapIdDeviceOsToCharacterCode`(
	IdDeviceOs INT 
) RETURNS char(1) CHARSET latin1
BEGIN
	DECLARE charCode CHAR(1);
	
	SET charCode =
		CASE IdDeviceOs 
			WHEN 1 THEN 'a'
			WHEN 2 THEN 'i'
			WHEN 3 THEN 'f'
			ELSE 'i'
		END;
	RETURN charCode;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CreateProfileGroupMembersProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `CreateProfileGroupMembersProcess`(
	IN IdProfileGroup INT,
	IN Geolocation INT,
	IN PushNotification INT,
	IN SMSNotification INT,
	IN EmailNotification INT, 
	IN PostalNotification INT,
	IN MinAge INT,
	IN ZipcodesList VARCHAR(1000),
	IN DeviceOsList VARCHAR(1000),
	IN StatesList VARCHAR(1000)
)
BEGIN
	INSERT INTO ProfileGroupMembers (IdProfileGroup, IdProfile)
	SELECT 
		IdProfileGroup, p.IdProfile 
	FROM Profiles p 
	WHERE  
		(Geolocation IS NULL OR Geolocation = p.Geolocation)
	AND (PushNotification IS NULL OR PushNotification = p.PushNotification)
	AND (SMSNotification IS NULL OR SMSNotification = p.SMSNotification)
	AND (EmailNotification IS NULL OR EmailNotification = p.EmailNotification)
	AND (PostalNotification IS NULL OR PostalNotification = p.PostalNotification)
	AND (MinAge IS NULL OR DATE_SUB(CURRENT_DATE(), INTERVAL MinAge YEAR) >= p.BirthDate)
	AND (ZipcodesList IS NULL OR FIND_IN_SET(p.ZipCode, ZipcodesList))
	AND (DeviceOsList IS NULL OR FIND_IN_SET(p.IdDeviceOS, DeviceOsList))
	AND (StatesList IS NULL OR FIND_IN_SET(p.IdState, StatesList));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DeactivatePreHomeImagesProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `DeactivatePreHomeImagesProcess`(
	IN id INT
)
BEGIN
	SET @isValid = 0;

	SELECT @isValid:=COUNT(*)
	FROM PreHomeImages
	WHERE IdPreHomeImage = id
	AND Active = 1;

	IF(@isValid = 1) THEN 
		UPDATE PreHomeImages 
		SET Active = 0
		WHERE IdPreHomeImage != id;
	END IF;

	SELECT @isValid as Updated;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `debug_msg` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `debug_msg`(enabled INTEGER, msg VARCHAR(255))
BEGIN
  IF enabled THEN BEGIN
    select concat("** ", msg) AS '** DEBUG:';
  END; END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FindAllStoreIdsProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `FindAllStoreIdsProcess`()
BEGIN
	SELECT IdStore 
	FROM Stores
	WHERE DeletionDatetime IS NULL;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GenericListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GenericListProcess`(
	IN tableName VARCHAR(50),
	IN orderByParam VARCHAR(200),
	IN search VARCHAR(600),
	IN rowOffset INT,
	IN pageSize INT,
	IN returnCount INT
)
BEGIN
	IF(returnCount IS NOT NULL AND returnCount > 0) THEN 
		SET @query = concat(
			"SELECT COUNT(*) as total FROM ",
			tableName,
			" WHERE DeletionDatetime IS NULL ");
	ELSE 
		SET @query = concat(
			"SELECT SQL_CALC_FOUND_ROWS * FROM ",
			tableName,
			" WHERE DeletionDatetime IS NULL ");
	END IF;

	IF(search IS NOT NULL AND search != '') THEN
		SET @query = concat(@query," AND ", search);
	END IF;
	
	IF (orderByParam IS NOT NULL and orderByParam != '') THEN
		SET @query = concat(@query," ORDER BY ", orderByParam);
	END IF;

	IF(returnCount IS NULL OR returnCount < 1) THEN 

		IF(rowOffset IS NOT NULL AND pageSize IS NOT NULL) then
			SET @query = concat(@query, " LIMIT ",rowOffset, ", ", pageSize, ";");
		END IF;

	END IF;

  	PREPARE stmt FROM @query;
  	EXECUTE stmt;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetCountGeocodingProcessingProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetCountGeocodingProcessingProcess`(
	IN PeriodHours INT
)
BEGIN
	SELECT COUNT(*) as total
	FROM Stores
	WHERE GeolocationProcessingDatetime BETWEEN date_sub(NOW(), INTERVAL PeriodHours HOUR) AND NOW();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetCouponsLastUpdateProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetCouponsLastUpdateProcess`()
BEGIN
	SELECT MAX(IFNULL(ModificationDatetime,CreationDatetime)) max_datetime
	FROM Coupons
	WHERE DeletionDatetime IS NULL;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetCouponsProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetCouponsProcess`(	
	IN IdProfile INT
)
BEGIN
	SELECT 
			c.IdCoupon,
			c.TargetURL,
			c.StartDatetime,
			c.ExpirationDatetime,
			c.Title,
			c.PromoCode,
			c.IdProfileGroup
	FROM 
			Coupons c
	LEFT JOIN 
			ProfileGroupMembers pgm ON pgm.IdProfileGroup = c.IdProfileGroup
	WHERE 
			c.DeletionDatetime IS NULL
		AND c.Active = 1
		AND CURRENT_TIMESTAMP BETWEEN c.StartDatetime AND c.ExpirationDatetime
		AND (
			pgm.IdProfile = IdProfile OR c.IdProfileGroup IS NULL
		)
	ORDER BY 
		c.ItemOrder DESC, c.CreationDatetime DESC; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetFoundRowsProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetFoundRowsProcess`()
BEGIN
	SELECT FOUND_ROWS() as count;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetHomeOffersLastUpdateProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetHomeOffersLastUpdateProcess`()
BEGIN
	SELECT MAX(IFNULL(ModificationDatetime,CreationDatetime)) max_datetime
	FROM HomeOffers
	WHERE DeletionDatetime IS NULL;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetHomeOffersProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetHomeOffersProcess`(
)
BEGIN
	SELECT 
			ho.IdHomeOffer,
			ho.TargetURL,
			ho.StartDatetime,
			ho.ExpirationDatetime
	FROM 
			HomeOffers ho
	WHERE 
			ho.DeletionDatetime IS NULL
		AND ho.Active = 1
		AND CURRENT_TIMESTAMP BETWEEN ho.StartDatetime AND ho.ExpirationDatetime
	ORDER BY 
		ho.ItemOrder DESC, ho.CreationDatetime DESC; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetLockedCampaignData` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetLockedCampaignData`(
	IN LockId varchar(64),
	OUT CampaignId int,
	OUT ProfileGroupId int,
	OUT TestProfileId int
)
BEGIN

	SET CampaignId = NULL;
	SET TestProfileId = NULL;
	SET ProfileGroupId = NULL;

	SELECT 
		c.CampaignId,
		c.ProfileGroupId,
		c.TestProfileId
    INTO 
		CampaignId,
		ProfileGroupId,
		TestProfileId
	FROM Campaign c
	WHERE c.LockId = LockId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetLookBookFiltersListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetLookBookFiltersListProcess`()
BEGIN
	SELECT 	c.Code as Field, 
			c.Name as FieldString, 
			a.IdLookBookAttribute as Value, 
			a.Name as Name
	FROM 
			LookBookAttributes a 
	INNER JOIN 
			LookBookAttributeCategories c ON c.IdLookBookAttributeCategory = a.IdLookBookAttributeCategory
	WHERE 
			a.DeletionDatetime IS NULL
		AND c.DeletionDatetime IS NULL
	ORDER BY c.ItemOrder, a.ItemOrder DESC, a.Name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetLookBookItemsLastUpdateProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetLookBookItemsLastUpdateProcess`()
BEGIN
	SELECT MAX(IFNULL(ModificationDatetime,CreationDatetime)) max_datetime
	FROM LookBookAttributes
	WHERE DeletionDatetime IS NULL;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetLookBookListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetLookBookListProcess`(
	IN idsList VARCHAR(200),
	IN numFilters INT,
	IN rowOffset INT,
	IN pageSize INT
)
BEGIN
	SELECT 		aux.Colors,
				aux.Attributes as AttributeIds,
				i.IdLookBookItem,
				i.LotId,
				i.Description,
				i.Name
	FROM 
		LookBookItems i 
	LEFT JOIN 
		LookBookItemAttributes ia ON ia.IdLookBookItem = i.IdLookBookItem
	LEFT JOIN 
	(
		SELECT 	ia.IdLookBookItem, 
				GROUP_CONCAT(ia.IdLookBookAttribute) as Attributes,
				GROUP_CONCAT(CASE WHEN attr.IdLookBookAttributeCategory = 3 THEN attr.Name END) as Colors
		FROM 
				LookBookItemAttributes ia 
		LEFT JOIN 
				LookBookAttributes attr ON attr.IdLookBookAttribute = ia.IdLookBookAttribute
		GROUP BY ia.IdLookBookItem
	) aux ON aux.IdLookBookItem = i.IdLookBookItem
	WHERE
		(idsList IS NULL OR FIND_IN_SET(ia.IdLookBookAttribute, idsList))
	AND i.DeletionDatetime IS NULL
	GROUP BY i.IdLookBookItem
	HAVING COUNT(ia.IdLookBookItem) >= numFilters
	ORDER BY i.Name ASc
	LIMIT rowOffset, pageSize;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPendingGeocodingStoreProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetPendingGeocodingStoreProcess`(
	IN MaxTries INT
)
BEGIN
	SELECT * 
	FROM Stores
	WHERE GeolocationTries < MaxTries
	AND (Latitude IS NULL OR Longitude IS NULL)
	ORDER BY GeolocationTries ASC, IdStore ASC 
	LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getPermissionsByIdRoleProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `getPermissionsByIdRoleProcess`(in idRole INT)
begin
SELECT Payless.Permissions.*, Payless.Modules.Name as ModuleName FROM Payless.Permissions join Payless.Modules on Payless.Permissions.IdModule = Payless.Modules.idModule where IdRole = idRole;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPermissionsByModuleAndRoleProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetPermissionsByModuleAndRoleProcess`(in idRole INT, in moduleName varchar(255))
begin
SELECT * FROM Payless.Permissions join Payless.Modules on Permissions.IdModule = Modules.IdModule where Modules.Name = moduleName and Permissions.IdRole= idRole;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPreHomeImageProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetPreHomeImageProcess`(
)
BEGIN
	SELECT i.IdPreHomeImage , i.Active
	FROM PreHomeImages i
	WHERE i.DeletionDatetime IS NULL
	AND i.Active = 1 
	ORDER BY i.CreationDatetime DESC 
	LIMIT 1;	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetPushNotificationsRatioProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetPushNotificationsRatioProcess`(
  OUT lastRatio INT(11) ,
  OUT lastUpdateDateTime datetime ,
  OUT currentDateTime datetime 
)
BEGIN
	SET lastRatio:=(SELECT Ratio from PushNotificationsRatio ORDER BY UpdateDateTime Desc LIMIT 0,1);
 	SET lastUpdateDateTime  = (SELECT UpdateDateTime from PushNotificationsRatio ORDER BY UpdateDateTime Desc LIMIT 0,1);
	SET currentDateTime  = (SELECT NOW());
	
	-- SELECT Ratio, UpdateDateTime from PushNotificationsRatio ORDER BY UpdateDateTime Desc LIMIT 0,1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetRelatedAttributesProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetRelatedAttributesProcess`(
	IN IdLookBookItem INT
)
BEGIN
	SELECT a.IdLookBookAttribute, a.IdLookBookAttributeCategory, Name 
	FROM LookBookAttributes a
	INNER JOIN LookBookItemAttributes ia ON a.IdLookBookAttribute = ia.IdLookBookAttribute
	WHERE ia.IdLookBookItem = IdLookBookItem
	AND a.DeletionDateTime IS NULL;  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetSocialListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetSocialListProcess`(
	IN rowOffset INT,
	IN pageSize INT
)
BEGIN
	SELECT SQL_CALC_FOUND_ROWS 
			s.Source,
			s.TargetURL,
			s.ImageURL,
			s.Text,
			s.Name
	FROM 
			SocialUpdates s
	WHERE
			s.DeletionDatetime IS NULL
	ORDER BY s.SocialItemDatetime DESC, s.IdSocialUpdate DESC 
	LIMIT rowOffset, pageSize;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetUserByEmailAndHashedPasswordProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetUserByEmailAndHashedPasswordProcess`(in email varchar(45), in password varchar(255))
begin
SELECT * FROM Payless.Users where Payless.Users.Email=email and Payless.Users.Password=password;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetUserByEmailAndPasswordProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetUserByEmailAndPasswordProcess`(in email varchar(45), in password varchar(255))
begin
SELECT * FROM Payless.Users where Payless.Users.Email=email and Payless.Users.Password=md5(password);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetXtifyIds` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetXtifyIds`(IN LockId varchar(64))
BEGIN

  SELECT Xid FROM PendingNotification pn
 	WHERE pn.LockId = LockId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetXtifyMessage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `GetXtifyMessage`()
BEGIN

	DECLARE CampaignId int;
	DECLARE Now datetime;
	DECLARE LastOs char(1);
	DECLARE XIds text;
	DECLARE Message varchar(255);
	DECLARE Action varchar(255);
	DECLARE ActionLabel varchar(50);
	DECLARE LockId varchar(64);
	DECLARE Os char(1);
	DECLARE Count int;
    
  SET Now = now();
	SET Count = 0;
	SET LockId = UUID();
	SET @enabled = FALSE;
	
	
	SELECT c.CampaignId, c.Message, c.MessageAction, c.MessageActionLabel, c.LastOs
    INTO CampaignId,Message,Action,ActionLabel,LastOs 
	FROM Campaign c
	WHERE c.LockId IS NULL
	AND c.Step = 'A'
	AND c.StartDate <= Now
	AND c.EndDate >= Now
	ORDER BY c.LastUpdate
	LIMIT 1;
	
	
	-- First run will try to lock iOS pushes
	IF (CampaignId IS NOT NULL) THEN
		CASE LastOs  
		  WHEN 'i' THEN SET Os = 'a';
		  WHEN 'a' THEN SET Os = 'f';
		  ELSE SET Os = 'i';
		END CASE;

		CALL LockPendingNotifications(LockId, CampaignId, Os, Count);

		-- if First run have 0 locked pushes will try with next device (Android)
		IF (Count = 0) THEN
			CASE Os  
			  WHEN 'i' THEN SET Os = 'a';
			  WHEN 'a' THEN SET Os = 'f';
			  ELSE SET Os = 'i';
			END CASE;

			CALL LockPendingNotifications(LockId, CampaignId, Os, Count);

		END IF;
		
		-- finally if last run have 0 locked pushes will try with next device (Amazon Fire)
		IF (Count = 0) THEN
			CASE Os  
			  WHEN 'i' THEN SET Os = 'a';
			  WHEN 'a' THEN SET Os = 'f';
			  ELSE SET Os = 'i';
			END CASE;

			CALL LockPendingNotifications(LockId, CampaignId, Os, Count);

		END IF;
	END IF;
	
	IF (Count = 0) THEN
		SELECT '' Message, '' ActionData, '' ActionLabel, '' LockId, '' Os;
	ELSE 	
		SELECT IFNULL(Message,'') as Message, IFNULL(Action,'') as ActionData, IFNULL(ActionLabel,'') as ActionLabel, IFNULL(LockId,'') as LockId, IFNULL(Os,'') Os;
	END IF;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InsertIgnoreSocialUpdateProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `InsertIgnoreSocialUpdateProcess`(
	IN IdSourceItem CHAR(32),
	IN Source CHAR(2),
	IN Name VARCHAR(800) CHARACTER SET utf8mb4,
	IN Text VARCHAR(800) CHARACTER SET utf8mb4,
	IN ImageURL VARCHAR(255),
	IN TargetURL VARCHAR(255),	
	IN SocialItemDatetime Datetime,
	IN CreationDatetime Datetime
)
BEGIN
	INSERT IGNORE INTO SocialUpdates
	(IdSourceItem,Source,Name,Text,ImageURL,TargetURL,SocialItemDatetime,CreationDatetime)
	VALUES 
	(IdSourceItem,Source,Name,Text,ImageURL,TargetURL,SocialItemDatetime,CreationDatetime);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InsertOrUpdateSocialFeedAccessLogProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `InsertOrUpdateSocialFeedAccessLogProcess`(
	IN Source varchar(40),
	IN now datetime
)
BEGIN
	DECLARE id INT DEFAULT 0;
	SELECT al.IdSocialFeedAccessLog INTO id 
	FROM SocialFeedAccessLog al
	WHERE al.Source = Source;

	IF id > 0 THEN
		UPDATE SocialFeedAccessLog 
		SET ModificationDatetime = now
		WHERE IdSocialFeedAccessLog = id;
	ELSE 
		INSERT INTO SocialFeedAccessLog (Source, ModificationDatetime) 
		VALUES (Source,now);
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ListProfileGroupMembersProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `ListProfileGroupMembersProcess`(
	IN IdProfileGroup INT
)
BEGIN
	SELECT p.DeviceId, p.IdProfile, p.FirstName, p.LastName, p.ZipCode
	FROM 
		ProfileGroupMembers m
	INNER JOIN 
		Profiles p ON m.IdProfile = p.IdProfile
	WHERE
		m.IdProfileGroup = IdProfileGroup;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ListProfileGroupMembersTemporaryProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `ListProfileGroupMembersTemporaryProcess`()
BEGIN
	SELECT p.IdProfile, p.DeviceId, p.FirstName, p.LastName, p.ZipCode  
	FROM ProfileGroupMembersTemporary tmp
	INNER JOIN Profiles p ON p.IdProfile = tmp.IdProfile;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LockPendingNotifications` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `LockPendingNotifications`(IN LockId varchar(64), 
       IN CampaignId int,
       IN Os char(1),
       OUT Count int)
BEGIN

      UPDATE PendingNotification pn SET pn.LockId = LockId, pn.LockDate = CURDATE() 
      WHERE pn.CampaignId = CampaignId 
            AND pn.LockId IS NULL
            AND pn.Os = Os
      LIMIT 1000;

    SELECT row_count() INTO Count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LookBookAttributeListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `LookBookAttributeListProcess`(
	IN searchName VARCHAR(600),
	IN rowOffset INT,
	IN pageSize INT,
	IN attributeCategoryId INT
)
BEGIN

	SELECT SQL_CALC_FOUND_ROWS 
			COUNT(ia.IdLookBookAttribute) as ItemsCount,
			a.IdLookBookAttribute,
			a.IdLookBookAttributeCategory,
			a.ItemOrder,
			a.Name
	FROM 
			LookBookAttributes a
	LEFT JOIN
			LookBookItemAttributes ia ON a.IdLookBookAttribute = ia.IdLookBookAttribute
	LEFT JOIN 
			LookBookItems i ON i.IdLookBookItem = ia.IdLookBookItem
	WHERE
		a.DeletionDatetime IS NULL 
		AND (searchName IS NULL OR a.Name LIKE searchName)
		AND (attributeCategoryId IS NULL OR a.IdLookBookAttributeCategory = attributeCategoryId)
	GROUP BY a.IdLookBookAttribute
	ORDER BY a.ItemOrder DESC, a.Name ASC
	LIMIT rowOffset, pageSize;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LookBookItemListProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `LookBookItemListProcess`(
	IN searchName VARCHAR(600),
	IN idsList VARCHAR(200),
	IN numFilters INT,
	IN rowOffset INT,
	IN pageSize INT
)
BEGIN

	SELECT SQL_CALC_FOUND_ROWS 
			GROUP_CONCAT(attr.Name) as Colors,	
			GROUP_CONCAT(ia.IdLookBookAttribute) as AttributeIds,
			i.IdLookBookItem,
			i.LotId,
			i.Description,
			i.Name
	FROM 
			LookBookItems i
	LEFT JOIN
			LookBookItemAttributes ia ON i.IdLookBookItem = ia.IdLookBookItem
	LEFT JOIN 
			LookBookAttributes attr ON attr.IdLookBookAttribute = ia.IdLookBookAttribute AND attr.IdLookBookAttributeCategory = 3 
	WHERE
			(idsList IS NULL OR FIND_IN_SET(ia.IdLookBookAttribute, idsList))
		AND i.DeletionDatetime IS NULL 
		AND (searchName IS NULL OR i.Name LIKE searchName)
	GROUP BY i.IdLookBookItem
	HAVING COUNT(ia.IdLookBookItem) >= numFilters
	ORDER BY i.Name ASC
	LIMIT rowOffset, pageSize;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PrepareCampaigns` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `PrepareCampaigns`()
BEGIN

	DECLARE LockId varchar(100);
	DECLARE Now datetime;
	DECLARE LockExpireDate datetime;

	DECLARE CampaignId int;
	DECLARE ProfileGroupId int;
	DECLARE TestProfileId int;

	DECLARE CONTINUE HANDLER FOR NOT FOUND BEGIN END;

	SET SESSION group_concat_max_len = 102400;

	CALL UpdatePendingGroupsProcess();

	SET LockId = UUID();
	SET Now = now();
	SET LockExpireDate = subdate(Now, Interval 30 Day);
	
	SET @enabled = 1;

	-- All Campaigns with expired Lock -------------------------------------------------------------------------------
	UPDATE Campaign c
	SET c.LockId = NULL,
	  c.LockDate = NULL
	WHERE c.LockDate < LockExpireDate
	AND c.LockId IS NOT NULL;

	-- All Campaigns expires ----------------------------------------------------------------------------------------
	UPDATE Campaign c
	SET c.Step = 'D'
	WHERE c.Step = 'A'
	AND c.ContinueNextDay = 0 AND c.EndDate < Now;

	-- All campaign with today timeframe expired
	UPDATE Campaign c
	SET c.StartDate = ADDDATE(StartDate, Interval 1 DAY),
	EndDate = ADDDATE(EndDate,Interval 1 DAY )
	WHERE c.LockId IS NULL
	AND c.Step <> 'T'
	AND c.ContinueNextDay = 1
	AND c.EndDate < Now;

	-- All Campaigns - Active but empty -----------------------------------------------------------------------------
	UPDATE Campaign c
	Set c.Step = 'D'
	WHERE c.LockId IS NULL
	AND   c.Step = 'A'
	AND c.CampaignId NOT IN (
		SELECT pn.CampaignId
		FROM PendingNotification pn
		WHERE pn.CampaignId = c.CampaignId
		AND (pn.LockId IS NULL
		  OR pn.LockDate > LockExpireDate));

	-- First Campaign - Discarded -----------------------------------------------------------------------------------
	UPDATE Campaign c
	SET c.LockId = LockId,
	c.LockDate = Now
	WHERE c.LockId IS NULL
	AND c.Step = 'D'
	limit 1;

	UPDATE PendingNotification pn
	SET pn.LockId = LockId,
	pn.LockDate = Now
	WHERE
		pn.LockId IS NULL
		AND pn.CampaignId IN (SELECT c.CampaignId Id
							  FROM Campaign c
							  WHERE c.LockId = LockId
							  AND c.CampaignId = pn.CampaignId );

	INSERT INTO FailedNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`, `Error`)
	SELECT pn.CampaignId, pn.ProfileId, pn.Xid, pn.Os, 'CampaignExpired'
	FROM PendingNotification pn
	WHERE pn.LockId = LockId;

	DELETE FROM pn USING PendingNotification AS pn
	WHERE pn.LockId = LockId;

	SELECT row_count() INTO @Matched;

	UPDATE Campaign c
	SET c.Fail = c.Fail + @Matched
	WHERE c.LockId = LockId;

	UPDATE PendingNotification pn
	SET pn.LockId = LockId,
	pn.LockDate = Now
	WHERE pn.CampaignId IN (SELECT c.CampaignId
							FROM Campaign c
							WHERE c.LockId IS NOT NULL
							AND pn.CampaignId = c.CampaignId);

	INSERT INTO FailedNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`, `Error`)
	SELECT pn.CampaignId, pn.ProfileId, pn.Xid, pn.Os, 'LockExpired'
	FROM PendingNotification pn
	WHERE pn.LockId = LockId;

	DELETE FROM pn USING PendingNotification AS pn
	WHERE pn.LockId = LockId;

	SELECT row_count() INTO @Matched;

	UPDATE Campaign c
	SET c.Step='T',
	c.Fail = Fail + @Matched,
	c.LockId = NULL,
	c.LockDate = NULL
	WHERE c.LockId = LockId;

	-- First Campaign - Pending Query to start now ------------------------------------------------------------------
	UPDATE Campaign c
	SET c.LockId = LockId,
	  c.LockDate = Now
	WHERE c.LockId IS NULL
		AND c.StartDate <= Now
		AND c.Step = 'Q'
	LIMIT 1;

	SET CampaignId = NULL;
	SET ProfileGroupId = NULL;
	SET TestProfileId = NULL;

	CALL GetLockedCampaignData(LockId,CampaignId,ProfileGroupId,TestProfileId);

	IF (CampaignId IS NOT NULL) THEN

		DELETE FROM pn USING PendingNotification AS pn
		WHERE pn.CampaignId = CampaignId; -- clear previous data for a campaign locked with expired lock date
		/*
		CALL GetFilteredProfiles(Device, Os, Target, BeginDate, EndDate, LocationX, LocationY, LocationDistanceMiles, TheatreIds, TestProfileIds,@tmpTableName);
		*/
		SET @CampaignId = CampaignId;
		SET @ProfileGroupId = ProfileGroupId;
		SET @TestProfileId = TestProfileId;		
		/*
		SET @s = CONCAT('INSERT INTO PendingNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`)
						SELECT @CampaignId,
						t.Id,
						t.XId,
						t.DeviceType
						FROM ',@tmpTableName,' t
						WHERE XId > \'\'
						AND OffersNotifications = 1');

		*/
		IF(@ProfileGroupId IS NOT NULL) THEN 

			INSERT INTO PendingNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`) 
			SELECT 		@CampaignId, 
						p.IdProfile,
						p.Xid,
						MapIdDeviceOsToCharacterCode(p.IdDeviceOs)
			FROM 		ProfileGroupMembers pgm
			INNER JOIN 	Profiles p ON p.IdProfile = pgm.IdProfile
			WHERE 		pgm.IdProfileGroup = @ProfileGroupId
			AND 		p.PushNotification = 1 
			AND 		(p.Xid IS NOT NULL AND p.Xid > '');

			SET @Matched = row_count();

			IF(@TestProfileId IS NOT NULL) THEN 
				/*	INSERT testing profile, we're using ignore because the test user could have been inserted in the previous step */
				INSERT IGNORE INTO 	PendingNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`) 
				SELECT 	@CampaignId, 
						p.IdProfile,
						p.Xid,
						MapIdDeviceOsToCharacterCode(p.IdDeviceOs)
				FROM 	Profiles p
				WHERE 	p.IdProfile= @TestProfileId
				AND 	p.PushNotification = 1 
				AND 	(p.Xid IS NOT NULL AND p.Xid > '');
				
				SET @TestUserMatched = row_count();

				SET @Matched = @Matched + @TestUserMatched; 

			END IF;
			
		ELSE 
			/*	 No ProfileGroupId, so we add all the profiles */
			INSERT INTO PendingNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`) 
			SELECT		@CampaignId, 
						p.IdProfile,
						p.Xid,
						MapIdDeviceOsToCharacterCode(p.IdDeviceOs)
			FROM 		Profiles p 
			WHERE 		p.PushNotification = 1 
			AND 		(p.Xid IS NOT NULL AND p.Xid > '');
			
			SET @Matched = row_count();

		END IF;
		

		UPDATE Campaign c
		SET c.Total = @Matched,
		c.Success = 0,
		c.Fail = 0,
		c.Step = 'A',
		c.LockId = NULL,
		c.LockDate = NULL,
		c.FirstExecution = Now
		WHERE c.CampaignId = CampaignId;
	END IF;

	-- First Campaign - Pending Estimation --------------------------------------------------------------------------
	UPDATE Campaign c
	SET c.LockId = LockId,
	  c.LockDate = Now
	WHERE c.LockId IS NULL
	AND c.Step = 'E'
	limit 1;

	/*
	CALL GetLockedCampaignData(LockId,Device,Os,Target,BeginDate,EndDate,LocationX,LocationY,LocationDistanceMiles,TheatreIds,TestProfileIds,UploadToken,CampaignId);
	call debug_msg(@enabled, (select concat_ws('',"CampaignId:", CampaignId)));
	*/
	SET CampaignId = NULL;
	SET ProfileGroupId = NULL;
	SET TestProfileId = NULL;

	CALL GetLockedCampaignData(LockId,CampaignId,ProfileGroupId,TestProfileId);

	IF (CampaignId IS NOT NULL)
	THEN

		/*
		CALL GetFilteredProfiles(Device, Os, Target, BeginDate, EndDate, LocationX, LocationY, LocationDistanceMiles, TheatreIds, TestProfileIds,@tmpTableName);
		SET @s = CONCAT('select count(*) into @cant from ',@tmpTableName,' WHERE XId > \'\' AND OffersNotifications = 1');
		*/

		SET @CampaignId = CampaignId;
		SET @ProfileGroupId = ProfileGroupId;
		SET @TestProfileId = TestProfileId;	
		
		SELECT 	COUNT(*) 
		INTO 	@cant 
		FROM 	PendingNotification pn
		WHERE 	pn.CampaignId = @CampaignId;

		/*
		SET @s = CONCAT('SELECT COUNT(*) INTO @cant 
						FROM 
							Profiles p 
						LEFT JOIN 
							ProfileGroupMembers pgm ON p.IdProfile = pgm.IdProfile
						WHERE 
							p.PushNotification = 1 
							AND p.Xid IS NOT NULL AND p.Xid > \'\'
							AND @ProfileGroupId IS NULL 
							OR (
								pgm.IdProfileGroup = @ProfileGroupId OR p.IdProfile = @TestProfileId
							)
						GROUP BY p.IdProfile');
		
		PREPARE stmt FROM @s;
		EXECUTE stmt;
		*/

		UPDATE Campaign c
		SET c.Total = @cant,
		c.Success = 0,
		c.Fail = 0,
		c.Step = 'Q',
		c.LockId = NULL,
		c.LockDate = NULL
		WHERE c.CampaignId = CampaignId;
	END IF;

	-- First Campaign - Uploading -----------------------------------------------------------------------------------
	UPDATE Campaign c
	SET c.LockId = LockId,
	  c.LockDate = Now
	WHERE c.LockId IS NULL
	AND c.Step = 'U'
	LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SetPushNotificationsRatioProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `SetPushNotificationsRatioProcess`(
  IN newRatio INT(11) 
)
BEGIN
	SET @lastRatio:=(SELECT Ratio from PushNotificationsRatio ORDER BY UpdateDateTime Desc LIMIT 0,1);
	IF (@lastRatio<>newRatio) THEN
		INSERT INTO PushNotificationsRatio (`Ratio`,`UpdateDateTime`) VALUES (newRatio, NOW());
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SetXtifyMessageResult` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `SetXtifyMessageResult`(IN `LockId` varchar(64), IN `HttpCode` int, IN `JobId` varchar(50), IN `XtifyTimeResponseContainer` varchar(50), IN `XtifyTimeResponseMonitoring` boolean)
BEGIN

	DECLARE Now datetime;
	DECLARE CampaignId int;
	DECLARE Os char(1);
    DECLARE Count int;
  
	SELECT now() into Now;

	SELECT pn.CampaignId,pn.Os into CampaignId,Os FROM PendingNotification pn WHERE pn.LockId = LockId limit 1;

	IF (HttpCode = 200 or HttpCode = 202) THEN
		INSERT INTO SucceedNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`, `JobId`)
		SELECT pn.CampaignId, pn.ProfileId, pn.Xid, pn.Os, JobId FROM PendingNotification pn
		WHERE pn.LockId = LockId;
    
		DELETE FROM pn USING PendingNotification AS pn
		WHERE pn.LockId = LockId;

		SELECT row_count() INTO Count;
    
		UPDATE Campaign c SET c.Success = c.Success + Count, c.LastUpdate = Now, c.LastOs = Os 
		WHERE c.CampaignId = CampaignId;
	ELSE 
		INSERT INTO FailedNotification (`CampaignId`, `ProfileId`, `Xid`, `Os`, `Error`, `ErrorDetail`)
		SELECT pn.CampaignId, pn.ProfileId, pn.Xid, pn.Os, 'XtifyHttp', concat('Http code: ',HttpCode)
		FROM PendingNotification pn
		WHERE pn.LockId = LockId;
		
		DELETE FROM pn USING PendingNotification AS pn
		WHERE pn.LockId = LockId;
		
		SELECT row_count() INTO Count;
    
		UPDATE Campaign c SET c.Fail = c.Fail + Count, c.LastUpdate = Now, c.LastOs = Os 
		WHERE c.CampaignId = CampaignId;
	END IF;

	IF (XtifyTimeResponseMonitoring) THEN
		INSERT INTO XtifyTimeLog(ExecStart, ExecEnd, HTTPCode)
		SELECT SUBSTRING_INDEX(XtifyTimeResponseContainer, '|', 1) as ExecStart, SUBSTRING_INDEX(XtifyTimeResponseContainer, '|', -1) as ExecEnd, HttpCode as HTTPCode;
	END IF;
                   
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `StoreSearchProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `StoreSearchProcess`(
	IN Longitude double /* X axis */,
	IN Latitude double	/* Y axix */,
	IN LocationDistanceMiles double,
	IN ZipCode VARCHAR(10),
	IN City VARCHAR(100),
	IN State CHAR(2),
	IN rowOffset INT,
	IN pageSize INT
)
BEGIN
	SELECT SQL_CALC_FOUND_ROWS		
		s.IdStore,
		s.Address,
		s.Name,
		s.City,
		s.ZipCode,
		st.Name as State,
		s.ContactNumber,
		s.Latitude,
		s.Longitude,
		s.StoreHours
	FROM 
		Stores s
	INNER JOIN 
		States st ON st.IdState = s.IdState
	WHERE 
		1
		AND 
		(
			ZipCode IS NULL OR s.ZipCode = ZipCode
		) 
		AND
		(
			State IS NULL OR City IS NULL OR 
			(
				s.IdState = State 
				AND 
				s.City LIKE CONCAT('%',City,'%')
			)
		)
		AND
		 (Longitude IS NULL OR Latitude IS NULL OR LocationDistanceMiles IS NULL OR
		  (s.Latitude BETWEEN Latitude - (LocationDistanceMiles/3959) * 180 / PI() AND Latitude + (LocationDistanceMiles/3959) * 180 / PI() AND
		   s.Longitude BETWEEN Longitude - (LocationDistanceMiles/3959/COS(Latitude * PI() / 180)) * 180 / PI() AND Longitude + (LocationDistanceMiles/3959/COS(Latitude * PI() / 180)) * 180 / PI() AND
		   ACOS(
			SIN(Latitude * PI() / 180)*SIN(s.Latitude * PI() / 180) +
			COS(Latitude * PI() / 180)*COS(s.Latitude * PI() / 180)*COS(s.Longitude * PI() / 180 - Longitude * PI() / 180)
		   )*3959 < LocationDistanceMiles
		  )
		 )
		
		AND	s.DeletionDatetime IS NULL
	
	ORDER BY s.IdStore
	LIMIT rowOffset, pageSize;


-- Location Filter
/*
 AND
 (LocationX IS NULL OR LocationY IS NULL OR LocationDistanceMiles IS NULL OR
  (p.LocationY BETWEEN LocationY - (LocationDistanceMiles/3959) * 180 / PI() AND LocationY + (LocationDistanceMiles/3959) * 180 / PI() AND
   p.LocationX BETWEEN LocationX - (LocationDistanceMiles/3959/COS(LocationY * PI() / 180)) * 180 / PI() AND LocationX + (LocationDistanceMiles/3959/COS(LocationY * PI() / 180)) * 180 / PI() AND
   ACOS(
    SIN(LocationY * PI() / 180)*SIN(p.LocationY * PI() / 180) +
    COS(LocationY * PI() / 180)*COS(p.LocationY * PI() / 180)*COS(p.LocationX * PI() / 180 - LocationX * PI() / 180)
   )*3959 < LocationDistanceMiles
  )
 )

*/
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TruncateProfileGroupMembersTemporaryProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `TruncateProfileGroupMembersTemporaryProcess`()
BEGIN
	TRUNCATE TABLE ProfileGroupMembersTemporary;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TruncateStoresProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `TruncateStoresProcess`()
BEGIN
	TRUNCATE TABLE Stores;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdatePendingGroupsProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `UpdatePendingGroupsProcess`()
BEGIN

	DECLARE vIdProfileGroup INT;
	DECLARE vGeolocation INT;
	DECLARE vPushNotification INT;
	DECLARE vSMSNotification INT;
	DECLARE vEmailNotification INT;
	DECLARE vPostalNotification INT;
	DECLARE vMinAge INT;

	DECLARE vZipcodesList VARCHAR(1000);
	DECLARE vDeviceOsList VARCHAR(1000);
	DECLARE vStatesList VARCHAR(1000);

	DECLARE vMatches INT;

	DECLARE vLockAcquired INT;
	DECLARE vLockId CHAR(36);
	DECLARE vNow datetime;
	DECLARE vLockExpireDate datetime;

	SET vIdProfileGroup = 0;

	SET vZipcodesList = NULL;
	SET vDeviceOsList = NULL;
	SET vStatesList = NULL;

	SET vMatches = 0;

	SET vLockAcquired = 0;
	SET vLockId = UUID();

	SET vNow = now();
	SET vLockExpireDate = subdate(vNow, Interval 10 Minute);

	/*	remove expired locks */
	UPDATE 	ProfileGroups pg
	SET 	pg.LockId = NULL,
			pg.LockDate = NULL
	WHERE 	pg.LockDate < vLockExpireDate
	AND 	pg.LockId IS NOT NULL;

	/*	find a candidate */
	SELECT 
			pg.IdProfileGroup,
			pg.Geolocation,
			pg.PushNotification,
			pg.SMSNotification,
			pg.EmailNotification,
			pg.PostalNotification,
			pg.MinAge
	INTO 
			vIdProfileGroup,
			vGeolocation,
			vPushNotification,
			vSMSNotification,	
			vEmailNotification,
			vPostalNotification,
			vMinAge
	FROM 
			ProfileGroups pg
	WHERE 
			pg.IsFixed = 0 
		AND 
			pg.MembersComputingDatetime IS NULL
		AND 
			pg.LockId IS NULL
	LIMIT 1;

	SELECT GROUP_CONCAT(ZipCode) INTO vZipcodesList FROM ProfileGroupZipcodes WHERE IdProfileGroup = vIdProfileGroup;
	SELECT GROUP_CONCAT(IdDeviceOs) INTO vDeviceOsList FROM ProfileGroupDeviceOs WHERE IdProfileGroup = vIdProfileGroup;
	SELECT GROUP_CONCAT(IdState) INTO vStatesList FROM ProfileGroupStates WHERE IdProfileGroup = vIdProfileGroup;

	/*
	SELECT vIdProfileGroup,vGeolocation,vPushNotification,vSMSNotification,vEmailNotification,vPostalNotification,vMinAge,vZipcodesList,vDeviceOsList,vStatesList;

	SET vIdProfileGroup = 0;
	*/

	IF vIdProfileGroup > 0 THEN 

		/*	candidate found, try to acquire lock	*/
		UPDATE 	ProfileGroups pg
		SET 	pg.LockId = vLockId,
				pg.LockDate = vNow
		WHERE 	pg.IdProfileGroup = vIdProfileGroup
		AND 	pg.LockId IS NULL;
		

		/*	verify that we got the lock	*/
		SELECT 	COUNT(*)
		INTO 	vLockAcquired
		FROM 	ProfileGroups pg
		WHERE 	pg.LockId = vLockId
		AND 	pg.IdProfileGroup = vIdProfileGroup;

		
		IF(vLockAcquired = 1) THEN
			/*	we got the lock, let's create the profile members	*/

			DELETE FROM ProfileGroupMembers WHERE IdProfileGroup = vIdProfileGroup;
			
			CALL CreateProfileGroupMembersProcess(	vIdProfileGroup,
													vGeolocation,
													vPushNotification,
													vSMSNotification,
													vEmailNotification,		
													vPostalNotification,
													vMinAge,
													vZipcodesList,
													vDeviceOsList,
													vStatesList);

			SELECT COUNT(*) INTO vMatches FROM ProfileGroupMembers WHERE IdProfileGroup = vIdProfileGroup;

			/*	we're done, let's update members count, done flag and remove lock */
			UPDATE 	ProfileGroups pg
			SET 	pg.MembersComputingDatetime = NOW(),
					pg.MembersCount = vMatches,
					pg.LockId = NULL,
					pg.LockDate = NULL 
			WHERE	pg.IdProfileGroup =  vIdProfileGroup;

		END IF;
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateSocialFeedAccessLogProcess` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`DBMUser`@`%` PROCEDURE `UpdateSocialFeedAccessLogProcess`(
	IN Source varchar(40),
	IN now datetime
)
BEGIN
	DECLARE id INT DEFAULT 0;
	SELECT al.IdSocialFeedAccessLog INTO id 
	FROM SocialFeedAccessLog al
	WHERE al.Source = Source;

	IF id > 0 THEN
		UPDATE SocialFeedAccessLog 
		SET ModificationDatetime = now
		WHERE IdSocialFeedAccessLog = id;
	ELSE 
		INSERT INTO SocialFeedAccessLog (Source, ModificationDatetime) 
		VALUES (Source,now);
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-03 22:36:34
