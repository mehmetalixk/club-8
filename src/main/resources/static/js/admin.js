function display_admin(id) {
    let x;
    const ids = ['list_members_id', 'add_role_id', 'list_roles_id', 'add_club_id', 'list_clubs_id', 'add_subClub_id',
        'list_subClubs_id'];

    for(let i=0; i<ids.length; i++){
        if(ids[i]===id){
            x = document.getElementById(id);
        }
        else {
            document.getElementById(ids[i]).style.display="none";
        }
    }
    if(x.style.display ==="none"){
        x.style.display = "block";
    }else{
        x.style.display="none";
    }
}

function fill(x) {
    x.classList.toggle("fas");
}

function empty() {
    document.getElementById("share_ico").classList.remove("fas");
    document.getElementById("share_ico").classList.add("far");
}

function popup() {
    var popup = document.getElementById("clipboard");
    popup.classList.remove("hide");
    popup.classList.add("show");
}

function popdown() {
    var popdown = document.getElementById("clipboard");
    popdown.classList.remove("show");
    popdown.classList.add("hide");
    empty();
}

function clip(x) {
    var dummy = document.createElement('input'),
        text = x;

    document.body.appendChild(dummy);
    dummy.value = text;
    dummy.select();
    document.execCommand('copy');
    document.body.removeChild(dummy);
    popup();
    setTimeout(popdown, 1000);
}

