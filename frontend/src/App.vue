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
            <router-link class="nav-item nav-link" to="/movie-page">Movies</router-link>
            <router-link class="nav-item nav-link" to="/director-page">Directors</router-link>
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

Vue.use(BootstrapVue)
Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    { path: '/', component: Movies },
    { path: '/movie-page', component: Movies },
    { path: '/director-page', component: Directors }
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
  text-align: center;
  color: #2c3e50;
}
</style>
