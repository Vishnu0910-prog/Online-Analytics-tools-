package com.grd.online.paper.Service;

import com.grd.online.paper.PROPERTIES;
import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.CommonException.GlobalException;
import com.grd.online.paper.Model.Question;
import com.grd.online.paper.Model.QuestionMaster;
import com.grd.online.paper.Model.UploadPath;
import com.grd.online.paper.Repository.QuestionMasterRepository;
import com.grd.online.paper.Repository.QuestionRepository;
import com.grd.online.paper.Repository.UploadPathRepository;
import com.grd.online.paper.Repository.UserRepository;
import com.grd.online.paper.Model.UserModel;
import com.grd.online.paper.utils.DirectoryUtil;
import com.grd.online.paper.utils.XlsxReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionMasterRepository questionMasterRepository;

    @Autowired
    private UploadPathRepository uploadPathRepository;

    public ResponseBean saveQuestion(Question question) {
        return ResponseBean.builder()
                .data(questionRepository.save(question))
                .message("Question saved successfully.")
                .build();
    }

    public ResponseBean getQuestionsByQuestionMasterId(Long questionMasterId) {
        List<Question> questions = questionRepository.findByQuestionMaster_QuestionMasterId(questionMasterId);
        String message;

        if (questions.isEmpty()) {
            message = "No questions found for the provided Paper ID.";
        } else {
            message = "Questions retrieved successfully.";
        }

        return ResponseBean.builder()
                .data(questions)
                .message(message)
                .build();
    }

    public ResponseBean uploadFromXLSX(MultipartFile file, Long questionMasterId, Long userId) {
        try {
            UserModel user = userRepository.findById(userId)
                    .orElseThrow(() -> new GlobalException.NotFound(PROPERTIES.MESSAGE.userNotFound));
            QuestionMaster questionMaster = questionMasterRepository.findById(questionMasterId)
                    .orElseThrow(() -> new GlobalException.NotFound("Question master not found"));

            List<List<String>> xlsx = XlsxReaderUtil.readExcelFromMultipart(file, false);

            if (xlsx == null || xlsx.isEmpty())
                throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.xlsxFileShouldNotEmpty);

            List<Question> questions = new ArrayList<Question>();
            for (int i = 1; i < xlsx.size(); i++) {
                List<String> headerRow = xlsx.get(0);
                List<String> row = xlsx.get(i);

                Question _question = new Question();
                _question.setQuestionMaster(questionMaster);
                _question.setQuestionText(_extractCellData(headerRow, row, "questionText"));
                _question.setOptionA(_extractCellData(headerRow, row, "optionA"));
                _question.setOptionB(_extractCellData(headerRow, row, "optionB"));
                _question.setOptionC(_extractCellData(headerRow, row, "optionC"));
                _question.setOptionD(_extractCellData(headerRow, row, "optionD"));
                _question.setCorrectOption(_extractCellData(headerRow, row, "correctOption"));
                _question.setMarks((int) Double.parseDouble(_extractCellData(headerRow, row, "marks")));

                questions.add(_question);
            }

            questionRepository.saveAll(questions);

            // Save file to local storage ...
            String _filePath = DirectoryUtil.createFile(null, file, user.getUsername());

            // Save the uploaded file path to the Mysql database ...
            uploadPathRepository.save(UploadPath.builder()
                    .questionMaster(questionMaster)
                    .path(_filePath)
                    .build());

            return ResponseBean.builder()
                    .message(PROPERTIES.MESSAGE.xlsxUploadSuccess)
                    .data(questions.size() + " questions uploaded successfully.")
                    .build();
        } catch (Exception e) {
            throw new GlobalException.BadRequest(e.getMessage());
        }
    }

    private String _extractCellData(List<String> headerRows, List<String> row, String headerString) {
        int index = headerRows
                .stream()
                .map(e -> e.replaceAll(" ", "").toLowerCase())
                .toList()
                .indexOf(headerString.toLowerCase());
        if (index < 0)
            throw new RuntimeException("Field header '" + headerString + "' was missing on the uploaded sheet");

        String data = row.get(index);
        if (data == null || data.isEmpty())
            throw new RuntimeException("There is an empty cell on field " + headerString);

        return data;
    }
}
