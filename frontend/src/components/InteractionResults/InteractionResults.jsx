import './InteractionResults.css';

function InteractionResults({ interactions, isLoading, medicineCount }) {
  if (medicineCount < 2) {
    return null;
  }

  if (isLoading) {
    return (
      <div className="loading">
        <div className="spinner-large"></div>
        <p>Checking interactions...</p>
      </div>
    );
  }

  const getSeverityClass = (severity) => {
    return severity.toLowerCase();
  };

  return (
    <div className="interactions-section">
      <h3>Interaction Analysis:</h3>

      {interactions.length === 0 ? (
        <div className="no-interaction">
          <div className="icon">✓</div>
          <h4>No Known Interactions Found</h4>
          <p>The selected medications appear safe to use together based on our database.</p>
          <p className="disclaimer">
            Always consult your healthcare provider before taking multiple medications.
          </p>
        </div>
      ) : (
        <div className="interactions-list">
          {interactions.map((interaction, idx) => (
            <div 
              key={idx} 
              className={`interaction-card ${getSeverityClass(interaction.severity)}`}
            >
              <div className="interaction-header">
                <span className="severity-badge">
                  {interaction.severity.toUpperCase()} INTERACTION
                </span>
                <div className="drug-pair">
                  {interaction.drugs.join(' + ')}
                </div>
              </div>
              <p className="description">{interaction.description}</p>
              <div className="recommendation">
                <strong>Recommendation:</strong> {interaction.recommendation}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default InteractionResults;