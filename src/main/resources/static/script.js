function fetchStudents() {
    fetch('http://localhost:8081/api/students')
        .then(response => response.json())
        .then(data => {
            studentDataList.push(...data);
            generateStudentBoxes();
        })
        .catch(error => console.error('Error fetching students:', error));
}

const popupContainer = document.getElementById('popup-container');

function displayFeedback(message) {
    popupContainer.innerText = message;
    popupContainer.style.display = 'block';
    setTimeout(() => {
        popupContainer.style.display = 'none';
        location.reload();
    }, 2000);
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
    <h3>${studentData.firstname} ${studentData.lastname}</h3>
    <p>Anul de studiu: ${studentData.studyYear}</p>
    <p>Nivelul de studiu: ${studentData.studyLevel}</p>
    <p>Forma de finantare: ${studentData.fundingForm}</p>
    <button class="buttonModify" onclick="editStudent('${studentData.id}')" style="display: ">Modifica student</button>
  `;
    studentBox.innerHTML = content;
    return studentBox;
}

function editStudent(studentId) {
    const form = createEditForm(studentId);
    const studentBox = document.getElementById(studentId);
    if (studentBox.classList.contains('edit-form')) {
        saveEditedInfo(studentId, form);
        studentBox.classList.remove('edit-form');
        studentBox.innerHTML = form.querySelector('form').innerHTML;
        studentBox.querySelector('button').addEventListener('click', () => editStudent(studentId));
        setFormValues(form, studentId);
    } else {
        studentBox.classList.add('edit-form');
        studentBox.innerHTML = '';
        studentBox.appendChild(form);
        setFormValues(form, studentId);
    }
}

function createEditForm(studentId) {
    const form = document.createElement('div');
    form.innerHTML = `
        <form onsubmit="event.preventDefault();">
            <label>Nume:</label>
            <input type="text" name="firstName" required>
            <label>Prenume:</label>
            <input type="text" name="lastName" required>
            <label>CNP:</label>
            <input type="text" name="cnp" minlength="13" maxlength="13"
                onkeypress='return event.charCode >= 48 && event.charCode <= 57'
                required/>
            <label>Data de nastere:</label>
            <input type="date" name="birthDate" required>
            <label>Anul de studiu</label>
            <select required class="selectstyle" name="studyYear">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <label>Nivelul de studiu</label>
            <select required class="selectstyle" name="studyLevel">
                <option value="Licenta">Licenta</option>
                <option value="Master">Master</option>
            <label>Forma de finantare</label>
            </select>
            <select required class="selectstyle" name="fundingForm">
                <option value="Buget">Buget</option>
                <option value="Taxa">Taxa</option>
            </select>
            <label>Liceu absolvit</label>
            <select class="selectstyle" name="graduatedHighSchool">
                <option value="Da">Da</option>
                <option value="Nu">Nu</option>
            </select>
             <button onclick="${studentId ? `saveEditedInfo('${studentId}', this.form)` : 'saveNewStudent(this, this.form)'}">Salvare</button>
        </form>
    `;
    return form;
}

async function setFormValues(form, studentId) {
    fetch(`http://localhost:8081/api/students/${studentId}`)
        .then(response => response.json())
        .then(studentData => {
            form.querySelector('input[name="firstName"]').value = studentData.firstname;
            form.querySelector('input[name="lastName"]').value = studentData.lastname;
            form.querySelector('input[name="cnp"]').value = studentData.cnp;
            form.querySelector('input[name="birthDate"]').value = studentData.birthDate;
            form.querySelector('select[name="studyYear"]').value = studentData.studyYear;
            form.querySelector('select[name="studyLevel"]').value = studentData.studyLevel;
            form.querySelector('select[name="fundingForm"]').value = studentData.fundingForm;
            form.querySelector('select[name="graduatedHighSchool"]').value = studentData.graduatedHighSchool;
        })
        .catch(error => console.error('Error fetching students:', error));
}

async function saveEditedInfo(studentId, form) {
    const studentBox = document.getElementById(studentId);
    const updatedStudentData = {
        firstname: form.elements['firstName'].value,
        lastname: form.elements['lastName'].value,
        cnp: form.elements['cnp'].value,
        birthDate: form.elements['birthDate'].value,
        studyYear: form.elements['studyYear'].value,
        studyLevel: form.elements['studyLevel'].value,
        fundingForm: form.elements['fundingForm'].value,
        graduatedHighSchool: form.elements['graduatedHighSchool'].value
    };
    await updateStudentInDatabase(studentId, updatedStudentData);
    const studentDataIndex = studentDataList.findIndex(student => student.id === studentId);
    if (studentDataIndex !== -1) {
        studentDataList[studentDataIndex] = {id: studentId, ...updatedStudentData};
    }
    studentBox.innerHTML = `
        <h3>${updatedStudentData.firstname} ${updatedStudentData.lastname}</h3>
    <p>Anul de studiu: ${updatedStudentData.studyYear}</p>
    <p>Nivelul de studiu: ${updatedStudentData.studyLevel}</p>
    <p>Forma de finantare: ${updatedStudentData.fundingForm}</p>
    <button id="buttonModify" class="buttonModify" onclick="editStudent('${updatedStudentData.id}')" style="display: ">Modifica student</button>
        `;
    document.getElementById('buttonModify').addEventListener('click', displayFeedback("Student modificat cu succes!"));
}

function showAddStudentForm() {
    const addStudentSection = document.getElementById('addStudentDiv');
    addStudentSection.style.display = 'block';
    document.getElementById('addStudentForm').reset();
}

async function updateStudentInDatabase(studentId, updatedStudentData) {
    try {
        const response = await fetch(`http://localhost:8081/api/students/${studentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedStudentData)
        });
        if (!response.ok) {
            throw new Error(`Failed to update student data: ${response.status} ${response.statusText}`);
        }
        console.log('Student data updated successfully.');
    } catch (error) {
        console.error('Error updating student data:', error);
    }
}

async function addStudent() {
    const formData = {
        firstname: document.getElementById('firstName').value,
        lastname: document.getElementById('lastName').value,
        cnp: document.getElementById('cnp').value,
        birthDate: document.getElementById('birthDate').value,
        studyYear: parseInt(document.getElementById('studyYear').value),
        studyLevel: document.getElementById('studyLevel').value,
        fundingForm: document.getElementById('fundingForm').value,
        graduatedHighSchool: document.getElementById('graduatedHighSchool').value,
    };
    console.log('Request Payload:', formData);
    const queryParams = new URLSearchParams(formData);
    try {
        const url = `http://localhost:8081/api/students/addStudent?${queryParams.toString()}`;

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            const data = await response.json();
            document.getElementById('addStudentDiv').style.display = 'none';
            displayFeedback("Student adaugat cu succes!");
            console.log('Server Response:', data);
        } else {
            console.error('Server responded with an error:', response.status, response.statusText);
            const errorData = await response.text();
            console.error('Server error details:', errorData);
        }
    } catch (error) {
        console.error('An error occurred during the fetch:', error);
    }
}

generateStudentBoxes();