<template>
    <div class="container" style="width: 70%;">
        <table class="table table-striped table-sm">
            <thead>
            <tr>
                <th style="text-align: left; width:5%;">Img</th>
                <th style="text-align: right; width:5%;">No</th>
                <th style="text-align: left">Title</th>
                <th style="text-align: left; width:5%;">Year</th>
                <th style="text-align: right; width:5%;">Rating</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="movie in movies">
                <td style="text-align: left"><a :href="movie.thumb">
                    <img :src="movie.thumb" height="40" width="30"/></a>
                </td>
                <td style="text-align: right">{{movie.rank}}</td>
                <td style="text-align: left">{{movie.title}}</td>
                <td style="text-align: left">{{movie.year}}</td>
                <td style="text-align: right">{{movie.rating}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import axios from "axios"

    @Component
    export default class Movies extends Vue {
        @Prop() private movies!: any[];

        created() {
            console.log("created!");
            axios.get("/movies").then((response: any) => {
                    console.log(response);
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
