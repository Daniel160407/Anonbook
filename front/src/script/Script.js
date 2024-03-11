import request from "./request";

export default class Script{
    constructor(title,image){
        this.title=title;
        this.image=image;
    }
    async addPost(){
        const postObject={
            "title": this.title,
            "image": this.image
        }
        console.log(this.title);
        console.log(this.image);
        const jsonValue = JSON.stringify(postObject);
        const response = await fetch(`/anonbook/post?post=${jsonValue}`);
        console.log(response.json());
    }
}