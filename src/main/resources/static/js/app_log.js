<<<<<<< HEAD
const API_URL = "http://192.168.2.103:8080/api/v2/usuarios"; //se conecta con el usuario 
=======
const API_URL = "http://192.168.1.9:8080/api/v2/usuarios"; 
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
  
function login() {
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
    
  if (!email || !password) {
    alert("Completa todos los campos");
    return;
  }

<<<<<<< HEAD
  fetch("http://192.168.2.103:8080/api/v2/usuarios/login", {
=======
  fetch("http://192.168.1.9:8080/api/v2/usuarios/login", {
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  })
  .then(res => res.json())
  .then(data => {
    if (data && data.nombre) {
      alert("Inicio de sesión exitoso como " + data.nombre);
      localStorage.setItem("usuario", JSON.stringify(data));
      window.location.href = "index.html";
    } else {
      alert("Cuenta");
    }
  })
  .catch(err => {
    console.error(err);
    alert("Error al conectar con el servidor.");
  });

}

document.getElementById("btn-logout").addEventListener("click", () => {
  localStorage.removeItem("usuario");
  window.location.reload();
});


