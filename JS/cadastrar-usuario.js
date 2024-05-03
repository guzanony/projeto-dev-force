const formulario = document.querySelector("form");
const iusername = document.querySelector(".username");
const iemail = document.querySelector(".email");
const ipassword = document.querySelector(".password");
const icpf = document.querySelector(".cpf");
const igrupo = document.querySelector(".grupo");

// Função para validar CPF
function validarCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, ''); // Remove tudo o que não é dígito
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

// Função para carregar os dados do usuário para edição
function loadUserData(userId) {
  fetch('http://localhost:8080/registerUsers/' + userId)
    .then(response => response.json())
    .then(user => {
      iusername.value = user.username;
      iemail.value = user.email;
      ipassword.value = user.password; // Considerar questões de segurança aqui
      icpf.value = user.cpf;
      igrupo.value = user.grupo;
    })
    .catch(error => console.error('Erro ao buscar dados do usuário:', error));
}

// Função para cadastrar ou atualizar usuário
function cadastrar() {
  if (!validarCPF(icpf.value)) {
    alert("CPF inválido.");
    return;
  }

  const userId = new URLSearchParams(window.location.search).get('userId');
  const method = userId ? 'PUT' : 'POST';
  const apiUrl = userId ? 'http://localhost:8080/registerUsers/' + userId : 'http://localhost:8080/registerUsers';

  fetch(apiUrl, {
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    method: method,
    body: JSON.stringify({
      username: iusername.value,
      email: iemail.value,
      password: ipassword.value,
      cpf: icpf.value,
      grupo: igrupo.value
    })
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
    console.error(error);
  });
}

// Função para limpar o formulário
function limpar() {
  iusername.value = "";
  iemail.value = "";
  ipassword.value = "";
  icpf.value = "";
  igrupo.value = "";
}

// Evento para prevenir o envio padrão do formulário e chamar a função de cadastro
formulario.addEventListener("submit", function(event) {
  event.preventDefault();
  cadastrar();
  limpar();
});

// Carrega os dados do usuário se estiver em modo de edição
window.addEventListener('load', function () {
  const userId = new URLSearchParams(window.location.search).get('userId');
  if (userId) loadUserData(userId);
});
