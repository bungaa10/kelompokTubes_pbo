package dao;

import config.Database;
import model.Hewan;

import java.sql.*;
import java.util.ArrayList;

public class HewanDao {

    public void insert(Hewan h) {
        try {
            Connection c = Database.getConnection();
            String sql = "INSERT INTO hewan (nama, jenis, umur, pemilik) VALUES (?,?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, h.getNama());
            ps.setString(2, h.getJenis());
            ps.setInt(3, h.getUmur());
            ps.setString(4, h.getPemilik());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Hewan> getAll() {
        ArrayList<Hewan> list = new ArrayList<>();
        try {
            Connection c = Database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM hewan");
            while (rs.next()) {
                list.add(new Hewan(
                    rs.getInt("idHewan"),
                    rs.getString("nama"),
                    rs.getString("jenis"),
                    rs.getInt("umur"),
                    rs.getString("pemilik")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void update(Hewan h) {
    try {
        Connection c = Database.getConnection();
        String sql = "UPDATE hewan SET nama=?, jenis=?, umur=?, pemilik=? WHERE idHewan=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, h.getNama());
        ps.setString(2, h.getJenis());
        ps.setInt(3, h.getUmur());
        ps.setString(4, h.getPemilik());
        ps.setInt(5, h.getIdHewan());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void delete(int id) {
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
}
