document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var data = {
        username: username,
        password: password
    };

    fetch('http://localhost:8080/usersAdmin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        // Handle response from backend
        if (data.token) {
            // Login successful, save token and redirect
            localStorage.setItem('token', data.token);
            window.location.href = "/html/TelaPrincipal"; // Redirect to dashboard or homepage
        } else {
            // Login failed, display error message
            alert('Login failed. Please check your credentials.');
        }
    })
    .then(response => {
        console.log('Response:', response);
        return response.json();
    })

    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred. Please try again later.');
    });
});
