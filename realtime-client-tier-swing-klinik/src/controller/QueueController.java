package controller;
import view.MainFrame;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class QueueController {

    private MainFrame view;
    private WebSocketClient socket;

    public QueueController(MainFrame view) {
        this.view = view;
        connect();
    }

    private void connect() {
        try {
            socket = new WebSocketClient(new URI("ws://localhost:8080")) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    view.log("✅ Terhubung ke WebSocket");
                }

                @Override
                public void onMessage(String message) {
                    view.log("📨 " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    view.log("❌ Koneksi terputus");
                }

                @Override
                public void onError(Exception ex) {
                    view.log("⚠️ Error: " + ex.getMessage());
                }
            };
            socket.connect();
        } catch (Exception e) {
            view.log("❌ Gagal koneksi WebSocket");
        }
    }
}
