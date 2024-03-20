document.addEventListener('DOMContentLoaded', function() {
        const apiUrl = 'http://localhost:8080/registerUsers';

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                const tableBody = document.getElementById('usersTable').getElementsByTagName('tbody')[0];

                data.forEach(
                user => {
                    let row = tableBody.insertRow();
                    let cellUsername = row.insertCell();
                    let cellEmail = row.insertCell();
                    let cellCpf = row.insertCell();
                    let cellGrupo = row.insertCell();

                    cellUsername.appendChild(document.createTextNode(user.username));
                    cellEmail.appendChild(document.createTextNode(user.email));
                    cellCpf.appendChild(document.createTextNode(user.cpf));
                    cellGrupo.appendChild(document.createTextNode(user.grupo));
                });
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    });