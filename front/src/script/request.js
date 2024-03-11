export default class request{
async addNewPostRequest(object) {
    const jsonValue = JSON.stringify(object);
    const response = await fetch(`/anonbook/post?post=${jsonValue}`);
    return await response.json();
}
}
