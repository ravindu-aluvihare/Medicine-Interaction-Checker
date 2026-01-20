import './MedicineList.css';

function MedicineList({ medicines, onRemove }) {
  if (medicines.length === 0) {
    return (
      <div className="empty-state">
        <div className="icon">🔍</div>
        <p>Start by searching and selecting medicines above</p>
        <p className="sub">Add at least 2 medicines to check for interactions</p>
      </div>
    );
  }

  return (
    <div className="medicine-list-section">
      <h3>Selected Medicines:</h3>
      <div className="selected-medicines">
        {medicines.map(med => (
          <div key={med.id} className="medicine-tag">
            <span>{med.name}</span>
            <button onClick={() => onRemove(med.id)}>✕</button>
          </div>
        ))}
      </div>
      {medicines.length === 1 && (
        <div className="hint">
          ℹ️ Add at least one more medicine to check for interactions
        </div>
      )}
    </div>
  );
}

export default MedicineList;