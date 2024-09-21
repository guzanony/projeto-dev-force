const formulario = document.querySelector("form");
const iusername = document.querySelector(".username");
const iemail = document.querySelector(".email");
const ipassword = document.querySelector(".password");
const icpf = document.querySelector(".cpf");
const igrupo = document.querySelector(".grupo");

let usuarioAtualId = null;

function getUserIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('userId');
}

function loadUserData(userId) {
    fetch(`http://localhost:8080/auth/user/${userId}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao carregar dados do usuário');
        }
        return response.json();
    })
    .then(user => {
        iusername.value = user.name;
        iemail.value = user.username;
        ipassword.value = '';
        icpf.value = user.cpf;
        igrupo.value = user.role.replace('ROLE_', '');
    })
    .catch(error => {
        console.error('Erro ao carregar usuário:', error);
        alert('Erro ao carregar usuário: ' + error.message);
    });
}

const userId = getUserIdFromUrl();
if (userId) {
    loadUserData(userId);
    usuarioAtualId = userId;
}

function salvarUsuario() {
    const email = iemail.value;
    const password = ipassword.value;
    const name = iusername.value;
    const cpf = icpf.value;
    const grupo = igrupo.value;

    const userData = {
        username: email,
        password: password,
        name: name,
        cpf: cpf,
        role: grupo,
    };

        console.log('Dados do usuário:', userData);

    const metodoHttp = usuarioAtualId ? "PUT" : "POST";
    const url = usuarioAtualId ? `http://localhost:8080/auth/user/${usuarioAtualId}` : "http://localhost:8080/auth/register";

    fetch(url, {
        method: metodoHttp,
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    })
    .then(response => {
        if (!response.ok) throw new Error('Erro ao salvar o usuário');
        return response.json();
    })
    .then(data => {
        const message = usuarioAtualId ? "Usuário atualizado com sucesso!" : "Usuário cadastrado com sucesso!";
        alert(message);
        window.location.href = "TabelaUsuarios.html";
    })
    .catch(error => {
        console.error('Erro ao salvar o usuário:', error);
        alert('Erro ao salvar o usuário: ' + error.message);
    });
}

formulario.addEventListener("submit", function(event) {
    event.preventDefault();
    salvarUsuario();
});
