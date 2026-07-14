<template>
  <div>
    <nav>
    </nav>
    <router-view />
  </div>
</template>

<script setup lang="ts">
import { useStore } from 'vuex'
const store = useStore()
</script>