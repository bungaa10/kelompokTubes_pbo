package model;

public class Hewan {
    private int idHewan;
    private String nama;
    private String jenis;
    private int umur;
    private String pemilik;

    public Hewan(int idHewan, String nama, String jenis, int umur , String pemilik) {
        this.idHewan = idHewan;
        this.nama = nama;
        this.jenis = jenis;
        this.umur = umur;
        this.pemilik = pemilik;
    }

    public int getIdHewan() {
        return idHewan;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public int getUmur() {
        return umur;
    }
    public String getPemilik() {
        return pemilik;
    }
}
