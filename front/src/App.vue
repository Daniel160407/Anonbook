<template>
  <div>
    <header>
      <h1>Anonbook</h1>
    </header>
    <div>
      <button id="addPost" @click="showPostWindow = true">Add Post</button>
      <div v-if="showPostWindow">
        <AddPost/>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import AddPost from "./components/AddPost.vue";

const showPostWindow = ref(false);
</script>