CREATE TABLE users (
  user_id VARCHAR(20) PRIMARY KEY,
  name    VARCHAR(40) NOT NULL
);

CREATE TABLE accounts (
  user_id VARCHAR(20) NOT NULL,
  balance DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_account_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transfers (
  transfer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  from_user_id VARCHAR(20) NOT NULL,
  to_user_id   VARCHAR(20) NOT NULL,
  amount       DECIMAL(15,2) NOT NULL,
  event_time   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_transfers_from FOREIGN KEY (from_user_id) REFERENCES users(user_id),
  CONSTRAINT fk_transfers_to   FOREIGN KEY (to_user_id)   REFERENCES users(user_id)
);

CREATE INDEX idx_tf_from ON transfers (from_user_id, transfer_id);
CREATE INDEX idx_tf_to   ON transfers (to_user_id, transfer_id);
