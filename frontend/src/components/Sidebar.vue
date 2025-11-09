<script setup lang="ts">
import {ref} from 'vue'

const isOpen = ref(false)

const toggleSidebar = () => {
  isOpen.value = !isOpen.value
}

const closeSidebar = () => {
  isOpen.value = false
}
</script>

<template>
  <div class="sidebar-wrapper">
    <button v-if="!isOpen" @click="toggleSidebar" class="menu-toggle">
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <line x1="3" y1="12" x2="21" y2="12"></line>
        <line x1="3" y1="6" x2="21" y2="6"></line>
        <line x1="3" y1="18" x2="21" y2="18"></line>
      </svg>
    </button>

    <aside :class="['sidebar', {'is-open': isOpen}]">
      <div class="sidebar-header">
        <h3>Menu</h3>
        <button @click="toggleSidebar" class="close-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>

      <nav class="sidebar-nav">
        <ul>
          <li><RouterLink to="/books" @click="closeSidebar">Home</RouterLink></li>
          <li><RouterLink to="/favorite-books" @click="closeSidebar">Favorite books</RouterLink></li>
          <li><RouterLink to="/authors" @click="closeSidebar">Authors</RouterLink></li>
        </ul>
      </nav>
    </aside>

    <div v-if="isOpen" @click="closeSidebar" class="sidebar-overlay"></div>
  </div>

</template>

<style scoped>
.menu-toggle {
  position: absolute;
  top: 1rem;
  left: 1rem;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  color: var(--color-text-primary);
  z-index: 1001;
  transition: color 0.2s ease, transform 0.2s ease;
}

.menu-toggle:hover {
  color: var(--color-accent);
  transform: scale(1.1);
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 25vw;
  max-width: 300px;
  height: 100%;
  background-color: var(--color-surface);
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.2);
  transform: translateX(-100%);
  transition: transform 0.3s ease-out;
  z-index: 1000;
  display: flex;
  flex-direction: column;
}

.sidebar.is-open {
  transform: translateX(0);
}

.sidebar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid var(--color-border);
}

.sidebar-header h3 {
  margin: 0;
  color: var(--color-text-primary);
}

.close-button {
  background: none;
  border: none;
  cursor: pointer;
  color: var(--color-text-primary);
  padding: 0.5rem;
  transition: color 0.2s ease, transform 0.2s ease;
}

.close-button:hover {
  color: var(--color-accent);
  transform: scale(1.1);
}

.sidebar-nav {
  flex-grow: 1;
  padding: 1rem;
}

.sidebar-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar-nav li {
  margin-bottom: 0.75rem;
}

.sidebar-nav a {
  display: block;
  padding: 0.75rem 1rem;
  color: var(--color-text-primary);
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.sidebar-nav a:hover,
.sidebar-nav a.router-link-active {
  background-color: var(--color-accent);
  color: var(--color-background);
}

.sidebar-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 999;
}
</style>