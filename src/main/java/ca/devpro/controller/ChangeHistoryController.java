package ca.devpro.controller;


import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.service.ChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/changehistory")
public class ChangeHistoryController {

    @Autowired
    private ChangeHistoryService changeHistoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChangeHistoryDto create(@RequestBody @Valid ChangeHistoryDto dto) {
        return changeHistoryService.create(dto);
    }

    @GetMapping()
    public List<ChangeHistoryDto> findAll() {
        return changeHistoryService.findAll();
    }

    @GetMapping("/{versionId}")
    public ChangeHistoryDto get(@PathVariable("versionId") UUID versionId) {
        return changeHistoryService.get(versionId);
    }

}
