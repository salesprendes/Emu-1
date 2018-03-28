SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comunidades
-- ----------------------------
DROP TABLE IF EXISTS `comunidades`;
CREATE TABLE `comunidades`  (
  `id` tinyint(2) NOT NULL COMMENT '0: Francesa\r\n1: Internacional\r\n2: Inglesa\r\n3: Alemana\r\n4: Española\r\n6: Portuguesa\r\n7: Holandesa\r\n9: Italiana',
  `nombre` varchar(2) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comunidades
-- ----------------------------
INSERT INTO `comunidades` VALUES (0, 'fr');
INSERT INTO `comunidades` VALUES (1, 'en');
INSERT INTO `comunidades` VALUES (2, 'en');
INSERT INTO `comunidades` VALUES (3, 'de');
INSERT INTO `comunidades` VALUES (4, 'es');
INSERT INTO `comunidades` VALUES (6, 'pt');
INSERT INTO `comunidades` VALUES (7, 'nl');
INSERT INTO `comunidades` VALUES (9, 'it');

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id de la cuenta',
  `usuario` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Usuario para hacer login',
  `password` varchar(35) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Contraseña para hacer login',
  `apodo` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT '' COMMENT 'Apodo visible para la cuenta',
  `rango_cuenta` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: Usuario\r\n1: Mod\r\n2: GM\r\n3: Admin',
  `tiempo_abono` datetime(0) NOT NULL COMMENT 'Abono con fecha y tiempo para la cuenta',
  `comunidad` tinyint(2) NULL DEFAULT 4 COMMENT 'id de la comunidad',
  `baneado` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: No baneado\r\n1: Baneado',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_nombre_usuario`(`usuario`) USING BTREE,
  INDEX `Relacion_Comunidad`(`comunidad`) USING BTREE,
  CONSTRAINT `Relacion_Comunidad` FOREIGN KEY (`comunidad`) REFERENCES `comunidades` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cuentas
-- ----------------------------
INSERT INTO `cuentas` VALUES (1, 'test', '1', 'Aidemu', 3, '2019-03-28 16:23:32', 4, 0);
INSERT INTO `cuentas` VALUES (2, 'tes', '1', '', 3, '2019-03-06 13:32:41', 4, 0);
INSERT INTO `cuentas` VALUES (3, 'caca', '1', 'apodo', 3, '2017-03-09 19:43:16', 4, 0);
INSERT INTO `cuentas` VALUES (4, 'vip', '1', '1', 3, '2019-03-31 20:02:24', 4, 0);

-- ----------------------------
-- Table structure for personajes
-- ----------------------------
DROP TABLE IF EXISTS `personajes`;
CREATE TABLE `personajes`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `color_1` int(11) NOT NULL DEFAULT -1,
  `color_2` int(11) NOT NULL DEFAULT -1,
  `color_3` int(11) NOT NULL DEFAULT -1,
  `raza_id` tinyint(2) NOT NULL COMMENT 'raza vinculado a raza',
  `cuenta_id` int(4) NOT NULL COMMENT 'cuenta referencia a cuentas',
  `servidor_id` int(4) NOT NULL,
  PRIMARY KEY (`id`, `nombre`) USING BTREE,
  INDEX `Referencia_cuenta`(`cuenta_id`) USING BTREE,
  INDEX `Referencia_raza`(`raza_id`) USING BTREE,
  INDEX `Referencia_servidor`(`servidor_id`) USING BTREE,
  CONSTRAINT `Referencia_cuenta` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Referencia_raza` FOREIGN KEY (`raza_id`) REFERENCES `razas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Referencia_servidor` FOREIGN KEY (`servidor_id`) REFERENCES `servidores` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personajes
-- ----------------------------
INSERT INTO `personajes` VALUES (1, 'xX-Aidemu-Xx', -1, -1, -1, 1, 1, 601);
INSERT INTO `personajes` VALUES (2, 'test', -1, -1, -1, 1, 1, 602);
INSERT INTO `personajes` VALUES (3, 'test1', -1, -1, -1, 4, 1, 602);

-- ----------------------------
-- Table structure for razas
-- ----------------------------
DROP TABLE IF EXISTS `razas`;
CREATE TABLE `razas`  (
  `id` tinyint(2) NOT NULL COMMENT 'Id de la raza',
  `nombre` text CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre de la raza',
  `pa` tinyint(2) NOT NULL COMMENT 'Pa principales para la raza',
  `pm` tinyint(2) NOT NULL COMMENT 'Pm principales para la raza',
  `vida` tinyint(4) NOT NULL COMMENT 'Vida principal para la raza',
  `iniciativa` tinyint(3) NOT NULL COMMENT 'Iniciativa principal para la raza',
  `prospeccion` tinyint(3) NOT NULL COMMENT 'Prospección principal para la raza',
  `mapa_inicial` int(4) NOT NULL COMMENT 'Mapa inicial donde iniciara la raza',
  `celda_inicial` int(4) NOT NULL COMMENT 'Celda donde iniciara la raza',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Relacion_mapa_inicial`(`mapa_inicial`) USING BTREE,
  CONSTRAINT `Relacion_mapa_inicial` FOREIGN KEY (`mapa_inicial`) REFERENCES `mapas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of razas
-- ----------------------------
INSERT INTO `razas` VALUES (1, 'Feca', 6, 3, 42, 0, 100, 10300, 337);
INSERT INTO `razas` VALUES (2, 'Osamodas', 6, 3, 42, 0, 100, 10299, 300);
INSERT INTO `razas` VALUES (3, 'Anotrof', 6, 3, 42, 0, 120, 10299, 300);
INSERT INTO `razas` VALUES (4, 'Sram', 6, 3, 42, 0, 100, 10285, 263);
INSERT INTO `razas` VALUES (5, 'Xelor', 6, 3, 42, 0, 100, 10298, 315);
INSERT INTO `razas` VALUES (6, 'Zurcarák', 6, 3, 46, 0, 100, 10276, 311);
INSERT INTO `razas` VALUES (7, 'Aniripsa', 6, 3, 42, 0, 100, 10283, 299);
INSERT INTO `razas` VALUES (8, 'Yopuka', 6, 3, 48, 0, 100, 10294, 309);
INSERT INTO `razas` VALUES (9, 'Ocra', 6, 3, 44, 0, 100, 10292, 299);
INSERT INTO `razas` VALUES (10, 'Sadida', 6, 3, 42, 0, 100, 10279, 284);
INSERT INTO `razas` VALUES (11, 'Sacrogito', 6, 3, 46, 0, 100, 10296, 258);
INSERT INTO `razas` VALUES (12, 'Pandawa', 6, 3, 46, 0, 100, 10289, 250);

-- ----------------------------
-- Table structure for servidores
-- ----------------------------
DROP TABLE IF EXISTS `servidores`;
CREATE TABLE `servidores`  (
  `id` int(4) NOT NULL COMMENT 'Id del servidor',
  `nombre` varchar(35) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Nombre del servidor',
  `comunidad` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'Comunidad del servidor',
  `estado` tinyint(2) NOT NULL DEFAULT 1 COMMENT 'Estado del servidor',
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'ip del juego',
  `puerto` varchar(5) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Puerto del juego',
  `ip_database` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'ip de la database',
  `usuario_database` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'usuario login de la database',
  `password_database` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'password de la database',
  PRIMARY KEY (`id`, `nombre`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of servidores
-- ----------------------------
INSERT INTO `servidores` VALUES (601, 'Eratz', 2, 0, 'localhost', '5555', 'localhost', 'root', '');
INSERT INTO `servidores` VALUES (602, 'Henual', 2, 0, 'localhost', '5556', 'localhost', 'root', '');

SET FOREIGN_KEY_CHECKS = 1;