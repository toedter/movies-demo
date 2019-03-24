<template>
    <div class="container">
        <br/>
        <h4 align="left">Movies</h4>
        <table class="table table-striped table-sm">
            <thead>
            <tr>
                <th style="text-align: left; width:1%;"></th>
                <th style="text-align: right; width:1%">No</th>
                <th style="text-align: left; width:45%;">Title</th>
                <th style="text-align: left; width:20%;">Director</th>
                <th style="text-align: left; width:5%;">Year</th>
                <th style="text-align: left; width:10%;">IMDB Rating</th>
                <th style="text-align: right; width:5%;"></th>
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
                <td style="text-align: left;vertical-align:middle;">
                    <Rate :value="movie.rating - 1.4" :length="10" :animate="0" :readonly="true" />
                </td>
                <td style="text-align: left;vertical-align:middle;">{{movie.rating}}</td>
            </tr>
            </tbody>
        </table>

        <nav aria-label="Page navigation example">
            <ul class="pagination">

                <li :class="{'page-item':true, 'disabled':!links.first}">
                    <a class="page-link" href="#" aria-label="First" v-on:click="getMovies(links.first.href)">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">First</span>
                    </a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.prev}">
                    <a class="page-link" href="#" aria-label="Previous" v-on:click="getMovies(links.prev.href)">
                        <span aria-hidden="true">&lsaquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>

                <li v-for="index in 10">
                    <a class="page-link" href="#" v-on:click="getMoviesByPage(index)">{{index}}</a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.next}">
                    <a class="page-link" href="#" aria-label="Next" v-on:click="getMovies(links.next.href)">
                        <span aria-hidden="true">&rsaquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.last}">
                    <a class="page-link" href="#" aria-label="Last" v-on:click="getMovies(links.last.href)">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Last</span>
                    </a>
                </li>
            </ul>
        </nav>

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
        @Prop() private links!: any;

        created() {
            this.getMovies("/movies")
        }

        private getMovies(url: string) {
            axios.get(url).then((response: any) => {
                    this.movies = response.data._embedded.movieList;
                    this.links = response.data._links;
                }
            )
        }

        private getMoviesByPage(page: number) {
            this.getMovies("/movies?page=" + page);
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
