<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { authorService } from '@/services/authorService';
import { bookService } from '@/services/bookService';
import type { AuthorResponseDTO } from '@/types/author.types';
import type { BookResponseDTO, BookFilterParamsDTO } from '@/types/book.types';
import ConfirmationModal from '@/components/ConfirmationModal.vue';
import Pagination from '@/components/Pagination.vue';

const route = useRoute();
const router = useRouter();

const author = ref<AuthorResponseDTO | null>(null);
const books = ref<BookResponseDTO[]>([]);
const isLoadingAuthor = ref(true);
const isLoadingBooks = ref(true);
const currentPage = ref(0);
const totalPages = ref(0);
const booksPerPage = 7;

const isModalOpen = ref(false);
const modalTitle = ref('');
const modalMessage = ref('');
const modalAction = ref<'deleteAuthor' | null>(null);

const fetchBooks = async (page: number = 0) => {
  if (!author.value) return;

  isLoadingBooks.value = true;
  try {
    const filters: BookFilterParamsDTO = {
      authorFullName: author.value.fullName
    };

    const response = await bookService.getBooks(page, booksPerPage, filters);

    books.value = response.content;
    totalPages.value = response.totalPages;
    currentPage.value = response.number;
  } catch (error) {
    console.error("Failed to fetch books for author:", error);
  } finally {
    isLoadingBooks.value = false;
  }
};

onMounted(async () => {
  const authorId = Number(route.params.id);

  if (isNaN(authorId) || authorId <= 0) {
    console.error("Invalid author ID from URL:", route.params.id);
    await router.push({ name: 'authors' });
    return;
  }

  try {
    isLoadingAuthor.value = true;
    author.value = await authorService.getAuthorById(authorId);
    await fetchBooks(0);

  } catch (error) {
    console.error("Failed to fetch author:", error);
    await router.push({ name: 'authors' });
  } finally {
    isLoadingAuthor.value = false;
  }
});

const openDeleteModal = () => {
  modalTitle.value = "Confirm delete";
  modalMessage.value = "Are you sure?";
  modalAction.value = 'deleteAuthor';
  isModalOpen.value = true;
};

const handleDelete = async () => {
  const authorId = Number(route.params.id);
  if (isNaN(authorId)) return;

  try {
    await authorService.deleteAuthor(authorId);
    await router.push({ name: 'authors' });
  } catch (error) {
    console.error("Failed to delete author:", error);
    alert('Failed to delete author');
  }
};

const goToBookDetail = (bookId: number) => {
  router.push({ name: 'book-details', params: { id: bookId } });
};

const closeModal = () => {
  isModalOpen.value = false;
  modalAction.value = null;
};

const onModalConfirm = async () => {
  if (modalAction.value === 'deleteAuthor') {
    await handleDelete();
  }
  closeModal();
};
</script>

<template>
  <div class="detail-layout">

    <aside class="author-panel">
      <div v-if="isLoadingAuthor">Loading...</div>
      <div v-else-if="author" class="author-card">

        <div class="delete-icon-wrapper">
          <button @click="openDeleteModal" class="delete-button" aria-label="Delete author">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
          </button>
        </div>

        <h2>{{ author.fullName }}</h2>
        <p>Birthdate: {{ author.birthdate }}</p>

      </div>
    </aside>

    <main class="book-list-panel">
      <h3 class="panel-title">Books of this author</h3>

      <div v-if="isLoadingBooks" class="loading-prompt">
        <p>Loading books...</p>
      </div>

      <div v-else-if="books.length > 0">
        <ul class="book-list">
          <li v-for="book in books" :key="book.id" @click="() => goToBookDetail(book.id)">
            {{ book.title }}
          </li>
        </ul>
        <Pagination
            :currentPage="currentPage"
            :totalPages="totalPages"
            @change-page="fetchBooks"
        />
      </div>

      <div v-else class="loading-prompt">
        <p>This author dont have books</p>
      </div>
    </main>

    <ConfirmationModal
        :isOpen="isModalOpen"
        :title="modalTitle"
        :message="modalMessage"
        @confirm="onModalConfirm"
        @cancel="closeModal"
    />
  </div>

</template>

<style scoped>
.detail-layout {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  width: 100%;
}

.author-panel {
  flex: 1;
  min-width: 280px;
  max-width: 33%;
}
.author-card {
  position: relative;
  background-color: var(--color-surface);
  padding: 2rem;
  border-radius: 8px;
  border: 1px solid var(--color-border);
}

.author-card h2 {
  margin-top: 2rem;
  margin-bottom: 0.5rem;
}
.author-card p {
  color: var(--color-text-secondary);
}

.delete-icon-wrapper {
  position: absolute;
  top: 1rem;
  right: 1rem;
}
.delete-button {
  background: none; border: none; cursor: pointer; padding: 0.5rem;
  color: var(--color-text-secondary);
  transition: color 0.2s ease, transform 0.2s ease;
}
.delete-button:hover {
  color: #ff8a80;
  transform: scale(1.1);
}

.book-list-panel {
  flex: 3;
  min-width: 300px;
}
.panel-title {
  font-size: 1.5rem;
  color: var(--color-text-secondary);
  margin-bottom: 1.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid var(--color-border);
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
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 4px;
  padding: 1rem;
  margin-bottom: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s;
}
.book-list li:hover {
  background-color: var(--color-border);
}
</style>