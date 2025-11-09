<template>
  <div id="app-wrapper" :class="showSideBar ? 'main-layout' : 'guest-layout'">
    <Sidebar v-if="showSideBar"/>

    <div v-if="showSideBar" class="logout-icon-wrapper">
      <button @click="handleLogout" class="logout-button-icon" aria-label="Вийти">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M15 3h6a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-6"></path>
          <polyline points="10 17 15 12 10 7"></polyline>
          <line x1="15" y1="12" x2="3" y2="12"></line>
        </svg>
      </button>
    </div>

    <RouterView/>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import {useRoute, useRouter} from "vue-router";
import  {RouterView} from "vue-router";
import Sidebar from '@/components/Sidebar.vue'
import {authService} from "@/services/authService.ts";

const route = useRoute()
const router = useRouter()

const guestRoutes = ['register', 'login']

const showSideBar = computed(() => {
  if (!route.name) {
    return false
  }

  return !guestRoutes.includes(route.name.toString())
})

const handleLogout = async () => {
  try {
    await authService.logout();
  } catch (error) {
    console.log("Error occurred while logout: ", error);
  } finally {
    await router.push('/login');
  }
};
</script>

<style scoped>
#app-wrapper {
  min-height: 100vh;
  background-color: var(--color-background);
}

.guest-layout {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 2rem;
}

.main-layout {
  padding-top: 60px;
  padding-left: 60px;
  width: 100%;
}

.logout-icon-wrapper {
  position: absolute;
  top: 1rem;
  right: 1rem;
  z-index: 1001;
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

.logout-button-icon svg {
  width: 24px;
  height: 24px;
}

</style>
