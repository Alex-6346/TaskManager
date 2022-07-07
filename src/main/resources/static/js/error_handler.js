function showErrors(xhr, errorsContainer){
    errors = JSON.parse(xhr.responseText)
    errors.forEach(function (err){
        var errorHTML = '<div class="alert alert-danger" role="alert">' + err + '</div>'
        console.log(errorHTML)
        $(errorsContainer).html(errorHTML)
    })
}