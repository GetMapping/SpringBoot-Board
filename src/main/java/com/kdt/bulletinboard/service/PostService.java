package com.kdt.bulletinboard.service;

import com.kdt.bulletinboard.converter.PostConverter;
import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.entity.Post;
import com.kdt.bulletinboard.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostConverter postConverter;

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertToPost(postDto);
        Post saved = postRepository.save(post);
        return saved.getId();
    }

    @Transactional
    public PostDto findOnePost(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertToPostDto)
                .orElseThrow(() -> new NotFoundException("해당 포스트를 찾을 수 없습니다."));
    }

    @Transactional
    public Page<PostDto> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertToPostDto);
    }
}