function showToast(message, isError = false) {
    const toast = document.getElementById("toast");
    toast.textContent = message;
    toast.className = "toast" + (isError ? " error" : " success");
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), 3000);
}

function setAvatarInitials(name) {
    const preview = document.getElementById("avatarPreview");
    const initials = document.getElementById("avatarInitials");
    if (!preview.style.backgroundImage || preview.style.backgroundImage === "none") {
        const parts = (name || "?").trim().split(" ");
        const text = parts.length >= 2
            ? parts[0][0] + parts[parts.length - 1][0]
            : parts[0][0];
        initials.textContent = text.toUpperCase();
        initials.style.display = "flex";
    } else {
        initials.style.display = "none";
    }
}

async function loadProfile() {
    const userId = getUserId();
    if (!userId) return;

    const [user, projects] = await Promise.all([
        apiCall(`/users/${userId}`),
        apiCall(`/projects/user/${userId}`)
    ]);

    if (user) {
        document.getElementById("displayName").value = user.username || "";
        document.getElementById("displayEmail").value = user.email || "";
        document.getElementById("displayPhone").value = user.telephone || "";

        const levelSelect = document.getElementById("displayLevel");
        if (user.experienceLevel) {
            levelSelect.value = user.experienceLevel;
        }

        if (user.avtUrl) {
            document.getElementById("avatarPreview").style.backgroundImage = `url('${user.avtUrl}')`;
            document.getElementById("avatarInitials").style.display = "none";
        } else {
            document.getElementById("avatarPreview").style.backgroundImage = "none";
            setAvatarInitials(user.username);
        }

        // Stats bar
        document.getElementById("statLevel").textContent = user.experienceLevel || "—";
    }

    if (projects) {
        document.getElementById("statProjects").textContent = projects.length;
    }
}

async function saveProfile() {
    const userId = getUserId();
    if (!userId) return;

    const updatedData = {
        username: document.getElementById("displayName").value.trim(),
        telephone: document.getElementById("displayPhone").value.trim(),
        experienceLevel: document.getElementById("displayLevel").value
    };

    const result = await apiCall(`/users/${userId}`, "PUT", updatedData);

    if (result) {
        alert("Updated");
        localStorage.setItem("userName", updatedData.username);

        // Refresh stats bar
        document.getElementById("statLevel").textContent = updatedData.experienceLevel || "—";
        setAvatarInitials(updatedData.username);
    } else {
        showToast("Failed to update profile.", true);
    }
}

document.addEventListener('DOMContentLoaded', loadProfile);