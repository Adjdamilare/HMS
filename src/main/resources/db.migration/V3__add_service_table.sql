CREATE TABLE Services (
    Channel_Service_ID VARCHAR(255)PRIMARY KEY unique,
    Channel_Service TEXT NOT NULL,
    Duration_Of_Service numeric,
    Charge_For_Service DECIMAL(10, 2),
    Service_Notes TEXT
);