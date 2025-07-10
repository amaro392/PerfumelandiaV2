<<<<<<< HEAD
    const API_URL = "http://192.168.2.103:8080/api/v2/usuarios";
=======
    const API_URL = "http://192.168.1.9:8080/api/v2/usuarios";
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
    function registrar() {
      const nombre = document.getElementById("nombre").value.trim();
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      if (!nombre || !email || !password) {
        alert("por favor completa todos los campos.");
        return;
      }

<<<<<<< HEAD
      fetch("http://192.168.2.103:8080/api/v2/usuarios", {
=======
      fetch("http://192.168.1.9:8080/api/v2/usuarios", {
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre , email , password })
      })
      .then(response => {
        if (response.ok) {
          alert("Usuario registrado correctamente");
          window.location.href = "login.html";
        } else {
          alert("Error verifiaca los datos.");
        }
      });
    }