package api;

import config.Database;   // ← INI PENTING
import model.Layanan;

import java.sql.*;
import java.util.ArrayList;

public class LayananApi {

    public void insert(Layanan l) {
        try {
            String sql = "INSERT INTO layanan (nama_layanan, harga) VALUES (?,?)";
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, l.getNama());
            ps.setInt(2, l.getHarga());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Layanan l) {
        try {
            String sql = "UPDATE layanan SET nama_layanan=?, harga=? WHERE id_layanan=?";
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, l.getNama());
            ps.setInt(2, l.getHarga());
            ps.setInt(3, l.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM layanan WHERE id_layanan=?";
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Layanan> getAll() {
        ArrayList<Layanan> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM layanan";
            Connection c = Database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Layanan(
                        rs.getInt("id_layanan"),
                        rs.getString("nama_layanan"),
                        rs.getInt("harga")
                       
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
