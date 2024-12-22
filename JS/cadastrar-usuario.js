const formulario = document.querySelector("form");
const iusername = document.querySelector(".username");
const iemail = document.querySelector(".email");
const ipassword = document.querySelector(".password");
const icpf = document.querySelector(".cpf");
const igrupo = document.querySelector(".grupo");

// Função para validar CPF (se necessário)
function validarCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, '');
  if (cpf.length !== 11) {
    return false;
  }

  let numeros, digitos, soma, i, resultado, digitos_iguais = true;
  for (i = 0; i < cpf.length - 1; i++) {
    if (cpf.charAt(i) !== cpf.charAt(i + 1)) {
      digitos_iguais = false;
      break;
    }
  }

  if (!digitos_iguais) {
    numeros = cpf.substring(0, 9);
    digitos = cpf.substring(9);
    soma = 0;
    for (i = 10; i > 1; i--) {
      soma += numeros.charAt(10 - i) * i;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
    if (resultado !== parseInt(digitos.charAt(0))) {
      return false;
    }
    soma = 0;
    numeros = cpf.substring(0, 10);
    for (i = 11; i > 1; i--) {
      soma += numeros.charAt(11 - i) * i;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
    if (resultado !== parseInt(digitos.charAt(1))) {
      return false;
    }
    return true;
  } else {
    return false;
  }
}

// Função para obter o parâmetro userId da URL
    function getUserIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get('userId');
    }

    // Função para carregar os dados do usuário para edição
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
    }

// Função para cadastrar usuário
function cadastrar() {
  const email = iemail.value;
  const password = ipassword.value;
  const name = iusername.value;
  const cpf = icpf.value;
  const grupo = igrupo.value;

  const registerData = {
    email: email,
    password: password,
    name: name,
    cpf: cpf,
    grupo: grupo,
  };

  fetch('http://localhost:8080/auth/register', {
    method: 'POST',
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(registerData)
  })
  .then(response => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Algo deu errado no servidor!');
    }
  })
  .then(data => {
    console.log(data);
    alert("Usuário cadastrado com sucesso!");
  })
  .catch(error => {
    console.error('Erro ao cadastrar usuário:', error);
    alert('Erro ao cadastrar usuário: ' + error.message);
  });
}

function limpar() {
  iusername.value = "";
  iemail.value = "";
  ipassword.value = "";
  icpf.value = "";
  igrupo.value = "";
}

formulario.addEventListener("submit", function(event) {
  event.preventDefault();
  cadastrar();
  limpar();
});
