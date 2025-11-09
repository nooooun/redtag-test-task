<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authorService } from '@/services/authorService';
import type { CreateAuthorDTO } from '@/types/author.types';
import { AxiosError } from 'axios';

const router = useRouter();
const fullName = ref('');
const birthdate = ref('');
const errorMsg = ref('');

const handleSubmit = async () => {
  errorMsg.value = '';
  try {
    const dto: CreateAuthorDTO = {
      fullName: fullName.value,
      birthdate: birthdate.value
    };
    await authorService.addAuthor(dto);

    await router.push({ name: 'authors' });

  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      if (error.response.status === 400) {
        errorMsg.value = 'Invalid data';
      } else {
        errorMsg.value = 'Unknown error occurred'
      }
    } else {
      errorMsg.value = 'Internal server error';
    }
  }
};
</script>

<template>
  <div class="auth-container">
    <form @submit.prevent="handleSubmit" class="auth-form">
      <h2>Add author</h2>

      <div class="form-group">
        <label for="fullName">FullName:</label>
        <input id="fullName" type="text" v-model="fullName" required />
      </div>

      <div class="form-group">
        <label for="birthdate">Birthdate:</label>
        <input id="birthdate" type="date" v-model="birthdate" required />
      </div>

      <div v-if="errorMsg" class="error-message">{{ errorMsg }}</div>

      <button type="submit" class="auth-button">Add</button>
    </form>
  </div>
</template>

<style scoped>
.auth-container {
  width: 100%;
  max-width: 400px;
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
  color: var(--color-text-primary);
}
.form-group {
  margin-bottom: 1rem;
}
.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--color-text-secondary);
}
.form-group input { width: 100%; padding: 0.75rem; background-color: var(--color-background); border: 1px solid var(--color-border); border-radius: 4px; color: var(--color-text-primary); font-size: 1rem; }
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
  transition: background-color 0.2s ease;
}
.auth-button:hover {
  background-color: var(--color-accent-hover);
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