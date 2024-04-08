const formulario = document.querySelector("form");
const iusername = document.querySelector(".username");
const iemail = document.querySelector(".email");
const ipassword = document.querySelector(".password");
const icpf = document.querySelector(".cpf");
const igrupo = document.querySelector(".grupo");

function validarCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, ''); // Remove tudo o que não é dígito
  if (cpf.length !== 11) {
    return false;
  }

  var numeros, digitos, soma, i, resultado, digitos_iguais;
  digitos_iguais = 1;

  for (i = 0; i < cpf.length - 1; i++) {
    if (cpf.charAt(i) !== cpf.charAt(i + 1)) {
      digitos_iguais = 0;
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

    if (resultado !== Number(digitos.charAt(0))) {
      return false;
    }

    numeros = cpf.substring(0, 10);
    soma = 0;

    for (i = 11; i > 1; i--) {
      soma += numeros.charAt(11 - i) * i;
    }

    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

    if (resultado !== Number(digitos.charAt(1))) {
      return false;
    }

    return true;
  } else {
    return false;
  }
}

function cadastrar() {
  if (!validarCPF(icpf.value)) {
    alert("CPF inválido.");
    return;
  }

  fetch("http://localhost:8080/registerUsers", {
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    method: "POST",
    body: JSON.stringify({
      username: iusername.value,
      email: iemail.value,
      password: ipassword.value,
      cpf: icpf.value,
      grupo: igrupo.value
    })
  })
  .then(function (response) {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Algo deu errado no servidor!');
    }
  })
  .then(function (data) {
    console.log(data);
    alert("Usuário cadastrado com sucesso!");
  })
  .catch(function (error) {
    console.error(error);
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
})
