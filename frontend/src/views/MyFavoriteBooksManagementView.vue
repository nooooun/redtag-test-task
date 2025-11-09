<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { bookService } from '@/services/bookService';
import type { BookResponseDTO, BookFilterParamsDTO } from '@/types/book.types';
import Pagination from '@/components/Pagination.vue';
import BookFilterPanel from '@/components/BookFilterPanel.vue';

const router = useRouter();
const route = useRoute();

const books = ref<BookResponseDTO[]>([]);
const currentPage = ref(0);
const totalPages = ref(0);
const isLoading = ref(true);
const booksPerPage = 7

const isFilterOpen = ref(false);
const searchQuery = ref('');
const currentFilters = ref<BookFilterParamsDTO>({});

const fetchFavoriteBooks = async (page: number = 0) => {
  isLoading.value = true;

  const allFilters: BookFilterParamsDTO = {
    ...currentFilters.value
  };

  if (searchQuery.value && searchQuery.value.trim() !== '') {
    allFilters.title = searchQuery.value.trim();
  } else {
    delete allFilters.title;
  }

  await router.push({
    query: { page: page + 1, ...allFilters }
  });

  try {
    const response = await bookService.getFavoriteBooks(page, booksPerPage, allFilters);
    books.value = response.content;
    totalPages.value = response.totalPages;
    currentPage.value = response.number;
  } catch (error) {
    console.error("Failed to fetch favorite books:", error);
  } finally {
    isLoading.value = false;
  }
};

const handleSearch = () => {
  fetchFavoriteBooks(0);
};

const applyFilters = (newFilters: BookFilterParamsDTO) => {
  currentFilters.value = newFilters;
  isFilterOpen.value = false;
  fetchFavoriteBooks(0);
};

const goToBookDetail = (bookId: number) => router.push({ name: 'book-details', params: { id: bookId } });

onMounted(() => {
  const { page, title, ...filters } = route.query;

  const urlPage = parseInt(page as string, 10);
  currentPage.value = urlPage > 0 ? urlPage - 1 : 0;
  searchQuery.value = (title as string) || '';
  currentFilters.value = filters as BookFilterParamsDTO;

  fetchFavoriteBooks(currentPage.value);
});
</script>

<template>
  <div class="management-container">

    <div class="search-bar">
      <button @click="isFilterOpen = true" class="filter-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="4" y1="21" x2="4" y2="14"></line><line x1="4" y1="10" x2="4" y2="3"></line><line x1="12" y1="21" x2="12" y2="12"></line><line x1="12" y1="8" x2="12" y2="3"></line><line x1="20" y1="21" x2="20" y2="16"></line><line x1="20" y1="12" x2="20" y2="3"></line><line x1="1" y1="14" x2="7" y2="14"></line><line x1="9" y1="8" x2="15" y2="8"></line><line x1="17" y1="16" x2="23" y2="16"></line></svg>
      </button>

      <input
          type="search"
          placeholder="Loading favorite books..."
          v-model="searchQuery"
          @keyup.enter="handleSearch"
      />
      <button @click="handleSearch" class="search-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
      </button>
    </div>

    <div class="content-area">
      <div v-if="isLoading" class="loading-prompt"><p>Loading books...</p></div>
      <ul v-else-if="books.length > 0" class="book-list">
        <li v-for="book in books" :key="book.id" @click="() => goToBookDetail(book.id)">
          {{ book.title }}
        </li>
      </ul>
      <div v-else class="loading-prompt"><p>There is no favorite books</p></div>
    </div>

    <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @change-page="fetchFavoriteBooks"
    />

    <BookFilterPanel
        :isOpen="isFilterOpen"
        :currentFilters="currentFilters"
        @close="isFilterOpen = false"
        @apply="applyFilters"
    />
  </div>
</template>

<style scoped>
.management-container {
  position: relative;
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem;
  padding-bottom: 80px;
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 8px;
}

.search-bar { display: flex; margin-bottom: 2rem; }
.filter-button {
  padding: 0 1rem; border: 1px solid var(--color-border); border-right: none;
  border-radius: 4px 0 0 4px; background-color: var(--color-surface);
  color: var(--color-text-primary); cursor: pointer;
}
.search-bar input {
  flex-grow: 1; padding: 0.75rem; background-color: var(--color-surface);
  border: 1px solid var(--color-border); border-left: none; border-right: none;
  border-radius: 0; color: var(--color-text-primary);
}
.search-button {
  padding: 0 1rem; border: 1px solid var(--color-border); border-left: none;
  border-radius: 0 4px 4px 0; background-color: var(--color-surface);
  color: var(--color-text-primary);
}
.filter-button:hover,
.search-button:hover {
  background-color: var(--color-border);
}

.loading-prompt {
  text-align: center;
  color: var(--color-text-secondary);
  padding: 4rem 0;
}

.book-list {
  list-style: none;
  padding: 0;
}

.book-list li {
  border: 1px solid var(--color-border);
  border-radius: 4px; padding: 1rem; margin-bottom: 0.5rem;
  cursor: pointer; transition: background-color 0.2s;
}
.book-list li:hover { background-color: var(--color-border); }
</style>