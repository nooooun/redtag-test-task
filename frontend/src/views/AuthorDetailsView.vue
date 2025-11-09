<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { authorService } from '@/services/authorService';
import type { AuthorResponseDTO } from '@/types/author.types';

const route = useRoute();
const router = useRouter();
const author = ref<AuthorResponseDTO | null>(null);

onMounted(async () => {
  const authorId = Number(route.params.id)
  if (isNaN(authorId) || authorId <= 0) {
    console.error("Invalid author ID from URL: " + route.params.id)
    await router.push({name: "authors"})
    return
  }

  try {
    author.value = await authorService.getAuthorById(authorId);
  } catch (error) {
    console.error("Failed to fetch author:", error);
    await router.push({ name: 'authors' });
  }
});

const handleDelete = async () => {
  if (confirm('Do you want to delete author?')) {
    const authorId = Number(route.params.id)
    if (isNaN(authorId) || authorId <= 0) {
      console.error("Invalid author ID from URL: " + route.params.id)
      await router.push({name: "authors"})
      return
    }

    try {
      await authorService.deleteAuthor(authorId);
      await router.push({ name: 'authors' });
    } catch (error) {
      console.error("Failed to delete author:", error);
      alert('Failed to delete author');
    }
  }
};
</script>

<template>
  <div class="author-detail-container">
    <div class="delete-icon-wrapper">
      <button @click="handleDelete" class="delete-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path><line x1="10" y1="11" x2="10" y2="17"></line><line x1="14" y1="11" x2="14" y2="17"></line></svg>
      </button>
    </div>

    <div v-if="author">
      <h1>{{ author.fullName }}</h1>
      <p>Birthdate: {{ author.birthdate }}</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<style scoped>
.author-detail-container {
  position: relative;
  width: 100%;
  max-width: 800px;
  padding: 2rem;
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 8px;
  margin: 0 auto;
}

.delete-icon-wrapper {
  position: absolute;
  top: 1rem;
  right: 1rem;
}

.delete-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  color: var(--color-text-secondary);
  transition: color 0.2s ease, transform 0.2s ease;
}

.delete-button:hover {
  color: #ff8a80;
  transform: scale(1.1);
}

h1 {
  margin-top: 2rem;
  margin-bottom: 1rem;
}
p {
  color: var(--color-text-secondary);
}
</style>