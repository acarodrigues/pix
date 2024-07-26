package br.com.ana.service;

import br.com.ana.model.ChavePix;
import br.com.ana.model.LinhaDigitavel;
import br.com.ana.model.qrcode.DadosEnvioPix;
import br.com.ana.model.qrcode.PixCopiaCola;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH = "c:/tmp/qrcode";

    public BufferedInputStream gerarQrCode (final String uuid) throws IOException {
        var imagePath = QRCODE_PATH +  "/" + uuid + ".png";
        //TODO Recuperar da cache
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(imagePath))) {
            return inputStream;
        } finally {
            Files.delete(Paths.get(imagePath));
        }
    }

    public LinhaDigitavel gerarLinhaDigitavel(final ChavePix chavePix, BigDecimal valor, String cidadeRemetente){
        var qrCode = new PixCopiaCola(new DadosEnvioPix(chavePix, valor, cidadeRemetente));
        var uuid = UUID.randomUUID().toString();
        var imagePath = QRCODE_PATH +  "/" + uuid + ".png";
        qrCode.save(Path.of(imagePath));
        // TODO IMPLEMENTAR CACHE
        String qrCodeString = qrCode.toString();
        return new LinhaDigitavel(qrCodeString, uuid);

    }

}
