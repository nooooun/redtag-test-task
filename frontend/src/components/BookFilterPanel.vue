<script setup lang="ts">
import {ref, watch} from "vue";
import {BookGenres} from "@/utils/constant.ts";
import type {BookFilterParamsDTO} from "@/types/book.types.ts";

const props = defineProps<{
  isOpen: Boolean,
  currentFilters: BookFilterParamsDTO
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'apply', filters: BookFilterParamsDTO): void
}>()

const localFilters = ref<BookFilterParamsDTO>({})

watch(() => props.isOpen, (newVal) => {
  if (newVal) {
    localFilters.value = {
      ...props.currentFilters,
      bookGenres: props.currentFilters.bookGenres ? [...props.currentFilters.bookGenres] : []
    }
  }
})

const closePanel = () => emit('close')
const apply = () => emit('apply', localFilters.value)

const reset = () => {
  localFilters.value = {bookGenres: []}
  emit('apply', localFilters.value)
}
</script>

<template>
  <Transition name="fade">
    <div v-if="isOpen" @click="closePanel" class="filter-overlay"></div>
  </Transition>

  <Transition name="slide">
    <aside v-if="isOpen" class="filter-panel">
      <div class="panel-header">
        <h3>Filters</h3>
        <button @click="closePanel" class="close-btn">&times;</button>
      </div>

      <div class="panel-content">
        <div class="form-group">
          <label for="title">Title:</label>
          <input id="title" type="text" v-model="localFilters.title"/>
        </div>

        <div class="form-group">
          <label for="authorFullName">Author full name:</label>
          <input id="authorFullName" type="text" v-model="localFilters.authorFullName"/>
        </div>

        <div class="form-group">
          <label for="publishedBefore">Published before:</label>
          <input id="publishedBefore" type="date" v-model="localFilters.publishedBefore"/>
        </div>

        <div class="form-group">
          <label for="publishedAfter">Published after:</label>
          <input id="publishedAfter" type="date" v-model="localFilters.publishedAfter"/>
        </div>

        <div class="form-group">
          <label>Genres:</label>
          <div class="genre-checkboxes">
            <div v-for="(displayName, key) in BookGenres" :key="key" class="checkbox-item">
              <input type="checkbox" :id="`genre-${key}`" :value="key" v-model="localFilters.bookGenres"/>
              <label :for="`genre-${key}`">{{displayName}}</label>
            </div>
          </div>
        </div>

        <button @click="apply" class="apply-button">Apply</button>
        <button @click="reset" class="reset-button">Reset</button>
      </div>
    </aside>
  </Transition>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease-out;
}

.slide-enter-from,
.slide-leave-to {
  transform: translateX(100%);
}

.filter-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1098;
}

.filter-panel {
  position: fixed;
  top: 0;
  right: 0;
  width: 90vw;
  max-width: 350px;
  height: 100vh;
  background-color: var(--color-surface);
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.2);
  z-index: 1099;
  display: flex;
  flex-direction: column;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid var(--color-border);
}

.panel-header h3 {
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  color: var(--color-text-primary);
  font-size: 2rem;
  cursor: pointer;
}

.panel-content {
  padding: 1rem;
  overflow-y: auto;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--color-text-secondary);
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 0.75rem;
  background-color: var(--color-background);
  border: 1px solid var(--color-border);
  border-radius: 4px;
  color: var(--color-text-primary);
  font-size: 1rem;
}

.genre-checkboxes {
  max-height: 200px;
  overflow-y: auto;
  background-color: var(--color-background);
  border: 1px solid var(--color-border);
  border-radius: 4px;
  padding: 0.5rem;
}

.checkbox-item {
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem;
}

.checkbox-item input {
  width: auto;
  margin-right: 0.5rem;
}

.checkbox-item label {
  margin-bottom: 0;
  color: var(--color-text-primary);
}

.apply-button {
  width: 100%;
  padding: 0.75rem;
  border: none;
  border-radius: 4px;
  background-color: var(--color-accent);
  color: var(--color-background);
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
}

.reset-button {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background-color: transparent;
  color: var(--color-text-secondary);
  font-size: 1rem;
  cursor: pointer;
  margin-top: 0.5rem;
}

.panel-content::-webkit-scrollbar {
  width: 8px;
}
.panel-content::-webkit-scrollbar-track {
  background: var(--color-background);
  border-radius: 4px;
}
.panel-content::-webkit-scrollbar-thumb {
  background-color: var(--color-border);
  border-radius: 4px;
}
.panel-content::-webkit-scrollbar-thumb:hover {
  background-color: var(--color-text-secondary);
}

.genre-checkboxes::-webkit-scrollbar {
  width: 8px;
}
.genre-checkboxes::-webkit-scrollbar-track {
  background: var(--color-background);
  border-radius: 4px;
}
.genre-checkboxes::-webkit-scrollbar-thumb {
  background-color: var(--color-border);
  border-radius: 4px;
}
.genre-checkboxes::-webkit-scrollbar-thumb:hover {
  background-color: var(--color-text-secondary);
}
</style>