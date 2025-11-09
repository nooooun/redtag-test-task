<script setup lang="ts">
import {useRouter} from "vue-router";
import {authService} from "@/services/authService.ts";

const router = useRouter()

const handleLogout = async () => {
  try {
    await authService.logout()
  } catch (error) {
    console.log("Error occurred while logout: ", error)
  } finally {
    await router.push('/login')
  }
}
</script>

<template>
  <div class="home-container">
    <h1>Tut budut knigi</h1>
    <p>You proishov goi</p>
    <div class="logout-icon-wrapper">
      <button @click="handleLogout" class="logout-button-icon">Logout</button>
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M15 3h6a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-6"></path>
        <polyline points="10 17 15 12 10 7"></polyline>
        <line x1="15" y1="12" x2="3" y2="12"></line>
      </svg>
    </div>

  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  max-width: 800px;
  padding: 2rem;
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 8px;
  text-align: center;
}
h1 {
  margin-top: 2rem;
  margin-bottom: 1rem;
}

p {
  color: var(--color-text-secondary);
  margin-bottom: 2rem;
}

.logout-icon-wrapper {
  position: absolute;
  top: 1rem;
  right: 1rem;
}

.logout-button-icon {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  color: var(--color-text-primary);
  transition: color 0.2s ease, transform 0.2s ease;
}

.logout-button-icon:hover {
  color: var(--color-accent);
  transform: scale(1.1);
}
</style>