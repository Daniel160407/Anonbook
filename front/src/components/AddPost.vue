<script setup>
import Script from "../script/Script.js";

let script = new Script();

function handleSubmit() {
  const postText = document.getElementById('postText').value;
  const uploadPhoto = document.getElementById('uploadPhoto').value;
  script.addPost(postText, uploadPhoto);
}
</script>

<template>
  <div>
    <h2>Add New Post</h2>
    <div id="container">
      <textarea id="postText" placeholder="How was your day?"></textarea><br/>
      <input type="file" id="uploadPhoto" accept="image/jpg" @change="handleFileChange"/>
      <input type="submit" value="Submit" @click="handleSubmit"/>
    </div>
  </div>
</template>

<style scoped>
</style>
