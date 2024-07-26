package br.com.ana.api;

import br.com.ana.model.ChavePix;
import br.com.ana.service.DictService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("v1/chaves/")
public class ChavePixResource {

    @Inject
    DictService dictService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{chavePix}")
    public Response buscar(@PathParam("chavePix") String chavePix){
        var chaveCached = dictService.buscarDetalhesChavePix(chavePix);
        return Response.ok(chaveCached).build();

    }


}
