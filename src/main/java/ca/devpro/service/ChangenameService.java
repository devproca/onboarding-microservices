package ca.devpro.service;

import ca.devpro.assembler.ChangenameAssembler;
import ca.devpro.dto.ChangenameDto;
import ca.devpro.entity.Changename;
import ca.devpro.repository.ChangenameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangenameService {
    @Autowired
    private ChangenameAssembler changenameAssembler;
    @Autowired
    private ChangenameRepository changenameRepository;
//    @Autowired
//    private ChangenameValidator changeValidator;

    public void createChangename(ChangenameDto dto) {
        System.out.println("Check 5: " + dto.getUserId());
        System.out.println("Check 6: " + dto.getLastName());
        Changename changeName = changenameAssembler.disassemble(dto);
        changenameRepository.save(changeName);

    }

}

