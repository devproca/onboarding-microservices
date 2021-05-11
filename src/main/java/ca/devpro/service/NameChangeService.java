package ca.devpro.service;

import ca.devpro.api.NameChangeDto;
import ca.devpro.entity.NameChange;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.NameChangeRepository;
import ca.devpro.assembler.NameChangeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NameChangeService {

    @Autowired
    private NameChangeAssembler nameChangeAssembler;
    @Autowired
    private NameChangeRepository nameChangeRepository;
    @Autowired
    private NameChangeValidator nameChangeValidator;

    public NameChangeDto updateName(NameChangeDto dto) {
        nameChangeValidator.validateAndThrow(dto);
        NameChange entity = nameChangeAssembler.disassemble(dto);
        nameChangeRepository.save(entity);
        return nameChangeAssembler.assemble(entity);
    }

    public List<NameChangeDto> findAll() {
        return nameChangeRepository.findAll()
                .stream()
                .map(nameChangeAssembler::assemble)
                .collect(Collectors.toList());
    }

    public NameChangeDto get(UUID nameChangeId) {
        return nameChangeRepository.findById(nameChangeId)
                .map(nameChangeAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }
}
