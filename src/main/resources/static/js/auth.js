// Firebase config is loaded securely from the backend
async function initializeApp() {
    try {
        const response = await fetch('http://localhost:8080/api/config/firebase');
        if (!response.ok) throw new Error('Failed to load Firebase config');

        const firebaseConfig = await response.json();

        // 2. Initialize Firebase
        firebase.initializeApp(firebaseConfig);
        console.log("Firebase initialized securely");

        // 3. Select the button from the HTML
        const loginBtn = document.getElementById('google-login');
        if (loginBtn) {
            setupLoginButton(loginBtn);
        }

    } catch (error) {
        console.error("Error initializing app:", error);
    }
}

function setupLoginButton(loginBtn) {
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
}

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

// Start the app
initializeApp();
