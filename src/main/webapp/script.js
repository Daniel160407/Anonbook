let selectedImage;

function showPostEditMenu() {
    document.getElementById("postEditMenu").style.display = 'block';
}

function handleFileChange(event) {
    selectedImage = event.target.files[0];
}

async function post() {
    const title = document.getElementById('postTitle').value;
    if (selectedImage !== undefined && title !== undefined) {
        const formData = new FormData();
        formData.append('image', selectedImage);

        const jsonArray = await postRequest(title, formData);

        imageDecoder(jsonArray);
    } else if (selectedImage === undefined && title !== undefined) {
        const jsonArray = await postWithoutImageRequest(title);

        imageDecoder(jsonArray);
    }
    document.getElementById('postEditMenu').style.display = 'none';
}

function imageDecoder(jsonArray) {
    document.getElementById('tape').innerHTML = "";
    for (let i = 0; i < jsonArray.data.length; i++) {
        const postDiv = document.createElement('div');
        const p = document.createElement('p');
        const title = document.createElement('p');
        postDiv.className = "postDiv";
        p.className = "time";
        p.innerText = `[N${jsonArray.data[i].id}] ${jsonArray.data[i].time}`;
        title.className = "title";
        title.innerText = jsonArray.data[i].title;
        console.log(jsonArray);
        postDiv.appendChild(p);
        postDiv.appendChild(title);
        if (jsonArray.imageBase64[i] !== undefined) {
            const img = document.createElement('img');
            img.src = 'data:image/jpeg;base64,' + jsonArray.imageBase64[i];
            img.className = "postImg";
            postDiv.appendChild(img);
        }
        document.getElementById('tape').appendChild(postDiv);
    }
}

async function getPosts() {
    const jsonArray = await getPostsRequest();
    imageDecoder(jsonArray);
}