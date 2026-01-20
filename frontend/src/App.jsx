import { useState, useEffect } from 'react';
import SearchBar from './components/SearchBar/SearchBar';
import MedicineList from './components/MedicineList/MedicineList';
import InteractionResults from './components/InteractionResults/InteractionResults';
import { checkInteractions } from './services/api';
import './App.css';

function App() {
  const [selectedMeds, setSelectedMeds] = useState([]);
  const [interactions, setInteractions] = useState([]);
  const [isCheckingInteractions, setIsCheckingInteractions] = useState(false);

  // Check interactions when medicines change
  useEffect(() => {
    if (selectedMeds.length >= 2) {
      setIsCheckingInteractions(true);
      const medicineNames = selectedMeds.map(m => m.name);
      checkInteractions(medicineNames).then(results => {
        setInteractions(results);
        setIsCheckingInteractions(false);
      });
    } else {
      setInteractions([]);
    }
  }, [selectedMeds]);

  const handleSelectMedicine = (medicine) => {
    if (!selectedMeds.find(m => m.id === medicine.id)) {
      setSelectedMeds([...selectedMeds, medicine]);
    }
  };

  const handleRemoveMedicine = (id) => {
    setSelectedMeds(selectedMeds.filter(m => m.id !== id));
  };

  return (
    <div className="app">
      <div className="container">
        <div className="header">
          <h1> Medicine Interaction Checker 💊</h1>
          <p>AI-Powered with React + Spring Boot</p>
        </div>

        <SearchBar onSelectMedicine={handleSelectMedicine} />

        <MedicineList 
          medicines={selectedMeds} 
          onRemove={handleRemoveMedicine} 
        />

        <InteractionResults 
          interactions={interactions}
          isLoading={isCheckingInteractions}
          medicineCount={selectedMeds.length}
        />

        <div className="footer">
          <strong>Disclaimer : </strong>  
          Always consult your healthcare provider before taking medications.
        </div>
      </div>
    </div>
  );
}

export default App;