package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/rotas")
public class IAControllers { // <--- Nome da classe no Plural

    private static final Logger logger = LoggerFactory.getLogger(IAControllers.class);

    // O Android vai mandar o JSON para este endereço:
    // POST http://<IP_DO_PC>:8081/api/rotas/calcular
    @PostMapping(path = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calcularRota(@RequestBody String jsonDoAndroid) {

        logger.info("Recebi um pedido de rota do Android: {}", jsonDoAndroid);

        try {
            // 1. Configurar o comando para chamar o Python
            ProcessBuilder pb = new ProcessBuilder(
                    "python3",
                    "/app/python/a_star.py", // Caminho DENTRO do Docker
                    jsonDoAndroid
            );

            pb.redirectErrorStream(true);

            // 2. Iniciar o Python
            Process process = pb.start();

            // 3. Ler a resposta (UTF-8 é importante para acentos)
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder resultadoPython = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                resultadoPython.append(linha);
            }

            // 4. Verificar se o Python acabou bem
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                logger.error("O script Python falhou. Saída: {}", resultadoPython);
                // Pequeno ajuste nas aspas do JSON de erro para ficar válido
                return ResponseEntity.internalServerError()
                        .body("{\"status\": \"erro\", \"mensagem\": \"Erro interno no Python: " + resultadoPython.toString().replace("\"", "'") + "\"}");
            }

            logger.info("Rota calculada com sucesso!");

            // 5. Devolver o JSON prontinho para o Android
            return ResponseEntity.ok(resultadoPython.toString());

        } catch (Exception e) {
            logger.error("Erro grave no servidor Java", e);
            return ResponseEntity.internalServerError()
                    .body("{\"status\": \"erro\", \"mensagem\": \"Erro no Java: " + e.getMessage() + "\"}");
        }
    }
}