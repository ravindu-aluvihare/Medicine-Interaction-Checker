import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/medicines';

// Search medicines (basic search)
export const searchMedicines = async (query) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/search`, {
      params: { query }
    });
    return response.data;
  } catch (error) {
    console.error('Error searching medicines:', error);
    return [];
  }
};

// AI-powered search
export const aiSearchMedicines = async (query) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/ai-search`, {
      query: query
    });
    return response.data;
  } catch (error) {
    console.error('Error in AI search:', error);
    return searchMedicines(query);
  }
};

// Check interactions between medicines
export const checkInteractions = async (medicineNames) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/check-interaction`, medicineNames);
    return response.data;
  } catch (error) {
    console.error('Error checking interactions:', error);
    return [];
  }
};

// Get all medicines
export const getAllMedicines = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/all`);
    return response.data;
  } catch (error) {
    console.error('Error getting medicines:', error);
    return [];
  }
};