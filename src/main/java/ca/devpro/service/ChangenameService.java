package ca.devpro.service;

import ca.devpro.assembler.ChangenameAssembler;
import ca.devpro.dto.ChangenameDto;
import ca.devpro.dto.UserDto;
import ca.devpro.entity.Changename;
import ca.devpro.repository.ChangenameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChangenameService {

    @Autowired
    private ChangenameAssembler changenameAssembler;
    @Autowired
    private ChangenameRepository changenameRepository;
    @Autowired
    private ChangenameValidator changeValidator;

    public void createChangename(ChangenameDto dto, String firstName, String lastName) {
        changeValidator.validateAndThrow(dto, firstName, lastName);
        if (!isChanged(dto, firstName, lastName)) {
            return;
        }
        Changename changeName = changenameAssembler.disassemble(dto);
        changenameRepository.save(changeName);
    }

    private boolean isChanged(ChangenameDto dto, String firstName, String lastName) {
        return isFirstNameChanged(dto, firstName) ||
                isLastNameChanged(dto, lastName);
    }

    private boolean isFirstNameChanged(ChangenameDto dto, String firstName) {
        return !dto.getFirstName().equals(firstName);
    }

    private boolean isLastNameChanged(ChangenameDto dto, String lastName) {
        return !dto.getLastName().equals(lastName);
    }

    public List<ChangenameDto> findByUserId(UUID userId) {
        return changenameRepository.findByUserId(userId)
                .stream()
                .map(changenameAssembler::assemble)
                .sorted(Comparator.comparing(ChangenameDto::getCreatedDate)
                        .thenComparing(ChangenameDto::getLastName))
                .collect(Collectors.toList());
    }
}

