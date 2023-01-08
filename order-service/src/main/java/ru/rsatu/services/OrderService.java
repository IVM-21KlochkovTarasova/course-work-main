package ru.rsatu.services;

import org.jboss.logging.Logger;
import ru.rsatu.dto.ContractDTO;
import ru.rsatu.models.Contract;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ResponseProcessingException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {

    private static final Logger LOG = Logger.getLogger(OrderService.class);

    @Transactional
    public ContractDTO savePurchase(ContractDTO purchaseInfoDTO){
        if (checkPurchaseExists(purchaseInfoDTO.getUserId(), purchaseInfoDTO.getStudyProgramId())){
            throw new BadRequestException("Уже куплено");
        }

        Contract contract = new Contract();
        contract.setUserId(purchaseInfoDTO.getUserId());
        contract.setStudyProgramId(purchaseInfoDTO.getStudyProgramId());
        contract.persist();
        LOG.info(String.format("Выполнена покупка программы %d польлзователем %s",
                contract.getStudyProgramId(),
                contract.getUserId()));
        return ContractDTO.builder()
                .contractId(contract.getContractId())
                .userId(contract.getUserId())
                .studyProgramId(contract.getStudyProgramId())
                .build();
    }

    @Transactional
    public void setCompleteContract(ContractDTO purchaseInfoDTO){
        Contract contract = Contract.findById(purchaseInfoDTO.getContractId());
        contract.setIsComplete(true);
        contract.persist();
        LOG.info(String.format("Пройдена программа %d пользователем %s",
                contract.getStudyProgramId(),
                contract.getUserId()));
    }

    public List<ContractDTO> getContractsByUserId(String userId){
        List<Contract> contracts = Contract.find("userId = ?1", userId).list();
        return contracts.stream().map(ContractDTO::new).toList();
    }

    public ContractDTO getContractsForStartExam(String userId, Long studyProgramId, Boolean isComplete){
        Contract contract = Contract.find("userId = ?1 and studyProgramId = ?2 and isComplete = ?3",
                userId, studyProgramId, isComplete).singleResult();
        return new ContractDTO(contract);
    }

    public List<ContractDTO> getAllContracts(Integer pageNumber, Integer pageSize){
        List<Contract> contracts = Contract.findAll().page(pageNumber, pageSize).list();
        return contracts.stream().map(ContractDTO::new).collect(Collectors.toList());
    }

    private boolean checkPurchaseExists(String userId, Long programId){
        return Contract.find("userId = ?1 and studyProgramId = ?2 and isComplete = false", userId, programId).count() > 0;
    }
}
