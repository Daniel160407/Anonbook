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

async function getPosts() {
    const jsonArray = await getPostsRequest();
    imageDecoder(jsonArray);
}

function imageDecoder(jsonArray) {
    document.getElementById('tape').innerHTML = "";
    for (let i = 0; i < jsonArray.data.length; i++) {
        const postDiv = document.createElement('div');
        const p = document.createElement('p');
        const title = document.createElement('p');
        postDiv.className = "postDiv";
        postDiv.id = jsonArray.data[i].id;
        p.className = "time";
        p.innerText = `[N${jsonArray.data[i].id}] ${jsonArray.data[i].time}`;
        title.className = "title";
        title.innerText = jsonArray.data[i].title;
        postDiv.appendChild(p);
        postDiv.appendChild(title);
        if (jsonArray.imageBase64[i] !== undefined) {
            const img = document.createElement('img');
            img.src = 'data:image/jpeg;base64,' + jsonArray.imageBase64[i];
            console.log(img.src);
            img.className = "postImg";
            postDiv.appendChild(img);
        }
        postDiv.onclick = async function () {
            await switchPage(postDiv);
        };
        document.getElementById('tape').appendChild(postDiv);
    }
}

async function switchPage(postDiv) {
    localStorage.setItem("postId", postDiv.id);
    window.location.href = "post.html";
}

async function getPostData() {
    //post data
    const postId = localStorage.getItem("postId");
    const jsonArray = await getPostComments(postId);

    const postDiv = document.createElement('div');
    const p = document.createElement('p');
    const title = document.createElement('p');
    postDiv.className = "postDiv";
    postDiv.id = jsonArray.data.id;
    p.className = "time";
    p.innerText = `[N${jsonArray.data.id}] ${jsonArray.data.time}`;
    title.className = "title";
    title.innerText = jsonArray.data.postText;
    postDiv.appendChild(p);
    postDiv.appendChild(title);

    if (jsonArray.imageBase64 !== undefined) {
        const img = document.createElement('img');
        img.src = 'data:image/jpeg;base64,' + jsonArray.imageBase64;
        img.className = 'postImg';
        postDiv.appendChild(img);
    }
    document.getElementById('root').appendChild(postDiv);

    //comments
    const yourComment = document.createElement('input');
    yourComment.id = "yourComment";
    yourComment.type = "text";
    yourComment.placeholder = "Write a comment";
    yourComment.onkeydown = function () {
        handleKeyDown(event);
    }

    const commentInput = document.createElement('div');
    commentInput.appendChild(yourComment);
    postDiv.appendChild(commentInput);

    displayComments(jsonArray);
}

async function handleKeyDown(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        await addCommentRequest(localStorage.getItem("postId"), document.getElementById('yourComment').value);
        const jsonArray = await getPostComments(localStorage.getItem("postId"));
        document.getElementById('yourComment').value = "";
        displayComments(jsonArray);
    }
}


const commentsDiv = document.createElement('div');
function displayComments(jsonArray) {
    commentsDiv.innerHTML = "";
    commentsDiv.id = "comments";
    for (let i = 0; i < jsonArray.comments.length; i++) {
        const comment = document.createElement('h4');
        comment.innerText = `[N${jsonArray.comments[i].id}] ${jsonArray.comments[i].comment}`;
        commentsDiv.appendChild(comment);
        document.getElementById(localStorage.getItem("postId")).appendChild(commentsDiv);
    }
}