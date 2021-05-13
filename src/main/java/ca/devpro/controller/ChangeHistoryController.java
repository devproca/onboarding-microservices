package ca.devpro.controller;


import ca.devpro.api.ChangeHistoryDto;
import ca.devpro.service.ChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/changes-history")
public class ChangeHistoryController {

    @Autowired
    private ChangeHistoryService changeHistoryService;

    @GetMapping()
    public List<ChangeHistoryDto> findAllChangeHistory() {
        return changeHistoryService.findAll();
    }

    @GetMapping("/{versionId}")
    public ChangeHistoryDto getChangeHistory(@PathVariable("versionId") UUID versionId) {
        return changeHistoryService.get(versionId);
    }

}
