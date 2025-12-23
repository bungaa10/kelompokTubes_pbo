package model;

public class Layanan {
    private int id;
    private String nama;
    private int harga;
    

    public Layanan(int id, String nama, int harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
       
    }

    public int getId() { 
        return id; 
    }
    public String getNama() {
        return nama; 
    }
    public int getHarga() { 
        return harga; 
    }
}
