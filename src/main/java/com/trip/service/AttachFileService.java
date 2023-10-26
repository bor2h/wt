package com.trip.service;

import com.trip.common.enums.FileType;
import com.trip.common.exception.Exception404;
import com.trip.common.exception.Exception500;
import com.trip.domain.AttachFile;
import com.trip.dto.AttachFileDto;
import com.trip.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AttachFileService {

    @Value("${file.upload.location}")
    private String uploadPath;

    private final AttachFileRepository attachFileRepository;

    //파일 업로드 (대량)
    public List<AttachFileDto.AttachFileInfo> upload(String refId,FileType fileType,MultipartFile[] files) {

        log.debug("refId : [{}] fileType: [{}]",refId,fileType);

        // 1. 업로드 할 세부경로 지정 : uploadPath/notice/boardId
        String path = uploadPath + File.separator + fileType + File.separator + refId;
        File folder = new File(path);

        // 2. 디렉터리가 존재하지 않을 경우 폴더 생성
        if (!folder.exists()) {
            folder.mkdirs();
        }

        List<AttachFileDto.AttachFileInfo> attachFileInfoList = new ArrayList<>();

        // 3. 다중파일 처리
        for (MultipartFile file : files) {

            // 3-1. uuid + 파일명
            String saveName = UUID.randomUUID().toString();
            String uploadFileName = file.getOriginalFilename();

            long fileSize = file.getSize();
            // 3-2. uploadpath에 파일 저장
            Path resultPath = Paths.get(String.valueOf(folder), saveName);
            String savePath = String.valueOf(resultPath);
            String contentType = file.getContentType();
            log.info("-------------------------------------");
            log.info("Upload File Name: " + uploadFileName);
            log.info("Upload File Size: " + fileSize);
            log.info("only file saveName: " + saveName);
            log.info("savePath: " + savePath);
            log.info("contentType: " + contentType);
            log.info("fileType: " + fileType);
            log.info("-------------------------------------");

            try {
                file.transferTo(resultPath);
                log.info("success to write > refId : [{}] resultPath : [{}]",refId,resultPath);
            } catch (IOException e) {
                log.error("fail to write > refId : [{}] resultPath : [{}]",refId,resultPath);
                log.error(e.getMessage());
                throw new Exception500(file.getOriginalFilename());
            }

            // 3-3. db에 entity 저장

            AttachFile attachFile =  attachFileRepository.save(AttachFile.builder()
                    .fileType(fileType)
                    .refId(refId)
                    .fileName(uploadFileName)
                    .uploadPath(savePath)
                    .fileSize(fileSize)
                    .contentType(contentType)
                    .build());
            attachFileInfoList.add(attachFile.toDto());
        }
        return attachFileInfoList;
    }


    //파일 업로드 (단건)
    public AttachFileDto.AttachFileInfo fileUpload(String refId, FileType fileType, MultipartFile file) {

        log.debug("refId : [{}] fileType: [{}]",refId,fileType);

        // 1. 업로드 할 세부경로 지정 : uploadPath/notice/boardId
        String path = uploadPath + File.separator + fileType + File.separator + refId;
        File folder = new File(path);

        // 2. 디렉터리가 존재하지 않을 경우 폴더 생성
        if (!folder.exists()) {
            folder.mkdirs();
        }


        // 3-1. uuid + 파일명
        String saveName = UUID.randomUUID().toString();
        String uploadFileName = file.getOriginalFilename();

        long fileSize = file.getSize();
        // 3-2. uploadpath에 파일 저장
        Path resultPath = Paths.get(String.valueOf(folder), saveName);
        String savePath = String.valueOf(resultPath);
        String contentType = file.getContentType();
        log.info("-------------------------------------");
        log.info("Upload File Name: " + uploadFileName);
        log.info("Upload File Size: " + fileSize);
        log.info("only file saveName: " + saveName);
        log.info("savePath: " + savePath);
        log.info("contentType: " + contentType);
        log.info("fileType: " + fileType);
        log.info("-------------------------------------");

        try {
            file.transferTo(resultPath);
            log.info("success to write > refId : [{}] resultPath : [{}]",refId,resultPath);
        } catch (IOException e) {
            log.error("fail to write > refId : [{}] resultPath : [{}]",refId,resultPath);
            log.error(e.getMessage());
            throw new Exception500(file.getOriginalFilename());
        }

        // 3-3. db에 entity 저장
        AttachFile attachFile = attachFileRepository.save(AttachFile.builder()
                .fileType(fileType)
                .refId(refId)
                .fileName(uploadFileName)
                .uploadPath(savePath)
                .fileSize(fileSize)
                .contentType(contentType)
                .build());
        return attachFile.toDto();
    }


    //파일 다운로드
    public ResponseEntity<Resource> download(Long fileNo) {

        //0. 유효성 체크
        AttachFile attachFile = attachFileRepository.findById(fileNo).orElseThrow(() -> new Exception404("존재하지 않는 fileNo 입니다."));

        // 1. fileId와 일치하는 파일정보(파일명, 경로) 가져옴
        String fileName = attachFile.getFileName();
        String path = attachFile.getUploadPath();
        Path filePath = Paths.get(path);

        //2. 파일경로에서 리소스 다운로드
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(filePath.toString()));
        } catch (FileNotFoundException e) {
            throw new Exception404("E40401");
        }

        //3. return
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                //ResponseEntity header에 값 넣어줌 -> 다운로드 시 파일명
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "\"")
                //ResponseEntity body에 파일 넣어줌
                .body(resource);
    }

    //파일 개별 삭제
    public void deleteFileById(Long fileNo) {

        // 1. 삭제할 파일 경로 지정
        AttachFile attachFile = attachFileRepository.findById(fileNo).orElseThrow(() -> new Exception404("존재하지 않는 fileNo 입니다."));

        try {
            // 2. DB 컬럼 삭제
            attachFileRepository.delete(attachFile);

            // 3. 파일 객체 만들기
            File file = new File(attachFile.getUploadPath());

            // 4. 파일이 존재하면 파일 삭제
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception500("deleteFileById " + e.getMessage());
        }

    }

    //파일 전체 삭제
    public void deleteFile(String refId,String fileType) {

        try {
            // 1. 삭제 할 경로 지정
            String path = uploadPath + File.separator + fileType + File.separator + refId;
            File folder = new File(path);

            // 2. DB 컬럼 삭제
            attachFileRepository.deleteByRefId(refId);

            // 3. 해당 경로의 디렉토리 삭제
            FileSystemUtils.deleteRecursively(folder);
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception500("deleteFile " + e.getMessage());
        }
    }

    public AttachFileDto.AttachFileInfo findByFileNo(Long fileNo){
        return attachFileRepository.findById(fileNo).orElseThrow(() -> new Exception404("존재하지 않는 fileNo 입니다.")).toDto();
    }

    public List<AttachFileDto.AttachFileInfo> findAllFile(){
        return attachFileRepository.findAll().stream().map(f -> f.toDto()).collect(Collectors.toList());
    }

}

