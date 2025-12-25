package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.Database;
import model.Pet;

public class PetController {

    // ===== READ =====
    public static ArrayList<Pet> getAll() {
        ArrayList<Pet> list = new ArrayList<>();
        try {
            Connection c = Database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM hewan");
            while (rs.next()) {
                list.add(new Pet(
                    rs.getInt("idHewan"),
                    rs.getString("nama"),
                    rs.getString("jenis"),
                    rs.getString("umur"),
                    rs.getString("pemilik"),
                    rs.getString("layanan"),
                    rs.getString("harga")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== CREATE =====
    public static void insert(String nama, String jenis, String umur,
                          String pemilik, String layanan, int harga) {

    String sql = """
        INSERT INTO hewan (nama, jenis, umur, pemilik, layanan, harga)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    try (Connection c = Database.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, nama);
        ps.setString(2, jenis);
        ps.setString(3, umur);
        ps.setString(4, pemilik);
        ps.setString(5, layanan);
        ps.setInt(6, harga);

        ps.executeUpdate(); // ⬅️ INI WAJIB

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // ===== UPDATE =====
    public void update(Pet h) {
    try {
        Connection c = Database.getConnection();
        String sql = "UPDATE hewan SET nama=?, jenis=?, umur=?, pemilik=? WHERE idHewan=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, h.getnama());
        ps.setString(2, h.getjenis());
        ps.setString(3, h.getumur());
        ps.setString(4, h.getpemilik());
        ps.setInt(5, h.getidhewan());
        ps.setString(6, h.getlayanan());
        ps.setString(7, h.getharga());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // ===== DELETE =====
    public static void delete(int id) {
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(
                "DELETE FROM hewan WHERE idHewan=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, String text, String text2, String text3, String text4, String string, int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
