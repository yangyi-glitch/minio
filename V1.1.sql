CREATE TABLE `file`
(
    `id` bigint NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `file_type` varchar(255) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1882076470415761413 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
