-- 🔥 Elimina l'utente se esiste (utile per reinstallazioni future)
DROP USER IF EXISTS 'librarian'@'localhost';

-- 👤 Crea l'utente con la password corretta
CREATE USER 'librarian'@'localhost' IDENTIFIED BY 'Libr@ry2026';

-- 🗄 Concede TUTTI i permessi sul database library_db
GRANT ALL PRIVILEGES ON library_db.* TO 'librarian'@'localhost';

-- 🔄 Applica immediatamente i permessi
FLUSH PRIVILEGES;

-- 🧪 (Opzionale) Verifica dei permessi
-- SHOW GRANTS FOR 'librarian'@'localhost';



-- Creazione del database principale del progetto
CREATE DATABASE library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Seleziona il database su cui lavorare
USE library_db;


-- Creazione della tabella 'book' basata sul model Book
CREATE TABLE book (
                      id INT AUTO_INCREMENT PRIMARY KEY,     -- Chiave primaria con autoincrement
                      title VARCHAR(255) NOT NULL,           -- Titolo del libro (obbligatorio)
                      author VARCHAR(255) NOT NULL,          -- Autore (obbligatorio)
                      publisher VARCHAR(255) NOT NULL        -- Casa editrice (obbligatoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Creazione della tabella 'member' basata sul model Member
CREATE TABLE member (
                        id INT AUTO_INCREMENT PRIMARY KEY,     -- Chiave primaria con autoincrement
                        first_name VARCHAR(100) NOT NULL,      -- Nome (obbligatorio)
                        last_name VARCHAR(100) NOT NULL,       -- Cognome (obbligatorio)
                        city VARCHAR(100) NOT NULL,            -- Città (obbligatoria)
                        phone VARCHAR(20) NOT NULL             -- Numero di telefono (obbligatorio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

create table loans (
id INT AUTO_INCREMENT PRIMARY KEY,
book_id int not null,
member_id int not null,
loan_date date not null,
return_date date,
foreign key (book_id) references `book`(id),
foreign key (member_id) references `member`(id)
)



-- Comando per avviare il server (mvn spring-boot:run)