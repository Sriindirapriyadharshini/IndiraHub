/**
 * IndiraHub - E-Commerce Client Application
 */

const FALLBACK_PRODUCTS = [
    {
        id: 1,
        name: "ProBook Ultra 16\" Laptop",
        description: "Intel Core i7 13th Gen, 16GB DDR5 RAM, 1TB NVMe SSD, 16-inch 2.5K 120Hz IPS Display with backlit keyboard.",
        price: 45999.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=700&q=80",
        stock: 25,
        rating: 4.8,
        reviewsCount: 142,
        badge: "Bestseller"
    },
    {
        id: 2,
        name: "Nexus Neo 5G Smartphone",
        description: "6.7-inch 120Hz AMOLED, 50MP Sony OIS Camera, Snapdragon 7+ Gen 3, 5000mAh Battery with 67W Turbo Charging.",
        price: 19999.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1598327105666-5b89351aff97?auto=format&fit=crop&w=700&q=80",
        stock: 40,
        rating: 4.6,
        reviewsCount: 98,
        badge: "Hot"
    },
    {
        id: 3,
        name: "AcousticPro ANC Wireless Headphones",
        description: "Active Noise Cancelling, Hi-Res Audio certification, 40-hour playback, ultra-soft memory foam earcups.",
        price: 2499.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=700&q=80",
        stock: 60,
        rating: 4.7,
        reviewsCount: 215,
        badge: "Top Rated"
    },
    {
        id: 4,
        name: "Apex Pulse Smartwatch",
        description: "1.96-inch AMOLED Always-On Display, Bluetooth calling, 100+ Sports modes, SpO2 & 24/7 Heart Rate monitoring.",
        price: 1999.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=700&q=80",
        stock: 85,
        rating: 4.5,
        reviewsCount: 180,
        badge: "Popular"
    },
    {
        id: 5,
        name: "Vortex RGB Mechanical Keyboard",
        description: "Hot-swappable Custom Linear Red switches, per-key RGB backlighting, aircraft-grade aluminum frame.",
        price: 899.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=700&q=80",
        stock: 55,
        rating: 4.6,
        reviewsCount: 88,
        badge: "Trending"
    },
    {
        id: 6,
        name: "TurboCharge 20000mAh Power Bank",
        description: "22.5W Two-way Fast Charging, Dual USB-A + Type-C Power Delivery, compact anti-scratch shell.",
        price: 799.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1609592424109-dd9892f1b177?auto=format&fit=crop&w=700&q=80",
        stock: 110,
        rating: 4.4,
        reviewsCount: 164,
        badge: "Essential"
    },
    {
        id: 7,
        name: "VisionView 27\" QHD Gaming Monitor",
        description: "2560x1440 IPS Panel, 165Hz Refresh Rate, 1ms Response Time, HDR400, AMD FreeSync Premium support.",
        price: 16499.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=700&q=80",
        stock: 18,
        rating: 4.9,
        reviewsCount: 64,
        badge: "New"
    },
    {
        id: 8,
        name: "PrecisionStrike Wireless Gaming Mouse",
        description: "PAW3395 26000 DPI Optical Sensor, 59g ultra-lightweight ergonomic chassis, 80-hour battery life.",
        price: 1299.00,
        category: "Electronics",
        imageUrl: "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?auto=format&fit=crop&w=700&q=80",
        stock: 45,
        rating: 4.6,
        reviewsCount: 73,
        badge: "Top Choice"
    },
    {
        id: 9,
        name: "Mastering Java & Spring Boot",
        description: "Comprehensive masterclass from core modern Java 21 concepts through robust Spring Boot microservices.",
        price: 599.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=700&q=80",
        stock: 70,
        rating: 4.9,
        reviewsCount: 310,
        badge: "Bestseller"
    },
    {
        id: 10,
        name: "The Art of C Programming",
        description: "From foundational memory pointers to advanced systems programming and embedded architectures.",
        price: 499.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1532012164546-f432f2e3777f?auto=format&fit=crop&w=700&q=80",
        stock: 50,
        rating: 4.7,
        reviewsCount: 145,
        badge: "Classic"
    },
    {
        id: 11,
        name: "Python For Data Science & AI",
        description: "Hands-on guide covering NumPy, Pandas, Scikit-Learn, neural networks, and modern LLM application workflows.",
        price: 699.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=700&q=80",
        stock: 95,
        rating: 4.8,
        reviewsCount: 260,
        badge: "Top Rated"
    },
    {
        id: 12,
        name: "Data Structures & Algorithms Made Easy",
        description: "In-depth problem-solving patterns, tree traversals, dynamic programming, and FAANG interview readiness.",
        price: 749.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=700&q=80",
        stock: 65,
        rating: 4.9,
        reviewsCount: 412,
        badge: "Must Read"
    },
    {
        id: 13,
        name: "Computer Networks & Distributed Systems",
        description: "Thorough exploration of modern TCP/IP, HTTP/3, WebSocket protocols, cloud routing, and cyber defenses.",
        price: 799.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=700&q=80",
        stock: 40,
        rating: 4.6,
        reviewsCount: 118,
        badge: "Academic"
    },
    {
        id: 14,
        name: "The Midnight Chronicle: Best-Selling Novel",
        description: "An enchanting, award-winning international fiction bestseller about second chances, parallel lives, and hope.",
        price: 399.00,
        category: "Books",
        imageUrl: "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?auto=format&fit=crop&w=700&q=80",
        stock: 80,
        rating: 4.7,
        reviewsCount: 340,
        badge: "Staff Pick"
    }
];

// App State
let state = {
    products: [],
    filteredProducts: [],
    currentCategory: 'All',
    searchQuery: '',
    sortBy: 'featured',
    cart: JSON.parse(localStorage.getItem('indirahub_cart') || '[]'),
    user: JSON.parse(localStorage.getItem('indirahub_user') || 'null'),
    selectedPaymentMethod: 'COD'
};

// DOM Content Loaded
document.addEventListener('DOMContentLoaded', () => {
    initAuthUI();
    loadProducts();
    updateCartUI();
    setupEventListeners();
});

// Setup Global Event Listeners
function setupEventListeners() {
    // Search input
    const searchInput = document.getElementById('search-input');
    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            state.searchQuery = e.target.value.toLowerCase().trim();
            filterAndRenderProducts();
        });
    }

    // Sort select
    const sortSelect = document.getElementById('sort-select');
    if (sortSelect) {
        sortSelect.addEventListener('change', (e) => {
            state.sortBy = e.target.value;
            filterAndRenderProducts();
        });
    }

    // Category Tabs
    const tabs = document.querySelectorAll('.tab-btn');
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            state.currentCategory = tab.dataset.category;
            filterAndRenderProducts();
        });
    });

    // Payment method selector
    const paymentCards = document.querySelectorAll('.payment-card');
    paymentCards.forEach(card => {
        card.addEventListener('click', () => {
            paymentCards.forEach(c => c.classList.remove('selected'));
            card.classList.add('selected');
            const radio = card.querySelector('input[type="radio"]');
            if (radio) {
                radio.checked = true;
                state.selectedPaymentMethod = radio.value;
            }
        });
    });
}

function triggerSearch() {
    const searchInput = document.getElementById('search-input');
    if (searchInput) {
        state.searchQuery = searchInput.value.toLowerCase().trim();
        filterAndRenderProducts();
        const catalogSection = document.getElementById('catalog-section');
        if (catalogSection) catalogSection.scrollIntoView({ behavior: 'smooth' });
    }
}

// Authentication & Header UI
function initAuthUI() {
    const authContainer = document.getElementById('auth-nav-container');
    if (!authContainer) return;

    if (state.user) {
        const initial = state.user.fullName ? state.user.fullName.charAt(0).toUpperCase() : 'U';
        const isAdmin = state.user.role === 'ADMIN';
        authContainer.innerHTML = `
            <div class="user-badge" title="Logged in as ${state.user.email || state.user.username}">
                <div class="user-avatar">${initial}</div>
                <span>Hi, ${state.user.fullName || state.user.username}${isAdmin ? ' 👑' : ''}</span>
            </div>
            <button class="btn-icon" onclick="openOrdersModal()" title="View your past orders">
                📦 Orders
            </button>
            <button class="btn-danger" onclick="logout()" title="Sign out">
                Logout
            </button>
        `;
    } else {
        authContainer.innerHTML = `
            <a href="login.html" class="btn-primary" id="login-nav-btn">
                <span>Sign In</span>
            </a>
        `;
    }
}

function logout() {
    localStorage.removeItem('indirahub_user');
    state.user = null;
    showToast('Logged out successfully', 'info');
    setTimeout(() => {
        window.location.href = 'login.html';
    }, 600);
}

// Fetch Products from Spring Boot Backend
async function loadProducts() {
    try {
        const response = await fetch('/api/products');
        if (response.ok) {
            const data = await response.json();
            if (Array.isArray(data) && data.length > 0) {
                state.products = data;
            } else {
                state.products = FALLBACK_PRODUCTS;
            }
        } else {
            state.products = FALLBACK_PRODUCTS;
        }
    } catch (err) {
        console.warn('API endpoint unavailable, using initialized catalog fallback:', err);
        state.products = FALLBACK_PRODUCTS;
    }
    filterAndRenderProducts();
}

// Filter and Render Products
function filterAndRenderProducts() {
    let list = [...state.products];

    // Filter by Category
    if (state.currentCategory && state.currentCategory !== 'All') {
        list = list.filter(p => p.category.toLowerCase() === state.currentCategory.toLowerCase());
    }

    // Filter by Search Query
    if (state.searchQuery) {
        list = list.filter(p =>
            p.name.toLowerCase().includes(state.searchQuery) ||
            p.description.toLowerCase().includes(state.searchQuery) ||
            p.category.toLowerCase().includes(state.searchQuery)
        );
    }

    // Sort
    if (state.sortBy === 'price-low') {
        list.sort((a, b) => a.price - b.price);
    } else if (state.sortBy === 'price-high') {
        list.sort((a, b) => b.price - a.price);
    } else if (state.sortBy === 'rating') {
        list.sort((a, b) => b.rating - a.rating);
    }

    state.filteredProducts = list;

    // Update count badge
    const countBadge = document.getElementById('products-count');
    if (countBadge) {
        countBadge.textContent = `${list.length} item${list.length === 1 ? '' : 's'}`;
    }

    renderProductsGrid(list);
}

// Render Products Grid
function renderProductsGrid(items) {
    const grid = document.getElementById('products-grid');
    if (!grid) return;

    if (items.length === 0) {
        grid.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🔍</div>
                <h3 class="empty-state-title">No products found</h3>
                <p class="empty-state-text">Try adjusting your search query or switching categories.</p>
                <button class="btn-outline" onclick="resetFilters()">View All Products</button>
            </div>
        `;
        return;
    }

    grid.innerHTML = items.map(product => {
        const categoryIcon = product.category === 'Electronics' ? '📱' : (product.category === 'Books' ? '📚' : '✨');
        const formattedPrice = Number(product.price).toLocaleString('en-IN');
        
        // High-urgency contextual badges
        let badgeClass = 'badge-tag';
        if (product.badge) {
            const b = product.badge.toLowerCase();
            if (b.includes('hot') || b.includes('deal') || b.includes('sale') || b.includes('urgent')) {
                badgeClass = 'badge-hot';
            } else if (b.includes('bestseller') || b.includes('top') || b.includes('popular') || b.includes('trending')) {
                badgeClass = 'badge-bestseller';
            }
        }
        const badgeHtml = product.badge ? `<span class="product-badge-tag ${badgeClass}">${product.badge}</span>` : '';

        return `
            <div class="product-card" id="product-${product.id}">
                ${badgeHtml}
                <div class="product-card-img-wrap" onclick="openQuickView(${product.id})">
                    <img class="product-card-img" src="${product.imageUrl}" alt="${product.name}" loading="lazy" onerror="this.src='https://images.unsplash.com/photo-1526738549149-8e07eca6c147?auto=format&fit=crop&w=700&q=80'">
                    <div class="quick-view-overlay">
                        <button class="quick-view-btn">Quick View</button>
                    </div>
                </div>

                <div class="product-category-meta">
                    <span class="category-label">${categoryIcon} ${product.category}</span>
                    <span class="rating-star">★ ${product.rating || 4.8} <small style="color:var(--text-muted);font-weight:normal">(${product.reviewsCount || 80})</small></span>
                </div>

                <h3 class="product-name" title="${product.name}">${product.name}</h3>
                <p class="product-desc" title="${product.description}">${product.description}</p>

                <div class="product-footer">
                    <div class="price-box">
                        <span class="price-label">Price</span>
                        <span class="price-amount">₹${formattedPrice}</span>
                    </div>
                    <button class="add-cart-btn" onclick="addToCart(${product.id})">
                        <span>+ Add</span>
                    </button>
                </div>
            </div>
        `;
    }).join('');
}

function resetFilters() {
    const searchInput = document.getElementById('search-input');
    if (searchInput) searchInput.value = '';
    state.searchQuery = '';
    state.currentCategory = 'All';

    const tabs = document.querySelectorAll('.tab-btn');
    tabs.forEach(t => {
        if (t.dataset.category === 'All') t.classList.add('active');
        else t.classList.remove('active');
    });

    filterAndRenderProducts();
}

// Cart Management
function addToCart(productId) {
    const product = state.products.find(p => p.id === productId);
    if (!product) return;

    const existingIndex = state.cart.findIndex(item => item.id === productId);
    if (existingIndex > -1) {
        state.cart[existingIndex].quantity += 1;
    } else {
        state.cart.push({
            id: product.id,
            name: product.name,
            price: product.price,
            imageUrl: product.imageUrl,
            category: product.category,
            quantity: 1
        });
    }

    saveCart();
    updateCartUI();
    showToast(`Added "${product.name}" to cart!`, 'success');

    // Button animation feedback
    const card = document.getElementById(`product-${productId}`);
    if (card) {
        const btn = card.querySelector('.add-cart-btn');
        if (btn) {
            btn.classList.add('added');
            btn.innerHTML = '<span>✓ Added</span>';
            setTimeout(() => {
                btn.classList.remove('added');
                btn.innerHTML = '<span>+ Add</span>';
            }, 1200);
        }
    }
}

function updateCartQuantity(productId, delta) {
    const itemIndex = state.cart.findIndex(i => i.id === productId);
    if (itemIndex > -1) {
        state.cart[itemIndex].quantity += delta;
        if (state.cart[itemIndex].quantity <= 0) {
            state.cart.splice(itemIndex, 1);
        }
    }
    saveCart();
    updateCartUI();
}

function removeFromCart(productId) {
    state.cart = state.cart.filter(item => item.id !== productId);
    saveCart();
    updateCartUI();
    showToast('Item removed from cart', 'info');
}

function saveCart() {
    localStorage.setItem('indirahub_cart', JSON.stringify(state.cart));
}

function updateCartUI() {
    const badge = document.getElementById('cart-badge');
    const totalItems = state.cart.reduce((sum, item) => sum + item.quantity, 0);

    if (badge) {
        badge.textContent = totalItems;
        badge.style.display = totalItems > 0 ? 'flex' : 'none';
    }

    const cartDrawerItems = document.getElementById('cart-items-container');
    const subtotalEl = document.getElementById('cart-subtotal');
    const shippingEl = document.getElementById('cart-shipping');
    const totalEl = document.getElementById('cart-total');
    const checkoutBtn = document.getElementById('cart-checkout-btn');
    const freeDeliveryNotice = document.getElementById('free-shipping-notice');
    const freeDeliveryFill = document.getElementById('free-shipping-fill');

    if (!cartDrawerItems) return;

    if (state.cart.length === 0) {
        cartDrawerItems.innerHTML = `
            <div style="text-align:center; padding: 60px 20px; color: var(--text-muted);">
                <div style="font-size: 3rem; margin-bottom: 12px;">🛒</div>
                <h4 style="color:var(--text-primary); margin-bottom: 6px;">Your cart is empty</h4>
                <p style="font-size: 0.9rem;">Add items from Electronics or Books to get started.</p>
            </div>
        `;
        if (subtotalEl) subtotalEl.textContent = '₹0';
        if (shippingEl) shippingEl.textContent = '₹0';
        if (totalEl) totalEl.textContent = '₹0';
        if (checkoutBtn) checkoutBtn.disabled = true;
        if (freeDeliveryNotice) freeDeliveryNotice.textContent = 'Add items worth ₹999 for FREE Delivery';
        if (freeDeliveryFill) freeDeliveryFill.style.width = '0%';
        return;
    }

    if (checkoutBtn) checkoutBtn.disabled = false;

    const subtotal = state.cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    const shippingFee = subtotal >= 999 ? 0 : 99;
    const total = subtotal + shippingFee;

    // Free delivery meter
    if (freeDeliveryNotice && freeDeliveryFill) {
        if (subtotal >= 999) {
            freeDeliveryNotice.innerHTML = '🎉 You qualify for <strong>FREE Delivery</strong>!';
            freeDeliveryFill.style.width = '100%';
        } else {
            const diff = 999 - subtotal;
            freeDeliveryNotice.innerHTML = `Add <strong>₹${diff.toLocaleString('en-IN')}</strong> more for <strong>FREE Delivery</strong>`;
            freeDeliveryFill.style.width = `${Math.min(100, (subtotal / 999) * 100)}%`;
        }
    }

    if (subtotalEl) subtotalEl.textContent = `₹${subtotal.toLocaleString('en-IN')}`;
    if (shippingEl) shippingEl.textContent = shippingFee === 0 ? 'FREE' : `₹${shippingFee}`;
    if (totalEl) totalEl.textContent = `₹${total.toLocaleString('en-IN')}`;

    cartDrawerItems.innerHTML = state.cart.map(item => `
        <div class="cart-item">
            <img class="cart-item-img" src="${item.imageUrl}" alt="${item.name}">
            <div class="cart-item-info">
                <div class="cart-item-title">${item.name}</div>
                <div class="cart-item-price">₹${Number(item.price * item.quantity).toLocaleString('en-IN')}</div>
                <div class="cart-item-qty">
                    <button class="qty-btn" onclick="updateCartQuantity(${item.id}, -1)">−</button>
                    <span class="qty-val">${item.quantity}</span>
                    <button class="qty-btn" onclick="updateCartQuantity(${item.id}, 1)">+</button>
                </div>
            </div>
            <button class="cart-item-remove" onclick="removeFromCart(${item.id})" title="Remove item">✕</button>
        </div>
    `).join('');
}

// Drawer Toggles
function toggleCart() {
    const drawer = document.getElementById('cart-drawer');
    const backdrop = document.getElementById('cart-backdrop');
    if (!drawer) return;

    drawer.classList.toggle('open');
    if (backdrop) backdrop.classList.toggle('open');
}

// Checkout Modal
function openCheckoutModal() {
    if (state.cart.length === 0) {
        showToast('Your cart is empty', 'error');
        return;
    }

    toggleCart(); // close cart drawer

    const modal = document.getElementById('checkout-modal');
    const backdrop = document.getElementById('checkout-backdrop');

    // Pre-fill user info if logged in
    if (state.user) {
        const nameInput = document.getElementById('checkout-name');
        const emailInput = document.getElementById('checkout-email');
        if (nameInput) nameInput.value = state.user.fullName || state.user.username;
        if (emailInput) emailInput.value = state.user.email || '';
    }

    // Update order summary in checkout
    const subtotal = state.cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    const shippingFee = subtotal >= 999 ? 0 : 99;
    const total = subtotal + shippingFee;

    const summaryEl = document.getElementById('checkout-summary-amount');
    if (summaryEl) summaryEl.textContent = `₹${total.toLocaleString('en-IN')}`;

    if (modal) modal.classList.add('open');
    if (backdrop) backdrop.classList.add('open');
}

function closeCheckoutModal() {
    const modal = document.getElementById('checkout-modal');
    const backdrop = document.getElementById('checkout-backdrop');
    if (modal) modal.classList.remove('open');
    if (backdrop) backdrop.classList.remove('open');
}

// Place Order
async function handleCheckoutSubmit(e) {
    e.preventDefault();

    const name = document.getElementById('checkout-name').value.trim();
    const email = document.getElementById('checkout-email').value.trim();
    const phone = document.getElementById('checkout-phone').value.trim();
    const address = document.getElementById('checkout-address').value.trim();

    if (!name || !email || !phone || !address) {
        showToast('Please fill in all delivery details', 'error');
        return;
    }

    const subtotal = state.cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    const shippingFee = subtotal >= 999 ? 0 : 99;
    const total = subtotal + shippingFee;

    const orderPayload = {
        userId: state.user ? state.user.id : null,
        customerName: name,
        customerEmail: email,
        customerPhone: phone,
        shippingAddress: address,
        paymentMethod: state.selectedPaymentMethod,
        items: state.cart.map(i => ({ productId: i.id, quantity: i.quantity }))
    };

    let orderSuccessId = 'ORD-' + Math.floor(100000 + Math.random() * 900000);

    try {
        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderPayload)
        });

        if (response.ok) {
            const result = await response.json();
            if (result.data && result.data.id) {
                orderSuccessId = `ORD-#${result.data.id}`;
            }
        }
    } catch (err) {
        console.warn('Backend order API offline, logged locally:', err);
    }

    // Save order in client history as well
    const clientOrder = {
        id: orderSuccessId,
        date: new Date().toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' }),
        items: [...state.cart],
        total: total,
        paymentMethod: state.selectedPaymentMethod,
        shippingAddress: address,
        customerName: name,
        customerEmail: email,
        status: 'CONFIRMED'
    };

    const pastOrders = JSON.parse(localStorage.getItem('indirahub_orders') || '[]');
    pastOrders.unshift(clientOrder);
    localStorage.setItem('indirahub_orders', JSON.stringify(pastOrders));

    // Clear cart
    state.cart = [];
    saveCart();
    updateCartUI();

    // Show confirmation inside modal
    const modalBody = document.querySelector('#checkout-modal .modal-body');
    if (modalBody) {
        modalBody.innerHTML = `
            <div class="success-card">
                <div class="success-icon-badge">✓</div>
                <h3 style="font-size:1.6rem; font-family:var(--font-heading); margin-bottom:8px;">Order Confirmed!</h3>
                <p style="color:var(--text-secondary); font-size:0.95rem;">
                    Thank you, <strong>${name}</strong>! Your order has been placed and is being prepared for dispatch.
                </p>
                <div class="order-badge-id">${orderSuccessId}</div>
                <div style="background:rgba(255,255,255,0.03); border:1px solid var(--border-color); border-radius:var(--radius-md); padding:16px; margin-bottom:24px; text-align:left;">
                    <div style="font-size:0.85rem; color:var(--text-muted); margin-bottom:6px;">Delivery Details:</div>
                    <div style="font-weight:600; font-size:0.92rem; color:var(--text-primary);">${address}</div>
                    <div style="font-size:0.85rem; color:var(--text-secondary); margin-top:4px;">Payment: <strong>${state.selectedPaymentMethod}</strong> | Total: <strong style="color:var(--accent-emerald)">₹${total.toLocaleString('en-IN')}</strong></div>
                </div>
                <div style="display:flex; gap:12px; justify-content:center;">
                    <button class="btn-primary" onclick="closeCheckoutModal(); filterAndRenderProducts();">Continue Shopping</button>
                    <button class="btn-outline" onclick="closeCheckoutModal(); openOrdersModal();">View Orders</button>
                </div>
            </div>
        `;
    }
}

// My Orders Modal
async function openOrdersModal() {
    const modal = document.getElementById('orders-modal');
    const backdrop = document.getElementById('orders-backdrop');
    const ordersList = document.getElementById('orders-list');

    let orders = JSON.parse(localStorage.getItem('indirahub_orders') || '[]');

    // Try fetching from backend if user email is available
    if (state.user && state.user.email) {
        try {
            const res = await fetch(`/api/orders?email=${encodeURIComponent(state.user.email)}`);
            if (res.ok) {
                const apiOrders = await res.json();
                if (Array.isArray(apiOrders) && apiOrders.length > 0) {
                    orders = apiOrders.map(o => ({
                        id: `ORD-#${o.id}`,
                        date: new Date(o.orderDate).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' }),
                        items: o.items ? o.items.map(i => ({
                            name: i.product ? i.product.name : 'Product',
                            quantity: i.quantity,
                            price: i.price
                        })) : [],
                        total: o.totalAmount,
                        paymentMethod: o.paymentMethod,
                        shippingAddress: o.shippingAddress,
                        status: o.orderStatus || 'CONFIRMED'
                    }));
                }
            }
        } catch (e) {
            console.warn('Backend orders fetch fallback to localStorage:', e);
        }
    }

    if (ordersList) {
        if (orders.length === 0) {
            ordersList.innerHTML = `
                <div style="text-align:center; padding:50px 20px; color:var(--text-muted);">
                    <div style="font-size:2.8rem; margin-bottom:12px;">📦</div>
                    <h4 style="color:var(--text-primary); margin-bottom:6px;">No orders found</h4>
                    <p style="font-size:0.9rem;">You haven't placed any orders yet.</p>
                </div>
            `;
        } else {
            ordersList.innerHTML = orders.map(ord => `
                <div style="background:rgba(255,255,255,0.03); border:1px solid var(--border-color); border-radius:var(--radius-md); padding:18px; margin-bottom:14px;">
                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:12px; border-bottom:1px solid rgba(255,255,255,0.05); padding-bottom:8px;">
                        <div>
                            <span style="font-weight:700; color:var(--primary-light); font-family:monospace;">${ord.id}</span>
                            <span style="color:var(--text-muted); font-size:0.8rem; margin-left:10px;">${ord.date}</span>
                        </div>
                        <span style="background:rgba(16,185,129,0.15); color:var(--accent-emerald); border:1px solid rgba(16,185,129,0.3); padding:3px 10px; border-radius:var(--radius-full); font-size:0.75rem; font-weight:700;">
                            ${ord.status || 'CONFIRMED'}
                        </span>
                    </div>
                    <div style="font-size:0.88rem; color:var(--text-secondary); margin-bottom:10px;">
                        ${ord.items && ord.items.length > 0 ? ord.items.map(i => `${i.name} (x${i.quantity || 1})`).join(', ') : 'Order items'}
                    </div>
                    <div style="display:flex; justify-content:space-between; align-items:center; font-size:0.85rem; color:var(--text-muted);">
                        <span>Method: <strong>${ord.paymentMethod || 'COD'}</strong></span>
                        <span style="font-size:1.1rem; font-weight:800; color:var(--accent-emerald);">₹${Number(ord.total).toLocaleString('en-IN')}</span>
                    </div>
                </div>
            `).join('');
        }
    }

    if (modal) modal.classList.add('open');
    if (backdrop) backdrop.classList.add('open');
}

function closeOrdersModal() {
    const modal = document.getElementById('orders-modal');
    const backdrop = document.getElementById('orders-backdrop');
    if (modal) modal.classList.remove('open');
    if (backdrop) backdrop.classList.remove('open');
}

// Quick View Modal
function openQuickView(productId) {
    const product = state.products.find(p => p.id === productId);
    if (!product) return;

    const modal = document.getElementById('quickview-modal');
    const backdrop = document.getElementById('quickview-backdrop');
    const body = document.getElementById('quickview-body');

    if (body) {
        body.innerHTML = `
            <div style="display:grid; grid-template-columns: 1fr 1fr; gap:24px;">
                <img src="${product.imageUrl}" alt="${product.name}" style="width:100%; height:260px; object-fit:cover; border-radius:var(--radius-md); border:1px solid var(--border-color);">
                <div style="display:flex; flex-direction:column; justify-content:space-between;">
                    <div>
                        <span style="text-transform:uppercase; font-size:0.75rem; font-weight:700; color:var(--primary-light);">${product.category}</span>
                        <h3 style="font-family:var(--font-heading); font-size:1.35rem; margin:6px 0 10px;">${product.name}</h3>
                        <p style="color:var(--text-secondary); font-size:0.9rem; line-height:1.5; margin-bottom:14px;">${product.description}</p>
                        <div style="font-size:0.88rem; color:var(--accent-amber); font-weight:700; margin-bottom:12px;">
                            ★ ${product.rating || 4.8} / 5.0 (${product.reviewsCount || 100} verified reviews)
                        </div>
                    </div>
                    <div>
                        <div style="font-size:1.6rem; font-weight:800; color:var(--accent-emerald); font-family:var(--font-heading); margin-bottom:14px;">
                            ₹${Number(product.price).toLocaleString('en-IN')}
                        </div>
                        <button class="btn-primary" style="width:100%; justify-content:center;" onclick="addToCart(${product.id}); closeQuickView();">
                            Add to Cart
                        </button>
                    </div>
                </div>
            </div>
        `;
    }

    if (modal) modal.classList.add('open');
    if (backdrop) backdrop.classList.add('open');
}

function closeQuickView() {
    const modal = document.getElementById('quickview-modal');
    const backdrop = document.getElementById('quickview-backdrop');
    if (modal) modal.classList.remove('open');
    if (backdrop) backdrop.classList.remove('open');
}

// Toast System
function showToast(message, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    const icon = type === 'success' ? '✓' : type === 'error' ? '✕' : 'ℹ';

    toast.innerHTML = `
        <span style="font-weight:bold; font-size:1.1rem;">${icon}</span>
        <span>${message}</span>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateY(10px)';
        toast.style.transition = 'all 0.25s ease';
        setTimeout(() => toast.remove(), 250);
    }, 3200);
}

// QR Code Modal Functions
function openQrModal() {
    const modal = document.getElementById('qr-modal');
    const backdrop = document.getElementById('qr-modal-backdrop');
    if (modal) modal.classList.add('open');
    if (backdrop) backdrop.classList.add('open');
}

function closeQrModal() {
    const modal = document.getElementById('qr-modal');
    const backdrop = document.getElementById('qr-modal-backdrop');
    if (modal) modal.classList.remove('open');
    if (backdrop) backdrop.classList.remove('open');
}
