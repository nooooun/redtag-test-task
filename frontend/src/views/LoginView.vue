<script setup lang="ts">
import {ref} from "vue"
import {useRouter} from 'vue-router'
import {authService} from "@/services/authService.ts";
import type {LoginDTO} from "@/types/auth.types.ts";
import {AxiosError} from "axios";

const router = useRouter()
const email = ref('')
const password = ref('')

const errorMsg = ref<string>('')

const handleLogin = async () => {
  errorMsg.value = ''

  const dto: LoginDTO = {
    email: email.value,
    password: password.value
  }

  try {
    await authService.login(dto)
    await router.push('/books')
  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      if (error.response.status === 404) {
        errorMsg.value = 'Wrong email'
      } else if (error.response.status === 403) {
        errorMsg.value = 'Wrong password'
      } else {
        errorMsg.value = 'Unknown error occurred'
      }
    } else {
      errorMsg.value = 'Internal server error'
    }
    console.log(error)
  }
}
</script>

<template>
  <div class="auth-container">
    <form @submit.prevent="handleLogin" class="auth-form">
      <h2>Login</h2>

      <div class="form-group">
        <label for="email">Email:</label>
        <input id="email" type="email" v-model="email" required />
      </div>

      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="password" required />
      </div>

      <div v-if="errorMsg" class="error-message">
        {{errorMsg}}
      </div>
      <button type="submit" class="auth-button">Login</button>

      <p class="auth-link">
        Do not have an account? <RouterLink to="/register">Register</RouterLink>
      </p>
    </form>
  </div>
</template>

<style scoped>
.auth-container {
  width: 100%;
  max-width: 400px;
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
.form-group input {
  width: 100%;
  padding: 0.75rem;
  background-color: var(--color-background);
  border: 1px solid var(--color-border);
  border-radius: 4px;
  color: var(--color-text-primary);
  font-size: 1rem;
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
  transition: background-color 0.2s ease;
}
.auth-button:hover {
  background-color: var(--color-accent-hover);
}
.auth-link {
  margin-top: 1.5rem;
  text-align: center;
  color: var(--color-text-secondary);
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