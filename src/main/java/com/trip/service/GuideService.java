package com.trip.service;

import com.trip.common.annotation.MyLog;
import com.trip.common.enums.FileType;
import com.trip.common.enums.GuideStatus;
import com.trip.common.exception.Exception404;
import com.trip.common.security.CustomUser;
import com.trip.domain.AttachFile;
import com.trip.domain.Guide;
import com.trip.domain.LikeGuide;
import com.trip.dto.AttachFileDto;
import com.trip.dto.GuideDTO;
import com.trip.repository.GuideRepository;
import com.trip.repository.LikeGuideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final LikeGuideRepository likeGuideRepository;
    private final AttachFileService attachFileService;


    @MyLog
    @Transactional
    public GuideDTO.GuideResponse createGuide (CustomUser customUser, GuideDTO.GuideCreateRequest request){
        Guide guide = guideRepository.save(request.toEntity(customUser));
        AttachFileDto.AttachFileInfo attachFile = attachFileService.fileUpload(guide.getId().toString(), FileType.GUIDE,request.getFile());
        guide.setProfileImg(attachFile.getFileNo().toString());
        return guide.toDto();
    }

    @MyLog
    @Transactional(readOnly = true)
    public GuideDTO.GuideResponse findGuideInfoByUserNo(CustomUser customUser){
        Guide guide = guideRepository.findByUserNo(customUser.getUserNo()).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));
        guide.setViewCount(guide.getViewCount() + 1);
        return guideRepository.save(guide).toDto();
    }

    @MyLog
    @Transactional
    public GuideDTO.GuideResponse updateGuideStatus(Long id, GuideStatus status){
        Guide guide = guideRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));
        guide.updatePassStatus(status);
        return guideRepository.save(guide).toDto();
    }

    @MyLog
    @Transactional(readOnly = true)
    public List<GuideDTO.GuideResponse> findAllGuide(){
        return guideRepository.findAll().stream().map(g -> g.toDto()).collect(Collectors.toList());
    }

    @MyLog
    @Transactional(readOnly = true)
    public List<GuideDTO.GuideResponse> pageGuide(Pageable pageable){
        return guideRepository.findAll(pageable).stream()
                .map(g -> g.toDto()).collect(Collectors.toList());
    }

    @MyLog
    @Transactional
    public Long likeGuide(CustomUser customUser,Long guideNo){

        Guide guide = guideRepository.findById(guideNo).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));

        Optional<LikeGuide> likeGuide = likeGuideRepository.findById(LikeGuide.LikeGuideId.builder()
                .guideNo(guideNo).userNo(customUser.getUserNo()).build());

        if (likeGuide.isPresent()){
            return guide.getLikeCount();
        } else {
            likeGuideRepository.save(LikeGuide.builder()
                            .guideNo(guideNo)
                            .userNo(customUser.getUserNo())
                    .build());
            guide.setLikeCount(guide.getLikeCount() + 1);
            return guide.getLikeCount();
        }

    }

    @MyLog
    @Transactional
    public Long disLikeGuide(CustomUser customUser,Long guideNo){

        Guide guide = guideRepository.findById(guideNo).orElseThrow(()->new Exception404("존재하지 않는 사용자입니다."));

        Optional<LikeGuide> likeGuide = likeGuideRepository.findById(LikeGuide.LikeGuideId.builder()
                .guideNo(guideNo).userNo(customUser.getUserNo()).build());

        if (likeGuide.isPresent()){
            guide.setLikeCount(guide.getLikeCount() - 1);
            guideRepository.save(guide);
            likeGuideRepository.delete(LikeGuide.builder()
                    .guideNo(guideNo)
                    .userNo(customUser.getUserNo())
                    .build());
            return guide.getLikeCount();
        } else {
            return guide.getLikeCount();
        }


    }


}
