<script setup lang="ts">
const props = defineProps<{
  currentPage: number;
  totalPages: number;
}>();

const emit = defineEmits<{
  (e: 'change-page', page: number): void
}>();

const changePage = (page: number) => {
  if (page >= 0 && page < props.totalPages) {
    emit('change-page', page);
  }
};
</script>

<template>
  <div class="pagination-container" v-if="totalPages > 1">
    <button @click="changePage(currentPage - 1)" :disabled="currentPage === 0" class="page-button">
      &lt;
    </button>

    <span class="page-info">
      Page {{currentPage + 1}} from {{totalPages}}
    </span>

    <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages - 1" class="page-button">
      &gt;
    </button>
  </div>
</template>

<style scoped>
.pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1.5rem;
  color: var(--color-text-secondary);
}
.page-button {
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  color: var(--color-text-primary);
  padding: 0.5rem 1rem;
  margin: 0 0.5rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}
.page-button:hover:not(:disabled) {
  background-color: var(--color-border);
}
.page-button:disabled {
  color: var(--color-text-secondary);
  cursor: not-allowed;
  opacity: 0.5;
}
.page-info {
  min-width: 120px;
  text-align: center;
}
</style>