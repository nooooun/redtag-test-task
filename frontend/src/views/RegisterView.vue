<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";
import {authService} from "@/services/authService.ts";
import type {RegisterDTO} from "@/types/auth.types.ts";
import {AxiosError} from 'axios'

const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const role = ref('USER')

const errorMsg = ref<string>('')

const handleRegistration = async () => {
  errorMsg.value = ''

  const dto: RegisterDTO = {
    username: username.value,
    email: email.value,
    password: password.value,
    role: role.value
  }

  try {
    await authService.register(dto)
    await router.push('/home')
  } catch (error) {
    if (error instanceof AxiosError) {
      if (error.response?.status === 409) {
        errorMsg.value = 'User with this email already exists'
      } else {
        errorMsg.value = 'Unknown error occurred'
      }
    } else {
      errorMsg.value = 'Internal server error'
    }
    console.error(error)
  }
}
</script>

<template>
  <div class="auth-container">
    <form @submit.prevent="handleRegistration" class="auth-form">
      <h2>Registration</h2>

      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" type="text" v-model="username" required />
      </div>

      <div class="form-group">
        <label for="email">Email:</label>
        <input id="email" type="email" v-model="email" required />
      </div>

      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="password" required />
      </div>

      <div class="form-group">
        <label for="role">Account Type:</label>
        <select id="role" v-model="role" required>
          <option value="USER">User</option>
          <option value="AUTHOR">Admin</option>
        </select>
      </div>

      <div v-if="errorMsg" class="error-message">
        {{errorMsg}}
      </div>
      <button type="submit" class="auth-button">Register</button>

      <p class="auth-link">
        Have an account? <RouterLink to="/login">Login</RouterLink>
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