package com.medicinecheck.controller;

import com.medicinecheck.model.Medicine;
import com.medicinecheck.model.Interaction;
import com.medicinecheck.service.MedicineService;
import com.medicinecheck.service.AIService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicineController {

    private final MedicineService medicineService;
    private final AIService aiService;

    public MedicineController(MedicineService medicineService, AIService aiService) {
        this.medicineService = medicineService;
        this.aiService = aiService;
    }

    // Endpoint 1: Search medicines
    @GetMapping("/search")
    public List<Medicine> searchMedicines(@RequestParam String query) {
        return medicineService.searchMedicines(query);
    }

    // Endpoint 2: AI-powered search
    @PostMapping("/ai-search")
    public List<Medicine> aiSearch(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        return aiService.aiSearch(query);
    }

    // Endpoint 3: Check interactions
    @PostMapping("/check-interaction")
    public List<Interaction> checkInteractions(@RequestBody List<String> medicineNames) {
        return medicineService.checkInteractions(medicineNames);
    }

    // Bonus: Get all medicines
    @GetMapping("/all")
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }
}