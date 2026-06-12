// Se non è presente auth salvata → redirect al login
if (!localStorage.getItem("auth")) {
    window.location.href = "/login.html";
}

// Header Authorization su TUTTE le chiamate Ajax
$.ajaxSetup({
    headers: {
        "Authorization": "Basic " + localStorage.getItem("auth")
    }
});

// Carica navbar quando il DOM è pronto
$(document).ready(function () {
    $("#navbar-placeholder").load("/components/navbar.html");
});

// Logout
function logout() {
    localStorage.removeItem("auth");
    window.location.href = "/login.html";
}
