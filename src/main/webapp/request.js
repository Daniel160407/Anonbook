async function postRequest(title, image) {
    const response = await fetch(`/anonBook/post?title=${title}`, {
        method: 'POST',
        body: image
    });
    return await response.json();
}

async function postWithoutImageRequest(title) {
    const response = await fetch(`/anonBook/post?title=${title}`, {method: 'POST'});
    return await response.json();
}

async function getPostsRequest() {
    const response = await fetch("/anonBook/post", {method: "GET"});
    return await response.json();
}

async function getPostComments(postId) {
    const response = await fetch(`/anonBook/comment?postId=${postId}`, {method: "GET"});
    return await response.json();
}