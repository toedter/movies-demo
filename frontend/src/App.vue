<template>
  <div id="app">
    <nav class="navbar navbar-fixed-top navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="#">Hypermedia Movies Demo</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
          <div class="navbar-nav">
            <router-link class="nav-item nav-link" to="/movies">Movies</router-link>
            <router-link class="nav-item nav-link" to="/directors">Directors</router-link>
          </div>
        </div>
      </div>
    </nav>

    <router-view class="view"></router-view>
<!--    <Movies />-->
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Movies from './components/Movies.vue';
import Directors from './components/Directors.vue';
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Movie from '@/components/Movie.vue';

Vue.use(BootstrapVue)
Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    { path: '/', component: Movies },
    { path: '/movies', component: Movies },
    { path: '/directors', component: Directors },
    { path: '/movies/:id', component: Movie, props: true },
    { path: '/xxx', component: Movie }
  ]
})

@Component({
  router,
  components: {
    Movies
  },
})
export default class App extends Vue {}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
