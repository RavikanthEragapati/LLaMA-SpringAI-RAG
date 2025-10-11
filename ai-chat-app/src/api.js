  const apiService = {

    createSession: (name) => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          if (name) {
            resolve({ success: true, token: `${Date.now()}` });
          } else {
            reject(new Error('Name cannot be empty.'));
          }
        }, 1000);
      });
    },

    sendMessage: async (userMessage, sessionToken) => {
        const errorResponse = `Unable to reach the server. Please try again later`;
        let botResponse = '';

        try{
            const resp = await fetch(`/chat/${encodeURIComponent(sessionToken)}/askUsingRAG?userPrompt=${encodeURIComponent(userMessage)}`);
            if(!resp.ok){
                botResponse=errorResponse;
            } else {
                const data = await resp.json();
                botResponse = data.response;
            }
        }catch(error){
            botResponse=errorResponse;
        }

        return {response: botResponse};
    },

    endSession: async (sessionToken) => {
        console.log(`end of session for token: ${sessionToken}. Chat Memory will be cleared.`);

        const errorResponse = `Unable to reach the server. Please try again later`;
        let botResponse = '';

        try{
            const resp = await fetch(`/chat/${encodeURIComponent(sessionToken)}/clearMemory`, {method: 'DELETE'});
            if(!resp.ok){
                botResponse=errorResponse;
            } else {
                const data = await resp.json();
                botResponse = data.response;
            }
        }catch(error){
            botResponse=errorResponse;
        }

        return {response: botResponse};
    }
  };

export default apiService;
