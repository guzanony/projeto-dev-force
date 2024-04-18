document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(loginForm);
        const loginData = {
            email: formData.get('email'),
            password: formData.get('password')
        };

        fetch('http://localhost:8080/auth/loginCliente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData)
        })
        .then(response => {
            if (response.ok) {
                console.log('Login bem-sucedido!');
                return response.json();
            } else {
                throw new Error('Falha no login!');
            }
        })
        .then(data => {
            window.location.href = '/projeto-dev-force/html/produtos-principal.html';
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
    });
});
