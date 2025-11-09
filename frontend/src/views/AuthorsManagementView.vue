<script setup lang="ts">
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import type {AuthorResponseDTO} from "@/types/author.types.ts";
import Pagination from "@/components/Pagination.vue";
import {authorService} from "@/services/authorService.ts";

const route = useRoute()
const router = useRouter()

const authors = ref<AuthorResponseDTO[]>([])
const currentPage = ref(0)
const totalPage = ref(0)
const searchQuery = ref('')
const isLoading = ref(true)
const authorsPerPage = 7

const fetchAuthors = async (page: number = 0) => {
  isLoading.value = true

  await router.push({
    query: {
      page: page + 1,
      search: searchQuery.value || undefined
    }
  })

  try {
    const response = await authorService.getAuthors(page, authorsPerPage, searchQuery.value)

    authors.value = response.content
    totalPage.value = response.totalPages
    currentPage.value = response.number
  } catch (error) {
    console.error("Failed to fetch authors: " + error)
    authors.value = []
    totalPage.value = 0
    currentPage.value = 0
  } finally {
    isLoading.value = false
  }
}

const handleSearch = () => {
  fetchAuthors(0)
}

const goToAddAuthor = () => {
  router.push({name: 'add-author'})
}

const goToAuthorDetail = (id: number) => {
  router.push({name: 'author-details', params: {id: id}})
}

onMounted(() => {
  const urlPage = parseInt(route.query.page as string, 10)
  const urlSearch = route.query.search as string

  currentPage.value = urlPage > 0 ? urlPage - 1 : 0
  searchQuery.value = urlSearch || ''

  fetchAuthors(currentPage.value)
})
</script>

<template>
  <div class="management-container">

    <div class="search-bar">
      <input
        type="search"
        placeholder="Search"
        v-model="searchQuery"
        @keyup.enter="handleSearch"/>
      <button @click="handleSearch" class="search-button" aria-label="Search authors...">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
      </button>
    </div>

    <div class="content-area">
      <div v-if="isLoading" class="loading-prompt">
        <p>Loading...</p>
      </div>

      <div v-else-if="authors.length > 0" class="author-list-container">
        <ul class="author-list">
          <li v-for="author in authors" :key="author.id" @click="() => goToAuthorDetail(author.id)">
            {{author.fullName}}
          </li>
        </ul>
      </div>

      <div v-else class="loading-prompt">
        <p>There is no authors</p>
      </div>
    </div>
    
    <Pagination
        :currentPage="currentPage"
        :totalPages="totalPage"
        @change-page="fetchAuthors"
    />

    <button @click="goToAddAuthor" class="fab">
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
    </button>
  </div>
</template>

<style scoped>
.management-container {
  position: relative;
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding-bottom: 80px;
}

.search-bar {
  display: flex;
  margin-bottom: 2rem;
}

.search-bar input {
  flex-grow: 1;
  padding: 0.75rem;
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-right: none;
  border-radius: 4px 0 0 4px;
  color: var(--color-text-primary);
  font-size: 1rem;
}

.search-button {
  padding: 0 1rem;
  border: 1px solid var(--color-border);
  border-radius: 0 4px 4px 0;
  background-color: var(--color-surface);
  color: var(--color-text-primary);
  cursor: pointer;
  transition: background-color 0.2s;
}
.search-button:hover {
  background-color: var(--color-border);
}

.loading-prompt {
  text-align: center;
  color: var(--color-text-secondary);
  padding: 4rem 0;
  font-size: 1.2rem;
}

.author-list {
  list-style: none;
  padding: 0;
}
.author-list li {
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 4px;
  padding: 1rem;
  margin-bottom: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s;
}
.author-list li:hover {
  background-color: var(--color-border);
}

.fab {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 60px;
  height: 60px;
  background-color: var(--color-accent);
  color: var(--color-background);
  border: none;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: transform 0.2s ease-out, background-color 0.2s ease;
}
.fab:hover {
  transform: scale(1.1);
  background-color: var(--color-accent-hover);
}
</style>