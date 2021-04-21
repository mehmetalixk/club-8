function addRole() {
    var x = document.getElementById("role_id");
    if(x.style.display ==="none"){
        x.style.display = "block";
    }else{
        x.style.display="none";
    }
}


var grid = new ej.grids.Grid({
    dataSource: data,
    columns: [
        { field: 'id', headerText: 'ID', width: 20, textAlign: 'Left' },
        { field: 'emailAddress', headerText: 'Email Address', width: 60, textAlign: 'Right' },
        { field: 'username', headerText: 'Username', width: 40, format: 'C2', textAlign: 'Right' },
        { field: 'name', headerText: 'Name', width: 40, textAlign: 'Right' },
        { field: 'surname', headerText: 'Surname', width: 40, format: 'C2', textAlign: 'Right' },
        { field: 'gender', headerText: 'Gender', width: 20, format: 'C2', textAlign: 'Right' },
        { field: 'birthdate', headerText: 'BirthDate', width: 20, format: 'C2', textAlign: 'Right' }
    ]
});
grid.appendTo('#Grid');

document.getElementById("Gridform").addEventListener("submit", (e) => {
    e.preventDefault();
    var value = parseInt(document.getElementById('multiplier').value, 10);

    // Filtering the data with user input value
    data = new ej.data.DataManager(window.hierarchyOrderdata).executeLocal(new ej.data.Query().where("EmployeeID", "equal", value).take(15));

    // Assigning to DataGrid
    grid.dataSource = data;

    document.getElementById("userinput").style.display = "none";
    document.getElementById("mtable").style.display = "";
    document.getElementById("Gridform").reset();
});
document.getElementById("close").addEventListener("click", (e)=>{
    document.getElementById("mtable").style.display = "none";
    document.getElementById("userinput").style.display = "";
});