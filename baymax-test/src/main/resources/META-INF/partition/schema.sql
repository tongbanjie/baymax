CREATE SCHEMA IF NOT EXISTS `ds_0`;
CREATE SCHEMA IF NOT EXISTS `ds_1`;


DROP TABLE IF EXISTS  `ds_0`.`t_order_0`;
DROP TABLE IF EXISTS  `ds_0`.`t_order_1`;
DROP TABLE IF EXISTS  `ds_1`.`t_order_2`;
DROP TABLE IF EXISTS  `ds_1`.`t_order_3`;


CREATE TABLE IF NOT EXISTS `ds_0`.`t_order_0` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_0`.`t_order_1` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_1`.`t_order_2` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));
CREATE TABLE IF NOT EXISTS `ds_1`.`t_order_3` (`order_id` INT NOT NULL, `user_id` INT NOT NULL,`product_id` INT NOT NULL,`product_name` VARCHAR(50) NOT NULL, `status`INT NOT NULL, PRIMARY KEY (`order_id`));

/*数据可通过单元测试jdbc.InsertTest生成*/