CREATE TABLE ipInfo (
    ipInfo_id SERIAL PRIMARY KEY,
    ip_address VARCHAR(15) NOT NULL UNIQUE,
    is_current_ip BOOLEAN NOT NULL DEFAULT FALSE,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255),
    zipcode VARCHAR(255),
    latitude VARCHAR(255),
    longitude VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_history (
    history_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    ipInfo_id INTEGER NOT NULL,
    accessed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_ipInfo FOREIGN KEY (ipInfo_id) REFERENCES ipInfo(ipInfo_id)
);

ALTER TABLE users ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;