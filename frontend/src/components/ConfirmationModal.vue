<script setup lang="ts">
defineProps<{
  isOpen: boolean
  title: string
  message: string
}>()

const emit = defineEmits<{
  (e: 'confirm'): void
  (e: 'cancel'): void
}>()

const confirm = () => {
  emit('confirm')
}

const cancel = () => {
  emit('cancel')
}
</script>

<template>
  <Transition name="modal-fade">
    <div v-if="isOpen" class="modal-overlay" @click="cancel">
      <div class="modal-content" @click.stop>

        <div class="modal-header">
          <h3>{{ title }}</h3>
          <button @click="cancel" class="close-btn" aria-label="Close">&times;</button>
        </div>

        <div class="modal-body">
          <p>{{ message }}</p>
        </div>

        <div class="modal-footer">
          <button @click="cancel" class="btn btn-secondary">No</button>
          <button @click="confirm" class="btn btn-primary">Yes</button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}
.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 90%;
  max-width: 450px;
  background-color: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 8px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid var(--color-border);
}
.modal-header h3 {
  margin: 0;
  font-size: 1.25rem;
}
.close-btn {
  background: none; border: none; font-size: 2rem;
  color: var(--color-text-secondary); cursor: pointer;
}

.modal-body {
  padding: 1.5rem;
  color: var(--color-text-primary);
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
  padding: 1rem 1.5rem;
  border-top: 1px solid var(--color-border);
}

.btn {
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s;
}
.btn-secondary {
  background-color: var(--color-surface);
  color: var(--color-text-primary);
  border: 1px solid var(--color-border);
}
.btn-secondary:hover {
  background-color: var(--color-border);
}
.btn-primary {
  background-color: var(--color-accent);
  color: var(--color-background);
}
.btn-primary:hover {
  background-color: var(--color-accent-hover);
}
</style>