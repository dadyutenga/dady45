function allowDrop(event) {
    event.preventDefault();
}

function drag(event) {
    event.dataTransfer.setData("text", event.target.id);
}

function drop(event) {
    event.preventDefault();
    const taskId = event.dataTransfer.getData("text").split("-")[1];
    const newStatus = event.target.closest(".column").id.toUpperCase();
    if (newStatus === "TODO" || newStatus === "IN_PROGRESS" || newStatus === "DONE") {
        // Send AJAX request to update task status
        const formData = new FormData();
        formData.append("taskId", taskId);
        formData.append("status", newStatus);

        fetch("/task/update-status", {
            method: "POST",
            body: formData
        }).then(() => {
            window.location.reload(); // Refresh to update the board
        }).catch(error => {
            console.error("Error updating task status:", error);
        });
    }
}

console.log("Kanban board script loaded.");