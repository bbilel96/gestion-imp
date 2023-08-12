package tn.iit.service.micro.app.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tn.iit.service.micro.app.model.Tirage;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.TirageStatus;

import java.io.IOException;
import java.util.List;

public interface TirageService {
    ResponseMessage addTirage(Tirage tirage, String id,String classeId, MultipartFile pdf) throws IOException;

    ResponseMessage changeTirageStatus(TirageStatus tirageStatus, String id, String agentId);

    ResponseMessage deleteTirage(String id, String userId);

    Page<Tirage> getAll(int page, int size);

    List<Tirage> getAllByIdAgent(String id, int page, int size);
    Page<Tirage> getAllByIdEnseignant(String id, int page, int size);


    Tirage getById(String id);

    ResponseMessage updateTirage(MultipartFile pdf, Tirage tirage, String id) throws IOException;

    Page<Tirage> getAllByIdAgentWithPage (String agentId, int page, int size);

    Page<Tirage> getAllWithAllData(int page, int size);

    Tirage getByIdWithAllData (String id);
}
