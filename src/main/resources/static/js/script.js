// DOM Ready
document.addEventListener('DOMContentLoaded', function() {
    console.log('Todo App loaded successfully!');
    
    // Initialize character counter
    const description = document.getElementById('description');
    const charCount = document.getElementById('charCount');
    
    if (description && charCount) {
        description.addEventListener('input', function() {
            charCount.textContent = this.value.length;
        });
        
        // Trigger initial count
        description.dispatchEvent(new Event('input'));
    }
    
    // Set minimum date for due date input
    const dueDateInput = document.getElementById('dueDate');
    if (dueDateInput) {
        const now = new Date();
        const localDateTime = now.toISOString().slice(0, 16);
        dueDateInput.min = localDateTime;
        
        // Set default to tomorrow
        const tomorrow = new Date(now);
        tomorrow.setDate(tomorrow.getDate() + 1);
        const tomorrowDateTime = tomorrow.toISOString().slice(0, 16);
        dueDateInput.value = tomorrowDateTime;
    }
    
    // Add animation to todo items
    const todoItems = document.querySelectorAll('.todo-item');
    todoItems.forEach((item, index) => {
        item.style.animationDelay = (index * 0.1) + 's';
    });
    
    // Form submission handler
    const todoForm = document.getElementById('todoForm');
    if (todoForm) {
        todoForm.addEventListener('submit', function(e) {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Adding...';
                submitBtn.disabled = true;
            }
        });
    }
});

// Toggle todo status
function toggleTodo(checkbox, event) {
    // Prevent form submission immediately for animation
    event.preventDefault();
    
    // Toggle checkbox visually
    checkbox.classList.toggle('checked');
    
    // Show toast notification if completing
    if (checkbox.classList.contains('checked')) {
        showToast('Task completed! 🎉');
        
        // Create confetti effect
        createConfetti();
    }
    
    // Submit the form after animation
    setTimeout(() => {
        checkbox.closest('form').submit();
    }, 300);
}

// Filter todos
function filterTodos(filter) {
    // Update active filter button
    const buttons = document.querySelectorAll('.filter-btn');
    buttons.forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    // Filter todo items
    const todoItems = document.querySelectorAll('.todo-item');
    
    todoItems.forEach(item => {
        const isCompleted = item.getAttribute('data-completed') === 'true';
        
        switch(filter) {
            case 'all':
                item.style.display = 'flex';
                break;
            case 'pending':
                item.style.display = isCompleted ? 'none' : 'flex';
                break;
            case 'completed':
                item.style.display = isCompleted ? 'flex' : 'none';
                break;
        }
    });
}

// Show toast notification
function showToast(message) {
    const toast = document.getElementById('toast');
    if (!toast) return;
    
    toast.textContent = message;
    toast.style.display = 'block';
    
    // Hide after 3 seconds
    setTimeout(() => {
        toast.style.display = 'none';
    }, 3000);
}

// Create confetti effect
function createConfetti() {
    const confettiColors = ['#667eea', '#764ba2', '#f093fb', '#f5576c', '#4facfe', '#00f2fe'];
    const confettiEmojis = ['🎉', '🎊', '✅', '🌟', '✨'];
    
    for (let i = 0; i < 30; i++) {
        setTimeout(() => {
            const confetti = document.createElement('div');
            confetti.style.position = 'fixed';
            confetti.style.left = Math.random() * 100 + 'vw';
            confetti.style.top = '-50px';
            confetti.style.fontSize = (Math.random() * 20 + 20) + 'px';
            confetti.style.zIndex = '9999';
            confetti.style.pointerEvents = 'none';
            confetti.style.userSelect = 'none';
            confetti.textContent = confettiEmojis[Math.floor(Math.random() * confettiEmojis.length)];
            document.body.appendChild(confetti);
            
            // Animate confetti
            const animation = confetti.animate([
                { 
                    transform: 'translateY(0) rotate(0deg)', 
                    opacity: 1 
                },
                { 
                    transform: `translateY(${window.innerHeight + 100}px) rotate(${Math.random() * 360}deg)`, 
                    opacity: 0 
                }
            ], {
                duration: 1500 + Math.random() * 1000,
                easing: 'cubic-bezier(0.1, 0.8, 0.3, 1)'
            });
            
            animation.onfinish = () => confetti.remove();
        }, i * 50);
    }
}

// Confirm delete
function confirmDelete() {
    return confirm('Are you sure you want to delete this task?');
}

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Ctrl/Cmd + N to focus on title input
    if ((e.ctrlKey || e.metaKey) && e.key === 'n') {
        e.preventDefault();
        const titleInput = document.getElementById('title');
        if (titleInput) {
            titleInput.focus();
        }
    }
    
    // Escape to clear form
    if (e.key === 'Escape') {
        const form = document.getElementById('todoForm');
        if (form) {
            form.reset();
            const charCount = document.getElementById('charCount');
            if (charCount) charCount.textContent = '0';
        }
    }
});

// Add hover effect to stat cards
const statCards = document.querySelectorAll('.stat-card');
statCards.forEach(card => {
    card.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-5px)';
    });
    
    card.addEventListener('mouseleave', function() {
        this.style.transform = 'translateY(0)';
    });
});