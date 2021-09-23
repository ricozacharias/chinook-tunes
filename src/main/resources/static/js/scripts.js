function validateSearchText(searchText)
{
    if (searchText != "") {
        document.getElementById('search_button').removeAttribute("disabled");
    }
    else {
        document.getElementById('search_button').setAttribute("disabled", "disabled");
    }
}