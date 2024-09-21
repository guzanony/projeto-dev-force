document.addEventListener('DOMContentLoaded', function() {
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
            if (response.ok) {
                document.getElementById('content').innerText = 'Token validado com sucesso!';
            } else {
                localStorage.removeItem('token');
                window.location.href = 'page-login-cliente.html';
            }
        })
        .catch(error => {
            console.error('Erro ao validar o token:', error);
        });
    }
});
