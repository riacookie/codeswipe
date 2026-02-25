// 1. Paste your Firebase Config here
const firebaseConfig = {
    apiKey: "AIzaSyCFijmuK0x4Mu8W2KXbuOyXKE0KvB68B_Y",
    authDomain: "codeswipe-24f7e.firebaseapp.com",
    projectId: "codeswipe-24f7e",
    storageBucket: "codeswipe-24f7e.firebasestorage.app",
    messagingSenderId: "40195048342",
    appId: "1:40195048342:web:1b034e974ea9d81d8bd3b7",
    measurementId: "G-N45RVBYBWH"
};

// 2. Initialize Firebase
firebase.initializeApp(firebaseConfig);

// 3. Select the button from the HTML
const loginBtn = document.getElementById('google-login');

// 4. When the button is clicked...
loginBtn.addEventListener('click', () => {
    const provider = new firebase.auth.GoogleAuthProvider();

    // Open the Google Popup
    firebase.auth().signInWithPopup(provider)
        .then((result) => {
            const user = result.user;
            console.log("Google Login Successful:", user.displayName);

            // Send data to your Spring Boot Backend
            saveUserToBackend(user);
        })
        .catch((error) => {
            console.error("Login Error:", error);
            alert("Login failed! Check console.");
        });
});

// 5. Function to send data to Spring Boot
async function saveUserToBackend(firebaseUser) {
    const userData = {
        username: firebaseUser.displayName,
        email: firebaseUser.email,
        avtUrl: firebaseUser.photoURL, // This is the Google profile pic
        role: "STUDENT"
    };

    try {
        const response = await fetch('http://localhost:8080/api/auth/google-login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            const backendUser = await response.json();

            // IMPORTANT: Save the userId from our database so we can use it later
            localStorage.setItem("userId", backendUser.userId);
            localStorage.setItem("userName", backendUser.username);

            alert("Logged in as " + backendUser.username);

            // Redirect to the swipe page (index.html)
            window.location.href = "index.html";
        } else {
            console.error("Backend Error:", response.status);
        }
    } catch (error) {
        console.error("Fetch Error:", error);
    }
}