SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id de la cuenta',
  `usuario` varchar(30) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Usuario para hacer login',
  `password` varchar(50) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Contraseña para hacer login',
  `apodo` varchar(30) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Apodo visible para la cuenta',
  `tiempo_abono` datetime NOT NULL COMMENT 'Abono con fecha y tiempo para la cuenta',
  `baneado` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_nombre_usuario` (`usuario`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of cuentas
-- ----------------------------
INSERT INTO `cuentas` VALUES ('1', 'test', '1', 'Aidemu', '2018-03-24 14:19:32', '0');
INSERT INTO `cuentas` VALUES ('2', 'tes', '1', 'as', '2018-03-06 13:32:41', '0');
INSERT INTO `cuentas` VALUES ('3', 'caca', '1', 'apodo', '2018-03-09 19:43:16', '0');

-- ----------------------------
-- Table structure for razas
-- ----------------------------
DROP TABLE IF EXISTS `razas`;
CREATE TABLE `razas` (
  `id` tinyint(2) NOT NULL COMMENT 'Id de la raza',
  `nombre` text COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre de la raza',
  `pa` tinyint(2) NOT NULL COMMENT 'Pa principales para la raza',
  `pm` tinyint(2) NOT NULL COMMENT 'Pm principales para la raza',
  `vida` tinyint(4) NOT NULL COMMENT 'Vida principal para la raza',
  `iniciativa` tinyint(3) NOT NULL COMMENT 'Iniciativa principal para la raza',
  `prospeccion` tinyint(3) NOT NULL COMMENT 'Prospección principal para la raza',
  `mapa_inicial` int(4) NOT NULL COMMENT 'Mapa inicial donde iniciara la raza',
  `celda_inicial` int(4) NOT NULL COMMENT 'Celda donde iniciara la raza',
  PRIMARY KEY (`id`),
  KEY `Relacion_mapa_inicial` (`mapa_inicial`),
  CONSTRAINT `Relacion_mapa_inicial` FOREIGN KEY (`mapa_inicial`) REFERENCES `dofus_servidor`.`mapas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of razas
-- ----------------------------
INSERT INTO `razas` VALUES ('1', 'Feca', '6', '3', '42', '0', '100', '10300', '337');
INSERT INTO `razas` VALUES ('2', 'Osamodas', '6', '3', '42', '0', '100', '10299', '300');
INSERT INTO `razas` VALUES ('3', 'Anotrof', '6', '3', '42', '0', '120', '10299', '300');
INSERT INTO `razas` VALUES ('4', 'Sram', '6', '3', '42', '0', '100', '10285', '263');
INSERT INTO `razas` VALUES ('5', 'Xelor', '6', '3', '42', '0', '100', '10298', '315');
INSERT INTO `razas` VALUES ('6', 'Zurcarák', '6', '3', '46', '0', '100', '10276', '311');
INSERT INTO `razas` VALUES ('7', 'Aniripsa', '6', '3', '42', '0', '100', '10283', '299');
INSERT INTO `razas` VALUES ('8', 'Yopuka', '6', '3', '48', '0', '100', '10294', '309');
INSERT INTO `razas` VALUES ('9', 'Ocra', '6', '3', '44', '0', '100', '10292', '299');
INSERT INTO `razas` VALUES ('10', 'Sadida', '6', '3', '42', '0', '100', '10279', '284');
INSERT INTO `razas` VALUES ('11', 'Sacrogito', '6', '3', '46', '0', '100', '10296', '258');
INSERT INTO `razas` VALUES ('12', 'Pandawa', '6', '3', '46', '0', '100', '10289', '250');

-- ----------------------------
-- Table structure for servidores
-- ----------------------------
DROP TABLE IF EXISTS `servidores`;
CREATE TABLE `servidores` (
  `id` int(4) NOT NULL COMMENT 'Id del servidor',
  `nombre` varchar(35) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre del servidor',
  `comunidad` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'Comunidad del servidor',
  `ip` varchar(15) COLLATE utf8_spanish_ci NOT NULL COMMENT 'ip del juego',
  `puerto` varchar(5) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Puerto del juego',
  `ip_database` varchar(15) COLLATE utf8_spanish_ci NOT NULL COMMENT 'ip de la database',
  `usuario_database` varchar(20) COLLATE utf8_spanish_ci NOT NULL COMMENT 'usuario login de la database',
  `password_database` varchar(30) COLLATE utf8_spanish_ci NOT NULL COMMENT 'password de la database',
  PRIMARY KEY (`id`,`nombre`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of servidores
-- ----------------------------
INSERT INTO `servidores` VALUES ('601', 'Eratz', '4', 'localhost', '444', 'localhost', 'root', 'root');
