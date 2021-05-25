function display_admin(id) {
    let x;
    const ids = ['list_members_id', 'add_role_id', 'list_roles_id', 'list_permissions_id', 'add_club_id', 'list_clubs_id', 'add_subClub_id',
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
