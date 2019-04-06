<template>
    <div class="container">
        <br/>
        <h4 align="left">Movie</h4>
        <form>
            <div class="form-group row" v-for="property in properties">
                <label class="col-sm-2 col-form-label" for="title">{{property.name}}</label>
                <div class="col-sm-10">
                    <div class="form-control" id="title">{{movie[property.name]}}</div>
                </div>
            </div>
        </form>
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
