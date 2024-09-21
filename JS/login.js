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
    .then(response => {
        if (!response.ok) {
            throw new Error('Login falhou');
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('username', data.username);
        localStorage.setItem('name', data.name);
        localStorage.setItem('role', data.role);
        console.log('Dados armazenados no localStorage:', data);
        window.location.href = '../html/TelaPrincipal.html';
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
        alert('Login falhou: ' + error.message);
    });
});
