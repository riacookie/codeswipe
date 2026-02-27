async function loadProfile() {
    const userId = getUserId();
    const user = await apiCall(`/users/${userId}`);
    if (user) {
        document.getElementById("profName").value = user.username || "";
        document.getElementById("profEmail").value = user.email || "";
        document.getElementById("profPhone").value = user.telephone || "";
        if (user.avtUrl) {
            document.getElementById("avatarPreview").style.backgroundImage = `url('${user.avtUrl}')`;
        }
    }
}

async function saveProfile() {
    const userId = getUserId();
    const updatedData = {
        username: document.getElementById("profName").value,
        telephone: document.getElementById("profPhone").value
    };
    const result = await apiCall(`/users/${userId}`, "PUT", updatedData);
    if (result) {
        alert("Profile updated successfully.");
        localStorage.setItem("userName", updatedData.username);
    }
}

loadProfile();