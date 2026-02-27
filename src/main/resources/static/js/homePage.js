let deck = [];
let currentIndex = 0;

async function loadDeck() {
    const userId = getUserId();
    if (!userId) return;
    deck = await apiCall(`/projects/deck/${userId}`);
    renderCard();
}

function renderCard() {
    const container = document.getElementById("cardContainer");

    if (!deck || deck.length === 0 || currentIndex >= deck.length) {
        container.innerHTML = `
            <div class="project-card">
                <div class="polaroid" style="display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding: 40px;">
                    <i class="fa-solid fa-rotate-left" style="font-size: 50px; color: #ff4b91; margin-bottom: 20px;"></i>
                    <h2 style="color: #ff4b91; margin-bottom: 10px;">End of the Deck</h2>
                    <p style="color: #666; margin-bottom: 30px;">You have swiped through all projects. Want to see your skips again?</p>
                    <button onclick="resetSkipsOnly()" class="save-btn" style="width: 200px; cursor: pointer; border:none;">
                        Rescroll Skips
                    </button>
                </div>
            </div>`;
        return;
    }

    const p = deck[currentIndex];
    container.innerHTML = `
        <div class="project-card" id="activeCard">
            <div class="polaroid">
              <div class="card-header">
                <div class="avatar" style="background-image: url('${p.user?.avtUrl || ''}'); background-size: cover;"></div>
                <div class="header-info">
                  <h2>${p.title}</h2>
                  <span class="author">Created by ${p.user?.username || 'User'}</span>
                </div>
                <div class="difficulty-badge ${p.difficultyLevel.toLowerCase()}">${p.difficultyLevel}</div>
              </div>
              <div class="polaroid-caption">
                <div class="card-section">
                  <strong>Description:</strong>
                  <p>${p.description}</p>
                </div>
                <div class="card-section">
                  <strong>Required Skills:</strong>
                  <div class="skills">
                    ${p.skillName.split(',').map(s => `<span class="skill-tag">${s.trim()}</span>`).join('')}
                  </div>
                </div>
                <hr class="card-divider" />
              </div>
            </div>
        </div>`;
}

async function swipe(type) {
    const userId = getUserId();
    const project = deck[currentIndex];
    const card = document.getElementById("activeCard");
    if (!card) return;

    fetch(`${API_BASE}/swipes?userId=${userId}&projectId=${project.projectId}&type=${type}`, { method: 'POST' });

    card.style.transform = type === 'LIKE' ? "translateX(1000px) rotate(30deg)" : "translateX(-1000px) rotate(-30deg)";
    card.style.opacity = "0";

    setTimeout(() => {
        currentIndex++;
        renderCard();
    }, 500);
}

async function resetSkipsOnly() {
    const userId = getUserId();
    const response = await fetch(`${API_BASE}/swipes/reset-skips/${userId}`, { method: 'DELETE' });
    if (response.ok) {
        currentIndex = 0;
        await loadDeck();
    }
}

function swipeLeft() { swipe('SKIP'); }
function swipeRight() { swipe('LIKE'); }
loadDeck();