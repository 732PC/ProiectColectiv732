function fetchProfessors() {
    fetch('http://localhost:8080/professors')
        .then(response => response.json())
        .then(data => {
            ProfessorDataList = [];
            ProfessorDataList.push(...data);
            generateProfessorBoxes();
        })
        .catch(error => console.error('Error fetching Professors:', error));
}
let ProfessorDataList = [];
document.addEventListener('DOMContentLoaded', function () {
    fetchProfessors();
});


document.addEventListener('DOMContentLoaded', function () {
    const profesoriButton = document.querySelector(".add-professor-button");
    profesoriButton.addEventListener('click', function () {
        const scrollableDiv = document.getElementById('content');
        scrollableDiv.innerHTML = '';
        fetchProfessors();
    });
});


function generateProfessorBoxes() {
    const scrollableDiv = document.getElementById('content');
    ProfessorDataList.forEach(ProfessorData => {
        const ProfessorBox = createProfessorBox(ProfessorData);
        scrollableDiv.appendChild(ProfessorBox);
    });
    scrollableDiv.style.display = 'block';
}


function createProfessorBox(ProfessorData) {
    const ProfessorBox = document.createElement('div');
    ProfessorBox.classList.add('profesor-box');
    ProfessorBox.id = ProfessorData.professorID;
    const professorName = document.createElement('div');
    professorName.classList.add('professor-name');
    professorName.textContent = `${ProfessorData.firstname} ${ProfessorData.lastname}`;
    ProfessorBox.appendChild(professorName);
    let selectedCountry = ProfessorData.country == null ? "Nu se specifica" : ProfessorData.country.toString();
    const content = `
        ${generateCoursesList(ProfessorData.courses)}
        <p>CNP: ${ProfessorData.cnp}</p>
        <p>Data de nastere: ${ProfessorData.birthdate}</p>
        <p>Tara: ${selectedCountry}</p>
        <button1 class="buttonModify" onclick="editProfessor(${ProfessorData.professorID})">Modifica Profesor</button1>`;
    ProfessorBox.innerHTML += content;
    return ProfessorBox;
}


function generateCoursesList(courses) {
    if (!courses || courses.length === 0) {
        return '<p>Cursuri: N/A</p>';}
    const coursesList = courses.map(course => `<p>- ${course.name}</p>`).join('');
    return `<p>Cursuri:</p>${coursesList}`;
}


function editProfessor(professorId) {
    document.getElementById('edit-professor').setAttribute('data-professor-id', professorId);
    const professorData = ProfessorDataList.find(prof => prof.professorID === professorId);
    const editProfessorSection = document.getElementById('edit-professor');
    editProfessorSection.style.display = 'block';
    console.log(professorData.firstname);
    document.getElementById('edit-firstName').value = professorData.firstname;
    document.getElementById('edit-lastName').value = professorData.lastname;
    document.getElementById('edit-cnp').value = professorData.cnp;
    document.getElementById('edit-birthdate').value = professorData.birthdate;
    document.getElementById('edit-country').value = professorData.country;
}

function cancelEditProfessor() {
    const editProfessorSection = document.getElementById('edit-professor');
    editProfessorSection.style.display = 'none';}


function saveProfessor() {
    const professorId = document.getElementById('edit-professor').getAttribute('data-professor-id');
    const updatedProfessor = {
        firstname: document.getElementById('edit-firstName').value,
        lastname: document.getElementById('edit-lastName').value,
        cnp: document.getElementById('edit-cnp').value,
        birthdate: document.getElementById('edit-birthdate').value,
        country: document.getElementById('edit-country').value,};
    fetch(`http://localhost:8080/professors/${professorId}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedProfessor),})
        .then(response => {
            if (!response.ok) {
                throw new Error('Error updating professor');}
            alert('Noile date sunt salvate cu succes!');
            document.getElementById('edit-professor').style.display = 'none';
            location.reload();})
        .catch(error => console.error('Error updating professor:', error));
    document.getElementById('edit-professor').style.display = 'none';}
document.addEventListener('DOMContentLoaded', function () {
    generateCountryOptions();});


function deleteProfessor() {
    const professorId = document.getElementById('edit-professor').getAttribute('data-professor-id');
    if (!professorId) {
        console.error('Professor ID is missing or invalid.');
        return;}
    const parsedProfessorId = parseInt(professorId, 10);
    if (isNaN(parsedProfessorId)) {
        console.error('Invalid professor ID format.');
        return;}
    fetch(`http://localhost:8080/professors/${parsedProfessorId}`)
        .then(response => response.json())
        .then(professorData => {
            const courses = professorData.courses;
            if (courses && courses.length > 0) {
                alert('Profesorul nu poate fi sters deoarece are cursuri in anul curent.');
            } else {if (confirm('Sigur vrei să stergi acest profesor?')) {
                    fetch(`http://localhost:8080/professors/${parsedProfessorId}`, {
                        method: 'DELETE',}).then(response => {
                        console.log(`Professor ID: ${parsedProfessorId}`);
                        console.log(`Response Status: ${response.status}`);
                        if (!response.ok) {
                            throw new Error(`Error deleting professor: ${response.statusText}`);}
                        setTimeout(() => {
                            location.reload();
                            }, 500);
                        alert('Profesor șters cu succes!');
                    }).catch(error => console.error(error));
                }
            }
        })
        .catch(error => console.error(error));
}



function generateCountryOptions() {
    const editCountrySelect = document.getElementById('edit-country');
    const addCountrySelect = document.getElementById('add-country');
    editCountrySelect.innerHTML = '';
    addCountrySelect.innerHTML = '';
    const defaultOptionAdd = document.createElement('option');
    defaultOptionAdd.value = '';
    defaultOptionAdd.textContent = 'Alege tara (optional)';
    addCountrySelect.appendChild(defaultOptionAdd);
    fetch('http://localhost:8080/api/countries/all')
        .then(response => response.json())
        .then(countries => {
            countries.forEach(country => {
                const editOption = document.createElement('option');
                editOption.value = country;
                editOption.textContent = country;
                editCountrySelect.appendChild(editOption);
                const addOption = document.createElement('option');
                addOption.value = country;
                addOption.textContent = country;
                addCountrySelect.appendChild(addOption);
            });
        }).catch(error => console.error('Error fetching countries:', error));
}



function addProfessor() {
    const cnpInput = document.getElementById('add-cnp');
    const cnpValue = cnpInput.value;
    if (cnpValue.length !== 13) {
        alert('CNP trebuie sa aiba exact 13 caractere');
        return;
    }
    const newProfessor = {
        firstname: document.getElementById('add-firstName').value,
        lastname: document.getElementById('add-lastName').value,
        cnp: cnpValue,
        role: 'PROF',
        birthdate: document.getElementById('add-birthdate').value,
    };
    const selectedCountry = document.getElementById('add-country').value;
    if (selectedCountry) {
        newProfessor.country = selectedCountry;}
    fetch('http://localhost:8080/professors', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newProfessor),})
        .then(response => {
            if (!response.ok) {
                throw new Error('Error adding professor');}
            alert('Date salvate cu succes!');
            document.getElementById('edit-professor').style.display = 'none';
            location.reload();})
        .catch(error => console.error('Error adding professor:', error));
}


function showAddProfessorForm() {
    const addProfessorSection = document.getElementById('add-professor');
    addProfessorSection.style.display = 'block';
    document.getElementById('add-professor-form').reset();
}


function cancelAddProfessor() {
    const addProfessorSection = document.getElementById('add-professor');
    addProfessorSection.style.display = 'none';
}