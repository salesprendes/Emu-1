/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : dofus_global

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 19/01/2019 01:11:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comunidades
-- ----------------------------
DROP TABLE IF EXISTS `comunidades`;
CREATE TABLE `comunidades`  (
  `id` tinyint(2) NOT NULL COMMENT '0: Francesa\r\n1: Internacional\r\n2: Inglesa\r\n3: Alemana\r\n4: Española\r\n6: Portuguesa\r\n7: Holandesa\r\n9: Italiana',
  `nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comunidades
-- ----------------------------
INSERT INTO `comunidades` VALUES (0, 'Francófona');
INSERT INTO `comunidades` VALUES (1, 'Reino Unido & Irlanda');
INSERT INTO `comunidades` VALUES (2, 'Internacional');
INSERT INTO `comunidades` VALUES (3, 'Alemana');
INSERT INTO `comunidades` VALUES (4, 'HispaÑia');
INSERT INTO `comunidades` VALUES (5, 'Rusa');
INSERT INTO `comunidades` VALUES (6, 'Brasileña');
INSERT INTO `comunidades` VALUES (7, 'Neerlandesa');
INSERT INTO `comunidades` VALUES (9, 'Italiana');
INSERT INTO `comunidades` VALUES (10, 'Japonesa');
INSERT INTO `comunidades` VALUES (99, 'DEBUG');

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id de la cuenta',
  `usuario` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Usuario para hacer login',
  `password` varchar(35) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'Contraseña para hacer login',
  `apodo` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '' COMMENT 'Apodo visible para la cuenta',
  `uid` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `rango_cuenta` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: Usuario\r\n1: Mod\r\n2: GM\r\n3: Admin',
  `tiempo_abono` datetime(0) NOT NULL COMMENT 'Abono con fecha y tiempo para la cuenta',
  `comunidad` tinyint(2) NOT NULL DEFAULT 4 COMMENT 'id de la comunidad',
  `baneado` bit(1) NOT NULL DEFAULT b'0' COMMENT '0: No baneado\r\n1: Baneado',
  `migracion` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_nombre_usuario`(`usuario`) USING BTREE,
  INDEX `Relacion_Comunidad`(`comunidad`) USING BTREE,
  CONSTRAINT `Relacion_Comunidad` FOREIGN KEY (`comunidad`) REFERENCES `comunidades` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cuentas
-- ----------------------------
INSERT INTO `cuentas` VALUES (1, 'test', '1', 'Aidemu', '6zNICR53q0i49d49C', '127.0.0.1', 4, '2030-03-28 17:45:50', 4, b'0', 0);
INSERT INTO `cuentas` VALUES (2, 'tes', '1', 'ApodoActualizado', '', '127.0.0.1', 4, '2019-03-27 17:46:08', 4, b'0', 0);
INSERT INTO `cuentas` VALUES (3, 'caca', '1', 'apodo', '', '127.0.0.1', 4, '2018-03-28 17:46:23', 4, b'0', 0);
INSERT INTO `cuentas` VALUES (4, 'vip', '1', 'vip1', '', '127.0.0.1', 4, '2018-03-28 17:46:37', 4, b'0', 0);

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
  `mapa_id` int(5) NOT NULL,
  `celda_id` int(4) NOT NULL DEFAULT 0,
  `sexo` tinyint(1) NOT NULL COMMENT 'sexo del personaje 0 = masculino, 1 = femenino',
  `experiencia` bigint(30) NOT NULL DEFAULT 0,
  `kamas` bigint(30) NULL DEFAULT 0,
  `porcentaje_vida` tinyint(3) NOT NULL DEFAULT 100,
  `raza_id` tinyint(2) NOT NULL COMMENT 'raza vinculado a raza',
  `vitalidad` int(11) NOT NULL DEFAULT 0,
  `sabiduria` int(11) NOT NULL DEFAULT 0,
  `fuerza` int(11) NOT NULL DEFAULT 0,
  `inteligencia` int(11) NOT NULL DEFAULT 0,
  `suerte` int(11) NOT NULL DEFAULT 0,
  `agilidad` int(11) NOT NULL DEFAULT 0,
  `emotes` int(7) NOT NULL DEFAULT 1 COMMENT 'numero de los emotes del personaje',
  `canales` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '*p?:',
  `cuenta_id` int(4) NOT NULL COMMENT 'cuenta referencia a cuentas',
  `derechos` int(5) NOT NULL DEFAULT 8192 COMMENT 'numero de los derechos que tiene el personaje',
  `restricciones` tinyint(3) NOT NULL DEFAULT 8 COMMENT 'numero de las restricciones que tiene el personaje',
  `servidor_id` int(4) NOT NULL,
  PRIMARY KEY (`id`, `nombre`) USING BTREE,
  INDEX `Referencia_cuenta`(`cuenta_id`) USING BTREE,
  INDEX `Referencia_servidor`(`servidor_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `Referencia_cuenta` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Referencia_servidor` FOREIGN KEY (`servidor_id`) REFERENCES `servidores` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personajes
-- ----------------------------
INSERT INTO `personajes` VALUES (1, 'xX-Aidemu-Xx', 11568205, 11568205, 11568205, 200, 10, 100, 7411, 250, 0, 0, 0, 100, 5, 900, 900, 900, 900, 900, 900, 1, '*p?:', 1, 8192, 8, 601);
INSERT INTO `personajes` VALUES (2, 'test-personaje', -1, -1, -1, 10, 20, 100, 666, 122, 1, 0, 0, 100, 1, 0, 0, 0, 0, 0, 0, 1, '*p?:', 2, 8192, 8, 601);
INSERT INTO `personajes` VALUES (3, 'test', -1, -1, -1, 1, 10, 100, 666, 122, 1, 0, 0, 100, 1, 0, 0, 0, 0, 0, 0, 1, '*p?:', 1, 8192, 8, 602);

-- ----------------------------
-- Table structure for personajes_alineamientos
-- ----------------------------
DROP TABLE IF EXISTS `personajes_alineamientos`;
CREATE TABLE `personajes_alineamientos`  (
  `personaje` int(11) NOT NULL,
  `alineamiento` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'id del alineamiento',
  `orden` int(4) NOT NULL DEFAULT 0,
  `orden_nivel` int(4) NOT NULL DEFAULT 0 COMMENT 'nivel de la orden',
  `honor` int(11) NOT NULL DEFAULT 0 COMMENT 'honor total del alineamiento',
  `deshonor` int(11) NOT NULL DEFAULT 0 COMMENT 'deshonor del alineamiento',
  `activado` bit(1) NOT NULL DEFAULT b'0' COMMENT 'sirve para mostrar o ocultar las alas',
  PRIMARY KEY (`personaje`) USING BTREE,
  CONSTRAINT `referencia_personajes` FOREIGN KEY (`personaje`) REFERENCES `personajes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personajes_alineamientos
-- ----------------------------
INSERT INTO `personajes_alineamientos` VALUES (1, 1, 0, 0, 18000, 0, b'1');

-- ----------------------------
-- Table structure for personajes_items
-- ----------------------------
DROP TABLE IF EXISTS `personajes_items`;
CREATE TABLE `personajes_items`  (
  `personaje` int(11) NOT NULL COMMENT 'id del personaje',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id del objeto',
  `id_modelo` int(11) NOT NULL COMMENT 'id del modelo del objeto base',
  `cantidad` int(11) NOT NULL,
  `posicion_inventario` tinyint(3) NOT NULL,
  `stats` text CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`, `personaje`) USING BTREE,
  INDEX `referencia_personaje`(`personaje`) USING BTREE,
  CONSTRAINT `referencia_personaje` FOREIGN KEY (`personaje`) REFERENCES `personajes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personajes_items
-- ----------------------------
INSERT INTO `personajes_items` VALUES (1, 1, 39, 1, -1, '');

-- ----------------------------
-- Table structure for servidores
-- ----------------------------
DROP TABLE IF EXISTS `servidores`;
CREATE TABLE `servidores`  (
  `id` int(4) NOT NULL DEFAULT 0 COMMENT 'Id del servidor',
  `comunidad` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'Comunidad del servidor',
  `poblacion` tinyint(2) NOT NULL DEFAULT 0 COMMENT 'id de las plazas libres del servidor',
  `abono_necesario` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Solo cuentas con abono',
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL DEFAULT '' COMMENT 'ip del game',
  `puerto` int(4) NOT NULL COMMENT 'puerto del game',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Referencia_Comunidad`(`comunidad`) USING BTREE,
  CONSTRAINT `Referencia_Comunidad` FOREIGN KEY (`comunidad`) REFERENCES `comunidades` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of servidores
-- ----------------------------
INSERT INTO `servidores` VALUES (601, 4, 0, b'0', '127.0.0.1', 5555);
INSERT INTO `servidores` VALUES (602, 4, 0, b'0', '127.0.0.1', 5556);

SET FOREIGN_KEY_CHECKS = 1;
