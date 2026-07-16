CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at DATETIME NOT NULL,
                       active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE trainer (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         phone_number VARCHAR(50),
                         specialization VARCHAR(100) NOT NULL
);

CREATE TABLE training_room (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(100) NOT NULL,
                               details VARCHAR(500),
                               capacity INT NOT NULL
);

CREATE TABLE training_type (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(100) NOT NULL,
                               description VARCHAR(500),
                               duration INT NOT NULL
);

CREATE TABLE training_session (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                  date DATE NOT NULL,
                                  start_time TIME NOT NULL,
                                  end_time TIME NOT NULL,

                                  max_participants INT NOT NULL,

                                  status VARCHAR(50) NOT NULL,

                                  trainer_id BIGINT NOT NULL,
                                  training_room_id BIGINT NOT NULL,
                                  training_type_id BIGINT NOT NULL,

                                  CONSTRAINT fk_session_trainer
                                      FOREIGN KEY (trainer_id)
                                          REFERENCES trainer(id),

                                  CONSTRAINT fk_session_room
                                      FOREIGN KEY (training_room_id)
                                          REFERENCES training_room(id),

                                  CONSTRAINT fk_session_type
                                      FOREIGN KEY (training_type_id)
                                          REFERENCES training_type(id)
);

CREATE TABLE booking (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,

                         booked_at DATETIME NOT NULL,

                         status VARCHAR(50) NOT NULL,

                         user_id BIGINT NOT NULL,
                         training_session_id BIGINT NOT NULL,

                         CONSTRAINT fk_booking_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id),

                         CONSTRAINT fk_booking_session
                             FOREIGN KEY (training_session_id)
                                 REFERENCES training_session(id),

                         CONSTRAINT uk_user_session
                             UNIQUE (user_id, training_session_id)
);