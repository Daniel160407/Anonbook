let selectedImage;

function showPostEditMenu() {
    document.getElementById("postEditMenu").style.display = 'block';
}

function handleFileChange(event) {
    selectedImage = event.target.files[0];
}

async function post() {
    const title = document.getElementById('postTitle').textContent;
    if (selectedImage !== undefined && title !== undefined) {
        const formData = new FormData();
        formData.append('image', selectedImage);

        const jsonArray = await postRequest(title, formData);

        imageDecoder(jsonArray);

        document.getElementById('postEditMenu').style.display = 'none';
    }
}

function imageDecoder(jsonArray) {
    for (let i = 0; i < jsonArray.length; i++) {
        const img = document.createElement('img');
        img.src = 'data:image/jpeg;base64,' + jsonArray[i];
        img.className = "postImg";
        document.getElementById('tape').appendChild(img);
    }
}

async function getPosts() {
    const jsonArray = await getPostsRequest();
    imageDecoder(jsonArray);
}