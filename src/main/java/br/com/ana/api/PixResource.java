package br.com.ana.api;

import br.com.ana.model.ChavePix;
import br.com.ana.model.Pix;
import br.com.ana.service.DictService;
import br.com.ana.service.PixService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.Objects;

@Path("/v1/pix")
public class PixResource {

    @Inject
    DictService dictService;

    @Inject
    PixService pixService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/linha")
    public Response gerarLinhaDigitavel(final Pix pix){

        var chavePix = dictService.buscarChave(pix.chave());
        if (Objects.nonNull(chavePix)){
           return Response
                   .ok(pixService.gerarLinhaDigitavel(chavePix, pix.valor(), pix.cidadeRemetente()))
                   .build();
        }
        return null;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/qrcode/{uuid}")
    public Response qrCode(@PathParam("uuid") String uuid) throws IOException {
        //TODO Adicionar controle de Exceções
        return Response.ok(pixService.gerarQrCode(uuid)).build();
    }


}
