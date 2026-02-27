async function loadMyCreatedProjects() {
    const userId = getUserId();
    const projects = await apiCall(`/projects/user/${userId}`);
    const grid = document.querySelector(".projects-grid");
    grid.innerHTML = "";

    if (!projects || projects.length === 0) {
        grid.innerHTML = "<p>No projects created yet.</p>";
        return;
    }

    projects.forEach(p => {
        grid.innerHTML += `
        <div class="my-project-card">
          <h3>${p.title}</h3>
          <p class="description">${p.description}</p>
          <div class="meta">
            <span><strong>Difficulty:</strong> ${p.difficultyLevel}</span>
            <span><strong>Skills:</strong> ${p.skillName}</span>
          </div>
          <div class="card-actions">
            <button class="delete-btn" onclick="deleteProject(${p.projectId})">
              <i class="fa-solid fa-trash"></i>
            </button>
          </div>
        </div>`;
    });
}

async function deleteProject(projectId) {
    if (!confirm("Are you sure you want to delete this project?")) return;
    const response = await fetch(`${API_BASE}/projects/${projectId}`, {method: 'DELETE'});
    if (response.ok) {
        alert("Project deleted.");
        loadMyCreatedProjects();
    }
}

loadMyCreatedProjects();