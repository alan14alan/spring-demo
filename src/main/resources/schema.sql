-- 建立幣別對應表
DROP TABLE IF EXISTS currency;

CREATE TABLE currency (
  code VARCHAR(10) PRIMARY KEY,
  chinese_name VARCHAR(50) NOT NULL
);