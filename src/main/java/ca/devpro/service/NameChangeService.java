package ca.devpro.service;

import ca.devpro.api.NameChangeDto;
import ca.devpro.api.PhoneDto;
import ca.devpro.api.UserDto;
import ca.devpro.entity.NameChange;
import ca.devpro.service.UserService;
import ca.devpro.assembler.PhoneAssembler;
import ca.devpro.entity.Phone;
import ca.devpro.entity.User;
import ca.devpro.exception.NotFoundException;
import ca.devpro.repository.NameChangeRepository;
import ca.devpro.assembler.NameChangeAssembler;
import ca.devpro.service.NameChangeValidator;
import ca.devpro.repository.PhoneRepository;
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

    public NameChangeDto processNameChange(NameChangeDto dto) {
        nameChangeValidator.validateAndThrow(dto);
        NameChange entity = nameChangeAssembler.disassemble(dto);
        nameChangeRepository.save(entity);
        return nameChangeAssembler.assemble(entity);
    }
//    public List<NameChangeDto> findAll(UUID userId) {
//        return phoneRepository.findAll()
//                .stream()
//                .map(phoneAssembler::assemble)
//                .collect(Collectors.toList());
//    }
}
