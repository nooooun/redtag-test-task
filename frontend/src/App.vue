<template>
  <div id="app-wrapper" :class="showSideBar ? 'main-layout' : 'guest-layout'">
    <Sidebar v-if="showSideBar"/>

    <RouterView/>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import {useRoute} from "vue-router";
import  {RouterView} from "vue-router";
import Sidebar from '@/components/Sidebar.vue'

const route = useRoute()

const guestRoutes = ['register', 'login']

const showSideBar = computed(() => {
  if (!route.name) {
    return false
  }

  return !guestRoutes.includes(route.name.toString())
})
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
</style>
