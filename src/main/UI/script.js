//Sterge : mock data
// const studentDataList = [
//     {
//         id: 'student1',
//         name: 'Marian',
//         firstname: 'Marian din Dej',
//         cnp: '1234567890123',
//         birthdate: '2000-01-01',
//         anStudiu: '2',
//         nivStudiu: 'Master',
//         formaFinantare: 'Buget',
//         liceu: 'Nu'
//     },
//     {
//         id: 'student2',
//         name: 'Dorian',
//         firstname: 'Dorian din Dej',
//         cnp: '1234567890123',
//         birthdate: '2000-01-01',
//         anStudiu: '2',
//         nivStudiu: 'Master',
//         formaFinantare: 'Buget',
//         liceu: 'Nu'
//     },
//     {
//         id: 'student3',
//         name: 'Dorian',
//         firstname: 'Dorian din Dej',
//         cnp: '1234567890123',
//         birthdate: '2000-01-01',
//         anStudiu: '2',
//         nivStudiu: 'Master',
//         formaFinantare: 'Buget',
//         liceu: 'Nu'
//     },
//     {
//         id: 'student4',
//         name: 'Dorian',
//         firstname: 'Dorian din Dej',
//         cnp: '1234567890123',
//         birthdate: '2000-01-01',
//         anStudiu: '2',
//         nivStudiu: 'Master',
//         formaFinantare: 'Buget',
//         liceu: 'Nu'
//     }
// ];

const studentDataList = [];

async function fetchStudents() {
    try {
        const response = await fetch('/api/students');
        const students = await response.json();
        studentDataList.push(...students);
        generateStudentBoxes();
    } catch (error) {
        console.error('Error fetching students:', error);
    }
}
fetchStudents();

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
    <p>Nume: ${studentData.name}</p>
    <p>Prenume: ${studentData.firstname}</p>
    <p>CNP: ${studentData.cnp}</p>
    <p>Data de nastere: ${studentData.birthdate}</p>
    <p>Anul de studiu: ${studentData.anStudiu}</p>
    <p>Nivelul de studiu: ${studentData.nivStudiu}</p>
    <p>Forma de finantare: ${studentData.formaFinantare}</p>
    <p>Liceu absolvit: ${studentData.liceu}</p>
    <button class="buttonModify" onclick="editStudent('${studentData.id}')">Modifica student</button>
  `;

    studentBox.innerHTML = content;
    return studentBox;
}



////////////////////////////////////////////////////

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
            <input type="text" name="firstname" required>
            <label>CNP:</label>
            <input type="text" name="cnp" minlength="13" maxlength="13"
                onkeypress='return event.charCode >= 48 && event.charCode <= 57'
                required/>
            <label>Data de nastere:</label>
            <input type="date" name="birthdate" required>
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
             <button onclick="${studentId ? `saveEditedInfo('${studentId}', this.form)` : 'saveNewStudent(this)'}">Salvare</button>

        </form>
    `;
    return form;
}

function setFormValues(form, studentData) {
    form.querySelector('input[name="name"]').value = studentData.name;
    form.querySelector('input[name="firstname"]').value = studentData.firstname;
    form.querySelector('input[name="cnp"]').value = studentData.cnp;
    form.querySelector('input[name="birthdate"]').value = studentData.birthdate;
    form.querySelector('select[name="anStudiu"]').value = studentData.anStudiu;
    form.querySelector('select[name="nivStudiu"]').value = studentData.nivStudiu;
    form.querySelector('select[name="formaFinantare"]').value = studentData.formaFinantare;
    form.querySelector('select[name="liceu"]').value = studentData.liceu;
}

function getStudentData(studentId) {
    return {
        name: "Marian",
        firstname: "Marian din dej",
        cnp: "1234567890123",
        birthdate: "2000-01-01",
        anStudiu: "2",
        nivStudiu: "Master",
        formaFinantare: "Buget",
        liceu: "Nu"
    };
}
function saveNewStudent(button) {
    const form = button.parentElement;
    const name = form.elements['name'].value;
    const firstname = form.elements['firstname'].value;
    const cnp = form.elements['cnp'].value;
    const birthdate = form.elements['birthdate'].value;
    const anStudiu = form.elements['anStudiu'].value;
    const nivStudiu = form.elements['nivStudiu'].value;
    const formaFinantare = form.elements['formaFinantare'].value;
    const liceu = form.elements['liceu'].value;

    const newStudentId = getNextStudentId();
    const newStudent = {
        id: newStudentId,
        name,
        firstname,
        cnp,
        birthdate,
        anStudiu,
        nivStudiu,
        formaFinantare,
        liceu
    };

    studentDataList.push(newStudent);
    const studentBox = createStudentBox(newStudent);

    const scrollableDiv = document.querySelector('.scrollableDiv');
    scrollableDiv.appendChild(studentBox);
}

function saveEditedInfo(studentId, form) {
    const name = form.elements['name'].value;
    const firstname = form.elements['firstname'].value;
    const cnp = form.elements['cnp'].value;
    const birthdate = form.elements['birthdate'].value;
    const anStudiu = form.elements['anStudiu'].value;
    const nivStudiu = form.elements['nivStudiu'].value;
    const formaFinantare = form.elements['formaFinantare'].value;
    const liceu = form.elements['liceu'].value;

    const studentBox = document.getElementById(studentId);
    studentBox.innerHTML = `
        <p>Nume: ${name}</p>
        <p>Prenume: ${firstname}</p>
        <p>CNP: ${cnp}</p>
        <p>Data de nastere: ${birthdate}</p>
        <p>Anul de studiu: ${anStudiu}</p>
        <p>Nivelul de studiu: ${nivStudiu}</p>
        <p>Forma de finantare: ${formaFinantare}</p>
        <p>Liceu absolvit: ${liceu}</p>
        <button class="buttonModify" onclick="editStudent('${studentId}')">Modifica student</button>
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
    let newId = 1;

    while (existingIds.includes(`student${newId}`)) {
        newId++;
    }

    return `student${newId}`;
}

generateStudentBoxes();