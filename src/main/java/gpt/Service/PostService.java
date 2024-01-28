package gpt.Service;

import gpt.data.PostData;

public class PostService {
    private PostData postData;
    private  Authenticator au;

    public PostService(PostData postData,Authenticator au) {
        this.postData = postData;
        this.au = au;
    }
}
