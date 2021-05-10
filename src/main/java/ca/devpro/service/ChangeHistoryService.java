package ca.devpro.service;

import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.assembler.ChangeHistoryAssembler;
import ca.devpro.entity.ChangeHistory;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.ChangeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChangeHistoryService {
    @Autowired
    private ChangeHistoryAssembler changeHistoryAssembler;
    @Autowired
    private ChangeHistoryRepository changeHistoryRepository;
    @Autowired
    private ChangeHistoryValidator changeHistoryValidator;

    public ChangeHistoryDto create(ChangeHistoryDto dto) {
        changeHistoryValidator.validateAndThrow(dto);
        ChangeHistory entity = changeHistoryAssembler.disassemble(dto);
        changeHistoryRepository.save(entity);
        return changeHistoryAssembler.assemble(entity);
    }
    public List<ChangeHistoryDto> findAll() {
        return changeHistoryRepository.findAll()
                .stream()
                .map(changeHistoryAssembler::assemble)
                .collect(Collectors.toList());
    }

    public ChangeHistoryDto get(UUID versionId) {
        return changeHistoryRepository.findById(versionId)
                .map(changeHistoryAssembler::assemble)
                .orElseThrow(() -> new NotFoundException());
    }
}
