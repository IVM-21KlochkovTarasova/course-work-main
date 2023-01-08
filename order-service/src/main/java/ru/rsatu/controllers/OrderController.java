package ru.rsatu.controllers;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import ru.rsatu.dto.ContractDTO;
import ru.rsatu.services.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/contract")
@Produces("application/json")
@Consumes("application/json")
public class OrderController {

    @Inject
    OrderService orderService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Counted(name = "purchase_count", description = "Количество попыток покупок.")
    @RolesAllowed({"default-roles-prof"})
    public Response createContract(ContractDTO contractDTO) {
        contractDTO.setUserId(jwt.getSubject());
        contractDTO.setUsername(jwt.getName());
        ContractDTO createdPurchase = orderService.savePurchase(contractDTO);
        return Response.ok(createdPurchase).build();
    }

    @PUT
    @Path("complete")
    @RolesAllowed({"default-roles-prof"})
    public Response setCompleteContract(ContractDTO contractDTO) {
        orderService.setCompleteContract(contractDTO);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed({"default-roles-prof"})
    @Timed(name = "get_contracts_timer", description = "Скорость выгрузки пользовательских контрактов", unit = MetricUnits.MILLISECONDS)
    public Response getContract(@QueryParam("userId") String userId,
                                @QueryParam("studyProgramId") Long studyProgramId,
                                @QueryParam("isComplete") Boolean isComplete) {
        ContractDTO userContract = orderService.getContractsForStartExam(userId, studyProgramId, isComplete);
        return Response.ok(userContract).build();
    }

    @GET
    @Path("user_contracts")
    @RolesAllowed({"default-roles-prof"})
    @Timed(name = "get_contracts_timer", description = "Скорость выгрузки пользовательских контрактов", unit = MetricUnits.MILLISECONDS)
    public Response getUserContracts(@QueryParam("userId") String userId) {
        List<ContractDTO> userContracts = orderService.getContractsByUserId(userId);
        return Response.ok(userContracts).build();
    }

    @GET
    @Path("all")
    @RolesAllowed({"admin"})
    @Timed(name = "get_contracts_timer", description = "Скорость выгрузки пользовательских контрактов", unit = MetricUnits.MILLISECONDS)
    public Response getAllContracts(@QueryParam("pageNumber") Integer pageNumber,
                                @QueryParam("pageSize") Integer pageSize) {
        return Response.ok(orderService.getAllContracts(pageNumber, pageSize)).build();
    }

}
