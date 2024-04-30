package com.example.questapp.services;

import com.example.questapp.entities.Post;
import com.example.questapp.entities.User;
import com.example.questapp.repos.PostRepository;
import com.example.questapp.requests.PostCreateRequest;
import com.example.questapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findAllByUserId(userId.get());
        }else{
            return postRepository.findAll();
        }
    }

    public Post getOnePost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
       User user = userService.getOneUser(newPostRequest.getUserId());
       if (user == null)
           return null;
       Post toSave = new Post();
       toSave.setId(newPostRequest.getId());
       toSave.setTitle(newPostRequest.getTitle());
       toSave.setText(newPostRequest.getText());
       toSave.setUser(user);

       return postRepository.save(toSave);
    }

    public Post updateOnePost(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post =  postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setTitle(updatePost.getTitle());
            toUpdate.setText(updatePost.getText());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }
}
