package br.com.ana.service;

import br.com.ana.config.RedisCache;
import br.com.ana.model.ChavePix;
import br.com.ana.model.TipoChave;
import br.com.ana.model.TipoPessoa;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@ApplicationScoped
public class DictService {

    @ConfigProperty(name = "pix.chave")
    private String chave;
    @ConfigProperty(name = "pix.ispb")
    private String ispb;
    @ConfigProperty(name = "pix.cnpj")
    private String cpfCnpj;
    @ConfigProperty(name = "pix.nome")
    private String nome;

    @Inject
    RedisCache redisCache;

    public ChavePix buscarDetalhesChavePix(String key){
        var chavePix = buscarChaveCached(key);
        if (Objects.isNull(chavePix)){
            var chaveFake = buscarChave(key);
            redisCache.set(key, chaveFake);
            return chaveFake;
        }
        return chavePix;
    }

    private ChavePix buscarChaveCached(String key){
        var chavePix = redisCache.get(key);
        Log.infof("Chave encontrada no cache %s", chavePix);
        return chavePix;


    }


    public ChavePix buscarChave(String chavePix){

        return new ChavePix(TipoChave.EMAIL,
                chave,
                ispb,
                TipoPessoa.FISICA,
                cpfCnpj,
                nome,
                LocalDateTime.now());
    }
}
