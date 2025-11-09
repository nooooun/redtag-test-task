<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";
import {bookService} from "@/services/bookService.ts";
import type {CreateBookDTO} from "@/types/book.types.ts";
import {BookGenres} from "@/utils/constant.ts";
import {AxiosError} from "axios";

const router = useRouter()

const book = ref<CreateBookDTO>({
  title: '',
  genre: '',
  publishDate: '',
  description: '',
  authorFullName: ''
})

const errorMsg = ref('')

const handleSubmit = async () => {
  errorMsg.value = ''
  if (book.value.genre === '') {
    errorMsg.value = 'Choose genre'
    return
  }

  try {
    await bookService.addBook(book.value)
    await router.push({name: 'books'})
  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      if (error.response.status === 400) {
        errorMsg.value = 'Please enter all fields'
      } else {
        errorMsg.value = 'Unknown error occurred'
      }
    } else {
      errorMsg.value = "Internal server error"
    }
  }
}
</script>

<template>
  <div class="form-container">
    <form @submit.prevent="handleSubmit" class="auth-form">
      <h2>Add book</h2>

      <div class="form-group">
        <label for="title">Title:</label>
        <input id="title" type="text" v-model="book.title" required />
      </div>

      <div class="form-group">
        <label for="authorFullName">Authors full name:</label>
        <input id="authorFullName" type="text" v-model="book.authorFullName" required />
      </div>

      <div class="form-group">
        <label for="genre">Genre:</label>
        <select id="genre" v-model="book.genre" required> <option disabled value="">Choose genre</option>
          <option v-for="(displayName, key) in BookGenres" :key="key" :value="key">
            {{ displayName }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="publishDate">Publish date:</label>
        <input id="publishDate" type="date" v-model="book.publishDate" required>
      </div>

      <div class="form-group">
        <label for="description">Description:</label>
        <textarea id="description" v-model="book.description" rows="5"></textarea>
      </div>

      <div v-if="errorMsg" class="error-message">{{ errorMsg }}</div>
      <button type="submit" class="auth-button">Add</button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

.auth-form {
  background-color: var(--color-surface);
  padding: 2rem;
  border-radius: 8px;
  border: 1px solid var(--color-border);
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
}
.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem; color: var(--color-text-secondary); }
.form-group input, .form-group select, .form-group textarea {
  width: 100%; padding: 0.75rem; background-color: var(--color-background);
  border: 1px solid var(--color-border); border-radius: 4px;
  color: var(--color-text-primary); font-size: 1rem; font-family: inherit;
}
textarea {
  resize: vertical;
}

.auth-button {
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

.error-message {
  color: #ff8a80;
  background-color: rgba(255, 138, 128, 0.1);
  border: 1px solid #ff8a80;
  border-radius: 4px;
  padding: 0.75rem;
  margin-bottom: 1rem;
  text-align: center;
}
</style>