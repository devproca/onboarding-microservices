package ca.devpro.service;

import ca.devpro.client.ChangenameClient;
import ca.devpro.repository.ChangenameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChangenameValidatorTest {

    private ChangenameClient changenameClient;
    private ChangenameValidator changenameValidator;

    @BeforeEach
    public void init() {
        testValidateChangeName_whenFindByUserId_shouldReturnError();
    }

    @Test
    public void testValidateChangeName_whenFindByUserId_shouldReturnError() {
        ChangenameRepository changenameRepository = mock(ChangenameRepository.class);
        when(changenameRepository.findByUserId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))).then((invocation) -> {
            String changenameId = invocation.getArgument(0);
            if("123456789".equals(changenameId)) {
                return true;
            }
            return false;
        });
        changenameValidator = new ChangenameValidator(changenameRepository);
    }
}