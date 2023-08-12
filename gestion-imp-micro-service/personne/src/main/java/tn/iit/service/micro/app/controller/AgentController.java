package tn.iit.service.micro.app.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.model.Agent;
import tn.iit.service.micro.app.service.implementation.AgentServiceImp;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.personne.AgentRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;

@RestController
@RequestMapping("agent")
@RequiredArgsConstructor

public class AgentController {
    private final AgentServiceImp agentServiceImp;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createAgent(@RequestBody @Validated(Create.class) AgentRequest agentRequest) {
        return new ResponseEntity<>(agentServiceImp.createUtilisateur(modelMapper.map(agentRequest, Agent.class)), HttpStatus.OK);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<AgentDto>> findAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                this.agentServiceImp.getAll(page, size)
                        .map(agent -> this.modelMapper.map(agent, AgentDto.class)),
                HttpStatus.OK
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<AgentDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(modelMapper.map(agentServiceImp.getById(id), AgentDto.class), HttpStatus.OK);
    }
}
