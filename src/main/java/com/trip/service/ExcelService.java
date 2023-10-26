package com.trip.service;

import com.trip.dto.user.UserDto;
import com.trip.repository.user.UserRepository;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExcelService {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private final UserRepository userRepository;


    /**
     * 엑셀 다운로드 구현 🔥🔥🔥
     */
    public void createExcelDownloadResponse(HttpServletResponse response) {

        List<UserDto.UserInfoDto> userList = userRepository.findAll().stream()
                .map(u -> u.toDto())
                .collect(Collectors.toList());

        try{
            Calendar today = Calendar.getInstance();
            String day = sdf.format(today.getTime());
            //파일명
            final String fileName = "회원리스트_" + day;
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(fileName);

            //폰트 설정
            Font Black11 = workbook.createFont();
            Black11.setFontName("나눔고딕"); //글씨체
            Black11.setFontHeight((short)(11*20)); //사이즈

            //폰트 설정
            Font fontOfGothicBlackBold16 = workbook.createFont();
            fontOfGothicBlackBold16.setFontName("나눔고딕"); //글씨체
            Black11.setFontHeight((short)(11*20)); //사이즈
            fontOfGothicBlackBold16.setBold(true);//볼드 (굵게)


            //눈금선 없애기
            sheet.setDisplayGridlines(false);

            //숫자 포맷은 아래 numberCellStyle을 적용시킬 것이다다
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            //테두리 선 (우,좌,위,아래)
            dateCellStyle.setBorderRight(BorderStyle.THIN);
            dateCellStyle.setBorderLeft(BorderStyle.THIN);
            dateCellStyle.setBorderTop(BorderStyle.THIN);
            dateCellStyle.setBorderBottom(BorderStyle.THIN);
            dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
            dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dateCellStyle.setFont(Black11);

            CellStyle cellStyle = workbook.createCellStyle();
            //테두리 선 (우,좌,위,아래)
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(Black11);

            CellStyle headerCellStyle = workbook.createCellStyle();
            //테두리 선 (우,좌,위,아래)
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setFont(fontOfGothicBlackBold16);


            //헤더
            final String[] header = {"이름", "아이디", "생년월일","성별","나이",
                    "휴대폰 번호","권한","상태","이메일","생성 날짜"};

            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerCellStyle);
            }

            //바디
            for (int i = 0; i < userList.size(); i++) {
                row = sheet.createRow(i + 1);  //헤더 이후로 데이터가 출력되어야하니 +1

                UserDto.UserInfoDto user = userList.get(i);

                Cell cell = null;
                cell = row.createCell(0);
                cell.setCellValue(user.getUserName()); //이름
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(user.getLoginId()); //아이디
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(user.getBirthDate()); //생년월일
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue(user.getGender().getId()); //성별
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(user.getAge()); //나이
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(user.getPhone()); //휴대폰 번호
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue(user.getRole().getId()); //권한
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellValue(user.getUserStatus().getId()); //상태
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellValue(user.getEmail()); //이메일
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellValue(user.getCreatedAt()); //생성 날짜
                cell.setCellStyle(dateCellStyle); //날짜포맷 적용

//                cell.setCellStyle(numberCellStyle);      //숫자포맷 적용
            }


            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }


}
