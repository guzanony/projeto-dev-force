document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var loginData = {
        username: username,
        password: password
    };

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            // Armazena o token no localStorage ou em cookies
            localStorage.setItem('token', data.token);
            window.location.href = '/html/TelaPrincipal.html';
        } else {
            alert('Login falhou');
        }
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
    });
});
