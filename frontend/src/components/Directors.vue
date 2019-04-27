<template>
    <div class="container">
        <br/>
        <h4 align="left">Directors</h4>
        <table class="table table-striped table-sm">
            <thead>
            <tr>
                <th style="text-align: left; width:20%;">Name</th>
                <th style="text-align: left; width:80%;">Movies</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="director in directors">
                <td style="text-align: left;vertical-align:middle;">{{director.name}}</td>
                <td v-if="director._links.movies instanceof Array" style="text-align: left;vertical-align:middle;">
                    <div v-for="movie in director._links.movies">
                        <router-link class="nav-item nav-link" :to="'/movies/' + getMovieId(movie.href)">
                            {{movie.title}}
                        </router-link>
                    </div>
                </td>
                <td v-if="!(director._links.movies instanceof Array)" style="text-align: left;vertical-align:middle;">
                    <router-link class="nav-item nav-link" :to="'/movies/' + getMovieId(director._links.movies.href)">
                        {{director._links.movies.title}}
                    </router-link>

                </td>
            </tr>
            </tbody>
        </table>

        <nav aria-label="Page navigation example">
            <ul class="pagination">

                <li :class="{'page-item':true, 'disabled':!links.first}">
                    <a class="page-link" href="#" aria-label="First" v-on:click="getDirectors(links.first.href)">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">First</span>
                    </a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.prev}">
                    <a class="page-link" href="#" aria-label="Previous" v-on:click="getDirectors(links.prev.href)">
                        <span aria-hidden="true">&lsaquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>

                <li v-for="index in getMaxPage() - getMinPage()" :class="{'page-item':true, 'active':page.number === index-1+getMinPage()}">
                    <a class="page-link" href="#" v-on:click="getDirectorsByPage(index-1+getMinPage())">{{index+getMinPage()}}</a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.next}">
                    <a class="page-link" href="#" aria-label="Next" v-on:click="getDirectors(links.next.href)">
                        <span aria-hidden="true">&rsaquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>

                <li :class="{'page-item':true, 'disabled':!links.last}">
                    <a class="page-link" href="#" aria-label="Last" v-on:click="getDirectors(links.last.href)">
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

    @Component
    export default class Directors extends Vue {
        private directors: any[] = [];
        private links: any = {};
        private page: any = {};

        private directorsHref = '';

        created() {
            this.getDirectors();
        }

        private getDirectors(url?: string) {
            if(this.directorsHref.length === 0) {
                axios.get('/api').then((response: any) => {
                        const directorsHref: string = response.data._links.directors.href;
                        this.directorsHref = directorsHref.substring(0, directorsHref.indexOf('{'));
                        this.getDirectors(url);
                    }
                )
            } else {
                if (!url) {
                    url = this.directorsHref;
                }
                axios.get(url).then((response: any) => {
                        this.directors = response.data._embedded.directors;
                        this.links = response.data._links;
                        this.page = response.data.page;
                    }
                )
            }
        }

        private getMovieId(href: string) {
            return href.substring(href.lastIndexOf("/") + 1);
        }

        private getDirectorsByPage(page: number) {
            this.getDirectors(this.directorsHref + '?page=' + page);
        }

        private getMinPage(): number {
            let minPage = 0;
            if( this.page.number > 5) {
                minPage = this.page.number - 5;
            }

            if( this.page.number > this.page.totalPages - 5) {
                minPage = this.page.totalPages - 10;
            }

            return minPage;
        }

        private getMaxPage(): number {
            return this.getMinPage() + 10;
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
