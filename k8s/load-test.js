for (let i = 1; i <= 10000; i++) {
    const options = {
      method: 'GET',
      headers: {
        Accept: '*/*',
        'Accept-Encoding': 'gzip, deflate, br',
        'User-Agent': 'EchoapiRuntime/1.1.0',
        Connection: 'keep-alive'
      }
    };
  
    fetch('http://teqmonic.com/orders/55', options)
      .then(response => {
        console.log(`Request ${i}: HTTP Status ${response.status}`);
        return response.json();
      })
      .then(data => console.log(`Response ${i}:`, data))
      .catch(err => console.error(`Error in request ${i}:`, err));
  }
  