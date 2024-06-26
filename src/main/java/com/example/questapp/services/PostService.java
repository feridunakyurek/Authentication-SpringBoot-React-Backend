package com.example.questapp.services;

import com.example.questapp.entities.Post;
import com.example.questapp.entities.User;
import com.example.questapp.repos.PostRepository;
import com.example.questapp.requests.PostCreateRequest;
import com.example.questapp.requests.PostUpdateRequest;
import com.example.questapp.responses.LikeResponse;
import com.example.questapp.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
           list = postRepository.findAllByUserId(userId.get());
        }
        list = postRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());
    }

    public Post getOnePost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
       User user = userService.getOneUser(newPostRequest.getUserId());
       if (user == null)
           return null;
       Post postToSave = new Post();
       postToSave.setId(newPostRequest.getId());
       postToSave.setTitle(newPostRequest.getTitle());
       postToSave.setText(newPostRequest.getText());
       postToSave.setUser(user);

       return postRepository.save(postToSave);
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
