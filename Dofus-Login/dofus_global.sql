/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : dofus_global

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 09/01/2019 06:45:45
*/

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
INSERT INTO `comunidades` VALUES (0, 'FR');
INSERT INTO `comunidades` VALUES (1, 'EN');
INSERT INTO `comunidades` VALUES (2, 'XX');
INSERT INTO `comunidades` VALUES (3, 'DE');
INSERT INTO `comunidades` VALUES (4, 'ES');
INSERT INTO `comunidades` VALUES (5, 'RU');
INSERT INTO `comunidades` VALUES (6, 'BR');
INSERT INTO `comunidades` VALUES (7, 'NL');
INSERT INTO `comunidades` VALUES (9, 'IT');
INSERT INTO `comunidades` VALUES (10, 'JP');
INSERT INTO `comunidades` VALUES (11, 'DE');

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id de la cuenta',
  `usuario` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Usuario para hacer login',
  `password` varchar(35) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Contraseña para hacer login',
  `apodo` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT '' COMMENT 'Apodo visible para la cuenta',
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `rango_cuenta` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: Usuario\r\n1: Mod\r\n2: GM\r\n3: Admin',
  `tiempo_abono` datetime(0) NOT NULL COMMENT 'Abono con fecha y tiempo para la cuenta',
  `comunidad` tinyint(2) NULL DEFAULT 4 COMMENT 'id de la comunidad',
  `baneado` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: No baneado\r\n1: Baneado',
  `migracion` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_nombre_usuario`(`usuario`) USING BTREE,
  INDEX `Relacion_Comunidad`(`comunidad`) USING BTREE,
  CONSTRAINT `Relacion_Comunidad` FOREIGN KEY (`comunidad`) REFERENCES `comunidades` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cuentas
-- ----------------------------
INSERT INTO `cuentas` VALUES (1, 'test', '1', 'Aidemu', '127.0.0.1', 4, '2030-03-28 17:45:50', 4, 0, 0);
INSERT INTO `cuentas` VALUES (2, 'tes', '1', 'ApodoActualizado', '127.0.0.1', 4, '2019-03-27 17:46:08', 4, 0, 0);
INSERT INTO `cuentas` VALUES (3, 'caca', '1', 'apodo', NULL, 4, '2018-03-28 17:46:23', 4, 0, 0);
INSERT INTO `cuentas` VALUES (4, 'vip', '1', 'vip1', NULL, 4, '2018-03-28 17:46:37', 4, 0, 0);

-- ----------------------------
-- Table structure for personajes
-- ----------------------------
DROP TABLE IF EXISTS `personajes`;
CREATE TABLE `personajes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `color_1` int(11) NOT NULL DEFAULT -1,
  `color_2` int(11) NOT NULL DEFAULT -1,
  `color_3` int(11) NOT NULL DEFAULT -1,
  `nivel` int(4) NOT NULL DEFAULT 1 COMMENT 'nivel del personaje',
  `gfx` int(4) NOT NULL,
  `tamano` int(4) NOT NULL DEFAULT 100 COMMENT 'tamaño del gfx (short maximo 32767)',
  `mapa_id` int(4) NOT NULL,
  `celda_id` int(4) NOT NULL DEFAULT 0,
  `raza_id` tinyint(2) NOT NULL COMMENT 'raza vinculado a raza',
  `cuenta_id` int(4) NOT NULL COMMENT 'cuenta referencia a cuentas',
  `derechos` int(5) NOT NULL DEFAULT 8192 COMMENT 'numero de los derechos que tiene el personaje',
  `restricciones` tinyint(3) NOT NULL DEFAULT 8 COMMENT 'numero de las restricciones que tiene el personaje',
  `servidor_id` int(4) NOT NULL,
  PRIMARY KEY (`id`, `nombre`) USING BTREE,
  INDEX `Referencia_cuenta`(`cuenta_id`) USING BTREE,
  INDEX `Referencia_raza`(`raza_id`) USING BTREE,
  INDEX `Referencia_servidor`(`servidor_id`) USING BTREE,
  INDEX `Referencia_mapa`(`mapa_id`) USING BTREE,
  CONSTRAINT `Referencia_cuenta` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Referencia_mapa` FOREIGN KEY (`mapa_id`) REFERENCES `dofus_servidor`.`mapas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Referencia_raza` FOREIGN KEY (`raza_id`) REFERENCES `dofus_servidor`.`razas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Referencia_servidor` FOREIGN KEY (`servidor_id`) REFERENCES `servidores` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personajes
-- ----------------------------
INSERT INTO `personajes` VALUES (1, 'xX-Aidemu-Xx', -1, -1, 11568205, 1, 10, 100, 666, 250, 5, 1, 8192, 8, 601);
INSERT INTO `personajes` VALUES (2, 'test-personaje', -1, -1, -1, 10, 20, 100, 666, 122, 1, 1, 8192, 8, 601);

-- ----------------------------
-- Table structure for servidores
-- ----------------------------
DROP TABLE IF EXISTS `servidores`;
CREATE TABLE `servidores`  (
  `id` int(4) NOT NULL DEFAULT 0 COMMENT 'Id del servidor',
  `comunidad` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'Comunidad del servidor',
  `poblacion` tinyint(2) NOT NULL DEFAULT 1 COMMENT 'id de las plazas libres del servidor',
  `vip_necesario` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'Solo Acceso vip',
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '' COMMENT 'ip del game',
  `puerto` int(4) NOT NULL COMMENT 'puerto del game',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Referencia_Comunidad`(`comunidad`) USING BTREE,
  CONSTRAINT `Referencia_Comunidad` FOREIGN KEY (`comunidad`) REFERENCES `comunidades` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of servidores
-- ----------------------------
INSERT INTO `servidores` VALUES (601, 2, 0, 1, '127.0.0.1', 5555);
INSERT INTO `servidores` VALUES (602, 2, 0, 0, '127.0.0.1', 5556);

SET FOREIGN_KEY_CHECKS = 1;
