CREATE DATABASE klinik_hewan;
USE klinik_hewan;

CREATE TABLE hewan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    jenis VARCHAR(100),
    umur INT
);
CREATE TABLE layanan (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    nama_layanan VARCHAR(100),
    harga INT
);

