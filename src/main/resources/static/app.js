// Base URL of your backend
const BASE_URL = window.location.origin;

// Store current short code globally
let currentShortCode = "";

//  SHORTEN URL
async function shortenUrl() {
    const originalUrl = document.getElementById("originalUrl").value.trim();
    const validityDays = document.getElementById("validityDays").value.trim();

    // Validate inputs
    if (!originalUrl) {
        showError("Please enter a URL!");
        return;
    }

    if (!isValidUrl(originalUrl)) {
        showError("Please enter a valid URL starting with http:// or https://");
        return;
    }

    // Show loader
    showLoader();
    hideError();
    hideResult();

    try {
        const response = await fetch(`${BASE_URL}/api/shorten`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                originalUrl: originalUrl,
                validityDays: validityDays ? parseInt(validityDays) : 30
            })
        });

        const data = await response.json();

        if (response.ok) {
            // Store short code
            currentShortCode = data.shortCode;

            // Display result
            const shortUrlElement = document.getElementById("shortUrl");
            shortUrlElement.href = data.shortUrl;
            shortUrlElement.textContent = data.shortUrl;

            showResult();
        } else {
            showError(data.message || "Something went wrong!");
        }

    } catch (error) {
        showError("Cannot connect to server. Make sure your backend is running!");
    } finally {
        hideLoader();
    }
}

//  COPY URL
function copyUrl() {
    const shortUrl = document.getElementById("shortUrl").textContent;
    navigator.clipboard.writeText(shortUrl).then(() => {
        alert("Short URL copied to clipboard!");
    }).catch(() => {
        showError("Failed to copy URL!");
    });
}

//  GET STATS
async function getStats() {
    if (!currentShortCode) {
        showError("No short URL found!");
        return;
    }

    console.log("Fetching stats for: " + currentShortCode);
    try {
        const response = await fetch(`${BASE_URL}/api/stats/${currentShortCode}`);
        const data = await response.json();

        if (response.ok) {
            // Fill stats card
            document.getElementById("statOriginalUrl").textContent = data.originalUrl;
            document.getElementById("statShortUrl").textContent = data.shortUrl;
            document.getElementById("statCreatedAt").textContent = formatDate(data.createdAt);
            document.getElementById("statExpiresAt").textContent = data.expiresAt ? formatDate(data.expiresAt) : "Never";
            document.getElementById("statClickCount").textContent = data.clickCount;

            // Show stats card
            document.getElementById("statsCard").classList.remove("hidden");

            // Scroll to stats
            document.getElementById("statsCard").scrollIntoView({ behavior: "smooth" });

        } else {
            showError(data.message || "Could not fetch stats!");
        }

    } catch (error) {
        showError("Cannot connect to server. Make sure your backend is running!");
    }
}

//  DELETE URL
async function deleteUrl() {
    if (!currentShortCode) {
        showError("No short URL found!");
        return;
    }

    const confirmDelete = confirm("Are you sure you want to delete this short URL?");
    if (!confirmDelete) return;

    try {
        const response = await fetch(`${BASE_URL}/api/delete/${currentShortCode}`, {
            method: "DELETE"
        });

        if (response.ok) {
            alert("Short URL deleted successfully!");
            // Reset everything
            hideResult();
            hideError();
            document.getElementById("statsCard").classList.add("hidden");
            document.getElementById("originalUrl").value = "";
            document.getElementById("validityDays").value = "";
            currentShortCode = "";
        } else {
            const data = await response.json();
            showError(data.message || "Could not delete URL!");
        }

    } catch (error) {
        showError("Cannot connect to server. Make sure your backend is running!");
    }
}

//  HELPER FUNCTIONS

// Validate URL format
function isValidUrl(url) {
    try {
        new URL(url);
        return true;
    } catch {
        return false;
    }
}

// Format date nicely
function formatDate(dateString) {
    if (!dateString) return "N/A";
    const date = new Date(dateString);
    return date.toLocaleString("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        hour12: true
    });
}

// Show loader
function showLoader() {
    document.getElementById("loader").classList.remove("hidden");
}

// Hide loader
function hideLoader() {
    document.getElementById("loader").classList.add("hidden");
}

// Show error message
function showError(message) {
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.textContent = message;
    errorMsg.classList.remove("hidden");
}

// Hide error message
function hideError() {
    document.getElementById("errorMsg").classList.add("hidden");
}

// Show result section
function showResult() {
    document.getElementById("result").classList.remove("hidden");
}

// Hide result section
function hideResult() {
    document.getElementById("result").classList.add("hidden");
}