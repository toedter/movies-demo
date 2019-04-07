<template>
    <div class="container">
        <br/>
        <h4 align="left">Movie</h4>
        <table class="table">
            <tr>
                <td style="width:1%;">
                    <img :src="movie.thumb" height="320">
                </td>
                <td>
                    <form>
                        <div class="form-group row" v-for="property in properties">
                            <label class="col-sm-3 col-form-label" for="title">{{property.prompt}}</label>
                            <div class="col-sm-9">
                                <div class="form-control" id="title">{{movie[property.name]}}</div>
                            </div>
                        </div>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import axios from "axios"

    @Component
    export default class Movie extends Vue {
        private movie: any = {};
        private links: any = {};
        private properties: any[] = [];

        created() {
            const movieId = this.$route.params.id;
            if (movieId) {
                this.getMovie("/api/movies/" + movieId);
            }
        }

        private getMovie(url: string) {
            axios.get(url).then((response: any) => {
                    const data = response.data;
                    this.movie = data;
                    this.links = data._links;
                    this.properties = data._templates.default.properties;
                }
            )
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
