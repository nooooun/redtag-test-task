<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import {ref, onMounted} from "vue";
import { bookService } from '@/services/bookService';
import type { BookResponseDTO, UpdateBookDetailsDTO } from '@/types/book.types';
import { BookGenres } from '@/utils/constant';
import { AxiosError } from 'axios';
import ConfirmationModal from '@/components/ConfirmationModal.vue';

const route = useRoute();
const router = useRouter();
const book = ref<BookResponseDTO | null>(null);
const isLoading = ref(true);
const errorMsg = ref('');
const bookId = Number(route.params.id);

const isModalOpen = ref(false);
const modalTitle = ref('');
const modalMessage = ref('');
const modalAction = ref<'update' | 'delete' | null>(null);

onMounted(async () => {
  if (isNaN(bookId)) {
    await router.push({ name: 'books' });
    return;
  }
  try {
    book.value = await bookService.getBookById(bookId);
  } catch (error) {
    console.error("Failed to fetch book:", error);
    await router.push({ name: 'books' });
  } finally {
    isLoading.value = false;
  }
});

const openUpdateModal = () => {
  modalTitle.value = "Confirm update";
  modalMessage.value = "Are you sure??";
  modalAction.value = 'update';
  isModalOpen.value = true;
};

const handleUpdate = async () => {
  if (!book.value) return;
  errorMsg.value = '';
  try {
    const updateDto: UpdateBookDetailsDTO = {
      title: book.value.title,
      genre: book.value.genre,
      description: book.value.description,
      publishDate: book.value.publishDate
    };
    await bookService.updateBookById(bookId, updateDto);
    await router.push({ name: 'books' });
  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      errorMsg.value = error.response.data.message;
    } else { errorMsg.value = 'Unknown error occurred'; }
  }
};

const openDeleteModal = () => {
  modalTitle.value = "Confirm delete";
  modalMessage.value = "Are you sure?";
  modalAction.value = 'delete';
  isModalOpen.value = true;
};

const handleDelete = async () => {
  try {
    await bookService.deleteBookById(bookId);
    await router.push({ name: 'books' });
  } catch (error) {
    console.error("Failed to delete book:", error);
    errorMsg.value = 'Failed to delete';
  }
};

const handleAddToFavorite = async () => {
  if (!book.value) return;
  try {
    await bookService.addBookToFavorite(bookId);
    alert('Book added to favorite');
  } catch (error) {
    console.error("Failed to add to favorite:", error);
    alert('Book is already in favorite');
  }
};

const handleRemoveFromFavorite = async () => {
  if (!book.value) return;
  try {
    await bookService.deleteFromFavorite(bookId);
    alert('Book deleted from favorite');
  } catch (error) {
    console.error("Failed to remove from favorite:", error);
    alert('Failed to remove from favorite');
  }
};

const closeModal = () => {
  isModalOpen.value = false;
  modalAction.value = null;
};

const onModalConfirm = async () => {
  if (modalAction.value === 'update') {
    await handleUpdate();
  } else if (modalAction.value === 'delete') {
    await handleDelete();
  }
  closeModal();
};
</script>

<template>
  <div class="form-container">
    <div v-if="isLoading" class="loading-prompt">Loading</div>

    <form v-else-if="book" @submit.prevent="openUpdateModal" class="auth-form">

      <div class="header-actions">
        <h2>Update book details</h2>
        <div class="header-buttons">

          <button
              @click.prevent="handleAddToFavorite"
              class="icon-button add-favorite-button"
              aria-label="Add to favorite"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </button>

          <button
              @click.prevent="handleRemoveFromFavorite"
              class="icon-button remove-favorite-button"
              aria-label="Delete from favorite"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </button>

          <button @click.prevent="openDeleteModal" class="icon-button delete-button" aria-label="Delete book">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
          </button>
        </div>
      </div>

      <div class="form-group">
        <label for="title">Title:</label>
        <input id="title" type="text" v-model="book.title" required />
      </div>
      <div class="form-group">
        <label for="publishDate">Publish date::</label>
        <input id="publishDate" type="date" v-model="book.publishDate"/>
      </div>
      <div class="form-group">
        <label for="genre">Genre:</label>
        <select id="genre" v-model="book.genre" required>
          <option v-for="(displayName, key) in BookGenres" :key="key" :value="key">
            {{ displayName }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="description">Description:</label>
        <textarea id="description" v-model="book.description" rows="5"></textarea>
      </div>

      <div v-if="errorMsg" class="error-message">{{ errorMsg }}</div>

      <button type="submit" class="auth-button">
        Update book details
      </button>
    </form>

    <ConfirmationModal :isOpen="isModalOpen" :title="modalTitle" :message="modalMessage" @confirm="onModalConfirm" @cancel="closeModal"/>
  </div>
</template>

<style scoped>
.form-container { width: 100%; max-width: 500px; margin: 0 auto; }
.auth-form { background-color: var(--color-surface); padding: 2rem; border-radius: 8px; border: 1px solid var(--color-border); }
.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.5rem; color: var(--color-text-secondary); }
.form-group input, .form-group select, .form-group textarea {
  width: 100%; padding: 0.75rem; background-color: var(--color-background);
  border: 1px solid var(--color-border); border-radius: 4px;
  color: var(--color-text-primary); font-size: 1rem; font-family: inherit;
}
.form-group input:disabled { background-color: #222; color: var(--color-text-secondary); cursor: not-allowed; }
textarea { resize: vertical; }
.auth-button {
  width: 100%; padding: 0.75rem; border: none; border-radius: 4px;
  background-color: var(--color-accent); color: var(--color-background);
  font-size: 1rem; font-weight: bold; cursor: pointer;
  display: inline-flex;
  justify-content: center;
  align-items: center;
}
.error-message {
  color: #ff8a80;
  background-color: rgba(255, 138, 128, 0.1);
  border: 1px solid #ff8a80;
  border-radius: 4px;
  padding: 0.75rem;
  margin-bottom: 1rem;
  text-align: center;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}
.header-actions h2 { margin: 0; }
.header-buttons { display: flex; gap: 0.5rem; }

.icon-button {
  background: none; border: 1px solid var(--color-border);
  cursor: pointer; padding: 0.5rem;
  color: var(--color-text-secondary);
  border-radius: 50%; width: 36px; height: 36px;
  display: flex; justify-content: center; align-items: center;
  transition: all 0.2s ease;
}
.icon-button:hover {
  color: var(--color-text-primary);
  border-color: var(--color-text-primary);
}
.icon-button.delete-button:hover {
  color: #ff8a80;
  border-color: #ff8a80;
}
</style>