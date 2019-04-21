<template>
    <div class="container">
        <br/>
        <h4 align="left">Movie</h4>
        <b-form @submit="onUpdate">
            <img :src="movie.thumb" height="320" class="float-left">
            <div class="form-group row" v-for="property in properties">
                <label class="col-sm-3 col-form-label" for="title">{{property.prompt}}</label>
                <div class="col-sm-9">
                    <b-form-input class="form-control" id="title" v-model="movie[property.name]"></b-form-input>
                </div>
            </div>
            <button v-if="showUpdate" type="submit" class="btn btn-primary btn-space">Update</button>
            <button v-if="showDelete" type="submit" class="btn btn-danger" @click.prevent="onDelete">Delete</button>
        </b-form>
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
        private uri = "";
        private showUpdate = false;
        private showDelete = false;

        created() {
            const movieId = this.$route.params.id;
            this.uri = "/api/movies/" + movieId;
            if (movieId) {
                this.getMovie(this.uri);
            }
        }

        private getMovie(uri: string) {
            axios.get(uri).then((response: any) => {
                    const data = response.data;
                    this.movie = data;
                    this.links = data._links;
                    if (data._templates) {
                        this.properties = data._templates.default.properties;
                        if (data._templates.default) {
                            this.showUpdate = true;
                        }
                        if (data._templates.deleteMovie) {
                            this.showDelete = true;
                        }
                    }
                }
            )
        }

        private onUpdate() {
            if (!window.location.host.startsWith('localhost')) {
                alert('Movie update not allowed.');
            } else {
                axios.put(this.uri, this.movie).then((response: any) => {
                        console.log(response);
                    }
                )
            }
        }

        private onDelete() {
            if (!window.location.host.startsWith('localhost')) {
                alert('Movie deletion not allowed.');
            } else {
                axios.delete(this.uri).then((response: any) => {
                        console.log(response);
                    }
                )
            }
            this.$router.go(-1);
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .btn-space {
        margin-right: 5px;
    }
</style>
