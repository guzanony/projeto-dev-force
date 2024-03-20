const formulario = document.querySelector("form");
const iusername = document.querySelector(".username");
const iemail = document.querySelector(".email");
const ipassword = document.querySelector(".password");
const icpf = document.querySelector(".cpf");
const igrupo = document.querySelector(".grupo");

  function cadastrar()  {
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
    .then(function (resp) { console.log(resp)})
    .catch(function (resp) { console.log(resp)})
  }

  function limpar() {
    iusername.value = "",
    iemail.value = "",
    ipassword.value = "",
    icpf.value = "",
    igrupo.value = ""
  }

  formulario.addEventListener("submit", function(event) {
    event.preventDefault();
    cadastrar();
    limpar();
  })