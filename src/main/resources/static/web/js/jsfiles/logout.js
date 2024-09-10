function logoutUser() {
        fetch('/secure/userlogout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(response => {
            if (response.ok) {
                // Redirect to the login page or home page after logout
                window.location.href = "/login.htm";
            } else {
                alert("Logout failed!");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Logout failed!");
        });
    }