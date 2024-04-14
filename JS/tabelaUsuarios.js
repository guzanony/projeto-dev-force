document.addEventListener('DOMContentLoaded', function() {
    const apiUrlGetUsers = 'http://localhost:8080/registerUsers';
    const apiUrlActiveUsers = 'http://localhost:8080/registerUsers/{id}/activate';
    const apiUrlDeactive = 'http://localhost:8080/registerUsers/{id}/deactivate';

    fetch(apiUrlGetUsers)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('usersTable').getElementsByTagName('tbody')[0];

            data.forEach(user => {
                let row = tableBody.insertRow();
                let cellUsername = row.insertCell();
                let cellEmail = row.insertCell();
                let cellStatus = row.insertCell();
                let cellGrupo = row.insertCell();
                let cellActions = row.insertCell();

                cellUsername.appendChild(document.createTextNode(user.username));
                cellEmail.appendChild(document.createTextNode(user.email));
                cellStatus.appendChild(document.createTextNode("ATIVO"));
                cellGrupo.appendChild(document.createTextNode(user.grupo));

                // Botão Editar
                let editBtn = document.createElement('button');
                editBtn.innerText = 'Editar';
                editBtn.className = 'btn btn-warning';
                editBtn.addEventListener('click', () => editUser(user.id));
                cellActions.appendChild(editBtn);

                // Botão Ativar/Desativar
                let statusBtn = document.createElement('button');
                statusBtn.innerText = user.isActive = 'Desativar';
                statusBtn.className = user.isActive ? 'btn btn-secondary' : 'btn btn-primary';
                statusBtn.addEventListener('click', () => toggleUserStatus(user.id, user.isActive));
                cellActions.appendChild(statusBtn);
            });
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
});

// Função para editar usuário
function editUser(userId) {

    console.log('Edit user', userId);
}

// Função para ativar/desativar usuário
function toggleUserStatus(userId, isActive) {
    const API_URL_TOGGLE_STATUS = isActive ? apiUrlDeactive.replace('{id}', userId) : API_URL_ACTIVATE_USER.replace('{id}', userId);

    fetch(API_URL_TOGGLE_STATUS, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Status do usuário alterado:', data);
        // Atualize a tabela ou notifique o usuário de sucesso aqui
    })
    .catch(error => {
        console.error('Erro ao alterar status do usuário:', error);
    });

    console.log('Toggle user status', userId, !isActive);
}
