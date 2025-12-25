package model;

import java.sql.*;
import java.util.*;

public class Pet {
    private int idHewan;
    private String nama;
    private String jenis;
    private String umur;
    private String pemilik;
    private String layanan;
    private String harga;

    public Pet(int idHewan, String Nama, String jenis, String umur, String pemilik, String layanan, String harga) {
        this.idHewan = idHewan;
        this.nama = nama;
        this.jenis = jenis;
        this.umur = umur;
        this.pemilik = pemilik;
        this.layanan = layanan;
        this.harga = harga;
    }

    public int getId() { 
        return idHewan; 
    }
    public String getnama() { 
        return nama; 
    }
    public String getjenis() { 
        return jenis; 
    }
    public String getumur() { 
        return umur; 
    }
    public String getpemilik() { 
        return pemilik; 
    }
    public String getlayanan() { 
        return layanan; 
    }
    public String getharga() { 
        return harga; 
    }

    public int getidhewan() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getidhewan'");
    }


    
}