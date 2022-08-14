package com.nathandownes.artcrm.analysis;


import com.nathandownes.artcrm.integrations.Integration;
import com.nathandownes.artcrm.integrations.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }


    @GetMapping(path = "/getAnalysis")
    @CrossOrigin(origins = "*")
    public Analysis getAnalysis() {
        return analysisService.getAnalysis();
    }
}
