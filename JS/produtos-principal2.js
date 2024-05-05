document.addEventListener('DOMContentLoaded', function() {
  const urlParams = new URLSearchParams(window.location.search);
  const nome = urlParams.get('nomeCompleto');
  if (nome) {
    document.getElementById('nome-usuario').textContent = nome;
  } else {
    window.location.href = 'page-login-cliente.html';
  }

  var token = localStorage.getItem('token');
  if (!token) {
    window.location.href = 'page-login-cliente.html';
  } else {
    fetch('http://localhost:8080/auth/validateTokenCliente', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      }
    })
    .then(response => {
      if (!response.ok) {
        localStorage.removeItem('token');
        window.location.href = 'page-login-cliente.html';
      }
      //adicionar código para carregar os produtos ou outras informações
    })
    .catch(error => {
      console.error('Erro ao validar o token:', error);
    });
  }
});
