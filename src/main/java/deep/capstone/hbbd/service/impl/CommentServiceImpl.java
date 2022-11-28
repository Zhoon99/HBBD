package deep.capstone.hbbd.service.impl;

import deep.capstone.hbbd.dto.*;
import deep.capstone.hbbd.entity.*;
import deep.capstone.hbbd.repository.ClassesRepository;
import deep.capstone.hbbd.repository.CommentImgRepository;
import deep.capstone.hbbd.repository.CommentRepository;
import deep.capstone.hbbd.security.form.service.UserPrincipal;
import deep.capstone.hbbd.security.util.FileUtil;
import deep.capstone.hbbd.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final ClassesRepository classesRepository;
    private final CommentRepository commentRepository;
    private final CommentImgRepository commentImgRepository;

    @Value("${image.upload.path}")
    private String updatePath;

    @Transactional
    @Override
    public void commentRegister(CommentDto.Response commentDto, MultipartFile[] commentImgFiles, Account account) {
        if (commentImgFiles != null && commentImgFiles.length > 0) {
            ModelMapper modelMapper = new ModelMapper();
            Comment comment = modelMapper.map(commentDto, Comment.class);
            comment.setAccountAndComment(account, classesRepository.findById(commentDto.getClassesId()).get());

            Comment newComment = commentRepository.save(comment);

            List<CommentImg> commentImgDtoList = new ArrayList<>();
            for (MultipartFile file : commentImgFiles) {
                ImageDto imageDto = FileUtil.saveFileAndThumbnail(updatePath, file, 300, 300);
                CommentImg commentImg = CommentImg.builder()
                        .path(imageDto.getPath())
                        .uuid(imageDto.getUuid())
                        .imgName(imageDto.getImgName())
                        .comment(newComment)
                        .build();
                commentImgDtoList.add(commentImg);
            }
            commentImgRepository.saveAll(commentImgDtoList);
        }
    }
}

