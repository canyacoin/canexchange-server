-- --------------------------------------------------------
-- Host:                         35.226.67.200
-- Server version:               5.7.14-google-log - (Google)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.5.0.5295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for canya
CREATE DATABASE IF NOT EXISTS `canya` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `canya`;

-- Dumping structure for table canya.DATABASECHANGELOG
CREATE TABLE IF NOT EXISTS `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table canya.DATABASECHANGELOG: ~0 rows (approximately)
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;

-- Dumping structure for table canya.DATABASECHANGELOGLOCK
CREATE TABLE IF NOT EXISTS `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table canya.DATABASECHANGELOGLOCK: ~1 rows (approximately)
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
	(1, b'0', NULL, NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;

-- Dumping structure for table canya.jhi_authority
CREATE TABLE IF NOT EXISTS `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table canya.jhi_authority: ~2 rows (approximately)
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` (`name`) VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;

-- Dumping structure for table canya.jhi_persistent_audit_event
CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table canya.jhi_persistent_audit_event: ~3 rows (approximately)
/*!40000 ALTER TABLE `jhi_persistent_audit_event` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
	(1, 'admin', '2018-10-18 11:46:50', 'AUTHENTICATION_SUCCESS'),
	(2, 'admin', '2018-10-18 12:32:25', 'AUTHENTICATION_SUCCESS'),
	(3, 'admin', '2018-10-18 07:41:29', 'AUTHENTICATION_SUCCESS');
/*!40000 ALTER TABLE `jhi_persistent_audit_event` ENABLE KEYS */;

-- Dumping structure for table canya.jhi_persistent_audit_evt_data
CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table canya.jhi_persistent_audit_evt_data: ~0 rows (approximately)
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` ENABLE KEYS */;

-- Dumping structure for table canya.jhi_user
CREATE TABLE IF NOT EXISTS `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table canya.jhi_user: ~4 rows (approximately)
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
	(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', '', b'1', 'en', NULL, NULL, 'system', '2018-09-27 15:12:40', NULL, 'system', NULL),
	(2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', b'1', 'en', NULL, NULL, 'system', '2018-09-27 15:12:40', NULL, 'system', NULL),
	(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'en', NULL, NULL, 'system', '2018-09-27 15:12:40', NULL, 'system', NULL),
	(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'en', NULL, NULL, 'system', '2018-09-27 15:12:40', NULL, 'system', NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;

-- Dumping structure for table canya.jhi_user_authority
CREATE TABLE IF NOT EXISTS `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table canya.jhi_user_authority: ~5 rows (approximately)
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
	(1, 'ROLE_ADMIN'),
	(3, 'ROLE_ADMIN'),
	(1, 'ROLE_USER'),
	(3, 'ROLE_USER'),
	(4, 'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
 

-- Dumping structure for table canya.tokenerc20
CREATE TABLE IF NOT EXISTS `tokenerc20` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` longtext,
  `created_date` datetime(3) DEFAULT NULL,
  `decimals` longtext,
  `last_modified_by` longtext,
  `last_modified_date` datetime(3) DEFAULT NULL,
  `name` longtext,
  `symbol` longtext,
  `tokenid` int(11) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- Dumping data for table canya.tokenerc20: ~92 rows (approximately)
/*!40000 ALTER TABLE `tokenerc20` DISABLE KEYS */;
INSERT INTO `tokenerc20` (`id`, `address`, `created_date`, `decimals`, `last_modified_by`, `last_modified_date`, `name`, `symbol`, `tokenid`, `created_by`, `reset_date`) VALUES
	(1, '0x5102791ca02fc3595398400bfe0e33d7b6c82267', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:25:43.469', 'FoldingCoin', 'LDC', 606, '', NULL),
	(2, '0xe0b7927c4af23765cb51314a0e0521a9645f0e2a', '2018-09-12 13:25:27.104', '9', 'anonymousUser', '2018-09-12 13:25:58.467', 'DigixDAO', 'DGD', 1229, '', NULL),
	(3, '0x1460a58096d80a50a2f1f956dda497611fa4f165', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:01.502', 'RichCoin', 'CHX', 1269, '', NULL),
	(4, '0x667088b212ce3d06a1b553a7221e1fd19000d9af', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:07.962', 'Wings', 'WINGS', 1500, '', NULL),
	(5, '0xe7775a6e9bcf904eb39da2b68c5efb4f9360e08c', '2018-09-12 13:25:27.104', '6', 'anonymousUser', '2018-09-12 13:26:14.178', 'TaaS', 'TAAS', 1592, '', NULL),
	(6, '0x08711d3b02c8758f2fb3ab4e80228418a7f8e39c', '2018-09-12 13:25:27.104', '0', 'anonymousUser', '2018-09-12 13:26:17.145', 'Edgeless', 'EDG', 1596, '', NULL),
	(7, '0x607f4c5bb672230e8672085532f7e901544a7375', '2018-09-12 13:25:27.104', '9', 'anonymousUser', '2018-09-12 13:26:24.681', 'iExec RLC', 'RLC', 1637, '', NULL),
	(8, '0xcb94be6f13a1182e4a4b6140cb7bf2025d28e41b', '2018-09-12 13:25:27.104', '6', 'anonymousUser', '2018-09-12 13:26:28.146', 'WeTrust', 'TRST', 1638, '', NULL),
	(9, '0x6810e776880c02933d47db1b9fc05908e5386b96', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:31.348', 'Gnosis', 'GNO', 1659, '', NULL),
	(10, '0xaaaf91d9b90df800df4f55c205fd6989c977e73a', '2018-09-12 13:25:27.104', '8', 'anonymousUser', '2018-09-12 13:26:34.390', 'TokenCard', 'TKN', 1660, '', NULL),
	(11, '0x0d8775f648430679a709e98d2b0cb6250d2887ef', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:41.065', 'Basic Attention Token', 'BAT', 1697, '', NULL),
	(12, '0x744d70fdbe2ba4cf95131626614a1763df805b9e', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:49.667', 'Status', 'SNT', 1759, '', NULL),
	(13, '0xf433089366899d83a9f26a773d59ec7ecf30355e', '2018-09-12 13:25:27.104', '8', 'anonymousUser', '2018-09-12 13:26:52.766', 'Metal', 'MTL', 1788, '', NULL),
	(14, '0xd26114cd6ee289accf82350c8d8487fedb8a0c07', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:26:58.603', 'OmiseGO', 'OMG', 1808, '', NULL),
	(15, '0xe3818504c1b32bf1557b16c238b2e01fd3149c17', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:08.199', 'Pillar', 'PLR', 1834, '', NULL),
	(16, '0xb8c77482e45f1f44de1745f52c74426c631bdd52', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:14.657', 'Binance Coin', 'BNB', 1839, '', NULL),
	(17, '0xea1f346faf023f974eb5adaf088bbcdf02d761f4', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:17.853', 'Blocktix', 'TIX', 1873, '', NULL),
	(18, '0x5d60d8d7ef6d37e16ebabc324de3be57f135e0bc', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:26.751', 'MyBit', 'MYB', 1902, '', NULL),
	(19, '0x0e0989b1f9b8a38983c2ba8053269ca62ec9b195', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:27:32.530', 'Po.et', 'POE', 1937, '', NULL),
	(20, '0x0f5d2fb29fb7d3cfee444a200298f468908cc942', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:35.524', 'Decentraland', 'MANA', 1966, '', NULL),
	(21, '0xf8e386eda857484f5a12e4b5daa9984e06e73705', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:38.949', 'Indorse Token', 'IND', 1967, '', NULL),
	(22, '0xd7631787b4dcc87b1254cfd1e5ce48e96823dee8', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:27:41.929', 'Sociall', 'SCL', 1969, '', NULL),
	(23, '0xdf6ef343350780bf8c3410bf062e0c015b1dd671', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:27:45.222', 'Blackmoon', 'BMC', 1976, '', NULL),
	(24, '0x3d1ba9be9f66b8ee101911bc36d3fb562eac2244', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:27:57.508', 'Rivetz', 'RVT', 1991, '', NULL),
	(25, '0x9214ec02cb71cba0ada6896b8da260736a67ab10', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:06.749', 'REAL', 'REAL', 2030, '', NULL),
	(26, '0x2daee1aa61d60a252dc80564499a69802853583a', '2018-09-12 13:25:27.105', '4', 'anonymousUser', '2018-09-12 13:28:10.017', 'Authorship', 'ATS', 2051, '', NULL),
	(27, '0x4ceda7906a5ed2179785cd3a40a69ee8bc99c466', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:28:13.297', 'Aion', 'AION', 2062, '', NULL),
	(28, '0x9af4f26941677c706cfecf6d3379ff01bb85d5ab', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:28:16.538', 'DomRaider', 'DRT', 2070, '', NULL),
	(29, '0x8f8221afbb33998d8584a2b05749ba73c37a938a', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:19.528', 'Request Network', 'REQ', 2071, '', NULL),
	(30, '0x595832f8fc6bf59c85c527fec3740a1b7a361269', '2018-09-12 13:25:27.105', '6', 'anonymousUser', '2018-09-12 13:28:31.991', 'Power Ledger', 'POWR', 2132, '', NULL),
	(31, '0x0cf0ee63788a0849fe5297f3407f701e122cc023', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:34.632', 'Streamr DATAcoin', 'DATA', 2143, '', NULL),
	(32, '0x9b68bfae21df5a510931a262cecf63f41338f264', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:37.608', 'DecentBet', 'DBET', 2175, '', NULL),
	(33, '0x340d2bde5eb28c1eed91b2f790723e3b160613b7', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:40.272', 'BLOCKv', 'VEE', 2223, '', NULL),
	(34, '0xf04a8ac553fcedb5ba99a64799155826c136b0be', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:43.638', 'Flixxo', 'FLIXX', 2231, '', NULL),
	(35, '0x1b22c32cd936cb97c28c5690a0695a82abf688e6', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:46.896', 'MyWish', 'WISH', 2236, '', NULL),
	(36, '0x419c4db4b9e25d6db2ad9691ccb832c8d9fda05e', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:53.487', 'Dragonchain', 'DRGN', 2243, '', NULL),
	(37, '0x27f610bf36eca0939093343ac28b1534a721dbb4', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:28:56.485', 'WandX', 'WAND', 2269, '', NULL),
	(38, '0xc5bbae50781be1669306b9e001eff57a2957b09d', '2018-09-12 13:25:27.105', '5', 'anonymousUser', '2018-09-12 13:29:02.755', 'Gifto', 'GTO', 2289, '', NULL),
	(39, '0xd0a4b8946cb52f0661273bfbc6fd0e0c75fc6433', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:29:06.058', 'Storm', 'STORM', 2297, '', NULL),
	(40, '0xbf2179859fc6d5bee9bf9158632dc51678a4100e', '2018-09-12 13:25:27.105', '18', 'anonymousUser', '2018-09-12 13:29:09.308', 'aelf', 'ELF', 2299, '', NULL),
	(41, '0x39bb259f66e1c59d5abef88375979b4d20d98022', '2018-09-12 13:25:27.105', '8', 'anonymousUser', '2018-09-12 13:29:11.951', 'WAX', 'WAX', 2300, '', NULL),
	(42, '0x89d24a6b4ccb1b6faa2625fe562bdd9a23260359', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:14.966', 'Dai', 'DAI', 2308, '', NULL),
	(43, '0x68d57c9a1c35f63e2c83ee8e49a64e9d70528d25', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:17.617', 'SIRIN LABS Token', 'SRN', 2313, '', NULL),
	(44, '0x1d462414fe14cf489c7a21cac78509f4bf8cd7c0', '2018-09-12 13:25:27.106', '6', 'anonymousUser', '2018-09-12 13:29:23.959', 'CanYaCoin', 'CAN', 2343, '', NULL),
	(45, '0x814e0908b12a99fecf5bc101bb5d0b8b5cdf7d26', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:27.148', 'Measurable Data Token', 'MDT', 2348, '', NULL),
	(46, '0x1063ce524265d5a3a624f4914acd573dd89ce988', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:35.833', 'Aigang', 'AIX', 2367, '', NULL),
	(47, '0x80bc5512561c7f85a3a9508c7df7901b370fa1df', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:39.031', 'Trade Token', 'TIO', 2373, '', NULL),
	(48, '0x6745fab6801e376cd24f03572b9c9b0d4edddccf', '2018-09-12 13:25:27.106', '8', 'anonymousUser', '2018-09-12 13:29:42.065', 'Sense', 'SENSE', 2402, '', NULL),
	(49, '0x3a92bd396aef82af98ebc0aa9030d25a23b11c6b', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:47.745', 'Tokenbox', 'TBX', 2452, '', NULL),
	(50, '0x37e8789bb9996cac9156cd5f5fd32599e6b91289', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:50.388', 'AidCoin', 'AID', 2462, '', NULL),
	(51, '0x607f4c5bb672230e8672085532f7e901544a7375', '2018-09-12 13:25:27.106', '9', 'anonymousUser', '2018-09-12 13:29:53.394', 'Garlicoin', 'RLC', 2475, '', NULL),
	(52, '0x9e88613418cf03dca54d6a2cf6ad934a78c7a17a', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:29:59.719', 'Swarm', 'SWM', 2506, '', NULL),
	(53, '0xe8a1df958be379045e2b46a31a98b93a2ecdfded', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:02.762', 'EtherSportz', 'ESZ', 2509, '', NULL),
	(54, '0x83cee9e086a77e492ee0bb93c2b0437ad6fdeccc', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:06.006', 'GoldMint', 'MNTP', 2513, '', NULL),
	(55, '0x9c23d67aea7b95d80942e3836bcdf7e708a747c2', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:09.677', 'LOCIcoin', 'LOCI', 2518, '', NULL),
	(56, '0x5e3346444010135322268a4630d2ed5f8d09446c', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:13.176', 'LockTrip', 'LOC', 2287, '', NULL),
	(57, '0xbc86727e770de68b1060c91f6bb6945c73e10388', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:25.616', 'Ink Protocol', 'XNK', 2549, '', NULL),
	(58, '0xc72fe8e3dd5bef0f9f31f259399f301272ef2a2d', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:29.166', 'Insights Network', 'INSTAR', 2558, '', NULL),
	(59, '0x9a005c9a89bd72a4bd27721e7a09a3c11d2b03c4', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:32.219', 'StarterCoin', 'STAC', 2565, '', NULL),
	(60, '0x0d262e5dc4a06a0f1c90ce79c7a60c09dfc884e4', '2018-09-12 13:25:27.106', '8', 'anonymousUser', '2018-09-12 13:30:35.226', 'JET8', 'J8T', 2568, '', NULL),
	(61, '0x9a0242b7a33dacbe40edb927834f96eb39f8fbcb', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:38.529', 'BABB', 'BAX', 2572, '', NULL),
	(62, '0x5102791ca02fc3595398400bfe0e33d7b6c82267', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:41.949', 'Leadcoin', 'LDC', 2580, '', NULL),
	(63, '0xef2463099360a085f1f10b076ed72ef625497a06', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:45.091', 'Sharpe Platform Token', 'SHP', 2581, '', NULL),
	(64, '0x6888a16ea9792c15a4dcf2f6c623d055c8ede792', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:50.919', 'Spectiv', 'SIG', 2611, '', NULL),
	(65, '0x28dee01d53fed0edf5f6e310bf8ef9311513ae40', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:53.585', 'BlitzPredict', 'XBP', 2614, '', NULL),
	(66, '0x41ab1b6fcbb2fa9dced81acbdec13ea6315f2bf2', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:56.238', 'XinFin Network', 'XDCE', 2634, '', NULL),
	(67, '0xc12d099be31567add4e4e4d0d45691c3f58f5663', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:30:59.564', 'Auctus', 'AUC', 2653, '', NULL),
	(68, '0x9b70740e708a083c6ff38df52297020f5dfaa5ee', '2018-09-12 13:25:27.106', '10', 'anonymousUser', '2018-09-12 13:31:02.946', 'Daneel', 'DAN', 2656, '', NULL),
	(69, '0x6710c63432a2de02954fc0f851db07146a6c0312', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:06.036', 'SyncFab', 'MFG', 2658, '', NULL),
	(70, '0x1460a58096d80a50a2f1f956dda497611fa4f165', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:12.314', 'Own', 'CHX', 2673, '', NULL),
	(71, '0xfc2c4d8f95002c14ed0a7aa65102cac9e5953b5e', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:18.649', 'Rublix', 'RBLX', 2689, '', NULL),
	(72, '0xb62132e35a6c13ee1ee0f84dc5d40bad8d815206', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:21.942', 'Nexo', 'NEXO', 2694, '', NULL),
	(73, '0x763186eb8d4856d536ed4478302971214febc6a9', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:25.209', 'BetterBetting', 'BETR', 2703, '', NULL),
	(74, '0xb0280743b44bf7db4b6be482b2ba7b75e5da096c', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:28.267', 'Transcodium', 'TNS', 2704, '', NULL),
	(75, '0x737f98ac8ca59f2c68ad658e3c3d8c8963e40a4c', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:31.349', 'Amon', 'AMN', 2705, '', NULL),
	(76, '0x47bc01597798dcd7506dcca36ac4302fc93a8cfb', '2018-09-12 13:25:27.106', '8', 'anonymousUser', '2018-09-12 13:31:34.583', 'Crowd Machine', 'CMCT', 2708, '', NULL),
	(77, '0xfb1e5f5e984c28ad7e228cdaa1f8a0919bb6a09b', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:37.954', 'Galaxy eSolutions', 'GES', 2716, '', NULL),
	(78, '0xedd7c94fd7b4971b916d15067bc454b9e1bad980', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:40.604', 'Zippie', 'ZIPT', 2724, '', NULL),
	(79, '0xc20464e0c373486d2b3335576e83a218b1618a5e', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:43.912', 'Datarius Credit', 'DTRC', 2752, '', NULL),
	(80, '0x4162178b78d6985480a308b2190ee5517460406d', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:47.087', 'Colu Local Network', 'CLN', 2753, '', NULL),
	(81, '0x744d70fdbe2ba4cf95131626614a1763df805b9e', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:50.386', 'Silent Notary', 'SNT', 2764, '', NULL),
	(82, '0xfb725bab323927cfb20fb82ba9a1975f7d24705b', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:53.534', 'EJOY', 'JOY', 2831, '', NULL),
	(83, '0x84f7c44b6fed1080f647e354d552595be2cc602f', '2018-09-12 13:25:27.106', '18', 'anonymousUser', '2018-09-12 13:31:56.509', 'Bigbom', 'BBO', 2836, '', NULL),
	(84, '0xb056c38f6b7dc4064367403e26424cd2c60655e1', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:31:59.180', 'CEEK VR', 'CEEK', 2856, '', NULL),
	(85, '0xc7c03b8a3fc5719066e185ea616e87b88eba44a3', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:08.105', 'Eligma Token', 'ELI', 3052, '', NULL),
	(86, '0x818fc6c2ec5986bc6e2cbf00939d90556ab12ce5', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:10.763', 'Kind Ads Token', 'KIN', 3078, '', NULL),
	(87, '0x910dfc18d6ea3d6a7124a6f8b5458f281060fa4c', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:16.919', 'X8X Token', 'X8X', 3079, '', NULL),
	(88, '0xbb1fa4fdeb3459733bf67ebc6f893003fa976a82', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:20.200', 'Bitnation', 'XPAT', 3112, '', NULL),
	(89, '0xdf6ef343350780bf8c3410bf062e0c015b1dd671', '2018-09-12 13:25:27.107', '8', 'anonymousUser', '2018-09-12 13:32:25.567', 'Rubex Money', 'BMC', 3204, '', NULL),
	(90, '0xd341d1680eeee3255b8c4c75bcce7eb57f144dae', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:28.537', 'ONG', 'ONG', 3217, '', NULL),
	(91, '0x009e864923b49263c7f10d19b7f8ab7a9a5aad33', '2018-09-12 13:25:27.107', '18', 'anonymousUser', '2018-09-12 13:32:35.120', 'Knoxstertoken', 'FKX', 3241, '', NULL),
	(92, '0x1234567461d3f8db7496581774bd869c83d51c93', '2018-09-12 13:25:27.104', '18', 'anonymousUser', '2018-09-12 13:25:43.469', 'BitClave', 'CAT', 606, '', NULL);
/*!40000 ALTER TABLE `tokenerc20` ENABLE KEYS */;

-- Dumping structure for table canya.transaction
CREATE TABLE IF NOT EXISTS `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(100) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `currency` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `accept` varchar(50) DEFAULT NULL,
  `usd` varchar(50) DEFAULT NULL,
  `orderid` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `hash` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `eth` varchar(100) DEFAULT NULL,
  `hashethertoaccount` varchar(100) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table canya.transaction: ~2 rows (approximately)
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` (`id`, `address`, `amount`, `currency`, `email`, `accept`, `usd`, `orderid`, `status`, `hash`, `date`, `eth`, `hashethertoaccount`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
	(2, '0x71B7334DA2176E837B6CC1F37Ac29eFA3E1E9F64', '104.5', 'ETH', 'my3d3d@gmail.com', '0', '5.030169', '', 'INITIATED', '', '', '0.023288', NULL, 'anonymousUser', '2018-09-27 18:27:46', NULL, 'anonymousUser', '2018-09-27 18:27:46'),
	(3, '0x71B7334DA2176E837B6CC1F37Ac29eFA3E1E9F64', '1', 'ETH', 'my3d3d@gmail.com', '0', '0.048136', '6D48jG5b', 'COMPLETE', '0xddb2230dd6a310e3064f1bb942b6a01ca0a4db7373a443ed6bec4c02c857c860', '2018-09-27 18:29:15', '0.000223', '0xddb2230dd6a310e3064f1bb942b6a01ca0a4db7373a443ed6bec4c02c857c860', 'anonymousUser', '2018-09-27 18:31:25', NULL, 'system', '2018-09-27 18:31:26');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
