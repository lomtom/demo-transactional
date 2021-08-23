SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_dictionary`;
CREATE TABLE `tb_data_dictionary`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置类型',
  `sort` int NOT NULL COMMENT '配置序号',
  `key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置key',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置名',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置值',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '0-正常 1-删除',
  `create_time` datetime NOT NULL COMMENT '插入时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1428549698146271233 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = COMPACT;

INSERT INTO `linan_characteristic`.`tb_data_dictionary` (`id`, `type`, `sort`, `key`, `name`, `value`, `deleted`, `create_time`, `update_time`, `desc`) VALUES (1427214682892009472, 'string', 0, 'string', 'string', 'string', 0, '2021-08-16 10:24:31', NULL, 'string');


SET FOREIGN_KEY_CHECKS = 1;
