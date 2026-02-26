const container = document.getElementById("cardContainer");

function swipeLeft() {
    const topCard = document.querySelector(".project-card");
    if (!topCard) return;

    topCard.style.transform = "translateX(-800px) rotate(-20deg)";
    topCard.style.opacity = "0";

    setTimeout(() => {
        container.appendChild(topCard);
        resetCards();
    }, 400);
}

function swipeRight() {
    const topCard = document.querySelector(".project-card");
    if (!topCard) return;

    topCard.style.transform = "translateX(800px) rotate(20deg)";
    topCard.style.opacity = "0";

    setTimeout(() => {
        container.appendChild(topCard);
        resetCards();
    }, 400);
}

function resetCards() {
    const cards = document.querySelectorAll(".project-card");
    cards.forEach(card => {
        card.style.transform = "";
        card.style.opacity = "1";
    });
}