package controller;

import dao.HewanDao;
import model.Hewan;

public class HewanController {

    public static void tambah(Hewan h) {
        HewanDao dao = new HewanDao();
        dao.insert(h);
    }
}
