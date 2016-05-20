CREATE SCHEMA IF NOT EXISTS `ds_0`;
CREATE SCHEMA IF NOT EXISTS `ds_1`;


DROP TABLE `ds_0`.`t_order_0`;
DROP TABLE `ds_0`.`t_order_1`;
DROP TABLE `ds_1`.`t_order_2`;
DROP TABLE `ds_1`.`t_order_3`;

DROP TABLE `ds_0`.`t_order_item_0`;
DROP TABLE `ds_0`.`t_order_item_1`;
DROP TABLE `ds_1`.`t_order_item_0`;
DROP TABLE `ds_1`.`t_order_item_1`;


CREATE TABLE IF NOT EXISTS `ds_0`.`t_order_0` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_0`.`t_order_1` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_1`.`t_order_2` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_1`.`t_order_3` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));


INSERT INTO `ds_0`.`t_order_0` VALUES (1000, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_0` VALUES (1002, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_0` VALUES (1004, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_0` VALUES (1006, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_0` VALUES (1008, 10, 101, '铜宝', 1);


INSERT INTO `ds_0`.`t_order_1` VALUES (1001, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_1` VALUES (1003, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_1` VALUES (1005, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_1` VALUES (1007, 10, 101, '铜宝', 1);
INSERT INTO `ds_0`.`t_order_1` VALUES (1009, 10, 101, '铜宝', 1);
