import { useState, useEffect } from 'react';
import './SearchBar.css';

function SearchBar({ onSelectMedicine }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);

  useEffect(() => {
    if (searchTerm.length >= 3) {
      setIsSearching(true);
      const timer = setTimeout(async () => {
        try {
          const response = await fetch(`http://localhost:8080/api/medicines/ai-search`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ query: searchTerm })
          });
          const results = await response.json();
          setSearchResults(results);
        } catch (error) {
          console.error('Search error:', error);
          setSearchResults([]);
        }
        setIsSearching(false);
      }, 500);
      return () => clearTimeout(timer);
    } else {
      setSearchResults([]);
    }
  }, [searchTerm]);

  const handleSelect = (medicine) => {
    onSelectMedicine(medicine);
    setSearchTerm('');
    setSearchResults([]);
  };

  return (
    <div className="search-section">
      <div className="search-bar">
        <input
          type="text"
          placeholder="Search medicines (e.g., 'aspirin', 'painkiller', 'blood pressure')..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />
        {isSearching && <div className="spinner"></div>}
      </div>

      {searchResults.length > 0 && (
        <div className="search-results">
          <div className="results-header">✨ AI Smart Search Results</div>
          {searchResults.map(med => (
            <div 
              key={med.id} 
              className="result-item"
              onClick={() => handleSelect(med)}
            >
              <div>
                <div className="med-name">{med.name}</div>
                <div className="med-generic">{med.generic}</div>
              </div>
              <span className="med-category">{med.category}</span>
            </div>
          ))}
        </div>
      )}

      {searchTerm.length >= 3 && !isSearching && searchResults.length === 0 && (
        <div className="no-results">
          No medicines found. Try different keywords.
        </div>
      )}

      <div className="examples">
        <p>Try: </p>
        {['painkiller', 'blood pressure', 'diabetes', 'aspirin'].map(example => (
          <button 
            key={example}
            onClick={() => setSearchTerm(example)}
            className="example-btn"
          >
            {example}
          </button>
        ))}
      </div>
    </div>
  );
}

export default SearchBar;