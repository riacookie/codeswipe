async function loadProfile() {
    const userId = getUserId();
    if (!userId) return;

    const user = await apiCall(`/users/${userId}`);
    if (user) {
        // Update input fields
        document.getElementById("displayName").value = user.username || "";
        document.getElementById("displayEmail").value = user.email || "";
        document.getElementById("displayPhone").value = user.telephone || "";
        
        // These fields might not exist in the user object yet, handle gracefully
        if(user.contact) document.getElementById("displayContact").value = user.contact;
        if(user.level) document.getElementById("displayLevel").value = user.level;

        if (user.avtUrl) {
            document.getElementById("avatarPreview").style.backgroundImage = `url('${user.avtUrl}')`;
        }
    }
}

async function saveProfile() {
    const userId = getUserId();
    if (!userId) return;

    const updatedData = {
        username: document.getElementById("displayName").value,
        telephone: document.getElementById("displayPhone").value,
        contact: document.getElementById("displayContact").value,
        level: document.getElementById("displayLevel").value
    };

    // Note: The backend might not support updating all these fields yet.
    // Adjust the payload based on your API definition.
    
    const result = await apiCall(`/users/${userId}`, "PUT", updatedData);
    
    if (result) {
        alert("Profile updated successfully.");
        localStorage.setItem("userName", updatedData.username);
        
        // Reload profile to ensure sync
        loadProfile();
    }
}

// Load profile when the script runs
document.addEventListener('DOMContentLoaded', loadProfile);