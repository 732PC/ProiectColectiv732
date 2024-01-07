const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('userId');
const courseId = urlParams.get('courseId');

function closeMateriale() {
    var cursuriPath = 'index.html';
    window.location.href = cursuriPath;
}

if (userId && courseId) {
    loadMaterials(userId, courseId);}

function loadMaterials(userId, courseId) {
    fetch(`/${userId}/courses/${courseId}/materials`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(materialsData => {
            const materialsContainer = document.getElementById("materialsContainer");
            materialsContainer.innerHTML = '';

            materialsData.forEach(material => {
                const materialDiv = createMaterialDiv(material);
                materialsContainer.appendChild(materialDiv);
            });
        })
        .catch(error => console.error('Error fetching materials:', error));
}

function createMaterialDiv(material) {
    const materialDiv = document.createElement('div');
    materialDiv.classList.add('course-window');
    materialDiv.innerHTML = `
        <div class="blue-ribbon"></div>
        <div class="course-details">
            <div class="title">${material.materialName}</div>
            <div class="text">${material.description}</div>
        </div>
    `;
    return materialDiv;
}

function addMat() {
    // Create a modal popup
    const modal = document.createElement('div');
    modal.classList.add('modal');

    // Create content for the modal
    modal.innerHTML = `
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <div class="modal-row">
                <label for="materialTitle">Titlu:</label>
                <input type="text" id="materialTitle" required>
            </div>
            <div class="modal-row">
                <label for="materialDescription">Descriere:</label>
                <textarea id="materialDescription" required></textarea>
            </div>
            <div class="modal-row">
                <button onclick="saveMaterial()">Save</button>
            </div>
        </div>
    `;

    // Append the modal to the body
    document.body.appendChild(modal);

    // Show the modal
    modal.style.display = 'flex';
}

function saveMaterial() {
    const title = document.getElementById('materialTitle').value;
    const description = document.getElementById('materialDescription').value;

    if (title.trim() === '' || description.trim() === '') {
        alert('Both Title and Description are required!');
        return;
    }

    // Perform the save operation (you can add fetch or any other logic here)

    // Close the modal after saving
    closeModal();
}

function closeModal() {
    // Remove the modal from the DOM
    const modal = document.querySelector('.modal');
    if (modal) {
        modal.style.display = 'none';
        modal.remove();
    }
}

function loadCourses(userId) {
    fetch(`/${userId}/courses`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(coursesData => {
            const obligatoryCourses = coursesData.filter(course => course.isObligatory);
            const optionalCourses = coursesData.filter(course => !course.isObligatory);

            const createCourseDiv = course => {
                const courseDiv = document.createElement('div');
                courseDiv.classList.add('course-window');
                courseDiv.innerHTML = `
                    <div class="blue-ribbon"></div>
                    <div class="course-details">
                        <div class="title">${course.courseName}</div>
                        <div class="subtitle">${course.abbreviation}</div>
                        <div class="text">${course.professor}</div>
                        <div class="text">${course.creditNr}</div>
                    </div>
                    <button class="materiale_button" onclick="openMateriale(${userId}, ${course.id})">Materiale</button>
                `;
                return courseDiv;
            };

            const addCoursesToContainer = (courses, containerId) => {
                const courseContainer = document.getElementById(containerId);
                courseContainer.innerHTML = '';
                courses.forEach(course => {
                    const courseDiv = createCourseDiv(course);
                    courseContainer.appendChild(courseDiv);
                });
            };

            addCoursesToContainer(obligatoryCourses, 'obligatoryCoursesContainer');
            addCoursesToContainer(optionalCourses, 'optionalCoursesContainer');
        })
        .catch(error => console.error('Error fetching courses:', error));
}

function openMateriale(userId, courseId) {
    var materialePath = `mat_ui.html?userId=${userId}&courseId=${courseId}`;
    window.location.href = materialePath;
}


document.addEventListener('DOMContentLoaded', loadCourses);