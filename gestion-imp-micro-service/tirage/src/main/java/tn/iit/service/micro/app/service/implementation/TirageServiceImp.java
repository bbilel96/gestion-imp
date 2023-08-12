package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.iit.service.micro.app.model.Tirage;
import tn.iit.service.micro.app.proxy.personne.ClasseFeignClient;
import tn.iit.service.micro.app.proxy.personne.PersonneFeignClient;
import tn.iit.service.micro.app.repository.TirageRepository;
import tn.iit.service.micro.app.service.TirageService;
import tn.iit.service.micro.dto.classe.ClasseDto;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.exception.classexception.BadRequestException;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;
import tn.iit.service.micro.utilenum.TirageStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
@Slf4j
public class TirageServiceImp implements TirageService {
    private final TirageRepository tirageRepository;
    private final PersonneFeignClient enseignantFeignClient;
    private final PersonneFeignClient agentTirageFeignClient;

    private final ClasseFeignClient classeFeignClient;
    @Value("${file.path}")
    private String pdfPath;


    @Override
    public ResponseMessage addTirage(Tirage tirage, String id, String classeId, MultipartFile pdf) throws IOException {
        log.info("Starting addTirage(tirage: {}, classId: {}).", tirage, classeId);

        MatiereDto foundedMatiere = classeFeignClient.getById(tirage.getMatiereId()).getBody();

        EnseignantDto foundedEnseignant = enseignantFeignClient.getByIdEns(tirage.getEnseignantId()).getBody();

        List<ClasseDto> foundedClasse = this.classeFeignClient.findAllByMatiereIdWithoutPage(tirage.getMatiereId()).getBody();
        foundedClasse = foundedClasse.stream().filter(classeDto -> classeDto.getId().equals(classeId)).toList();
        if (foundedClasse.isEmpty()) {
            throw new ObjectNotFound("Matiere n'est pas affecter dans cetter classe");
        }
        if (foundedClasse.get(0).getNbEtudiant() < tirage.getNbTirage()) {
            throw new ObjectNotFound("Nombre de d'étudiant saisie doit être inférieur ou egale de nombre d'étudiant dans ce classe");
        }

        Tirage tirageWithPath = this.uploadPdf(pdf, tirage, classeId);
        tirageWithPath.setEnseignantId(foundedEnseignant.getId());
        tirageWithPath.setClasseId(classeId);
        tirageWithPath.setMatiereId(foundedMatiere.getId());
        tirageRepository.save(tirageWithPath);
        log.info("addTirage(tirage: {}, id: {}) finished.", tirage, classeId);

        return new ResponseMessage("La de demande de tirage est ajouté.", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage changeTirageStatus(TirageStatus tirageStatus, String id, String agentId) {

        log.info("Starting changeTirageStatus(tirageStatus: {}, agentId: {}).", tirageStatus, agentId);
        AgentDto foundedAgent = enseignantFeignClient.getByIdAgent(agentId).getBody();
        Tirage foundedTirage = this.getById(id);
        foundedTirage.setStatus(tirageStatus);
        foundedTirage.setAgentId(foundedAgent.getId());
        this.tirageRepository.save(foundedTirage);
        log.info("changeTirageStatus(tirageStatus: {}, agentId: {}) finished.", tirageStatus, agentId);
        return new ResponseMessage("Status de tirage est changé avec success", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage deleteTirage(String id, String userId) {
        log.info("Starting deleteTirage(id: {}, userId: {}).", id, userId);
        enseignantFeignClient.getByIdEns(userId).getStatusCode();
        Tirage foundedTirage = this.getById(id);
        this.tirageRepository.delete(foundedTirage);
        log.info("deleteTirage(id: {}, userId: {}) finished.", id, userId);
        return new ResponseMessage("Demande tirage numero: " + foundedTirage.getId() + "  est supprimer.", ResponseBehavior.SUCCESS);


    }
    @Transactional
    public ResponseMessage deleteTirageByClasse(String id) {
        this.tirageRepository.deleteByClasseId(id);
        return new ResponseMessage("Demande tirage numero: " + id + "  est supprimer.", ResponseBehavior.SUCCESS);


    }
    @Transactional
    public ResponseMessage deleteTirage(String id) {
        this.tirageRepository.deleteById(id);
        return new ResponseMessage("Demande tirage numero: " + id + "  est supprimer.", ResponseBehavior.SUCCESS);


    }
    @Transactional
    public ResponseMessage deleteTirageByMatiere(String id) {
        this.tirageRepository.deleteByMatiereId(id);
        return new ResponseMessage("Demande tirage numero: " + id + "  est supprimer.", ResponseBehavior.SUCCESS);


    }

    @Override
    public Page<Tirage> getAll(int page, int size) {
        log.info("Starting getAll(page: {}, size: {}).", page, size);
        return this.tirageRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Tirage> getAllByIdAgent(String id, int page, int size) {
        log.info("Starting getAllByIdAgent(id: {}, page: {}, size: {}).", id, page, size);
        return this.tirageRepository.findAllByAgentId(id, PageRequest.of(page, size)).getContent();
    }

    @Override
    public Page<Tirage> getAllByIdEnseignant(String id, int page, int size) {
        log.info("Starting getAllByIdEnseignant(id: {}, page: {}, size: {}).", id, page, size);
        return this.tirageRepository.findAllByEnseignantId(id, PageRequest.of(page, size));
    }

    @Override
    public Tirage getById(String id) {
        log.info("Starting getById(id: {}).", id);
        log.error(this.tirageRepository.findById(id).toString());
        return this.tirageRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tirage of id: {}, does not exist.", id);
                    throw new ObjectNotFound("Tirage n'existe pas.");
                });

    }


    @Override
    public ResponseMessage updateTirage(MultipartFile pdf, Tirage tirage, String id) throws IOException {
        log.info("Starting updateTirage({}, id: {}).", tirage, id);
        Tirage foundedTirage = this.getById(id);
        Tirage tirageWithPath = this.saveFile(pdf, tirage, id);
        foundedTirage.update(tirageWithPath);
        this.tirageRepository.save(foundedTirage);
        log.info("updateTirage({}, id: {}) finished.", tirage, id);
        return new ResponseMessage("Tirage est modifier avec success.", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<Tirage> getAllByIdAgentWithPage(String agentId, int page, int size) {
        Page<Tirage> foundedTirage = this.tirageRepository.findAllByAgentId(agentId, PageRequest.of(page, size));
        foundedTirage.forEach(tirage -> {
            tirage.setEnseignantDto(enseignantFeignClient.getByIdEns(tirage.getEnseignantId()).getBody());
            tirage.setMatiereDto(classeFeignClient.getById(tirage.getMatiereId()).getBody());
            tirage.setAgentDto(agentTirageFeignClient.getByIdAgent(tirage.getAgentId()).getBody());
        });
        return foundedTirage;
    }


    public Tirage uploadPdf(MultipartFile pdf, Tirage tirage, String classeId) throws IOException {
        log.info("Starting uploadPdf");
        File userDirectory = new File(pdfPath);
        tirageRepository.getTirageByClasseIdAndMatiereId(classeId, tirage.getMatiereId())
                .forEach(tirage1 ->
                        {
                            if (tirage1.getMatiereId().equals(tirage.getMatiereId()) && tirage1.getClasseId().equals(classeId)){
                                if (!tirage1.getStatus().equals(TirageStatus.IMPRIMER)){
                                    throw new BadRequestException("le fichier pdf de cette matiere existe deja.");
                                }
                            }
                        }
                );
        /*for (final File fileEntry : userDirectory.listFiles()) {
            if (fileEntry.getName().contains(classeId + "_" + tirage.getMatiereId())) {
                throw new BadRequestException("le fichier pdf de cette matiere existe deja.");
            }
        }*/
        return this.saveFile(pdf, tirage, classeId);

    }

    public Tirage saveFile(MultipartFile pdf, Tirage tirage, String classeId) {
        File userDirectory = new File(pdfPath);
        try {
            if (userDirectory.exists()) {
                if (FilenameUtils.getExtension(pdf.getOriginalFilename()).equals("pdf")) {
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Path fileStorage = Paths.get(pdfPath + "/" + classeId +"_" + tirage.getMatiereId()+".pdf");
                    log.info("{}", fileStorage);
                    copy(pdf.getInputStream(), fileStorage, REPLACE_EXISTING);
                    log.info("Pdf saved successfully .");
                    tirage.setDocumentPdf(pdf.getOriginalFilename());
                    tirage.setNamedPdf(fileStorage.getFileName().toString());
                    return tirage;
                } else {
                    throw new FileNotFoundException("le Fichier doit être un pdf");
                }
            }
            throw new BadRequestException("Il y a une problem dans la safeguard de fichier pdf.");
        } catch (IOException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }


    public Resource downloadPdf(String path, String id) throws IOException {
        Tirage foundedTirage = this.tirageRepository.findByIdAndDocumentPdf(id, path)
                .orElseThrow(() -> new ObjectNotFound("Fichier n'existe pas."));
        Path filePath = get(pdfPath).toAbsolutePath().normalize().resolve(foundedTirage.getNamedPdf());
        if (!Files.exists(filePath)) {
            throw new ObjectNotFound("Pdf n'existe pas.");
        }
        return new UrlResource(filePath.toUri());
    }

    @Override
    public Page<Tirage> getAllWithAllData(int page, int size) {
        Page<Tirage> foundedTirage = this.tirageRepository.findAll(PageRequest.of(page, size));
        foundedTirage
                .forEach(tirage -> {
                    tirage.setEnseignantDto(enseignantFeignClient.getByIdEns(tirage.getEnseignantId()).getBody());

                    tirage.setMatiereDto(classeFeignClient.getById(tirage.getMatiereId()).getBody());
                    if (tirage.getAgentId() != null) {
                        tirage.setAgentDto(agentTirageFeignClient.getByIdAgent(tirage.getAgentId()).getBody());

                    } else {
                        tirage.setAgentDto(new AgentDto());
                    }
                });
        return foundedTirage;
    }

    @Override
    public Tirage getByIdWithAllData(String id) {
        Tirage foundedTirage = this.getById(id);
        if (foundedTirage.getAgentId() != null) {
            foundedTirage.setAgentDto(this.agentTirageFeignClient.getByIdAgent(foundedTirage.getAgentId()).getBody());
        }
        foundedTirage.setMatiereDto(this.classeFeignClient.getMatiereByIdWithoutData(foundedTirage.getMatiereId()).getBody());
        foundedTirage.setClasseDto(this.classeFeignClient.getClasseById(foundedTirage.getClasseId()).getBody());

       return foundedTirage;
    }

}
