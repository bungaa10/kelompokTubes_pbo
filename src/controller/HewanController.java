package controller;

import api.HewanApi;
import model.Hewan;

public class HewanController {

    public static void tambah(Hewan h) {
        HewanApi dao = new HewanApi();
        dao.insert(h);
    }
}
