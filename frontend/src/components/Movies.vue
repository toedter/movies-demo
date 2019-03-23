<template>
    <div class="container">
        <br/>
        <h4 align="left">Movies</h4>
        <table class="table table-striped table-sm">
            <thead>
            <tr>
                <th style="text-align: left; width:1%;"></th>
                <th style="text-align: right; width:1%">No</th>
                <th style="text-align: left">Title</th>
                <th style="text-align: left">Director</th>
                <th style="text-align: left; width:5%;">Year</th>
                <th style="text-align: left; width:10%;">IMDB Rating</th>
                <th style="text-align: right; width:1%;"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="movie in movies">
                <td style="text-align: left"><a :href="movie.thumb">
                    <img :src="movie.thumb" height="40" width="30"/></a>
                </td>
                <td style="text-align: right;vertical-align:middle;">{{movie.rank}}</td>
                <td style="text-align: left;vertical-align:middle;">{{movie.title}}</td>
                <td style="text-align: left;vertical-align:middle;">{{movie.director}}</td>
                <td style="text-align: left;vertical-align:middle;">{{movie.year}}</td>
                <td style="text-align: left;vertical-align:middle;"><Rate :value="movie.rating - 1.4" :length=10 :animate=0 :readonly=true /></td>
                <td style="text-align: left;vertical-align:middle;">{{movie.rating}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import axios from "axios"
    import Rate from "vue-tiny-rate/Rate.vue";

    @Component({
        components: {
            Rate,
        },
    })
    export default class Movies extends Vue {
        @Prop() private movies!: any[];

        created() {
            axios.get("/movies").then((response: any) => {
                    this.movies = response.data._embedded.movieList;
                }
            )
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    a {
        color: #42b983;
    }
</style>
