document.addEventListener('DOMContentLoaded', function() {
    const apiUrlGetUsers = 'http://localhost:8080/auth/users';
    const apiUrlActive = 'http://localhost:8080/auth/registerUsers/{id}/activate';
    const apiUrlDeactive = 'http://localhost:8080/auth/registerUsers/{id}/deactivate';

    function loadUsers() {
        fetch(apiUrlGetUsers)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                const tableBody = document.getElementById('usersTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = '';
                data.forEach(user => {
                    let row = tableBody.insertRow();
                    let cellUsername = row.insertCell();
                    let cellEmail = row.insertCell();
                    let cellStatus = row.insertCell();
                    let cellGrupo = row.insertCell();
                    let cellActions = row.insertCell();

                    cellUsername.textContent = user.name;
                    cellEmail.textContent = user.username;
                    cellStatus.textContent = user.active ? "ATIVO" : 'INATIVO'
                    cellGrupo.textContent = user.role.replace('ROLE_', '');

                    let editBtn = document.createElement('button');
                    editBtn.textContent = 'Editar';
                    editBtn.className = 'btn btn-warning';
                    editBtn.addEventListener('click', () => {
                        window.location.href = `cadastrar-usuario.html?userId=${user.id}`;
                    });
                    cellActions.appendChild(editBtn);

                    let statusBtn = document.createElement('button');
                    statusBtn.textContent = user.active ? 'Desativar' : 'Ativar';
                    statusBtn.className = user.active ? 'btn btn-secondary' : 'btn btn-primary';
                    statusBtn.addEventListener('click', () => toggleUserStatus(user.id, user.active));
                    cellActions.appendChild(statusBtn);
                });
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }

    function toggleUserStatus(userId, isActive) {
        const apiUrl = isActive ? apiUrlDeactive.replace('{id}', userId) : apiUrlActive.replace('{id}', userId);

        fetch(apiUrl, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            if (response.status === 204 || response.bodyUsed === false) {
                return {};
            }
            return response.json();
        })
        .then(data => {
            console.log('Status do usuário alterado com sucesso:', data);
            alert('Status do usuário alterado com sucesso!');
            window.location.reload();
        })
        .catch(error => {
            console.error('Erro ao alterar status do usuário:', error);
        });
    }

    loadUsers();
});
