function fetchStudents() {
    fetch('http://localhost:8081/api/students')
        .then(response => response.json())
        .then(data => {
            studentDataList.push(...data);
            generateStudentBoxes();
        })
        .catch(error => console.error('Error fetching students:', error));
}

fetchStudents();

const studentDataList = [];


function generateStudentBoxes() {
    const scrollableDiv = document.querySelector('.scrollableDiv');

    studentDataList.forEach(studentData => {
        const studentBox = createStudentBox(studentData);
        scrollableDiv.appendChild(studentBox);
    });
}

function createStudentBox(studentData) {
    const studentBox = document.createElement('div');
    studentBox.classList.add('student-box');
    studentBox.id = studentData.id;

    const content = `
    <p>Nume: ${studentData.firstName}</p>
    <p>Prenume: ${studentData.lastName}</p>
    <p>CNP: ${studentData.cnp}</p>
    <p>Data de nastere: ${studentData.birthDate}</p>
    <p>Anul de studiu: ${studentData.studyYear}</p>
    <p>Nivelul de studiu: ${studentData.studyLevel}</p>
    <p>Forma de finantare: ${studentData.fundingForm}</p>
    <p>Liceu absolvit: ${studentData.graduatedHighSchool}</p>
    <button class="buttonModify" onclick="editStudent('${studentData.id}')">Modifica student</button>
  `;
    studentBox.innerHTML = content;
    return studentBox;
}

function editStudent(studentId) {
    const studentBox = document.getElementById(studentId);
    const form = createEditForm(studentId);

    if (studentBox.classList.contains('edit-form')) {
        saveEditedInfo(studentId, form);
        studentBox.classList.remove('edit-form');
        studentBox.innerHTML = form.querySelector('form').innerHTML;
        studentBox.querySelector('button').addEventListener('click', () => editStudent(studentId));

        const studentData = getStudentData(studentId);
        setFormValues(form, studentData);
    } else {
        studentBox.classList.add('edit-form');
        studentBox.innerHTML = '';
        studentBox.appendChild(form);

        const studentData = getStudentData(studentId);
        setFormValues(form, studentData);
    }
}

function createEditForm(studentId) {
    const form = document.createElement('div');
    form.innerHTML = `
        <form onsubmit="event.preventDefault();">
            <label>Nume:</label>
            <input type="text" name="name" required>
            <label>Prenume:</label>
            <input type="text" name="firstName" required>
            <label>CNP:</label>
            <input type="text" name="cnp" minlength="13" maxlength="13"
                onkeypress='return event.charCode >= 48 && event.charCode <= 57'
                required/>
            <label>Data de nastere:</label>
            <input type="date" name="birthDate" required>
            <label>Anul de studiu</label>
            <select required class="selectstyle" name="anStudiu">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <label>Nivelul de studiu</label>
            <select required class="selectstyle" name="nivStudiu">
                <option value="Licenta">Licenta</option>
                <option value="Master">Master</option>
            </select>
            <label>Forma de finantare</label>
            <select required class="selectstyle" name="formaFinantare">
                <option value="Buget">Buget</option>
                <option value="Taxa">Taxa</option>
            </select>
            <label>Liceu absolvit</label>
            <select class="selectstyle" name="liceu">
                <option value="Da">Da</option>
                <option value="Nu">Nu</option>
            </select>
             <button onclick="${studentId ? saveEditedInfo(studentId, this.form) : saveNewStudent(this)}">Salvare</button>

        </form>
    `;
    return form;
}

function setFormValues(form, studentData) {
    form.querySelector('input[name="name"]').value = studentData.firstName;
    form.querySelector('input[name="firstName"]').value = studentData.lastName;
    form.querySelector('input[name="cnp"]').value = studentData.cnp;
    form.querySelector('input[name="birthDate"]').value = studentData.birthDate;
    form.querySelector('select[name="anStudiu"]').value = studentData.studyYear;
    form.querySelector('select[name="nivStudiu"]').value = studentData.studyLevel;
    form.querySelector('select[name="formaFinantare"]').value = studentData.fundingForm;
    form.querySelector('select[name="liceu"]').value = studentData.graduatedHighSchool;
}

function getStudentData(studentId) {
    return {
        name: "Marian",
        firstName: "Marian din dej",
        cnp: "1234567890123",
        birthDate: "2000-01-01",
        anStudiu: "2",
        nivStudiu: "Master",
        formaFinantare: "Buget",
        liceu: "Nu"
    };
}

function saveNewStudent(button) {
    const newStudent = saveEditedInfo(form);
    studentDataList.push(newStudent);
    generateStudentBoxes();

}

function saveEditedInfo(studentId, form) {
    const firstName = form.elements['firstName'].value;
    const lastName = form.elements['lastName'].value;
    const cnp = form.elements['cnp'].value;
    const birthDate = form.elements['birthDate'].value;
    const studyYear = form.elements['studyYear'].value;
    const studyLevel = form.elements['studyLevel'].value;
    const fundingForm = form.elements['fundingForm'].value;
    const graduatedHighSchool = form.elements['graduatedHighSchool'].value;

    const studentBox = document.getElementById(studentId);
    studentBox.innerHTML = `
        <p>Nume: ${studentData.firstName}</p>
        <p>Prenume: ${studentData.lastName}</p>
        <p>CNP: ${studentData.cnp}</p>
        <p>Data de nastere: ${studentData.birthDate}</p>
        <p>Anul de studiu: ${studentData.studyYear}</p>
        <p>Nivelul de studiu: ${studentData.studyLevel}</p>
        <p>Forma de finantare: ${studentData.fundingForm}</p>
        <p>Liceu absolvit: ${studentData.graduatedHighSchool}</p>
        <button class="buttonModify" onclick="editStudent(studentId)">Modifica student</button>
    `;
}

function addStudentForm() {
    const nextStudentId = getNextStudentId();
    const form = createEditForm(nextStudentId);

    const scrollableDiv = document.querySelector('.scrollableDiv');
    scrollableDiv.appendChild(form);
}

function getNextStudentId() {
    const existingIds = studentDataList.map(student => student.id);
    if (existingIds.length === 0) {
        return 1;
    }
    const nextId = Math.max(...existingIds) + 1;
    return nextId;
}

//
// htmx.onLoad(function () {
//     htmx.ajax('api/studentsdents')
// });

generateStudentBoxes();