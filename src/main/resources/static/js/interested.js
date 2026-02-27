async function loadLiked() {
    const userId = getUserId();
    const projects = await apiCall(`/swipes/liked/${userId}`);
    const grid = document.querySelector(".projects-grid");
    grid.innerHTML = "";

    if (!projects || projects.length === 0) {
        grid.innerHTML = "<p>No projects in your interested list.</p>";
        return;
    }

    projects.forEach(p => {
        grid.innerHTML += `
            <div class="project-card" style="position: relative;">
              <button onclick="removeInterest(${p.projectId})" 
                      style="position: absolute; top: 15px; right: 15px; background: none; border: none; color: #ff4b91; cursor: pointer; font-size: 18px;">
                  <i class="fa-solid fa-trash-can"></i>
              </button>
              <div class="card-header">
                <div class="avatar" style="background-image:url('${p.user?.avtUrl || ''}'); background-size:cover;"></div>
                <div>
                  <h3>${p.title}</h3>
                  <span class="author">by ${p.user?.username || 'User'}</span>
                </div>
              </div>
              <p class="description">${p.description}</p>
              <div class="skills">
                ${p.skillName.split(',').map(s => `<span>${s.trim()}</span>`).join('')}
              </div>
              <div class="difficulty ${p.difficultyLevel.toLowerCase()}">${p.difficultyLevel}</div>
            </div>`;
    });
}

async function removeInterest(projectId) {
    if (!confirm("Remove this project from your list?")) return;
    const userId = getUserId();
    await fetch(`${API_BASE}/swipes/unlike?userId=${userId}&projectId=${projectId}`, {method: 'DELETE'});
    loadLiked();
}

loadLiked();