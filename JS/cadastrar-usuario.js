const formulario = document.querySelector("form");
const iusername = document.querySelector(".form-control");
const iemail = document.querySelector(".form-control");
const ipassword = document.querySelector(".form-control");
const icpf = document.querySelector(".form-control");

  function cadastrar() {
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
            cpf: icpf.value
        })
    })
    .then(function (resp) { console.log(resp)})
    .catch(function (resp) { console.log(resp)})
  }

  function limpar() {
    iusername.value = "",
    iemail.value = "",
    ipassword.value = "",
    icpf.value = ""
  }

  formulario.addEventListener("submit", function(event) {
    event.preventDefault();
    console.log(icpf)
    cadastrar();
    limpar();
  })