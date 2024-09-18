function showLoader() {
    document.getElementById('loader').style.display = 'block';
}

function hideLoader() {
    document.getElementById('loader').style.display = 'none';
}

// Example: Show the loader when form is submitted
document.addEventListener('DOMContentLoaded', function() {
    var forms = document.querySelectorAll('form');
    forms.forEach(function(form) {
        form.addEventListener('submit', function() {
            showLoader();
        });
    });

    // Hide the loader after some delay for testing purposes
    // Remove this part in production
    setTimeout(function() {
        hideLoader();
    }, 2000); // 2 seconds delay
});