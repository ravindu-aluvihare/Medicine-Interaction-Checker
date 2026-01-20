package com.medicinecheck.service;

import com.medicinecheck.model.Medicine;
import com.medicinecheck.model.Interaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    // In-memory database of medicines
    private final List<Medicine> medicines = Arrays.asList(
            new Medicine(1L, "Aspirin", "Acetylsalicylic acid", "Pain reliever",
                    Arrays.asList("pain", "headache", "fever", "painkiller")),
            new Medicine(2L, "Ibuprofen", "Ibuprofen", "Pain reliever",
                    Arrays.asList("pain", "inflammation", "fever", "painkiller")),
            new Medicine(3L, "Warfarin", "Warfarin", "Blood thinner",
                    Arrays.asList("blood", "clot", "anticoagulant")),
            new Medicine(4L, "Lisinopril", "Lisinopril", "Blood pressure",
                    Arrays.asList("hypertension", "blood pressure", "heart")),
            new Medicine(5L, "Metformin", "Metformin", "Diabetes",
                    Arrays.asList("diabetes", "sugar", "blood glucose")),
            new Medicine(6L, "Atorvastatin", "Atorvastatin", "Cholesterol",
                    Arrays.asList("cholesterol", "statin", "lipid")),
            new Medicine(7L, "Omeprazole", "Omeprazole", "Acid reducer",
                    Arrays.asList("acid", "reflux", "heartburn", "stomach")),
            new Medicine(8L, "Amoxicillin", "Amoxicillin", "Antibiotic",
                    Arrays.asList("antibiotic", "infection", "bacteria")),
            new Medicine(9L, "Simvastatin", "Simvastatin", "Cholesterol",
                    Arrays.asList("cholesterol", "statin", "lipid")),
            new Medicine(10L, "Acetaminophen", "Paracetamol", "Pain reliever",
                    Arrays.asList("pain", "fever", "tylenol", "painkiller"))
    );

    // In-memory database of interactions
    private final List<Interaction> interactions = Arrays.asList(
            new Interaction(
                    Arrays.asList("Aspirin", "Warfarin"),
                    "severe",
                    "Increased risk of bleeding. Both medications affect blood clotting.",
                    "Consult your doctor immediately. Do not combine without medical supervision."
            ),
            new Interaction(
                    Arrays.asList("Aspirin", "Ibuprofen"),
                    "moderate",
                    "May reduce the cardioprotective effects of aspirin and increase stomach bleeding risk.",
                    "Take ibuprofen at least 2 hours after aspirin. Consult your doctor."
            ),
            new Interaction(
                    Arrays.asList("Simvastatin", "Atorvastatin"),
                    "moderate",
                    "Both are statins. Taking together may increase risk of muscle problems.",
                    "Usually not prescribed together. Consult your doctor."
            )
    );

    // Search medicines by query
    public List<Medicine> searchMedicines(String query) {
        String lowerQuery = query.toLowerCase();
        return medicines.stream()
                .filter(med ->
                        med.getName().toLowerCase().contains(lowerQuery) ||
                                med.getGeneric().toLowerCase().contains(lowerQuery) ||
                                med.getKeywords().stream().anyMatch(k -> k.toLowerCase().contains(lowerQuery))
                )
                .collect(Collectors.toList());
    }

    // Check interactions between medicines
    public List<Interaction> checkInteractions(List<String> medicineNames) {
        List<Interaction> found = new ArrayList<>();

        for (int i = 0; i < medicineNames.size(); i++) {
            for (int j = i + 1; j < medicineNames.size(); j++) {
                String med1 = medicineNames.get(i);
                String med2 = medicineNames.get(j);

                for (Interaction interaction : interactions) {
                    if (interaction.getDrugs().contains(med1) &&
                            interaction.getDrugs().contains(med2)) {
                        found.add(interaction);
                    }
                }
            }
        }

        return found;
    }

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return medicines;
    }
}