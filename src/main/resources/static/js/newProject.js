async function confirmPublish() {
    const userId = getUserId();

    const projectData = {
        title: document.querySelector('input[placeholder="Enter project title"]').value,
        description: document.querySelector('textarea').value,
        difficultyLevel: document.querySelector('select').value,
        skillName: document.querySelector('input[placeholder="Java, Spring Boot, MySQL"]').value,
        user: { userId: parseInt(userId) } // Associate with current user
    };

    const result = await apiCall("/projects/all", "POST", projectData); // Note: Make sure ProjectController has a POST /all method

    if (result) {
        alert("Project Published!");
        window.location.href = "/project";
    }
}