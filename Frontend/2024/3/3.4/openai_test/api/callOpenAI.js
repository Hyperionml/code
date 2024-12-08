// api.js  
import axios from 'axios';  

const API_KEY = '';  
const BASE_URL = '';  

export const callOpenAI = async (endpoint, method, data = {}) => {  
  try {  
    const response = await axios({  
      method,  
      url: `${BASE_URL}${endpoint}`,  
      headers: {  
        'Authorization': `Bearer ${API_KEY}`,  
        'Content-Type': 'application/json',  
      },  
      data,  
    });  
    return response.data;  
  } catch (error) {  
    console.error('OpenAI API调用失败:', error);  
    throw error;  
  }  
};