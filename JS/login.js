fetch('/usersAdmin')
    .then(response => response.json())
    .then(data => {
        // Faça algo com os dados recebidos
        console.log(data);
    })
    .catch(error => {
        console.error('Erro ao obter dados:', error);
    });

