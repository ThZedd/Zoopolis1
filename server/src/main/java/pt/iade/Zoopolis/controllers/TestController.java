package pt.iade.Zoopolis.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.InetAddress;

@RestController
public class TestController {

    @GetMapping("/quem-sou-eu")
    public String checkInstance() {
        String apiInstanceName;
        String dbIpAddress = "Desconhecido";

        // 1. Quem é a API?
        try {
            apiInstanceName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            apiInstanceName = "API Desconhecida";
        }

        // 2. Quem é a DB? (CRIANDO NOVA CONEXÃO MANUALMENTE PARA FORÇAR O HAPROXY)
        // NOTA: Isto é mau para performance em produção, mas ótimo para este teste!
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://haproxy:5432/postgres",
                "postgres",
                "ThZ3d1112");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT inet_server_addr()::text")) {

            if (rs.next()) {
                dbIpAddress = rs.getString(1);
            }
        } catch (Exception e) {
            return "Erro DB: " + e.getMessage();
        }

        return String.format(
                "📍 API ID: %s \n🎲 DB IP: %s (Balanceado)",
                apiInstanceName,
                dbIpAddress
        );
    }
}