const API_BASE = "http://localhost:8080/api";

// Get the logged-in User ID
function getUserId() {
    const id = localStorage.getItem("userId");
    if (!id) {
        window.location.href = "/login";
        return null;
    }
    return id;
}

// Clean helper for Fetch
async function apiCall(endpoint, method = "GET", body = null) {
    const url = endpoint.startsWith('http') ? endpoint : `${API_BASE}${endpoint}`;

    const options = {
        method,
        headers: {
            "Content-Type": "application/json"
        },
        credentials: 'include'
    };

    if (body) options.body = JSON.stringify(body);

    const response = await fetch(url, options);
    if (!response.ok) {
        console.error("API Error:", await response.text());
        return null;
    }
    // Handle cases where response might be a string instead of JSON
    const contentType = response.headers.get("content-type");
    return contentType && contentType.includes("application/json") ? response.json() : response.text();
}