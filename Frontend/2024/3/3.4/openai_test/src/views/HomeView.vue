<template> 
  <div>  
    <button @click="fetchText">生成文本</button>  
    <p>{{ generatedText }}</p>  
  </div>  
</template>

<script>
import { callOpenAI } from '../../api/callOpenAI';  
 
export default {  
  data() {  
    return {  
      generatedText: '',  
    };  
  },  
  methods: {  
    async fetchText() {  
      try {  
        const prompt = '请为我生成一段有趣的文本。';  
        const response = await callOpenAI('/engines/text-davinci-002/completions', 'POST', { prompt });  
        this.generatedText = response.choices[0].text;  
      } catch (error) {  
        console.error('生成文本失败:', error);  
      }  
    },  
  },  
};  

</script>
