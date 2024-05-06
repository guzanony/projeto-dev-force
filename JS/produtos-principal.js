document.addEventListener('DOMContentLoaded', function() {
    var token = localStorage.getItem('token');
    if (!token) {
        window.location.href = 'page-login-cliente.html'; // Redireciona para a página de login se o token não existir
    } else {
        fetch('http://localhost:8080/auth/validateTokenCliente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then(response => {
            if (response.ok) {
                document.getElementById('content').innerText = 'Token validado com sucesso!';
            } else {
                localStorage.removeItem('token'); // Remove o token inválido
                window.location.href = 'page-login-cliente.html'; // Redireciona para a página de login
            }
        })
        .catch(error => {
            console.error('Erro ao validar o token:', error);
        });
    }
});
